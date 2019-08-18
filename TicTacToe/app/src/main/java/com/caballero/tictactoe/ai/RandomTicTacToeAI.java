package com.caballero.tictactoe.ai;

import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Random;

public class RandomTicTacToeAI extends TicTacToeAI {

    private static final String TAG = "RandomTicTacToeAI";
    
    RandomTicTacToeAI(String[][] boardValues, ImageView[][] imageViews) {
        super(boardValues, imageViews);
    }

    @Override
    ImageView makeMove() {
        final String[][] validMoves = getValidMoves();
        int row = getRow();
        int col = getCol();
        Log.d(TAG, "makeMove: row: " + row + " col: " + col);
        Log.d(TAG, "makeMove: " + Arrays.deepToString(validMoves));
        Log.d(TAG, "makeMove: " + !validMoves[row][col].equals(VALID));
        while (!validMoves[row][col].equals(VALID)) {
            row = getRow();
            col = getCol();
            Log.d(TAG, "makeMove: row: " + row + " col: " + col);
            Log.d(TAG, "makeMove: " + Arrays.deepToString(validMoves));
            Log.d(TAG, "makeMove: " + !validMoves[row][col].equals(VALID));
        }
        return getImageViews()[row][col];
    }

    private int getRow() {
        return new Random().nextInt((getMaxExc() - getMinInc())) + getMinInc();
    }

    private int getCol() {
        return new Random().nextInt((getMaxExc() - getMinInc())) + getMinInc();
    }
}
