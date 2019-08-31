package com.caballero.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String PLAYER_EXTRA = "com.caballero.tictactoe.extra.player";
    public static final String DIFFICULTY_EXTRA = "com.caballero.tictactoe.extra.difficulty";
    public static final String EMPTY_SELECTION = "--Select--";
    public static final String SINGLE_PLAYER = "Single Player";
    public static final String TWO_PLAYERS = "Two Players";
    public static final String EASY_DIFFICULTY = "Easy";
    private static final String[] PLAYERS = {EMPTY_SELECTION, SINGLE_PLAYER, TWO_PLAYERS};
    private static final String[] DIFFICULTY = {EMPTY_SELECTION, EASY_DIFFICULTY};

    private Button buttonStartGame;
    private Spinner spinnerPlayers;
    private Spinner spinnerDifficulty;

    private boolean isSinglePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        spinnerPlayers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (TWO_PLAYERS.equals(parent.getSelectedItem().toString())) {
                    isSinglePlayer = false;
                } else {
                    isSinglePlayer = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: " + parent.getSelectedItem().toString());
            }
        });
    }

    private void initViews() {
        buttonStartGame = findViewById(R.id.start_game_button);
        spinnerPlayers = findViewById(R.id.main_spinner_players);
        spinnerDifficulty = findViewById(R.id.main_spinner_difficulty);

        ArrayAdapter<String> adapterPlayer = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PLAYERS);
        adapterPlayer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayers.setAdapter(adapterPlayer);
        spinnerPlayers.setSelection(0);

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DIFFICULTY);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
        spinnerDifficulty.setSelection(0);
    }

    private void startGame() {
        Intent intentStartGame = new Intent(MainActivity.this, TicTacToeActivity.class);
        intentStartGame.putExtra(PLAYER_EXTRA, isSinglePlayer);
        intentStartGame.putExtra(DIFFICULTY_EXTRA, spinnerDifficulty.getSelectedItem().toString());
        startActivity(intentStartGame);
    }

    // TODO: 8/29/2019 customize button
    // TODO: 8/29/2019 send intent
    // TODO: 8/29/2019 make better title
    // TODO: 8/29/2019 make theme
    // TODO: 8/30/2019 player image select 
}
