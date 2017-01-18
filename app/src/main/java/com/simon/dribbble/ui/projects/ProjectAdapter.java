package com.simon.dribbble.ui.projects;

import android.support.v7.widget.RecyclerView;

import com.simon.agiledevelop.recycler.RecycledViewHolder;
import com.simon.agiledevelop.recycler.adapter.RecycledAdapter;
import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ProjectEntity;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:41
 */

public class ProjectAdapter extends RecycledAdapter<ProjectEntity,RecycledViewHolder> {

    private RecyclerView.RecycledViewPool mPool = new RecyclerView.RecycledViewPool();

    public ProjectAdapter() {
        super(R.layout.item_project);
    }

    @Override
    protected void convert(RecycledViewHolder helper, ProjectEntity item) {
        if (null != item) {
            helper.setText(R.id.tv_project_na, item.getName());
            helper.setText(R.id.tv_project_count, item.getShots_count() + "  作品");
            helper.setText(R.id.tv_project_desc, item.getDescription());
        }
    }

    public RecyclerView.RecycledViewPool getPool() {
        return mPool;
    }
}
