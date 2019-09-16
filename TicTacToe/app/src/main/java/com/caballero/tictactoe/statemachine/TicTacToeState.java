package com.caballero.tictactoe.statemachine;

import android.graphics.Point;
import android.view.View;

public interface TicTacToeState {

    void idle();

    void makeMove(Point point);

    void evaluateMove(Point point);

    void evaluateBoard();

    void gameOver(int winner);
}
