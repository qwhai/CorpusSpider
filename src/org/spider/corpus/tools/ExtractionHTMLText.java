package org.spider.corpus.tools;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.spider.corpus.consts.Constants;
import org.spider.corpus.thread.ParserHTMLRunnable;
import org.spider.corpus.thread.SpiderThreadPool;
import org.spider.corpus.utils.AnjsSplitWordsUtils;
import org.utils.naga.excep.FileNameNotExistsException;
import org.utils.naga.files.FileReadUtils;
import org.utils.naga.files.FileSearchUtils;
import org.utils.naga.files.FileUtils;
import org.utils.naga.str.StringUtils;
import org.utils.naga.threads.ThreadUtils;

/**
 * 多线程解析URL
 * <p>1.提取出HTML中的可见文本</p>
 * <p>2.分词</p>
 * <p>3.存入文本</p>
 * 
 * 2015‎年‎12‎月‎3‎日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.ne7t/lemon_tree12138</a>
 * @version 0.1
 */
public class ExtractionHTMLText {

    private AnjsSplitWordsUtils splitWordUtils = AnjsSplitWordsUtils.newInstance();
    private SpiderThreadPool mPool = null;
    
    public static void main(String[] args) {
        try {
            ExtractionHTMLText entry = new ExtractionHTMLText();
            entry.initThreadPool();
            entry.parserURL();
        } catch (NotDirectoryException | FileNameNotExistsException e) {
            e.printStackTrace();
        }
    }
    
    private void initThreadPool() {
        mPool = SpiderThreadPool.newInstance().create();
    }
    
    private void parserURL() throws NotDirectoryException, FileNameNotExistsException {
        String[] fileNames = FileSearchUtils.getAllFileName(Constants.RAW_PATH);
        for (String fileName : fileNames) {
            try {
                AtomicInteger urlIndex = new AtomicInteger(0);
                List<String> urList = FileReadUtils.readLines(Constants.RAW_PATH + fileName);
                for (String url : urList) {
                    mPool.submit(new ParserHTMLRunnable(splitWordUtils, url, FileUtils.removeSuffixName(fileName), StringUtils.formatIntegerString(urlIndex.get(), "#00000")), 3000);
                    urlIndex.incrementAndGet();
                    
                    ThreadUtils.sleep(50);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            System.out.println("" + fileName);
        }
    }
}
