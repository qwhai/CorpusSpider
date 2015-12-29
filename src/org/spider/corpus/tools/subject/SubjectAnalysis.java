package org.spider.corpus.tools.subject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.spider.corpus.filter.KeywordFilter;
import org.utils.naga.containers.MapUtils;
import org.utils.naga.files.FileReadUtils;
import org.utils.naga.str.StringUtils;

/**
 * <p>
 * 主题分析
 * </p>
 * 2015年12月18日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class SubjectAnalysis {
    
    private KeywordFilter blackListFilter = KeywordFilter.newInstance();

    public static void main(String[] args) {
        SubjectAnalysis analysis = new SubjectAnalysis();
        
        try {
            List<String> segments = FileReadUtils.readLines("F:/Temp/subject.txt");
            analysis.convolution(segments, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 主题发现
     * 
     * @param segments
     *      所有讨论信息
     */
    public void analysis(List<String> segments) {
        if (segments == null || segments.size() == 0) {
            throw new NullPointerException("analysis > 请给出一个有效的输入信息");
        }
        
        KeyWordComputer computer = new KeyWordComputer(20); // 关键词计算器
        Map<String, Integer> keywordMap = new HashMap<>();
        for (String segment : segments) {
            System.out.println("原文：" + segment);
            
            System.out.println("分词：" + SubjectTools.filterSlightNature(SubjectTools.filterBlackList(segment, blackListFilter)));
            
            Collection<Keyword> result = computer.computeArticleTfidf(SubjectTools.filterSlightNature(segment), ""); // 关键词计算结果
            for (Keyword keyword : result) {
                if (blackListFilter.contains(keyword.getName())) {
                    continue;
                }
                
                if (keywordMap.get(keyword.getName()) == null) {
                    keywordMap.put(keyword.getName(), 1);
                } else {
                    keywordMap.put(keyword.getName(), 1 + keywordMap.get(keyword.getName()));
                }
            }
        }
        
        List<Map.Entry<String, Integer>> sortedMap = MapUtils.sortIntegerMap(keywordMap);
        System.out.println(sortedMap);
    }
    
    /**
     * 卷积计算
     * 
     * @param segments
     *      原句子
     * @param groupLength
     *      每个卷积中有多少个句子
     */
    public void convolution(List<String> segments, int groupLength) {
        if (segments == null || segments.size() == 0) {
            throw new NullPointerException("convolution > 请给出一个有效的输入信息");
        }
        
        int segmentSize = segments.size();
        
        // 使每个卷积分组中的大小合理化
        if (groupLength > segmentSize) {
            groupLength = segmentSize;
        }
        
        List<List<Term>> clearTerms = SubjectTools.clearTerms(segments, blackListFilter);
        
        {
            Map<String, Integer> map = new HashMap<>();
            for (List<Term> list : clearTerms) {
                for (Term term : list) {
                    if (StringUtils.isEmpty(term.getName()) || StringUtils.isEmpty(term.getName().trim())) {
                        continue;
                    }
                    
                    if (map.containsKey(term.getName())) {
                        map.put(term.getName(), map.get(term.getName()) + 1);
                    } else {
                        map.put(term.getName(), 1);
                    }
                }
            }
            List<Entry<String, Integer>> sortedMap = MapUtils.sortIntegerMap(map);
            System.out.println(sortedMap);
        }
        
//        for (int i = groupLength - 1; i < segmentSize; i++) {
//            features(part(clearTerms, i - (groupLength - 1), i + 1));
//        }
    }
    
    /*
     * 提取出一个List中的一部分数据
     * 
     * @param segments
     *      待提取的List
     * @param startIndex
     *      开始的List下标
     * @param endIndex
     *      结束的List下标
     * @return
     *      部分数据
     */
    @SuppressWarnings("unused")
    private List<List<Term>> part(List<List<Term>> terms, int startIndex, int endIndex) {
        List<List<Term>> partTerms = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            partTerms.add(terms.get(i));
        }
        
        return partTerms;
    }
    
    /*
     * 计算几个句子之间的相似关键词
     * 
     * @param terms
     *      待计算的句子列表
     * @return
     *      相似关键词集合
     */
    @SuppressWarnings("unused")
    private List<String> features(List<List<Term>> terms) {
        if (terms == null || terms.size() == 0) {
            throw new NullPointerException("features > 请给出一个有效的输入信息");
        }
        
        List<String> features = new ArrayList<>(); // 结果特征
        Map<String, Integer> map = new HashMap<>();
        for (List<Term> termList : terms) {
            if (termList.size() == 0) {
                continue;
            }
            
//            termList = SubjectTools.filterSlightNature(termList);
            for (Term term : termList) {
                if (StringUtils.isEmpty(term.getName()) || StringUtils.isEmpty(term.getName().trim())) {
                    continue;
                }
                
                if (map.containsKey(term.getName())) {
                    map.put(term.getName(), map.get(term.getName()) + 1);
                } else {
                    map.put(term.getName(), 1);
                }
            }
        }
        
        List<Entry<String, Integer>> sortedMap = MapUtils.sortIntegerMap(map);
        System.out.println(sortedMap);
        
        return features;
    }
    
    /**
     * 针对单个句子的关键信息提取
     * 
     * @param segments
     *      各个句子
     */
    public void segmentSubject(List<String> segments) {
        if (segments == null || segments.size() == 0) {
            throw new NullPointerException("segmentSubject > 请给出一个有效的输入信息");
        }
        
        for (String segment : segments) {
            System.out.println("原文：" + segment);
            
            List<Term> parse = NlpAnalysis.parse(segment);
            parse = SubjectTools.filterDuplicate(parse);
            parse = SubjectTools.filterSlight(parse);
            parse = SubjectTools.filterGranularity(parse);
            
            System.out.println("过滤后：" + SubjectTools.listToString(parse));
        }
    }
}
