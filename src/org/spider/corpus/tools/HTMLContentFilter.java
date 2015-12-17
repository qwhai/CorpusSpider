package org.spider.corpus.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.nodes.Document;
import org.spider.corpus.utils.AnjsSplitWordsUtils;
import org.utils.naga.web.impl.WebHTMLParserImpl;
import org.utils.naga.web.poke.HTMLParserStrategy;

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

    private static HTMLParserStrategy htmlParser;
    
    public static void main(String[] args) {
        HTMLContentFilter filter = new HTMLContentFilter();
        htmlParser = new HTMLParserStrategy(new WebHTMLParserImpl());
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String url = "";
            System.out.print("输入URL：");
            while ((url = reader.readLine()) != "EOF") {
                filter.filter(url);
                System.out.print("输入URL：");
            }
            
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filter(String url) {
        try {
            Document document = htmlParser.requestHTML(url, 30000);
            System.out.println("分词前：" + document.text());
            
            AnjsSplitWordsUtils splitWordsUtils = AnjsSplitWordsUtils.newInstance();
            System.out.println("分词后：" + splitWordsUtils.getAnalyzerWords(document.text()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
