package com.caballero.tictactoe.ai;

import com.caballero.tictactoe.components.GameBoard;
import com.caballero.tictactoe.components.Position;

public class HardTicTacToeAi extends TicTacToeAi {

    public HardTicTacToeAi(int playerHuman, int playerComputer) {
        super(playerHuman, playerComputer);
    }

    @Override
    public void makeMove(String[][] board) {
        Position position = analyzeMove(board);
        moveListenerResult(position);
    }

    private Position analyzeMove(String[][] board) {

        // defend or attack rows
        for (int row = 0; row < GameBoard.ROWS; row++) {
            Position position = new Position(0, 0);
            int count = 0;
            for (int col = 0; col < GameBoard.COLS; col++) {
                if (String.valueOf(playerHuman).equals(board[row][col])) {
                    count -= 1;
                } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                    count += 1;
                } else {
                    position.setPosition(row, col);
                }
            }
            if (count < -1 || count > 1) {
                return position;
            }
        }

        // defend or attack cols
        for (int col = 0; col < GameBoard.COLS; col++) {
            Position position = new Position(0, 0);
            int count = 0;
            for (int row = 0; row < GameBoard.ROWS; row++) {
                if (String.valueOf(playerHuman).equals(board[row][col])) {
                    count -= 1;
                } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                    count += 1;
                } else {
                    position.setPosition(row, col);
                }
            }
            if (count < -1 || count > 1) {
                return position;
            }
        }

        //top left diagonal
        int count = 0;
        int col = 0;
        Position position = new Position(0, 0);

        for (int row = 0; row < GameBoard.ROWS; row++) {
            if (String.valueOf(playerHuman).equals(board[row][col])) {
                count -= 1;
            } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                count += 1;
            } else {
                position.setPosition(row, col);
            }
            col++;
        }
        if (count < -1 || count > 1) {
            return position;
        }

        // top right diagonal
        count = 0;
        col = 2;
        for (int row = 0; row < GameBoard.ROWS; row++) {
            if (String.valueOf(playerHuman).equals(board[row][col])) {
                count -= 1;
            } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                count += 1;
            } else {
                position.setPosition(row, col);
            }
            col--;
        }
        if (count < -1 || count > 1) {
            return position;
        }

        if (GameBoard.EMPTY_VALUE.equals(board[1][1])) {
            position.setPosition(1,1);
        }
        else if (GameBoard.EMPTY_VALUE.equals(board[1][0])) {
            position.setPosition(1,0);
        }
        else if (GameBoard.EMPTY_VALUE.equals(board[2][1])) {
            position.setPosition(2,1);
        }
        else if (GameBoard.EMPTY_VALUE.equals(board[1][2])) {
            position.setPosition(1,2);
        }
        else if (GameBoard.EMPTY_VALUE.equals(board[0][1])) {
            position.setPosition(0,1);
        }
        else if (GameBoard.EMPTY_VALUE.equals(board[0][0])) {
            position.setPosition(0,0);
        }
        else if (GameBoard.EMPTY_VALUE.equals(board[2][0])) {
            position.setPosition(2,0);
        }
        else if (GameBoard.EMPTY_VALUE.equals(board[0][2])) {
            position.setPosition(0,2);
        }
        else if (GameBoard.EMPTY_VALUE.equals(board[2][2])) {
            position.setPosition(2,2);
        }
        return position;
    }

    // TODO: 9/6/2019 special cases 
}
