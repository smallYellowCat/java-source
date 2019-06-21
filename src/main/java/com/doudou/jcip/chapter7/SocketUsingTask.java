package com.doudou.jcip.chapter7;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Encapsulating nonstandard cancellation in a task with newTaskFor
 * @author 豆豆
 * @date 2019/5/24 16:20
 * @flag 以万物智能，化百千万亿身
 */
public  class SocketUsingTask<T> implements CancelableTask<T> {

    @GuardedBy("this")
    private Socket socket;

    protected synchronized void setSocket(Socket s){
        socket = s;
    }



    @Override
    public synchronized void cancel() {
        try {
            if (socket != null){
                socket.close();
            }
        } catch (IOException ignored) {

        }
    }

    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUsingTask.this.cancel();
                    System.out.println("yyyy");
                }finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }

    @Override
    public T call() throws Exception {
        return null;
    }
}


/**
 * extension <link>Callable</> interface that provider cancel method and new newTask factory method
 * which to construct RunnableFuture
 * @author 豆豆
 * @date 2019/5/24 16:08
 * @flag 以万物智能，化百千万亿身
 */
interface CancelableTask<T> extends Callable<T>{
    /**
     * provider cancel method for invoker
     */
    void cancel();

    /**
     * construct RunnableFuture
     * @return
     */
    RunnableFuture<T> newTask();

}

/**
 * CancellingExecutor extended ThreadPoolExecutor and override newTaskFor which make CancelableTask could
 * construct itself Future
 * @author 豆豆
 * @date 2019/5/24 16:18
 * @flag 以万物智能，化百千万亿身
 */
@ThreadSafe
class CancellingExecutor extends ThreadPoolExecutor {


    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable){
        if (callable instanceof CancelableTask){
            return ((CancelableTask<T>) callable).newTask();
        }else {
            return super.newTaskFor(callable);
        }
    }
}

class MyTest{
    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/24 17:59a
    */
    public static void main(String[] args){
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        CancellingExecutor executor = new CancellingExecutor(1, 1, 1, SECONDS, workQueue);
        RunnableFuture runnableFuture = executor.newTaskFor(new SocketUsingTask<String>());

        //runnableFuture.run();
        runnableFuture.cancel(true);
    }

}
