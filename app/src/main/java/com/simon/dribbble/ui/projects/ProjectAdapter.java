package com.simon.dribbble.ui.projects;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ProjectEntity;

import net.quickrecyclerview.show.BaseQuickAdapter;
import net.quickrecyclerview.show.BaseViewHolder;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/12 18:41
 */

public class ProjectAdapter extends BaseQuickAdapter<ProjectEntity> {

    public ProjectAdapter() {
        super(R.layout.item_project);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectEntity item) {
        if (null != item) {
            helper.setText(R.id.tv_project_na, item.getName());
            helper.setText(R.id.tv_project_count, item.getShots_count() + "  作品");
            helper.setText(R.id.tv_project_desc, item.getDescription());
        }
    }
}
