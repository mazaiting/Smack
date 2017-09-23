package com.mazaiting.smackim.util.xmpp.manager;

import com.mazaiting.smackim.util.xmpp.interfaces.chat.InComeMessageListener;
import com.mazaiting.smackim.util.xmpp.interfaces.other.DestroyInterface;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * 聊天管理者
 * Created by mazaiting on 2017/9/20.
 */

public class ChatOfManager implements DestroyInterface{
  private static ChatOfManager sChattingManager;
  /**聊天管理者*/
  private ChatManager mChatManager;
  /**聊天窗口*/
  private Chat mChat;
  private InComeMessageListener mInComeMessageListener;

  private ChatOfManager(){
    mChatManager = XMPPManager.getXMPPManager().getChatManager();
  }
  public static ChatOfManager getInstance(){
    if (null == sChattingManager){
      synchronized (ChatOfManager.class){
        if (null == sChattingManager){
          sChattingManager = new ChatOfManager();
        }
      }
    }
    return sChattingManager;
  }

  /**
   * 创建聊天窗口
   */
  public Chat createChat(String jid){
    try {
      mChat = mChatManager.chatWith(JidCreate.entityBareFrom(jid));
      mInComeMessageListener = new InComeMessageListener();
      mChatManager.addIncomingListener(mInComeMessageListener);
      return mChat;
    } catch (XmppStringprepException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 发送消息
   * @param message
   */
  public void sendMessage(String message){
    try {
      mChat.send(message);
    } catch (SmackException.NotConnectedException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 销毁聊天管理器
   */
  @Override public void destroy(){
    mChatManager.removeListener(mInComeMessageListener);
    mInComeMessageListener = null;
    mChat = null;
    mChatManager = null;
  }



}
