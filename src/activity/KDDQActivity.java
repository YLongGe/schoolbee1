package activity;

import java.util.List;

import com.tjnu.schoolbee.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import entity.MyUser;

public class KDDQActivity extends BaseActivity implements OnClickListener{
	
	private TextView et_nichen;
	private TextView et_mail;
	private TextView et_property;
	private TextView et_phoneNumber;
	
	private Button bt_ok;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_team_dq);
		
		initView();
		setDataToView();
	}

	private void setDataToView() {
		String team_name = getIntent().getStringExtra("team_name");
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.addWhereEqualTo("username", team_name);
		query.findObjects(new FindListener<MyUser>() {

			@Override
			public void done(List<MyUser> users, BmobException e) {
				if(e==null){
		            //toast("查询用户成功:"+object.size());
					MyUser user = users.get(0);
					String username = user.getUsername();
					String phoneNumber = user.getMobilePhoneNumber();
					String email = user.getEmail();
					String property = user.getTeamFlag();
					if (property.equals("1")){
						property = "个人代取";
					}else{
						property = "团队代取";
					}
					et_nichen.setText(username);
					et_mail.setText(email);
					et_phoneNumber.setText(phoneNumber);
					et_property.setText(property);
					
		        }else{
		           // toast("更新用户信息失败:" + e.getMessage());
		        	Toast.makeText(KDDQActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		        }
			}
		});
		
	}

	private void initView() {
		et_nichen = (TextView) findViewById(R.id.et_nichen);
		et_mail = (TextView) findViewById(R.id.et_mail);
		et_property = (TextView) findViewById(R.id.et_property);
		et_phoneNumber = (TextView) findViewById(R.id.et_phoneNumber);
		
		bt_ok = (Button) findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_ok:
			
			break;

		default:
			break;
		}
		
	}
}
