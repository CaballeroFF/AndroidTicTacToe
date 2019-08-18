package com.caballero.tictactoe.statemachine;

import android.view.View;

public interface TicTacToeState {

    void idle();

    void makeMove(View view);

    void evaluateMove(View view);

    void evaluateBoard();

    void gameOver(int winner);
}
