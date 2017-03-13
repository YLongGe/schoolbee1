package activity;

import com.tjnu.schoolbee.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class KDFoundTeamAndPersonActivity extends Activity implements OnClickListener {
	
	private ImageView ib_individual_choose;
	private ImageView ib_team_choose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kd_foundteam_and_person);
		
		initView();
		
	}

	private void initView() {
		ib_team_choose = (ImageView) findViewById(R.id.ib_team_choose);
		ib_team_choose.setOnClickListener(this);
		
		ib_individual_choose = (ImageView) findViewById(R.id.ib_individual_choose);
		ib_individual_choose.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ib_individual_choose:
			startActivity(new Intent(this, KDIndividualDQActivity.class));
			break;
			
		case R.id.ib_team_choose:
			startActivity(new Intent(this, KDTeamDQActivity.class));
			break;
		
		
		
		}
		
	}
}
