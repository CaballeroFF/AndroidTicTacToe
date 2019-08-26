package com.caballero.tictactoe.ai;

import android.widget.ImageView;

import java.util.Random;

public class RandomTicTacToeAI {

    private static final String TAG = "RandomTicTacToeAI";
    public static final int MIN = 0;
    public static final int MAX = 3;

    public RandomTicTacToeAI() {
    }

    public int getRow() {
        return new Random().nextInt(MAX - MIN) + MIN;
    }

    public int getCol() {
        return new Random().nextInt(MAX - MIN) + MIN;
    }
}
