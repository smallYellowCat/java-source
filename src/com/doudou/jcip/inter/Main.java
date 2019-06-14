package com.doudou.jcip.inter;

public class Main {
   private  Thread thread;

    public Main(Button button){
        button.registerListener(new EventLisner() {
            @Override
            public void doSome() {
                //隐式的this引用逸出，在这个内部类中Main还没有构建完成
            }
        });
    }



    public Main(){
        //构造函数中创建线程时不要立即启动线程，应该提供一个启动函数
       this.thread =  new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    public boolean start(Main main){
        main.thread.start();
        return true;
    }


}
