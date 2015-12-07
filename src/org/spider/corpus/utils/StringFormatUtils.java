package org.spider.corpus.utils;

import org.spider.corpus.consts.Constants;
import org.utils.naga.exceptions.CannotInstanceException;
import org.utils.naga.interf.UtilsInterface;

/**
 * <p>字符串格式化操作工具类</p>
 * 2015年11月25日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class StringFormatUtils implements UtilsInterface {

    // 工具类，禁止实例化
    private StringFormatUtils() throws CannotInstanceException {
        throw new CannotInstanceException(getTag() + ":请不要试图实例化我");
    }
    
    @Override
    public void description() {
        System.out.println("你可以通过我来进行一些字符串的格式化操作。");
    }

    @Override
    public String getTag() {
        return getClass().getName();
    }
    
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
