package org.spider.corpus.consts;

import java.util.ArrayList;
import java.util.List;

import org.utils.naga.containers.ArrayUtils;

/**
 * 配置文件
 * 多是一些常量的记录文件
 * 
 * @author Naga
 * Blog : http://blog.csdn.net/lemon_tree12138
 */
public class Config {

    public static class SystemConfig {
        /** 是否打印调试信息的开关 */
        public static final boolean DEBUG = false;
    }
    
    public static class UrlConfig {
        public static final String URL_MAP_INFO_TITLE = "info_title";
        public static final String URL_MAP_INFO_KEYWORDS = "info_keywords";
        public static final String URL_MAP_INFO_DESCRIPTION = "info_description";
        
        public final static int URL_VISITED_READ_TIMED_OUT = 100; // Read timed out
        public final static int URL_VISITED_UNKNOWNHOST = 101; // UnknownHostException
        public final static int URL_VISITED_UNHANDLED_CONTENT_TYPE = 102; // Unhandled content type
        public final static int URL_VISITED_UNKNOWN_ERROR = 103; // unknown error
        public final static int URL_VISITED_OK = 200;
        public final static int URL_VISITED_403 = 403; // HTTP error fetching URL. Status=403
        public final static int URL_VISITED_404 = 404; // HTTP error fetching URL. Status=404
        public final static int URL_VISITED_500 = 500; // HTTP error fetching URL. Status=500
    }
    
    public static class ClassifyPathConfig {
        private final static String TRAINNING_HOME = "E:/workspace/src/Java/Bigdata/Classify/URL/naive_bayes_classifier_data/";
        
        /** 分类器的训练文档 */
        public final static String TRAINNING_PATH = TRAINNING_HOME + "TrainningSet";
        /** 从网页上download下来的原始训练文档目录 */
        public static final String RAW_SET_DIRECTORY = TRAINNING_HOME + "raw_set";
        /** 分类器训练文档的来源保存目录 */
        public static final String RAW_URL_DIRECTORY = TRAINNING_HOME + "raw_url";
    }
    
    public static class ClassifyConfig {
        /** 在分词过程中，用于分隔开词汇的分隔符 */
        public static final String ANALYZER_SEPARATOR = "#@#";
        
        /** 40个特征关键词的权重 */
        public final static double[] FEATURES_WEIGHT = {
                23.372442228572158, 11.686221114286079, 7.79081407619072, 5.8431105571430395, 4.674488445714432,
                3.89540703809536, 3.338920318367451, 2.9215552785715198, 2.5969380253969065, 2.337244222857216,
                2.1247674753247416, 1.94770351904768, 1.7978801714286274, 1.6694601591837255, 1.558162815238144,
                1.4607776392857599, 1.3748495428571859, 1.2984690126984533, 1.230128538345903, 1.168622111428608,
                1.112973439455817, 1.0623837376623708, 1.0161931403727025, 0.97385175952384, 0.9348976891428863,
                0.8989400857143137, 0.8656460084656354, 0.8347300795918627, 0.8059462837438676, 0.779081407619072,
                0.7539497493087793, 0.7303888196428799, 0.7082558251082473, 0.6874247714285929, 0.6677840636734902,
                0.6492345063492266, 0.6316876277992475, 0.6150642691729515, 0.5992933904762092, 0.584311055714304
        };
    }
    
    /**
     * 过滤器配置文件
     * @author Naga
     */
    public static class FilterConfig {
        
        private static FilterConfig mInstance = null; 
        
        private FilterConfig() {
        }
        
        /**
         * 单例模式构造FilterConfig对象
         */
        public static FilterConfig newInstance() {
            if (mInstance == null) {
                mInstance = new FilterConfig();
            }
            
            return mInstance;
        }
        
        /**
         * 英文白名单
         * (忽视大小写)
         */
        private static List<String> mLegalEnglishKeywords = new ArrayList<>();
        
        /** 中文黑名单 */
        private static List<String> mKeywordBlackList = new ArrayList<>();
        
        /**
         * 初始化英文白名单
         */
        public static void initLegalKeywords() {
            if (mLegalEnglishKeywords.size() == 0) {
                ArrayUtils.addStringArrayToList(mLegalEnglishKeywords, DEVELOPING_LANGUAGE);
                ArrayUtils.addStringArrayToList(mLegalEnglishKeywords, OPERATING_SYSTEM);
                ArrayUtils.addStringArrayToList(mLegalEnglishKeywords, PROTOCOLS);
                ArrayUtils.addStringArrayToList(mLegalEnglishKeywords, SERVICES);
                ArrayUtils.addStringArrayToList(mLegalEnglishKeywords, DATABASES);
                ArrayUtils.addStringArrayToList(mLegalEnglishKeywords, COMPANYS);
                ArrayUtils.addStringArrayToList(mLegalEnglishKeywords, DEVELOPING_TECHNOLOGY);
            }
        }
        
        /**
         * 初始化中文黑名单
         */
        public static void initKeywordBlackList() {
            if (mKeywordBlackList.size() == 0) {
                ArrayUtils.addStringArrayToList(mKeywordBlackList, BLACK_LIST);
            }
        }
        
        /**
         * 获得英文白名单
         * @return
         *      白名单列表:mLegalEnglishKeywords
         */
        public static List<String> getLagalEnglishKeywords() {
            initLegalKeywords();
            return mLegalEnglishKeywords;
        }
        
        /**
         * 获得中文黑名单
         */
        public static List<String> getKeywordBlackList() {
            initKeywordBlackList();
            return mLegalEnglishKeywords;
        }
        
        // 开发语言
        public static final String[] DEVELOPING_LANGUAGE = {
                "android", "java", "ruby", "python", "php", "go", "jython", "objective-c", "pascal", "c#", "f#", "actionscript",
                "delphi",
        };
        
        // 操作系统
        public static final String[] OPERATING_SYSTEM = {
                "linux", "centos", "windows", "win7", "unix", "mac", "ios", "android", "symbian", "ubuntu", "debian", "dos",
        };
        
        // 协议 
        public static final String[] PROTOCOLS = {
                "tcp", "udp", "ip",
        };
        
        // 服务器
        public static final String[] SERVICES = {
                "ftp", "git",
        };
        
        // 数据库
        public static final String[] DATABASES = {
                "database", "mysql", "sqlite", "oracle", "sql", "access", "db2", "informix", "sybase", "postgresql", "foxpro",
        };
        
        // 公司
        public static final String[] COMPANYS = {
                "microsoft", "google", "imb", "sun", "apple",
        };
        
        // 相关开发技术
        public static final String[] DEVELOPING_TECHNOLOGY = {
                "github", "unity", "hadoop", "csdn", "ram", "rom", "cart", "virtualbox", "layout", "json", "qt",
                "md5", "sha1",
        };
        
        public static final String[] BLACK_LIST = {
                "的", "地", "得", "我", "你", "他", "它", "京", "证", "粤", "备", "闽", "版权所有", "沪",
                "许可证",
        };
    }
}
