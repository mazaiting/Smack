package com.mazaiting.smackim.util.xmpp.manager;

import com.mazaiting.smackim.util.xmpp.interfaces.login.XMPPConnectionListener;
import com.mazaiting.smackim.util.xmpp.interfaces.roster.AddFriendMessageListener;
import com.mazaiting.smackim.util.xmpp.interfaces.roster.ReceiverFriendStatusListener;
import com.mazaiting.smackim.util.xmpp.interfaces.roster.ReceiverStanzaListener;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaIdFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.PresenceEventListener;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterLoadedListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.iqregister.packet.Registration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.offline.OfflineMessageManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.FullJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * Created by mazaiting on 2017/9/19.
 */

public class XMPPManager {
  /** 客户端在线 */
  public static Presence.Type ON_LINE = Presence.Type.available;
  /** 客户端离线 */
  public static Presence.Type OFF_LINE = Presence.Type.unavailable;
  /** 同意好友请求 */
  public static Presence.Type AGREE_FRIEND = Presence.Type.subscribed;
  /** 拒绝好友请求 */
  public static Presence.Type UNAGREE_FRIEND = Presence.Type.unsubscribed;

  /** XMPP Domain */
  private static String XMPP_DOMAIN = "ez13r5jo73gs8ui";
  /** 主机地址 */
  private static String XMPP_HOST = "172.31.46.5";
  /** XMPP 通信IP 端口号 */
  private static int XMPP_IP_IM = 5222;
  /** 用户名 */
  private String mUserName = "admin";
  /** 密码 */
  private String mPassWord = "admin";
  /** 客户端状态 */
  private Presence.Type isOnLine = Presence.Type.available;

  /** 连接对象 */
  private AbstractXMPPConnection mConnection = null;
  /** XMPP管理类 */
  private static XMPPManager sXMPPManager = null;

  private ReceiverStanzaListener mReceiverStanzaListener;

  private XMPPManager() {
  }

  public static XMPPManager getXMPPManager() {
    if (null == sXMPPManager) {
      synchronized (XMPPManager.class) {
        if (null == sXMPPManager) {
          sXMPPManager = new XMPPManager();
        }
      }
    }
    return sXMPPManager;
  }

  /**
   * 连接服务器
   *
   * @return 连接服务器对象
   */
  public void connect() {
    try {
      XMPPTCPConnectionConfiguration configuration =
          XMPPTCPConnectionConfiguration.builder().setXmppDomain(XMPP_DOMAIN) // 设置域名
              .setHostAddress(InetAddress.getByName(XMPP_HOST)) // 设置主机
              .setPort(5222) // 设置端口
              .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // 禁用SSL连接
              .setCompressionEnabled(false) // 禁用SSL连接
              //.setSendPresence(false) // 设置为离线状态
              .setDebuggerEnabled(true) // 开启调试模式
              .build();
      // 设置需要经过同意才可以添加好友
      Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
      AbstractXMPPConnection connection = new XMPPTCPConnection(configuration);
      connection.connect();// 连接, 可设置监听
      connection.addConnectionListener(new XMPPConnectionListener());
      this.mConnection = connection;
    } catch (UnknownHostException e) {
      Logger.e("UnknownHostException");
      e.printStackTrace();
    } catch (XmppStringprepException e) {
      Logger.e("XmppStringprepException");
      e.printStackTrace();
    } catch (InterruptedException | IOException | XMPPException | SmackException e) {
      Logger.e("ConnectException");
      e.printStackTrace();
    }
  }

  /**
   * 获取连接对象
   */
  AbstractXMPPConnection getConnection() {
    return mConnection;
  }

  /**
   * 登录服务器
   *
   * @param userName 用户名
   * @param passWord 密码
   * @return 是否登录成功。 true 成功; false 失败。
   */
  public boolean login(String userName, String passWord) {
    checkConnect();
    this.mUserName = userName;
    this.mPassWord = userName;
    try {
      if (!mConnection.isAuthenticated()) {
        mConnection.login(userName, passWord);
        isOnLine = Presence.Type.available;
        addListener();
        return true;
      }
      return false;
    } catch (XMPPException | SmackException | InterruptedException | IOException e) {
      Logger.e("登录出错");
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 登录成功后添加监听
   */
  private void addListener() {
    RosterManager.getInstance().addRosterListener();
    addStanzaListener();
  }

  /**
   * 移除监听
   */
  private void removeListener() {
    removeStanzaListener();
    RosterManager.getInstance().removeRosterListener();
  }

  /**
   * 添加好友相关请求
   */
  private void addStanzaListener() {
    StanzaFilter filter = new StanzaTypeFilter(Presence.class);
    mReceiverStanzaListener = new ReceiverStanzaListener();
    mConnection.addAsyncStanzaListener(mReceiverStanzaListener, filter);
    mConnection.addPacketSendingListener(new StanzaListener() {
      @Override public void processStanza(Stanza packet)
          throws SmackException.NotConnectedException, InterruptedException {
        Logger.e("processStanza");
      }
    }, new StanzaFilter() {
      @Override public boolean accept(Stanza stanza) {
        Logger.e("accept");
        return false;
      }
    });
  }

  /**
   * 移除关于好友请求的监听
   */
  private void removeStanzaListener() {
    mConnection.removeAsyncStanzaListener(mReceiverStanzaListener);
  }

  /**
   * 关闭连接
   */
  public void disConnect() {
    checkConnect();
    if (mConnection.isConnected()) mConnection.disconnect();
    mConnection = null;
  }

  /**
   * 退出登录服务器
   */
  public void logout() {
    if (null == mConnection || !mConnection.isConnected()) {
      Logger.e("服务器未连接");
    }
    removeListener();
    isOnLine = Presence.Type.unavailable;
    disConnect();
  }

  /**
   * 设置在线、离线等状态
   */
  public void setOnLine(Presence.Type type) {
    try {
      checkConnect();
      if (isOnLine != type) {
        Presence presence = new Presence(type);
        //presence.setStatus("Gone fishing");// 设置状态消息
        mConnection.sendStanza(presence);
        isOnLine = type;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /***
   * 获取好友列表
   */
  public List<RosterEntry> getFriendList() {
    checkConnect();
    Roster roster = Roster.getInstanceFor(mConnection);
    List<RosterEntry> list = new ArrayList<>();
    Set<RosterEntry> entries = roster.getEntries();
    list.addAll(entries);
    return list;
  }

  /**
   * 同意或者拒绝
   *
   * @param type 类型
   */
  public void handleAddFriend(Presence.Type type, String jid) {
    try {
      Presence presence = new Presence(type);
      presence.setTo(JidCreate.from(jid));
      mConnection.sendStanza(presence);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取当前用户
   */
  public EntityFullJid getCurrentUser() {
    checkConnect();
    return mConnection.getUser();
  }

  /**
   * 检查服务器是否连接
   */
  private void checkConnect() {
    if (null == mConnection || !mConnection.isConnected()) {
      throw new RuntimeException("服务器未连接");
    }
  }

  /**
   * 获取角色信息
   */
  Roster getRoster(){
    checkConnect();
    return Roster.getInstanceFor(mConnection);
  }

  /**
   * 获取聊天对象管理器
   */
  ChatManager getChatManager() {
    checkConnect();
    return ChatManager.getInstanceFor(mConnection);
  }

  /**
   * 获取多人聊天管理者
   */
  MultiUserChatManager getMultiUserChatManager(){
    checkConnect();
    return MultiUserChatManager.getInstanceFor(mConnection);
  }

  /**
   * 获取发送文件的发送器
   */
  FileTransferManager getSendFileTransfer(){
    checkConnect();
    return FileTransferManager.getInstanceFor(mConnection);
  }

  /**
   * 获取信息管理者
   */
  VCardManager getVCardManager() {
    checkConnect();
    return VCardManager.getInstanceFor(mConnection);
  }

  /**
   * 获取账户管理者
   */
  AccountManager getAccountManager() {
    checkConnect();
    return AccountManager.getInstance(mConnection);
  }

  /**
   * 获取离线消息管理者
   */
  OfflineMessageManager getOfflineMessageManager(){
    checkConnect();
    return new OfflineMessageManager(mConnection);
  }
}
