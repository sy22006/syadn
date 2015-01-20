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

	// 所有自定义fragment的有序容器列表
	public LinkedList<Fragment> list = new LinkedList<Fragment>();
	// 所有自定义fragment的标题的有序容器列表
	public LinkedList<String> titles = new LinkedList<String>();
	// 第三方控件：tab指示器
	public PagerSlidingTabStrip tabs;
	// 所有fragment的滑动容器
	public ViewPager pager;
	// 滑动容器适配器
	public TabPageIndicatorAdapter adapter;
	//控件原生：0xFF666666；自定义：0x00666666
	public int currentColor = 0xFF0087FF;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tabpage_indicator, container, false);
		initView(rootView);
		return rootView;
	}

	protected void initView(View rootView) {
		// 初始化PagerSlidingTabStrip控件
		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
		// 初始化容器中的fragment数组
		setFragments(list);
		// 初始化每个fragment对应的标题
		setTitles(titles);
		// 初始化ViewPager控件
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		// 初始化ViewPager对应的适配器
		adapter = new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
		// 定义每个page的间距
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		// 设置间距
		pager.setPageMargin(pageMargin);
//		pager.setOffscreenPageLimit(2);
		// 设置适配器
		pager.setAdapter(adapter);
		// 将PagerSlidingTabStrip与ViewPager绑定
		tabs.setViewPager(pager);
		// 设置指示器的颜色
		tabs.setIndicatorColor(currentColor);
	}

	/** 
	 * @Title: setTabWidth 
	 * @说       明: 每个标题的宽度
	 * @参       数: @param width   
	 * @return void    返回类型 
	 * @throws 
	 */ 
	protected void setTabWidth(int width){
		tabs.setTabWidth(width);
	}
	
	/** 
	 * @Title: setTabWidth 
	 * @说       明: 每个标题的颜色
	 * @参       数: @param currentTextColor   
	 * @return void    返回类型 
	 * @throws 
	 */ 
	protected void setCurrentTextColor(int currentTextColor){
		int color = getResources().getColor(currentTextColor);
		tabs.setCurrentTextColor(color);
	}
	/** 
	 * @Title: setTabWidth 
	 * @说       明: 每个标题的字体颜色
	 * @参       数: @param dimensId   
	 * @return void    返回类型 
	 * @throws 
	 */ 
	protected void setTabTextSize(int dimensId){
		int dimens = (int) getResources().getDimension(dimensId);
		tabs.setTextSize(dimens);
	}
	/** 
	 * @Title: setTabWidth 
	 * @说       明: 每个标题下面导航条的高度
	 * @参       数: @param indicatorLineHeightPx   
	 * @return void    返回类型 
	 * @throws 
	 */ 
	protected void setIndicatorHeight(int indicatorLineHeightPx){
		tabs.setIndicatorHeight(indicatorLineHeightPx);
	}

	/**
	 * 将所有的自定义fragment添加到此list中，
	 * 界面将按照添加的顺序显示，所以请注意要和tabs的标题顺序一致
	 * @param fragmentList 所有自定义的fragment，都要添加到此有序容器列表中
	 */
	public abstract void setFragments(LinkedList<Fragment> fragmentList);

	/**
	 * 将所有自定义的fragment的标题添加到此list中，
	 * tabs将按照添加的顺序显示，所以请注意要和fragment的顺序一致
	 * @param titleList 所有自定义的fragment的标题，都要添加到次有序容器列表中
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
