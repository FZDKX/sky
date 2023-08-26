package com.fzdkx.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fzdkx.constant.MessageConstant;
import com.fzdkx.controller.websocket.MyWebSocketServer;
import com.fzdkx.dto.*;
import com.fzdkx.entity.*;
import com.fzdkx.exception.BaseException;
import com.fzdkx.exception.OrderBusinessException;
import com.fzdkx.exception.ParamException;
import com.fzdkx.exception.SqlException;
import com.fzdkx.mapper.*;
import com.fzdkx.properties.BaiDuProperties;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.OrderService;
import com.fzdkx.utils.HttpClientUtil;
import com.fzdkx.utils.UserThreadLocal;
import com.fzdkx.vo.FindOrderVO;
import com.fzdkx.vo.OrderPaymentVO;
import com.fzdkx.vo.OrderSubmitVO;
import com.fzdkx.vo.OrderVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fzdkx.entity.Order.*;

/**
 * @author 发着呆看星
 * @create 2023/8/24 19:56
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AddressBookMapper addressBookMapper;
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private MyWebSocketServer myWebSocketServer;
    @Resource
    private BaiDuProperties baiDuProperties;

    @Override
    @Transactional
    public OrderSubmitVO orderSubmit(OrderSubmitDTO orderSubmitDTO) {
        Long userId = UserThreadLocal.getId();
        // 校验数据
        // 1. 校验地址簿
        AddressBook addressBook = addressBookMapper.selectAddressById(orderSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }

        checkOutOfRange(addressBook.getProvinceName() + addressBook.getCityName() +
                                    addressBook.getDistrictName() + addressBook.getDetail());

        // 2. 校验购物车
        List<ShoppingCart> list = shoppingCartMapper.selectCartList(userId);
        if (list == null || list.size() == 0) {
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }

        // 向订单表中插入一条数据
        Order order = builder()
                .number(UUID.randomUUID().toString())
                .status(NO_PAY)
                .userId(userId)
                .orderTime(LocalDateTime.now())
                .payStatus(NO)
                .phone(addressBook.getPhone())
                .consignee(addressBook.getConsignee()).build();
        BeanUtils.copyProperties(orderSubmitDTO, order);

        orderMapper.insert(order);

        ArrayList<OrderDetail> orderDetails = new ArrayList<>(list.size());
        // 向订单明细表中插入n条数据
        for (ShoppingCart cart : list) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetails.add(orderDetail);
        }
        orderDetailMapper.insertList(orderDetails);

        // 清空购物车数据
        shoppingCartMapper.deleteCartAll(userId);

        // 封装返回对象

        // 返回
        return OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderAmount(order.getAmount())
                .orderTime(order.getOrderTime()).build();

    }

    /**
     * 订单支付
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = UserThreadLocal.getId();
        User user = userMapper.getById(userId);

//        //调用微信支付接口，生成预支付交易单
//        JSONObject jsonObject = weChatPayUtil. pay(
//                ordersPaymentDTO.getOrderNumber(), //商户订单号
//                new BigDecimal(0.01), //支付金额，单位 元
//                "苍穹外卖订单", //商品描述
//                user.getOpenid() //微信用户的openid
//        );

        JSONObject jsonObject = new JSONObject();
        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new SqlException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Order ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Order orders = Order.builder()
                .id(ordersDB.getId())
                .status(WAIT_ORDER)
                .payStatus(WX)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);

        // 封装来单提醒数据
        Map<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("orderId", orders.getId());
        map.put("content", "订单号：" + outTradeNo);
        String json = JSONUtils.toJSONString(map);
        // 给管理端推送新订单消息
        myWebSocketServer.sendToAllClient(json);
    }

    @Override
    public void reminder(Long id) {
        Order order = orderMapper.selectOrder(id);
        if (order == null) {
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }
        // 封装催单响应数据
        Map<String, Object> map = new HashMap<>();
        map.put("type", 2);
        map.put("orderId", id);
        map.put("content", "订单号：" + order.getNumber());
        String json = JSONUtils.toJSONString(map);
        // 响应给管理端
        myWebSocketServer.sendToAllClient(json);
    }

    @Override
    public PageResult<OrderVO> historyOrders(HistoryOrdersDTO historyOrdersDTO) {
        Long userId = UserThreadLocal.getId();

        PageHelper.startPage(historyOrdersDTO.getPage(), historyOrdersDTO.getPageSize());

        // 查询用户历史订单
        List<Order> orders = orderMapper.selectHistoryOrder(historyOrdersDTO.getStatus(), userId);
        if (orders == null || orders.size() == 0) {
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }
        // 保存VO
        ArrayList<OrderVO> list = new ArrayList<>();
        for (Order order : orders) {
            OrderVO orderVO = new OrderVO();
            // 拷贝数据
            BeanUtils.copyProperties(order, orderVO);
            // 查询相关菜品信息
            List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(order.getId());
            // 注入菜品信息
            orderVO.setOrderDetailList(orderDetails);
            list.add(orderVO);
        }
        // 获取分页数据
        PageInfo<OrderVO> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        List<OrderVO> orderVOList = pageInfo.getList();

        return new PageResult<>(total, orderVOList);
    }

    @Override
    public void cancel(Long id) {
        LocalDateTime now = LocalDateTime.now();
        Order order = builder()
                .status(6)
                .cancelReason("客户主动取消")
                .cancelTime(now)
                .id(id).build();
        orderMapper.update(order);
    }

    @Override
    public OrderVO orderDetail(Long id) {
        Order order = orderMapper.selectOrder(id);
        if (order == null) {
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }
        // 拼接地址
        String address = getAddress(order.getAddressBookId());
        // 获取订单详细信息
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(order.getId());
        for (OrderDetail orderDetail : orderDetails) {

        }
        // 封装VO对象
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        orderVO.setOrderDetailList(orderDetails);
        orderVO.setAddress(address);
        return orderVO;
    }

    @Override
    public void repetition(Long id) {
        // 查询订单详情
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(id);
        // 判断是否存在
        if (orderDetails == null || orderDetails.size() == 0) {
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }
        Long userId = UserThreadLocal.getId();
        LocalDateTime now = LocalDateTime.now();
        List<ShoppingCart> carts = new ArrayList<>();
        // 遍历订单详情集合封装购物车对象
        for (OrderDetail orderDetail : orderDetails) {
            ShoppingCart cart = new ShoppingCart();
            BeanUtils.copyProperties(orderDetail, cart);
            cart.setUserId(userId);
            cart.setCreateTime(now);
            carts.add(cart);
        }
        shoppingCartMapper.insertCartList(carts);
    }

    @Override
    public PageResult<FindOrderVO> conditionSearch(FindOrderDTO findOrderDTO) {
        PageHelper.startPage(findOrderDTO.getPage(), findOrderDTO.getPageSize());
        List<Order> orders = orderMapper.selectOrderList(findOrderDTO);
        if (orders == null || orders.size() == 0) {
            new PageResult<>();
        }
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        List<FindOrderVO> orderVOList = getVOList(orders);
        return new PageResult<>(pageInfo.getTotal(), orderVOList);
    }

    @Override
    public void delivery(Long id) {
        Order order = builder()
                .id(id)
                .status(4)
                .estimatedDeliveryTime(LocalDateTime.now().plusMinutes(20)).build();
        orderMapper.update(order);
    }

    @Override
    public void confirm(Long id) {
        Order order = builder()
                .id(id)
                .status(3).build();
        orderMapper.update(order);
    }

    @Override
    public void rejection(RejectionDTO rejectionDTO) {

        Order orderDb = orderMapper.selectOrder(rejectionDTO.getId());
        if (orderDb == null || !orderDb.getStatus().equals(2)){
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }
         //支付状态
        Integer payStatus = orderDb.getPayStatus();
        if (payStatus.equals(1)) {
            //用户已支付，需要退款
//            String refund = weChatPayUtil.refund(
//                    order.getNumber(),
//                    order.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
            log.info("申请退款：{}", orderDb.getId() );
        }

        Order order = builder()
                .id(orderDb.getId())
                .status(6)
                .rejectionReason(rejectionDTO.getRejectionReason())
                .build();
        orderMapper.update(order);

    }

    @Override
    public void adminCancel(CancelDTO cancelDTO) {
        Order orderDb = orderMapper.selectOrder(cancelDTO.getId());
        //支付状态
        Integer payStatus = orderDb.getPayStatus();
        if (payStatus.equals(1)) {
            //用户已支付，需要退款
//            String refund = weChatPayUtil.refund(
//                    order.getNumber(),
//                    order.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
            log.info("申请退款：{}", orderDb.getId() );
        }

        Order order = builder()
                .id(cancelDTO.getId())
                .status(6)
                .cancelReason(cancelDTO.getCancelReason())
                .build();
        orderMapper.update(order);
    }

    @Override
    public void complete(Long id) {

        Order orderDb = orderMapper.selectOrder(id);

        if (orderDb == null || !orderDb.getStatus().equals(4)){
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }

        Order order = builder()
                .id(id)
                .status(5)
                .deliveryTime(LocalDateTime.now()).build();
        orderMapper.update(order);
    }

    @Override
    public StatisticsVO statistics() {
        Integer confirmed = orderMapper.selectConfirmed();
        Integer deliveryInProgress = orderMapper.selectDeliveryInProgress();
        Integer toBeConfirmed = orderMapper.selectToBeConfirmed();
        return new StatisticsVO(confirmed, deliveryInProgress, toBeConfirmed);
    }

    private List<FindOrderVO> getVOList(List<Order> orders) {
        List<FindOrderVO> list = new ArrayList<>();
        // 保存VO
        for (Order order : orders) {
            FindOrderVO findOrderVO = new FindOrderVO();
            BeanUtils.copyProperties(order, findOrderVO);
            String address = getAddress(order.getAddressBookId());
            findOrderVO.setAddress(address);
            List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(order.getId());
            List<String> collect = orderDetails.stream().map
                            (x -> x.getName() + "*" + x.getNumber())
                    .collect(Collectors.toList());
            String join = String.join("", collect);
            findOrderVO.setOrderDishes(join);
            list.add(findOrderVO);
        }
        return list;
    }

    private String getAddress(Long addressBookId) {
        AddressBook addressBook = addressBookMapper.selectAddressById(addressBookId);
        return addressBook.getProvinceName()
                + addressBook.getCityName()
                + addressBook.getDistrictName()
                + "(--" + addressBook.getDetail() + ")";
    }

    private void checkOutOfRange(String userAddress){
        // 获取商品地址的经纬度

        // 1.获取参数，构建map
        Map<String, String> map = new HashMap<>();
        String shopAddress = baiDuProperties.getShopAddress();
        String ak = baiDuProperties.getAk();
        map.put("address",shopAddress);
        map.put("ak",ak);
        map.put("output","json");

        // 2.调用API
        String shopCoordinate = HttpClientUtil.doGet("https://api.map.baidu.com/geocoding/v3", map);

        // 3.解析API
        JSONObject shopJsonObject = JSON.parseObject(shopCoordinate);
        if (!shopJsonObject.getString("status").equals("0")) {
            throw new OrderBusinessException("店铺地址解析失败");
        }
        JSONObject shopLocation = shopJsonObject.getJSONObject("result").getJSONObject("location");
        String shopLat = shopLocation.getString("lat");
        String shopLng = shopLocation.getString("lng");
        String shopLngLat = shopLat + "," + shopLng;


        // 获取用户地址的经纬度
        map.put("address", userAddress);
        String userCoordinate = HttpClientUtil.doGet("https://api.map.baidu.com/geocoding/v3", map);
        JSONObject userJsonObject = JSON.parseObject(userCoordinate);
        if (!userJsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("用户地址解析失败");
        }
        JSONObject userLocation = userJsonObject.getJSONObject("result").getJSONObject("location");
        String userLat = userLocation.getString("lat");
        String userLng = userLocation.getString("lng");
        String userLngLat = userLat + "," + userLng;

        // 路线规划
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("ak",ak);
        hashMap.put("origin",shopLngLat);
        hashMap.put("destination",userLngLat);
        String json = HttpClientUtil.doGet("https://api.map.baidu.com/directionlite/v1/driving", map);

        JSONObject jsonObject = JSON.parseObject(json);
        if (!jsonObject.getString("status").equals("0")) {
            throw new OrderBusinessException("路线规划失败");
        }
        JSONObject result = jsonObject.getJSONObject("result");
        // routes：有很多路线，是一个数组
        JSONArray jsonArray =(JSONArray) result.get("routes");
        // 获取第一种路线下的 距离
        Integer distance = (Integer)((JSONObject) jsonArray.get(0)).get("distance");

        // 如果距离 > 5000米，则不能配送
        if (distance > 5000){
            throw new OrderBusinessException("超出配送范围");
        }
    }
}
