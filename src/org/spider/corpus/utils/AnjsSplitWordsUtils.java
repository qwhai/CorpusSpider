package org.spider.corpus.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.spider.corpus.filter.KeywordFilter;
import org.utils.naga.str.StringUtils;

/**
 * 
 * <p>工具类</p>
 * <p>使用Anjs分词器进行分词的分词工具</p>
 * 2015年12月3日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class AnjsSplitWordsUtils implements WordsSplitUtils {

    private static AnjsSplitWordsUtils mSplitWordsUtils = null;
    private KeywordFilter mKeywordFilter = null;
    
    private AnjsSplitWordsUtils() {
        initEvent();
    }
    
    // 使用单例模式来实例化分词工具类
    public static AnjsSplitWordsUtils newInstance() {
        if (mSplitWordsUtils == null) {
            mSplitWordsUtils = new AnjsSplitWordsUtils();
        }
        
        return mSplitWordsUtils;
    }
    
    private void initEvent() {
        if (mKeywordFilter == null) {
            mKeywordFilter = KeywordFilter.newInstance();
        }
    }
    
    // 使用Anjs的精确分词对文本进行分词
    private ArrayList<String> realySplit(String strbuf) {
        List<Term> parse = ToAnalysis.parse(strbuf);
        ArrayList<String> words = new ArrayList<>();
        for (Term term : parse) {
            if (StringUtils.RegexUtils.hasChinese(term.toString())) {
                words.add(term.getName());
            }
        }
        
        return words;
    }

    @Override
    public ArrayList<String> getAnalyzerWordList(String text)
            throws IOException {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        
        ArrayList<String> resultList = new ArrayList<String>();
        
        List<String> words = realySplit(text);
        if (words == null || words.size() == 0) {
            return null;
        }
        
        int wordSize = words.size();
        for (int i = 0; i < wordSize; i++) {
            // 添加过滤器
            String label = mKeywordFilter.formatLabel(words.get(i));
            if (label == null) {
                continue;
            }
            
            resultList.add(label);
        }
        
        return resultList;
    }

    @Override
    public String getAnalyzerWords(String text) throws IOException {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        
        List<String> words = realySplit(text);
        if (words == null || words.size() == 0) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        int wordSize = words.size();
        boolean firstFlag = true;
        for (int i = 0; i < wordSize; i++) {
            // 添加过滤器
            String label = mKeywordFilter.formatLabel(words.get(i));
            if (StringUtils.isEmpty(label)) {
                continue;
            }
            
            buffer.append((firstFlag ? "" : " ") + label);
            
            if (firstFlag) {
                firstFlag = false;
            }
        }
        
        return buffer.toString();
    }
}
