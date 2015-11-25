package org.spider.corpus.utils;

import org.spider.corpus.consts.Constants;

public class StringFormatUtils {

    public static String formatURL(String url) {
        return url.replaceAll("/", Constants.SLASH_REPLACE).replaceAll(":", Constants.COLON_REPLACE);
    }
    
    private static String replace(String label, String regex, String replacement) {
        return label.replaceAll(regex, replacement);
    }
    
    public static void main(String[] args) {
        System.out.println(replace("http://www.baidu.com/", "/", Constants.SLASH_REPLACE));
        System.out.println("http://www.baidu.com/".replaceAll("/", Constants.SLASH_REPLACE).replaceAll(":", Constants.COLON_REPLACE));
    }
}
