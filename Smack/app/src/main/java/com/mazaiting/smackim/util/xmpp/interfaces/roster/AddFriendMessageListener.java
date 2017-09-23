package com.mazaiting.smackim.util.xmpp.interfaces.roster;

import com.mazaiting.smackim.event.RefreshEvent;
import com.mazaiting.smackim.model.bean.NewFriend;
import com.mazaiting.smackim.model.db.manager.NewFriendManager;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.SubscribeListener;
import org.jivesoftware.smack.util.StringUtils;
import org.jxmpp.jid.Jid;

/**
 * 获取到其他人添加自己的信息监听
 * Created by mazaiting on 2017/9/19.
 */
public class AddFriendMessageListener implements SubscribeListener {
  @Override public SubscribeAnswer processSubscribe(Jid from, Presence subscribeRequest) {
    // TODO 添加好友请求
    Logger.e("processSubscribe");
    // 将请求保存在数据库中

    NewFriend newFriend = new NewFriend();
    newFriend.setJid(String.valueOf(from));
    newFriend.setStatus(subscribeRequest.getStatus());
    newFriend.setMode(String.valueOf(subscribeRequest.getMode()));
    newFriend.setPriority(subscribeRequest.getPriority());
    newFriend.setType(String.valueOf(subscribeRequest.getType()));
    NewFriendManager.getInstance().insert(newFriend);
    EventBus.getDefault().post(new RefreshEvent(RefreshEvent.ADD_FRIEND_REQUEST));
    return null;
  }
}
