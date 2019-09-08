package com.caballero.tictactoe.ai;

import com.caballero.tictactoe.TicTacToeActivity;
import com.caballero.tictactoe.util.Position;

public class HardTicTacToeAi extends TicTacToeAi {
    private static final String TAG = "HardTicTacToeAi";

    private Position currentPos;
    private String[][] board;

    public HardTicTacToeAi(int playerHuman, int playerComputer) {
        super(playerHuman, playerComputer);
    }

    @Override
    public void makeMove(String[][] board) {
        this.board = board;
        Position position = analyzeMove();
        moveListenerResult(position);
    }

    private Position analyzeMove() {
        // attack rows
        currentPos = new Position(0, 0);
        if (isKeyRowMove(true)) {
            return currentPos;
        }
        // attack cols
        if (isKeyColMove(true)) {
            return currentPos;
        }
        // attack left diagonal
        if (isKeyDiagonalMove(0, true)) {
            return currentPos;
        }
        // attack right diagonal
        if (isKeyDiagonalMove(2, true)) {
            return currentPos;
        }


        // defend rows
        if (isKeyRowMove(false)) {
            return currentPos;
        }
        // defend cols
        if (isKeyColMove(false)) {
            return currentPos;
        }
        // defend left diagonal
        if (isKeyDiagonalMove(0, false)) {
            return currentPos;
        }
        // defend right diagonal
        if (isKeyDiagonalMove(2, false)) {
            return currentPos;
        }
        if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][1]) || String.valueOf(playerComputer).equals(board[1][1])) {
            freeMiddleMove();
        } else {
            takenMiddleMove();
        }
        return currentPos;
    }

    private boolean isKeyRowMove(boolean attacking) {
        // attack rows
        for (int row = 0; row < TicTacToeActivity.ROWS; row++) {
            int count = 0;
            for (int col = 0; col < TicTacToeActivity.COLS; col++) {
                if (String.valueOf(playerHuman).equals(board[row][col])) {
                    count -= 1;
                } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                    count += 1;
                } else {
                    currentPos.setPosition(row, col);
                }
            }
            if (attacking) {
                if (count > 1) {
                    return true;
                }
            } else {
                if (count < -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isKeyColMove(boolean attacking) {
        // attack rows
        for (int col = 0; col < TicTacToeActivity.ROWS; col++) {
            int count = 0;
            for (int row = 0; row < TicTacToeActivity.COLS; row++) {
                if (String.valueOf(playerHuman).equals(board[row][col])) {
                    count -= 1;
                } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                    count += 1;
                } else {
                    currentPos.setPosition(row, col);
                }
            }
            if (attacking) {
                if (count > 1) {
                    return true;
                }
            } else {
                if (count < -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isKeyDiagonalMove(int columnStart, boolean attacking) {
        //top left diagonal
        int count = 0;
        int col = columnStart;
        int direction = 1;
        if (col == 2) {
            direction = -1;
        }

        for (int row = 0; row < TicTacToeActivity.ROWS; row++) {
            if (String.valueOf(playerHuman).equals(board[row][col])) {
                count -= 1;
            } else if (String.valueOf(playerComputer).equals(board[row][col])) {
                count += 1;
            } else {
                currentPos.setPosition(row, col);
            }
            col += direction;
        }
        if (attacking) {
            return count > 1;
        } else {
            return count < -1;
        }

    }

    private void takenMiddleMove() {
        if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][0])) {
            currentPos.setPosition(0, 0);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][0])) {
            currentPos.setPosition(2, 0);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][2])) {
            currentPos.setPosition(0, 2);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][2])) {
            currentPos.setPosition(2, 2);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][1])) {
            currentPos.setPosition(1, 1);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][1])) {
            currentPos.setPosition(0, 1);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][0])) {
            currentPos.setPosition(1, 0);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][1])) {
            currentPos.setPosition(2, 1);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][2])) {
            currentPos.setPosition(1, 2);
        }
    }

    private void freeMiddleMove() {
        if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][1])) {
            currentPos.setPosition(1, 1);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][0])) {
            currentPos.setPosition(1, 0);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][1])) {
            currentPos.setPosition(2, 1);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[1][2])) {
            currentPos.setPosition(1, 2);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][1])) {
            currentPos.setPosition(0, 1);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][0])) {
            currentPos.setPosition(0, 0);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][0])) {
            currentPos.setPosition(2, 0);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[0][2])) {
            currentPos.setPosition(0, 2);
        } else if (TicTacToeActivity.EMPTY_VALUE.equals(board[2][2])) {
            currentPos.setPosition(2, 2);
        }
    }
}
