package com.viv.sh.dto;

public class PriceDto {
	private double currentPrice;
	private double openPrice;
	private String tableName;
	private Integer numericCode;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Integer getNumericCode() {
		return numericCode;
	}
	public void setNumericCode(Integer numericCode) {
		this.numericCode = numericCode;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	 

}
