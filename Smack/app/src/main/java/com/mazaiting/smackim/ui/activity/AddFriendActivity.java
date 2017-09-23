package com.mazaiting.smackim.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.mazaiting.smackim.R;
import com.mazaiting.smackim.base.BaseActivity;
import com.mazaiting.smackim.model.bean.NewFriend;
import com.mazaiting.smackim.model.db.manager.NewFriendManager;
import com.mazaiting.smackim.util.xmpp.manager.AccountOfManager;
import com.mazaiting.smackim.util.xmpp.manager.RosterManager;
import com.mazaiting.smackim.util.xmpp.manager.VCardOfManager;
import com.mazaiting.smackim.util.xmpp.manager.XMPPManager;
import com.orhanobut.logger.Logger;
import java.util.List;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

public class AddFriendActivity extends BaseActivity {
  @BindView(R.id.etContent_new_friend)
  EditText mEtSearch;
  @BindView(R.id.rvNewFriend_new_friend)
  RecyclerView mRecyclerView;

  @Override protected void initData() {
    // TODO 从数据库中读取信息显示
    NewFriendManager newFriendManager = NewFriendManager.getInstance();
    List<NewFriend> newFriends = newFriendManager.queryAll();
    for (int i = 0;i < newFriends.size(); i++){
      NewFriend newFriend = newFriends.get(i);
      VCard vCard = VCardOfManager.getInstance().queryInfoFromJid(newFriend.getJid());
      Logger.e(vCard.toString());
    }
  }

  @OnClick(R.id.btn_search)
  public void onSearch(){

  }

  //admin@ez13r5jo73gs8ui, subscribe, available
  @OnClick(R.id.btn_agree)
  public void onAgree(){
    String jid = "admin@ez13r5jo73gs8ui";
    XMPPManager.getXMPPManager().handleAddFriend(XMPPManager.AGREE_FRIEND, jid);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_add_friend;
  }
}
