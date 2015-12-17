package org.spider.corpus.filter;

import java.io.IOException;
import java.util.List;

import org.spider.corpus.consts.Config;
import org.utils.naga.files.FileReadUtils;
import org.utils.naga.filter.BloomFilter;
import org.utils.naga.str.StringUtils;

/**
 * <p>关键词过滤器</p>
 * <p>过滤一些中文黑名单和一些英文白名单</p>
 * 2015年12月3日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class KeywordFilter {

    private static KeywordFilter mKeywordFilter = null;
    private BloomFilter mBlacklistFilter = null;
    
    private KeywordFilter() {
        initEvent();
    }
    
    // 使用单例模式来实例化关键词过滤类
    public static KeywordFilter newInstance() {
        if (mKeywordFilter == null) {
            mKeywordFilter = new KeywordFilter();
        }
        
        return mKeywordFilter;
    }
    
    private void initEvent() {
        if (mBlacklistFilter == null) {
            mBlacklistFilter = new BloomFilter();
        }
        
        initBloomFilter(mBlacklistFilter, "./data/black_list");
    }
    
    /**
     * 去除分词器分出来的字词中包含的词性
     * 
     * @param label
     *          待过滤的字词
     * @return
     *          返回去除了词性的字词
     */
    public String removeSpeech(String label) {
        return label.split("/")[0];
    }
    
    /**
     * 格式一些特殊的字符串
     * 
     * @param label
     *          待格式化的字词
     * @return
     *          格式化后的字词
     */
    public String formatString(String label) {
        if (StringUtils.RegexUtils.isSub(label, "[0-9]*\\.*[0-9]+[集家岁千元幢万亿折度分期路场室厅卫厨届套]")) {
            StringBuffer buffer = new StringBuffer(label);
            buffer = new StringBuffer(buffer.toString().replaceAll("[0-9]*\\.*[0-9]+", "DD"));
            return buffer.toString();
        }
        
        if (StringUtils.RegexUtils.isSub(label, "[0-9]+[十百千万亿]*家")) {
            return "DD家";
        }
        
        if (StringUtils.RegexUtils.isSub(label, "[0-9]+厘米")) {
            return "DD厘米";
        }
        
        if (StringUtils.RegexUtils.isSub(label, "满[0-9]+")) {
            return "满DD";
        }
        
        if (StringUtils.RegexUtils.isSub(label, "[0-9]+平米")) {
            return "DD平米";
        }
        
        if (StringUtils.RegexUtils.isSub(label, "[0-9]+公里")) {
            return "DD公里";
        }
        
        return label;
    }
    
    public String formatLabel(String label) {
        if (StringUtils.isEmpty(label)) {
            return null;
        }
        
        String formatLabel = "";
        List<String> legalKeywords = Config.FilterConfig.getLagalEnglishKeywords();
//        List<String> keywordBlackList = Config.FilterConfig.getKeywordBlackList(); // TODO
        if (StringUtils.RegexUtils.hasChinese(label)) {
            // 是否在黑名单当中
            if (mBlacklistFilter.contains(label)) {
                return null;
            }
            
            if (StringUtils.RegexUtils.isSub(label, "[0-9]*\\.*[0-9]+[号年月日天]")) {
                return null;
            }
            
            // 添加格式操作
            formatLabel = formatString(label);
        } else {
            // 是否在白名单当中
            if (legalKeywords.contains(label.toLowerCase())) {
                formatLabel = label;
            } else {
                return null;
            }
        }
        
        return formatLabel;
    }
    
    // 初始化分词黑名单
    private void initBloomFilter(BloomFilter filter, String path) {
        try {
            List<String> words = FileReadUtils.readLines(path);
            for (String word : words) {
                filter.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
