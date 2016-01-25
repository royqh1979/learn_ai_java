package net.roy.learn.ai.search.game;

/**
 * alpha-beta Algrothims
 * @See Artifical Intelligence A Modern Approacth 3rd edition, Chaphter 5.3, P170
 */
public abstract class AlphaBetaGameSearch implements GameSearch {

    public static final boolean DEBUG = false;

    public abstract boolean drawnPosition(Position p);
    public abstract boolean wonPosition(Position p, Player player);
    public abstract double positionEvaluation(Position p);
    public abstract void printPosition(Position p);
    public abstract Position [] possibleMoves(Position p, Player player);
    public abstract Position makeMove(Position p, Player player, Move move);
    public abstract boolean reachedMaxDepth(Position p, int depth);
    public abstract Move getHumanMove();

    /*
     * Search utility methods:
     */

    protected ScoreAndSteps alphaBeta(int depth, Position p, Player player) {
        ScoreAndSteps ss;
        //Score is evaluated for Human.
        // So human need to max it, and computer need to min it
        if (player==Player.Human) {
            ss = alphaBetaMaxValue(0, p, player, -1000000, 1000000);
        } else {
            ss = alphaBetaMinValue(0, p, player, -1000000, 1000000);
        }
        return ss;
    }

    protected  ScoreAndSteps alphaBetaMaxValue(int depth,
                                               Position p, Player player,
                                               double alpha, double beta){
        if (reachedMaxDepth(p, depth)) {
            double value = positionEvaluation(p);
            ScoreAndSteps ss = new ScoreAndSteps(value);
            return ss;
        }
        ScoreAndSteps max=new ScoreAndSteps(alpha);
        Position [] moves = possibleMoves(p, player);
        for (int i=0;i<moves.length;i++) {
            ScoreAndSteps ss2=alphaBetaMinValue(depth+1,moves[i],player.getOpposite(),
                alpha,beta);
            if (ss2.getScore()>max.getScore()) {
                max.clear();
                max.setScore(ss2.getScore());
                max.appendStep(moves[i]);
                max.appendSteps(ss2.getSteps());
            }
            //branch prune:
            // current max score is higher than know beta -- min(max score)
            // Computer will choose the branch with the min(max score),
            // so it will not choose this branch.
            if (max.getScore()>beta) {
                break;
            }
            alpha=Math.max(alpha,max.getScore());
        }
        return max;
    }

    private ScoreAndSteps alphaBetaMinValue(int depth,
                                            Position p, Player player,
                                            double alpha, double beta) {
        if (reachedMaxDepth(p, depth)) {
            double value = positionEvaluation(p);
            ScoreAndSteps ss = new ScoreAndSteps(value);
            return ss;
        }
        ScoreAndSteps min=new ScoreAndSteps(beta);
        Position [] moves = possibleMoves(p, player);
        for (int i=0;i<moves.length;i++) {
            ScoreAndSteps ss2=alphaBetaMaxValue(depth + 1, moves[i], player.getOpposite(),
                    alpha, beta);
            if (ss2.getScore()<min.getScore()) {
                min.clear();
                min.setScore(ss2.getScore());
                min.appendStep(moves[i]);
                min.appendSteps(ss2.getSteps());
            }
            //branch prune:
            // current min score is lower than know alpha -- max(min score)
            // Human should choose the branch with the  max(min score),
            // so we will not choose this branch.
            if (min.getScore()<alpha) {
                break;
            }
            beta=Math.min(beta,min.getScore());
        }
        return min;
    }

    public void playGame(Position startingPosition, Player firstPlayer) {
        if (firstPlayer == Player.Computer) {
            ScoreAndSteps scoreAndSteps = alphaBeta(0, startingPosition, Player.Computer);
            startingPosition = scoreAndSteps.getStartPoisition();
        }
        while (true) {
            printPosition(startingPosition);
            if (wonPosition(startingPosition, Player.Computer)) {
                System.out.println("Program won");
                break;
            }
            if (wonPosition(startingPosition, Player.Human)) {
                System.out.println("Human won");
                break;
            }
            if (drawnPosition(startingPosition)) {
                System.out.println("Drawn game");
                break;
            }
            System.out.println("Your move:");
            Move move = getHumanMove();
            startingPosition = makeMove(startingPosition, Player.Human, move);
            printPosition(startingPosition);
            ScoreAndSteps ss = alphaBeta(0, startingPosition, Player.Computer);
            startingPosition = ss.getStartPoisition();
        }
    }
}
