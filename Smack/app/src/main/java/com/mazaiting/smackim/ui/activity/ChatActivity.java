package com.mazaiting.smackim.ui.activity;

import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.mazaiting.smackim.R;
import com.mazaiting.smackim.base.BaseActivity;
import com.mazaiting.smackim.util.xmpp.manager.ChatOfManager;

public class ChatActivity extends BaseActivity {
  @BindView(R.id.et_content)
  EditText mEditText;
  private String mJid;
  private ChatOfManager mChattingManager;

  @Override protected void initData() {
    mChattingManager = ChatOfManager.getInstance();
    mChattingManager.createChat(mJid);
  }

  @OnClick(R.id.btn_send)
  public void onSend(){
    mChattingManager.sendMessage(mEditText.getText().toString().trim());
  }

  @Override protected void getIntentData() {
    super.getIntentData();
    mJid = getIntent().getStringExtra("jid");
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_chat;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mChattingManager.destroy();
    mChattingManager = null;
  }
}
