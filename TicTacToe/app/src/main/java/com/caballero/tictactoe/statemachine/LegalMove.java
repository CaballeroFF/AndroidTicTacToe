package com.caballero.tictactoe.statemachine;

import android.graphics.Point;
import android.util.Log;
import android.view.View;

import com.caballero.tictactoe.TicTacToeActivity;

public class LegalMove implements TicTacToeState {

    private static final String TAG = "statemachine";

    TicTacToeMachine ticTacToeMachine;

    public LegalMove(TicTacToeMachine ticTacToeMachine) {
        this.ticTacToeMachine = ticTacToeMachine;
    }

    @Override
    public void idle() {
        Log.d(TAG, "idle: ");
    }

    @Override
    public void makeMove(Point point) {
        Log.d(TAG, "makeMove: evaluating move");
    }

    @Override
    public void evaluateMove(Point point) {
        TicTacToeActivity activity = ticTacToeMachine.getWeakReference().get();
        if (activity.isLegalMove(point)) {
            Log.d(TAG, "evaluateMove: legal");
            activity.updateUI(point);
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getEvaluateState());
            ticTacToeMachine.evaluateBoard();
        } else {
            Log.d(TAG, "evaluateMove: illegal move, try again.");
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getIdleState());
            ticTacToeMachine.idleState();
        }
    }

    @Override
    public void evaluateBoard() {
        Log.d(TAG, "evaluateBoard: evaluating move");
    }

    @Override
    public void gameOver(int winner) {
        Log.d(TAG, "gameOver: evaluating move");
    }
}
