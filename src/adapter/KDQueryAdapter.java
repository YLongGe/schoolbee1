package adapter;

import java.util.List;

import com.tjnu.schoolbee.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import entity.KDQueryData;

public class KDQueryAdapter extends BaseAdapter {

	private Context mContext; 
	private List<KDQueryData> data;
	private LayoutInflater inflater;

	public KDQueryAdapter(Context mContext, List<KDQueryData> data) {
		this.mContext = mContext;
		this.data = data;
		inflater = LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		
		if (convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_kd_query,parent, false);
			viewHolder.tv_datatime = (TextView) convertView.findViewById(R.id.tv_datetime);
			viewHolder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		viewHolder.tv_datatime.setText(data.get(position).getDatetime());
		viewHolder.tv_remark.setText(data.get(position).getRemark());
		
		
		
		return convertView;
	}

	class ViewHolder {
		private TextView tv_remark;
		private TextView tv_datatime;
	}
}








