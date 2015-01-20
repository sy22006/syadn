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
 * ������
 * @ClassName: MainActivity
 * @Description: 
 * @author smile
 * @date 2014-5-29 ����2:45:35
 */
public class MainActivity extends ActivityBase implements EventListener{
	private Button[] mTabs;
//	private ContactFragment contactFragment;
//	private RecentFragment recentFragment;
//	private SettingsFragment settingFragment;	// ����
	private MyLineFragment myLineFragment;		// �ҵ���·
	private JourneyFriendsFragment jfFragment;	// ��;����
	
	private Fragment[] fragments;
	private int index;
	private int currentTabIndex;
	private DrawerLayout mDrawer_layout;// DrawerLayout����
	
	ImageView iv_set_tips,iv_contact_tips;//��Ϣ��ʾ
	
	View rl_shoucang;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//������ʱ�����񣨵�λΪ�룩-���������̨�Ƿ���δ������Ϣ���еĻ���ȡ����
		//�������ü�����ȽϺ������͵�������Ҳ����ȥ����仰-ͬʱ����onDestory���������stopPollService����
		BmobChat.getInstance(this).startPollService(30);
		//�����㲥������
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
		
		//�ѵ�һ��tab��Ϊѡ��״̬
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
		// �����ʾ��һ��fragment
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
	 * button����¼�
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
		//�ѵ�ǰtab��Ϊѡ��״̬
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	@Override
	protected void onResume() {
		super.onResume();
		iv_set_tips.setVisibility(View.GONE);
//		//СԲ����ʾ
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
		MyMessageReceiver.ehList.add(this);// �������͵���Ϣ
		//���
		MyMessageReceiver.mNewNum=0;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// ȡ���������͵���Ϣ
	}
	
	@Override
	public void onMessage(BmobMsg message) {
		refreshNewMsg(message);
	}
	
	
	/** ˢ�½���
	  * @Title: refreshNewMsg
	  * @Description: 
	  * @param @param message 
	  * @return void
	  * @throws
	  */
	private void refreshNewMsg(BmobMsg message){
		// ������ʾ
		boolean isAllow = CustomApplcation.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			CustomApplcation.getInstance().getMediaPlayer().start();
		}
//		iv_recent_tips.setVisibility(View.VISIBLE);
		//ҲҪ�洢����
		if(message!=null){
			BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(true,message);
		}
		if(currentTabIndex==1){
			//��ǰҳ�����Ϊ�Ựҳ�棬ˢ�´�ҳ��
//			if(jfFragment != null){
//				jfFragment.refresh();
//			}
		}
	}
	
	NewBroadcastReceiver  newReceiver;
	
	private void initNewMessageBroadCast(){
		// ע�������Ϣ�㲥
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_NEW_MESSAGE);
		//���ȼ�Ҫ����ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}
	
	/**
	 * ����Ϣ�㲥������
	 * 
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//ˢ�½���
			refreshNewMsg(null);
			// �ǵðѹ㲥���ս��
			abortBroadcast();
		}
	}
	
	TagBroadcastReceiver  userReceiver;
	
	private void initTagMessageBroadCast(){
		// ע�������Ϣ�㲥
		userReceiver = new TagBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_ADD_USER_MESSAGE);
		//���ȼ�Ҫ����ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(userReceiver, intentFilter);
	}
	
	/**
	 * ��ǩ��Ϣ�㲥������
	 */
	private class TagBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BmobInvitation message = (BmobInvitation) intent.getSerializableExtra("invite");
			refreshInvite(message);
			// �ǵðѹ㲥���ս��
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
	
	/** ˢ�º�������
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
			//ͬʱ����֪ͨ
			String tickerText = message.getFromname()+"������Ӻ���";
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
	 * ���������η��ؼ����˳�
	 */
	@Override
	public void onBackPressed() {
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			ShowToast("�ٰ�һ���˳�����");
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
		//ȡ����ʱ������
		BmobChat.getInstance(this).stopPollService();
	}

	public void myAccount() {
		mDrawer_layout.openDrawer(Gravity.LEFT);
	}
	
}
