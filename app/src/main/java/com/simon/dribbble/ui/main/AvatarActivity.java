package com.simon.dribbble.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.simon.agiledevelop.widget.AvatarImageView;
import com.simon.agiledevelop.widget.AvatarLayout;
import com.simon.dribbble.R;

public class AvatarActivity extends AppCompatActivity implements AvatarLayout.OnClipBitmapListener {


    private ImageView mImageView;
    AvatarLayout rectOrCircleLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        this.rectOrCircleLayout = (AvatarLayout) findViewById(R.id.id_ClipView);
        rectOrCircleLayout.setmType(AvatarImageView.TYPE.RECT);
        rectOrCircleLayout.setImgDrawable(BitmapFactory.decodeResource(getResources(),R.mipmap.qq));
        rectOrCircleLayout.setOnClipBitmapListener(this);
        this.mImageView = (ImageView) findViewById(R.id.imv_clip);
    }
    //回调方法,得到截图
    @Override
    public void onClipBitmap() {
        Bitmap m = rectOrCircleLayout.clipBitmap();
        if(m!=null){
            mImageView.setImageBitmap(m);
            mImageView.setVisibility(View.VISIBLE);
            rectOrCircleLayout.setVisibility(View.INVISIBLE);
        }
    }
}
