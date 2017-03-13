package fragment;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.tjnu.schoolbee.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import adapter.ClassroomSelectAdapter;
import adapter.CourseInfoAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import entity.ClassroomSelect;
import entity.CourseInfo;
import utils.IpAdressUtils;
import utils.RemoteServiceUtils;

public class LibraryFragment extends Fragment {

	// private final static String url =
	// "http://192.168.1.120:8080/JsonWeb/choose/chooseInfo.action?";

	// private final static String url = GetServer.getURL();

	TextView tv_grade_cridit; // 查询的黄条!!!!

	TextView tv_choose; // 查询黄条!!!!

	TextView tv_classroom1;

	Button bt_grade_cridit;
	Button bt_choose;
	Button bt_avg;
	Button bt_classroom;

	EditText et_grade_cridit;
	EditText et_classroom;

	ListView lv_both;
	View courseHeader; //课程信息的headerView
	View classroomSelectHeader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_library, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		// 第一个功能
		tv_grade_cridit = (TextView) view.findViewById(R.id.tv_grade_cridit);
		et_grade_cridit = (EditText) view.findViewById(R.id.et_grade_cridit);
		bt_grade_cridit = (Button) view.findViewById(R.id.bt_grade_cridit);
		// 第二个功能
		tv_choose = (TextView) view.findViewById(R.id.tv_choose);
		bt_choose = (Button) view.findViewById(R.id.bt_choose);
		// 第三个功能
		bt_avg = (Button) view.findViewById(R.id.bt_avg);
		// 第四个功能
		tv_classroom1 = (TextView) view.findViewById(R.id.tv_classroom1);
		bt_classroom = (Button) view.findViewById(R.id.bt_classroom);
		et_classroom = (EditText) view.findViewById(R.id.et_classroom);

		lv_both = (ListView) view.findViewById(R.id.lv_both);
		courseHeader = LayoutInflater.from(getActivity().getApplicationContext())
				.inflate(R.layout.course_info_item_header, null, false);
		classroomSelectHeader = LayoutInflater.from(getActivity().getApplicationContext())
				.inflate(R.layout.classroom_select_item_header, null, false);
		
		
		// 设置监听
		bt_choose.setOnClickListener(listener);
		bt_grade_cridit.setOnClickListener(listener);
		bt_avg.setOnClickListener(listener);
		bt_classroom.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String username = (String) getActivity().getIntent().getExtras().get("username");
			String cname = et_grade_cridit.getText().toString().trim();
			String time = et_classroom.getText().toString().trim();
			String classroom = et_classroom.getText().toString().trim();

			switch (v.getId()) {
			case R.id.bt_choose:
				String data = loginRemoteService(username);
				tv_choose.setText(data);
				break;
			case R.id.bt_grade_cridit:
				String grade_cridit = loginRemoteService(username, cname);
				tv_grade_cridit.setText(grade_cridit);
				break;
			case R.id.bt_avg:
				List<CourseInfo> course_info = courseInfoRemoteService();
				lv_both.setAdapter(null);
				lv_both.setAdapter(new CourseInfoAdapter(course_info, getActivity().getApplicationContext()));
				
				break;
			case R.id.bt_classroom:
				lv_both.setAdapter(null);
				List<ClassroomSelect> classroomSelect = classroomSelectRemoteService(time, classroom);

				lv_both.setAdapter(new ClassroomSelectAdapter(classroomSelect, getActivity().getApplicationContext()));
				
				break;
			default: 
				break;
			}
		}
	};

	
	public List<ClassroomSelect> classroomSelectRemoteService(String time, String classroom) {
		List<ClassroomSelect> result = null;
		String url = IpAdressUtils.getURL() + "/JsonWeb/classroom/classroomSelect.action?time=" + time + "&classroom=" + classroom;
		String json = RemoteServiceUtils.loginRemoteService(url);
		if (json != null) {
			Log.i("Test", json);

			Gson gson2 = new Gson();
			List<ClassroomSelect> list2 = new ArrayList<ClassroomSelect>();
			list2 = gson2.fromJson(json, new TypeToken<ArrayList<ClassroomSelect>>() {
			}.getType());
			result = list2;
			Log.i("test4", list2.toString());

		}
		return result;
	}
	
	public List<CourseInfo> courseInfoRemoteService() {
		List<CourseInfo> result = null;
		String url = IpAdressUtils.getURL() + "/JsonWeb/course/courseInfo.action?";
		String json = RemoteServiceUtils.loginRemoteService(url);
		if (json != null) {
			Log.i("Test", json);

			Gson gson2 = new Gson();
			List<CourseInfo> list2 = new ArrayList<CourseInfo>();
			list2 = gson2.fromJson(json, new TypeToken<ArrayList<CourseInfo>>() {
			}.getType());
			result = list2;
			Log.i("test4", list2.toString());

		}
		return result;
	}

	public String loginRemoteService(String username) {
		String result = null;
		try{
			String url = IpAdressUtils.getURL() + "/JsonWeb/choose/chooseInfo.action?username=" + username;
			String json = RemoteServiceUtils.loginRemoteService(url);
			if (json != null) {
				JSONObject jsonObject = new JSONObject(json);
				result = jsonObject.get("choose").toString();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
			return result;
	}

	@SuppressWarnings("finally")
	public String loginRemoteService(String username, String cname) {
		String result = null;

		try {
			cname = URLEncoder.encode(cname, "UTF-8"); // 创建一个HttpClient对象
			HttpClient httpclient = new DefaultHttpClient();
			String url = IpAdressUtils.getURL() + "/JsonWeb/grade_cridit/gradeCridit.action?username=" + username
					+ "&cname=" + cname;
			Log.d("远程URL", url); // 创建HttpGet对象
			HttpPost request = new HttpPost(url);
			request.addHeader("Accept", "text/json");
			// 获取响应的结果
			HttpResponse response = httpclient.execute(request); // 获取HttpEntity
			HttpEntity entity = response.getEntity();

			// 获取响应的结果信息
			String json = EntityUtils.toString(entity, "UTF-8");
			// JSON的解析过程
			if (json != null) {
				Log.i("sno", json);
				JSONObject jsonObject = new JSONObject(json);
				result = jsonObject.get("grade_cridit").toString();
			}

		} catch (Exception e) {

		} finally {
			if (result == null) {
				result = "not find";
			}

			return result;
		}

	}
}
