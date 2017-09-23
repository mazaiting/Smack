package com.mazaiting.smackim.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import com.mazaiting.smackim.ui.activity.RegisterActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mazaiting on 2017/9/19.
 */

public class FileUtil {
  /**
   * 获取拍照文件
   */
  public static File getCameraFile(){
    // 使用系统当前日期加以调整作为照片的名称
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
    // 拍照文件
    return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
        dateFormat.format(date) + ".jpg");
  }

  /**
   * 启动系统裁剪
   * @param activity Activity
   * @param uri uri
   * @param picCode 请求码
   */
  public static void doCropPhoto(Activity activity, Uri uri, int picCode) {
    Intent intent = getCropImageIntent(uri);
    activity.startActivityForResult(intent, picCode);
  }

  /**
   * 裁剪图片
   * @param uri uri
   */
  private static Intent getCropImageIntent(Uri uri) {
    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setDataAndType(uri, "image/*");
    // crop为true是设置在开启的intent中设置显示的view可以剪裁
    intent.putExtra("crop", true);
    // aspectX aspectY 是宽高的比例
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);
    // outputX,outputY 是剪裁图片的宽高
    intent.putExtra("outputX", 400);
    intent.putExtra("outputY", 400);
    intent.putExtra("return-data", true);
    intent.putExtra("noFaceDetection",true);
    return intent;
  }

  /**
   * bitmap 转换为 字节数组
   * @param bitmap bitmap
   * @return byte[]
   */
  public static byte[] BitmapToBytes(Bitmap bitmap){
    if (null == bitmap) return null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    return baos.toByteArray();
  }

  /**
   * bitmap 转换为 drawable
   * @param context 上下文
   * @param bitmap 图片
   * @return drawable
   */
  public static Drawable BitmapToDrawable(Context context, Bitmap bitmap){
    return new BitmapDrawable(context.getResources(), bitmap);
  }
}
