package com.mazaiting.smackim.event;

/**
 * Created by mazaiting on 2017/9/20.
 */

public class RefreshEvent {
  public static final int ADD_FRIEND_REQUEST = 0x01;

  private int type;

  public RefreshEvent(int type){
    this.type = type;
  }

  public int getType() {
    return type;
  }
}
