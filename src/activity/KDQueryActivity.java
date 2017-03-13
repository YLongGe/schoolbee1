package activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tjnu.schoolbee.R;

import adapter.KDQueryAdapter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import entity.KDQueryData;
import utils.L;
import utils.StaticClass;
import view.CustomDialog;

public class KDQueryActivity extends BaseActivity implements OnClickListener {

	private Button btn_query;
	private EditText et_firm;
	private EditText et_number;
	private ListView lv_display;

	private List<KDQueryData> kDQueryData;
	
	private CustomDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kd_query);
		initView();
	}

	private void initView() {
		
		dialog = new CustomDialog(this, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		dialog.setCancelable(false);

		
		btn_query = (Button) findViewById(R.id.btn_query);
		btn_query.setOnClickListener(this);
		et_firm = (EditText) findViewById(R.id.et_firm);
		et_number = (EditText) findViewById(R.id.et_number);

		lv_display = (ListView) findViewById(R.id.lv_display);
		lv_display.setDividerHeight(0);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_query:
			String firm = et_firm.getText().toString().trim();
			String number = et_number.getText().toString().trim();
			if (!TextUtils.isEmpty(firm) && !TextUtils.isEmpty(number)) {
				queryKD(firm, number);

			} else {
				Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	private void queryKD(String firm, String number) {
		dialog.show();
		String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.KD_QUERY_KEY + "&com=" + firm + "&no=" + number;
		RequestQueue mQueue = Volley.newRequestQueue(KDQueryActivity.this);
		StringRequest request = new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				L.i(response);
				parsingJson(response);
			}

		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				dialog.dismiss();
				Toast.makeText(KDQueryActivity.this, error+"", Toast.LENGTH_LONG).show();
				
			}
		});
		mQueue.add(request);
	}

	private void parsingJson(String response) {

		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject jsonResult = jsonObject.getJSONObject("result");
			JSONArray jsonArray = jsonResult.getJSONArray("list");

			kDQueryData = new ArrayList<KDQueryData>();
			KDQueryData buffer = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = (JSONObject) jsonArray.get(i);
				buffer = new KDQueryData();
				L.i(json + "");
				buffer.setRemark(json.getString("remark"));
				buffer.setDatetime(json.getString("datetime"));
				kDQueryData.add(buffer);
			}
			L.i(kDQueryData.size() + "");
			lv_display.setAdapter(new KDQueryAdapter(KDQueryActivity.this, kDQueryData));
			dialog.dismiss();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
