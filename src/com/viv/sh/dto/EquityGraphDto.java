package com.viv.sh.dto;

public class EquityGraphDto {
	
	private double openVal;
	private double highVal;
	private double lowVal;
	private double closeVal;
	private int volume;
	private double tradeVal;
	private String valueDate;
	
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public double getOpenVal() {
		return openVal;
	}
	public void setOpenVal(double openVal) {
		this.openVal = openVal;
	}
	public double getHighVal() {
		return highVal;
	}
	public void setHighVal(double highVal) {
		this.highVal = highVal;
	}
	public double getLowVal() {
		return lowVal;
	}
	public void setLowVal(double lowVal) {
		this.lowVal = lowVal;
	}
	public double getCloseVal() {
		return closeVal;
	}
	public void setCloseVal(double closeVal) {
		this.closeVal = closeVal;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public double getTradeVal() {
		return tradeVal;
	}
	public void setTradeVal(double tradeVal) {
		this.tradeVal = tradeVal;
	}
	
	
	

}
