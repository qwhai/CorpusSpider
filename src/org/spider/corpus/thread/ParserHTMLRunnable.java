package org.spider.corpus.thread;

import java.io.IOException;

import org.spider.corpus.consts.Constants;
import org.spider.corpus.utils.AnjsSplitWordsUtils;
import org.utils.naga.files.FileUtils;
import org.utils.naga.files.FileWriteUtils;
import org.utils.naga.web.HTMLParserUtils;

/**
 * <p>HTML解析工具</p>
 * <p>1.使用Jsoup解析HTML网页</p>
 * <p>2.使用Ansj进行文本分词</p>
 * <p>3.将分词结果写入文件</p>
 * 2015年12月7日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class ParserHTMLRunnable implements Runnable {

    private AnjsSplitWordsUtils mSplitWordUtils;
    private String mURL = null;
    private String mClassify = null; // 这个Classify与文件夹名称对应
    private String mFileName = null; // 这个FileName与URL对应
    
    public ParserHTMLRunnable(AnjsSplitWordsUtils splitWordUtils, String url, String classify, String fileName) {
        mSplitWordUtils = splitWordUtils;
        mURL = url;
        mClassify = classify;
        mFileName = fileName;
    }
    
    @Override
    public void run() {
        try {
            String text = HTMLParserUtils.requestHTMLText(mURL, 30000);
            writeWordToFile(text, mClassify, mFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("./" + mClassify + "/" + mFileName + "已经下载完成");
    }
    
    // 将解析的文本写入文件
    private void writeWordToFile(String text, String folderName, String fileName) throws IOException {
        String textWords = mSplitWordUtils.getAnalyzerWords(text);
        FileUtils.makeDirs(Constants.PARSER_PATH + folderName); // 创建文件夹，避免因没有文件夹出现异常
        FileWriteUtils.appendFile(Constants.PARSER_PATH + folderName + "/" + fileName, textWords);
    }
}
