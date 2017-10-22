package com.haoxiong.taotao.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/20.
 */
public class BitmapUtils {
    public static Bitmap createImageThumbnail(String filePath,  int newHeight,
                                        int newWidth) {
        BitmapFactory.Options options =  new BitmapFactory.Options();
        options.inJustDecodeBounds =  true ;
        BitmapFactory.decodeFile(filePath, options);
        int oldHeight = options.outHeight;
        int oldWidth = options.outWidth;
 Log.i("fasdfasdf", " 高度是： " + oldHeight + " ，宽度是： " + oldWidth);
        int ratioHeight = oldHeight / newHeight;
        int ratioWidth = oldWidth / newWidth;
        options.inSampleSize = ratioHeight > ratioWidth ? ratioWidth
                : ratioHeight;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds =  false ;
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
 Log.i("fasdfasdf", " 高度是： " + options.outHeight + " ，宽度是： " + options.outWidth);
        return bm;
    }
}
