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
 * ��;����
 * @ClassName: JourneyFriendsFragment 
 * @author zhangjiangbo 
 * @date 2015-1-17 ����1:05:42
 */
@SuppressLint("ResourceAsColor") 
public class JourneyFriendsFragment extends TabPageIndicatorFragment {
	public HeaderLayout mHeaderLayout;
	
	@Override
	protected void initView(View rootView) {
		// TODO Auto-generated method stub
		super.initView(rootView);
		initTopBarForLeft("��;����", rootView);
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
		// TODO �������Զ����fragment��ӵ���������
		
		setCurrentTextColor(R.color.theme_color);
		setIndicatorHeight(3);
		setTabTextSize(R.dimen.text_medium);
		setTabWidth(PixelUtil.getScreenWidth(getActivity())/3);
		
		fragmentList.add(new SameWayFragment());	// ͬ��·��Ⱥ
		fragmentList.add(new SameWayFragment());	// �ʼ�
		fragmentList.add(new ContactFragment());	// ����
	}

	@Override
	public void setTitles(LinkedList<String> titleList) {
		// TODO �������Զ����fragment�ı�����ӵ��������б���
		titleList.add("ͬ·��Ⱥ");
		titleList.add("������·");// ������·/·�˼�
		titleList.add("�ҵĺ���");
	}

}
