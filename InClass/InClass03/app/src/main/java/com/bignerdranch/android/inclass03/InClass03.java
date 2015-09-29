package com.bignerdranch.android.inclass03;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InClass03 extends FragmentActivity {

    private static final String BURGER_FRAGMENT = "Burger Fragment";

    private Button buttonOne, buttonTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);

        buttonOne = (Button)findViewById(R.id.button_one);
        buttonTwo = (Button)findViewById(R.id.button_two);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();

                BurgerFragment burgerFragment = (BurgerFragment)fm.findFragmentById(R.id.fragment_holder);

                if(burgerFragment == null) {
                    fm.beginTransaction()
                            .add(R.id.fragment_holder, new BurgerFragment(), BURGER_FRAGMENT)
                            .commit();
                } else {
                    fm.beginTransaction()
                            .remove(burgerFragment)
                            .commit();
                }
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_in_class03, menu);
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
