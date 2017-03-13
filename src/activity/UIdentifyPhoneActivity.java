package activity;

import com.tjnu.schoolbee.R;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import utils.CountDownTimerUtils;
import utils.UtilTools;

public class UIdentifyPhoneActivity extends BaseActivity implements android.view.View.OnClickListener{
	
	private Button btn_get_identy_code;
	
	private EditText et_write_phone;
	private EditText et_identify_code;
	
	private Button btn_submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_u_identify_phone);
		
		initView();
	}

	//初始化布局 
	private void initView() {
		btn_get_identy_code = (Button) findViewById(R.id.btn_get_identy_code);
		btn_get_identy_code.setOnClickListener(this);
		btn_get_identy_code.setBackground(getResources()
				.getDrawable(R.drawable.bg_identify_code_press));
		
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		
		et_write_phone = (EditText) findViewById(R.id.et_write_phone);
		et_write_phone.addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0){
					btn_get_identy_code.setEnabled(true);
					btn_get_identy_code.setBackground(getResources()
							.getDrawable(R.drawable.selector));
					
				}else{
					btn_get_identy_code.setEnabled(false);
					btn_get_identy_code.setBackground(getResources()
							.getDrawable(R.drawable.bg_identify_code_press));
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		et_identify_code = (EditText) findViewById(R.id.et_identify_code);
		
	}

	
	@Override
	public void onClick(View v) {
		String mobileNumber = et_write_phone.getText().toString().trim();
		String identify_code = et_identify_code.getText().toString().trim();
		switch (v.getId()) {
		case R.id.btn_get_identy_code:
			
			if (UtilTools.checkMobileNumber(mobileNumber))
			{
				CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(btn_get_identy_code, 60000, 1000);
				mCountDownTimerUtils.start();
			}else{
				Toast.makeText(this, "电话号码格式不正确", Toast.LENGTH_LONG).show();
			}
			
			break;
		
		case R.id.btn_submit:
			if ( !TextUtils.isEmpty(identify_code) && TextUtils.isEmpty(mobileNumber) ){
				submitIdentify();
			}else{
				Toast.makeText(this, "输入框不能为空", Toast.LENGTH_LONG).show();
			}
			
			break;
			
		default:
			break;
		}
	}

	//提交验证码验证手机
	private void submitIdentify() {
		
	}


}
