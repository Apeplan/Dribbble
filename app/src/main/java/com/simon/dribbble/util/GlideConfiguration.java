package com.simon.dribbble.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/2/25 17:22
 */

public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {//应用选项

//        .setMemoryCache(MemoryCache memoryCache)
//        .setBitmapPool(BitmapPool bitmapPool)
//        .setDiskCache(DiskCache.Factory diskCacheFactory)
//        .setDiskCacheService(ExecutorService service)
//        .setResizeService(ExecutorService service)
//        .setDecodeFormat(DecodeFormat decodeFormat)

        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        int cacheSize = 10 << 20;
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize)
                //内部缓存
                //new ExternalCacheDiskCacheFactory(context, cacheSize) 外部缓存
        );
    }

    @Override
    public void registerComponents(Context context, Glide glide) {//注册组件
        // nothing to do here
    }
}
