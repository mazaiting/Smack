package com.mazaiting.smackim.util.xmpp.manager;

import com.mazaiting.smackim.util.xmpp.interfaces.other.DestroyInterface;
import com.mazaiting.smackim.util.xmpp.interfaces.roster.AddFriendMessageListener;
import com.mazaiting.smackim.util.xmpp.interfaces.roster.ReceiverFriendStatusListener;
import com.orhanobut.logger.Logger;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.PresenceEventListener;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterLoadedListener;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.FullJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * 角色管理者
 * Created by mazaiting on 2017/9/20.
 */
public class RosterManager implements DestroyInterface {

  private static RosterManager sFriendManager;
  private Roster mRoster;
  private ReceiverFriendStatusListener mReceiverFriendStatusListener;
  private AddFriendMessageListener mAddFriendMessageListener;
  private RosterManager() {
    mRoster = XMPPManager.getXMPPManager().getRoster();
  }

  public static RosterManager getInstance() {
    if (null == sFriendManager) {
      synchronized (RosterManager.class) {
        if (null == sFriendManager) {
          sFriendManager = new RosterManager();
        }
      }
    }
    return sFriendManager;
  }

  /**
   * 主动添加好友
   *
   * @param jid jid
   * @param name 姓名
   * @param group 组
   */
  public void addFriend(String jid, String name, String[] group)
      throws XmppStringprepException, SmackException.NotLoggedInException,
      XMPPException.XMPPErrorException, SmackException.NotConnectedException, InterruptedException,
      SmackException.NoResponseException {
    mRoster.createEntry(JidCreate.bareFrom(jid), name, group);
  }

  /**
   * 删除好友
   *
   * @throws XMPPException.XMPPErrorException
   * @throws SmackException.NotConnectedException
   */
  public void removeFriend(String jid)
      throws XmppStringprepException, SmackException.NotLoggedInException,
      XMPPException.XMPPErrorException, SmackException.NotConnectedException, InterruptedException,
      SmackException.NoResponseException {
    RosterEntry entry = mRoster.getEntry(JidCreate.bareFrom(jid));
    mRoster.removeEntry(entry);
  }

  /**
   * 获取指定账号的好友信息
   */
  public RosterEntry getFriend(String jid) throws XmppStringprepException {
    return mRoster.getEntry(JidCreate.bareFrom(jid));
  }

  /**
   * 添加角色相关监听
   */
  public void addRosterListener(){

    // 接收到好友信息变化
    mReceiverFriendStatusListener = new ReceiverFriendStatusListener();
    mRoster.addRosterListener(mReceiverFriendStatusListener);
    // 接收到添加好友信息
    mAddFriendMessageListener = new AddFriendMessageListener();
    mRoster.addSubscribeListener(mAddFriendMessageListener);
    mRoster.addPresenceEventListener(new PresenceEventListener() {
      @Override public void presenceAvailable(FullJid address, Presence availablePresence) {
        Logger.e("presenceAvailable");
      }

      @Override public void presenceUnavailable(FullJid address, Presence presence) {
        Logger.e("presenceUnavailable");
      }

      @Override public void presenceError(Jid address, Presence errorPresence) {
        Logger.e("presenceError");
      }

      @Override public void presenceSubscribed(BareJid address, Presence subscribedPresence) {
        Logger.e("presenceSubscribed");
      }

      @Override public void presenceUnsubscribed(BareJid address, Presence unsubscribedPresence) {
        Logger.e("presenceUnsubscribed");
      }
    });
    mRoster.addRosterLoadedListener(new RosterLoadedListener() {
      @Override public void onRosterLoaded(Roster roster) {
        Logger.e("onRosterLoaded");
      }

      @Override public void onRosterLoadingFailed(Exception exception) {
        Logger.e("onRosterLoadingFailed");
      }
    });
  }

  /**
   * 移除关于角色的监听
   */
  public void removeRosterListener() {
    // 接收到好友信息变化
    mRoster.removeRosterListener(mReceiverFriendStatusListener);
    // 接收到添加好友信息
    mRoster.removeSubscribeListener(mAddFriendMessageListener);
  }

  @Override public void destroy() {
    mRoster = null;
  }
}
