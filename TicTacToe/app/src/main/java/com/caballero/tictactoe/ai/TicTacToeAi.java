package com.caballero.tictactoe.ai;

public abstract class TicTacToeAi {

    private OnMoveListener listener;

    public abstract void makeMove();

    public void moveListenerResult(int row, int col) {
        if (listener != null) {
            listener.moveResults(row, col);
        }
    }

    public void setOnMoveMadeListener(OnMoveListener moveListener) {
        listener = moveListener;
    }

    public interface OnMoveListener {
        void moveResults(int row, int col);
    }
}
