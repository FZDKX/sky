package com.fzdkx.service;

import com.fzdkx.vo.Top10VO;

import java.time.LocalDate;

/**
 * @author 发着呆看星
 * @create 2023/8/28 13:17
 */
public interface OrderDetailService {
    Top10VO top10(LocalDate begin, LocalDate end);

}
