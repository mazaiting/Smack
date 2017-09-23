package com.mazaiting.smackim.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.mazaiting.smackim.base.BaseFragment;
import com.mazaiting.smackim.ui.fragment.ContactsFragment;
import com.mazaiting.smackim.ui.fragment.SessionsFragment;

/**
 * 主界面碎片适配器
 * Created by mazaiting on 2017/9/19.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
  public FragmentAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    BaseFragment fragment = null;
    switch (position){
      case 0:
        fragment = new SessionsFragment();
        break;
      case 1:
        fragment = new ContactsFragment();
        break;
      case 2:
        fragment = new ContactsFragment();
        break;
    }
    return fragment;
  }

  @Override public int getCount() {
    return 3;
  }
}
