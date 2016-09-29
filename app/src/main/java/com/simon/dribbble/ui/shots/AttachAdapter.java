package com.simon.dribbble.ui.shots;

import android.widget.ImageView;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.AttachmentEntity;
import com.simon.dribbble.util.ImgLoadHelper;
import com.simon.dribbble.util.StringUtil;

import net.quickrecyclerview.show.BaseQuickAdapter;
import net.quickrecyclerview.show.BaseViewHolder;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 16:25
 */

public class AttachAdapter extends BaseQuickAdapter<AttachmentEntity> {

    public AttachAdapter() {
        super(R.layout.item_attach);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttachmentEntity item) {
        if (null != item) {
            String url = item.getUrl();
            int views_count = item.getViews_count();
            long size = item.getSize();

            ImgLoadHelper.loadImage(url, (ImageView) helper.getView(R.id.imv_attach_pic));

            helper.setText(R.id.tv_views_count, views_count + "");

            String fileSize = StringUtil.formatFileSize(size, true);
            helper.setText(R.id.tv_size, fileSize);

        }
    }
}
