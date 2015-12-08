package com.example.dtrepani.inclass07;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Hashtable;

/**
 * Created by mkowalcz on 11/11/2015.
 */
public class EasyRecyclerViewHolder extends RecyclerView.ViewHolder {
    private Hashtable<Integer, View> viewLookup;
    private int index;

    public EasyRecyclerViewHolder(View itemView) {
        super(itemView);
        viewLookup = new Hashtable<Integer, View>();
    }

    void setIndex(int i) {
        index = i;
    }

    public int getIndex() {
        return index;
    }

    public View getView(int id) {
        //If not in the hashtable get the instance, save a reference in the hashtable
        if (!viewLookup.containsKey(id)) {
            View v = itemView.findViewById(id);
            viewLookup.put(id, v);
            return v;
        }
        //return it
        else {
            return viewLookup.get(id);
        }
    }
}
