package com.caballero.tictactoe.statemachine;

import android.graphics.Point;
import android.util.Log;
import android.view.View;

import com.caballero.tictactoe.TicTacToeActivity;

import java.lang.ref.WeakReference;

public class TicTacToeMachine {

    private static final String TAG = "statemachine";

    private TicTacToeState idleState;
    private TicTacToeState playerMove;
    private TicTacToeState aiMove;
    private TicTacToeState legalMove;
    private TicTacToeState isGameOver;
    private TicTacToeState gameOver;

    private TicTacToeState ticTacToeState;

    private WeakReference<TicTacToeActivity> weakReference;

    public TicTacToeMachine(TicTacToeActivity activity) {

        Log.d(TAG, "TicTacToeMachine: created");

        weakReference = new WeakReference<>(activity);
        idleState = new IdleState(this);
        playerMove = new PlayerMove(this);
        aiMove = new AiMove(this);
        legalMove = new LegalMove(this);
        isGameOver = new IsGameOver(this);
        gameOver = new GameOver(this);

        ticTacToeState = idleState;

    }

    public void startMachine() {
        idleState();
    }

    public boolean isSinglePlayer() {
        TicTacToeActivity activity = weakReference.get();
        return activity.isSinglePlayer();
    }

    public int getTurn() {
        TicTacToeActivity activity = weakReference.get();
        return activity.getTurn();
    }

    public void setTurn(int turn) {
        TicTacToeActivity activity = weakReference.get();
        activity.setTurn(turn);
    }

    public boolean isPlayerTurn() {
        TicTacToeActivity activity = weakReference.get();
        return activity.isPlayer1Turn();
    }

    public void setPlayerTurn(boolean playerTurn) {
        TicTacToeActivity activity = weakReference.get();
        activity.setPlayer1Turn(playerTurn);
    }

    public void idleState() {
        ticTacToeState.idle();
    }

    public void makeMove(Point point) {
        ticTacToeState.makeMove(point);
    }

    public void evaluateMove(Point point) {
        ticTacToeState.evaluateMove(point);
    }

    public void evaluateBoard() {
        ticTacToeState.evaluateBoard();
    }

    public void gameOver(int winner) {
        ticTacToeState.gameOver(winner);
    }

    public void setTicTacToeState(TicTacToeState ticTacToeState) {
        this.ticTacToeState = ticTacToeState;
    }

    public TicTacToeState getTicTacToeState() {
        return ticTacToeState;
    }

    public TicTacToeState getIdleState() {
        return idleState;
    }

    public TicTacToeState getPlayerMove() {
        return playerMove;
    }

    public TicTacToeState getAiMove() {
        return aiMove;
    }

    public TicTacToeState getLegalMove() {
        return legalMove;
    }

    public TicTacToeState getEvaluateState() {
        return isGameOver;
    }

    public TicTacToeState getGameOver() {
        return gameOver;
    }

    public WeakReference<TicTacToeActivity> getWeakReference() {
        return weakReference;
    }
}
