package com.caballero.tictactoe.ai;

import android.graphics.Point;

import com.caballero.tictactoe.TicTacToeActivity;

public class MediumTicTacToeAi extends TicTacToeAi {


    public MediumTicTacToeAi(int playerHuman, int playerComputer) {
        super(playerHuman, playerComputer);
    }

    @Override
    public void makeMove(String[][] board) {
        Point position = analyzeMove(board);
        moveListenerResult(position);
    }

    private Point analyzeMove(String[][] board) {
        // defend or attack rows
        for (int row = 0; row < TicTacToeActivity.ROWS; row++) {
            Point position = new Point(0, 0);
            int count = 0;
            for (int col = 0; col < TicTacToeActivity.COLS; col++) {
                if (String.valueOf(playerHuman).equals(board[row][col])) {
                    count -= 1;
                } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                    count += 1;
                } else {
                    position.set(row, col);
                }
            }
            if (count < -1 || count > 1) {
                return position;
            }
        }

        // defend or attack cols
        for (int col = 0; col < TicTacToeActivity.COLS; col++) {
            Point position = new Point(0, 0);
            int count = 0;
            for (int row = 0; row < TicTacToeActivity.ROWS; row++) {
                if (String.valueOf(playerHuman).equals(board[row][col])) {
                    count -= 1;
                } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                    count += 1;
                } else {
                    position.set(row, col);
                }
            }
            if (count < -1 || count > 1) {
                return position;
            }
        }

        //top left diagonal
        int count = 0;
        int col = 0;
        Point position = new Point(0, 0);

        for (int row = 0; row < TicTacToeActivity.ROWS; row++) {
            if (String.valueOf(playerHuman).equals(board[row][col])) {
                count -= 1;
            } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                count += 1;
            } else {
                position.set(row, col);
            }
            col++;
        }
        if (count < -1 || count > 1) {
            return position;
        }

        // top right diagonal
        count = 0;
        col = 2;
        for (int row = 0; row < TicTacToeActivity.ROWS; row++) {
            if (String.valueOf(playerHuman).equals(board[row][col])) {
                count -= 1;
            } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                count += 1;
            } else {
                position.set(row, col);
            }
            col--;
        }
        if (count < -1 || count > 1) {
            return position;
        }

        if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][1])) {
            position.set(1,1);
        }
        else if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][0])) {
            position.set(0,0);
        }
        else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][0])) {
            position.set(2,0);
        }
        else if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][2])) {
            position.set(0,2);
        }
        else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][2])) {
            position.set(2,2);
        }
        else if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][0])) {
            position.set(1,0);
        }
        else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][1])) {
            position.set(2,1);
        }
        else if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][2])) {
            position.set(1,2);
        }
        else if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][1])) {
            position.set(0,1);
        }
        return position;
    }
}
