package com.mazaiting.smackim.util.xmpp.interfaces.login;

import com.orhanobut.logger.Logger;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * 服务器连接监听
 * Created by mazaiting on 2017/9/19.
 */

public class XMPPConnectionListener implements ConnectionListener {

  @Override public void connected(XMPPConnection connection) {
    Logger.e("connected");
  }

  @Override public void authenticated(XMPPConnection connection, boolean resumed) {
    Logger.e("authenticated");
  }

  @Override public void connectionClosed() {
    Logger.e("connectionClosed");
  }

  @Override public void connectionClosedOnError(Exception e) {
    Logger.e("connectionClosedOnError");
  }

  @Override public void reconnectionSuccessful() {
    Logger.e("reconnectionSuccessful");
  }

  @Override public void reconnectingIn(int seconds) {
    Logger.e("reconnectingIn");
  }

  @Override public void reconnectionFailed(Exception e) {
    Logger.e("reconnectionFailed");
  }
}
