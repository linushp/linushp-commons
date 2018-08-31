package com.github.linushp.commons.impl;

import com.github.linushp.commons.BeanField;

public class IgnoreNullToMapFilter extends DefaultBeanToMapFilter {

    public IgnoreNullToMapFilter() {
        super(false);
    }

    public IgnoreNullToMapFilter(boolean isUnderlineKey) {
        super(isUnderlineKey);
    }

    public boolean isInclude(Object value, BeanField beanField) throws Exception {
        Object v = beanField.getBeanValue(value);
        if (v == null) {
            return false;
        }
        return true;
    }

}
