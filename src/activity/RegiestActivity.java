package activity;

import org.json.JSONObject;

import com.tjnu.schoolbee.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import entity.MyUser;
import utils.IpAdressUtils;
import utils.Md5Utils;
import utils.RemoteServiceUtils;
import utils.UtilTools;
import view.AuthCodeView;
import view.CustomDialog;

public class RegiestActivity extends BaseActivity implements OnClickListener {
	// private static final String TABLE_NAME = "user.db"; // 数据库名
	// private UserDb db;
	private AuthCodeView mauthCodeView;

	// 接收用户输入的用户名、密码、验证码
	private EditText et_user_name;
	private EditText et_password;
	private EditText et_again_password;
	private EditText et_check; // 验证码的EditText
	private Button bt_ok;

	private CustomDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		UtilTools.setStrategy(); // 设置策略
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regiest);
		initView(); // 初始化布局
	}

	// 初始化布局
	public void initView() {
		et_user_name = (EditText) findViewById(R.id.et_user_name);
		et_password = (EditText) findViewById(R.id.et_password);
		et_again_password = (EditText) findViewById(R.id.et_again_password);

		dialog = new CustomDialog(this, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		dialog.setCancelable(false);

		bt_ok = (Button) findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(this);

		mauthCodeView = (AuthCodeView) findViewById(R.id.bt_check);
		et_check = (EditText) findViewById(R.id.et_check);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_ok: // 点击完成按钮
			String username = et_user_name.getText().toString().trim();
			String password = et_password.getText().toString().trim();
			String again_password = et_again_password.getText().toString().trim();
			String codeString = et_check.getText().toString().trim(); // 用户输入的验证码
			regiesting(username, password, again_password, codeString);
			break;
		default:
			break;
		}
	}

	private void regiesting(String username, String password, String again_password, String codeString) {
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && password.equals(again_password)
				&& codeString.equals(mauthCodeView.getAuthCode())) {
			dialog.show();
			// 注册到 bmob
			registFromBmob(username, password);
			// 注册到sqlserver
			// regiestFromSQLServer(username, password);
		} else if (TextUtils.isEmpty(username)) {
			Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
		} else if (!password.equals(again_password)) {
			Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
		} else if (!codeString.equals(mauthCodeView.getAuthCode())) {
			Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "信息填写不完整", Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 注册到 bmob云端
	 * 
	 * @param username
	 * @param password
	 */
	private void registFromBmob(String username, String password) {
		MyUser myUser = new MyUser();
		myUser.setUsername(username);
		myUser.setPassword(password);

		// 注意：不能用save方法进行注册
		myUser.signUp(new SaveListener<MyUser>() {
			@Override
			public void done(MyUser s, BmobException e) {
				dialog.dismiss();
				if (e == null) {
					Toast.makeText(RegiestActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(RegiestActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * 远程注册到SQLServer 2012
	 * 
	 * @param userName
	 * @param password
	 */
	public void regiestFromSQLServer(String userName, String password) {
		String result = null;
		password = Md5Utils.string2MD5(password);
		String url = IpAdressUtils.getURL() + "/JsonWeb/myaction/doGet.action?userName=" + userName + "&password="
				+ password;
		String json = RemoteServiceUtils.loginRemoteService(url);
		try {
			if (json != null) {
				JSONObject jsonObject = new JSONObject(json);
				result = jsonObject.get("message").toString();

			} else {
				result = "注册失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result.equals("注册成功")) {
			Intent intent = new Intent();
			intent.putExtra("username", userName);
			intent.putExtra("password", password);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			// 创建提示框提醒是否登录成功
			AlertDialog.Builder builder = new Builder(RegiestActivity.this);
			builder.setTitle("提示").setMessage(result).setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).create().show();
		}

	}

}
