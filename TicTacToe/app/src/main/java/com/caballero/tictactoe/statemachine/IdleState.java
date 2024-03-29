package com.caballero.tictactoe.statemachine;

import android.graphics.Point;
import android.util.Log;
import android.view.View;

import com.caballero.tictactoe.TicTacToeActivity;

public class IdleState implements TicTacToeState {

    private static final String TAG = "statemachine";

    private TicTacToeMachine ticTacToeMachine;

    public IdleState(TicTacToeMachine ticTacToeMachine) {
        this.ticTacToeMachine = ticTacToeMachine;
    }

    @Override
    public void idle() {
        Log.d(TAG, "idle: ");
        if (ticTacToeMachine.isSinglePlayer() && !ticTacToeMachine.isPlayerTurn()) {
            TicTacToeActivity activity = ticTacToeMachine.getWeakReference().get();
            activity.aiMakeMove();
        }
    }

    @Override
    public void makeMove(Point point) {
        Log.d(TAG, "makeMove: idle");
        if (ticTacToeMachine.isPlayerTurn()) {
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getPlayerMove());
        } else {
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getAiMove());
        }
        ticTacToeMachine.makeMove(point);
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
