package com.mazaiting.smackim.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 新朋友表
 * Created by mazaiting on 2017/9/20.
 */
@Entity()
public class NewFriend {

  @Id(autoincrement = true) long id;

  String jid;

  int priority;

  String status;

  String mode;

  String Type;

  public NewFriend() {
  }

  @Generated(hash = 318996583)
  public NewFriend(long id, String jid, int priority, String status, String mode,
      String Type) {
    this.id = id;
    this.jid = jid;
    this.priority = priority;
    this.status = status;
    this.mode = mode;
    this.Type = Type;
  }

  public String getJid() {
    return jid;
  }

  public void setJid(String jid) {
    this.jid = jid;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getType() {
    return Type;
  }

  public void setType(String type) {
    Type = type;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override public String toString() {
    return "NewFriend{" +
        "Type='" + Type + '\'' +
        ", mode='" + mode + '\'' +
        ", status='" + status + '\'' +
        ", priority=" + priority +
        ", jid='" + jid + '\'' +
        ", id=" + id +
        '}';
  }
}
