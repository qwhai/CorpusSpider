package org.spider.corpus.tools;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.utils.naga.web.HTMLParserUtils;

/**
 * 将HTML中会被显示给用户的内容给筛选出来
 * 
 * @author Naga
 * Blog : http://blog.csdn.net/lemon_tree12138
 */
public class HTMLContentFilter {

    public static void main(String[] args) {
        new HTMLContentFilter().filter("http://dataunion.org/13026.html");
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
