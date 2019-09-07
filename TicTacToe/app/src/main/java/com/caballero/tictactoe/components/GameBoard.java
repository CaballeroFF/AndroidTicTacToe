package com.caballero.tictactoe.components;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Arrays;

public class GameBoard implements Parcelable {

    private static final String TAG = "GameBoard";

    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String EMPTY_VALUE = "empty";
    
    private String[][] boardValues = new String[ROWS][COLS];
    
    public GameBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boardValues[row][col] = EMPTY_VALUE;
            }
        }
    }

    public boolean isMoveLegal(Position position) {
        boolean legal = EMPTY_VALUE.equals(boardValues[position.getRow()][position.getCol()]);
        Log.d(TAG, "isMoveLegal: " + legal);
        Log.d(TAG, "isMoveLegal: " + Arrays.deepToString(boardValues));
        return legal;
    }

    public void makeMove(Position position, int player) {
        Log.d(TAG, "makeMove: " + position + " player " + player);
        boardValues[position.getRow()][position.getCol()] = String.valueOf(player);
        //interface paintView(row, col, playerRes)
    }
    
    public boolean evaluateForWin() {
        //rows
        for (int i = 0; i < 3; i++) {
            if (boardValues[i][0].equals(boardValues[i][1])
                    && boardValues[i][0].equals(boardValues[i][2])
                    && !boardValues[i][0].equals(EMPTY_VALUE)) {
                return true;
            }
        }
        //columns
        for (int i = 0; i < 3; i++) {
            if (boardValues[0][i].equals(boardValues[1][i])
                    && boardValues[0][i].equals(boardValues[2][i])
                    && !boardValues[0][i].equals(EMPTY_VALUE)) {
                return true;
            }
        }
        //diagonal
        if (boardValues[0][0].equals(boardValues[1][1])
                && boardValues[0][0].equals(boardValues[2][2])
                && !boardValues[0][0].equals(EMPTY_VALUE)) {
            return true;
        }
        //diagonal
        if (boardValues[0][2].equals(boardValues[1][1])
                && boardValues[0][2].equals(boardValues[2][0])
                && !boardValues[0][2].equals(EMPTY_VALUE)) {
            return true;
        }
        return false;
    }

    protected GameBoard(Parcel in) {
    }

    public static final Creator<GameBoard> CREATOR = new Creator<GameBoard>() {
        @Override
        public GameBoard createFromParcel(Parcel in) {
            return new GameBoard(in);
        }

        @Override
        public GameBoard[] newArray(int size) {
            return new GameBoard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
