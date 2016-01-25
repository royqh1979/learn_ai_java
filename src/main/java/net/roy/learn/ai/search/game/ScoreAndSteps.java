package net.roy.learn.ai.search.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Roy on 2016/1/25.
 */
public class ScoreAndSteps {
    private double score;
    private List<Position> steps=new ArrayList<Position>();


    public ScoreAndSteps(double score) {
        this.score=score;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void appendStep(Position move) {
        steps.add(move);
    }

    public List<Position> getSteps() {
        return steps;
    }

    public void appendSteps(List<Position> steps) {
        this.steps.addAll(steps);
    }

    public void clear() {
        steps.clear();
    }

    public Position getStartPoisition() {
        return this.steps.get(0);
    }
}
