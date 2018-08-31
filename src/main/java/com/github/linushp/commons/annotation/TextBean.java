package com.github.linushp.commons.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TextBean {
    TextBeanTypeEnum textType() default TextBeanTypeEnum.JSON;
}
