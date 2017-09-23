package com.mazaiting.smackim.util.xmpp.manager;

import com.mazaiting.smackim.util.xmpp.interfaces.other.DestroyInterface;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * Created by mazaiting on 2017/9/20.
 */

public class VCardOfManager implements DestroyInterface{

  /**昵称*/
  private String mNickName;
  /**头像*/
  private byte[] mAvatar;

  private static VCardOfManager sVCardOfManager;
  private VCardManager mVCardManager;

  private VCardOfManager(){
    mVCardManager = XMPPManager.getXMPPManager().getVCardManager();
  }
  public static VCardOfManager getInstance(){
    if (null == sVCardOfManager){
      synchronized (VCardOfManager.class){
        if (null == sVCardOfManager){
          sVCardOfManager = new VCardOfManager();
        }
      }
    }
    return sVCardOfManager;
  }

  public VCardOfManager setAvatar(byte[] avatar) {
    this.mAvatar = avatar;
    return this;
  }

  public VCardOfManager setNickName(String nickName) {
    this.mNickName = nickName;
    return this;
  }

  /**
   * 保存信息
   */
  public void saveVCard(){
    try {
      VCard vCard = mVCardManager.loadVCard();
      if (null != mNickName){
        vCard.setNickName(mNickName);
      }
      if (null != mAvatar){
        vCard.setAvatar(mAvatar);
      }
      mVCardManager.saveVCard(vCard);
    } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | InterruptedException | SmackException.NotConnectedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据jid查询用户信息
   */
  public VCard queryInfoFromJid(String jid) {
    try {
      EntityBareJid entityBareJid = JidCreate.entityBareFrom(jid);
      return mVCardManager.loadVCard(entityBareJid);
    } catch (XmppStringprepException | InterruptedException | SmackException.NotConnectedException | XMPPException.XMPPErrorException | SmackException.NoResponseException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void destroy() {
    mVCardManager = null;
  }
}
