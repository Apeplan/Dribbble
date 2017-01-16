package com.simon.agiledevelop.recycler.listeners;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.simon.agiledevelop.recycler.adapter.RapidAdapter;

/**
 * describe: item click listener for RecyclerView
 *
 * @author Simon Han
 * @date 2015.09.17
 * @email hanzx1024@gmail.com
 */

public abstract class OnItemClickListener extends SimpleClickListener {

    @Override
    protected void onItemLongClick(RapidAdapter adapter, RecyclerView recyclerView, View view, int
            position) {

    }

    @Override
    protected void onItemChildLongClick(RapidAdapter adapter, RecyclerView recyclerView, View
            view, int position) {

    }

    @Override
    protected void onItemChildClick(RapidAdapter adapter, RecyclerView recyclerView, View view,
                                    int position) {

    }
}
