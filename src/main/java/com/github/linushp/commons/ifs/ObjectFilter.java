package com.github.linushp.commons.ifs;

public interface ObjectFilter<T> {
    boolean isOK(T obj);
}