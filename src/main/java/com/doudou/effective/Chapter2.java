package com.doudou.effective;

public class Chapter2 {

    //演示终结方法攻击

    Integer value = 0;

    Chapter2(int val){
        if (val < 0){
            throw  new IllegalArgumentException("非法参数");
        }
        this.value = val;
    }

    @Override
    public String toString() {
        return value.toString();
    }


}

class Son extends Chapter2{
    static Chapter2 chapter2;

    Son(int val) {
        super(val);
    }

    /**
     * 虚拟机在某个不确定的时间执行，也可能不执行
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        //super.finalize();
        chapter2 = this;
    }


    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/14 21:18a
    */
    public static void main(String[] args){
        try {
            Son son = new Son(-1);

        }catch (Exception e){
            System.out.println(e);
        }

        System.gc();
        System.runFinalization();
        if (chapter2 != null){
            System.out.println("chapter11 is : " + chapter2);
        }
    }
}
