package com.fzdkx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/24 20:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    public static Integer NO_PAY = 1;
    public static Integer WAIT_ORDER = 2;
    public static Integer RECEIVE_ORDER = 3;
    public static Integer SHIPMENT = 4;
    public static Integer SUCCESS = 5;
    public static Integer CANCEL = 6;
    public static Integer REFUND = 7;

    public static Integer NO = 0;
    public static Integer YES = 1;
    public static Integer TK = 2;

    public static Integer WX = 1;
    public static Integer ZFB = 2;


    private Long id;
    private String number;
    private Integer status;
    private Long userId;
    private Long addressBookId;
    private LocalDateTime orderTime;
    private LocalDateTime checkoutTime;
    private Integer payMethod;
    private Integer payStatus;
    private BigDecimal amount;
    private String remark;
    private String phone;
    private String address;
    private String userName;
    private String consignee;
    private String cancelReason;
    private String rejectionReason;
    private LocalDateTime cancelTime;
    private LocalDateTime estimatedDeliveryTime;
    private Integer deliveryStatus;
    private LocalDateTime deliveryTime;
    private Integer packAmount;
    private Integer tablewareNumber;
    private Integer tablewareStatus;
}
