package org.spider.corpus.tools;

import java.io.IOException;
import java.util.List;

import org.utils.naga.files.FileReadUtils;
import org.utils.naga.str.StringUtils;

/**
 * <p>解析文件，找到所有匹配的文本</p>
 * 2015年12月8日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class ParserURL {

    public static void main(String[] args) {
        String pattern = "<a target=\"_blank\" href=\"http://dataunion.org/[0-9]+.html\">";
        String string = "";
        try {
            string = FileReadUtils.readToString("C:\\Users\\Naga\\Desktop\\BUFFER.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        List<String> list = StringUtils.RegexUtils.getSubs(string, pattern);
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            System.out.println(list.get(i));
        }
    }
}
