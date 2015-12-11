package org.spider.corpus.consts;

/**
 * <p>
 * 分类的枚举
 * </p>
 * 2015年12月7日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public enum Classify {

    // 编程开发
    Android("Android开发"), Python("Python开发"), Java("Java开发"), IOS("IOS开发"),
    C("C语言"), PHP("PHP开发"), Linux("Linux技术"), Coding("编程开发"),
    Database("数据库"), Demand("项目需求"), Datamining("数据挖掘"), AI("人工智能"), Bigdata("大数据"),
    ComputerGraphics("计算机图形"),
    SoftTesting("软件测试"), DesignPattern("软件设计"), ProjectManage("项目管理"), InformationSecurity("信息安全"), CloudComputing("云计算"),
    FramewordDesign("架构设计"),
    
    // 休闲娱乐
    Video("视频电影"), Muisc("音乐歌曲"), Financial("金融理财"), Cartoon("卡通动漫"), Picture("图片壁纸"),
    Novel("小说文字"), Game("游戏"), Fortune("星相算命"), Humor("幽默搞笑"), Live("直播服务"),
    Sport("体育运动"), Lottery("彩票中奖"), 
    
    // 网上商城
    Surrounding("团购周边"), Shopping("网上购物"),
    
    // 网络服务
    SearchEngine("搜索引擎"), Login("登录注册"), CloudDisk("网络云盘"), Portal("门户网站"), Communication("移动通讯"),
    E_Mail("电子邮件"), Spam("垃圾邮箱"), E_GreetingCard("电子贺卡"), 
    
    // 商业经济
    Energy("能源化工"), Agriculture("农林牧渔"), Bank("银行储蓄"),
    
    // 生活服务
    Hotel("酒店住宿"), Tourism("旅游出行"), Cook("美食烹饪"), Map("地图交通"), Weather("天气预报"),
    Ticket("票务查询"), House("房产土地"), Friend("交友婚恋"), Logistics("快递物流"), Law("法律维权"), 
    Health("健康养生"), 
    
    // 教育文化
    Education("教育文化"), Math("数学学科"), Chymistry("化学学科"), Physical("物理学科"), Exam("考试招生"),
    Biology("生物学科"), Astronomy("天文科普"), History("历史资料"), Baby("母婴育儿"),
    
    // 博客论坛
    Salon("会议沙龙"), Tainning("技术培训"), TechnicalBBS("技术社区"),
    
    // 资讯新闻
    ChineseNews("国内新闻"), ITNews("科技资讯"), Social("社会新闻"), Military("军事军情"), InternationalNews("国际新闻"), CarNews("汽车新闻"),
    Gossip("八卦新闻"), LawNews("法治新闻"),
    
    // 资源工具
    Translation("外语翻译"), Material("资源素材"), Download("资源下载"),
    
    // 综合其他
    Computer("电脑硬件"), Digital("数码产品"), Recruitment("人才招聘"), Government("政府网站");
    
    private String desc;
    
    Classify(String _desc) {
        desc = _desc;
    }
    
    public String getDesc() {
        return desc;
    }
}
