package com.viv.sh.command;

public class BuyForm {
	private int numericCode;
	private double buyVal;
	private int quantity;
	private String error;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getNumericCode() {
		return numericCode;
	}
	public void setNumericCode(int numericCode) {
		this.numericCode = numericCode;
	}
	public double getBuyVal() {
		return buyVal;
	}
	public void setBuyVal(double buyVal) {
		this.buyVal = buyVal;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
