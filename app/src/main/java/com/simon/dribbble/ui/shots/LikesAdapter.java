package com.simon.dribbble.ui.shots;

import android.content.res.ColorStateList;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.data.model.User;
import com.simon.dribbble.util.ColorPhrase;
import com.simon.dribbble.util.ImgLoadHelper;

import net.quickrecyclerview.show.BaseQuickAdapter;
import net.quickrecyclerview.show.BaseViewHolder;
import net.quickrecyclerview.utils.log.LLog;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 15:15
 */

public class LikesAdapter extends BaseQuickAdapter<LikeEntity> {
    public LikesAdapter() {
        super(R.layout.item_user);
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeEntity item) {
        if (null != item) {
            User follower = item.getUser();

            String avatar_url = follower.avatar_url;
            ImageView avatar = helper.getView(R.id.imv_avatar);

            ImgLoadHelper.loadAvatar(avatar_url, avatar);

            helper.setText(R.id.tv_username, follower.name);
            helper.setText(R.id.tv_location, follower.location);

            CharSequence shot_count = ColorPhrase.from(follower.shots_count + "  <作品>")
                    .withSeparator("<>")
                    .innerColor(0xFF808080)
                    .outerColor(0xFF0000FF)
                    .format();

            CharSequence follower_count = ColorPhrase.from(follower.followers_count + "  <粉丝>")
                    .withSeparator("<>")
                    .innerColor(0xFF808080)
                    .outerColor(0xFF0000FF)
                    .format();

            TextView view = helper.getView(R.id.tv_location);
            ColorStateList textColors = view.getTextColors();
            LLog.d("Simon", "convert: " + textColors.toString());

            helper.setText(R.id.tv_shots_count, shot_count);
            helper.setText(R.id.tv_follower_count, follower_count);

        }
    }
}
