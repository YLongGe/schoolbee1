package activity;

import java.util.ArrayList;
import java.util.List;

import com.tjnu.schoolbee.R;

import adapter.KDTeamAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import entity.KDTeamData;
import entity.MyUser;
import utils.L;
import utils.StaticClass;
import utils.UtilTools;
import view.CustomDialog;

public class KDTeamDQActivity extends BaseActivity implements OnClickListener{

	private ListView lv_team;

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
			case StaticClass.LOAD_DATA_OK:
				KDTeamAdapter adapter = new KDTeamAdapter(KDTeamDQActivity.this, data);
				adapter.notifyDataSetChanged();
				lv_team.setAdapter(adapter);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kd_team_dq);

		initView();
		getData();
	}

	private void initView() {
		int width = (int) (UtilTools.getWindowWidth(this) * 0.8);
		int height = (int) (UtilTools.getWindowHeigth(this) * 0.6);

		dialog = new CustomDialog(this, width, height, R.layout.dialog_team_dq, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		et_nichen = (EditText) dialog.findViewById(R.id.et_nichen);
		et_mail = (EditText) dialog.findViewById(R.id.et_mail);
		et_property = (EditText) dialog.findViewById(R.id.et_property);
		et_phoneNumber = (EditText) dialog.findViewById(R.id.et_phoneNumber);

		bt_ok = (Button) dialog.findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(this);

		lv_team = (ListView) findViewById(R.id.lv_team);
		lv_team.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				L.i("data size" + data.size());
				String team_name = data.get(position).getTeam_name();
				setDataToView(team_name);
				dialog.show();

			}
		});

	}

	private void getData() {

		//复合查询，不能直接查找某一列的值
		List<BmobQuery<MyUser>> queries = new ArrayList<BmobQuery<MyUser>>();
		BmobQuery<MyUser> q2 = new BmobQuery<MyUser>();
		q2.addQueryKeys("teamFlag");
		queries.add(q2);

		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.or(queries).order("-createdAt").setLimit(20).findObjects(new FindListener<MyUser>() {

			@Override
			public void done(List<MyUser> object, BmobException e) {
				if (e == null) {
					L.i("getData size = " + object.size());
					L.i(object.get(0).getUsername() + "ok");
					data.clear();
					KDTeamData bufferData = null;
					MyUser user = null;
					for (int i = 0; i < object.size(); i++) {
						user = object.get(i);
						if (!user.getTeamFlag().equals("1")) {
							bufferData = new KDTeamData();
							bufferData.setTeam_name(user.getUsername().toString());
							bufferData.setTeam_property(user.getTeamFlag() + "团队");
							bufferData.setTeam_head(user.getImgHead().toString());
							data.add(bufferData);
						}

					}
					handler.sendEmptyMessage(StaticClass.LOAD_DATA_OK);
				} else {
					L.i("getData 失败 :" + e.getMessage());
				}
			}
		});

	}

	// 设置数据到dialog上
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
					et_property.setText("团队代取");

				} else {
					L.i(e.getMessage() + e.getErrorCode());
					Toast.makeText(KDTeamDQActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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








