package com.doudou.jcip.chapter6;

import java.util.concurrent.*;

/**
 * 在预定时间内获取广告信息。
 * 考虑这样一个场景： 用户在某个旅游门户网站上输入目的地，日期以及其他条件。门户网站获取并
 * 显示多条航线，旅店或者汽车租赁公司的报价。不同的公司获取报价可能涉及到web服务，访问数据
 * 库，执行一个EDI事物或者其他几只。与其让页面的响应时间受限于最慢的一个响应，不如让页面显
 * 示在给定预定时间内获得的信息，这样更好些。对于没有及时响应的服务提供者，页面要么完全忽略
 * 他们，要么一个占位符。
 * @author 豆豆
 * @date 2019/5/22 18:47
 * @flag 以万物智能，化百千万亿身
 */
public class RenderWithTimeBudget {

    private static final Ad DEFAULT_AD = new Ad();
    private static final long TIME_BUDGET = 1000;
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        Future<Ad> f = exec.submit(new FetchAdTask());

        //Render the page while waiting for the ad
        Page page = renderPageBody();
        Ad ad = null;

        try {
            long timeLeft = endNanos - System.nanoTime();
            ad = f.get(timeLeft, TimeUnit.NANOSECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
            f.cancel(true);
        }
        page.setAd(ad);
        return page;
    }

    Page renderPageBody() { return new Page(); }


    static class Ad {
    }

    static class Page {
        public void setAd(Ad ad) { }
    }

    static class FetchAdTask implements Callable<Ad> {
        public Ad call() {
            return new Ad();
        }
    }

}
