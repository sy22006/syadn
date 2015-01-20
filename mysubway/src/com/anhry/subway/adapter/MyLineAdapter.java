package com.anhry.subway.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anhry.subway.R;
import com.anhry.subway.bean.SubWayLine;
import com.anhry.subway.bean.User;


/** 
 * @ClassName: MyLineAdapter 
 * @author zhangjiangbo 
 * @date 2015-1-5 下午2:35:54 
 */
@SuppressLint("DefaultLocale")
public class MyLineAdapter extends BaseAdapter {
	private Context ct;
	private List<SubWayLine> data;

	public MyLineAdapter(Context ct, List<SubWayLine> datas) {
		this.ct = ct;
		this.data = datas;
	}

	/** 当ListView数据发生变化时,调用此方法来更新ListView
	  * @Title: updateListView
	  * @Description: TODO
	  * @param @param list 
	  * @return void
	  * @throws
	  */
	public void updateListView(List<SubWayLine> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	public void remove(User user){
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
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_my_line, null);
			viewHolder = new ViewHolder();
//			viewHolder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_friend_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		SubWayLine friend = data.get(position);
		final String name = friend.getStartName();
		final String end = friend.getEndName();
		viewHolder.name.setText(name+"-->"+end);
		return convertView;
	}

	static class ViewHolder {
//		TextView alpha;// 首字母提示
		TextView name;
	}
}