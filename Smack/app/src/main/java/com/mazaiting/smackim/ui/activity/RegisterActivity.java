package com.mazaiting.smackim.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import com.mazaiting.smackim.R;
import com.mazaiting.smackim.base.BaseActivity;
import com.mazaiting.smackim.ui.adapter.RegisterAdapter;
import com.mazaiting.smackim.ui.view.RoundedImageView;
import com.mazaiting.smackim.util.FileUtil;
import com.mazaiting.smackim.util.xmpp.manager.AccountOfManager;
import com.mazaiting.smackim.util.xmpp.manager.VCardOfManager;
import com.mazaiting.smackim.util.xmpp.manager.XMPPManager;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

public class RegisterActivity extends BaseActivity {
  /** 选择照片返回码 */
  private static final int selectCode = 123;
  /** 拍照返回码 */
  private static final int cameraCode = 124;
  /** 系统裁剪返回码 */
  private static final int picCode = 125;
  @BindView(R.id.activity_sign_view_pager) ViewPager mViewPager;
  // ViewPager界面
  private View mCreateAccount, mPerfectAccount, mUploadAvatar;
  /** 用户名 */
  private EditText mEtUserName;
  /** 密码 */
  private EditText mEtPassWord;
  /** 注册页面--下一步 */
  private Button mCommitAccount;
  /** 昵称 */
  private EditText mEtNickName;
  /** 昵称页面--下一步 */
  private Button mCommitPerfect;
  /** 圆形头像 */
  private RoundedImageView mRoundedImageView;
  /** 上传头像页面--完成 */
  private Button mCommitUpload;
  /** 用户名，密码，昵称 */
  private String mUserName, mPassWord, mNickName;
  /** xmpp管理者 */
  private XMPPManager mXMPPManager;
  /** 头像字节数 */
  private byte[] mAvatarBytes = null;
  /** 选择图片对话框 */
  private AlertDialog mAlertDialog;
  /** 选择图片时的临时文件 */
  private File mTempFile;

  @Override protected void initData() {
    loadView();
    initView();
    setListener();
  }

  /**
   * 设置监听
   */
  private void setListener() {
    // 第一页
    mCommitAccount.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        final String username = mEtUserName.getText().toString().trim();
        final String password = mEtPassWord.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
          Toast.makeText(RegisterActivity.this, "请检查账户", Toast.LENGTH_LONG).show();
          return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
          Toast.makeText(RegisterActivity.this, "密码不能为空，并且长度大于6位", Toast.LENGTH_LONG).show();
          return;
        }

        new AsyncTask<String, Void, Boolean>() {
          private ProgressDialog dialog;

          @Override protected void onPreExecute() {
            dialog = ProgressDialog.show(RegisterActivity.this, null, "正在连接服务器...");
          }

          @Override protected Boolean doInBackground(String... params) {
            try {
              mXMPPManager = XMPPManager.getXMPPManager();
              mXMPPManager.connect();

              Map<String, String> map = new HashMap<>();
              // 安全的链接上可调用
              //mAccountManager.createAccount(Localpart.from(params[0]), params[1], map);
              AccountOfManager.getInstance().createAccount(params[0], params[1], map);

              boolean isLogin = mXMPPManager.login(params[0], params[1]);
              mUserName = mXMPPManager.getCurrentUser().asEntityBareJidString();
              mPassWord = params[1];
              return true;
            } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | InterruptedException | SmackException.NotConnectedException e) {
              e.printStackTrace();
            }
            return false;
          }

          @Override protected void onPostExecute(Boolean aBoolean) {
            dialog.dismiss();
            if (aBoolean) {
              mViewPager.setCurrentItem(1);
            } else {
              Toast.makeText(RegisterActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
          }
        }.execute(username, password);
      }
    });
    // 第二页 -- 昵称界面
    mCommitPerfect.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String nickname = mEtNickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
          Toast.makeText(RegisterActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
          return;
        }
        mViewPager.setCurrentItem(2);
      }
    });
    // 第三页-- 完成注册
    mCommitUpload.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (null == mAvatarBytes) {
          showDialog();
        } else {
          Logger.e("lsdjfklsdjf");
          // 上传头像，完成注册任务
          new AsyncTask<Void, Void, Boolean>() {
            private ProgressDialog dialog;

            @Override protected void onPreExecute() {
              dialog = ProgressDialog.show(RegisterActivity.this, null, "正在保存账户...");
              Logger.e("onPreExecute");
            }

            @Override protected Boolean doInBackground(Void... params) {
              Logger.e("doInBackground");
              VCardOfManager.getInstance()
                  .setNickName(mNickName)
                  .setAvatar(mAvatarBytes)
                  .saveVCard();
              return true;
            }

            @Override protected void onPostExecute(Boolean aBoolean) {
              Logger.e(aBoolean + "");
              dialog.dismiss();
              if (aBoolean) {
                // TODO 保存账户
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                // 销毁登录界面
                setResult(RESULT_OK);
                finish();
              } else {
                Toast.makeText(RegisterActivity.this, "提交异常", Toast.LENGTH_SHORT).show();
              }
            }
          }.execute();
        }
      }
    });
    // 头像点击 -- 上传头像布局监听
    mRoundedImageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showDialog();
      }
    });
  }

  @Override protected void onPause() {
    super.onPause();
    Logger.e("onPause");
  }

  @Override protected void onStop() {
    super.onStop();
    Logger.e("onStop");
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    Logger.e("onDestroy");
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Logger.e(requestCode+", " + resultCode+", " );
    if (resultCode != RESULT_OK) return;
    switch (requestCode) {
      case cameraCode:// 拍照
        // 获取照片，开始裁剪
        FileUtil.doCropPhoto(RegisterActivity.this, Uri.fromFile(mTempFile), picCode);
        break;
      case selectCode:// 图库
        Uri uri = data.getData();
        String[] pojo = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, pojo, null, null, null);
        if (null != cursor) {
          if (cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            if (!TextUtils.isEmpty(path)) {
              // 文件后缀判断
              if (path.endsWith(".jpg") || path.endsWith(".png")) {
                // 获取照片，开始裁剪
                //FileUtil.doCropPhoto(RegisterActivity.this, uri, picCode);
                Bitmap photoPic = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path), 400, 400, false);
                if (null != photoPic) {
                  mRoundedImageView.setImageDrawable(FileUtil.BitmapToDrawable(this, photoPic));
                  mAvatarBytes = FileUtil.BitmapToBytes(photoPic);
                }
              }
            }
          }
          cursor.close();
        }
        break;
      case picCode: // 裁剪
        if (null != data) {
          Bitmap photoPic = data.getParcelableExtra("data");
          if (null != photoPic) {
            mRoundedImageView.setImageDrawable(FileUtil.BitmapToDrawable(this, photoPic));
            mAvatarBytes = FileUtil.BitmapToBytes(photoPic);
          }
        }
        break;
    }
  }

  /***
   * 显示对话框
   */
  private void showDialog() {
    if (null == mAlertDialog) {
      mAlertDialog = new AlertDialog.Builder(this).setTitle("选择照片")
          .setItems(R.array.select_photo_items, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              switch (which) {
                case 0:
                  mTempFile = FileUtil.getCameraFile();
                  // 进入拍照
                  Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
                  startActivityForResult(intentCamera, cameraCode);
                  break;
                case 1:
                  // 浏览图库
                  Intent intentSelect = new Intent();
                  intentSelect.setType("image/*");
                  intentSelect.setAction(Intent.ACTION_GET_CONTENT);
                  startActivityForResult(intentSelect, selectCode);
                  break;
              }
              mAlertDialog.dismiss();
            }
          })
          .create();
    }
    mAlertDialog.show();
  }

  /***
   * 初始化视图
   */
  private void initView() {
    mEtUserName =
        (EditText) mCreateAccount.findViewById(R.id.activity_sign_view_create_account_account);
    mEtPassWord =
        (EditText) mCreateAccount.findViewById(R.id.activity_sign_view_create_account_password);
    mCommitAccount =
        (Button) mCreateAccount.findViewById(R.id.activity_sign_view_create_account_commit);

    mEtNickName =
        (EditText) mPerfectAccount.findViewById(R.id.activity_sign_view_perfect_account_nickname);
    mCommitPerfect =
        (Button) mPerfectAccount.findViewById(R.id.activity_sign_view_perfect_account_commit);

    mRoundedImageView =
        (RoundedImageView) mUploadAvatar.findViewById(R.id.activity_sign_view_upload_avatar_avatar);

    mCommitUpload =
        (Button) mUploadAvatar.findViewById(R.id.activity_sign_view_upload_avatar_commit);
  }

  /**
   * 加载视图
   */
  private void loadView() {
    if (null != getActionBar()) getActionBar().setHomeButtonEnabled(true);
    // 禁止滑动
    mViewPager.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        return true;
      }
    });

    mCreateAccount = getLayoutInflater().inflate(R.layout.activity_sign_view_create_account, null);
    mPerfectAccount =
        getLayoutInflater().inflate(R.layout.activity_sign_view_perfect_account, null);
    mUploadAvatar = getLayoutInflater().inflate(R.layout.activity_sign_view_upload_avatar, null);

    List<View> views = new ArrayList<>();
    views.add(mCreateAccount);
    views.add(mPerfectAccount);
    views.add(mUploadAvatar);
    RegisterAdapter adapter = new RegisterAdapter(views);
    mViewPager.setAdapter(adapter);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_register;
  }

  @Override public void onBackPressed() {
    // 退出注册判断
    if (TextUtils.isEmpty(mUserName)) {
      finish();
    } else {
      new AlertDialog.Builder(this).setTitle("确定放弃注册")
          .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              // 删除账号，必须要登录注册账号
              AccountOfManager.getInstance().deleteAccount();
              // 关闭连接
              mXMPPManager.disConnect();
              finish();
            }
          })
          .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
            }
          })
          .create()
          .show();
    }
  }
}
