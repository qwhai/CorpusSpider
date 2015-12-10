package org.spider.corpus.consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.utils.naga.containers.ArrayUtils;

/**
 * <p>配置文件</p>
 * <p>多是一些常量的记录文件</p>
 * 2015年12月3日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class Config {

    public static class SystemConfig {
        
        /** 是否打印调试信息的开关 */
        public static final boolean DEBUG = true;
    }
    
    public static class ClassifyConfig {
        public static Map<String, Classify> classifyMap = new HashMap<String, Classify>();
        static {
            // 技术开发
            classifyMap.put(Classify.Android.getDesc(), Classify.Android);
            classifyMap.put(Classify.Python.getDesc(), Classify.Python);
            classifyMap.put(Classify.Java.getDesc(), Classify.Java);
            classifyMap.put(Classify.IOS.getDesc(), Classify.IOS);
            classifyMap.put(Classify.C.getDesc(), Classify.C);
            classifyMap.put(Classify.PHP.getDesc(), Classify.PHP);
            classifyMap.put(Classify.Linux.getDesc(), Classify.Linux);
            classifyMap.put(Classify.Coding.getDesc(), Classify.Coding);
            classifyMap.put(Classify.Database.getDesc(), Classify.Database);
            classifyMap.put(Classify.Demand.getDesc(), Classify.Demand);
            classifyMap.put(Classify.Datamining.getDesc(), Classify.Datamining);
            classifyMap.put(Classify.AI.getDesc(), Classify.AI);
            classifyMap.put(Classify.Bigdata.getDesc(), Classify.Bigdata);
            classifyMap.put(Classify.ComputerGraphics.getDesc(), Classify.ComputerGraphics);
            classifyMap.put(Classify.SoftTesting.getDesc(), Classify.SoftTesting);
            classifyMap.put(Classify.DesignPattern.getDesc(), Classify.DesignPattern);
            classifyMap.put(Classify.ProjectManage.getDesc(), Classify.ProjectManage);
            classifyMap.put(Classify.InformationSecurity.getDesc(), Classify.InformationSecurity);
            classifyMap.put(Classify.CloudComputing.getDesc(), Classify.CloudComputing);
            classifyMap.put(Classify.FramewordDesign.getDesc(), Classify.FramewordDesign);
            
            // 休闲娱乐
            classifyMap.put(Classify.Video.getDesc(), Classify.Video);
            classifyMap.put(Classify.Muisc.getDesc(), Classify.Muisc);
            classifyMap.put(Classify.Financial.getDesc(), Classify.Financial);
            classifyMap.put(Classify.Cartoon.getDesc(), Classify.Cartoon);
            classifyMap.put(Classify.Picture.getDesc(), Classify.Picture);
            classifyMap.put(Classify.Novel.getDesc(), Classify.Novel);
            classifyMap.put(Classify.Game.getDesc(), Classify.Game);
            classifyMap.put(Classify.Fortune.getDesc(), Classify.Fortune);
            classifyMap.put(Classify.Humor.getDesc(), Classify.Humor);
            classifyMap.put(Classify.Live.getDesc(), Classify.Live);
            classifyMap.put(Classify.Sport.getDesc(), Classify.Sport);
            classifyMap.put(Classify.Lottery.getDesc(), Classify.Lottery);
            
            // 网上商城
            classifyMap.put(Classify.Surrounding.getDesc(), Classify.Surrounding);
            classifyMap.put(Classify.Shopping.getDesc(), Classify.Shopping);
            
            // 网络服务
            classifyMap.put(Classify.SearchEngine.getDesc(), Classify.SearchEngine);
            classifyMap.put(Classify.Login.getDesc(), Classify.Login);
            classifyMap.put(Classify.CloudDisk.getDesc(), Classify.CloudDisk);
            classifyMap.put(Classify.Portal.getDesc(), Classify.Portal);
            classifyMap.put(Classify.Communication.getDesc(), Classify.Communication);
            classifyMap.put(Classify.E_Mail.getDesc(), Classify.E_Mail);
            classifyMap.put(Classify.Spam.getDesc(), Classify.Spam);
            classifyMap.put(Classify.E_GreetingCard.getDesc(), Classify.E_GreetingCard);
            
            // 商业经济
            classifyMap.put(Classify.Energy.getDesc(), Classify.Energy);
            classifyMap.put(Classify.Agriculture.getDesc(), Classify.Agriculture);
            classifyMap.put(Classify.Bank.getDesc(), Classify.Bank);
            
            // 生活服务
            classifyMap.put(Classify.Hotel.getDesc(), Classify.Hotel);
            classifyMap.put(Classify.Tourism.getDesc(), Classify.Tourism);
            classifyMap.put(Classify.Cook.getDesc(), Classify.Cook);
            classifyMap.put(Classify.Map.getDesc(), Classify.Map);
            classifyMap.put(Classify.Weather.getDesc(), Classify.Weather);
            classifyMap.put(Classify.Ticket.getDesc(), Classify.Ticket);
            classifyMap.put(Classify.House.getDesc(), Classify.House);
            classifyMap.put(Classify.Friend.getDesc(), Classify.Friend);
            classifyMap.put(Classify.Logistics.getDesc(), Classify.Logistics);
            classifyMap.put(Classify.Law.getDesc(), Classify.Law);
            classifyMap.put(Classify.Health.getDesc(), Classify.Health);
            
            // 教育文化
            classifyMap.put(Classify.Education.getDesc(), Classify.Education);
            classifyMap.put(Classify.Math.getDesc(), Classify.Math);
            classifyMap.put(Classify.Chymistry.getDesc(), Classify.Chymistry);
            classifyMap.put(Classify.Physical.getDesc(), Classify.Physical);
            classifyMap.put(Classify.Exam.getDesc(), Classify.Exam);
            classifyMap.put(Classify.Biology.getDesc(), Classify.Biology);
            classifyMap.put(Classify.Astronomy.getDesc(), Classify.Astronomy);
            classifyMap.put(Classify.History.getDesc(), Classify.History);
            classifyMap.put(Classify.Baby.getDesc(), Classify.Baby);
            
            // 博客论坛
            classifyMap.put(Classify.Salon.getDesc(), Classify.Salon);
            classifyMap.put(Classify.Tainning.getDesc(), Classify.Tainning);
            classifyMap.put(Classify.TechnicalBBS.getDesc(), Classify.TechnicalBBS);
            
            // 资讯新闻
            classifyMap.put(Classify.ChineseNews.getDesc(), Classify.ChineseNews);
            classifyMap.put(Classify.ITNews.getDesc(), Classify.ITNews);
            classifyMap.put(Classify.Social.getDesc(), Classify.Social);
            classifyMap.put(Classify.Military.getDesc(), Classify.Military);
            classifyMap.put(Classify.InternationalNews.getDesc(), Classify.InternationalNews);
            classifyMap.put(Classify.CarNews.getDesc(), Classify.CarNews);
            classifyMap.put(Classify.Gossip.getDesc(), Classify.Gossip);
            classifyMap.put(Classify.LawNews.getDesc(), Classify.LawNews);
            
            // 资源工具
            classifyMap.put(Classify.Translation.getDesc(), Classify.Translation);
            classifyMap.put(Classify.Material.getDesc(), Classify.Material);
            classifyMap.put(Classify.Download.getDesc(), Classify.Download);
            
            // 综合其他
            classifyMap.put(Classify.Computer.getDesc(), Classify.Computer);
            classifyMap.put(Classify.Digital.getDesc(), Classify.Digital);
            classifyMap.put(Classify.Recruitment.getDesc(), Classify.Recruitment);
            classifyMap.put(Classify.Government.getDesc(), Classify.Government);
        }
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
