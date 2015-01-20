package com.anhry.subway.ui.fragment;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;

import com.anhry.subway.R;
import com.anhry.subway.util.PixelUtil;
import com.anhry.subway.ui.MainActivity;
import com.anhry.subway.view.HeaderLayout;
import com.anhry.subway.view.HeaderLayout.HeaderStyle;
import com.anhry.subway.view.HeaderLayout.onLeftImageButtonClickListener;

/**
 * 旅途交友
 * @ClassName: JourneyFriendsFragment 
 * @author zhangjiangbo 
 * @date 2015-1-17 下午1:05:42
 */
@SuppressLint("ResourceAsColor") 
public class JourneyFriendsFragment extends TabPageIndicatorFragment {
	public HeaderLayout mHeaderLayout;
	
	@Override
	protected void initView(View rootView) {
		// TODO Auto-generated method stub
		super.initView(rootView);
		initTopBarForLeft("旅途交友", rootView);
	}
	public void initTopBarForLeft(String titleName, View rootView) {
		mHeaderLayout = (HeaderLayout)rootView.findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_LIFT_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton(titleName, R.drawable.right_menu, new onLeftImageButtonClickListener(){
			@Override
			public void onClick() {
				((MainActivity)getActivity()).myAccount();
			}
		});
	}
	
	@Override
	public void setFragments(LinkedList<Fragment> fragmentList) {
		// TODO 将所有自定义的fragment添加到此容器中
		
		setCurrentTextColor(R.color.theme_color);
		setIndicatorHeight(3);
		setTabTextSize(R.dimen.text_medium);
		setTabWidth(PixelUtil.getScreenWidth(getActivity())/3);
		
		fragmentList.add(new SameWayFragment());	// 同线路人群
		fragmentList.add(new SameWayFragment());	// 邮件
		fragmentList.add(new ContactFragment());	// 公车
	}

	@Override
	public void setTitles(LinkedList<String> titleList) {
		// TODO 将所有自定义的fragment的标题添加到此容器列表中
		titleList.add("同路人群");
		titleList.add("热门线路");// 他人线路/路人甲
		titleList.add("我的好友");
	}

}
