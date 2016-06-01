package com.example.yangtianrui.reader.util;

/**
 * Created by yangtianrui on 16-5-31.
 */
public class FormatUtil {
    public static String subText(String title, int subLength) {
        if (title.length() > subLength) {
            return title.substring(0, subLength) + "...";
        } else {
            return title;
        }
    }
}
