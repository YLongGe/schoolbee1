package activity;

import org.json.JSONObject;

import com.tjnu.schoolbee.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import entity.MyUser;
import utils.IpAdressUtils;
import utils.Md5Utils;
import utils.RemoteServiceUtils;
import utils.ShareUtils;
import utils.UtilTools;
import view.CircleImageView;
import view.CustomDialog;

public class LoginActivity extends Activity implements OnClickListener {

	private TextView tv_regiest;
	private Button bt_login;
	private EditText et_acconut;
	private EditText et_userpassword;
	private CircleImageView iv_head;

	private CheckBox cb_remberPWD;

	private CustomDialog dialog;

	ProgressDialog progress;
	
	private ImageButton ib_qq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// UtilTools.setStrategy();
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView(); // 初始化布局

	}

	private void initView() {

		dialog = new CustomDialog(this, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		dialog.setCancelable(false);

		tv_regiest = (TextView) findViewById(R.id.bt_register);
		tv_regiest.setOnClickListener(this);
		bt_login = (Button) findViewById(R.id.bt_login);
		bt_login.setOnClickListener(this);

		et_acconut = (EditText) findViewById(R.id.et_acconut);
		et_userpassword = (EditText) findViewById(R.id.et_userpassword);

		cb_remberPWD = (CheckBox) findViewById(R.id.cb_remberPWD);

		iv_head = (CircleImageView) findViewById(R.id.iv_head);
		iv_head.setBorderWidth(2);
		iv_head.setBorderColor(Color.BLUE);
		
		ib_qq = (ImageButton) findViewById(R.id.ib_qq);
		ib_qq.setOnClickListener(this);
		
		// 初始化头像
		String imgString = ShareUtils.getString(this, "image_title", "");
		BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.add_pic);
		String headString = UtilTools.bitmapToString(this, bd);
		Bitmap bitmap = null;
		if ((bitmap = UtilTools.stringToBitmap(imgString)) != null && !imgString.equals(headString)) {
			iv_head.setImageBitmap(bitmap);
		}


		if (ShareUtils.getBoolean(this, "checkboxBoolean", false)) {
			String username = ShareUtils.getString(LoginActivity.this, "username", "");
			String password = ShareUtils.getString(LoginActivity.this, "password", "");
			et_acconut.setText(username);
			et_userpassword.setText(password);
			cb_remberPWD.setChecked(true);
		}

	}

	// 设置按钮事件监听
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_register: // 点击没有帐号按钮
			Intent intent = new Intent();
			intent.setAction("com.login.RegiestActivity");
			startActivityForResult(intent, 1);
			break;
		case R.id.bt_login: // 点击登录按钮
			String username = et_acconut.getText().toString();
			String password = et_userpassword.getText().toString();

			// loginInSQLserver(username, password);
			loginInBmob(username, password);
			break;
		case R.id.ib_qq:
			Toast.makeText(this, "QQ登录", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	/**
	 * 登录到Bmob 云平台
	 * 
	 * @param username
	 * @param password
	 */
	private void loginInBmob(final String username, final String password) {
		dialog.show();
		MyUser myUser = new MyUser();
		myUser.setUsername(username);
		myUser.setPassword(password);
		myUser.login(new SaveListener<BmobUser>() {

			@Override
			public void done(BmobUser bmobUser, BmobException e) {
				dialog.dismiss();
				if (e == null) {
					Intent intent = new Intent();
					intent.setAction("com.index.IndexActivity");
					intent.putExtra("username", username);
					remberPWD(username, password);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	// 接受注册页面 RegiesActivity返回的数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 1) {
			String username = data.getStringExtra("username");
			et_acconut.setText(username);
			et_userpassword.setText("");
		}

	}
	
	
	/**
	 * 登录远程服务器
	 * 
	 * @param userName
	 * @param password
	 */
	public String loginRemoteService(String userName, String password) {
		String result = null;
		password = Md5Utils.string2MD5(password);
		String url = IpAdressUtils.getURL() + "/JsonWeb/action/login.action?userName=" + userName + "&password="
				+ password;
		String json = RemoteServiceUtils.loginRemoteService(url);

		if (json != null) {
			try {
				JSONObject jsonObject = new JSONObject(json);
				result = jsonObject.get("message").toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (result == null) {
			result = "Login_Fail";
		}

		return result;
	}

	/**
	 * 实现记住密码功能
	 * 
	 * @param username
	 * @param userpass
	 */
	private void remberPWD(String username, String password) {
		if (cb_remberPWD.isChecked()) {
			ShareUtils.putString(this, "username", username);
			ShareUtils.putString(this, "password", password);
			ShareUtils.putBoolean(this, "checkboxBoolean", true);
		} else {
			ShareUtils.putString(this, "username", null);
			ShareUtils.putString(this, "password", null);
			ShareUtils.putBoolean(this, "checkboxBoolean", false);
		}
	}

}
