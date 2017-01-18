package com.simon.dribbble.ui.shots;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.agiledevelop.log.LLog;
import com.simon.agiledevelop.recycler.RecycledViewHolder;
import com.simon.agiledevelop.recycler.adapter.RecycledAdapter;
import com.simon.agiledevelop.utils.ImgLoadHelper;
import com.simon.dribbble.R;
import com.simon.dribbble.data.model.LikeEntity;
import com.simon.dribbble.data.model.User;
import com.simon.dribbble.util.ColorPhrase;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/14 15:15
 */

public class LikesAdapter extends RecycledAdapter<LikeEntity, RecycledViewHolder> {
    private RecyclerView.RecycledViewPool mPool = new RecyclerView.RecycledViewPool();

    public LikesAdapter() {
        super(R.layout.item_user);
    }

    @Override
    protected void convert(RecycledViewHolder holder, LikeEntity item) {
        if (null != item) {
            User follower = item.getUser();

            String avatar_url = follower.avatar_url;
            ImageView avatar = holder.getView(R.id.imv_avatar);

            ImgLoadHelper.loadAvatar(avatar_url, avatar);

            holder.setText(R.id.tv_username, follower.name);
            String loca = TextUtils.isEmpty(follower.location) ? "Unknown" : follower.location;
            holder.setText(R.id.tv_location, loca);

            CharSequence shot_count = ColorPhrase.from(follower.shots_count + "  <作品>")
                    .withSeparator("<>")
                    .innerColor(0xFF808080)
                    .outerColor(0xFF333333)
                    .format();

            CharSequence follower_count = ColorPhrase.from(follower.followers_count + "  <粉丝>")
                    .withSeparator("<>")
                    .innerColor(0xFF808080)
                    .outerColor(0xFF333333)
                    .format();

            TextView view = holder.getView(R.id.tv_location);
            ColorStateList textColors = view.getTextColors();
            LLog.d("convert: " + textColors.toString());

            holder.setText(R.id.tv_shots_count, shot_count);
            holder.setText(R.id.tv_follower_count, follower_count);

        }
    }

    public RecyclerView.RecycledViewPool getPool() {
        return mPool;
    }
}
