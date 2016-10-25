package com.simon.dribbble.ui.shots;

import android.text.Html;
import android.widget.ImageView;

import com.simon.dribbble.R;
import com.simon.dribbble.data.model.CommentEntity;
import com.simon.dribbble.data.model.User;
import com.simon.dribbble.util.RegexHelper;
import com.simon.dribbble.util.DateTimeUtil;
import com.simon.dribbble.util.ImgLoadHelper;

import net.quickrecyclerview.show.BaseQuickAdapter;
import net.quickrecyclerview.show.BaseViewHolder;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/9/1 13:56
 */

public class CommentAdapter extends BaseQuickAdapter<CommentEntity> {

    public CommentAdapter() {
        super(R.layout.item_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentEntity item) {
        if (null != item) {
            User user = item.getUser();
            String avatar_url = user.avatar_url;
            ImgLoadHelper.loadAvatar(avatar_url, (ImageView) helper.getView(R.id.imv_avatar));
            helper.setText(R.id.tv_author, user.name);
            String body = item.getBody();
            String created_at = item.getCreated_at();
            String formatUTC = DateTimeUtil.formatUTC(created_at);
            String friendly_time = DateTimeUtil.friendly_time(formatUTC);

            if (!RegexHelper.isEmpty(body)) {
                if (body.contains("<p>")) {
                    body = body.replace("<p>", "");
                }
                if (body.contains("</p>")) {
                    body = body.replace("</p>", ".");
                }

                helper.setText(R.id.tv_comment_content, Html.fromHtml(body));
            }

            helper.setText(R.id.tv_comment_time, friendly_time);
            int likes_count = item.getLikes_count();
            if (likes_count > 0) {
                helper.setVisible(R.id.tv_likes_count, true);
                helper.setText(R.id.tv_likes_count, item.getLikes_count() + "");
            } else {
                helper.setVisible(R.id.tv_likes_count, false);
            }

        }
    }
}
