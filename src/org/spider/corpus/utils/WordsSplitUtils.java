package org.spider.corpus.utils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>分词工具类接口</p>
 * <p>所有分词器的接口对象</p>
 * 2015年12月3日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public interface WordsSplitUtils {

    public ArrayList<String> getAnalyzerWordList(String text) throws IOException;
    
    public String getAnalyzerWords(String text) throws IOException;
}
