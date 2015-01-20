package com.anhry.subway.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anhry.subway.R;
import com.anhry.subway.bean.Line;
import com.anhry.subway.bean.User;
import com.baidu.mapapi.search.core.VehicleInfo;

/**
 * @ClassName: MyLineAdapter
 * @author zhangjiangbo
 * @date 2015-1-5 ����2:35:54
 */
@SuppressLint("DefaultLocale")
public class AddLineAdapter extends BaseAdapter {
	private Context ct;
	private List<Line> data;

	public AddLineAdapter(Context ct, List<Line> datas) {
		this.ct = ct;
		this.data = datas;
	}

	/**
	 * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
	 * 
	 * @Title: updateListView
	 * @Description: TODO
	 * @param @param list
	 * @return void
	 * @throws
	 */
	public void updateListView(List<Line> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	public void remove(User user) {
		this.data.remove(user);
		notifyDataSetChanged();
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(ct).inflate(
					R.layout.item_add_line, null);
			viewHolder = new ViewHolder();
			viewHolder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_friend_name);
			viewHolder.check = (ImageView) convertView.findViewById(R.id.check_line);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final Line line = data.get(position);
		StringBuffer sb = new StringBuffer();
		int i = 0;
		List<VehicleInfo> ll = line.getVehList();
		for (VehicleInfo info : ll) {
			i++;
			if (i < ll.size()) {
				sb.append(info.getTitle()).append("-->");
			} else {
				sb.append(info.getTitle());
			}
		}
		String title = sb.toString();
		viewHolder.name.setText(title);
		viewHolder.alpha.setText("ȫ��Լ��"+getDist(line.getDistance())+"����"+"--->ȫ��Լ��"+getDura(line.getDuration()));
		if(line.isChoose()){
			viewHolder.check.setBackgroundResource(R.drawable.check_yes);
		}else{
			viewHolder.check.setBackgroundResource(R.drawable.check_no);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				viewHolder.check.setChecked(true);
				if (line.isChoose()) {
					line.setChoose(false);
//					for(TbXzzfModViewList mod:mDatas){
//						mod.setChoose(false);
//					}
				}else{
					for(Line mod:data){
						mod.setChoose(false);
					}
					line.setChoose(true);
				}
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	
	public double getDist(int distance){
		 return Math.round(distance/100d)/10d;
	}
	
	public String getDura(int dura){
		long hour = dura/3600;    //!Сʱ
        long minute = dura%3600/60;  //!����
//        long second = dura%60;       // !��
        if(hour< 1){
        	return minute+"����";
        }else {
        	return hour+"Сʱ"+minute+"����";
        }
	}

	static class ViewHolder {
		TextView alpha;
		TextView name;
		ImageView check;
	}

}