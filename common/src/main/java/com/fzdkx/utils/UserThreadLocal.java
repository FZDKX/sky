package com.fzdkx.utils;

/**
 * @author 发着呆看星
 * @create 2023/8/21 11:23
 */
public class UserThreadLocal {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();


    public static Long getId(){
        return threadLocal.get();
    }

    public static void setId(Long id){
        threadLocal.set(id);
    }
    public static void remove(){
        Long id = threadLocal.get();
        if (id != null){
            threadLocal.remove();
        }
    }
}
