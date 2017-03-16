package fragment; 

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tjnu.schoolbee.R;

import activity.UAboutWeActivity;
import activity.UMyInfomationActivity;
import activity.UMyOrdersActivity;
import activity.UMyPaiJianActivity;
import activity.USetingActivity;
import adapter.MyInfoAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import entity.InfoItem;
import entity.MyUser;
import utils.L;
import utils.ShareUtils;
import utils.UtilTools;
import view.CircleImageView;
import view.CustomDialog;

public class MyInfoFragment extends Fragment implements View.OnClickListener {
	private ListView listview;
	private List<InfoItem> data;

	private CircleImageView cv_head;

	private CustomDialog dialog;

	private Button btn_cancel;
	private Button btn_camera;
	private Button btn_picture;

	public static final String PHOTO_IMAGE_FILE_NAME = "fileName.jpg";
	public static final int CAMERA_REQUEST_CODE = 100;
	public static final int PICTURE_REQUEST_CODE = 101;
	public static final int RESULT_REQUEST_CODE = 102;

	public File tempFile = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_myinfo, container, false);

		findView(view);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	private void findView(View view) {
		dialog = new CustomDialog(getActivity(), WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_photo, R.style.Theme_dialog, Gravity.BOTTOM,
				R.style.pop_anim_style);

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		listview = (ListView) view.findViewById(R.id.list_myinfo);

		cv_head = (CircleImageView) view.findViewById(R.id.cv_head);
		cv_head.setBorderColor(Color.BLUE);
		cv_head.setOnClickListener(this);

		String imgString = ShareUtils.getString(getActivity(), "image_title", "");
		Bitmap bitmap = null;
		if ((bitmap = UtilTools.stringToBitmap(imgString)) != null) {
			cv_head.setImageBitmap(bitmap);
		}

		btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
		btn_camera.setOnClickListener(this);
		btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
		btn_picture.setOnClickListener(this);

		// 从上下文中获取intent传递过来的username
		// String text = "Hello:" +
		// getActivity().getIntent().getStringExtra("username");

		listview.setAdapter(new MyInfoAdapter(getData(), getActivity()));
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				switch (arg2) {
				// 我的订单
				case 0:
					startActivity(new Intent(getActivity(), UMyOrdersActivity.class));
					break;
				// 我的信息
				case 1:
					startActivity(new Intent(getActivity(), UMyInfomationActivity.class));
					break;
				// 我的派件
				case 2:
					startActivity(new Intent(getActivity(), UMyPaiJianActivity.class));
					break;
				// 带点评
				case 3:
					break;
				// 关于我们
				case 4:
					startActivity(new Intent(getActivity(), UAboutWeActivity.class));
					break;
				// 设置
				case 5:
					startActivity(new Intent(getActivity(), USetingActivity.class));
					break;

				default:
					break;
				}

			}
		});

	}

	// 将每一项的数据存入List中，InfoItem为实体类，存储显示的图片和文本
	private List<InfoItem> getData() {
		data = new ArrayList<InfoItem>();
		String[] textItem = getResources().getStringArray(R.array.info);
		int[] drableItem = new int[] { R.drawable.account, R.drawable.wallet, R.drawable.earning, R.drawable.evalue,
				R.drawable.time, R.drawable.setting };
		InfoItem buffer;
		for (int i = 0; i < textItem.length; i++) {
			buffer = new InfoItem(drableItem[i], textItem[i]);
			data.add(buffer);
		}
		return data;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.cv_head:
			dialog.show();
			break;
		case R.id.btn_cancel:
			dialog.dismiss();
			break;

		case R.id.btn_camera:
			toCamera();
			break;
		case R.id.btn_picture:
			toPicture();
			break;
		default:
			break;
		}
	}

	// 跳转相册
	private void toPicture() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		L.i("pictureOk");
		startActivityForResult(intent, PICTURE_REQUEST_CODE);
		dialog.dismiss();

	}

	// 跳转相机
	private void toCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 判断内存是否可用
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
		L.i("toCameraOk" + Environment.getExternalStorageDirectory());
		startActivityForResult(intent, CAMERA_REQUEST_CODE);
		dialog.dismiss();
	}

	// 裁剪
	private void startPhotoZoom(Uri uri) {
		if (uri == null) {
			L.i("uri == null");
			return;
		}
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_CANCELED) {
			switch (requestCode) {
			// 图片数据
			case PICTURE_REQUEST_CODE:

				startPhotoZoom(data.getData());
				break;
			// 相机数据
			case CAMERA_REQUEST_CODE:
				tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
				L.i(tempFile + "");
				startPhotoZoom(Uri.fromFile(tempFile));
				break;

			case RESULT_REQUEST_CODE:
				// 有可能不纯在
				if (data != null) {

					setImageToView(data);

					// 删除缓存图片
					if (tempFile != null) {
						tempFile.delete();
					}
				}

				break;

			default:
				break;
			}
		}

	}

	// 设置图片
	private void setImageToView(Intent data) {
		Bundle bundle = data.getExtras();
		if (bundle != null) {
			Bitmap bitmap = bundle.getParcelable("data");
			cv_head.setImageBitmap(bitmap);
			

		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		BitmapDrawable drawble = (BitmapDrawable) cv_head.getDrawable();
		String bmString = UtilTools.bitmapToString(getActivity(), drawble);
		ShareUtils.putString(getActivity(), "image_title", bmString);
		
		uploadImageHead(bmString);

	}
	
	//上传图片到bmob
	private void uploadImageHead(String bmString) {
		MyUser newUser = new MyUser();
		newUser.setImgHead(bmString);
		BmobUser bmobUser = MyUser.getCurrentUser();
		newUser.update(bmobUser.getObjectId(),new UpdateListener() {
		    @Override
		    public void done(BmobException e) {
		        if(e==null){
		            L.i("上传头像成功");
		        }else{
		            L.i("上传头像失败" + e);
		        }
		    }
		});
	}
}
