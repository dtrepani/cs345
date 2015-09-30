package com.example.dtrepani.inclass02;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class InClass02 extends AppCompatActivity {

    private static final String TAG = "InClass02";

    private Button buttonTest, buttonClear;
    private TextView textTest, textAvg;
    private SeekBar seekBarDelay;

    private double startTime, endTime, avgTime;
    private int numOfTests, progress, maxProgress, minProgress, stepSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02);

        loadPreferences();
        initWidgets();
        setupSeekBar();

        if(avgTime == 0) textAvg.setVisibility(View.INVISIBLE);
        else textAvg.setText(String.format(getString(R.string.text_avg), avgTime));

        setButtonOnClickListeners();
    }

    private void loadPreferences() {
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        numOfTests = settings.getInt("numOfTests", 0);
        progress = settings.getInt("progress", 0);
        avgTime = getDouble(settings, "avgTime", 0);
    }

    private void initWidgets() {
        buttonTest = (Button)findViewById(R.id.button_test);
        buttonClear = (Button)findViewById(R.id.button_clear);
        textTest = (TextView)findViewById(R.id.text_test);
        textAvg = (TextView)findViewById(R.id.text_avg);
        seekBarDelay = (SeekBar)findViewById(R.id.seekbar_delay);
    }

    private void setupSeekBar() {
        maxProgress = 2000;
        minProgress = 500;
        stepSize = 100;
        seekBarDelay.setMax((maxProgress - minProgress) / stepSize);
        seekBarDelay.setProgress(progress);
    }

    private void setButtonOnClickListeners() {
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((buttonTest.getText().equals(getString(R.string.button_test))))
                    startTest();
                else
                    endTest();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avgTime = 0;
                numOfTests = 0;
                textAvg.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void startTest() {
        progress = seekBarDelay.getProgress();
        Random rand = new Random();
        int     minRandVal = minProgress + (seekBarDelay.getProgress() * stepSize),
                maxRandVal = minRandVal * 4,
                randNum = rand.nextInt(maxRandVal - minRandVal) + minRandVal;

        Log.d(TAG, "minRandVal: " + minRandVal + ", maxRandVal: " + maxRandVal + ", raw progress: " + seekBarDelay.getProgress() + ", randNum: " + randNum);

        textTest.setText(R.string.text_stop);
        buttonTest.setEnabled(false);

        buttonTest.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonTest.setText(R.string.button_stop);
                buttonTest.setEnabled(true);
                startTime = System.currentTimeMillis();
            }
        }, randNum);
    }

    private void endTest() {
        endTime = System.currentTimeMillis();
        double time = (endTime - startTime) / 1000;

        numOfTests++;
        calculateAverage(time);

        buttonTest.setText(R.string.button_test);
        textTest.setText(String.format(getString(R.string.text_time), time));
        textAvg.setText(String.format(getString(R.string.text_avg), avgTime));
        textAvg.setVisibility(View.VISIBLE);
    }

    // Calculates the running average without keeping track of previous values.
    private void calculateAverage(double time) {
        avgTime -= avgTime / numOfTests;
        avgTime += time / numOfTests;
    }

    // SharedPreferences doesn't support storage of doubles, so it's necessary to store the double in its converted long form.
    SharedPreferences.Editor putDouble(final SharedPreferences.Editor editor, final String key, final double value) {
        return editor.putLong(key, Double.doubleToRawLongBits(value));
    }

    // Need to convert the double (stored in long form) back to a double.
    double getDouble(final SharedPreferences settings, final String key, final double defaultValue) {
        return Double.longBitsToDouble(settings.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        putDouble(editor, "avgTime", avgTime);
        editor
                .putInt("numOfTests", numOfTests)
                .putInt("progress", progress)
                .apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_in_class02, menu);
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
