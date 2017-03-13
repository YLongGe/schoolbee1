package adapter;
 
import java.util.List;

import com.tjnu.schoolbee.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import entity.ClassroomSelect;

public class ClassroomSelectAdapter extends BaseAdapter {

	private List<ClassroomSelect> mdata;
	private Context context;

	public ClassroomSelectAdapter(List<ClassroomSelect> data, Context context) {
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
			view = inflater.inflate(R.layout.classroom_select_item, parent, false);
			zhujian = new Zhujian();
			zhujian.tv_time = (TextView) view.findViewById(R.id.tv_time);
			zhujian.tv_classroom = (TextView) view.findViewById(R.id.tv_classroom);
			zhujian.tv_classroom_cname = (TextView) view.findViewById(R.id.tv_classroom_cname);
			zhujian.tv_teacher = (TextView) view.findViewById(R.id.tv_teacher);
			view.setTag(zhujian);
		}

		zhujian.tv_time.setText(mdata.get(position).getTime());
		zhujian.tv_classroom.setText(mdata.get(position).getClassroom());
		zhujian.tv_classroom_cname.setText(mdata.get(position).getClassname());
		zhujian.tv_teacher.setText(mdata.get(position).getTeacher());

		return view;
	}

	class Zhujian {
		TextView tv_time;
		TextView tv_classroom;
		TextView tv_classroom_cname;
		TextView tv_teacher;
	}

}
