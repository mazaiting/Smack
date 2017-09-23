package com.mazaiting.smackim.util.xmpp.manager;

import com.mazaiting.smackim.util.xmpp.interfaces.chat.InComeFileListener;
import com.mazaiting.smackim.util.xmpp.interfaces.other.DestroyInterface;
import java.io.File;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * 文件管理者
 * 聊天时给管理员传文件时的JID为：admin@192.168.0.108/Smack
 * Created by mazaiting on 2017/9/20.
 */
public class FileOfManager implements DestroyInterface{
  private static FileOfManager sFileManager;
  /**文件管理器*/
  private FileTransferManager mSendFileTransfer;
  private OutgoingFileTransfer mOutgoingFileTransfer;
  private InComeFileListener mInComeFileListener;

  private FileOfManager(){
    mSendFileTransfer = XMPPManager.getXMPPManager().getSendFileTransfer();
  }
  public static FileOfManager getInstance(){
    if (null == sFileManager){
      synchronized (ChatOfManager.class){
        if (null == sFileManager){
          sFileManager = new FileOfManager();
        }
      }
    }
    return sFileManager;
  }

  /**
   * 获取发送文件的发送器
   */
  public OutgoingFileTransfer getOutgoingFileTransfer(String jid){
    try {
      mOutgoingFileTransfer =
          mSendFileTransfer.createOutgoingFileTransfer(JidCreate.entityFullFrom(jid));
      mInComeFileListener = new InComeFileListener();
      mSendFileTransfer.addFileTransferListener(mInComeFileListener);
      return mOutgoingFileTransfer;
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 发送文件
   * @param file 文件
   * @param description 文件描述
   */
  public void sendFile(File file, String description){
    try {
      mOutgoingFileTransfer.sendFile(file,description);
    } catch (SmackException e) {
      e.printStackTrace();
    }
  }

  /**
   * 取消发送文件
   */
  public void cancelSend(){
    mOutgoingFileTransfer.cancel();
  }

  @Override public void destroy() {
    mSendFileTransfer.removeFileTransferListener(mInComeFileListener);//移除监听
    mInComeFileListener = null;
    mSendFileTransfer = null;
    mOutgoingFileTransfer = null;
  }
}
