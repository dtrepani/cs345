package com.example.dtrepani.finalexam;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class FinalExam extends AppCompatActivity {

    private static final String AVERAGE_DIALOG = "AverageDialog";

    int numberOfEntries;
    private Button mButtonAdd, mButtonAverage, mButtonEducat;
    private EditText mEditText;
    private SQLiteDatabase mDatabase;

    private void insert(String number) {
        ContentValues numberEntry = new ContentValues();
        numberEntry.put("number", number);
        mDatabase.insert("Number", null, numberEntry);
        numberOfEntries++;
    }

    private double getAverage() {
        double total = 0;
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Number", null);

        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                total += Double.parseDouble(cursor.getString(cursor.getColumnIndex("Number")));
            }
        } finally {
            cursor.close();
        }

        return total/numberOfEntries;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_exam);

        mButtonAdd = (Button)findViewById(R.id.button_add);
        mButtonAverage = (Button)findViewById(R.id.button_average);
        mButtonEducat = (Button)findViewById(R.id.button_educat);
        mEditText = (EditText)findViewById(R.id.edit_text);
        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(mEditText.getText().toString());
                mEditText.setText("");
            }
        });

        mButtonAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AverageDialogFragment dialog = AverageDialogFragment.newInstance(getAverage());
                dialog.show(fm, AVERAGE_DIALOG);
            }
        });

        mButtonEducat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://educat.nmu.edu");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final_exam, menu);
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
