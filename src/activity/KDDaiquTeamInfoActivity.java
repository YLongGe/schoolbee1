package activity;

import com.tjnu.schoolbee.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class KDDaiquTeamInfoActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kd_daiquteam_info);
	}
}
