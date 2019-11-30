package com.fengying.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
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

    //Tue Jan 01 08:00:00 CST 2019
    public static Date parseStringDate(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
                    Locale.US);
            return DateUtils.addHours(dateFormat.parse(dateString), -8);
        }catch (ParseException ex){
            log.error("parseStringDate error:{}",dateString);
            return null;
        }

    }

}
