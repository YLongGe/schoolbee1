package activity;

import com.tjnu.schoolbee.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class KDZhuanQianActivity extends BaseActivity {
	ImageView team_money;
	ImageView person_money;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kd_zhuanqian);
		
		team_money = (ImageView) findViewById(R.id.team_money);
		person_money = (ImageView) findViewById(R.id.person_money);

		team_money.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(KDZhuanQianActivity.this,KDTeamRegister.class);
				startActivity(intent1);
			}
		});
		
		person_money.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(KDZhuanQianActivity.this,KDGerenZhuceActivity.class);
				startActivity(intent1);
			}
		});
		
	}
}
