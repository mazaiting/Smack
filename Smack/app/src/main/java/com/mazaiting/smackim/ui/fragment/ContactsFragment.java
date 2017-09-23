package com.mazaiting.smackim.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.mazaiting.smackim.R;
import com.mazaiting.smackim.base.BaseFragment;
import com.mazaiting.smackim.event.RefreshEvent;
import com.mazaiting.smackim.ui.activity.AddFriendActivity;
import com.mazaiting.smackim.ui.activity.ChatActivity;
import com.mazaiting.smackim.util.xmpp.manager.XMPPManager;
import java.util.List;
import org.jivesoftware.smack.roster.RosterEntry;

/**
 * 联系人Fragment
 * Created by mazaiting on 2017/9/19.
 */

public class ContactsFragment extends BaseFragment {
  @BindView(R.id.vNewFriendUnread_header_contacts)
  View view_red;
  @BindView(R.id.tvName_contact_cv)
  TextView mTvFriendName;

  @Override protected void initData() {
    XMPPManager xmppManager = XMPPManager.getXMPPManager();
    List<RosterEntry> friendList = xmppManager.getFriendList();
    RosterEntry rosterEntry = friendList.get(0);
    mTvFriendName.setText(rosterEntry.getJid());
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_contacts;
  }

  @OnClick(R.id.llNewFriend_header_contacts)
  public void onStartNewFriend(){
    startActivity(new Intent(getActivity(), AddFriendActivity.class));
    view_red.setVisibility(View.GONE);
  }

  @OnClick(R.id.llIndex_contact)
  public void onChat(){
    Intent intent = new Intent(getActivity(), ChatActivity.class);
    intent.putExtra("jid", mTvFriendName.getText().toString().trim());
    startActivity(intent);
  }

  @Override protected void updateRefreshEvent(RefreshEvent event) {
    switch (event.getType()){
      case RefreshEvent.ADD_FRIEND_REQUEST:
        view_red.setVisibility(View.VISIBLE);
        break;
      default:
        break;
    }
  }
}
