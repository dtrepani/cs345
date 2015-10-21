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
    private static final String BUNDLE_OUTPUTS = "bundleOutputs";
    private static ConsoleIO consoleIO;

    private RecyclerView mTerminalRecyclerView;
    private Button mEnterButton;
    private EditText mEditText;

    private List<String> mOutputs;

    static void printLine(final String input) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                consoleIO.mOutputs.add(input);
                consoleIO.mEditText.setText("");
                consoleIO.mTerminalRecyclerView.getAdapter().notifyDataSetChanged();
            }
        };
        consoleIO.runOnUiThread(r);
    }

    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment mTerminalFragment = fm.findFragmentByTag(TERMINAL_FRAGMENT);

        if(mTerminalFragment == null) {
            mTerminalFragment = new TerminalFragment();
            mTerminalFragment.setRetainInstance(true);

            fm.beginTransaction()
                    .add(mTerminalFragment, TERMINAL_FRAGMENT)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_support);

        consoleIO = this;
        mOutputs = (savedInstanceState == null) ? new ArrayList<>() : (ArrayList)savedInstanceState.getStringArrayList(BUNDLE_OUTPUTS);
        mEnterButton = (Button)findViewById(R.id.enter_button);

        mEditText = (EditText)findViewById(R.id.edit_text);
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printLine(mEditText.getText().toString());
            }
        });

        mTerminalRecyclerView = (RecyclerView)findViewById(R.id.terminal_recycler_view);
        mTerminalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTerminalRecyclerView.setAdapter(new TerminalAdapter(mOutputs));

        createFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(BUNDLE_OUTPUTS, (ArrayList)mOutputs);
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
