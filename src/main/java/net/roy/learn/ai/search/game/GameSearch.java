package net.roy.learn.ai.search.game;

/**
 * Created by Roy on 2016/1/25.
 */
public interface GameSearch {
    boolean drawnPosition(Position p);
    boolean wonPosition(Position p, Player player);

    /**
     * return socore of the position, in the view of the HUMAN
     * @param p
     * @return
     */
    double positionEvaluation(Position p);
    void printPosition(Position p);
    Position [] possibleMoves(Position p, Player player);
    Position makeMove(Position p, Player player, Move move);
    boolean reachedMaxDepth(Position p, int depth);
    Move getHumanMove();
    void playGame(Position startingPosition, Player fistPlayer);
}
