package org.spider.corpus.tools.subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.spider.corpus.filter.KeywordFilter;
import org.utils.naga.consts.ComparativeSize;
import org.utils.naga.str.StringUtils;

public class SubjectTools {

    /**
     * 过滤分词列表中具有重复词性的词语
     * 
     * @param parse
     *      分词列表
     * @return
     *      过滤后的分词列表
     */
    public static List<Term> filterDuplicate(List<Term> parse) {
        if (parse == null) {
            throw new NullPointerException("filterDuplicate > 参数为空");
        }
        
        int listSize = parse.size();
        
        if (listSize < 2) {
            return parse;
        }
        
        List<Term> tmpTerm = new ArrayList<>();
        Term lastTerm = parse.get(listSize - 1);
        tmpTerm.add(lastTerm);
        for (int i = listSize - 2; i >= 0; i--) {
            if (similarNatureTerm(parse.get(i), lastTerm)) {
                continue;
            }
            
            lastTerm = parse.get(i);
            tmpTerm.add(lastTerm);
        }
        
        List<Term> result = new ArrayList<>();
        int tmpSize = tmpTerm.size();
        for (int i = tmpSize - 1; i >= 0; i--) {
            result.add(tmpTerm.get(i));
        }
        
        return result;
    }
    
    /**
     * 过滤掉一些不重要词性的词语
     * 
     * @param parse
     *      分词列表
     * @return
     *      过滤后的分词列表
     */
    public static List<Term> filterSlight(List<Term> parse) {
        if (parse == null) {
            throw new NullPointerException("filterSlight > 参数为空");
        }
        
        List<Term> result = new ArrayList<>();
        
        for (Term term : parse) {
            String natureStr = term.getNatureStr();
            if (StringUtils.isEmpty(natureStr) || StringUtils.isEmpty(natureStr.trim())) {
                continue;
            }
            
            natureStr = natureStr.substring(0, 1);
            if (StringUtils.RegexUtils.isSub(natureStr, "^[iltsfabzrmqdpcueyohkx]$")) {
                continue;
            }
            result.add(term);
        }
        
        return result;
    }
    
    /**
     * 粒度过滤器
     * 过滤掉一些邻近的更小粒度词性的词语
     * 比如：A/n B/nz 这里就把B过滤掉
     * 
     * @param parse
     * @return
     */
    public static List<Term> filterGranularity(List<Term> parse) {
        if (parse == null) {
            throw new NullPointerException("filterGranularity > 参数为空");
        }
        
        int length = parse.size();
        boolean[] removeFlag = new boolean[length];
        int max = 0;
        int curr = 0;
        while(max < length) {
            if (sameNatureTerm(parse.get(max), parse.get(curr))) {
                ComparativeSize compare = compareToGranularity(parse.get(curr), parse.get(max));
                
                switch (compare) {
                case Bigger:
                    removeFlag[max] = true;
                    max = curr;
                    break;
                case Equal:
                    break;
                case Small:
                    removeFlag[curr] = true;
                    break;
                default:
                    throw new AssertionError();
                }
            } else {
                max = curr;
            }
            
            if (++curr >= length) {
                break;
            }
        }
        
        List<Term> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (!removeFlag[i]) {
                result.add(parse.get(i));
            }
        }
        
        return result;
    }
    
    
    
    // 
    @SuppressWarnings("unused")
    private static char[] natureFisrtChar(List<Term> parse, int size) {
        char[] natures = new char[size];
        for (int i = 0; i < size; i++) {
            natures[i] = parse.get(i).getNatureStr().charAt(0);
        }
        
        return natures;
    }
    
    /**
     * 将一句话分词并去除无关的词性
     * 
     * @param segment
     *      句子
     * @return
     *      筛选之后的分词列表
     */
    public static List<Term> splitScreenSegment(String segment) {
        if (StringUtils.isEmpty(segment)) {
            throw new NullPointerException("splitScreenSegment > 请给出一个有效的输入信息");
        }
        
        List<Term> result = new ArrayList<>();
        List<Term> parse = NlpAnalysis.parse(segment);
        for (Term term : parse) {
            String natureStr = term.getNatureStr();
            if (StringUtils.isEmpty(natureStr) || StringUtils.isEmpty(natureStr.trim())) {
                continue;
            }
            
            natureStr = natureStr.substring(0, 1);
            if (StringUtils.RegexUtils.isSub(natureStr, "^[iltsfabzrmqdpcueyohkxw]$")) {
                continue;
            }
            
            result.add(term);
        }
        
        return result;
    }
    
    /**
     * 过滤掉原句子中的无关词性的词语
     * 
     * @param parse
     *      原数据
     * @return
     *      筛选之后的分词列表
     */
    public static List<Term> filterSlightNature(List<Term> parse) {
        if (parse == null || parse.size() == 0) {
            throw new NullPointerException("filterSlightNature > 请给出一个有效的输入信息" + parse);
        }
        
        List<Term> result = new ArrayList<>();
        for (Term term : parse) {
            String natureStr = term.getNatureStr();
            if (StringUtils.isEmpty(natureStr) || StringUtils.isEmpty(natureStr.trim())) {
                continue;
            }
            
            natureStr = natureStr.substring(0, 1);
            if (StringUtils.RegexUtils.isSub(natureStr, "^[iltsfabzrmqdpcueyohkxw]$")) {
                continue;
            }
            
            result.add(term);
        }
        
        return result;
    }
    
    /**
     * 过滤掉原句子中的无关词性的词语
     * 
     * @param segment
     *      原句子
     * @return
     *      过滤后的句子
     */
    public static String filterSlightNature(String segment) {
        List<Term> terms = splitScreenSegment(segment);
        StringBuffer buffer = new StringBuffer();
        for (Term term : terms) {
            buffer.append(term.getName());
        }
        return buffer.toString();
    }
    
    /**
     * 过滤掉黑名单中存在的词语
     * 
     * @param segment
     *      原句
     * @param blacklist
     *      黑名单
     * @return
     *      过滤后的分词列表
     */
    public static List<Term> filterBlackList(String segment, KeywordFilter blackListFilter) {
        List<Term> parse = NlpAnalysis.parse(segment);
        List<Term> result = new ArrayList<>();
        for (Term term : parse) {
            if (blackListFilter.contains(term.getName())) {
                continue;
            }
            
            result.add(term);
        }
        
        return result;
    }
    
    /**
     * 将一句话分词并封装成Subject对象
     * 
     * @param segment
     *      句子
     * @return
     *      主题对象
     */
    public static Subject splitWord(String segment) {
        List<Term> parse = NlpAnalysis.parse(segment);
        Subject subject = new Subject();
        
        for (Term term : parse) {
            String name = term.getName();
            if (StringUtils.isEmpty(name) || name.equals(" ")) {
                continue;
            }
            
            if (term.getNatureStr().startsWith("n") || term.getNatureStr().equals("j")) {
                if (subject.getPredicate() == null || subject.getPredicate().size() == 0) {
                    subject.addSubject(name);
                } else {
                    subject.addObject(name);
                }
            } else if (term.getNatureStr().startsWith("v")) {
                subject.addPredicate(name);
            }
        }
        
        return subject;
    }
    
    /**
     * 获得一句话的关键词，并封装到一个Map中
     * 
     * @param computer
     *      关键词计算器
     * @param segment
     *      句子
     * @return
     *      关键词Map
     */
    public static Map<String, Double> buildKeywordMap(KeyWordComputer computer, String segment) {
        Collection<Keyword> result = computer.computeArticleTfidf(segment, "");
        Map<String, Double> keywordMap = new HashMap<>();
        
        for (Keyword keyword : result) {
            if (StringUtils.isEmpty(keyword.getName())) {
                continue;
            }
            keywordMap.put(keyword.getName(), keyword.getScore());
        }
        
        return keywordMap;
    }
    
    /**
     * 对确定的Subject(主题)对象进行定位
     * 
     * @param keywordMap
     *      关键词Map
     * @param subject
     *      Subject对象
     */
    public static void locationSubject(Map<String, Double> keywordMap, Subject subject) {
        List<List<String>> subjectMembers = subject.getMembers();
        for (List<String> member : subjectMembers) {
            if (member == null) {
                continue;
            }
            
            String label = "";
            double maxScore = Double.MIN_VALUE;
            for (String word : member) {
                Double score = keywordMap.get(word);
                if (score == null) {
                    continue;
                }
                
                if (score > maxScore) {
                    maxScore = score;
                    label = word;
                }
            }
            
            member.clear();
            member.add(label);
        }
    }
    
    /**
     * 将一个分词列表转化成一个字符串
     * 
     * @param parse
     *      分词列表
     * @return
     *      字符串
     */
    public static String listToString(List<Term> parse) {
        if (parse == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (Term term : parse) {
            buffer.append(term.getName());
        }
        
        return buffer.toString();
    }
    
    /**
     * 获得原始句子过滤后的分词列表
     * 
     * @param segments
     *      原始句子
     * @param filter
     *      黑名单过滤器
     * @return
     *      分词列表
     */
    public static List<List<Term>> clearTerms(List<String> segments, KeywordFilter filter) {
        if (segments == null || segments.size() == 0) {
            return null;
        }
        
        List<List<Term>> terms = new ArrayList<>();
        for (String segment : segments) {
            terms.add(filterSlight(filterBlackList(segment, filter)));
        }
        
        return terms;
    }
    
    /*
     * 判断两个Term是不是有相近的词性
     * 
     * @param term1
     *      词语
     * @param term2
     *      词语
     * @return
     *      词性是否相近
     */
    private static boolean similarNatureTerm(Term term1, Term term2) {
        String nature1 = term1.getNatureStr().substring(0, 1);
        String nature2 = term2.getNatureStr().substring(0, 1);
        
        nature1 = nature1.equals("j") ? "n" : nature1;
        nature2 = nature2.equals("j") ? "n" : nature2;
        
        return nature1.equals(nature2);
    }
    
    // 
    private static boolean sameNatureTerm(Term term1, Term term2) {
        String nature1 = term1.getNatureStr().substring(0, 1);
        String nature2 = term2.getNatureStr().substring(0, 1);
        
        return nature1.equals(nature2);
    }
    
    /*
     * 比较两个词语的词性粒度
     * 
     * @param term1
     *      词语
     * @param term2
     *      词语
     * @return
     *      大小结果
     */
    private static ComparativeSize compareToGranularity(Term term1, Term term2) {
        String nature1 = term1.getNatureStr();
        String nature2 = term2.getNatureStr();
        
        if (nature1.length() == nature2.length()) {
            return ComparativeSize.Equal;
        } else if (nature1.length() < nature2.length()) {
            return ComparativeSize.Bigger;
        }
        
        return ComparativeSize.Small;
    }
}
