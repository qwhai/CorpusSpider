package org.spider.corpus.tools;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.utils.naga.web.HTMLParserUtils;

/**
 * <p>
 * 将HTML中会被显示给用户的内容给筛选出来
 * </p>
 * 2015年12月7日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/
 *      lemon_tree12138</a>
 * @version 0.1
 */
public class HTMLContentFilter {

    public static void main(String[] args) {
        new HTMLContentFilter().filter("http://music.baidu.com/song/259143606");
    }

    private void filter(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            System.out.println(document.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
