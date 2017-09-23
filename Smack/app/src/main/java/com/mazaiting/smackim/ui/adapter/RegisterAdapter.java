package com.mazaiting.smackim.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * 注册页适配器
 * Created by mazaiting on 2017/9/19.
 */

public class RegisterAdapter extends PagerAdapter{
  private List<View> mViews;

  public RegisterAdapter(List<View> views){
    this.mViews = views;
  }

  @Override public int getCount() {
    return (null == mViews) ? 0 : mViews.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    final View view;
    switch (position){
      case 0:
        view = mViews.get(0);
        break;
      case 1:
        view = mViews.get(1);
        break;
      case 2:
        view = mViews.get(2);
        break;
      default:
        view = mViews.get(position);
    }
    container.addView(view);
    return view;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView(mViews.get(position));
  }
}
