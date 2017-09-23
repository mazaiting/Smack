package com.mazaiting.smackim.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Activity基类
 * Created by mazaiting on 2017/9/19.
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    getIntentData();
    initData();
  }

  /**
   * 初始化数据
   */
  protected abstract void initData();

  /**
   * 获取意图数据
   */
  protected void getIntentData() {}

  /**
   * 设置布局ID
   */
  protected abstract int getLayoutId();
}
