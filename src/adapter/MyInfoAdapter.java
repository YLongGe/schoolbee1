package adapter;

import java.util.List;

import com.tjnu.schoolbee.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import entity.InfoItem;

public class MyInfoAdapter extends BaseAdapter {

	private List<InfoItem> mdata;
	private Context context;

	public MyInfoAdapter(List<InfoItem> data, Context context) {
		this.mdata = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		return mdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		Zhujian zhujian = null;
		View view = null;
		if (convertView != null) {
			view = convertView;
			zhujian = (Zhujian) view.getTag();
		} else {
			view = inflater.inflate(R.layout.item_myinfo, parent, false);
			zhujian = new Zhujian();
			zhujian.tv_item = (TextView) view.findViewById(R.id.tv_item_myinfo);
			zhujian.iv_item = (ImageView) view.findViewById(R.id.iv_item_util);
			view.setTag(zhujian);
		}

		zhujian.iv_item.setImageResource(mdata.get(position).getIv_item_util());
		zhujian.tv_item.setText(mdata.get(position).getTv_item_myinfo());

		return view;
	}

	class Zhujian {
		TextView tv_item;
		ImageView iv_item;
	}

}
