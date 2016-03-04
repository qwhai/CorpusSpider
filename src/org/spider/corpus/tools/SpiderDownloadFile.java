package org.spider.corpus.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.spider.corpus.model.MalwareModel;
import org.utils.naga.str.StringUtils;
import org.utils.naga.web.impl.WebHTMLParserImpl;
import org.utils.naga.web.poke.HTMLParserUtils;

/**
 * <p>
 * 爬取https://malwareconfig.com/网页并下载yara文件
 * </p>
 * <p>
 * 短期任务，单线程处理即可
 * </p>
 * 2016年2月29日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/
 *      lemon_tree12138</a>
 * @version 0.1.1
 */
public class SpiderDownloadFile {

    private HTMLParserUtils parser = new HTMLParserUtils(new WebHTMLParserImpl());
    private static final String rootURL = "https://malwareconfig.com";
    private static final String ROOT_PATH = "F:/Temp/malware/";

    public static void main(String[] args) {
        SpiderDownloadFile spider = new SpiderDownloadFile();
        Set<String> set = spider.readAllMalwareSubURLs(rootURL);
        System.out.println("Malware解析完成，共采取到" + set.size() + "项Malware.");
        List<MalwareModel> models = spider.getURLYara(set);
        System.out.println("Yar文件地址解析完成.");
        try {
            spider.downloadYar(models);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * 解析并读取所有Malware网页下的yar文件所在URL
     * 组合后的访问URL格式如：https://malwareconfig.com/?page=2
     * 
     * @param baseURL
     *      基本URL
     * @return
     *      所有Malware主页
     */
    private Set<String> readAllMalwareSubURLs(String baseURL) {
        Set<String> allSet = new HashSet<>();
        for (int i = 1; i < 608; i++) {
            System.out.println("正在解析第" + i + "页...");
            allSet.addAll(readMalwareSubURLs(baseURL + "/?page=" + i));
        }
        
        return allSet;
    }
    
    /*
     * 获得Malware网页下的yar文件的所在URL
     * 
     * @param url
     *      Malware主页
     */
    private Set<String> readMalwareSubURLs(String url) {
        Set<String> urls = new HashSet<String>();
        try {
            Document document = parser.requestHTML(url, 30000);
            Elements elements = document.getElementsByTag("tbody");
            Element element = elements.get(0);
            Elements childElements = element.children();
            String subURL = "";
            for (int i = 1; i < childElements.size(); i++) {
                subURL = rootURL + childElements.get(i).child(1).child(0).attr("href");
                urls.add(subURL);
            }
        } catch (IOException e) {
            System.err.println("readMalwareSubURLs:" + url);
            e.printStackTrace();
        }

        return urls;
    }

    /*
     * 批量下载Yar文件
     * 
     * @param models
     */
    private void downloadYar(List<MalwareModel> models) throws IOException {
        if (models == null || models.size() == 0) {
            System.err.println("models为空");
            return;
        }

        for (MalwareModel malwareModel : models) {
            String folder = ROOT_PATH + malwareModel.getMalwareName() + "/";
            createFolder(folder);
            System.out.println("正在保存" + malwareModel.getMalwareName() + "所有版本的yar...");
            String fileName = extractName("");
            Set<String> urlSet = malwareModel.getUrlSet();
            for (String url : urlSet) {
                fileName = extractName(url) + ".yar";
                downloadYar(url, folder + fileName);
            }
        }
    }
    
    /*
     * 创建一个文件夹
     * 
     * @param folderName
     *      文件夹目录
     * @return
     *      是否创建成功
     */
    private boolean createFolder(String folderName) {
        if (folderName == null || folderName.isEmpty()) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }
    
    /*
     * 解析yar文件所在的单独界面HTML
     * 
     * @param yarURL
     *      单个yar所在URL
     * @param savePath
     *      文件保存路径
     */
    private void downloadYar(String yarURL, String savePath) {
        try {
            Document document = parser.requestHTML(yarURL, 30000);
            Elements elements = document.getElementsByAttributeValue("class", "panel-body");
            for (Element element : elements) {
                if (element.toString().contains("Click to Download ")) {
                    String hashURL = rootURL + element.child(0).attr("href");
                    downloadFile(hashURL, savePath);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 保存yar文件
     * 
     * @param yarURL
     *      yar文件所在网址
     * @param savePath
     *      保存目录
     * @throws IOException
     *      读写异常
     */
    private void downloadFile(String yarURL, String savePath) throws IOException {
        // 给定一条网络路径
        URL url = new URL(yarURL);
        HttpURLConnection huconn = (HttpURLConnection) url.openConnection(); // URL用openConnection()打开连接
        huconn.setRequestMethod("GET"); // 设置请求命令将被发送到远程HTTP服务器

        huconn.setConnectTimeout(5000); // 设置超时时间

        InputStream inStream = huconn.getInputStream(); // 通过输入流输入图片数据
        byte[] data = readBytesFileFromWeb(inStream); // 得到图片的二进制数据
        File image = new File(savePath);
        FileOutputStream outStream = new FileOutputStream(image);
        outStream.write(data);
        outStream.close();
    }

    /*
     * 从输入流中读取文件所在地址的字节数组
     * 
     * @param inStream
     *      输入流
     * @return
     *      字节数组
     * @throws IOException
     *      读写异常
     */
    private byte[] readBytesFileFromWeb(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /*
     * 获得每个Malware对应的所有Hash版本
     * 
     * @param urls
     *      Malware网页集
     */
    private List<MalwareModel> getURLYara(Set<String> urls) {
        if (urls == null || urls.size() == 0) {
            System.err.println("网页集合为空");
            return null;
        }

        List<MalwareModel> models = new ArrayList<>();
        for (String urlItem : urls) {
            Set<String> singleSet = getSingleURLYara(urlItem);

            MalwareModel model = new MalwareModel();
            model.setMalwareName(extractName(urlItem));
            model.setUrlSet(singleSet);
            models.add(model);
        }

        return models;
    }

    /*
     * 提取出URL中包含的Malware名称
     * 
     * @param url
     *      地址
     * @return
     *      Malware名称
     */
    private String extractName(String url) {
        String name = "";
        String[] splits = url.split("/");
        for (int i = splits.length - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(splits[i])) {
                continue;
            }
            name = splits[i];
            break;
        }
        return name;
    }

    /*
     * 获得某一个URL中存在的hash yara网页地址
     * 
     * @param url
     *      本网页地址
     * @return
     *      网页中存在的yar文件地址
     */
    private Set<String> getSingleURLYara(String url) {
        Set<String> urls = new HashSet<String>();
        try {
            Document document = parser.requestHTML(url, 30000);
            Elements elements = document.getElementsByAttributeValue("class",
                    "list-group");
            Element element = elements.get(0);
            elements = element.children();

            String hashURL = "";
            for (Element e : elements) {
                hashURL = rootURL + e.attr("href");
                urls.add(hashURL);
            }
        } catch (IOException e) {
            System.err.println("getSingleURLYara:" + url);
            e.printStackTrace();
        }

        return urls;
    }
}
