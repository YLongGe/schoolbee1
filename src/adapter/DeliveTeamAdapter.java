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
import entity.DeliveTeamData;

public class DeliveTeamAdapter extends BaseAdapter {

	private Context context;
	private List<DeliveTeamData> data;
	
	public DeliveTeamAdapter() {
	}
	
	public DeliveTeamAdapter(Context context, List<DeliveTeamData> data){
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
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
			view = inflater.inflate(R.layout.delive_team_item, parent, false);
			zhujian = new Zhujian();
			
			zhujian.iv_teamIcon = (ImageView) view.findViewById(R.id.iv_deliveteam);
			zhujian.tv_teamName = (TextView) view.findViewById(R.id.tv_team);
			zhujian.tv_time = (TextView) view.findViewById(R.id.tv_time);
			zhujian.tv_heartCount = (TextView) view.findViewById(R.id.tv_heartcount);
			
			view.setTag(zhujian);
		}

		zhujian.iv_teamIcon.setImageResource(data.get(position).getTeamIcon());
		zhujian.tv_teamName.setText(data.get(position).getTeamName());
		zhujian.tv_time.setText(data.get(position).getTime());
		zhujian.tv_heartCount.setText(data.get(position).getHeartCount());

		return view;
	}
	class Zhujian {
		ImageView iv_teamIcon;
		TextView tv_teamName;
		TextView tv_time;
		TextView tv_heartCount;
		
	}
}













