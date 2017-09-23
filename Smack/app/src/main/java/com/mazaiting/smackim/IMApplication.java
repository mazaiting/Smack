package com.mazaiting.smackim;

import android.app.Application;
import android.content.Context;
import com.mazaiting.smackim.util.FileCrashHandler;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by mazaiting on 2017/9/19.
 */

public class IMApplication extends Application {

  private static Context mContext;
  @Override public void onCreate() {
    super.onCreate();
    mContext = this;
    Logger.addLogAdapter(new AndroidLogAdapter());
    //new FileCrashHandler(this);
  }

  public static Context getContext() {
    return mContext;
  }
}
