package com.viv.sh.dto;

public class EquityData {
	private String symbol;
	private double openValue;
	private double highValue;
	private double lowValue;
	private double closeValue;
	private double lastVal;
	private double prevClose;
	private int totTradeQty;
	private double totTradeVal;
	private int totTrade;
	private double percChange;
	
	
	public double getOpenValue() {
		return openValue;
	}
	public void setOpenValue(double openValue) {
		this.openValue = openValue;
	}
	public double getHighValue() {
		return highValue;
	}
	public void setHighValue(double highValue) {
		this.highValue = highValue;
	}
	public double getLowValue() {
		return lowValue;
	}
	public void setLowValue(double lowValue) {
		this.lowValue = lowValue;
	}
	public double getCloseValue() {
		return closeValue;
	}
	public void setCloseValue(double closeValue) {
		this.closeValue = closeValue;
	}
	
	public double getPercChange() {
		return percChange;
	}
	public void setPercChange(double percChange) {
		this.percChange = percChange;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getLastVal() {
		return lastVal;
	}
	public void setLastVal(double lastVal) {
		this.lastVal = lastVal;
	}
	public double getPrevClose() {
		return prevClose;
	}
	public void setPrevClose(double prevClose) {
		this.prevClose = prevClose;
	}
	public int getTotTradeQty() {
		return totTradeQty;
	}
	public void setTotTradeQty(int totTradeQty) {
		this.totTradeQty = totTradeQty;
	}
	public double getTotTradeVal() {
		return totTradeVal;
	}
	public void setTotTradeVal(double totTradeVal) {
		this.totTradeVal = totTradeVal;
	}
	public int getTotTrade() {
		return totTrade;
	}
	public void setTotTrade(int totTrade) {
		this.totTrade = totTrade;
	}
	
	
}
