package com.anhry.subway.bean;

import cn.bmob.v3.BmobObject;


/** 
 * �ҵ�·��
 * @ClassName: SubWayLine 
 * @author zhangjiangbo 
 * @date 2015-1-5 ����1:40:52 
 */
public class SubWayLine extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startName;	// start station name
	private String endName;		// end station name
	private String userId;		// people ID
	private Integer stationNum; // station number
	private String lineLong;	// line long kilometre
	private String price;		// line price
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	public String getEndName() {
		return endName;
	}
	public void setEndName(String endName) {
		this.endName = endName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getStationNum() {
		return stationNum;
	}
	public void setStationNum(Integer stationNum) {
		this.stationNum = stationNum;
	}
	public String getLineLong() {
		return lineLong;
	}
	public void setLineLong(String lineLong) {
		this.lineLong = lineLong;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
