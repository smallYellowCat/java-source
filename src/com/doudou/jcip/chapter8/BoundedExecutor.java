package com.doudou.jcip.chapter8;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * use a semaphore(信号量) to throttle task submission.
 * Semaphore会限制任务的注入率。此示例使用一个非受限队列（没有
 * 理由同时限制队列大小和注入率），设置Semaphore的限制范围等于在
 * 池的大小上加上你希望允许的可排队的任务数量，因为Semaphore限制
 * 的是当前执行的任务数和等待执行的任务数。
 * @author 豆豆
 * @date 2019/6/3 9:49
 * @flag 以万物智能，化百千万亿身
 */
public class BoundedExecutor {

    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, int bound){
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(() -> {
                try {
                    command.run();
                }finally {
                    semaphore.release();
                }
            });
        }catch (RejectedExecutionException e){
            semaphore.release();
        }
    }
}
