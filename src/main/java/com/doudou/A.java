package com.doudou;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class A<T> {
    //Collections
    //AbstractList
    public static final String FALSE_ORG = ".*@@@.*";


    public A() {
    }

    private void test(A<?> a) throws IllegalAccessException, InstantiationException {
        T t = (T) a.getClass().newInstance();
        System.out.println(a.FALSE_ORG);
    }

    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/7 16:46a
    */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        A<String> a = new A<>();
        a.test(new A<String>());
        /*//ConcurrentHashMap
        List<String> ss = new ArrayList<>();
        ss.add(FALSE_ORG);

        B b = new B();
        b.setName(FALSE_ORG);

        ss.add(b.getName());

        Map<String, String> m = Collections.synchronizedMap(new HashMap<>());

        Set<String> s = m.keySet();

    // Synchronizing on m, not s!
        synchronized(m) {
           // while (String k : s)
            //foo(k);
        }
        System.out.println(ss.size());*/

        //Semaphore

        //CyclicBarrier

        //Timer
        //ScheduledThreadPoolExecutor

        //DelayQueue

        //Callable

        //PrivilegedAction

        //ExecutorCompletionService

        //UncaughtExceptionHandler

        //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
        //threadPoolExecutor.setRejectedExecutionHandler();

        //Error

        //ConcurrentHashMap
        //ReentrantLock


        String s = "";
        s.intern();

        Thread.interrupted();



        System.out.printf("x455454xx");
        System.out.print("  ");

        //Comparable
        //CyclicBarrier




        new Thread().stop();
        new Thread().suspend();
        try {
            new Thread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ThreadFactory.class;

        //A.this.test();



       ExecutorService exec = Executors.newCachedThreadPool();
        if (exec instanceof  ThreadPoolExecutor){
            ((ThreadPoolExecutor) exec).setCorePoolSize(10);
        }else {
            throw new AssertionError("Oops, bad assumption");
        }


        System.out.println(System.nanoTime());

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        blockingQueue.poll();
        try {
            new Thread(() -> {
                try {
                    Thread.sleep(1000 * 5);
                    blockingQueue.put("s");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            blockingQueue.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }



    static class B{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        /**
         *入口函数，自动生成
         * @author 豆豆
         * @date 2019/5/29 16:06a
        */
        public static void main(String[] args){

            //byte[] bytes =

        }
    }

    //private static final Comparator
}
