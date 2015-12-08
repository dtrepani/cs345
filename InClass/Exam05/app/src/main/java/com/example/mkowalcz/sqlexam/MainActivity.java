package com.example.mkowalcz.sqlexam;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mEditTextTitle, mEditTextISBN;
    private Button mButtonInsert, mButtonUpdate, mButtonDelete;

    private SQLiteDatabase mDatabase;

    private void insert(String title, String isbn) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("isbn", Integer.parseInt(isbn));
        mDatabase.insert("Book", null, values);
    }

    private void updateUI() {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Book", null);

        try {
            mTextView.setText("");
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                mTextView.append(cursor.getString(cursor.getColumnIndex("title")) + " " +
                                 cursor.getInt(cursor.getColumnIndex("isbn")) + "\n");
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

    private void clearEditText() {
        mEditTextTitle.setText("");
        mEditTextISBN.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView)findViewById(R.id.text_view);

        mEditTextTitle = (EditText)findViewById(R.id.edit_text_title);
        mEditTextISBN = (EditText)findViewById(R.id.edit_text_isbn);

        mButtonInsert = (Button)findViewById(R.id.button_insert);
        mButtonUpdate = (Button)findViewById(R.id.button_update);
        mButtonDelete = (Button)findViewById(R.id.button_delete);

        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();

        mButtonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(mEditTextTitle.getText().toString(), mEditTextISBN.getText().toString());
                clearEditText();
                updateUI();
            }
        });

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rawUpdate("UPDATE Book SET title = ? WHERE isbn = ?",
                        new String[] { mEditTextTitle.getText().toString(), mEditTextISBN.getText().toString() });
                clearEditText();
                updateUI();
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.delete("Book", "isbn = ?", new String[] { mEditTextISBN.getText().toString() });
                clearEditText();
                updateUI();
            }
        });

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
