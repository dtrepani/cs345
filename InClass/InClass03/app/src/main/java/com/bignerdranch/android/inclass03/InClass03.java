package com.bignerdranch.android.inclass03;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InClass03 extends FragmentActivity {

    private static final String BURGER_FRAGMENT = "Burger Fragment";
    private static final String TOPPINGS_FRAGMENT = "Toppings Fragment";

    private FragmentManager fm = getSupportFragmentManager();
    private BurgerFragment burgerFragment = (BurgerFragment) fm.findFragmentByTag(BURGER_FRAGMENT);
    private ToppingsFragment toppingsFragment = (ToppingsFragment) fm.findFragmentByTag(TOPPINGS_FRAGMENT);

    private Button buttonBurger, buttonToppings;

    private void detachAnyDisplayFragments() {
        if( burgerFragment.isVisible() ) {
            fm.beginTransaction()
                    .detach(burgerFragment)
                    .commit();
        } else if( toppingsFragment.isVisible() ) {
            fm.beginTransaction()
                    .detach(toppingsFragment)
                    .commit();
        }
    }

    private void toggleFragments(String fragment) {
        // if fragment is in fm, call attach; otherwise add
        // is something being displayed? detach

        detachAnyDisplayFragments();

        // TODO: add toppings fragment
        if( fragment == BURGER_FRAGMENT ) {
            if( burgerFragment.isAdded() ) {
                fm.beginTransaction()
                        .attach(burgerFragment)
                        .commit();
            } else {
                fm.beginTransaction()
                        .add(R.id.fragment_holder, new BurgerFragment(), BURGER_FRAGMENT)
                        .commit();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class03);

        buttonBurger = (Button)findViewById(R.id.button_one);
        buttonToppings = (Button)findViewById(R.id.button_two);


        buttonBurger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (burgerFragment == null) {
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

        buttonToppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toppingsFragment == null) {
                    fm.beginTransaction()
                            .add(R.id.fragment_holder, new ToppingsFragment(), TOPPINGS_FRAGMENT)
                            .commit();
                } else {
                    fm.beginTransaction()
                            .remove(toppingsFragment)
                            .commit();
                }
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
