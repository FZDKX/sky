package com.fzdkx.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/28 11:36
 */
public class LocalDateUtils {
    public static List<LocalDate> getDateList(LocalDate begin, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        if (begin != null && end != null && !end.isBefore(begin)){
            dates.add(begin);
            while (!begin.equals(end)){
                begin = begin.plusDays(1);
                dates.add(begin);
            }
        }
        return dates;
    }
}
