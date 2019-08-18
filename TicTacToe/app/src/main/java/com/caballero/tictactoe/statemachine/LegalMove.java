package com.caballero.tictactoe.statemachine;

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
    public void makeMove(View view) {
        Log.d(TAG, "makeMove: evaluating move");
    }

    @Override
    public void evaluateMove(View view) {
        TicTacToeActivity activity = ticTacToeMachine.getWeakReference().get();
        if (activity.isLegalMove(view)) {
            Log.d(TAG, "evaluateMove: legal");
            activity.updateUI(view);
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getEvaluateState());
            ticTacToeMachine.evaluateBoard();
        } else {
            Log.d(TAG, "evaluateMove: illegal move, try again.");
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getIdleState());
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
