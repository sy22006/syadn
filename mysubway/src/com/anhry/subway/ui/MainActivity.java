package com.anhry.subway.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import cn.bmob.im.BmobChat;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.inteface.EventListener;

import com.anhry.subway.CustomApplcation;
import com.anhry.subway.MyMessageReceiver;
import com.anhry.subway.R;
import com.anhry.subway.ui.fragment.JourneyFriendsFragment;
import com.anhry.subway.ui.fragment.MyLineFragment;

/**
 * 主界面
 * @ClassName: MainActivity
 * @Description: 
 * @author smile
 * @date 2014-5-29 下午2:45:35
 */
public class MainActivity extends ActivityBase implements EventListener{
	private Button[] mTabs;
//	private ContactFragment contactFragment;
//	private RecentFragment recentFragment;
//	private SettingsFragment settingFragment;	// 设置
	private MyLineFragment myLineFragment;		// 我的线路
	private JourneyFriendsFragment jfFragment;	// 旅途交友
	
	private Fragment[] fragments;
	private int index;
	private int currentTabIndex;
	private DrawerLayout mDrawer_layout;// DrawerLayout容器
	
	ImageView iv_set_tips,iv_contact_tips;//消息提示
	
	View rl_shoucang;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//开启定时检测服务（单位为秒）-在这里检测后台是否还有未读的消息，有的话就取出来
		//如果你觉得检测服务比较耗流量和电量，你也可以去掉这句话-同时还有onDestory方法里面的stopPollService方法
		BmobChat.getInstance(this).startPollService(30);
		//开启广播接收器
		initNewMessageBroadCast();
		initTagMessageBroadCast();
		initView();
		initTab();
		initSlidingMenu();
	}

	private void initSlidingMenu() {
		mDrawer_layout = (DrawerLayout) findViewById(R.id.layout_drawerlayout);
		mDrawer_layout.setScrimColor(Color.TRANSPARENT);
	}

	private void initView(){
		mTabs = new Button[3];
		mTabs[0] = (Button) findViewById(R.id.btn_set);
//		mTabs[1] = (Button) findViewById(R.id.btn_message);
		mTabs[1] = (Button) findViewById(R.id.btn_contract);
		
		iv_set_tips = (ImageView)findViewById(R.id.iv_set_tips);
//		iv_recent_tips = (ImageView)findViewById(R.id.iv_recent_tips);
		iv_contact_tips = (ImageView)findViewById(R.id.iv_contact_tipss);
		
		//把第一个tab设为选中状态
		mTabs[0].setSelected(true);
		
		rl_shoucang = (View)findViewById(R.id.rl_shoucang);
		findViewById(R.id.rl_userId).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		findViewById(R.id.tv_info).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		findViewById(R.id.img_userhead).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		findViewById(R.id.tv_search).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		findViewById(R.id.tv_setting).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		findViewById(R.id.user_name_tv).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		rl_shoucang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
	
	private void initTab(){
//		contactFragment = new ContactFragment();
//		recentFragment = new RecentFragment();
//		settingFragment = new SettingsFragment();
		
		myLineFragment = new MyLineFragment();
		jfFragment = new JourneyFriendsFragment();
		
		fragments = new Fragment[] {myLineFragment, jfFragment};
		// 添加显示第一个fragment
		/*getSupportFragmentManager().beginTransaction().
		add(R.id.fragment_container, recentFragment).
		add(R.id.fragment_container, contactFragment).
		hide(contactFragment).
		show(recentFragment).
		commit();*/
		getSupportFragmentManager().beginTransaction().
		add(R.id.fragment_container, myLineFragment).
		add(R.id.fragment_container, jfFragment).
		hide(jfFragment).
		show(myLineFragment).
		commit();
	}
	
	/**
	 * button点击事件
	 * @param view
	 */
	public void onTabSelect(View view) {
		switch (view.getId()) {
		case R.id.btn_set:
			index = 0;
			break;
		case R.id.btn_contract:
			index = 1;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		//把当前tab设为选中状态
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	@Override
	protected void onResume() {
		super.onResume();
		iv_set_tips.setVisibility(View.GONE);
//		//小圆点提示
//		if(BmobDB.create(this).hasUnReadMsg()){
//			iv_recent_tips.setVisibility(View.VISIBLE);
//		}else{
//			iv_recent_tips.setVisibility(View.GONE);
//		}
		if(BmobDB.create(this).hasNewInvite()){
			iv_contact_tips.setVisibility(View.VISIBLE);
		}else{
			iv_contact_tips.setVisibility(View.GONE);
		}
		MyMessageReceiver.ehList.add(this);// 监听推送的消息
		//清空
		MyMessageReceiver.mNewNum=0;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// 取消监听推送的消息
	}
	
	@Override
	public void onMessage(BmobMsg message) {
		refreshNewMsg(message);
	}
	
	
	/** 刷新界面
	  * @Title: refreshNewMsg
	  * @Description: 
	  * @param @param message 
	  * @return void
	  * @throws
	  */
	private void refreshNewMsg(BmobMsg message){
		// 声音提示
		boolean isAllow = CustomApplcation.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			CustomApplcation.getInstance().getMediaPlayer().start();
		}
//		iv_recent_tips.setVisibility(View.VISIBLE);
		//也要存储起来
		if(message!=null){
			BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(true,message);
		}
		if(currentTabIndex==1){
			//当前页面如果为会话页面，刷新此页面
//			if(jfFragment != null){
//				jfFragment.refresh();
//			}
		}
	}
	
	NewBroadcastReceiver  newReceiver;
	
	private void initNewMessageBroadCast(){
		// 注册接收消息广播
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_NEW_MESSAGE);
		//优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}
	
	/**
	 * 新消息广播接收者
	 * 
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//刷新界面
			refreshNewMsg(null);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}
	
	TagBroadcastReceiver  userReceiver;
	
	private void initTagMessageBroadCast(){
		// 注册接收消息广播
		userReceiver = new TagBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_ADD_USER_MESSAGE);
		//优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(userReceiver, intentFilter);
	}
	
	/**
	 * 标签消息广播接收者
	 */
	private class TagBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BmobInvitation message = (BmobInvitation) intent.getSerializableExtra("invite");
			refreshInvite(message);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}
	
	@Override
	public void onNetChange(boolean isNetConnected) {
		if(isNetConnected){
			ShowToast(R.string.network_tips);
		}
	}

	@Override
	public void onAddUser(BmobInvitation message) {
		refreshInvite(message);
	}
	
	/** 刷新好友请求
	  * @Title: notifyAddUser
	  * @Description: 
	  * @param @param message 
	  * @return void
	  * @throws
	  */
	private void refreshInvite(BmobInvitation message){
		boolean isAllow = CustomApplcation.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			CustomApplcation.getInstance().getMediaPlayer().start();
		}
		iv_contact_tips.setVisibility(View.VISIBLE);
		if(currentTabIndex==2){
//			if(jfFragment != null){
//				jfFragment.refresh();
//			}
		}else{
			//同时提醒通知
			String tickerText = message.getFromname()+"请求添加好友";
			boolean isAllowVibrate = CustomApplcation.getInstance().getSpUtil().isAllowVibrate();
			BmobNotifyManager.getInstance(this).showNotify(isAllow,isAllowVibrate,R.drawable.ic_launcher, tickerText, message.getFromname(), tickerText.toString(),NewFriendActivity.class);
		}
	}

	@Override
	public void onOffline() {
		showOfflineDialog(this);
	}
	
	@Override
	public void onReaded(String conversionId, String msgTime) {
	}
	
	
	private static long firstTime;
	/**
	 * 连续按两次返回键就退出
	 */
	@Override
	public void onBackPressed() {
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			ShowToast("再按一次退出程序");
		}
		firstTime = System.currentTimeMillis();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			unregisterReceiver(newReceiver);
		} catch (Exception e) {
		}
		try {
			unregisterReceiver(userReceiver);
		} catch (Exception e) {
		}
		//取消定时检测服务
		BmobChat.getInstance(this).stopPollService();
	}

	public void myAccount() {
		mDrawer_layout.openDrawer(Gravity.LEFT);
	}
	
}
