package com.doudou.jcip.chapter7;

import com.doudou.jcip.chapter5.LaunderThrowable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Interrupting a task in a dedicated thread
 * @author 豆豆
 * @date 2019/5/24 11:45
 * @flag 以万物智能，化百千万亿身
 */
public class TimeRun2 {

    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

    public static void timeRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable{

            private volatile  Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                }catch (Throwable t){
                    this.t = t;
                }
            }

            void rethrow(){
                if (t != null){
                    throw LaunderThrowable.launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();

        cancelExec.schedule(taskThread::interrupt, timeout, unit);
        taskThread.join();unit.toMillis(timeout);
        task.rethrow();
    }

}
