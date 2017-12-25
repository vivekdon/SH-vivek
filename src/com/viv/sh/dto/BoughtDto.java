package com.viv.sh.dto;

import java.util.ArrayList;
import java.util.List;

public class BoughtDto {
	private int numericCode;
	private String stock;
	private double quantity;
	private double price;
	private double totalAmount;
	private List<Double> currentPriceList;
	
	private List<Double> percentList;
	
	
	
	
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public List<Double> getPercentList() {
		return percentList;
		
		
	}
	public void setPercentList() {
		if(currentPriceList!=null && currentPriceList.size()>0){
			percentList=new ArrayList<Double>();
			for(Double dt: currentPriceList){
				 percentList.add(Math.floor( ((dt-price)*100/price)*1000)/1000);
			}
		}
	}
	public int getNumericCode() {
		return numericCode;
	}
	public void setNumericCode(int numericCode) {
		this.numericCode = numericCode;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<Double> getCurrentPriceList() {
		return currentPriceList;
	}
	public void setCurrentPriceList(List<Double> currentPriceList) {
		this.currentPriceList = currentPriceList;
	}
	
}
