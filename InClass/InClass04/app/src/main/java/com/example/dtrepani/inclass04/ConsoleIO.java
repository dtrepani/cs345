package com.example.dtrepani.inclass04;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ConsoleIO extends AppCompatActivity {

    private static final String TERMINAL_FRAGMENT = "terminalFragment";
    private static ConsoleIO consoleIO;

    private RecyclerView mTerminalRecyclerView;
    private Button mEnterButton;
    private EditText mEditText;

    private List<String> mOutputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_support);

        consoleIO = this;
        mOutputs = new ArrayList<>();

        mEnterButton = (Button)findViewById(R.id.enter_button);
        mEditText = (EditText)findViewById(R.id.edit_text);
        mTerminalRecyclerView = (RecyclerView)findViewById(R.id.terminal_recycler_view);

        mTerminalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTerminalRecyclerView.setAdapter(new TerminalAdapter(mOutputs));

        FragmentManager fm = getSupportFragmentManager();
        Fragment mTerminalFragment = fm.findFragmentByTag(TERMINAL_FRAGMENT);

        if(mTerminalFragment == null) {
            mTerminalFragment = new TerminalFragment();
            mTerminalFragment.setRetainInstance(true);

            fm.beginTransaction()
                    .add(mTerminalFragment, TERMINAL_FRAGMENT)
                    .commit();
        }

        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: maybe move input and runnable to outside and set input in here
                final String input = mEditText.getText().toString();

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        printLine(input);
                    }
                };

                runOnUiThread(r);
            }
        });
    }

    static void printLine(String input) {
        consoleIO.mOutputs.add(input);
        consoleIO.mEditText.setText("");
        consoleIO.mTerminalRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_terminal_support, menu);
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
