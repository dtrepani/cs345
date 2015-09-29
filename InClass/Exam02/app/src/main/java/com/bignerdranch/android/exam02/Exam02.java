package com.bignerdranch.android.exam02;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class Exam02 extends AppCompatActivity {

    static final String STRING_DATE = "";

    private Button buttonCount;
    private TextView textButtonPresses, textAppStarted;
    private int count;
    private String date = (new Date()).toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam02);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        count = settings.getInt("count", 0);

        buttonCount = (Button)findViewById(R.id.button_count);
        textButtonPresses = (TextView)findViewById(R.id.text_presses);
        textAppStarted = (TextView)findViewById(R.id.text_started);

        textButtonPresses.setText(String.format(getString(R.string.button_presses), count));
        textAppStarted.setText(String.format(getString(R.string.app_started), date));

        buttonCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                textButtonPresses.setText(String.format(getString(R.string.button_presses), count));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        settings.edit()
                .putInt("count", count)
                .apply();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        date = savedInstanceState.getString(STRING_DATE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(STRING_DATE, date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exam02, menu);
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
