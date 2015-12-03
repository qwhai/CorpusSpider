package org.spider.corpus.utils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 分词工具类接口
 * 所有分词器的接口对象
 * 
 * @author Naga
 * Blog : http://blog.csdn.net/lemon_tree12138
 */
public interface WordsSplitUtils {

    public ArrayList<String> getAnalyzerWordList(String text) throws IOException;
}
