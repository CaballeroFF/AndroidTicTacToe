package com.caballero.tictactoe.ai;

import android.graphics.Point;

import java.util.Random;

public class RandomTicTacToeAI extends TicTacToeAi {

    private static final String TAG = "RandomTicTacToeAI";

    public static final int MIN = 0;
    public static final int MAX = 3;

    public RandomTicTacToeAI(int playerHuman, int playerComputer) {
        super(playerHuman, playerComputer);
    }

    @Override
    public void makeMove(String[][] board) {
        Point position = new Point(getRow(), getCol());
        moveListenerResult(position);
    }

    private int getRow() {
        return new Random().nextInt(MAX - MIN) + MIN;
    }

    private int getCol() {
        return new Random().nextInt(MAX - MIN) + MIN;
    }

}
