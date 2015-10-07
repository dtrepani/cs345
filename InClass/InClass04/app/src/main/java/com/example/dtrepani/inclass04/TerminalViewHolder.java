package com.example.dtrepani.inclass04;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class TerminalViewHolder extends RecyclerView.ViewHolder {

    TextView mOutputTextView;

    public TerminalViewHolder(View v) {
        super(v);
        mOutputTextView = (TextView)v.findViewById(R.id.list_item_text_view);
    }

    public void bindOutput(String output) {
        mOutputTextView.setText(output);
    }
}
