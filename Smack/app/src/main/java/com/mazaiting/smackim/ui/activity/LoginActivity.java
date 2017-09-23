package com.mazaiting.smackim.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.mazaiting.smackim.R;
import com.mazaiting.smackim.base.BaseActivity;
import com.mazaiting.smackim.util.xmpp.manager.XMPPManager;
import com.orhanobut.logger.Logger;

public class LoginActivity extends BaseActivity {
  @BindView(R.id.activity_login_input_account) EditText mUsername;
  @BindView(R.id.activity_login_input_password) EditText mPassWord;
  private XMPPManager mXmppManager;

  @Override protected void initData() {

  }

  @OnClick(R.id.activity_login_btn_login) public void login() {
    final String userName = mUsername.getText().toString().trim();
    final String passWord = mPassWord.getText().toString().trim();

    if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
      Toast.makeText(this, "账户或密码不能为空", Toast.LENGTH_LONG).show();
      return;
    }
    new Thread(new Runnable() {
      @Override public void run() {
        mXmppManager = XMPPManager.getXMPPManager();
        mXmppManager.connect();
        boolean isLogin = mXmppManager.login(userName, passWord);
        if (isLogin) {
          startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
          Logger.e("登录失败");
        }
      }
    }).start();
  }

  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.login, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_register) {
      startActivityForResult(new Intent(this, RegisterActivity.class), RESULT_FIRST_USER);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_login;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (RESULT_OK == resultCode) {
      //startActivity(new Intent(LoginActivity.this, MainActivity.class));
      this.finish();
    }
  }
}
