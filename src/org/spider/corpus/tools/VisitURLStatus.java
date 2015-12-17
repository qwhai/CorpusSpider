package org.spider.corpus.tools;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Connection.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.utils.naga.str.StringUtils;

/**
 * <p>
 * 网页访问状态
 * </p>
 * 2015年12月16日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class VisitURLStatus {

    public static void main(String[] args) {
        String url = "http://item.jd.com/0878181836.html";
        VisitURLStatus status = new VisitURLStatus();
        status.printStatus(url);
        status.redirect(url);
    }
    
    private void printStatus(String url) {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        
        try {
            // 网页访问状态
            int statusCode = httpClient.executeMethod(getMethod);
            System.out.println(statusCode);
            
            // 读取为字节数组
//            byte[] responseBody = getMethod.getResponseBody();
//            System.out.println(new String(responseBody, "GBK"));
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void redirect(String url) {
        try {
            //true = 跟随redirects跳转 ； false = 不跟随跳转
            Document doc = Jsoup.connect(url).timeout(120000).followRedirects(false).execute().parse();
            System.out.println(doc.baseUri());
            System.out.println(doc);
            
            System.out.println("---------------------------------------------------------------------------------");
            
            Document doc2 = Jsoup.connect(url).timeout(120000).followRedirects(false).get();
            System.out.println(doc2.baseUri());
            System.out.println(doc2);
            
            System.out.println("---------------------------------------------------------------------------------");
            
            Document doc3 = Jsoup.connect(url).timeout(120000).followRedirects(false).post();
            System.out.println(doc3.baseUri());
            System.out.println(doc3);
            
            System.out.println("---------------------------------------------------------------------------------");
            
            System.out.println(StringUtils.RegexUtils.isSub(doc3.toString(), "<title>302 Found</title>"));
            
            System.out.println("---------------------------------------------------------------------------------");
            
            Request request = Jsoup.connect(url).request().followRedirects(true);
            System.out.println(request.url());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
