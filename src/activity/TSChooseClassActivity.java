package activity;

import java.util.ArrayList;
import java.util.List;

import com.tjnu.schoolbee.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import utils.RemoteServiceUtils;

public class TSChooseClassActivity extends Activity {
	private EditText et_chooseclass_username;
	private Spinner sp_chooseclass_class;
	private Button bt_chooseclass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ts_choose_class_activity);
		et_chooseclass_username = (EditText) this.findViewById(R.id.et_chooseclass_username);
		sp_chooseclass_class = (Spinner) this.findViewById(R.id.sp_chooseclass_class);
		bt_chooseclass = (Button) this.findViewById(R.id.bt_chooseclass);
		
		et_chooseclass_username.setText("1430090001");
		bt_chooseclass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getCourseNames();
			}
		});
	}
	
	public List<String> getCourseNames() {
		List<String> result = null;
		String url = "http://192.168.1.100:8080/JsonWeb/courseName/courseNames.action?";
		String json = RemoteServiceUtils.loginRemoteService(url);
		Log.i("test1", json);
		if (json != null) {
			Log.i("Test", json);

			Gson gson2 = new Gson();
			List<String> list2 = new ArrayList<String>();
			list2 = gson2.fromJson(json, new TypeToken<ArrayList<String>>() {
			}.getType());
			result = list2;
			Log.i("test4", list2.toString());

		}
		return result;
	}
}







