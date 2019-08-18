package com.caballero.tictactoe.statemachine;

import android.util.Log;
import android.view.View;

import com.caballero.tictactoe.TicTacToeActivity;

import java.lang.ref.WeakReference;

public class TicTacToeMachine {

    private static final String TAG = "statemachine";
    public static final String IDLE_STATE = "com.caballero.tictactoe.statemachine.state.idle";
    public static final String PLAYER_STATE = "com.caballero.tictactoe.statemachine.state.player";
    public static final String AI_STATE = "com.caballero.tictactoe.statemachine.state.ai";
    public static final String LEGAL_MOVE_STATE = "com.caballero.tictactoe.statemachine.state.legal.move";
    public static final String EVALUATE_BOARD_STATE = "com.caballero.tictactoe.statemachine.state.evaluate.board";
    public static final String GAME_OVER_STATE = "com.caballero.tictactoe.statemachine.state.game.over";

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

    public void makeMove(View view) {
        ticTacToeState.makeMove(view);
    }

    public void evaluateMove(View view) {
        ticTacToeState.evaluateMove(view);
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
