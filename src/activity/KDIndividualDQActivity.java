package activity;

import java.util.ArrayList;
import java.util.List;

import com.tjnu.schoolbee.R;

import adapter.KDTeamAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import entity.KDTeamData;
import entity.MyUser;
import utils.L;
import utils.StaticClass;
import utils.UtilTools;
import view.CustomDialog;


public class KDIndividualDQActivity extends BaseActivity implements OnClickListener {
	
	private ListView iv_individula;
	private List<KDTeamData> data = new ArrayList<KDTeamData>();
	
	private CustomDialog dialog;
	
	private EditText et_nichen;
	private EditText et_mail;
	private EditText et_property;
	private EditText et_phoneNumber;

	// 就选他了 按钮
	private Button bt_ok;
	
	private final Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case StaticClass.INIT_DATA_OK:
				L.i("flagIsOnedata----" + data.size());
				KDTeamAdapter adapter = new KDTeamAdapter(KDIndividualDQActivity.this, data);
				adapter.notifyDataSetChanged();
				iv_individula.setAdapter(adapter);
				UtilTools.setListViewHeightBasedOnChildren(iv_individula);
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kd_individula_dq);
		
		initView();
		getData();
	}
	
	private void initView() {
		
		int width = (int) (UtilTools.getWindowWidth(this) * 0.8);
		int height = (int) (UtilTools.getWindowHeigth(this) * 0.6);
		
		dialog = new CustomDialog(this, width, height, R.layout.dialog_team_dq, R.style.Theme_dialog,
				Gravity.CENTER, R.style.pop_anim_style);

		dialog.setCancelable(true);
		
		
		et_nichen = (EditText) dialog.findViewById(R.id.et_nichen);
		et_mail = (EditText) dialog.findViewById(R.id.et_mail);
		et_property = (EditText) dialog.findViewById(R.id.et_property);
		et_phoneNumber = (EditText) dialog.findViewById(R.id.et_phoneNumber);

		bt_ok = (Button) dialog.findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(this);
		
		
		iv_individula = (ListView) findViewById(R.id.iv_individula);
		
		iv_individula.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String team_name = data.get(position).getTeam_name();
				setDataToView(team_name);
				dialog.show();

			}
		});
		
	}

	//从Bmob获取数据
	private void getData() {

		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.addWhereEqualTo("teamFlag", "1");
		query.order("-createdAt").setLimit(20).findObjects(new FindListener<MyUser>() {

			@Override
			public void done(List<MyUser> object, BmobException e) {
				if (e == null) {
					L.i("getData size = " + object.size());
					data.clear();
					KDTeamData bufferData = null;
					MyUser user = null;
					for (int i = 0; i < object.size(); i++) {
						bufferData = new KDTeamData();
						user = object.get(i);
						bufferData.setTeam_name(user.getUsername().toString());
						if (user.getTeamFlag().equals("1")){
							bufferData.setTeam_property("个人代取");
						} else{
							bufferData.setTeam_property(user.getTeamFlag() + "团队");
						}
						bufferData.setTeam_head(user.getImgHead().toString());
						data.add(bufferData);
					}
					handler.sendEmptyMessage(StaticClass.INIT_DATA_OK);
				} else {
					L.i("getData 失败");
				}
			}
		});

	}
	
	//设置数据到dialog上
	private void setDataToView(String team_name) {
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.addWhereEqualTo("username", team_name);
		query.findObjects(new FindListener<MyUser>() {

			@Override
			public void done(List<MyUser> users, BmobException e) {
				if (e == null) {
					MyUser user = users.get(0);
					String username = user.getUsername();
					String phoneNumber = user.getMobilePhoneNumber();
					String email = user.getEmail();
					et_nichen.setText(username);
					et_mail.setText(email);
					et_phoneNumber.setText(phoneNumber);
					et_property.setText("个人代取");

				} else {
					Toast.makeText(KDIndividualDQActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_ok:
			Intent intent = new Intent(this, KDDQInfomationActivity.class);
			intent.putExtra("dq_phone", et_phoneNumber.getText().toString());
			startActivity(intent);
			dialog.dismiss();
			break;

		default:
			break;
		}
		
	}



}	










