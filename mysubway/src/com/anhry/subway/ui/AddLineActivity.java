package com.anhry.subway.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.anhry.subway.R;
import com.anhry.subway.adapter.AddLineAdapter;
import com.anhry.subway.bean.Line;
import com.anhry.subway.bean.SubWayLine;
import com.anhry.subway.bean.User;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

/**
 * 添加路线
 * 
 * @ClassName: AddLineActivity
 * @author zhangjiangbo
 * @date 2015-1-5 下午2:43:25
 */
public class AddLineActivity extends ActivityBase implements OnClickListener, OnGetRoutePlanResultListener {
	EditText et_find_name;
	EditText statr_name;
	EditText end_name;
	
	Button btn_search;
	Button btn_line;
	
	String start;
	String end;

	ListView mListView;
	AddLineAdapter adapter;
	RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	List<Line> lineList = new ArrayList<Line>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_line);
		initView();
	}

	private void initView() {
		initTopBarForLeft("添加路线", "返回");
		et_find_name = (EditText) findViewById(R.id.et_find_name);
		statr_name = (EditText) findViewById(R.id.statr_name);
		end_name = (EditText) findViewById(R.id.end_name);

		btn_search = (Button) findViewById(R.id.btn_search);
		btn_line = (Button) findViewById(R.id.btn_line);
		btn_search.setOnClickListener(this);
		btn_line.setOnClickListener(this);

		// 初始化搜索模块，注册事件监听
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);

		initXListView();
		
		
	}

	private void initXListView() {
		mListView = (ListView) findViewById(R.id.list_search);
		adapter = new AddLineAdapter(this, lineList);
		mListView.setAdapter(adapter);
	}
	
	/**
	 * 插入对象
	 */
	private void insertObject(List<VehicleInfo> vehList) {
		final SubWayLine p2 = new SubWayLine();
		VehicleInfo info = vehList.get(0);
//		p2.setStartName(info.getTitle());
		p2.setStartName(start);
		p2.setEndName(end);
		p2.setPrice("" + info.getTotalPrice());
		p2.setStationNum(info.getPassStationNum());
//		info = vehList.get(vehList.size() - 1);
//		p2.setEndName(info.getTitle());
		p2.setLineLong("16");
		final User user = (User) userManager.getCurrentUser(User.class);
		p2.setUserId(user.getObjectId());
		p2.save(this, new SaveListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(AddLineActivity.this, "选择路线成功！", Toast.LENGTH_LONG).show();
				setResult(Activity.RESULT_OK);
				AddLineActivity.this.finish();
			}
			@Override
			public void onFailure(int code, String msg) {
				// toast("创建数据失败：" + msg);
			}
		});
	}
	
	public void queryObjects(String startName, String endName, final List<VehicleInfo> vehList){
		final User user = (User) userManager.getCurrentUser(User.class);
		String userId = user.getObjectId();
		BmobQuery<SubWayLine> bmobQuery	 = new BmobQuery<SubWayLine>();
		bmobQuery.addWhereEqualTo("userId", userId);
		bmobQuery.addWhereEqualTo("startName", startName);
		bmobQuery.addWhereEqualTo("endName", endName);
		bmobQuery.findObjects(AddLineActivity.this, new FindListener<SubWayLine>() {
			@Override
			public void onSuccess(List<SubWayLine> object) {
				if(null != object && object.size() >0){
					Toast.makeText(AddLineActivity.this, "你已经添加过这个线路了！", Toast.LENGTH_SHORT).show();
				}else{
					insertObject(vehList);
				}
			}
			@Override
			public void onError(int code, String msg) {
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_search:// 搜索
			search();
			break;
		case R.id.btn_line:	//添加
			Line line = null;
			for(Line mod:lineList){
				if(mod.isChoose()){
					line = mod;
				}
			}
			if(null == line){
				Toast.makeText(AddLineActivity.this, "请选择一条线路", Toast.LENGTH_SHORT).show();
				return;
			}
			queryObjects(start, end, line.getVehList());
			break;
		default:
			break;
		}
	}

	/**************************************************************************************/

	public void search() {
		start = statr_name.getText().toString();
		if(TextUtils.isEmpty(start)){
			statr_name.setError("请输入起点");
			statr_name.setFocusable(true);
			return ;
		}
		end = end_name.getText().toString();
		if(TextUtils.isEmpty(end)){
			end_name.setError("请输入终点");
			end_name.setFocusable(true);
			return ;
		}
		PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", start);
		PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", end);
		mSearch.transitSearch((new TransitRoutePlanOption()).from(stNode).city("北京").to(enNode));
		mListView.setEmptyView(findViewById(R.id.empview));
		lineList.clear();
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(AddLineActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
			findViewById(R.id.empview).setVisibility(View.GONE);
			mListView.setEmptyView(findViewById(R.id.emptext));
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			List<TransitRouteLine> list = result.getRouteLines();
			if(null != list && list.size() >0){
				lineList.clear();
				for (TransitRouteLine line : list) {
					List<TransitStep> transitSteps = line.getAllStep();

					List<VehicleInfo> vehicleList = new ArrayList<VehicleInfo>();
					int tempDistance = 0;
					int tempDuration = 0;
					for (TransitStep transitStep : transitSteps) {
						tempDistance += transitStep.getDistance();
						tempDuration += transitStep.getDuration();

						VehicleInfo vehicleInfo = transitStep.getVehicleInfo();
						if (null != vehicleInfo) {
							vehicleList.add(vehicleInfo);
						}
					}
					Line l = new Line(vehicleList, tempDistance, tempDuration, false);
					lineList.add(l);
				}
				adapter.notifyDataSetChanged();
				// TransitRouteOverlay overlay = new
				// MyTransitRouteOverlay(mBaidumap);
				// mBaidumap.setOnMarkerClickListener(overlay);
				// overlay.setData(result.getRouteLines().get(0));
				// overlay.addToMap();
				// overlay.zoomToSpan();
			}else {
				mListView.setEmptyView(findViewById(R.id.emptext));
			}
		}
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult arg0) { }

	@Override
	protected void onDestroy() {
		mSearch.destroy();
		super.onDestroy();
	}
}