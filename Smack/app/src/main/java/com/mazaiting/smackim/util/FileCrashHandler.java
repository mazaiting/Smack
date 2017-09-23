package com.mazaiting.smackim.util;

import android.content.Context;
import com.mazaiting.report.AbstractCrashReportHandler;
import com.orhanobut.logger.Logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by mazaiting on 2017/9/19.
 */

public class FileCrashHandler extends AbstractCrashReportHandler {
  public FileCrashHandler(Context context) {
    super(context);
  }

  @Override protected void sendReport(String title, String body, File file) {
    try {
      FileInputStream fis = new FileInputStream(file);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int len = 0;
      byte[] bytes = new byte[1024];
      while ((len = fis.read(bytes)) != -1){
        baos.write(bytes, 0, len);
      }
      Logger.e(baos.toString("UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
