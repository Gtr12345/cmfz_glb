package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//指明当前注解所使用的位置
@Retention(RetentionPolicy.RUNTIME)//指明当前注解所生效的时机
public @interface ClearRedis {
}
