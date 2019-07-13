package com.caballero.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int COLS = 3;
    public static final int ROWS = 3;
    public static final String EMPTY = "empty";
    public static final String PLAYER1_TURN = "com.caballero.tictactoe.player1.turn";
    public static final String TURN = "com.caballero.tictactoe.player1.turn";
    public static final String PLAYER1_SCORE = "com.caballero.tictactoe.player1.turn";
    public static final String PLAYER2_SCORE = "com.caballero.tictactoe.player1.turn";

    private ImageView[][] imageViews = new ImageView[3][3];
    private ImageView turnImage, player1Img, player2Img;
    private TextView player1Text, player2Text;
    private Button resetButton;

    private boolean player1Turn;
    private int player1Score;
    private int player2Score;
    private int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PLAYER1_TURN, player1Turn);
        outState.putInt(TURN, turn);
        outState.putInt(PLAYER1_SCORE, player1Score);
        outState.putInt(PLAYER2_SCORE, player2Score);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        player1Turn = savedInstanceState.getBoolean(PLAYER1_SCORE, true);
        turn = savedInstanceState.getInt(TURN, 0);
        player1Score = savedInstanceState.getInt(PLAYER1_SCORE, 0);
        player2Score = savedInstanceState.getInt(PLAYER2_SCORE, 0);
    }

    private void initViews() {
        player1Text = findViewById(R.id.player1);
        player2Text = findViewById(R.id.player2);
        resetButton = findViewById(R.id.reset_button);
        turnImage = findViewById(R.id.turn_image);
        player1Img = findViewById(R.id.player1_image);
        player2Img = findViewById(R.id.player2_image);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String imageId = "button_" + row + col;
                int resId = getResources().getIdentifier(imageId, "id", getPackageName());

                imageViews[row][col] = findViewById(resId);
                imageViews[row][col].setOnClickListener(this);
            }
        }

        player1Img.setImageResource(R.drawable.ic_dot);
        player2Img.setImageResource(R.drawable.ic_x);
        turnImage.setImageResource(R.drawable.ic_dot);
        player1Turn = true;
        turn = 0;
        player1Score = 0;
        player2Score = 0;

        updateScores();
    }

    @Override
    public void onClick(View v) {
        if (isLegalMove(v)) {
            if (player1Turn) {
                ((ImageView) v).setImageResource(R.drawable.ic_dot);
                v.setTag(R.drawable.ic_dot);
                turnImage.setImageResource(R.drawable.ic_x);
            } else {
                ((ImageView) v).setImageResource(R.drawable.ic_x);
                v.setTag(R.drawable.ic_x);
                turnImage.setImageResource(R.drawable.ic_dot);
            }
            turn++;
            evaluateBoard();
            player1Turn = !player1Turn;
        }
    }

    private void evaluateBoard() {

        if (evaluateForWin()) {

            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }

        } else if (turn >= 9) {
            draw();
        }

    }

    private boolean isLegalMove(View view) {
        return view.getTag().toString().equals(EMPTY);
    }

    private void resetGame() {
        resetBoard();
        player1Score = 0;
        player2Score = 0;
        updateScores();
    }

    private void resetBoard() {
        for (ImageView[] views : imageViews) {
            for (ImageView view : views) {
                view.setImageResource(R.drawable.ic_placeholder);
                view.setTag(EMPTY);
            }
        }
        turnImage.setImageResource(R.drawable.ic_dot);
        turn = 0;
        player1Turn = true;
    }

    private void updateScores() {
        player1Text.setText(String.valueOf(player1Score));
        player2Text.setText(String.valueOf(player2Score));
    }

    private void draw() {
        gameOver("Draw");
    }

    private void player1Wins() {
        player1Score++;
        updateScores();
        gameOver("Player 1 wins!!");
    }

    private void player2Wins() {
        player2Score++;
        updateScores();
        gameOver("Player 2 wins!!");
    }

    private boolean evaluateForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = imageViews[i][j].getTag().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals(EMPTY)) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(EMPTY)) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals(EMPTY)) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals(EMPTY)) {
            return true;
        }
        return false;
    }

    private void gameOver(String winMsg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Game Over");
        dialog.setMessage(winMsg + "\nPlay again?");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetBoard();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });
        dialog.create().show();
    }

    // TODO: 7/13/2019 saved state 
    // TODO: 7/13/2019 landscape 
    // TODO: 7/13/2019 AI 
}
