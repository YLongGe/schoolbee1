package activity;

import com.tjnu.schoolbee.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import entity.MyUser;

public class USetingActivity extends BaseActivity implements OnClickListener {

	private LinearLayout ll_updata_password;
	private LinearLayout ll_updata_phone_number;

	private Button btn_logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seting);

		initView();
	}

	// 初始化布局
	private void initView() {
		ll_updata_password = (LinearLayout) findViewById(R.id.ll_updata_password);
		ll_updata_password.setOnClickListener(this);
		ll_updata_phone_number = (LinearLayout) findViewById(R.id.ll_updata_phone_number);
		ll_updata_phone_number.setOnClickListener(this);

		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_updata_password:
			startActivity(new Intent(this, UModifyActivity.class));
			break;
		case R.id.ll_updata_phone_number:
			startActivity(new Intent(this, UIdentifyPhoneActivity.class));
			break;

		case R.id.btn_logout:
			MyUser.logOut();   //清除缓存用户对象
			startActivity(new Intent(this, LoginActivity.class));
			Toast.makeText(this, "退出成功", Toast.LENGTH_LONG).show();
			//BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
			break;
		default:
			break;
		}

	}
}
