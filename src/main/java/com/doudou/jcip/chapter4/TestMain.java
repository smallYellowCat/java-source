package com.doudou.jcip.chapter4;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestMain {
    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/16 9:36a
    */
    public static void main(String[] args){
        PrivateLock privateLock = new PrivateLock();

        //车辆调度的测试
        //随机生成int证书
        Map<String, MutablePoint> locations = new HashMap<>(8);

        for (int i = 0; i < 6; i++){
            //初始化所有的车辆位置
            MutablePoint point = new MutablePoint();
            locations.put(""+i, point);
        }
        MonitorVehicleTracker monitorVehicleTracker = new MonitorVehicleTracker(locations);

        MyThread thread1 = new MyThread(privateLock, monitorVehicleTracker);
        MyThread thread2 = new MyThread(privateLock, monitorVehicleTracker);
        MyThread thread3 = new MyThread(privateLock, monitorVehicleTracker);
        MyThread thread4 = new MyThread(privateLock, monitorVehicleTracker);
        thread1.setName("thread1");
        thread2.setName("thread2");
        thread3.setName("thread3");
        thread4.setName("thread4");






        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }

    static class MyThread extends Thread{

        private Widget widget;

        private PrivateLock privateLock;

        /**车辆追踪监视器*/
        private MonitorVehicleTracker monitorVehicleTracker;

        public MyThread(Widget widget){
            this.widget = widget;
        }

        public MyThread(PrivateLock privateLock){
            this.privateLock = privateLock;
        }

        public MyThread(PrivateLock privateLock, MonitorVehicleTracker monitorVehicleTracker){
            this.privateLock = privateLock;
            this.monitorVehicleTracker = monitorVehicleTracker;
        }

        @Override
        public void run() {
            //super.run();
           // privateLock.someMethod();

            //读取或是修改车的位置
            Random random = new Random(10);
            for (int i = 0; i < 6; i++){
                int x = random.nextInt(360);
                int y = random.nextInt(360);
                monitorVehicleTracker.setLocation(""+i, x, y);
                System.out.println("set " + i +  ": x = " + x + "y = " + y);

                String s = i + " : x = " + monitorVehicleTracker.getLocation(""+i).x + "; y = "
                        + monitorVehicleTracker.getLocation(""+i).y;
                System.out.println(s);
            }
        }
    }
}
