package adapter;

import java.util.List;

import com.tjnu.schoolbee.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import entity.CourseInfo;

public class CourseInfoAdapter extends BaseAdapter {

	private List<CourseInfo> mdata;
	private Context context;

	public CourseInfoAdapter(List<CourseInfo> data, Context context) {
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
			view = inflater.inflate(R.layout.course_info_item, parent, false);
			zhujian = new Zhujian();
			zhujian.tv_cname = (TextView) view.findViewById(R.id.tv_cname);
			zhujian.tv_course = (TextView) view.findViewById(R.id.tv_course);
			zhujian.tv_avg = (TextView) view.findViewById(R.id.tv_avg);
			view.setTag(zhujian);
		}
		Log.i("Hello2", mdata.toString());
		zhujian.tv_cname.setText(mdata.get(position).getCname());
		zhujian.tv_course.setText(mdata.get(position).getCourse());
		zhujian.tv_avg.setText(mdata.get(position).getAvg()+"");

		return view;
	}

	class Zhujian {
		TextView tv_cname;
		TextView tv_course;
		TextView tv_avg;
	}

}
