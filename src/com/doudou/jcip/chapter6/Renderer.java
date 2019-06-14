package com.doudou.jcip.chapter6;

import com.doudou.jcip.chapter5.LaunderThrowable;

import java.util.List;
import java.util.concurrent.*;

/**
 * 使用CompletionService渲染可用的页面元素。每个任务使用一个线程池中的
 * 线程，任务完成后会有返回值，完成一个渲染一个。并行执行减少了图像下载的总
 * 事件。边下载边渲染提高了页面总的渲染速度
 * @author 豆豆
 * @date 2019/5/22 11:44
 * @flag 以万物智能，化百千万亿身
 */
public abstract class Renderer {

    private final ExecutorService executorService;

    Renderer(ExecutorService executorService){this.executorService = executorService;}

    void renderPage(CharSequence source){
        final List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executorService);
        for (final ImageInfo imageInfo : info){
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
            renderText(source);
            try {
                for (int t= 0, n = info.size(); t < n; t ++){
                    Future<ImageData> f = completionService.take();
                    ImageData imageData = f.get();
                    renderImage(imageData);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
               throw LaunderThrowable.launderThrowable(e.getCause());
            }
        }
    }


    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);


}
