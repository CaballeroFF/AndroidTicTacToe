package com.caballero.tictactoe;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.caballero.tictactoe.adapters.SpinnerAdapter;
import com.caballero.tictactoe.util.BoardView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String PLAYER_EXTRA = "com.caballero.tictactoe.extra.player";
    public static final String DIFFICULTY_EXTRA = "com.caballero.tictactoe.extra.difficulty";
    public static final String PLAYER_ONE_EXTRA = "com.caballero.tictactoe.extra.player1";
    public static final String PLAYER_TWO_EXTRA = "com.caballero.tictactoe.extra.player2";
    public static final String EMPTY_SELECTION = "--Select--";
    public static final String SINGLE_PLAYER = "Single Player";
    public static final String TWO_PLAYERS = "Two Players";
    public static final String EASY_DIFFICULTY = "Easy";
    public static final String MEDIUM_DIFFICULTY = "Medium";
    public static final String HARD_DIFFICULTY = "Hard";
    private static final String[] PLAYERS = {EMPTY_SELECTION, SINGLE_PLAYER, TWO_PLAYERS};
    private static final String[] DIFFICULTY = {EMPTY_SELECTION, EASY_DIFFICULTY, MEDIUM_DIFFICULTY, HARD_DIFFICULTY};
    public static final int[] PLAYER_IMAGES = {R.drawable.ic_dot, R.drawable.ic_x};

    private Button buttonStartGame;
    private Spinner spinnerPlayerImage;
    private Spinner spinnerPlayerTwoImage;
    private Spinner spinnerPlayers;
    private Spinner spinnerDifficulty;
    private TextView difficultyLiteral;
    private TextView playerTwoLiteral;
    private BoardView boardView;

    private boolean isSinglePlayer;

    private boolean isPieceX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setListeners();
    }


    private void initViews() {
        buttonStartGame = findViewById(R.id.start_game_button);
        spinnerPlayerTwoImage = findViewById(R.id.main_image_spinner_player_2);
        spinnerPlayerImage = findViewById(R.id.main_image_spinner);
        spinnerPlayers = findViewById(R.id.main_spinner_players);
        spinnerDifficulty = findViewById(R.id.main_spinner_difficulty);
        difficultyLiteral = findViewById(R.id.difficulty_literal);
        playerTwoLiteral = findViewById(R.id.main_player2_image_literal);

        boardView = findViewById(R.id.board_view);
        boardView.setStrokeWidth(30f);
        boardView.startTouchListener();
        boardView.draw();

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, PLAYER_IMAGES);
        spinnerPlayerImage.setAdapter(spinnerAdapter);
        spinnerPlayerImage.setSelection(0);

        SpinnerAdapter spinnerAdapterTwo = new SpinnerAdapter(this, PLAYER_IMAGES);
        spinnerPlayerTwoImage.setAdapter(spinnerAdapterTwo);
        spinnerPlayerTwoImage.setSelection(1);

        ArrayAdapter<String> adapterPlayer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PLAYERS);
        adapterPlayer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayers.setAdapter(adapterPlayer);
        spinnerPlayers.setSelection(0);

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DIFFICULTY);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
        spinnerDifficulty.setSelection(0);
    }

    private void setListeners() {
        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        spinnerPlayers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isSinglePlayer = !TWO_PLAYERS.equals(parent.getSelectedItem().toString());
                setDifficultyVisibility(isSinglePlayer);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerPlayerImage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setPlayerImage(spinnerPlayerTwoImage, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPlayerTwoImage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setPlayerImage(spinnerPlayerImage, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        boardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String piece;
                if (isPieceX) {
                    piece = BoardView.X;
                    isPieceX = false;
                } else {
                    piece = BoardView.O;
                    isPieceX = true;
                }
                boardView.drawPiece(boardView.getTicTacToeTile(), piece);
            }
        });

    }

    private void startGame() {
        int playerOneImg = Integer.parseInt(spinnerPlayerImage.getSelectedItem().toString());
        int playerTwoImg = Integer.parseInt(spinnerPlayerTwoImage.getSelectedItem().toString());
        String difficulty = spinnerDifficulty.getSelectedItem().toString();
        if (MainActivity.EMPTY_SELECTION.equals(difficulty)) {
            difficulty = MainActivity.EASY_DIFFICULTY;
        }
        Intent intentStartGame = new Intent(MainActivity.this, TicTacToeActivity.class);
        intentStartGame.putExtra(PLAYER_ONE_EXTRA, playerOneImg);
        intentStartGame.putExtra(PLAYER_TWO_EXTRA, playerTwoImg);
        intentStartGame.putExtra(PLAYER_EXTRA, isSinglePlayer);
        intentStartGame.putExtra(DIFFICULTY_EXTRA, difficulty);
        startActivity(intentStartGame);
    }

    private void setDifficultyVisibility(boolean visible) {
        if (visible) {
            difficultyLiteral.setVisibility(View.VISIBLE);
            spinnerDifficulty.setVisibility(View.VISIBLE);

            spinnerPlayerTwoImage.setVisibility(View.INVISIBLE);
            playerTwoLiteral.setVisibility(View.INVISIBLE);
        } else {
            difficultyLiteral.setVisibility(View.INVISIBLE);
            spinnerDifficulty.setVisibility(View.INVISIBLE);

            spinnerPlayerTwoImage.setVisibility(View.VISIBLE);
            playerTwoLiteral.setVisibility(View.VISIBLE);
        }
    }

    private void setPlayerImage(Spinner spinner, int position) {
        if (position == 0) {
            spinner.setSelection(1);
        } else {
            spinner.setSelection(0);
        }
    }

    // TODO: 8/29/2019 make better title
    // TODO: 8/29/2019 make theme
    // TODO: 9/16/2019 clean boardview class
    // TODO: 9/16/2019 clean tictactoe activity class
    // TODO: 9/16/2019 clean tictactoe layout
}
