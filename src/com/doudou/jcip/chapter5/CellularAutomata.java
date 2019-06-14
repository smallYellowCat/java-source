package com.doudou.jcip.chapter5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 细胞自动化模拟，使用CyclicBarrier协调计算
 * @author 豆豆
 * @date 2019/5/21 10:29
 * @flag 以万物智能，化百千万亿身
 */
public class CellularAutomata {

    private final Board mainBoard;

    private final CyclicBarrier barrier;
    private final Worker[] wokers;

    public CellularAutomata(Board board){
        this.mainBoard = board;
        //虚拟机中可用处理器数量
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                mainBoard.commitNewValues();
            }
        });
        this.wokers = new Worker[count];
        for (int i = 0; i < count; i++){
            wokers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    private class Worker implements Runnable{
        private final Board board;
        public Worker(Board board){
            this.board = board;
        }
        @Override
        public void run() {
            while (!board.hasConverged()){
                for (int x = 0; x < board.getMaxX(); x++){
                    for (int y = 0; y < board.getMaxY(); y++){
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        private int computeValue(int x, int y) {
            // Compute the new value that goes in (x,y)
            return 0;
        }



    }

    public void start(){
        for (int i = 0; i < wokers.length; i++){
            new Thread(wokers[i]).start();
        }
        mainBoard.waitForConvergence();
    }


    interface Board {
        int getMaxX();
        int getMaxY();
        int getValue(int x, int y);
        int setNewValue(int x, int y, int value);
        void commitNewValues();
        boolean hasConverged();
        void waitForConvergence();
        Board getSubBoard(int numPartitions, int index);
    }

    public static class Cell implements Board{

        //二对一的数据结构，首先需要构造这样一个数据结构
        //然后有一个集合，集合的类型就是构造的这种数据结构
        //下面的算法都是针对此数据结构或者数据结构集合进行的
        private List<Node> nodeList = new ArrayList<>();

        private class Node{
            private int x, y, value;

            public Node(int x, int y, int value){
                this.x = x;
                this.y = y;
                this.value = value;
            }
        }

        @Override
        public int getMaxX() {
            return 0;
        }

        @Override
        public int getMaxY() {
            return 0;
        }

        @Override
        public int getValue(int x, int y) {
            return 0;
        }

        @Override
        public int setNewValue(int x, int y, int value) {
            return 0;
        }

        @Override
        public void commitNewValues() {

        }

        @Override
        public boolean hasConverged() {
            return false;
        }

        @Override
        public void waitForConvergence() {

        }

        @Override
        public Board getSubBoard(int numPartitions, int index) {
            return null;
        }
    }
}
