package com.mazaiting.smackim.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.mazaiting.smackim.R;
import com.mazaiting.smackim.base.BaseActivity;
import com.mazaiting.smackim.event.RefreshEvent;
import com.mazaiting.smackim.ui.adapter.FragmentAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {
  private static int SESSIONS = 0x00;
  private static int CONTACTS = 0x01;
  private static int SETTINGS = 0x02;
  @BindView(R.id.view_pager)
  ViewPager mViewPager;
  @BindView(R.id.jpTabBar)
  JPTabBar mJPTabBar;
  @Titles
  private static final String[] mTitles = {"会话","联系人","设置"};

  @SeleIcons
  private static final int[] mSelIcons = {R.mipmap.message_press,R.mipmap.contacts_press,R.mipmap.settings_press};

  @NorIcons
  private static final int[] mNormalIcons = {R.mipmap.message_normal, R.mipmap.contacts_normal, R.mipmap.settings_normal};

  @Override protected void initData() {
    mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
    mJPTabBar.setTitles(mTitles).setSelectedIcons(mSelIcons).setNormalIcons(mNormalIcons).generate();;
    mJPTabBar.setContainer(mViewPager);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        return true;
      case R.id.action_add:
        startActivity(new Intent(this, AddFriendActivity.class));
        return true;
      case R.id.action_account:
        startActivity(new Intent(this, UserInfoActivity.class));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Subscribe
  public void onRefreshEvent(final RefreshEvent event){
    runOnUiThread(new Runnable() {
      @Override public void run() {
        switch (event.getType()){
          case RefreshEvent.ADD_FRIEND_REQUEST:
            mJPTabBar.showCircleBadge(CONTACTS);
            break;
          default:
            break;
        }
      }
    });
  }

  @Override protected void onStart() {
    super.onResume();
    EventBus.getDefault().register(this);
  }

  @Override protected void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }
}
