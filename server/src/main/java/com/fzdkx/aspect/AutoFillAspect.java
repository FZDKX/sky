package com.fzdkx.aspect;

import com.fzdkx.annotion.AutoFill;
import com.fzdkx.constant.AutoFillType;
import com.fzdkx.constant.MessageConstant;
import com.fzdkx.constant.SqlConstant;
import com.fzdkx.utils.IdThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/17 20:53
 * 自动填充切面
 */
@Component
@Aspect
@Slf4j
public class AutoFillAspect {
    /**
     * 切点表达式含义
     * com.fzdkx.mapper包下(不含子包) 所有的类 的 所有的 方法(不限访问修饰符与方法参数) 中
     * 筛选 方法上标注 注解 @AutoFill 的方法
     */
    @Pointcut("execution(* com.fzdkx.mapper.*.*(..)) && @annotation(com.fzdkx.annotion.AutoFill)")
    public void t1() {
    }

    @Before("t1()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("对公共字段进行填充");
        // 获取方法注解上的数据库操作类型 INSERT or UPDATE
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 获取方法签名
        Method method = signature.getMethod();  // 获取方法
        AutoFill autoFill = method.getAnnotation(AutoFill.class);  // 获取方法上的注解
        AutoFillType type = autoFill.value();  // 获取注解的value值

        // 获取方法的第一个参数--实体类对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        // 根据数据库操作类型，对实体类对象进行公共字段填充
        LocalDateTime now = LocalDateTime.now();
        Long id = IdThreadLocal.getId();
        try {
            if (type == AutoFillType.INSERT) {
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, id);
            }
            Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
            setUpdateTime.invoke(entity, now);
            setUpdateUser.invoke(entity, id);
        } catch (Exception e) {
            log.error(MessageConstant.AUTO_FILL_ERROR);
        }
    }
}
