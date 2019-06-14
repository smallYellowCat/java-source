package com.doudou.jcip.chapter6;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 豆豆
 * @date 2019/5/21 13:58
 * @flag 以万物智能，化百千万亿身
 */
public class TaskExecutionWebServer {

    //ExecutorService
    //ThreadPoolExecutor

    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/21 14:02a
    */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true){
            final Socket connection = serverSocket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    //handle request
                }
            };

            exec.execute(task);
        }
    }

}
