package com.example.dtrepani.exam01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Exam01 extends AppCompatActivity {

    private Button buttonSad, buttonMad, buttonHappy;
    private TextView textMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam01);

        buttonSad = (Button)findViewById(R.id.button_sad);
        buttonMad = (Button)findViewById(R.id.button_mad);
        buttonHappy = (Button)findViewById(R.id.button_happy);

        textMood = (TextView)findViewById(R.id.text_mood);

        buttonSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMood.setText(R.string.mood_sad);
            }
        });

        buttonMad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMood.setText(R.string.mood_mad);
            }
        });

        buttonHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMood.setText(R.string.mood_happy);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exam01, menu);
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
