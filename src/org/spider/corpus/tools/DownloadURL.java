package org.spider.corpus.tools;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spider.corpus.queue.SpiderQueue;
import org.utils.naga.files.FileWriteUtils;
import org.utils.naga.filter.BloomFilter;
import org.utils.naga.web.HTMLParserUtils;

/**
 * 按照一定的协议将网页地址下载到本地
 * 
 * @author Naga
 * Blog : http://blog.csdn.net/lemon_tree12138
 */
public class DownloadURL {

    static final String BASE_ADDRESS = "http://domestic.firefox.163.com";
    private static BloomFilter mBloomFilter = null;
    private static final String BASE_URL = "http://domestic.firefox.163.com/roll_";
    private static final String BASE_PATH = "E:/workspace/src/Java/Bigdata/Classify/URL/naive_bayes_classifier_data/raw_html_set";
    private static SpiderQueue mSpiderQueue = null;
    private static final String SUB_PATH = BASE_PATH + "/其他分类/云计算.txt";
    
    static {
        mBloomFilter = new BloomFilter();
        mSpiderQueue = new SpiderQueue();
    }
    
    public static void main(String[] args) {
        String url = "";
        for (int i = 1; i <= 1; i++) {
            url = BASE_URL + i + ".html";
            addFirefoxHTMLElements(url);
        }
        
//        downloadHTMLs(SUB_PATH);
    }
    
    // 数盟 http://dataunion.org
    @SuppressWarnings("unused")
    private static void addDataunionHTMLElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "excerpt");
            for (Element element : titlelnkElements) {
                mSpiderQueue.offer(element.child(0).child(0).child(0).attr("href"));
                System.out.println(element.child(0).text());
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    // 博客园 http://www.cnblogs.com/
    @SuppressWarnings("unused")
    private static void addCnblogsHTMLElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "titlelnk");
            for (Element element : titlelnkElements) {
                mSpiderQueue.offer(element.attr("href"));
                System.out.println(element.text());
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    // 火狐新闻
    private static void addFirefoxHTMLElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "list_box");
            for (Element element : titlelnkElements) {
                mSpiderQueue.offer(BASE_ADDRESS + element.attr("href"));
                System.out.println(element.child(0).text());
                System.out.println(BASE_ADDRESS + element.attr("href"));
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    private static void downloadHTMLs(String downloadPath) {
        String currentURL = "";
        StringBuffer buffer = new StringBuffer();
        while(!mSpiderQueue.isQueueEmpty()) {
            currentURL = mSpiderQueue.poll();
            if (!mBloomFilter.contains(currentURL)) {
                buffer.append(currentURL + "\n");
                mBloomFilter.add(currentURL);
            }
            
            if (buffer.length() >= 1000) {
                writeURLs(downloadPath, buffer.toString());
                buffer.setLength(0);
            }
        }
        
        writeURLs(downloadPath, buffer.toString());
    }
    
    private static void writeURLs(String path, String content) {
        try {
            FileWriteUtils.write2File(path, content);
        } catch (IOException e) {
            System.err.println("downloadHTML:" + content);
            e.printStackTrace();
        }
    }
}
