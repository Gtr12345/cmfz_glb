package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.ClearRedis;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

@Configuration//表明当前这个类是配置类
@Aspect
public class RedisCacheAopHash {
    @Autowired
    private Jedis jedis;

    @Around("execution(* com.baizhi.service.impl.*.selectAll(..))")
    //切入点表达式表示切所有的查询方法，的一个*表示返回值，
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object target = proceedingJoinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(RedisCache.class);
        if (b) {
            String ClassName = target.getClass().getName();
            StringBuilder sb = new StringBuilder();
            String methodName = method.getName();
            sb.append(methodName).append("(");
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i == args.length - 1) {
                    break;
                }
                sb.append(",");
            }
            sb.append(")");
            System.out.println(ClassName);
            System.out.println(sb.toString());
            if (jedis.hexists(ClassName, sb.toString())) {
                //判断redis中是否存在对应的key
                String hget = jedis.hget(ClassName, sb.toString());
                return hget;
            } else {
                Object proceed = proceedingJoinPoint.proceed();
                jedis.hset(ClassName, sb.toString(), JSONObject.toJSONString(proceed));
                return proceed;
            }
        } else {
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        }
    }

    @After("execution(* com.baizhi.service.impl.*.*(..)) && !execution(* com.baizhi.service.impl.*.selectAll(..))")
    public void after(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        String name = target.getClass().getName();
        if (method.isAnnotationPresent(ClearRedis.class)) {
            jedis.del(name);
        }
    }
}
