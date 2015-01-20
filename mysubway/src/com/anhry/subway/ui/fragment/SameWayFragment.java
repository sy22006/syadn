package com.anhry.subway.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anhry.subway.R;
import com.anhry.subway.ui.FragmentBase;

/**
 * 同线路
 * @ClassName: SameWayFragment 
 * @author zhangjiangbo 
 * @date 2015-1-17 下午2:46:35
 */
public class SameWayFragment extends FragmentBase{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contacts, container, false);
	}

}
