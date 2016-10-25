package com.simon.dribbble.listener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.util.Log;

import com.simon.dribbble.data.model.ShotEntity;

import java.util.List;

/**
 * RecyclerView 差异化更新
 * <p>
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 16/10/18 10:42
 */

public class ItemDiffCallBack extends DiffUtil.Callback {
    private final String TAG = getClass().getSimpleName();
    private List<ShotEntity> mOldData;
    private List<ShotEntity> mNewData;

    public ItemDiffCallBack(List<ShotEntity> oldData, List<ShotEntity> newData) {
        mOldData = oldData;
        mNewData = newData;
    }

    @Override
    public int getOldListSize() {
        return mOldData == null ? 0 : mOldData.size();
    }

    @Override
    public int getNewListSize() {
        return mNewData == null ? 0 : mNewData.size();
    }

    // 判断是否是同一个对象
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldData.get(oldItemPosition).getId() == mNewData.get(newItemPosition).getId();
    }

    // 判断相同的对象，内容是否相同
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ShotEntity oldShot = mOldData.get(oldItemPosition);
        ShotEntity newShot = mNewData.get(newItemPosition);

        String oldTitle = oldShot.getTitle();
        String newTitle = newShot.getTitle();
        String oldName = oldShot.getUser().name;
        String newName = newShot.getUser().name;
        String oldAvatar = oldShot.getUser().avatar_url;
        String newAvatar = newShot.getUser().avatar_url;
        int oldViews = oldShot.getViews_count();
        int newViews = newShot.getViews_count();
        int oldComments = oldShot.getComments_count();
        int newComments = newShot.getComments_count();
        int oldLikes = oldShot.getLikes_count();
        int newLikes = newShot.getLikes_count();

        boolean title = TextUtils.equals(oldTitle, newTitle);
        boolean name = TextUtils.equals(oldName, newName);
        boolean avatar = TextUtils.equals(oldAvatar, newAvatar);
        boolean views = oldViews == newViews;
        boolean comments = oldComments == newComments;
        boolean likes = oldLikes == newLikes;

        Log.i(TAG, "oldContent:" + oldTitle + " newContent:" + newTitle);
        return title && name && avatar && views && comments && likes;
    }

    //找出其中的不同
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        ShotEntity oldShot = mOldData.get(oldItemPosition);
        ShotEntity newShot = mNewData.get(newItemPosition);

        String oldTitle = oldShot.getTitle();
        String newTitle = newShot.getTitle();
        String oldName = oldShot.getUser().name;
        String newName = newShot.getUser().name;
        String oldAvatar = oldShot.getUser().avatar_url;
        String newAvatar = newShot.getUser().avatar_url;
        int oldViews = oldShot.getViews_count();
        int newViews = newShot.getViews_count();
        int oldComments = oldShot.getComments_count();
        int newComments = newShot.getComments_count();
        int oldLikes = oldShot.getLikes_count();
        int newLikes = newShot.getLikes_count();

        boolean title = TextUtils.equals(oldTitle, newTitle);
        boolean name = TextUtils.equals(oldName, newName);
        boolean avatar = TextUtils.equals(oldAvatar, newAvatar);
        boolean views = oldViews == newViews;
        boolean comments = oldComments == newComments;
        boolean likes = oldLikes == newLikes;

        Bundle diffBundle = new Bundle();
        if (!title) {
            diffBundle.putString("title", newTitle);
        }
        if (!name) {
            diffBundle.putString("name", newName);
        }
        if (!avatar) {
            diffBundle.putString("avatar_url", newAvatar);
        }
        if (!views) {
            diffBundle.putInt("views_count", newViews);
        }
        if (!comments) {
            diffBundle.putInt("comments_count", newComments);
        }
        if (!likes) {
            diffBundle.putInt("likes_count", newLikes);
        }

        return diffBundle;
    }
}
