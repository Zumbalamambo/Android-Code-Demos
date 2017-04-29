package io.innofang.rxjava2demo.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Author: Inno Fang
 * Time: 2017/4/28 19:38
 * Description:
 */


public class MemoryCacheObservable extends CacheObservable {


    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    int cacheSize = maxMemory / 8;

    LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }
    };

    @Override
    public Image getDataFromCache(String url) {
        Bitmap bitmap = mLruCache.get(url);

        if (null != bitmap) {
            return new Image(url, bitmap);
        }
        return null;
    }

    @Override
    public void putDataToCache(Image image) {
        mLruCache.put(image.getUrl(), image.getBitmap());
    }
}
