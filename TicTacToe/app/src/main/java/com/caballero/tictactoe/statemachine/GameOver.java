package com.caballero.tictactoe.statemachine;

import android.graphics.Point;
import android.util.Log;
import android.view.View;

import com.caballero.tictactoe.TicTacToeActivity;

public class GameOver implements TicTacToeState {

    private static final String TAG = "statemachine";

    TicTacToeMachine ticTacToeMachine;

    public GameOver(TicTacToeMachine ticTacToeMachine) {
        this.ticTacToeMachine = ticTacToeMachine;
    }

    @Override
    public void idle() {
        Log.d(TAG, "idle: game over");
    }

    @Override
    public void makeMove(Point point) {
        Log.d(TAG, "makeMove: game over");
    }

    @Override
    public void evaluateMove(Point point) {
        Log.d(TAG, "evaluateMove: game over");
    }

    @Override
    public void evaluateBoard() {
        Log.d(TAG, "evaluateBoard: game over");
    }

    @Override
    public void gameOver(int winner) {
        TicTacToeActivity activity = ticTacToeMachine.getWeakReference().get();
        activity.gameOver(winner);
        Log.d(TAG, "gameOver: game over");
    }
}
