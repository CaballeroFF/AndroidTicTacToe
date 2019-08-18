package com.caballero.tictactoe.ai;

import android.util.Log;
import android.widget.ImageView;

import com.caballero.tictactoe.MainActivity;

import java.util.Arrays;
import java.util.Random;

class TicTacToeAI {

    public static final String VALID = "Valid";
    public static final String INVALID = "Invalid";
    private static final String TAG = "TicTacToeAI";

    private String[][] validMoves = {{VALID, VALID, VALID}, {VALID, VALID, VALID}, {VALID, VALID, VALID}};
    private String[][] boardValues;
    private ImageView[][] imageViews;
    private int minInc = 0;
    private int maxExc = 3;

    TicTacToeAI(String[][] boardValues, ImageView[][] imageViews) {
        this.boardValues = boardValues;
        this.imageViews = imageViews;
    }

    void updateBoardValues(final int row, final int col, final String tag) {
        boardValues[row][col] = tag;
        if (!tag.equals(MainActivity.EMPTY)) {
            validMoves[row][col] = INVALID;
        }
    }

    String[][] getBoardValues() {
        return boardValues;
    }

    ImageView makeMove() {
//        int row = getRow();
//        int col = getCol();
//        Log.d(TAG, "makeMove: row: " + row + " col: " + col);
//        Log.d(TAG, "makeMove: " + Arrays.deepToString(validMoves));
//        Log.d(TAG, "makeMove: " + !validMoves[row][col].equals(VALID));
//        while (!validMoves[row][col].equals(VALID)) {
//            row = getRow();
//            col = getCol();
//            Log.d(TAG, "makeMove: row: " + row + " col: " + col);
//            Log.d(TAG, "makeMove: " + Arrays.deepToString(validMoves));
//            Log.d(TAG, "makeMove: " + !validMoves[row][col].equals(VALID));
//        }
//        return imageViews[row][col];
        return imageViews[0][0];
    }

    void resetBoard() {
        Log.d(TAG, "resetBoardAI: ");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                validMoves[row][col] = VALID;
                boardValues[row][col] = MainActivity.EMPTY;
            }
        }
    }

//    private int getRow() {
//        return new Random().nextInt((maxExc - minInc)) + minInc;
//    }
//
//    private int getCol() {
//        return new Random().nextInt((maxExc - minInc)) + minInc;
//    }

    public ImageView[][] getImageViews() {
        return imageViews;
    }

    public void setImageViews(ImageView[][] imageViews) {
        this.imageViews = imageViews;
    }

    public String[][] getValidMoves() {
        return validMoves;
    }

    public int getMinInc() {
        return minInc;
    }

    public int getMaxExc() {
        return maxExc;
    }
}
