package com.anhry.subway.bean;

import java.util.List;

import com.baidu.mapapi.search.core.VehicleInfo;

public class Line {
	private List<VehicleInfo> vehList;	// 线路
	private int distance;				// 米
	private int duration;				// 分
	private boolean choose;				// 选择
	public Line(){}
	public Line(List<VehicleInfo> vehicleList) {
		this.vehList = vehicleList;
	}
	public Line(List<VehicleInfo> vehicleList, int distance, int duration, boolean choose) {
		this.vehList = vehicleList;
		this.distance = distance;
		this.duration = duration;
		this.choose = choose;
	}
	public List<VehicleInfo> getVehList() {
		return vehList;
	}
	public void setVehList(List<VehicleInfo> vehList) {
		this.vehList = vehList;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public boolean isChoose() {
		return choose;
	}
	public void setChoose(boolean choose) {
		this.choose = choose;
	}
}
