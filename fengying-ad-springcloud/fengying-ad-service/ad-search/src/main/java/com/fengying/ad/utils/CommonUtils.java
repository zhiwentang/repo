package com.fengying.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

public class CommonUtils {

    //当key不存在的时候 new一个新的value出来
    public static <K, V> V getorCreate(K key, Map<K, V> map,
                                       Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringConcat(String... args){
        StringBuilder sb=new StringBuilder();
        for(String s:args){
            sb.append(s);
            sb.append("-");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}
