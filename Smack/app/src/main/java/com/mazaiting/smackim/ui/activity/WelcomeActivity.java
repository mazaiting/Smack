package com.mazaiting.smackim.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import butterknife.BindView;
import com.mazaiting.smackim.R;
import com.mazaiting.smackim.base.BaseActivity;
import com.mazaiting.smackim.ui.view.SecretTextView;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends BaseActivity {
  @BindView(R.id.secret_text_activity_welcome)
  SecretTextView mSecretTextView;
  @Override protected void initData() {
    // 设置动画
    mSecretTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.welcome_hint_bottom_in));
    mSecretTextView.setDuration(2000);
    mSecretTextView.toggle();

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        WelcomeActivity.this.finish();
      }
    }, 2500);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_welcome;
  }
}
