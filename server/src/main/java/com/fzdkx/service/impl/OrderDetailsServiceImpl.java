package com.fzdkx.service.impl;

import com.fzdkx.dto.GoodsSalesDTO;
import com.fzdkx.exception.ParamException;
import com.fzdkx.mapper.OrderDetailMapper;
import com.fzdkx.service.OrderDetailService;
import com.fzdkx.vo.Top10VO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 发着呆看星
 * @create 2023/8/28 13:18
 */
@Service
public class OrderDetailsServiceImpl implements OrderDetailService {

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Top10VO top10(LocalDate begin, LocalDate end) {
        if (begin == null || end == null || begin.isAfter(end)) {
            throw new ParamException("参数传递错误");
        }
        // 获取销量与菜品名称 的集合
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> list = orderDetailMapper.selectTop10(beginTime, endTime);
        // 菜品名称集合
        List<String> nameList = list.stream()
                .map(GoodsSalesDTO::getName)
                .collect(Collectors.toList());
        // 销量集合
        List<Integer> salesList = list.stream()
                .map(GoodsSalesDTO::getSales)
                .collect(Collectors.toList());

        return new Top10VO(StringUtils.join(nameList,","),
                            StringUtils.join(salesList,","));
    }
}
