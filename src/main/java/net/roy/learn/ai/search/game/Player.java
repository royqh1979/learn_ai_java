package net.roy.learn.ai.search.game;

/**
 * Created by Roy on 2016/1/25.
 */
public enum Player {
    Human,
    Computer;
    public Player getOpposite() {
        if (this==Human)
            return Computer;
        else
            return Human;
    }
}
