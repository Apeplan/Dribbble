package com.simon.dribbble.ui.user;

import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.dribbble.DribbbleApp;
import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.model.UserLikeEntity;
import com.simon.dribbble.util.ColorPhrase;
import com.simon.dribbble.util.ImgLoadHelper;
import com.simon.dribbble.util.StringUtil;

import net.quickrecyclerview.show.BaseQuickAdapter;
import net.quickrecyclerview.show.BaseViewHolder;


/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/2/25 15:50
 */

public class LikeShotsAdapter extends BaseQuickAdapter<UserLikeEntity> {

    public LikeShotsAdapter() {
        super(R.layout.item_shot);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserLikeEntity likeEntity) {
        if (null != likeEntity) {
            ShotEntity shot = likeEntity.getShot();
            long id = shot.getId();
            String title = shot.getTitle();
            String normal = shot.getImages().getNormal();
            String hidpi = shot.getImages().getHidpi();

            String avatar_url = shot.getUser().avatar_url;
            String name = shot.getUser().name;
            boolean animated = shot.isAnimated();// 是否是GIF
            ImageView imageView = helper.getView(R.id.imv_shot_pic);
            ImageView avatar = helper.getView(R.id.imv_avatar);

            ImgLoadHelper.loadImage(StringUtil.isEmpty(hidpi) ? normal : hidpi, imageView);
            ImgLoadHelper.loadAvatar(avatar_url, avatar);

            helper.setVisible(R.id.tv_type, animated);
            helper.setText(R.id.tv_title, title);

            CharSequence colorName = ColorPhrase.from("<by> " + name)
                    .withSeparator("<>").
                            innerColor(0xFFFF286F).
                            outerColor(0xFF575757).
                            format();
            Typeface typeface = Typeface.createFromAsset(DribbbleApp.context().getAssets(),
                    "fonts/Pacifico.ttf");
            TextView nameTv = helper.getView(R.id.tv_user);

            nameTv.setText(colorName);// #6DB7B8   #575757
            nameTv.setTypeface(typeface);
            helper.setText(R.id.tv_views_count, shot.getViews_count() + "");
            helper.setText(R.id.tv_comments_count, shot.getComments_count() + "");
            helper.setText(R.id.tv_likes_count, shot.getLikes_count() + "");

        }
    }

}

