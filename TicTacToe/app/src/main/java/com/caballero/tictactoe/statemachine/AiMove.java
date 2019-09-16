package com.caballero.tictactoe.statemachine;

import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class AiMove implements TicTacToeState {

    private static final String TAG = "statemachine";

    TicTacToeMachine ticTacToeMachine;

    public AiMove(TicTacToeMachine ticTacToeMachine) {
        this.ticTacToeMachine = ticTacToeMachine;
    }

    @Override
    public void idle() {
        Log.d(TAG, "idle: ");
    }

    @Override
    public void makeMove(Point point) {
        Log.d(TAG, "makeMove: player 2 move");

        long delay = ticTacToeMachine.isSinglePlayer() ? 500 : 0;
        final Point p = point;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getLegalMove());
                ticTacToeMachine.evaluateMove(p);
            }
        }, delay);
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
