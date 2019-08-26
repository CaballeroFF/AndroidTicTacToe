package com.caballero.tictactoe.statemachine;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.caballero.tictactoe.TicTacToeActivity;

import java.util.Random;

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
    public void makeMove(View view) {
        Log.d(TAG, "makeMove: player 2 move");
//        ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getLegalMove());
//        ticTacToeMachine.evaluateMove(view);
        // TODO: 8/25/2019 make 2 player game 
        final View v = view;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ticTacToeMachine.setTicTacToeState(ticTacToeMachine.getLegalMove());
                ticTacToeMachine.evaluateMove(v);
            }
        }, 500);
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
