package com.mazaiting.smackim.util.xmpp.manager;

import com.mazaiting.smackim.util.xmpp.interfaces.other.DestroyInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaIdFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.iqregister.packet.Registration;

/**
 * Created by mazaiting on 2017/9/20.
 */

public class AccountOfManager implements DestroyInterface{

  private static AccountOfManager sAccountOfManager;
  private AccountManager mAccountManager;
  private final XMPPManager mXmppManager;

  private AccountOfManager(){
    mXmppManager = XMPPManager.getXMPPManager();
    mAccountManager = mXmppManager.getAccountManager();
  }
  public static AccountOfManager getInstance(){
    if (null == sAccountOfManager){
      synchronized (AccountOfManager.class){
        if (null == sAccountOfManager){
          sAccountOfManager = new AccountOfManager();
        }
      }
    }
    return sAccountOfManager;
  }

  /**
   * 创建用户
   * @param userName 用户名
   * @param passWord 密码
   * @param attributes 属性
   */
  public void createAccount(String userName, String passWord, Map<String, String> attributes)
      throws SmackException.NotConnectedException, InterruptedException,
      XMPPException.XMPPErrorException, SmackException.NoResponseException {
    if (StringUtils.isNullOrEmpty(userName)) {
      throw new IllegalArgumentException("Username must not be null");
    }
    if (StringUtils.isNullOrEmpty(passWord)) {
      throw new IllegalArgumentException("Password must not be null");
    }

    attributes.put("username", userName);
    attributes.put("password", passWord);
    Registration reg = new Registration(attributes);
    reg.setType(IQ.Type.set);
    reg.setTo(mXmppManager.getConnection().getXMPPServiceDomain());
    createStanzaCollectorAndSend(reg).nextResultOrThrow();
  }

  private StanzaCollector createStanzaCollectorAndSend(IQ req)
      throws SmackException.NotConnectedException, InterruptedException {
    return mXmppManager.getConnection().createStanzaCollectorAndSend(new StanzaIdFilter(req.getStanzaId()), req);
  }

  /**
   * 删除账户
   */
  public void deleteAccount(){
    try {
      mAccountManager.deleteAccount();
    } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | InterruptedException | SmackException.NotConnectedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取账户所有属性
   */
  public List<String> getAccountAttributes(){
    try {
      Set<String> accountAttributes = mAccountManager.getAccountAttributes();
      List<String> list = new ArrayList<>();
      list.addAll(accountAttributes);
      return list;
    } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | InterruptedException | SmackException.NotConnectedException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override public void destroy() {
    mAccountManager = null;
  }
}
