package activity;

import org.json.JSONObject;

import com.tjnu.schoolbee.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import utils.IpAdressUtils;
import utils.Md5Utils;
import utils.RemoteServiceUtils;

public class UModifyActivity extends BaseActivity implements OnClickListener {

	private EditText passwordest;
	private EditText repasswordest;
	private EditText reppasswordest;
	private CheckBox check;

	private Button btn_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_u_modify);

		initView();

	}

	private void initView() {
		passwordest = (EditText) findViewById(R.id.passwordest);
		repasswordest = (EditText) findViewById(R.id.repasswordest);
		reppasswordest = (EditText) findViewById(R.id.reppasswordest);

		check = (CheckBox) findViewById(R.id.check);
		check.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (check.isChecked()) {
					passwordest.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					repasswordest.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {
					passwordest.setTransformationMethod(PasswordTransformationMethod.getInstance());
					repasswordest.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});

		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String beforPwd = passwordest.getText().toString().trim();
		String pwd = repasswordest.getText().toString().trim();
		String rePwd = reppasswordest.getText().toString().trim();
	//	String username = getIntent().getStringExtra("username");
		switch (v.getId()) {
		case R.id.btn_login:
			if (pwd.equals(rePwd) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(rePwd)
					&& !TextUtils.isEmpty(beforPwd)) {
				// 从sql server 修改
				// modifyPWDFromSQL(username, rePwd).equals("ok");

				// 从Bmob 云后端修改
				modifyPWDFromBmob(beforPwd, rePwd);
			} else if (!pwd.equals(rePwd)) {
				Toast.makeText(UModifyActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
			} else {

				Toast.makeText(UModifyActivity.this, "请检查信息填写是否完整哟", Toast.LENGTH_SHORT).show();
			}

		default:
			break;
		}
	}

	/**
	 * 修改密码从Bmob
	 * @param beforPwd
	 * @param rePwd
	 */
	private void modifyPWDFromBmob(String beforPwd, String rePwd) {
		BmobUser.updateCurrentUserPassword(beforPwd, rePwd, new UpdateListener() {

			@Override
			public void done(BmobException e) {
				if (e == null) {
					Toast.makeText(UModifyActivity.this, "密码修改成功，可以用新密码进行登录啦", Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(UModifyActivity.this, "旧密码不正确", Toast.LENGTH_LONG).show();
				}
			}

		});

	}
	
	/**
	 * 修改密码从 Sqlserver
	 * @param username
	 * @param password
	 * @return 
	 */
	public String modifyPWDFromSQL(String username, String password) {
		String result = "no";
		password = Md5Utils.string2MD5(password);
		try {
			String url = IpAdressUtils.getURL() + "/JsonWeb/modify/modifyPassword.action?username=" + username
					+ "&password=" + password;
			Log.i("Test", url);
			String json = RemoteServiceUtils.loginRemoteService(url);
			if (json != null) {
				JSONObject jsonObject = new JSONObject(json);
				result = jsonObject.get("Res").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
