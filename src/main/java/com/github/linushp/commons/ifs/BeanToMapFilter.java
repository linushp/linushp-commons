package com.github.linushp.commons.ifs;

import com.github.linushp.commons.BeanField;


/**
 * 用户可以通过此方式自己扩展注解配置
 */
public interface BeanToMapFilter {

    boolean isInclude(Object value, BeanField beanField) throws Exception;

    String getMapKey(BeanField beanField);

    /**
     * 转换成Map的Value数据类型
     *
     * @param value     数据
     * @param beanField Bean Field
     * @return 转换之后的数据类型
     */
    Object toMapValueType(Object value, BeanField beanField);
}