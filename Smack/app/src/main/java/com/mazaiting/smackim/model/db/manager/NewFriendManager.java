package com.mazaiting.smackim.model.db.manager;

import android.content.Context;
import com.mazaiting.smackim.IMApplication;
import com.mazaiting.smackim.model.bean.NewFriend;
import com.mazaiting.smackim.model.db.dao.DaoMaster;
import com.mazaiting.smackim.model.db.dao.DaoSession;
import com.mazaiting.smackim.model.db.dao.NewFriendDao;
import java.util.List;
import org.greenrobot.greendao.query.WhereCondition;

/**
 * Created by mazaiting on 2017/9/20.
 */

public class NewFriendManager {

  private static NewFriendManager sNewFriendManager;
  private final DaoSession mDaoSession;

  private NewFriendManager(){
    Context context = IMApplication.getContext();
    DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "smack.db");
    DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
    mDaoSession = daoMaster.newSession();
  }

  /**
   * 单例获取实例
   */
  public static NewFriendManager getInstance(){
    if (null == sNewFriendManager){
      synchronized (NewFriendManager.class){
        if (null == sNewFriendManager){
          sNewFriendManager = new NewFriendManager();
        }
      }
    }
    return sNewFriendManager;
  }

  /**
   * 插入新朋友消息
   * @param newFriend
   */
  public void insert(NewFriend newFriend){
    long count = mDaoSession.queryBuilder(NewFriend.class).where(NewFriendDao.Properties.Jid.eq(newFriend.getJid())).count();
    if (count > 0) return;
    mDaoSession.insert(newFriend);
  }

  /**
   * 查询所有的新朋友消息
   */
  public List<NewFriend> queryAll(){
    return mDaoSession.queryBuilder(NewFriend.class).list();
  }

}
