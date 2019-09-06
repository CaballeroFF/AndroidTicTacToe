package com.caballero.tictactoe.ai;

import java.util.Random;

public class RandomTicTacToeAI extends TicTacToeAi{

    private static final String TAG = "RandomTicTacToeAI";
    public static final int MIN = 0;
    public static final int MAX = 3;

    public RandomTicTacToeAI() {
    }

    @Override
    public void makeMove() {
        moveListenerResult(getRow(), getCol());
    }

    private int getRow() {
        return new Random().nextInt(MAX - MIN) + MIN;
    }

    private int getCol() {
        return new Random().nextInt(MAX - MIN) + MIN;
    }

}
