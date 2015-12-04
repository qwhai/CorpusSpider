package org.spider.corpus.tools;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spider.corpus.queue.SpiderQueue;
import org.utils.naga.files.FileWriteUtils;
import org.utils.naga.filter.BloomFilter;
import org.utils.naga.web.HTMLParserUtils;

/**
 * 按照一定的协议将网页地址下载到本地
 * 2015‎年‎11‎月‎26‎日
 * 
 * @author Q-WHai
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class DownloadURL {

    static final String BASE_ADDRESS = "http://history.sina.com.cn";
    private static BloomFilter mBloomFilter = null;
    private static final String BASE_URL = BASE_ADDRESS + "/";
    private static final String BASE_PATH = "E:/workspace/src/Java/Bigdata/Classify/URL/naive_bayes_classifier_data/raw_html_set";
    private static SpiderQueue mSpiderQueue = null;
    private static final String SUB_PATH = BASE_PATH + "/历史资料.txt";
    
    static AtomicInteger urlCount = new AtomicInteger(0);
    
    static {
        mBloomFilter = new BloomFilter();
        mSpiderQueue = new SpiderQueue();
    }
    
    public static void main(String[] args) {
        String url = "";
        for (int i = 1; i <= 1; i++) {
            url = BASE_URL;
            addSinaHistoryHTMLElements(url);
            downloadHTMLs(SUB_PATH);
        }
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
    @SuppressWarnings("unused")
    private static void addFirefoxHTMLElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "list_box");
            for (Element element : titlelnkElements) {
                mSpiderQueue.offer(BASE_ADDRESS + element.attr("href"));
                System.out.println(element.child(0).text());
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    // 新浪天气(省)
    @SuppressWarnings("unused")
    private static void addWeatherSinaHTMLElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "wd_piC");
            for (Element element : titlelnkElements) {
                for (Element childElement : element.children()) {
                    addWeatherSinaSubElements(childElement.attr("href"));
                }
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    // 新浪天气(市)
    private static void addWeatherSinaSubElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "wd_cm_table");
            for (Element element : titlelnkElements) {
                for (Element childElement : element.children()) {
                    Element tmpElement = childElement.child(0).child(0).child(0);
                    mSpiderQueue.offer(tmpElement.attr("href"));
                    System.out.println(tmpElement.attr("href"));
                }
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    // 新浪房产
    @SuppressWarnings("unused")
    private static void addSinaHouseHTMLElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "grid-s5m0e5");
            for (Element element : titlelnkElements) {
                urlCount.incrementAndGet();
                Element tmpElement = element.child(0).child(0).child(0).child(0).child(0);
                mSpiderQueue.offer(tmpElement.attr("href"));
                System.out.println("[" + urlCount.get() + "]" + tmpElement.attr("href"));
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    // 新浪中医
    @SuppressWarnings("unused")
    private static void addSinaChineseMedicineHTMLElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "artList");
            Elements childrenElements = titlelnkElements.get(0).children();
            for (Element element : childrenElements) {
                urlCount.incrementAndGet();
                Element tmpElement = element.child(0).child(0);
                mSpiderQueue.offer(BASE_ADDRESS + tmpElement.attr("href"));
                System.out.println("[" + urlCount.get() + "]" + BASE_ADDRESS + tmpElement.attr("href"));
                System.out.println(tmpElement.text());
            }
        } catch (IOException e) {
            System.err.println("addElements:" + url);
            e.printStackTrace();
        }
    }
    
    // TODO 新浪历史
    private static void addSinaHistoryHTMLElements(String url) {
        try {
            Document document = HTMLParserUtils.requestHTML(url, 30000);
            Elements titlelnkElements = document.getElementsByAttributeValue("class", "news-item  img-news-item");
            for (Element element : titlelnkElements) {
                urlCount.incrementAndGet();
                Element tmpElement = element.child(0).child(0);
                mSpiderQueue.offer(tmpElement.attr("href"));
                System.out.println("[" + urlCount.get() + "]" + tmpElement.attr("href"));
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
            } else {
                System.err.println("----------------- URL已存在 --------------------");
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
            FileWriteUtils.appendFile(path, content);
        } catch (IOException e) {
            System.err.println("downloadHTML:" + content);
            e.printStackTrace();
        }
    }
}
