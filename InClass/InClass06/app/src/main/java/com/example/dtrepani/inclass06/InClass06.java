package com.example.dtrepani.inclass06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InClass06 extends AppCompatActivity {

    private static final String TAG = "InClass06";

    private TextView mTextView;
    private EditText mEditText;
    private Button mStartTaskButton, mDeleteTaskButton;

    private SQLiteDatabase mDatabase;

    private void insert(String taskName) {
        ContentValues name = new ContentValues();
        name.put("name", taskName);
        mDatabase.insert("Task", null, name);

        ContentValues timeStarted = new ContentValues();
        timeStarted.put("timeStarted", System.currentTimeMillis());
        Log.d(TAG, "" + System.currentTimeMillis());
        mDatabase.update("Task", timeStarted, "name = ?", new String[] {taskName});
    }

    private void updateUI() {
        updateTimeSpent();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Task", null);

        try {
            mTextView.setText("");
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                long timeStarted = cursor.getInt(cursor.getColumnIndex("timeStarted"));
                long timeSpent = cursor.getInt(cursor.getColumnIndex("timeSpent"));
                //DateFormat.format("hh:mm:ss", name);

                mTextView.append("Name: " + cursor.getString(cursor.getColumnIndex("name")) +
                                ", timeStarted: " + timeStarted +
                                ", timeSpent: " + timeSpent + "\n");
            }
        } finally {
            cursor.close();
        }
    }

    private int rawUpdate(String sq1, String[] args) {
        SQLiteStatement stmt = mDatabase.compileStatement(sq1);
        stmt.bindAllArgsAsStrings(args);
        return stmt.executeUpdateDelete();
    }

    private void updateTimeSpent() {
        rawUpdate("UPDATE Task SET timeSpent = timeSpent + ? - timeStarted", new String[] {"" + System.currentTimeMillis()});
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class05);

        mTextView = (TextView) findViewById(R.id.text_view);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mStartTaskButton = (Button) findViewById(R.id.button_start_task);
        mDeleteTaskButton = (Button) findViewById(R.id.button_delete_task);

        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();

        mStartTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(mEditText.getText().toString());
                mEditText.setText("");
                updateUI();
            }
        });

        mDeleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.delete("Task", "name = ?", new String[] { mEditText.getText().toString() });
                mEditText.setText("");
                updateUI();
            }
        });

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_in_class05, menu);
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
