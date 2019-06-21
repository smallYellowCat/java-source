package com.doudou.jcip.chapter7;

import net.jcip.annotations.GuardedBy;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Using TrackingExecutorService to save unfinished tasks for later execution
 * TrackingExecutor存在不可避免的竞争条件，识别出被取消的任务实际上可能已经结束。原因是在任务执行
 * 最后一条指令与线程池记录任务结束之间的这段时间线程池可能发生关闭（shutdownNow,就是任务做完了但
 * 是还没有记录已完成）。如果任务是幂等的，那这不会有什么问题。应用程序得到已被取消的任务必须注意这个
 * 风险，应该为这种假阳性现象做好准备。
 * @author 豆豆
 * @date 2019/5/29 11:08
 * @flag 以万物智能，化百千万亿身
 */
public abstract class WebCrawler {

    private volatile TrackingExecutor exec;

    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<>();

    private final ConcurrentHashMap<URL, Boolean> seen = new ConcurrentHashMap<>();

    private static final long TIMEOUT = 500;

    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    public WebCrawler(URL startUrl){
        urlsToCrawl.add(startUrl);
    }

    public synchronized void start(){
        exec = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl){
            submitCrawlTask(url);
        }
        urlsToCrawl.clear();
    }

    public synchronized void stop(){
        try {
            try {
                saveUncrawled(exec.shutdownNow());
                if (exec.awaitTermination(TIMEOUT, UNIT)){
                    saveUncrawled(exec.getCancelledTasks());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            exec = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    private void saveUncrawled(List<Runnable> uncrawled){
        for (Runnable task : uncrawled){
            urlsToCrawl.add(((CrawlTask)task).getPage());
        }
    }

    private void submitCrawlTask(URL url){
        exec.execute(new CrawlTask(url));
    }

    private class CrawlTask implements Runnable {
        private final URL url;

        CrawlTask(URL url) {
            this.url = url;
        }

        private int count = 1;

        boolean alreadyCrawled() {
            return seen.putIfAbsent(url, true) != null;
        }

        void markUncrawled() {
            seen.remove(url);
            System.out.printf("marking %s uncrawled%n", url);
        }

        public void run() {
            for (URL link : processPage(url)) {
                if (Thread.currentThread().isInterrupted())
                    return;
                submitCrawlTask(link);
            }
        }

        public URL getPage() {
            return url;
        }
    }


}
