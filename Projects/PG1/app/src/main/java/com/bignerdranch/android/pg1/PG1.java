package com.bignerdranch.android.pg1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PG1 extends AppCompatActivity {
    private static final String HIGH_SCORE = "score",
                                KEY_DIFFICULTY = "difficulty",
                                TAG = "PG1";
    private static final int GAME_ACTIVITY = 1;

    private Button mStartButton, mDifficultyButton;
    private TextView mHighScoreText;

    private int mHighScore;
    private boolean mDifficultyIsHard = false;

    // Difficulties are normal and hard and will switch between them, announcing the change via a Toast.
    private void setDifficulty() {
        mDifficultyIsHard = !mDifficultyIsHard;
        String difficulty = mDifficultyIsHard ? getString(R.string.difficulty_hard) : getString(R.string.difficulty_normal);

        Toast.makeText(getApplicationContext(), String.format(getString(R.string.difficulty_message), difficulty), Toast.LENGTH_SHORT).show();
    }

    // Set the onClickListeners for starting the game and changing the difficulty.
    private void setOnClickListeners() {
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(GameActivity.newIntent(PG1.this, mDifficultyIsHard), GAME_ACTIVITY);
            }
        });

        mDifficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDifficulty();
            }
        });
    }

    // Load the high score if previous records exist.
    private void loadPreferences() {
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        setHighScore(settings.getInt(HIGH_SCORE, 0));
    }

    // Will not check if given score is higher than the actual high score
    private void setHighScore(int score) {
        mHighScore = score;
        mHighScoreText.setText(String.format(getString(R.string.high_score), mHighScore));
    }

    // Dialog box that displays after the game finishes.
    // Lists the player's score and the current high score and gives an option to clear the high score.
    private void newAlertDialog(int score) {
        new AlertDialog.Builder(this)
                .setTitle( String.format(getString(R.string.score), score) )
                .setMessage( String.format(getString(R.string.high_score), mHighScore) )
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton(R.string.dialog_clear_high_score, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setHighScore(0);
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg1);

        mStartButton = (Button)findViewById(R.id.button_start);
        mDifficultyButton = (Button)findViewById(R.id.button_difficulty);
        mHighScoreText = (TextView)findViewById(R.id.text_high_score);

        mDifficultyIsHard = ( savedInstanceState != null ) ? savedInstanceState.getBoolean(KEY_DIFFICULTY, false) : false;

        loadPreferences();
        setOnClickListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if( requestCode == GAME_ACTIVITY && resultCode == RESULT_OK ) {
            int score = GameActivity.getScore(intent);

            if(score >= mHighScore) {
                setHighScore(score);
            }

            newAlertDialog(score);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor
                .putInt(HIGH_SCORE, mHighScore)
                .apply();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_DIFFICULTY, mDifficultyIsHard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pg1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
