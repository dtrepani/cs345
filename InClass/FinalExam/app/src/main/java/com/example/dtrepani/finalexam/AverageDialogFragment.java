package com.example.dtrepani.finalexam;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AverageDialogFragment extends DialogFragment {

    private static final String AVERAGE = "com.example.dtrepani.finalexam.avg";

    private TextView mTextAverage;

    public static AverageDialogFragment newInstance(Double avg) {
        AverageDialogFragment dialog = new AverageDialogFragment();

        Bundle b = new Bundle();
        b.putSerializable(AVERAGE, avg);
        dialog.setArguments(b);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout, null);

        mTextAverage = (TextView)v.findViewById(R.id.text_average);
        mTextAverage.setText(String.valueOf(getArguments().getSerializable(AVERAGE)));

        AlertDialog a = new AlertDialog.Builder(getActivity())
                .setTitle("Average")
                .setView(v)
                .setPositiveButton("OK", null)
                .create();

        return a;
    }
}
