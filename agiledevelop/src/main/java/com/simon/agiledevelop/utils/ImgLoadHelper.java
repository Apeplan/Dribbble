package com.simon.agiledevelop.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.simon.agiledevelop.R;
import com.simon.agiledevelop.glide.BlurTransformation;
import com.simon.agiledevelop.glide.CircleTransform;
import com.simon.agiledevelop.glide.RoundTransformation;


/**
 * describe: Help class for loading image
 *
 * @author Simon Han
 * @date 2016.10.20
 * @email hanzx1024@gmail.com
 */
public class ImgLoadHelper {

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
     * 全局Context
     */
    private static Context mContext = App.INSTANCE;
    /**
     * 默认加载占位图
     */
    private static int DEF_PLACEHOLDER = R.drawable.placeholder;
    /**
     * 默认加载出错的图片
     */
    private static int DEF_ERROR = R.drawable.placeholder;

    /**
     * 只是加载图片，不做其他的设置
     *
     * @param resId     本地图片资源ID
     * @param imageView 图片容器 Image View
     */
    public static void imageSimple(int resId, ImageView imageView) {
        Glide.with(mContext)
                .load(resId)
                .into(imageView);
    }

    /**
     * 只是加载图片，不做其他的设置
     *
     * @param url       图片地址(网络地址或者本地地址)
     * @param imageView 图片容器 Image View
     */
    public static void imageSimple(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .into(imageView);
    }

    /**
     * 使用默认配置图片加载
     *
     * @param resId     图片地址
     * @param imageView 显示图片的容器
     */
    public static void image(int resId, ImageView imageView) {
        image(resId, DEF_PLACEHOLDER, DEF_ERROR, imageView);
    }

    /**
     * 使用默认配置图片加载
     *
     * @param url       图片地址
     * @param imageView 显示图片的容器
     */
    public static void image(String url, ImageView imageView) {
        image(url, DEF_PLACEHOLDER, DEF_ERROR, imageView);
    }

    /**
     * 图片加载方法：可以配置加载时的占位图、加载错误时展示的图片、图片加载监听
     *
     * @param url         图片地址
     * @param placeholder 加载时的占位图片
     * @param error       加载错误显示的图片
     * @param imageView   显示图片的容器
     */
    public static void image(String url, int placeholder, int error, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .crossFade()
                .into(imageView);
    }

    /**
     * 图片加载方法：可以配置加载时的占位图、加载错误时展示的图片
     *
     * @param resId       图片资源ID
     * @param placeholder 加载时的占位图片
     * @param error       加载错误显示的图片
     * @param imageView   显示图片的容器
     */
    public static void image(int resId, int placeholder, int error, ImageView imageView) {
        Glide.with(mContext)
                .load(resId)
                .placeholder(placeholder)
                .error(error)
                .into(imageView);
    }

    /**
     * 只是加载图片，并伴有淡入淡出效果
     *
     * @param resId 本地图片资源ID
     * @param view  图片容器 Image View
     */

    public static void imageFade(int resId, ImageView view) {
        DrawableTypeRequest<Integer> request = Glide.with(mContext).load(resId);
        if (DEF_PLACEHOLDER != -1) {
            request.placeholder(DEF_PLACEHOLDER);
        }
        request.crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);

    }

    /**
     * 只是加载图片，并伴有淡入淡出效果
     *
     * @param url  加载图片资源（网络地址或者本地地址）
     * @param view 图片容器 Image View
     */

    public static void imageFade(String url, ImageView view) {
        DrawableTypeRequest<String> request = Glide.with(mContext).load(url);
        if (DEF_PLACEHOLDER != -1) {
            request.placeholder(DEF_PLACEHOLDER);
        }
        request.crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);

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
                .placeholder(R.drawable.head_portrait_def)
                .error(R.drawable.head_portrait_def)
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
                .placeholder(DEF_PLACEHOLDER)
                .error(DEF_ERROR)
                //   NONE 是指图片加载完不缓存在内存中
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);

    }

    /**
     * 加载图片，并指定宽高
     *
     * @param url    加载图片资源（网络地址或者本地地址）
     * @param view   图片容器 Image View
     * @param width  宽度
     * @param height 高度
     */
    public static void imageW_H(String url, ImageView view, int width, int height) {
        imageW_H(url,view,width,height,DEF_PLACEHOLDER);
    }

    /**
     * 加载图片，并指定宽高
     *
     * @param url    加载图片资源（网络地址或者本地地址）
     * @param view   图片容器 Image View
     * @param width  宽度
     * @param height 高度
     */
    public static void imageW_H(String url, ImageView view, int width, int height, int
            placeholderResId) {
        DrawableTypeRequest<String> request = Glide.with(mContext).load(url);
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        if (placeholderResId != -1) {
            request.placeholder(placeholderResId);
        }
        request.crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }

    /**
     * 加载图片，并指定宽高
     *
     * @param resId  图片资源ID
     * @param view   图片容器 Image View
     * @param width  宽度
     * @param height 高度
     */
    public static void imageW_H(int resId, ImageView view, int width, int height) {
        DrawableTypeRequest<Integer> request = Glide.with(mContext).load(resId);
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        if (DEF_PLACEHOLDER != -1) {
            request.placeholder(DEF_PLACEHOLDER);
        }
        request.crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }

    /**
     * 高斯模糊图片处理
     *
     * @param url  圆形图片（网络地址或者本地地址）
     * @param view 图片容器 Image Vew
     */

    public static void imageBlur(String url, ImageView view) {
        DrawableTypeRequest<String> request = Glide.with(mContext).load(url);
        if (DEF_PLACEHOLDER != -1) {
            request.placeholder(DEF_PLACEHOLDER);
        }
        request.crossFade()
                .transform(new BlurTransformation(mContext))
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .into(view);
    }

    /**
     * 加载有弧度的图片，如圆角、圆形
     *
     * @param url    圆形图片（网络地址或者本地地址）
     * @param radius 弧度
     * @param view   图片容器 Image Vew
     */
    public static void imageRound(String url, int radius, ImageView view) {
        DrawableTypeRequest<String> request = Glide.with(mContext).load(url);
        if (DEF_PLACEHOLDER != -1) {
            request.placeholder(DEF_PLACEHOLDER);
        }
        request.crossFade()
                .centerCrop()
                .transform(new RoundTransformation(mContext, radius))
                .into(view);
    }

    /**
     * 加载有弧度的图片，如圆角、圆形
     *
     * @param resId  圆形图片资源ID
     * @param radius 弧度
     * @param view   图片容器 Image Vew
     */

    public static void imageRound(int resId, int radius, ImageView view) {
        DrawableTypeRequest<Integer> request = Glide.with(mContext).load(resId);
        if (DEF_PLACEHOLDER != -1) {
            request.placeholder(DEF_PLACEHOLDER);
        }
        request.crossFade()//淡入淡出
                .centerCrop() // 缩放图片让图片充满整个ImageView的边框，然后裁掉超出的部分
                .transform(new RoundTransformation(mContext, radius))
                .into(view);
    }


    /**
     * 加载有弧度的图片，如圆角、圆形
     *
     * @param url         圆形图片（网络地址或者本地地址）
     * @param radius      弧度
     * @param view        图片容器 Image Vew
     * @param placeholder 占位图
     */
    public static void imageRound(String url, int radius, ImageView view, int placeholder) {
        Glide.with(mContext).load(url)
                .placeholder(placeholder)
                .crossFade()
                .transform(new RoundTransformation(mContext, radius))
                .into(view);
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
