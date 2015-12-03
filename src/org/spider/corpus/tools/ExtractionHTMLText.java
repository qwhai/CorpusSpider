package org.spider.corpus.tools;

import java.io.IOException;
import java.util.List;

import org.spider.corpus.utils.AnjsSplitWordsUtils;
import org.utils.naga.files.FileReadUtils;
import org.utils.naga.files.FileUtils;
import org.utils.naga.files.FileWriteUtils;
import org.utils.naga.str.StringUtils;
import org.utils.naga.web.HTMLParserUtils;

/**
 * 1.提取出HTML中的可见文本
 * 2.分词
 * 3.存入文本
 * 
 * 2015‎年‎12‎月‎3‎日
 * 
 * @author Q-WHai
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class ExtractionHTMLText {

    private static final String RAW_PATH = "E:/workspace/src/Java/Bigdata/Classify/URL/naive_bayes_classifier_data/raw_html_set/";
    private static final String PARSER_PATH = "E:/workspace/src/Java/Bigdata/Classify/URL/naive_bayes_classifier_data/parser_html_set/";
    
    public static void main(String[] args) {
        AnjsSplitWordsUtils utils = new AnjsSplitWordsUtils();
        
        try {
            List<String> lines = FileReadUtils.readLines(RAW_PATH + "Android.txt");
            
            for (String line : lines) {
                String text = HTMLParserUtils.requestHTMLText(line, 30000);
                if (StringUtils.isEmpty(text)) {
                    continue;
                }
                
                FileWriteUtils.appendFile(PARSER_PATH + "Android.txt", text);
                
                List<String> textWords = utils.getAnalyzerWordList(text);
                FileWriteUtils.appendFile(PARSER_PATH + FileUtils.removeSuffixName("Android.txt"), textWords.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
