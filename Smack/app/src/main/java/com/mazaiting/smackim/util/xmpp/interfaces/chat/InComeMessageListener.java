package com.mazaiting.smackim.util.xmpp.interfaces.chat;

import com.orhanobut.logger.Logger;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

/**
 * 聊天消息进入
 * Created by mazaiting on 2017/9/20.
 */

public class InComeMessageListener implements IncomingChatMessageListener {
  @Override public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
    // 接收消息
    Logger.e("newIncomingMessage"+ message.getBody());// 消息
  }
}
