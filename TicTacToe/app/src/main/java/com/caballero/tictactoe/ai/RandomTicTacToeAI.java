package com.caballero.tictactoe.ai;

import com.caballero.tictactoe.components.Position;

import java.util.Random;

public class RandomTicTacToeAI extends TicTacToeAi{

    private static final String TAG = "RandomTicTacToeAI";

    public static final int MIN = 0;
    public static final int MAX = 3;

    public RandomTicTacToeAI(int playerHuman, int playerComputer) {
        super(playerHuman, playerComputer);
    }

    @Override
    public void makeMove(String[][] board) {
        Position position = new Position(getRow(), getCol());
        moveListenerResult(position);
    }

    private int getRow() {
        return new Random().nextInt(MAX - MIN) + MIN;
    }

    private int getCol() {
        return new Random().nextInt(MAX - MIN) + MIN;
    }

}
