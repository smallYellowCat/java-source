package com.doudou.jcip.chapter7;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 可以在关闭ExecutorService时，记录为执行成功中途被取消的任务
 * @author 豆豆
 * @date 2019/5/29 11:02
 * @flag 以万物智能，化百千万亿身
 */
public class TrackingExecutor extends AbstractExecutorService {

    private final ExecutorService exec;
    private final Set<Runnable> taskCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

    public TrackingExecutor(ExecutorService exec){
        this.exec = exec;
    }

    @Override
    public void shutdown() {
        exec.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

    @Override
    public void execute(final Runnable command) {
        exec.execute(() -> {
            try {
                command.run();
            }finally {
                if (isShutdown() && Thread.currentThread().isInterrupted()){
                    taskCancelledAtShutdown.add(command);
                }
            }
        });
    }

    /**
     *
     * @return 返回被取消的任务清单
     */
    public List<Runnable> getCancelledTasks(){
        if (!exec.isTerminated()){
            throw new IllegalStateException("exec not terminated");
        }
        return new ArrayList<>(taskCancelledAtShutdown);
    }
}
