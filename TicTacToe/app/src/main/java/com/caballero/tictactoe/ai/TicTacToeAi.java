package com.caballero.tictactoe.ai;

import com.caballero.tictactoe.components.Position;

public abstract class TicTacToeAi {

    private OnMoveListener listener;

    protected int playerHuman;
    protected int playerComputer;


    public TicTacToeAi(int playerHuman, int playerComputer) {
        this.playerHuman = playerHuman;
        this.playerComputer = playerComputer;
    }

    public abstract void makeMove(String[][] board);

    public void moveListenerResult(Position position) {
        if (listener != null) {
            listener.moveResults(position);
        }
    }



    public void setOnMoveMadeListener(OnMoveListener moveListener) {
        listener = moveListener;
    }

    public interface OnMoveListener {
        void moveResults(Position position);
    }
}
