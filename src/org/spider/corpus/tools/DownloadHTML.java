package org.spider.corpus.tools;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spider.corpus.queue.SpiderQueue;
import org.spider.corpus.utils.StringFormatUtils;
import org.utils.naga.files.FileWriteUtils;
import org.utils.naga.filter.BloomFilter;
import org.utils.naga.web.HTMLParserUtils;

/**
 * 按照一定的协议将网页下载到本地
 * 
 * @author Naga
 * Blog : http://blog.csdn.net/lemon_tree12138
 */
@Deprecated
public class DownloadHTML {

    private static BloomFilter mBloomFilter = null;
    private static final String BASE_URL = "http://dataunion.org/category/article/tech/%E5%88%86%E6%9E%90%E6%8C%96%E6%8E%98/page/";
    private static final String BASE_PATH = "E:/workspace/src/Java/Bigdata/Classify/URL/naive_bayes_classifier_data/raw_html_set";
    private static SpiderQueue mSpiderQueue = null;
    private static final String SUB_PATH = BASE_PATH + "/数据挖掘";
    
    static {
        mBloomFilter = new BloomFilter();
        mSpiderQueue = new SpiderQueue();
    }
    
    public static void main(String[] args) {
        String url = "";
        for (int i = 1; i <= 26; i++) {
            url = BASE_URL + i;
            addElements(url);
            downloadHTMLs(SUB_PATH);
            System.out.println("[" + i + "] " + url);
        }
    }
    
    private static void addElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "excerpt");
            for (Element element : titlelnkElements) {
                mSpiderQueue.offer(element.child(0).child(0).child(0).attr("href"));
                
                System.out.println(element.child(0).child(0).child(0).attr("href"));
                System.out.println(element.child(0).text());
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    private static void downloadHTMLs(String downloadPath) {
        String currentURL = "";
        while(!mSpiderQueue.isQueueEmpty()) {
            currentURL = mSpiderQueue.poll();
            if (!mBloomFilter.contains(currentURL)) {
                downloadHTML(downloadPath + "/" + StringFormatUtils.formatURL(currentURL) + ".html", currentURL);
                mBloomFilter.add(currentURL);
            }
        }
    }
    
    private static void downloadHTML(String path, String url) {
        try {
            String htmlContent = HTMLParserUtils.requestHTMLToString(url);
            FileWriteUtils.write2File(path, htmlContent);
        } catch (IOException e) {
            System.err.println("downloadHTML:" + url);
            e.printStackTrace();
        }
    }
}
