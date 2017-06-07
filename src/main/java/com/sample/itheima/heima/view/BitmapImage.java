package com.sample.itheima.heima.view;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Bitmap Image Object.
 * 
 * @author loopj
 * @see http://loopj.com/android-smart-image-view/
 *
 */
public class BitmapImage implements SmartImage {
    private Bitmap bitmap;

    public BitmapImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(Context context) {
        return bitmap;
    }
}