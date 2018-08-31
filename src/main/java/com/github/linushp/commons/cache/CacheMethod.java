package com.github.linushp.commons.cache;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheMethod {
    long activeTime() default 5000;

    String cacheKey() default "";

    int[] paramKey() default {};
}