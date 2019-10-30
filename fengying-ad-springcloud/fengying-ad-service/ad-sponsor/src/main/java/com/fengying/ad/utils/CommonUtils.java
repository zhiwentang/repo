package com.fengying.ad.utils;

import com.fengying.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

public class CommonUtils {

    //可以添加任意格式
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String dateString) throws AdException {

        try{
            return DateUtils.parseDate(dateString,parsePatterns);
        }catch (Exception e){
            throw new AdException(e.getMessage());
        }
    }
}
