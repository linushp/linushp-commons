package com.github.linushp.commons.cache;

import com.github.linushp.commons.StringUtils;

import java.lang.reflect.Method;

public class CacheAnnotationUtils {

    private static final String CACHE_KEY_PREFIX = "sys:";

    public static boolean isNeedCache(Method method){

        CacheMethod cacheAnnotation = method.getDeclaredAnnotation(CacheMethod.class);
        if (cacheAnnotation == null) {
            return false;
        }

        return true;
    }


    public static Object getResultFromCacheAnnotation(Method method, Object[] params) {
        CacheMethod cacheAnnotation = method.getDeclaredAnnotation(CacheMethod.class);
        if (cacheAnnotation == null) {
            return null;
        }
        String key = toCacheKey(method, cacheAnnotation, params);
        return CacheManager.getObject(key);
    }


    public static void saveResultToCacheAnnotation(Method method, Object[] params, Object invokeResult) {

        CacheMethod cacheAnnotation = method.getAnnotation(CacheMethod.class);
        if (cacheAnnotation == null) {
            return;
        }

        String key = toCacheKey(method, cacheAnnotation, params);

        long activeTime = cacheAnnotation.activeTime();
        CacheManager.putObject(key, invokeResult, activeTime);
    }


    private static String toCacheKey(Method method, CacheMethod cacheAnnotation, Object[] params) {

        String cache_Key = cacheAnnotation.cacheKey();

        if (StringUtils.isEmpty(cache_Key)) {
            cache_Key = method.getDeclaringClass().getSimpleName() + ":" + method.getName();
        }


        String key = CACHE_KEY_PREFIX + cache_Key;

        int[] paramsKey = cacheAnnotation.paramKey();
        if (paramsKey.length == 0) {
            return key;
        }


        StringBuilder sb = new StringBuilder();
        sb.append(key);
        for (int i = 0; i < paramsKey.length; i++) {
            sb.append("_");
            sb.append(String.valueOf(params[paramsKey[i]]));
        }

        return sb.toString();
    }

}
