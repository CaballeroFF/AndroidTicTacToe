package com.caballero.tictactoe.statemachine;

import android.util.Log;
import android.view.View;

public class IdleState implements TicTacToeState {

    private static final String TAG = "statemachine";

    TicTacToeMachine ticTacToeMachine;

    public IdleState(TicTacToeMachine ticTacToeMachine) {
        this.ticTacToeMachine = ticTacToeMachine;
    }

    @Override
    public void idle() {
        Log.d(TAG, "idle: ");
    }

    @Override
    public void makeMove(View view) {
        Log.d(TAG, "makeMove: idle");
        if (ticTacToeMachine.isPlayerTurn()) {
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getPlayerMove());
        } else {
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getAiMove());
        }
        ticTacToeMachine.makeMove(view);
    }

    @Override
    public void evaluateMove(View view) {
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
