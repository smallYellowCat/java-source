package com.doudou.jcip.chapter5.HightSpeedCacheDesign;

import com.doudou.jcip.chapter5.LaunderThrowable;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 此缓存还有一个问题是缓存过期的问题，可以通过FutureTask的子类来完成
 * 为每一个结果关联一个过期时间，并且周期性的扫描缓存中过期的元素（同样，他也没有解决
 * 缓存清理的问题，即移除旧的计算结果，给新的腾出空间）
 * @author 豆豆
 * @date 2019/5/21 12:31
 * @flag 以万物智能，化百千万亿身
 */
public class Memoizer<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>(100);

    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c){
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true){
            Future<V> f = cache.get(arg);
            if (f == null){
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };

                FutureTask<V> ft = new FutureTask<>(eval);
                //利用了底层的原子操作，来解决检查再运行存在的竞态条件
                f =cache.putIfAbsent(arg, ft);
                //cache.put(arg, ft);
                //c.compute发生在这里
                //ft.run();

                if (f == null){f =ft; ft.run();}
            }
            try {
                return f.get();
            } catch (CancellationException e){
                //解决缓存污染，如果一个计算被取消或是失败，未来尝试对这个值进行计算都会表现为取消或是失败。
                //所以需要从缓存中移除失败或是失败的计算
                cache.remove(arg, f);
            }catch (ExecutionException e) {
                throw LaunderThrowable.launderThrowable(e.getCause());
            }
        }

    }
}
