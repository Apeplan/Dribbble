package com.simon.dribbble.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.simon.dribbble.DribbbleApp;
import com.simon.dribbble.R;
import com.simon.dribbble.widget.CircleTransform;

/**
 * 加载图片的帮助类
 * <p/>
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/4/5 16:48
 */

public class ImgLoadHelper {
    /**
     * 全局Context
     */
    private static Context mContext = DribbbleApp.context();

    /*
        .placeholder() 占位图片
        .error() 加载失败
        .crossFade()淡入淡出
        .dontAnimate()无动画效果
        .override()调整图片大小
        .transform()自定义图形转换
        .skipMemoryCache(true)不做内存缓存
        .diskCacheStrategy(DiskCacheStrategy.ALL)磁盘缓存

        DiskCacheStrategy.ALL  缓存所有版本的图片
        DiskCacheStrategy.NONE 不缓存任何图片
        DiskCacheStrategy.SOURCE 只缓存全分辨率的图像
        DiskCacheStrategy.RESULT 只缓存经过处理的图片
    */

    /**
     * 使用默认配置图片加载
     *
     * @param url       图片地址
     * @param imageView 显示图片的容器
     */
    public static void loadImage(String url, ImageView imageView) {
        loadImage(url, R.drawable.placeholderr, R.drawable.placeholderr, imageView);
    }

    /**
     * 图片加载方法：可以配置加载时的占位图、加载错误时展示的图片、图片加载监听
     *
     * @param url         图片地址
     * @param placeholder 加载时的占位图片
     * @param error       加载错误显示的图片
     * @param imageView   显示图片的容器
     */
    public static void loadImage(String url, int placeholder, int error, ImageView imageView) {

        Glide.with(mContext)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .into(imageView);
    }

    /**
     * 没有占位图，可以设置加载错误图片和加载监听的图片加载方法
     *
     * @param url       图片地址
     * @param error     加载错误显示的图片
     * @param imageView 显示图片的容器
     */
    public static void loadImage(String url, int error, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .error(error)
                .into(imageView);
    }

    /**
     * 加载圆形头像
     *
     * @param url       头像地址
     * @param imageView 显示头像的容器
     */
    public static void loadAvatar(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.person_image_empty)
                .error(R.drawable.person_image_empty)
                .transform(new CircleTransform(mContext))
                .into(imageView);
    }

    /**
     * Picasso默认会使用设备的15%的内存作为内存图片缓存，且现有的api无法清空内存缓存。
     * 可以在查看大图时放弃使用内存缓存，图片从网络下载完成后会缓存到磁盘中，加载会从磁盘中加载，
     * 这样可以加速内存的回收。
     *
     * @param url
     * @param imageView
     */
    public static void loadBigPic(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                //   NONE 是指图片加载完不缓存在内存中
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);

    }

    /**
     * 加载剪裁后的图片
     *
     * @param url       图片地址
     * @param pxW       要剪裁的目标宽度
     * @param pxH       要剪裁的目标高度
     * @param imageView 显示图片的容器
     */
    public static void loadCropPic(String url, int pxW, int pxH, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .override(pxW, pxH)
                .centerCrop()
                .into(imageView);
    }


    //  获取缓存大小：
    //  new GetDiskCacheSizeTask(textView).execute(new File(getCacheDir(), DiskCache
    //  .Factory.DEFAULT_DISK_CACHE_DIR));

    /**
     * 清楚 Glide 图片缓存
     */
    public static void clearCache() {
        //清除内存缓存
        Glide.get(mContext).clearMemory();
        //清除磁盘缓存，注意：清除磁盘缓存必须在子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();
            }
        }).start();
    }

}
