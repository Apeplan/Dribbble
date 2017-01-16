package com.simon.dribbble.ui.projects;

import com.simon.agiledevelop.recycler.RapidViewHolder;
import com.simon.agiledevelop.recycler.adapter.RapidAdapter;
import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ProjectEntity;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:41
 */

public class ProjectAdapter extends RapidAdapter<ProjectEntity,RapidViewHolder> {

    public ProjectAdapter() {
        super(R.layout.item_project);
    }

    @Override
    protected void convert(RapidViewHolder helper, ProjectEntity item) {
        if (null != item) {
            helper.setText(R.id.tv_project_na, item.getName());
            helper.setText(R.id.tv_project_count, item.getShots_count() + "  作品");
            helper.setText(R.id.tv_project_desc, item.getDescription());
        }
    }
}
