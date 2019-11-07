package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.ClearRedis;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

/*@Configuration//表明当前这个类是配置类
@Aspect*/
public class RedisCacheAop {
    @Autowired
    private Jedis jedis;

    @Around("execution(* com.baizhi.service.impl.*.selectAll(..))")
    //切入点表达式表示切所有的查询方法，第一个*表示返回值，
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //判断目标方法上是否存在RedisCache注解
        //如果需要，则需要缓存
        //如果没有，则不需要缓存，直放行

        //获取目标方法
        Object target = proceedingJoinPoint.getTarget();//获取目标方法的类的对象    targetcom.baizhi.service.impl.AlbumServiceImpl@3d56229
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();//获取目标方法   signatureMap com.baizhi.service.impl.AlbumServiceImpl.selectAll(Integer,Integer)
        Object[] args = proceedingJoinPoint.getArgs();//获取目标方法的参数
        Method method = methodSignature.getMethod();
        boolean annotationPresent = method.isAnnotationPresent(RedisCache.class);//判断当前这个方法上是否存在这个注解
        if (annotationPresent) {
            //直接方法redis数据库，根据key获取对应值
            // com.baizhi.service.impl.BannerServiceImpl.selectAll(1,3);
            String ClassName = target.getClass().getName();//拿到类名
            String MethodName = method.getName();//拿到方法名
            StringBuilder sb = new StringBuilder();
            sb.append(ClassName).append(".").append(MethodName).append("(");
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i == args.length - 1) {
                    break;
                }
                sb.append(",");
            }
            sb.append(")");
            String key = sb.toString();//完整的key
            //判断redis缓存中是否存在这个key
            if (jedis.exists(key)) {
                String result = jedis.get(key);
                return JSONObject.parse(result);
            } else {
                Object result = proceedingJoinPoint.proceed();
                jedis.set(key, JSONObject.toJSONString(result));
                return result;
            }
        } else {
            Object proceed = proceedingJoinPoint.proceed();//方法放行
            return proceed;
        }
    }

    @After("execution(* com.baizhi.service.impl.*.*(..)) && !execution(* com.baizhi.service.impl.*.selectAll(..))")
    public void after(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        String name = target.getClass().getName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //Object[] args = joinPoint.getArgs();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(ClearRedis.class);//判断当前这个方法上是否存在这个注解
        if (b) {
            //清理缓存
            Set<String> keys = jedis.keys("*");
            for (String key : keys) {
                if (key.startsWith(name)) {
                    jedis.del(key);
                }
            }
        }
    }
}
