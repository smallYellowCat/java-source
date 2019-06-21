package com.doudou.jcip.chapter7;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <li>
 * The log service was separated into single log thread. so, produce message thread
 * will not write directly message into output stream, 而是由LogWriter通过BlockingQueue
 * 把这个任务移交给了日志线程，并由日志线程写入。这是一个多生产者，单消费者的模式。
 *
 * note：为了让此服务可用我们需要提供终止方法，这样不会让JVM无法正常关闭。停止日志线程很容易，因为他在不
 * 断的调用take，而take响应中断；如果日志线程被修改为捕获到InterruptedException就退出，那么中断日志
 * 线程就能够停止服务。
 *
 * 但是，这样突然的关闭忽略了等待中需要被记录的日志，但更重要的是，线程会因为队列已满在log处阻塞，永远不可
 * 能从阻塞中解脱。取消消费者生产者活动既要取消生产者还要取消消费者。
 *
 * 因此可以设置一个"已请求关闭"的标志，避免消息进一步提交进来。在接收到关闭请求后，消费者会离开队列，写出所
 * 有等待中的消息，并将log中所有阻塞的生产者解除阻塞。
 * </li>
 * <code>
 *     public void log(string msg) throws InterruptedException{
 *         if(!shutdownRequested)
 *             queue.put(msg);
*          else
 *             throw new IllegalStateException("logger is shut down");
 *     }
 * </code>
 *
 * <p>
 * note : 请注意此方法存在竞态条件，这是一个典型的先检查后执行的例子。生产者观察没有被关闭的服务，但是即使
 * 关闭后任然会把消息放入队列。同样的这里存在这样的风险，生产者在log中被阻塞却不能解脱。
 *
 * 要解决这个问题就必须保证创建新日志消息的各个任务必须是原子的。但是我们不希望在消息接入队列时加锁，因为put
 * 方法本身可能发生阻塞。所以我们能做的是原子化的检查关闭，并有条件地递增记录获得提交消息权利的计数器。请参阅
 *{@link LogService}
 * </p>
 *
 *
 * @author 豆豆
 * @date 2019/5/27 10:05
 * @flag 以万物智能，化百千万亿身
 */
public class LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private static final int CAPACITY = 1000;

    public LogWriter(Writer writer){
        this.queue = new LinkedBlockingQueue<>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start(){
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LoggerThread extends Thread{

        private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer, true);
        }

        @Override
        public void run() {
            //super.run();
            try {
                while (true){
                    writer.println(queue.take());
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
                Thread.currentThread().interrupt();
            }finally {
                writer.close();
            }
        }
    }
}
