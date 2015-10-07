package com.example.dtrepani.inclass04;

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

public class TerminalSupport extends AppCompatActivity {

    private RecyclerView mTerminalRecyclerView;
    private Button mEnterButton;
    private EditText mEditText;

    private List<String> mOutputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_support);

        mOutputs = new ArrayList<>();

        mEnterButton = (Button)findViewById(R.id.enter_button);
        mEditText = (EditText)findViewById(R.id.edit_text);
        mTerminalRecyclerView = (RecyclerView)findViewById(R.id.terminal_recycler_view);

        mTerminalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTerminalRecyclerView.setAdapter(new TerminalAdapter(mOutputs));

        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOutputs.add(mEditText.getText().toString());
                mEditText.setText("");
            }
        });
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
