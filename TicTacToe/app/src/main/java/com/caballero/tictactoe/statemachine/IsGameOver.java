package com.caballero.tictactoe.statemachine;

import android.util.Log;
import android.view.View;

import com.caballero.tictactoe.TicTacToeActivity;

public class IsGameOver implements TicTacToeState {

    private static final String TAG = "statemachine";

    TicTacToeMachine ticTacToeMachine;

    public IsGameOver(TicTacToeMachine ticTacToeMachine) {
        this.ticTacToeMachine = ticTacToeMachine;
    }

    @Override
    public void idle() {
        Log.d(TAG, "idle: ");
    }

    @Override
    public void makeMove(View view) {
        Log.d(TAG, "makeMove: evaluating board");
    }

    @Override
    public void evaluateMove(View view) {
        Log.d(TAG, "evaluateMove: evaluating move");
    }

    @Override
    public void evaluateBoard() {
        TicTacToeActivity activity = ticTacToeMachine.getWeakReference().get();
        ticTacToeMachine.setTurn(ticTacToeMachine.getTurn() + 1);
        int winner = activity.evaluateBoard();
        if (winner >= 0) {
            Log.d(TAG, "evaluateBoard: game over!");
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getGameOver());
            ticTacToeMachine.gameOver(winner);
        } else {
            Log.d(TAG, "evaluateBoard: ");
            ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getIdleState());
        }
        ticTacToeMachine.setPlayerTurn(!ticTacToeMachine.isPlayerTurn());
        activity.setBoardValues();
    }

    @Override
    public void gameOver(int winner) {
        Log.d(TAG, "gameOver: evaluating move");
    }
}
