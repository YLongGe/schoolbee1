package activity;

import java.util.ArrayList;
import java.util.List;

import com.tjnu.schoolbee.R;

import adapter.MyOrdersAdapter;
import android.R.bool;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import entity.MyOrdersData;
import entity.MyUser;
import entity.UserDqInfomation;
import utils.L;
import view.CustomDialog;

public class UMyOrdersActivity extends BaseActivity implements View.OnClickListener{

	private ListView lv_orders;
	private List<MyOrdersData> data = new ArrayList<MyOrdersData>();
	
	//数据加载成功
	private static final int CACHE_DATA_OK = 200;
	//数据加载失败
	private static final int CACHE_DATA_FAIL = 201;
	
	private static  String PHONE;
	
	private TextView tv_sum;
	
	//记录快件个数
	private int sum = 0;
	
	private CustomDialog dialog;
	
	//点击listview item 后弹出的dialog变量
	private CustomDialog displayList;
	private TextView tv_username;
	private TextView tv_addr;
	private TextView tv_phone;
	private Button bt_end;

	private Handler hander = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CACHE_DATA_OK:
				MyOrdersAdapter adapter = new MyOrdersAdapter(UMyOrdersActivity.this, data);
				adapter.notifyDataSetChanged();
				lv_orders.setAdapter(adapter);
				tv_sum.setText("您当前有" + data.size() + "件快递正在派送");
				break;
			case CACHE_DATA_FAIL:
				tv_sum.setText("您还没有下单，赶紧去找人取件吧");
				lv_orders.setAdapter(null);
				dialog.dismiss();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_u_my_orders);
		
		
		initView();
		
	}

	private void initView() {
				
		dialog = new CustomDialog(this, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		dialog.setCancelable(false);
		
		WindowManager manager = this.getWindowManager();
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		int width = (int) (outMetrics.widthPixels * 0.7);
		int height = (int) (outMetrics.heightPixels * 0.5);
		//显示详细信息的dialog
		displayList = new CustomDialog(this, width,
				height, R.layout.dialog_display_list, R.style.Theme_dialog, Gravity.CENTER,
				R.style.pop_anim_style);

		tv_username = (TextView) displayList.findViewById(R.id.tv_username);
		tv_addr = (TextView) displayList.findViewById(R.id.tv_addr);
		tv_phone = (TextView) displayList.findViewById(R.id.tv_phone);
		bt_end = (Button) displayList.findViewById(R.id.bt_end);
		bt_end.setOnClickListener(this);
		
		tv_sum = (TextView) findViewById(R.id.tv_sum);
		lv_orders = (ListView) findViewById(R.id.lv_orders);
		queryData();
		lv_orders.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				displayList.show();
				tv_username.setText(data.get(position).getUsername());
				tv_addr.setText(data.get(position).getAddr());
				tv_phone.setText(data.get(position).getPhoneNumber());
			}
		});
	}
	
	
	
	
	// 查数据
	private  void queryData() {
		dialog.show();
		List<BmobQuery<UserDqInfomation>> queries = new ArrayList<BmobQuery<UserDqInfomation>>();
		BmobQuery<UserDqInfomation> q1 = new BmobQuery<UserDqInfomation>();
		q1.addWhereEqualTo("success", false);
		BmobUser user = MyUser.getCurrentUser();
		String username = user.getUsername();
		BmobQuery<UserDqInfomation> q2 = new BmobQuery<UserDqInfomation>();
		q2.addWhereEqualTo("username", username);
		
		queries.add(q1);
		queries.add(q2);
		
		BmobQuery<UserDqInfomation> query = new BmobQuery<UserDqInfomation>();
		query.and(queries);
		query.findObjects(new FindListener<UserDqInfomation>() {

			@Override
			public void done(List<UserDqInfomation> object, BmobException e) {

				if (e == null) {
					if (object.size() == 0){
						hander.sendEmptyMessage(CACHE_DATA_FAIL);
						return ;
					}
					data.clear();
					L.i("queryData = " + object.size());
					MyOrdersData buffer;
					
						for (UserDqInfomation u : object) {
							buffer = new MyOrdersData();
							buffer.setAddr(u.getAddr());
							buffer.setPhoneNumber(u.getDq_phone());
							queryUsername(u.getDq_phone(), buffer);
	
						}
				} else {
					L.i("queryData失败：" + e.getMessage());
					hander.sendEmptyMessage(CACHE_DATA_FAIL);
					Toast.makeText(UMyOrdersActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}

	//查询代取人的用户名（用代取电话查询）
	private  void queryUsername(String dq_phone, final MyOrdersData buffer) {
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.addWhereEqualTo("mobilePhoneNumber", dq_phone);
		query.findObjects(new FindListener<MyUser>() {
			@Override
			public void done(List<MyUser> object, BmobException e) {
				if (e == null) {

					buffer.setUsername(object.get(0).getUsername() + "  正在为你取件中...");
					L.i(buffer.getAddr() + "--" + buffer.getPhoneNumber() + "--" + buffer.getUsername());
					data.add(buffer);
					hander.sendEmptyMessage(CACHE_DATA_OK);
				} else {
					L.i("query username fail" + e.getMessage() + e.getErrorCode());
					Toast.makeText(UMyOrdersActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				dialog.dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_end:
			//弹出提示框提醒
			new AlertDialog.Builder(this).setTitle("确认收获？")
					.setMessage("收到快递之后才点击确定哦~")
					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dealResult();
						}
					}).setNegativeButton("取消", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							displayList.dismiss();
						}
					}).show();
			break;

		default:
			break;
		}
		
	}

	private void dealResult() {
		L.i("da_phone = " + tv_phone.getText().toString());
		L.i("addr = " + tv_addr.getText().toString());
			
		List<BmobQuery<UserDqInfomation>> queries = new ArrayList<BmobQuery<UserDqInfomation>>();
		BmobQuery<UserDqInfomation> q1 = new BmobQuery<UserDqInfomation>();
		q1.addWhereEqualTo("dq_phone", tv_phone.getText().toString());
		BmobQuery<UserDqInfomation> q2 = new BmobQuery<UserDqInfomation>();
		q2.addWhereEqualTo("addr", tv_addr.getText().toString());
		
		BmobQuery<UserDqInfomation> q3 = new BmobQuery<UserDqInfomation>();
		BmobUser user = MyUser.getCurrentUser();
		String username = user.getUsername();
		q3.addWhereEqualTo("username", username);
		
		queries.add(q1);
		queries.add(q2);
		queries.add(q3);
		
		
			//找到objId
			BmobQuery<UserDqInfomation> query = new BmobQuery<UserDqInfomation>();
			query.and(queries);
			//执行查询方法 获得 objId
		    query.findObjects(new FindListener<UserDqInfomation>() {
				
			    @Override
			    public void done(List<UserDqInfomation> object, BmobException e) {
			        if(e==null){
			        	
						UserDqInfomation udi = new UserDqInfomation();
						udi.setSuccess(true);
						//执行更新方法
						udi.update(object.get(0).getObjectId(), new UpdateListener() {

						    @Override
						    public void done(BmobException e) {
						        if(e==null){
						            L.i(" success 更新成功");
						            displayList.dismiss();
						            queryData();
						            Toast.makeText(UMyOrdersActivity.this, "代取成功，我们将一直为您服务", Toast.LENGTH_SHORT).show();
						        }else{
						            L.i("bmob 更新失败："+ e.getMessage()+","+e.getErrorCode());
						            Toast.makeText(UMyOrdersActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
						        }
						    }
						});
			            
			        }else{
			            L.i("query dq_phone at UMyOrderActivity 失败："+e.getMessage()+","+e.getErrorCode());
			            Toast.makeText(UMyOrdersActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
			        }
			    }
			});
		}
}









