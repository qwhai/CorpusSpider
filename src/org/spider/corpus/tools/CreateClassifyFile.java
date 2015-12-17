package org.spider.corpus.tools;

import java.io.IOException;

import org.spider.corpus.consts.Classify;
import org.spider.corpus.consts.Constants;
import org.utils.naga.files.FileUtils;

/**
 * <p>
 * 将Classify枚举中的所有枚举类型都创建一个与之对应的文件
 * </p>
 * 2015年12月8日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class CreateClassifyFile {

    public static void main(String[] args) {
        CreateClassifyFile entry = new CreateClassifyFile();
        try {
            entry.getClassifyDescs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void getClassifyDescs() throws IOException {
        Classify[] classifyArray = Classify.values();
        if (classifyArray == null || classifyArray.length == 0) {
            return;
        }
        
        for (Classify classify : classifyArray) {
            createClassifyFile(classify);
        }
    }
    
    private void createClassifyFile(Classify classify) throws IOException {
        String fileName = Constants.RAW_PATH + classify.getDesc() + ".txt";
        FileUtils.createFullFile(fileName);
    }
}
