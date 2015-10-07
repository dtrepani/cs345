package com.example.dtrepani.inclass04;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TerminalAdapter extends RecyclerView.Adapter<TerminalViewHolder> {

    private List<String> mOutputs;

    public TerminalAdapter (List<String> outputs) {
        mOutputs = outputs;
    }

    @Override
    public TerminalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_layout, parent, false);
        return new TerminalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TerminalViewHolder holder, int position) {
        String output = mOutputs.get(position);
        holder.bindOutput(output);
    }

    @Override
    public int getItemCount() { return mOutputs.size(); }
}
