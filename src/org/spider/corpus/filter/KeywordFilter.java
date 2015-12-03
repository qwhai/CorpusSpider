package org.spider.corpus.filter;

import java.util.List;

import org.spider.corpus.consts.Config;
import org.utils.naga.str.StringUtils;

/**
 * 关键词过滤器
 * 过滤一些中文黑名单和一些英文白名单
 * 
 * @author Naga
 * Blog : http://blog.csdn.net/lemon_tree12138
 */
public class KeywordFilter {

    /**
     * 去除分词器分出来的字词中包含的词性
     * 
     * @param label
     *          待过滤的字词
     * @return
     *          返回去除了词性的字词
     */
    public static String removeSpeech(String label) {
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
    public static String formatString(String label) {
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
    
    public static String formatLabel(String label) {
        if (StringUtils.isEmpty(label)) {
            return null;
        }
        
        String formatLabel = "";
        List<String> legalKeywords = Config.FilterConfig.getLagalEnglishKeywords();
        List<String> keywordBlackList = Config.FilterConfig.getKeywordBlackList();
        if (StringUtils.RegexUtils.hasChinese(label)) {
            // 是否在黑名单当中
            if (keywordBlackList.contains(label)) {
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
}
