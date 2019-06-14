package com.doudou.jcip.chapter8;

import java.util.concurrent.*;

/**
 * Task that deadlocks in a single-threaded Executor
 * @author 豆豆
 * @date 2019/5/29 19:29
 * @flag 以万物智能，化百千万亿身
 */
public class ThreadDeadlock {

    static ExecutorService exec = Executors.newCachedThreadPool();

    public static class LoadFileTask implements Callable<String>{

        private final String fileName;

        public LoadFileTask(String fileName){
            this.fileName = fileName;
        }

        @Override
        public String call() throws Exception {
            //Here's where we would actually read file
            return "fileName";
        }
    }

    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/30 16:50a
    */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> header, footer, page;

        page = exec.submit(new RenderPageTask());
        //page = exec.submit(new LoadFileTask("header.html"));
        System.out.println(page.get());
    }

    public static class RenderPageTask implements Callable<String>{


        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            //will deadlock - task waiting for result of subtask
            return header.get() + page + footer.get();
        }

        private String renderBody(){
            //Here's where we would actually render the page
            return "";
        }
    }


}
