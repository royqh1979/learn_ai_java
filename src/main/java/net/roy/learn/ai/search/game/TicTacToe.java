package net.roy.learn.ai.search.game;

import java.util.*;

public class TicTacToe extends AlphaBetaGameSearch {

    public boolean drawnPosition(Position p) {
        if (AlphaBetaGameSearch.DEBUG) System.out.println("drawnPosition("+p+")");
        boolean ret = true;
        TicTacToePosition pos = (TicTacToePosition)p;
        for (int i=0; i<9; i++) {
            if (pos.board[i] == TicTacToePosition.BLANK){
                ret = false;
                break;
            }
        }
        if (AlphaBetaGameSearch.DEBUG) System.out.println("     ret="+ret);
        return ret;
    }

    @Override
    public boolean wonPosition(Position p, Player player) {
        if (AlphaBetaGameSearch.DEBUG)
            System.out.println("wonPosition("+p+","+player+")");
        boolean ret = false;
        TicTacToePosition pos = (TicTacToePosition)p;
        if (winCheck(0,1,2, player, pos)) ret = true;
        else if (winCheck(3,4,5, player, pos)) ret = true;
        else if (winCheck(6,7,8, player, pos)) ret = true;
        else if (winCheck(2,5,8, player, pos)) ret = true;
        else if (winCheck(0,4,8, player, pos)) ret = true;
        else if (winCheck(2,4,6, player, pos)) ret = true;
        else if (winCheck(0,3,6, player, pos)) ret = true;
        else if (winCheck(1,4,7, player, pos)) ret = true;
        if (AlphaBetaGameSearch.DEBUG) System.out.println("     ret="+ret);
        return ret;
    }

    private boolean winCheck(int i1, int i2, int i3,
                             Player player, TicTacToePosition pos) {
        int b = 0;
        if (player==Player.Human) b = TicTacToePosition.HUMAN;
        else        b = TicTacToePosition.PROGRAM;
        if (pos.board[i1] == b &&
            pos.board[i2] == b &&
            pos.board[i3] == b)         return true;
        return false;
    }

    public double positionEvaluation(Position p) {
        int count = 0;
        TicTacToePosition pos = (TicTacToePosition)p;
        for (int i=0; i<9; i++) {
            if (pos.board[i] == 0) count++;
        }
        count = 10 - count;
        // prefer the center square:
        double base = 0f;
        if (pos.board[4] == TicTacToePosition.HUMAN ) {
            base += 0.4f;
        }
        if (pos.board[4] == TicTacToePosition.PROGRAM ) {
            base -= 0.4f;
        }
        if (wonPosition(p, Player.Human))  {
            base += (10.0f / count);
        }
        if (wonPosition(p, Player.Computer))  {
            base -= (10.0f / count);
        }
        return base;
    }
    public void printPosition(Position p) {
        System.out.println("Board position:");
        TicTacToePosition pos = (TicTacToePosition)p;
        int count = 0;
        for (int row=0; row<3; row++) {
            System.out.print("+---+---+---+");
            System.out.println();
            for (int col=0; col<3; col++) {
                if (pos.board[count] == TicTacToePosition.HUMAN) {
                    System.out.print("| H ");
                } else if (pos.board[count] == TicTacToePosition.PROGRAM) {
                    System.out.print("| P ");
                } else {
                    System.out.print("|   ");
                }
                count++;
            }
            System.out.println("|");
        }
        System.out.print("+---+---+---+");
        System.out.println();
    }

    @Override
    public Position [] possibleMoves(Position p, Player player) {
        if (AlphaBetaGameSearch.DEBUG)
            System.out.println("posibleMoves("+p+","+player+")");
        TicTacToePosition pos = (TicTacToePosition)p;
        int count = 0;
        for (int i=0; i<9; i++) if (pos.board[i] == 0) count++;
        if (count == 0) return new Position[0];
        Position [] ret = new Position[count];
        count = 0;
        for (int i=0; i<9; i++) {
            if (pos.board[i] == 0) {
                TicTacToePosition pos2 = new  TicTacToePosition();
                for (int j=0; j<9; j++) pos2.board[j] = pos.board[j];
                if (player==Player.Human)
                    pos2.board[i] = TicTacToePosition.HUMAN;
                else
                    pos2.board[i] = TicTacToePosition.PROGRAM;
                ret[count++] = pos2;
                if (AlphaBetaGameSearch.DEBUG) System.out.println("    "+pos2);
            }
        }
        return ret;
    }
    public Position makeMove(Position p, Player player, Move move) {
        if (AlphaBetaGameSearch.DEBUG)
            System.out.println("Entered TicTacToe.makeMove");
        TicTacToeMove m = (TicTacToeMove)move;
        TicTacToePosition pos = (TicTacToePosition)p;
        TicTacToePosition pos2 = new  TicTacToePosition();
        for (int i=0; i<9; i++) pos2.board[i] = pos.board[i];
        int pp;
        if (player==Player.Human)
            pp =  TicTacToePosition.HUMAN;
        else
            pp = TicTacToePosition.PROGRAM;
        if (AlphaBetaGameSearch.DEBUG)
            System.out.println("makeMove: m.moveIndex = " + m.moveIndex);
        pos2.board[m.moveIndex] = pp;
        return pos2;
    }
    public boolean reachedMaxDepth(Position p, int depth) {
        boolean ret = false;
        if (wonPosition(p, Player.Computer)) ret = true;
        else if (wonPosition(p, Player.Human))  ret = true;
        else if (drawnPosition(p))      ret = true;
        if (AlphaBetaGameSearch.DEBUG) {
            System.out.println("reachedMaxDepth: pos=" + p.toString() + ", depth="+depth
                               +", ret=" + ret);
        }
        return ret;
    }
    public Move getHumanMove() {
        if (AlphaBetaGameSearch.DEBUG) System.out.println("Enter blank square index [0,8]:");
        int i = 0;
        try {
            int ch = System.in.read();
            i = ch - 48;
            System.in.read();
            System.in.read();
            System.out.println("ch="+ch+", i=" + i);
        } catch (Exception e) { }
        TicTacToeMove mm = new TicTacToeMove();
        mm.moveIndex = i;
        return mm;
    }
    static public void main(String [] args) {
        TicTacToePosition p = new TicTacToePosition();
        TicTacToe ttt = new TicTacToe();
        ttt.playGame(p, Player.Computer);
    }
}
