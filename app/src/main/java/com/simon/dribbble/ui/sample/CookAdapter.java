package com.simon.dribbble.ui.sample;

import android.widget.ImageView;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.Cook;
import com.simon.dribbble.util.ImgLoadHelper;

import net.quickrecyclerview.show.BaseQuickAdapter;
import net.quickrecyclerview.show.BaseViewHolder;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/7/25 15:09
 */

public class CookAdapter extends BaseQuickAdapter<Cook> {

    public static String IMAGE_URL = "http://tnfs.tngou.net/image";

    public CookAdapter(List<Cook> data) {
        super(R.layout.item_cook, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Cook item) {
        if (item != null) {
            String imgUrl = IMAGE_URL + item.getImg() + "_300x300";
            ImgLoadHelper.loadImage(imgUrl, (ImageView) helper.getView(R.id.imv_cook_icon));
            helper.setText(R.id.tv_cook_name, item.getName());
            helper.setText(R.id.tv_cook_description, item.getDescription());
            helper.setText(R.id.tv_count, item.getCount() + "浏览过");
            helper.setText(R.id.tv_rcount, item.getRcount() + "评论");
            helper.setText(R.id.tv_fcount, item.getFcount() + "收藏");
        }
    }
}
