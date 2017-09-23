package com.mazaiting.smackim.util.xmpp.interfaces.roster;

import com.orhanobut.logger.Logger;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jxmpp.jid.Jid;

/**
 * Created by mazaiting on 2017/9/20.
 */

public class ReceiverStanzaListener implements StanzaListener {
  @Override public void processStanza(Stanza packet)
      throws SmackException.NotConnectedException, InterruptedException {
    Logger.e("processStanza");
    if (packet instanceof Presence){
      Presence presence = (Presence) packet;
      Jid from = presence.getFrom();
      if (presence.getType().equals(Presence.Type.subscribe)) {
        Logger.e("请求添加好友" + from);
      } else if (presence.getType().equals(Presence.Type.subscribed)) {//对方同意订阅
        Logger.e("同意订阅" + from);
      } else if (presence.getType().equals(Presence.Type.unsubscribe)) {//取消订阅
        Logger.e("取消订阅" + from);
      } else if (presence.getType().equals(Presence.Type.unsubscribed)) {//拒绝订阅
        Logger.e("拒绝订阅" + from);
      } else if (presence.getType().equals(Presence.Type.unavailable)) {//离线
        Logger.e("离线" + from);
      } else if (presence.getType().equals(Presence.Type.available)) {//上线
        Logger.e("上线" + from);
      }
    }
  }
}
