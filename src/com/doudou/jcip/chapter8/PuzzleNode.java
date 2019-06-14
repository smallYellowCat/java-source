package com.doudou.jcip.chapter8;

import net.jcip.annotations.Immutable;

import java.util.LinkedList;
import java.util.List;

/**
 * 谜题中的位置类
 * @author 豆豆
 * @date 2019/6/3 11:35
 * @flag 以万物智能，化百千万亿身
 */
@Immutable
public class PuzzleNode<P, M> {

    final P pos;
    final M move;
    final PuzzleNode<P, M> prev;

   public  PuzzleNode(P pos, M move, PuzzleNode<P, M> prev){
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList(){
       List<M> solution = new LinkedList<>();
       for (PuzzleNode<P, M> n = this; n.move != null; n = n.prev){
           solution.add(0, n.move);
       }
       return solution;
    }
}
