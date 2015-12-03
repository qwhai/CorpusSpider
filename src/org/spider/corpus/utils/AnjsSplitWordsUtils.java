package org.spider.corpus.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.spider.corpus.filter.KeywordFilter;
import org.utils.naga.str.StringUtils;

/**
 * 工具类
 * 使用Anjs分词器进行分词的分词工具
 * 
 * @author Naga
 * Blog : http://blog.csdn.net/lemon_tree12138
 */
public class AnjsSplitWordsUtils implements WordsSplitUtils {

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
            String label = KeywordFilter.formatLabel(words.get(i));
            if (label == null) {
                continue;
            }
            
            resultList.add(label);
        }
        
        return resultList;
    }
}
