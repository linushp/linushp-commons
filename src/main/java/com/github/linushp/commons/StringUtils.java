package com.github.linushp.commons;


import com.alibaba.fastjson.JSON;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return isEmpty(str) || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }


    public static boolean isBlankString(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return isBlank((String) value);
        }
        return false;
    }

    public static String join(Collection collection, String flag) {
        return join(collection, flag, null);
    }


    public static String join(Collection collection, String flag, StringParser stringParser) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }

        Object[] objArray = collection.toArray(new Object[collection.size()]);
        return join(objArray, flag, stringParser);
    }


    public static String join(Object[] o, String flag, StringParser stringParser) {

        if (o == null || o.length == 0) {
            return "";
        }

        StringBuilder str_buff = new StringBuilder();

        for (int i = 0, len = o.length; i < len; i++) {

            Object obj = o[i];

            String str;
            if (stringParser != null) {
                str = stringParser.valueOf(obj);
            } else {
                str = String.valueOf(obj);
            }


            str_buff.append(str);

            if (i < len - 1) {
                str_buff.append(flag);
            }
        }

        return str_buff.toString();
    }

    public static String replaceAll(String str, Map<String, String> map) {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            str = str.replaceAll(entry.getKey(), entry.getValue());
        }
        return str;
    }


    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰法转下划线
     *
     * @param str 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static final Map<String, String> camel2UnderlineCache = new ConcurrentHashMap<>();

    public static String camel2Underline(String str, boolean isCache) {
        if (isCache) {
            String result = camel2UnderlineCache.get(str);
            if (result == null) {
                result = camel2Underline(str);
                camel2UnderlineCache.put(str, result);
            }
            return result;
        }
        return camel2Underline(str);
    }


    public static String appendIfNotEnd(String path, String s) {
        if (path.endsWith(s)) {
            return path;
        }
        return path + s;
    }


    public static String getRootPath(URL url) {

        /*
         * "file:/home/whf/cn/fh" ==》 "/home/whf/cn/fh"
         * "jar:file:/home/whf/foo.jar!cn/fh" ==》 "/home/whf/foo.jar"
         */
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    public static String dotToSplash(String name) {

        /*
         * "cn.fh.lightning" ==》 "cn/fh/lightning"
         */
        return name.replaceAll("\\.", "/");
    }

    public static String splashToDot(String name) {
        return name.replaceAll("/", ".");
    }

    public static String trimExtension(String name) {

        /*
         * "Apple.class" -> "Apple"
         */
        int pos = name.lastIndexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }


    public static boolean isIntegerNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char x = str.charAt(i);
            if (x < '0' || x > '9') {
                return false;
            }
        }
        return true;
    }


    public static String ignoreSuffix(String str, String suffix) {
        if (isEmpty(str)) {
            return str;
        }

        if (str.endsWith(suffix)) {
            int endIndex = str.length() - suffix.length();
            return str.substring(0, endIndex);
        }
        return str;
    }

    public static String ignorePrefix(String str, String prefix) {
        if (isEmpty(str)) {
            return str;
        }

        if (str.startsWith(prefix)) {
            int endIndex = str.length();
            int startIndex = prefix.length();
            return str.substring(startIndex, endIndex);
        }
        return str;
    }


    /**
     * 下划线转驼峰
     */
    public static String underline2Camel(String str) {
        if (str == null) {
            return str;
        }

        str = str.trim();
        int strLength = str.length();
        if (strLength == 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        while (i < strLength) {
            char charAtIndex = str.charAt(i);
            if (charAtIndex != '_') {
                int beforeChatIndex = i - 1;

                //小写字母，并且前面有下划线
                if (beforeChatIndex >= 0 && str.charAt(beforeChatIndex) == '_' && charAtIndex >= 'a' && charAtIndex <= 'z') {
                    charAtIndex = (char) (charAtIndex - 32);
                }
                stringBuilder.append(charAtIndex);
            }
            i++;
        }
        return stringBuilder.toString();
    }


    public static final Map<String, String> underline2CamelCache = new ConcurrentHashMap<>();


    /**
     * 下划线转驼峰
     */
    public static String underline2Camel(String str, boolean isCache) {
        if (isCache) {
            String result = underline2CamelCache.get(str);
            if (result == null) {
                result = underline2Camel(str);
                underline2CamelCache.put(str, result);
            }
            return result;
        }
        return underline2Camel(str);
    }


    public static String trimString(Object obj){
        if (obj == null){
            return "";
        }
        return obj.toString().trim();
    }


}
