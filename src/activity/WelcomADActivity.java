package activity;

import com.tjnu.schoolbee.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class WelcomADActivity extends Activity {

	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcom_ad);
		
		iv = (ImageView) findViewById(R.id.iv_bg);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				goLogin();
			}
		}, 2 * 1000);
	}

	private void goLogin() {
		Intent intent = new Intent(WelcomADActivity.this, LoginActivity.class);
		WelcomADActivity.this.startActivity(intent);
		WelcomADActivity.this.finish();
	}
}
