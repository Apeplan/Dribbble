package com.simon.dribbble.ui.shots;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.agiledevelop.recycler.RecycledViewHolder;
import com.simon.agiledevelop.recycler.adapter.RecycledAdapter;
import com.simon.agiledevelop.utils.ImgLoadHelper;
import com.simon.dribbble.DribbbleApp;
import com.simon.dribbble.R;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.util.ColorPhrase;
import com.simon.dribbble.util.StringUtil;


/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/2/25 15:50
 */

public class ShotsAdapter extends RecycledAdapter<ShotEntity, RecycledViewHolder> {

    private ShotClickListener mShotClickListener;

    private RecyclerView.RecycledViewPool mPool = new RecyclerView.RecycledViewPool() {
        @Override
        public RecyclerView.ViewHolder getRecycledView(int viewType) {
            RecyclerView.ViewHolder scrap = super.getRecycledView(viewType);
            Log.i("@@@", "get holder from pool: " + scrap);
            return scrap;
        }

        @Override
        public void putRecycledView(RecyclerView.ViewHolder scrap) {
            super.putRecycledView(scrap);
            Log.i("@@@", "put holder to pool: " + scrap);
        }

        @Override
        public String toString() {
            return "ViewPool in adapter@" + Integer.toHexString(hashCode());
        }
    };

    public ShotsAdapter() {
        super(R.layout.item_shot);
    }

    @Override
    protected void convert(RecycledViewHolder holder, final ShotEntity shot) {
        if (null != shot) {
            final long id = shot.getId();
            String title = shot.getTitle();
            String normal = shot.getImages().getNormal();
            String hidpi = shot.getImages().getHidpi();

            String avatar_url = shot.getUser().avatar_url;
            final String name = shot.getUser().name;
            boolean animated = shot.isAnimated();// 是否是GIF
            ImageView imageView = holder.getView(R.id.imv_shot_pic);
            ImageView avatar = holder.getView(R.id.imv_avatar);

            ImgLoadHelper.image(StringUtil.isEmpty(hidpi) ? normal : hidpi, R.drawable
                    .placeholder_pic, R.drawable.placeholder_pic, imageView);
            ImgLoadHelper.loadAvatar(avatar_url, avatar);

            holder.setVisible(R.id.tv_type, animated);
            holder.setText(R.id.tv_title, title);

            CharSequence colorName = ColorPhrase.from("<by> " + name)
                    .withSeparator("<>").
                            innerColor(0xFFFF286F).
                            outerColor(0xFF575757).
                            format();
            Typeface typeface = Typeface.createFromAsset(DribbbleApp.context().getAssets(),
                    "fonts/Pacifico.ttf");
            TextView nameTv = holder.getView(R.id.tv_user);

            nameTv.setText(colorName);// #6DB7B8   #575757
            nameTv.setTypeface(typeface);
            holder.setText(R.id.tv_views_count, shot.getViews_count() + "");
            holder.setText(R.id.tv_comments_count, shot.getComments_count() + "");
            holder.setText(R.id.tv_likes_count, shot.getLikes_count() + "");

            holder.getView(R.id.rl_userinfo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShotClickListener) {
                        mShotClickListener.shotClick(shot, 0);
                    }
                }
            });

            holder.getView(R.id.cv_pic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mShotClickListener) {
                        mShotClickListener.shotClick(shot, 1);
                    }
                }
            });

        }
    }

    public RecyclerView.RecycledViewPool getPool() {
//        RecycledViewPool可以自主控制需要缓存的ViewHolder数量
//        mPool.setMaxRecycledViews(itemViewType, number);
        return mPool;
    }

    public void setShotClickListener(ShotClickListener listener) {
        this.mShotClickListener = listener;
    }

    interface ShotClickListener {
        void shotClick(ShotEntity shot, int type);
    }

}

