package com.mazaiting.smackim.util.xmpp.manager;

import com.mazaiting.smackim.util.xmpp.interfaces.other.DestroyInterface;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

/**
 * Created by mazaiting on 2017/9/20.
 */

public class MultiChatOfManager implements DestroyInterface{


  private static MultiChatOfManager sMultiChatOfManager;
  private final MultiUserChatManager mMultiUserChatManager;

  private MultiChatOfManager(){
    mMultiUserChatManager = XMPPManager.getXMPPManager().getMultiUserChatManager();
  }
  public static MultiChatOfManager getInstance(){
    if (null == sMultiChatOfManager){
      synchronized (MultiChatOfManager.class){
        if (null == sMultiChatOfManager){
          sMultiChatOfManager = new MultiChatOfManager();
        }
      }
    }
    return sMultiChatOfManager;
  }

  /**
   * 创建群聊聊天室
   * @param roomName		聊天室名字
   * @param nickName		创建者在聊天室中的昵称
   * @param password		聊天室密码
   * @return
   */
  //public MultiUserChat createChatRoom(String roomName, String nickName, String password) {
  //  MultiUserChat muc = null;
  //  try {
  //    // 创建一个MultiUserChat
  //    //muc = mMultiUserChatManager.getMultiUserChat(roomName + "@conference." + connection.getServiceName());
  //    muc = mMultiUserChatManager.getMultiUserChat(roomName + "@conference." + connection.getServiceName());
  //    // 创建聊天室
  //    boolean isCreated = muc.createOrJoin(nickName);
  //    if(isCreated) {
  //      // 获得聊天室的配置表单
  //      Form form = muc.getConfigurationForm();
  //      // 根据原始表单创建一个要提交的新表单。
  //      Form submitForm = form.createAnswerForm();
  //      // 向要提交的表单添加默认答复
  //      List fields = form.getFields();
  //      for(int i = 0; fields != null && i < fields.size(); i++) {
  //        if(FormField.Type.hidden != fields.get(i).getType() &&
  //            fields.get(i).getVariable() != null) {
  //          // 设置默认值作为答复
  //          submitForm.setDefaultAnswer(fields.get(i).getVariable());
  //        }
  //      }
  //      // 设置聊天室的新拥有者
  //      List owners = new ArrayList();
  //      owners.add(connection.getUser());// 用户JID
  //      submitForm.setAnswer("muc#roomconfig_roomowners", owners);
  //      // 设置聊天室是持久聊天室，即将要被保存下来
  //      submitForm.setAnswer("muc#roomconfig_persistentroom", true);
  //      // 房间仅对成员开放
  //      submitForm.setAnswer("muc#roomconfig_membersonly", false);
  //      // 允许占有者邀请其他人
  //      submitForm.setAnswer("muc#roomconfig_allowinvites", true);
  //      if(password != null && password.length() != 0) {
  //        // 进入是否需要密码
  //        submitForm.setAnswer("muc#roomconfig_passwordprotectedroom",  true);
  //        // 设置进入密码
  //        submitForm.setAnswer("muc#roomconfig_roomsecret", password);
  //      }
  //      // 能够发现占有者真实 JID 的角色
  //      // submitForm.setAnswer("muc#roomconfig_whois", "anyone");
  //      // 登录房间对话
  //      submitForm.setAnswer("muc#roomconfig_enablelogging", true);
  //      // 仅允许注册的昵称登录
  //      submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
  //      // 允许使用者修改昵称
  //      submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
  //      // 允许用户注册房间
  //      submitForm.setAnswer("x-muc#roomconfig_registration", false);
  //      // 发送已完成的表单（有默认值）到服务器来配置聊天室
  //      muc.sendConfigurationForm(submitForm);
  //    }
  //  } catch (XMPPException | SmackException e) {
  //    e.printStackTrace();
  //    return null;
  //  }
  //  return muc;
  //}

  /**
   * 加入一个群聊聊天室
   * @param roomName		聊天室名字
   * @param nickName		用户在聊天室中的昵称
   * @param password		聊天室密码
   * @return
   */
  //public MultiUserChat joinChatRoom(String roomName,  String nickName, String password) {
  //  if(!isConnected()) {
  //    throw new NullPointerException("服务器连接失败，请先连接服务器");
  //  }
  //  try {
  //    // 使用XMPPConnection创建一个MultiUserChat窗口
  //    MultiUserChat muc = MultiUserChatManager.getInstanceFor(connection).
  //        getMultiUserChat(roomName + "@conference." + connection.getServiceName());
  //    // 聊天室服务将会决定要接受的历史记录数量
  //    DiscussionHistory history = new DiscussionHistory();
  //    history.setMaxChars(0);
  //    // history.setSince(new Date());
  //    // 用户加入聊天室
  //    muc.join(nickName, password);
  //    return muc;
  //  } catch (XMPPException | SmackException e) {
  //    e.printStackTrace();
  //    return null;
  //  }
  //}

  /**
   * 群聊发送消息
   * @param message 消息内容
   */
  public void sendMessage(String message){
    //multiUserChat.sendMessage(msg);//发送群聊消息
  }

  @Override public void destroy() {
    sMultiChatOfManager = null;
  }
}
