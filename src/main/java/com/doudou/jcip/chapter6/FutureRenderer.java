package com.doudou.jcip.chapter6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.doudou.jcip.chapter5.LaunderThrowable.launderThrowable;

/**
 * 使用Future等待图像下载
 * @author 豆豆
 * @date 2019/5/22 11:43
 * @flag 以万物智能，化百千万亿身
 */
public abstract class FutureRenderer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    void renderPage(CharSequence source){
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<>();
                for (ImageInfo imageInfo : imageInfos){
                    result.add(imageInfo.downloadImage());
                }
                return result;
            }
        };

        Future<List<ImageData>> future = executorService.submit(task);
        //future.cancel(true);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData){
                renderImage(data);
            }
        } catch (InterruptedException e) {
            // Re-assert the thread's interrupted status
            Thread.currentThread().interrupt();
            // We don't need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }

    interface ImageData{}
    interface ImageInfo{
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);
}
