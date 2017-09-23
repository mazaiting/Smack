package com.mazaiting.smackim.util.xmpp.manager;

import com.mazaiting.smackim.util.xmpp.interfaces.other.DestroyInterface;
import java.util.List;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

/**
 * Created by mazaiting on 2017/9/20.
 */

public class OfflineOfManager implements DestroyInterface{

  private static OfflineOfManager sOfflineManager;
  private OfflineMessageManager mOfflineMessageManager;

  private OfflineOfManager(){
    mOfflineMessageManager = XMPPManager.getXMPPManager().getOfflineMessageManager();
  }
  public static OfflineOfManager getInstance(){
    if (null == sOfflineManager){
      synchronized (ChatOfManager.class){
        if (null == sOfflineManager){
          sOfflineManager = new OfflineOfManager();
        }
      }
    }
    return sOfflineManager;
  }

  /**
   * 获取离线消息
   */
  public List<Message> getOfflineMessage() {
    try {
      return mOfflineMessageManager.getMessages();
    } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | InterruptedException | SmackException.NotConnectedException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override public void destroy() {
    mOfflineMessageManager = null;
  }
}
