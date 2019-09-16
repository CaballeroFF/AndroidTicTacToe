package com.caballero.tictactoe;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.caballero.tictactoe.ai.HardTicTacToeAi;
import com.caballero.tictactoe.ai.MediumTicTacToeAi;
import com.caballero.tictactoe.ai.RandomTicTacToeAI;
import com.caballero.tictactoe.ai.TicTacToeAi;
import com.caballero.tictactoe.statemachine.TicTacToeMachine;
import com.caballero.tictactoe.util.BoardView;
import com.caballero.tictactoe.util.CustomDialog;
import com.caballero.tictactoe.util.LineView;

public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener, CustomDialog.OnClickListener {

    public static final int COLS = 3;
    public static final int ROWS = 3;
    public static final String EMPTY_VALUE = "empty";
    public static final String PLAYER1_TURN = "com.caballero.tictactoe.player1.turn";
    public static final String TURN = "com.caballero.tictactoe.turn.key";
    public static final String TURN_IMG = "com.caballero.tictactoe.turn.image";
    public static final String PLAYER1_SCORE = "com.caballero.tictactoe.player1.score";
    public static final String PLAYER2_SCORE = "com.caballero.tictactoe.player2.score";
    public static final String BOARD_VALUES1 = "com.caballero.tictactoe.board.values1";
    public static final String BOARD_VALUES2 = "com.caballero.tictactoe.board.values2";
    public static final String BOARD_VALUES3 = "com.caballero.tictactoe.board.values3";

    private static final String TAG = "TicTacToeActivity";

    private LineView winLine;
    private ImageView[][] imageViews = new ImageView[ROWS][COLS];
    private String[][] boardValues = new String[ROWS][COLS];
    private ImageView turnImage;
    private ImageView player1Img;
    private ImageView player2Img;
    private TextView player1Text;
    private TextView player2Text;
    private TextView playerStatus;
    private TextView difficultyStatus;
    private Button resetButton;
    private BoardView boardView;

    private TicTacToeMachine machine;
    private TicTacToeAi ai;

    private String winType = LineView.EMPTY;
    private String difficulty;

    private boolean singlePlayer;
    private boolean player1Turn;
    private boolean endGame;
    private boolean resetToggle;
    private int player1Score;
    private int player2Score;
    private int playerOneImgRes;
    private int playerTwoImgRes;
    private int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        machine = new TicTacToeMachine(this);
        machine.startMachine();

        Intent intent = getIntent();
        singlePlayer = intent.getBooleanExtra(MainActivity.PLAYER_EXTRA, false);
        playerOneImgRes = intent.getIntExtra(MainActivity.PLAYER_ONE_EXTRA, R.drawable.ic_dot);
        playerTwoImgRes = intent.getIntExtra(MainActivity.PLAYER_TWO_EXTRA, R.drawable.ic_x);
        difficulty = intent.getStringExtra(MainActivity.DIFFICULTY_EXTRA);

        ai = getAi(playerOneImgRes, playerTwoImgRes, difficulty);

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setViewClickListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        winLine.setDrawingFinishedListener(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PLAYER1_TURN, player1Turn);
        outState.putInt(TURN, turn);
        outState.putInt(TURN_IMG, getTurnImage(player1Turn));
        outState.putInt(PLAYER1_SCORE, player1Score);
        outState.putInt(PLAYER2_SCORE, player2Score);
        outState.putStringArray(BOARD_VALUES1, boardValues[0]);
        outState.putStringArray(BOARD_VALUES2, boardValues[1]);
        outState.putStringArray(BOARD_VALUES3, boardValues[2]);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        player1Turn = savedInstanceState.getBoolean(PLAYER1_TURN, true);
        turn = savedInstanceState.getInt(TURN, 0);
        player1Score = savedInstanceState.getInt(PLAYER1_SCORE, 0);
        player2Score = savedInstanceState.getInt(PLAYER2_SCORE, 0);
        boardValues[0] = savedInstanceState.getStringArray(BOARD_VALUES1);
        boardValues[1] = savedInstanceState.getStringArray(BOARD_VALUES2);
        boardValues[2] = savedInstanceState.getStringArray(BOARD_VALUES3);
        turnImage.setImageResource(savedInstanceState.getInt(TURN_IMG, R.drawable.ic_dot));
        updateScores();
        restoreBoardValues();
    }

    @Override
    public void onClick(View v) {
        machine.makeMove(v);
    }

    @Override
    public void noClicked() {
        //resetGame();
        finish();
    }

    @Override
    public void yesClicked() {
        resetBoard();
    }

    private void initViews() {
        playerStatus = findViewById(R.id.single_player_status);
        difficultyStatus = findViewById(R.id.difficulty_status);
        player1Text = findViewById(R.id.player1);
        player2Text = findViewById(R.id.player2);
        resetButton = findViewById(R.id.reset_button);
        turnImage = findViewById(R.id.turn_image);
        player1Img = findViewById(R.id.player1_image);
        player2Img = findViewById(R.id.player2_image);
        winLine = findViewById(R.id.win_line);
        winLine.setStrokeWidth(20f);

        boardView = findViewById(R.id.board_view);
        boardView.startTouchListener();
        boardView.draw();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String imageId = "button_" + row + col;
                int resId = getResources().getIdentifier(imageId, "id", getPackageName());

                boardValues[row][col] = EMPTY_VALUE;
                imageViews[row][col] = findViewById(resId);
            }
        }

        player1Img.setImageResource(playerOneImgRes);
        player2Img.setImageResource(playerTwoImgRes);
        turnImage.setImageResource(playerOneImgRes);
        player1Turn = true;
        turn = 0;
        player1Score = 0;
        player2Score = 0;

        updatePlayerStatus();
        updateScores();
    }

    private void setViewClickListeners() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                imageViews[row][col].setOnClickListener(this);
            }
        }
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resetToggle) {
                    winLine.setLine(LineView.RIGHT_COLUMN);
                    winLine.draw();
                    resetToggle = true;
                } else {
                    winLine.clearLine();
                    resetToggle = false;
                }
            }
        });

        boardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String piece = "o";

                boardView.drawPiece(boardView.getTicTacToeTile(), piece);
            }
        });

        ai.setOnMoveMadeListener(new TicTacToeAi.OnMoveListener() {
            @Override
            public void moveResults(Point position) {
                machine.makeMove(imageViews[position.x][position.y]);
            }
        });
    }

    private void drawWinLine(String type) {
        winLine.setLine(type);
        winLine.draw();
    }

    private void clearWinLine() {
        winType = LineView.EMPTY;
        winLine.setDrawingFinishedListener(null);
        winLine.clearLine();
    }

    public void setBoardValues() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String tag = imageViews[row][col].getTag().toString();
                boardValues[row][col] = tag;
            }
        }
    }

    private int getTurnImage(boolean playerTurn) {
        if (playerTurn) {
            return playerOneImgRes;
        }
        return playerTwoImgRes;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private void updatePlayerStatus() {
        String playerStatusString = "Two Players";
        difficultyStatus.setVisibility(View.INVISIBLE);
        if (singlePlayer) {
            playerStatusString = "Single Player";
            difficultyStatus.setText(difficulty);
            difficultyStatus.setVisibility(View.VISIBLE);
        }
        playerStatus.setText(playerStatusString);
    }

    public void updateScores() {
        player1Text.setText(String.valueOf(player1Score));
        player2Text.setText(String.valueOf(player2Score));
    }

    public void updateUI(View view) {
        int tileImg = playerTwoImgRes;
        int turnImg = playerOneImgRes;
        if (player1Turn) {
            tileImg = playerOneImgRes;
            turnImg = playerTwoImgRes;
        }
        ((ImageView) view).setImageResource(tileImg);
        view.setTag(tileImg);
        turnImage.setImageResource(turnImg);
    }

    public boolean isLegalMove(View view) {
        return view.getTag().toString().equals(EMPTY_VALUE);
    }

    public int evaluateBoard() {
        if (evaluateForWin()) {
            if (player1Turn) {
                player1Wins();
                return 1;
            } else {
                player2Wins();
                return 2;
            }
        } else if (turn >= 9) {
            return 0;
        }
        return -1;
    }

    private void player1Wins() {
        player1Score++;
        updateScores();
    }

    private void player2Wins() {
        player2Score++;
        updateScores();
    }

    private void setRowWinType(int row) {
        switch (row) {
            case 0:
                winType = LineView.TOP_ROW;
                break;
            case 1:
                winType = LineView.MIDDLE_ROW;
                break;
            case 2:
                winType = LineView.BOTTOM_ROW;
                break;
            default:
                winType = LineView.EMPTY;
                break;
        }
    }

    private void setColumnWinType(int col) {
        switch (col) {
            case 0:
                winType = LineView.LEFT_COLUMN;
                break;
            case 1:
                winType = LineView.MIDDLE_COLUMN;
                break;
            case 2:
                winType = LineView.RIGHT_COLUMN;
                break;
            default:
                winType = LineView.EMPTY;
                break;
        }
    }

    private boolean evaluateForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = imageViews[i][j].getTag().toString();
            }
        }
        //rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals(EMPTY_VALUE)) {
                setRowWinType(i);
                return true;
            }
        }
        //columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(EMPTY_VALUE)) {
                setColumnWinType(i);
                return true;
            }
        }
        //diagonal
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals(EMPTY_VALUE)) {
            winType = LineView.TOP_LEFT_DIAGONAL;
            return true;
        }
        //diagonal
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals(EMPTY_VALUE)) {
            winType = LineView.TOP_RIGHT_DIAGONAL;
            return true;
        }
        return false;
    }

    public void gameOver(final int winner) {
        endGame = true;
        drawWinLine(winType);
        winLine.setDrawingFinishedListener(new LineView.DrawingFinishedListener() {
            @Override
            public void finishedDrawing() {
                String winMsg = "";
                if (winner == 0) {
                    winMsg = "Draw";
                } else if (winner == 1) {
                    winMsg = "Player 1 wins!!";
                } else if (winner == 2) {
                    winMsg = "Player 2 wins!!";
                }
                Bundle bundle = new Bundle();
                bundle.putString(CustomDialog.DIALOG_TITLE, "Game Over");
                bundle.putString(CustomDialog.DIALOG_MESSAGE, winMsg + "\nPlay again?");
                CustomDialog customDialog = new CustomDialog();
                customDialog.setArguments(bundle);
                customDialog.setCancelable(false);
                customDialog.show(getSupportFragmentManager(), "custom dialog");
            }
        });
    }

    private void resetBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boardValues[row][col] = EMPTY_VALUE;
                imageViews[row][col].setImageResource(R.drawable.ic_placeholder);
                imageViews[row][col].setTag(EMPTY_VALUE);
            }
        }
        clearWinLine();
        turnImage.setImageResource(playerOneImgRes);
        turn = 0;
        player1Turn = true;
        endGame = false;
        machine.setTicTacToeState(machine.getIdleState());
    }

    private void resetGame() {
        resetBoard();
        player1Score = 0;
        player2Score = 0;
        updateScores();
    }

    private void restoreBoardValues() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String stringId = boardValues[row][col];
                imageViews[row][col].setTag(stringId);
                if (!stringId.equals(EMPTY_VALUE)) {
                    int resId = Integer.parseInt(stringId);
                    imageViews[row][col].setImageResource(resId);
                } else {
                    imageViews[row][col].setImageResource(R.drawable.ic_placeholder);
                }
            }
        }
    }

    public void aiMakeMove() {
        ai.makeMove(boardValues);
    }

    private Point getViewPosition(int id) {
        Point position = new Point(0, 0);
        switch (id) {
            case R.id.button_00:
                position.set(0, 0);
                break;
            case R.id.button_01:
                position.set(0, 1);
                break;
            case R.id.button_02:
                position.set(0, 2);
                break;
            case R.id.button_10:
                position.set(1, 0);
                break;
            case R.id.button_11:
                position.set(1, 1);
                break;
            case R.id.button_12:
                position.set(1, 2);
                break;
            case R.id.button_20:
                position.set(2, 0);
                break;
            case R.id.button_21:
                position.set(2, 1);
                break;
            case R.id.button_22:
                position.set(2, 2);
                break;
        }
        return position;
    }

    private TicTacToeAi getAi(int playerOne, int playerTwo, String difficulty) {
        switch (difficulty) {
            case MainActivity.EASY_DIFFICULTY:
                return new RandomTicTacToeAI(playerOne, playerTwo);
            case MainActivity.MEDIUM_DIFFICULTY:
                return new MediumTicTacToeAi(playerOne, playerTwo);
            case MainActivity.HARD_DIFFICULTY:
                return new HardTicTacToeAi(playerOne, playerTwo);
            default:
                return new RandomTicTacToeAI(playerOne, playerTwo);
        }
    }
}
