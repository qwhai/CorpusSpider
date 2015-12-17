package org.spider.corpus.tools;

import java.io.IOException;
import java.util.List;

import org.utils.naga.files.FileReadUtils;
import org.utils.naga.str.StringUtils;

/**
 * <p>
 * 提取出网页文本的有效链接
 * </p>
 * 2015年12月16日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class ExtractionKeywords {

    public static void main(String[] args) {
        String text = "";
        try {
            text = FileReadUtils.readToString("C:/Users/Naga/Desktop/BUFFER.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        List<String> list = StringUtils.RegexUtils.getSubs(text, "<a class=\"article-title\" href=\"http://www.guokr.com/article/[0-9]+/");
        for (String string : list) {
            System.out.println(string.replace("<a class=\"article-title\" href=\"", ""));
        }
    }
}
