package com.fzdkx.utils;

/**
 * @author 发着呆看星
 * @create 2023/8/11 15:46
 */
public class IdThreadLocal {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setId(Long id) {
        threadLocal.set(id);
    }

    public static Long getId() {
        return threadLocal.get();
    }

    public static void removeId() {
        if (threadLocal.get() != null){
            threadLocal.remove();
        }
    }

}
