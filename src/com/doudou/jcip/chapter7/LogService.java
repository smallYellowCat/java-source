package com.doudou.jcip.chapter7;

import net.jcip.annotations.GuardedBy;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.*;

/**
 * 向{@link LogWriter}添加可靠的取消。
 *
 * 使用ExecutorService提供的两种关闭方法可以更加简单和优雅的实现。
 * @author 豆豆
 * @date 2019/5/27 11:07
 * @flag 以万物智能，化百千万亿身
 */
public class LogService {

    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;

    public LogService(Writer writer){
        this.queue = new LinkedBlockingQueue<>();
        this.loggerThread = new LoggerThread();
        this.writer =  new PrintWriter(writer);
    }

    @GuardedBy("this")
    private boolean isShutdown;
    @GuardedBy("this")
    private int reservations;

    public void start(){loggerThread.start();}

    public void newStop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(1, TimeUnit.SECONDS);
        }finally {
            writer.close();
        }
    }

    public void newLog(String msg){
        exec.execute(new WriterTask(msg));
    }



    public void stop(){
        synchronized (this){
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized(this){
            if (isShutdown){
                throw new IllegalStateException("logThread is stop");
            }
            ++reservations;
        }
        queue.put(msg);
    }


    private class LoggerThread extends Thread{
        @Override
        public void run() {
            //super.run();
            try {
                while (true){
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0)
                                break;
                        }
                        String msg = queue.take();
                        synchronized (LogService.this){
                            --reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) {
                        //retry
                        e.printStackTrace();
                    }
                }
            }finally {
                writer.close();
            }
        }
    }

    private class WriterTask implements Runnable{

        private final String msg;

        public WriterTask(String msg){
            this.msg = msg;
        }

        @Override
        public void run() {
            writer.println(msg);
        }

    }
}
