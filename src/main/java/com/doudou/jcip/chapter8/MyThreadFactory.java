package com.doudou.jcip.chapter8;

import java.util.concurrent.ThreadFactory;

/**
 * <p>
 *  Custom thread factory,  interesting custom behavior occurs in MyThread class
 *  which make you can provider name for thread and set custom UncaughtException
 *  -Handler, so that you can write message in log and obtain statistic info,record
 *  number of already created, destroy thread. finally, you can write debug message
 *  into log when thread create or terminate.
 *
 * </p>
 * @author 豆豆
 * @date 2019/6/3 10:28
 * @flag 以万物智能，化百千万亿身
 */
public class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory(String poolName){
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r, poolName);
    }
}
