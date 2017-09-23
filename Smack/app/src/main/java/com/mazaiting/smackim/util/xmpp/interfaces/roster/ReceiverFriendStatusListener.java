package com.mazaiting.smackim.util.xmpp.interfaces.roster;

import com.orhanobut.logger.Logger;
import java.util.Collection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterListener;
import org.jxmpp.jid.Jid;

/**
 * 可以接收到其他人是否删除了自己,好友在线状态改变
 * Created by mazaiting on 2017/9/19.
 */
public class ReceiverFriendStatusListener implements RosterListener {
  @Override public void entriesAdded(Collection<Jid> addresses) {
    // 添加好友后会调用此方法
    Logger.e("entriesAdded");
  }

  @Override public void entriesUpdated(Collection<Jid> addresses) {
    Logger.e("entriesUpdated");
  }

  @Override public void entriesDeleted(Collection<Jid> addresses) {
    // 被好友删除后会调用此方法
    Logger.e("entriesDeleted");
  }

  @Override public void presenceChanged(Presence presence) {
    Logger.e("presenceChanged");
  }
}
