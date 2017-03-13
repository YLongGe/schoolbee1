package activity;

import java.util.List;

import com.tjnu.schoolbee.R;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import entity.MyUser;
import entity.UserDqInfomation;
import view.CustomDialog;

public class UMyInfomationActivity extends BaseActivity implements OnClickListener {

	private EditText et_nichen;
	private EditText et_phoneNumber;
	private EditText et_idCard;
	private EditText et_mail;
	private EditText et_real_name;

	private Button btn_modify;
	private Button btn_updata;
	
	private CustomDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_u_myinfomation);

		initView();
		setDataTOView();
	}

	private void setDataTOView() {
		BmobUser userQuery = MyUser.getCurrentUser();
		String nichenQuery = userQuery.getUsername();

		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.addWhereEqualTo("username", nichenQuery);
		query.findObjects(new FindListener<MyUser>() {

			@Override
			public void done(List<MyUser> object, BmobException e) {
				if (e == null) {
					MyUser user = object.get(0);
					String nichen = user.getUsername();
					String phoneNumber = user.getMobilePhoneNumber();
					String email = user.getEmail();
					String idCard = user.getIdCard();
					String realName = user.getRealName();

					et_nichen.setText(nichen);
					et_mail.setText(email);
					et_idCard.setText(idCard);
					et_phoneNumber.setText(phoneNumber);
					et_real_name.setText(realName);

				} else {
					Toast.makeText(UMyInfomationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	private void initView() {
		
		dialog = new CustomDialog(this, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		dialog.setCancelable(false);
		
		et_nichen = (EditText) findViewById(R.id.et_nichen);
		et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
		et_idCard = (EditText) findViewById(R.id.et_idCard);
		et_mail = (EditText) findViewById(R.id.et_mail);
		et_real_name = (EditText) findViewById(R.id.et_real_name);
		setEditTextEnable(false);

		btn_modify = (Button) findViewById(R.id.btn_modify);
		btn_modify.setOnClickListener(this);
		btn_modify.setVisibility(View.VISIBLE);

		btn_updata = (Button) findViewById(R.id.btn_updata);
		btn_updata.setOnClickListener(this);

	}

	private void setEditTextEnable(boolean state) {
		et_nichen.setEnabled(state);
		et_phoneNumber.setEnabled(state);
		et_idCard.setEnabled(state);
		et_mail.setEnabled(state);
		et_real_name.setEnabled(state);

		if (!state) {
			et_nichen.setBackgroundColor(Color.WHITE);
			et_phoneNumber.setBackgroundColor(Color.WHITE);
			et_idCard.setBackgroundColor(Color.WHITE);
			et_mail.setBackgroundColor(Color.WHITE);
			et_real_name.setBackgroundColor(Color.WHITE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_modify:
			setEditTextEnable(true);
			btn_updata.setVisibility(View.VISIBLE);
			btn_modify.setVisibility(View.GONE);
			break;

		default:
		case R.id.btn_updata:
			setEditTextEnable(false);
			btn_modify.setVisibility(View.VISIBLE);
			btn_updata.setVisibility(View.GONE);
			dialog.show();
			alterInfomation();
			break;
		}

	}
	
	//修改资料
	private void alterInfomation() {
		
		String nichen = et_nichen.getText().toString().trim();
		String real_name = et_real_name.getText().toString().trim();
		String phoneNumber = et_phoneNumber.getText().toString().trim();
		String idCard = et_idCard.getText().toString().trim();
		String email = et_mail.getText().toString().trim();
		BmobUser bmobUser = BmobUser.getCurrentUser();
		
		MyUser newUser = new MyUser();
		newUser.setEmail(email);
		newUser.setUsername(nichen);
		newUser.setRealName(real_name);
		newUser.setMobilePhoneNumber(phoneNumber);
		newUser.setIdCard(idCard);
		
		
		newUser.update(bmobUser.getObjectId(),new UpdateListener() {
		    @Override
		    public void done(BmobException e) {
		    	dialog.dismiss();
		        if(e==null){
		        	Toast.makeText(UMyInfomationActivity.this, "修改资料成功", Toast.LENGTH_SHORT).show();
		        }else{
		        	Toast.makeText(UMyInfomationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
		        }
		    }
		});
		
		
	}

}
