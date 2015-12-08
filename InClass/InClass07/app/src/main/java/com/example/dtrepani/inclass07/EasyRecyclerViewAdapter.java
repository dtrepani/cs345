package com.example.dtrepani.inclass07;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by mkowalcz on 11/9/2015.
 */
public abstract class EasyRecyclerViewAdapter extends RecyclerView.Adapter<EasyRecyclerViewHolder> {
    protected List<? extends Object> theList;
    private int itemLayoutId;
    private Context context;

    public EasyRecyclerViewAdapter(Context context, List<? extends Object> theList, int itemLayoutId) {
        super();
        this.context = context;
        this.theList = theList;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public EasyRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new EasyRecyclerViewHolder(LayoutInflater.from(context).inflate(itemLayoutId, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(EasyRecyclerViewHolder easyRecyclerViewHolder, int i) {
        easyRecyclerViewHolder.setIndex(i);
    }

    @Override
    public int getItemCount() {
        return theList.size();
    }
}
