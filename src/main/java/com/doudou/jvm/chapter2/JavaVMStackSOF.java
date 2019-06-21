package com.doudou.jvm.chapter2;

/**
 * simulate jvm stack overflow,set vm args: -Xss128k
 * @author 豆豆
 * @date 2019/6/20 20:08
 * @flag 以万物智能，化百千万亿身
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/6/20 20:14a
    */
    public static void main(String[] args){
        JavaVMStackSOF sof = new JavaVMStackSOF();
        try {

            sof.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length :" + sof.stackLength);
            throw e;
        }
    }
}
