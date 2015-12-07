package org.spider.corpus.thread;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.utils.naga.consts.Constant.TimeUtils;

/**
 * <p>自定义封装的线程池</p>
 * 2015年12月7日
 * 
 * @author <a href="http://weibo.com/u/5131020927">Q-WHai</a>
 * @see <a href="http://blog.csdn.net/lemon_tree12138">http://blog.csdn.net/lemon_tree12138</a>
 * @version 0.1
 */
public class SpiderThreadPool {

    private static SpiderThreadPool mSpiderThreadPool = null;
    
    private ThreadPoolExecutor mThreadPool; // 线程池
    private final int DEFAULT_CORE_POOL_SIZE = 40;
    private final int DEFAULT_MAXIMUM_POOL_SIZE = 50;
    private final int DEFAULT_KEEP_ALIVE_TIME = 3;
    private final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    private final long DEFAULT_WAITTING_TIME = 3 * TimeUtils.ONE_SECOND_MILLIS; // 默认情况下，线程池满时的等待时间

    // 这里采用单例模式创建线程池对象，关闭了公有的构造器
    private SpiderThreadPool() {
    }
    
    /**
     * 创建一个线程池的对象
     * 
     * @return
     *      线程池对象
     */
    public static SpiderThreadPool newInstance() {
        if (mSpiderThreadPool == null) {
            mSpiderThreadPool = new SpiderThreadPool();
        }
        
        return mSpiderThreadPool;
    }
    
    /**
     * 以默认方式创建一个线程池对象
     * 
     * @return
     *      线程池
     */
    public SpiderThreadPool create() {
        if (mThreadPool == null) {
            mThreadPool = getThreadPool();
        }
        
        return mSpiderThreadPool;
    }
    
    /**
     * 创建一个自定义的线程池对象
     * 
     * @param corePoolSize
     *      核心线程数
     * @param maximumPoolSize
     *      最大线程数
     * @param keepAliveTime
     *      线程保持活跃的时间
     * @param unit
     *      活跃时间的单位
     * @return
     *      线程池
     */
    public SpiderThreadPool create(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        if (mThreadPool == null) {
            mThreadPool = getThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit);
        }
        
        return mSpiderThreadPool;
    }

    /**
     * 提交一个线程操作
     * 
     * @param thread
     *      线程对象
     */
    public void submit(Runnable thread) {
        submit(thread, DEFAULT_WAITTING_TIME);
    }
    
    /**
     * 提交一个线程操作
     * 
     * @param thread
     *      待执行线程
     * @param waittingTime
     *      线程池满时的等待时间
     */
    public void submit(Runnable thread, long waittingTime) {
        if (mThreadPool == null) {
            throw new NullPointerException("请先初始化线程池");
        }

        // 执行线程之前检查线程池是否已满
        poolQueueFull(waittingTime);
        
        mThreadPool.execute(thread);
    }
    
    // 判断线程池中的线程队列是否已经满了
    private void poolQueueFull(long waittingTime) {
        while (getQueueSize(mThreadPool.getQueue()) >= mThreadPool.getMaximumPoolSize()) {
            System.out.println("线程池队列已满，等" + waittingTime + "毫秒再添加任务");
            try {
                Thread.sleep(waittingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    // 获得一个线程池对象
    private ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAXIMUM_POOL_SIZE, DEFAULT_KEEP_ALIVE_TIME, DEFAULT_TIME_UNIT, new ArrayBlockingQueue<Runnable>(DEFAULT_MAXIMUM_POOL_SIZE), new ThreadPoolExecutor.DiscardOldestPolicy());
    }
    
    // 获得一个线程池对象
    private ThreadPoolExecutor getThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new ArrayBlockingQueue<Runnable>(maximumPoolSize), new ThreadPoolExecutor.DiscardOldestPolicy());
    }
    
    // 获得线程池中的阻塞线程数
    private synchronized int getQueueSize(Queue<Runnable> queue) {
        return queue.size();
    }
}
