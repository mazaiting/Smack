package com.mazaiting.smackim.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.mazaiting.smackim.event.RefreshEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Fragment基类
 * Created by mazaiting on 2017/9/19.
 */

public abstract class BaseFragment extends Fragment {

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutId(), container, false);
    ButterKnife.bind(this, view);
    initData();
    return view;
  }

  @Override public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe
  public void onRefreshEvent(final RefreshEvent event){
    getActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        updateRefreshEvent(event);
      }
    });
  }

  /**
   * 更新界面通知
   */
  protected abstract void updateRefreshEvent(RefreshEvent event);

  /**
   * 初始化数据
   */
  protected abstract void initData();

  /**
   * 获取布局ID
   */
  protected abstract int getLayoutId();
}
