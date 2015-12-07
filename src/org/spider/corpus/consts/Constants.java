package org.spider.corpus.consts;

/**
 * <p>程序中使用的常量定义</p>
 * 2015年11月25日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class Constants {

    // ':'的替代字符串(字符':'的十六进制表示为3A)
    public static final String COLON_REPLACE = "%3A";
    
    // '/'的替代字符串(字符'/'的十六进制表示为2F)
    public static final String SLASH_REPLACE = "%2F";
    
    // 只有网址的数据集路径
    public static final String RAW_PATH = "E:/workspace/src/Java/Bigdata/Classify/URL/naive_bayes_classifier_data/raw_html_set/";
    
    // 从网址中解析出的文本数据集路径
    public static final String PARSER_PATH = "E:/workspace/src/Java/Bigdata/Classify/URL/naive_bayes_classifier_data/parser_html_set/";
}
