package com.mazaiting.smackim.util.xmpp.interfaces.chat;

import java.io.File;
import java.io.IOException;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

/**
 * Created by mazaiting on 2017/9/20.
 */

public class InComeFileListener implements FileTransferListener {
  @Override public void fileTransferRequest(FileTransferRequest request) {
    // 接收文件
    // Accept it
    IncomingFileTransfer transfer = request.accept();
    try {
      String description = request.getDescription();
      //在目录fileDir目录下新建一个名字为request.getFileName()的文件
      File file = new File("/sdcard/" ,request.getFileName());
      //开始接收文件(将传输过来的文件内容输出到file中)
      transfer.recieveFile(file);
      // TODO 此处执行文件传输监听
    } catch (SmackException | IOException e) {
      e.printStackTrace();
    }
  }
}
