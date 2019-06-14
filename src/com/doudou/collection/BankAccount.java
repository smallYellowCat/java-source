package com.doudou.collection;


import java.math.BigDecimal;

public class BankAccount {

    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/4/28 10:23a
    */
    public static void main(String[] args){
        //Exception
        UserAccount userAccount = UserAccount.init();
        //模拟日常消费
        MyThread myThread1 = new MyThread(1, userAccount, new BigDecimal(10));
        MyThread myThread2 = new MyThread(0, userAccount, new BigDecimal(100));
        MyThread myThread3 = new MyThread(1, userAccount, new BigDecimal(205));

        myThread1.run();
        myThread2.run();
        myThread3.run();
    }

    static class MyThread implements Runnable{

        private int flag;
        private UserAccount userAccount;
        private BigDecimal num;

        public MyThread(int flag, UserAccount userAccount, BigDecimal num){
            this.flag = flag;
            this.userAccount = userAccount;
            this.num = num;
        }

        @Override
        public void run() {
            if (flag == 0){
                //存钱
                userAccount.add(num);
            }else if (flag == 1){
                //消费
                userAccount.consume(num);
            }
            System.out.println("账户余额为："+ userAccount.getCount());
        }
    }


}


class UserAccount{
    private String username;



    private BigDecimal count;

    public static UserAccount init(){
        return new UserAccount("张三", new BigDecimal(10000));
    }

    private UserAccount(String username, BigDecimal count){
        this.username = username;
        this.count = count;
    }

    public synchronized BigDecimal consume(BigDecimal bigDecimal){
        this.count = this.count.subtract(bigDecimal);
        return this.count;
    }

    public synchronized BigDecimal add(BigDecimal bigDecimal){
        this.count = this.count.add(bigDecimal);
        return this.count;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
