package activity;

import java.util.ArrayList;
import java.util.List;

import com.tjnu.schoolbee.R;

import adapter.MyOrdersAdapter;
import adapter.MySendOrdersAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import entity.MyOrdersData;
import utils.L;
import utils.StaticClass;
import utils.UtilTools;
import view.CustomDialog;

/**
 * 我的派件界面
 * @author LongGe
 *
 */
public class UMyPaiJianActivity extends BaseActivity implements OnClickListener{
	
	private ListView lv_send_orders;
	
	private TextView tv_send_sum;
	
	private CustomDialog dialog;
	
	private CustomDialog displayList;
	private TextView tv_username;
	private TextView tv_addr;
	private TextView tv_phone;
	
	private List<MyOrdersData> data = new ArrayList<>();
	
	private Handler hander = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case StaticClass.LOAD_DATA_OK:
				dialog.dismiss();
				MySendOrdersAdapter adapter = new MySendOrdersAdapter(UMyPaiJianActivity.this, data);
				adapter.notifyDataSetChanged();
				lv_send_orders.setAdapter(adapter);
				L.i("send dataSize = " + data.size());
				tv_send_sum.setText("当前有" + data.size() + "个快递需要你去派送");
				break;
			case StaticClass.LOAD_DATA_FAIL:
				tv_send_sum.setText("还没有人找过你取件");
				lv_send_orders.setAdapter(null);
				dialog.dismiss();
				break;
			}
		};
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_u_my_paijian);
		
		initView();
		initData();
		
	}

	private void initData() {
		dialog.show();
		MyOrdersData buffer;
		for (int i=0; i<5; i++){
			buffer = new MyOrdersData();
			buffer.setUsername("aaa");
			buffer.setAddr("天津");
			buffer.setPhoneNumber("1245");
			data.add(buffer);
		}
		
		hander.sendEmptyMessage(StaticClass.LOAD_DATA_OK);
		
	}

	private void initView() {
		dialog = new CustomDialog(this, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		dialog.setCancelable(false);
		
		int width = (int) (UtilTools.getWindowWidth(this) * 0.7);
		int height = (int)(UtilTools.getWindowHeigth(this) * 0.5);
		//显示详细信息的dialog
		displayList = new CustomDialog(this, width,
				height, R.layout.dialog_send_display_list, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		tv_send_sum = (TextView) findViewById(R.id.tv_send_sum);
		
		tv_username = (TextView) displayList.findViewById(R.id.tv_username);
		tv_addr = (TextView) displayList.findViewById(R.id.tv_addr);
		tv_phone = (TextView) displayList.findViewById(R.id.tv_phone);
		
		lv_send_orders = (ListView) findViewById(R.id.lv_send_orders);
		lv_send_orders.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				displayList.show();
				tv_username.setText(data.get(position).getUsername());
				tv_addr.setText(data.get(position).getAddr());
				tv_phone.setText(data.get(position).getPhoneNumber());
			}
		});
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 0:
			
			break;

		default:
			break;
		}
		
	}
}






