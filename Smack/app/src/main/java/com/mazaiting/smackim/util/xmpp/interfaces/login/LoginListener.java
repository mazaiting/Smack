package com.mazaiting.smackim.util.xmpp.interfaces.login;

/**
 * 登录监听
 * Created by mazaiting on 2017/9/19.
 */
public interface LoginListener {
  /**
   * 登录成功监听
   */
  void onSuccess();

  /**
   * 登录失败监听
   * @param e 异常结果
   */
  void onFailed(Exception e);
}
