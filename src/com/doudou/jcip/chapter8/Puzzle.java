package com.doudou.jcip.chapter8;

import java.util.Set;

/**
 * 类似于搬箱子的谜题游戏的抽象
 * P代表位置类， M代表移动类
 * @author 豆豆
 * @date 2019/6/3 11:30
 * @flag 以万物智能，化百千万亿身
 */
public interface Puzzle<P, M> {

    /**
     *
     * @return
     */
    P initialPosition();

    /**
     *
     * @param position
     * @return
     */
    boolean isGoal(P position);

    /**
     *
     * @param position
     * @return
     */
    Set<M> legalMoves(P position);

    /**
     *
     * @param position
     * @param move
     * @return
     */
    P move(P position, M move);

}
