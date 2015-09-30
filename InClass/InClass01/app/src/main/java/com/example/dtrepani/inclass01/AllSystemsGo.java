package com.example.dtrepani.inclass01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class AllSystemsGo extends AppCompatActivity {

    static final String BOOL_TOP = "strTop", BOOL_MIDDLE = "strMiddle", BOOL_BOTTOM = "strBottom";

    private Switch switchTop, switchMiddle, switchBottom;
    private TextView textAllOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_systems_go);

        switchTop = (Switch)findViewById(R.id.switch_top);
        switchMiddle = (Switch)findViewById(R.id.switch_middle);
        switchBottom = (Switch)findViewById(R.id.switch_bottom);

        textAllOn = (TextView)findViewById(R.id.text_all_on);

        switchTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchTextVisibility();
            }
        });

        switchMiddle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchTextVisibility();
            }
        });

        switchBottom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchTextVisibility();
            }
        });
    }

    private void switchTextVisibility() {
        if(switchTop.isChecked() && switchMiddle.isChecked() && switchBottom.isChecked()) {
            textAllOn.setVisibility(View.VISIBLE);
        } else {
            textAllOn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        switchTop.setChecked(savedInstanceState.getBoolean(BOOL_TOP));
        switchMiddle.setChecked(savedInstanceState.getBoolean(BOOL_MIDDLE));
        switchBottom.setChecked(savedInstanceState.getBoolean(BOOL_BOTTOM));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(BOOL_TOP, switchTop.isChecked());
        savedInstanceState.putBoolean(BOOL_MIDDLE, switchMiddle.isChecked());
        savedInstanceState.putBoolean(BOOL_BOTTOM, switchBottom.isChecked());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_systems_go, menu);
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
