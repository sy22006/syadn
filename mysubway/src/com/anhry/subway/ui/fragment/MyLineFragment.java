package com.anhry.subway.ui.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.anhry.subway.R;
import com.anhry.subway.adapter.MyLineAdapter;
import com.anhry.subway.bean.SubWayLine;
import com.anhry.subway.bean.User;
import com.anhry.subway.ui.AddLineActivity;
import com.anhry.subway.ui.FragmentBase;
import com.anhry.subway.util.SharePreferenceUtil;
import com.anhry.subway.view.ClearEditText;
import com.anhry.subway.view.HeaderLayout.onRightImageButtonClickListener;
import com.anhry.subway.view.xlist.XListView;
import com.anhry.subway.view.xlist.XListView.IXListViewListener;


/** 
 * 我的路线
 * @ClassName: MyLineFragment 
 * @author zhangjiangbo 
 * @date 2015-1-5 下午1:06:57 
 */
@SuppressLint("SimpleDateFormat")
public class MyLineFragment extends FragmentBase implements OnItemClickListener, IXListViewListener{
	SharePreferenceUtil mSharedUtil;
	ClearEditText mClearEditText;
	XListView mListView;
	private MyLineAdapter userAdapter;			// 路线适配器
	public static final int NEW_MESSAGE = 0x001;// 收到消息
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSharedUtil = mApplication.getSpUtil();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_line, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
		initXListView();
	}

	private void initView() {
		initTopBarForOnlyTitle("我的路线");
		mListView = (XListView)findViewById(R.id.list);
		mListView.setOnItemClickListener(this);
		
		mClearEditText = (ClearEditText)findViewById(R.id.et_msg_search);
		mClearEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		initTopBarForRight("我的路线", R.drawable.base_action_bar_add_bg_selector,
		new onRightImageButtonClickListener() {
			@Override
			public void onClick() {
//				startAnimActivity(AddLineActivity.class);
				Intent intent = new Intent();
				intent.setClass(getActivity(), AddLineActivity.class);
				startActivityForResult(intent, 1113);
			}
		});
	}
	
	private void initXListView() {
		// 首先不允许加载更多
		mListView.setPullLoadEnable(false);
		// 允许下拉
		mListView.setPullRefreshEnable(true);
		// 设置监听器
		mListView.setXListViewListener(this);
		mListView.pullRefreshing();
		mListView.setDividerHeight(0);
	}
	
	private void queryObjects(){
		final User user = (User) userManager.getCurrentUser(User.class);
		String userId = user.getObjectId();
		
		BmobQuery<SubWayLine> bmobQuery	 = new BmobQuery<SubWayLine>();
		bmobQuery.addWhereEqualTo("userId", userId);
//		bmobQuery.addWhereEqualTo("age", 25);
//		bmobQuery.addWhereNotEqualTo("age", 25);
//		bmobQuery.addQueryKeys("objectId");
//		bmobQuery.setLimit(10);
//		bmobQuery.setSkip(15);
		bmobQuery.order("-createdAt");
//		bmobQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
		bmobQuery.findObjects(getActivity(), new FindListener<SubWayLine>() {
			@Override
			public void onSuccess(List<SubWayLine> object) {
				userAdapter = new MyLineAdapter(getActivity() ,object);
				mListView.setAdapter(userAdapter);
			}
			@Override
			public void onError(int code, String msg) {
			}
		});
	}

	private void initData() {
		queryObjects();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		initData();
	}
	

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == NEW_MESSAGE) {
				
			}
		};
	};

	@Override
	public void onRefresh() {	//下拉刷新
		Log.e("TAG", "onRefresh...");
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				
				mListView.stopRefresh();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {	//上拉更多
		Log.e("TAG", "onLoadMore...");
		mListView.stopRefresh();
	}
}