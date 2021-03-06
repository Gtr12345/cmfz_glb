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
            System.out.println("方法名" + ClassName);
            System.out.println("类名" + sb.toString());
            if (jedis.hexists(ClassName, sb.toString())) {
                //判断redis中是否存在对应的key
                System.out.println("有redis缓存");
                String hget = jedis.hget(ClassName, sb.toString());
                return JSONObject.parse(hget);
            } else {
                System.out.println("无redis缓存");
                Object proceed = proceedingJoinPoint.proceed();
                System.out.println(proceed);
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
        Object target = joinPoint.getTarget();//获取目标方法的类的对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();//获取目标方法
        Method method = methodSignature.getMethod();
        String name = target.getClass().getName();
        //判断方法上是否存在此注解
        if (method.isAnnotationPresent(ClearRedis.class)) {
            //清理redis缓存
            jedis.del(name);
        }
    }
}
