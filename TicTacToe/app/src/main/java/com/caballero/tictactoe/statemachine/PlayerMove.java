package com.caballero.tictactoe.statemachine;

import android.graphics.Point;
import android.util.Log;
import android.view.View;

public class PlayerMove implements TicTacToeState {

    private static final String TAG = "statemachine";

    TicTacToeMachine ticTacToeMachine;

    public PlayerMove(TicTacToeMachine ticTacToeMachine) {
        this.ticTacToeMachine = ticTacToeMachine;
    }

    @Override
    public void idle() {
        Log.d(TAG, "idle: ");
    }

    @Override
    public void makeMove(Point point) {
        Log.d(TAG, "makeMove: player move");
        ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getLegalMove());
        ticTacToeMachine.evaluateMove(point);
    }

    @Override
    public void evaluateMove(Point point) {
        Log.d(TAG, "evaluateMove: make move first");
    }

    @Override
    public void evaluateBoard() {
        Log.d(TAG, "evaluateBoard: make move first");
    }

    @Override
    public void gameOver(int winner) {
        Log.d(TAG, "gameOver: make move first");
    }
}
