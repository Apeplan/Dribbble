package com.simon.agiledevelop.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2017/2/6
 * @email hanzx1024@gmail.com
 */

public class AvatarLayout extends RelativeLayout {
    private AvatarImageView mAvatarImageView;

    public AvatarLayout(Context context) {
        this(context, null);
    }

    public AvatarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        //将控件添加到布局中
        mAvatarImageView = new AvatarImageView(context);
        this.addView(mAvatarImageView, 0, relParams);
        //按钮
        LinearLayout mLinear = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        mLinear.setOrientation(LinearLayout.HORIZONTAL);
        mLinear.setLayoutParams(params);
        final TextView txtClip = new Button(context);
        txtClip.setText("剪切");
        txtClip.setBackgroundColor(Color.parseColor("#00000000"));
        txtClip.setTextColor(Color.WHITE);
        mLinear.setBackgroundColor(Color.parseColor("#60424348"));
        mLinear.addView(txtClip);
        //将“剪切”按钮添加到布局中
        relParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        relParams.addRule(RelativeLayout.BELOW, mAvatarImageView.getId());
        this.addView(mLinear, 1, relParams);
        //实现接口,回调
        txtClip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClipBitmapListener.onClipBitmap();
            }
        });
    }

    /**
     * 为控件设置图片
     *
     * @param drawable
     */
    public void setImgDrawable(Drawable drawable) {
        mAvatarImageView.setImageDrawable(drawable);
    }

    public void setImgDrawable(Bitmap bitmap) {
        mAvatarImageView.setImageBitmap(bitmap);
    }

    public void setImgDrawable(int resId) {
        mAvatarImageView.setImageResource(resId);
    }

    public void setmType(AvatarImageView.TYPE mType) {
        mAvatarImageView.setmType(mType);
    }

    //截图方法
    public Bitmap clipBitmap() {
        return mAvatarImageView.clipBitmap();
    }

    /**
     * 回调接口
     */
    OnClipBitmapListener onClipBitmapListener;

    public void setOnClipBitmapListener(OnClipBitmapListener listener) {
        this.onClipBitmapListener = listener;
    }

    public interface OnClipBitmapListener {
        void onClipBitmap();
    }
}
