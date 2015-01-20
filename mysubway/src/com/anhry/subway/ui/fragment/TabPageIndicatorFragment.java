package com.anhry.subway.ui.fragment;

import java.util.LinkedList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anhry.subway.R;
import com.anhry.subway.ui.FragmentBase;
import com.anhry.subway.view.PagerSlidingTabStrip;

public abstract class TabPageIndicatorFragment extends FragmentBase {

	// �����Զ���fragment�����������б�
	public LinkedList<Fragment> list = new LinkedList<Fragment>();
	// �����Զ���fragment�ı�������������б�
	public LinkedList<String> titles = new LinkedList<String>();
	// �������ؼ���tabָʾ��
	public PagerSlidingTabStrip tabs;
	// ����fragment�Ļ�������
	public ViewPager pager;
	// ��������������
	public TabPageIndicatorAdapter adapter;
	//�ؼ�ԭ����0xFF666666���Զ��壺0x00666666
	public int currentColor = 0xFF0087FF;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tabpage_indicator, container, false);
		initView(rootView);
		return rootView;
	}

	protected void initView(View rootView) {
		// ��ʼ��PagerSlidingTabStrip�ؼ�
		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
		// ��ʼ�������е�fragment����
		setFragments(list);
		// ��ʼ��ÿ��fragment��Ӧ�ı���
		setTitles(titles);
		// ��ʼ��ViewPager�ؼ�
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		// ��ʼ��ViewPager��Ӧ��������
		adapter = new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
		// ����ÿ��page�ļ��
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		// ���ü��
		pager.setPageMargin(pageMargin);
//		pager.setOffscreenPageLimit(2);
		// ����������
		pager.setAdapter(adapter);
		// ��PagerSlidingTabStrip��ViewPager��
		tabs.setViewPager(pager);
		// ����ָʾ������ɫ
		tabs.setIndicatorColor(currentColor);
	}

	/** 
	 * @Title: setTabWidth 
	 * @˵       ��: ÿ������Ŀ��
	 * @��       ��: @param width   
	 * @return void    �������� 
	 * @throws 
	 */ 
	protected void setTabWidth(int width){
		tabs.setTabWidth(width);
	}
	
	/** 
	 * @Title: setTabWidth 
	 * @˵       ��: ÿ���������ɫ
	 * @��       ��: @param currentTextColor   
	 * @return void    �������� 
	 * @throws 
	 */ 
	protected void setCurrentTextColor(int currentTextColor){
		int color = getResources().getColor(currentTextColor);
		tabs.setCurrentTextColor(color);
	}
	/** 
	 * @Title: setTabWidth 
	 * @˵       ��: ÿ�������������ɫ
	 * @��       ��: @param dimensId   
	 * @return void    �������� 
	 * @throws 
	 */ 
	protected void setTabTextSize(int dimensId){
		int dimens = (int) getResources().getDimension(dimensId);
		tabs.setTextSize(dimens);
	}
	/** 
	 * @Title: setTabWidth 
	 * @˵       ��: ÿ���������浼�����ĸ߶�
	 * @��       ��: @param indicatorLineHeightPx   
	 * @return void    �������� 
	 * @throws 
	 */ 
	protected void setIndicatorHeight(int indicatorLineHeightPx){
		tabs.setIndicatorHeight(indicatorLineHeightPx);
	}

	/**
	 * �����е��Զ���fragment��ӵ���list�У�
	 * ���潫������ӵ�˳����ʾ��������ע��Ҫ��tabs�ı���˳��һ��
	 * @param fragmentList �����Զ����fragment����Ҫ��ӵ������������б���
	 */
	public abstract void setFragments(LinkedList<Fragment> fragmentList);

	/**
	 * �������Զ����fragment�ı�����ӵ���list�У�
	 * tabs��������ӵ�˳����ʾ��������ע��Ҫ��fragment��˳��һ��
	 * @param titleList �����Զ����fragment�ı��⣬��Ҫ��ӵ������������б���
	 */
	public abstract void setTitles(LinkedList<String> titleList);

	public class TabPageIndicatorAdapter extends FragmentPagerAdapter {

		public TabPageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return list.get(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles.get(position).toString();
		}

		@Override
		public int getCount() {
			return titles.size();
		}

	}

}
