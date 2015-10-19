package com.example.dtrepani.inclass04;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class TerminalFragment extends Fragment {
    private Thread thread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                MainProgram.main(new String[0]);
            }
        };

        thread = new Thread(r);
        thread.start();
    }
}
