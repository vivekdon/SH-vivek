package com.viv.sh.dto;

public class OptionDataDto {
	
	private String symbol;
	private String expDate;
	private double strPrice;
	private String optType;
	
	private double cp1;
	private double v1;
	private double tq1;
	private String vd1;
	
	private double cp1p;
	private double v1p;
	private double tq1p;
	
	private double cp2;
	private double v2;
	private double tq2;
	private String vd2;
	
	private double cp2p;
	private double v2p;
	private double tq2p;
	
	private double cp3;
	private double v3;
	private double tq3;
	private String vd3;
	
	private double cp3p;
	private double v3p;
	private double tq3p;
	
	private double cp4;
	private double v4;
	private double tq4;
	private String vd4;
	
	private double cp4p;
	private double v4p;
	private double tq4p;
	
	private double cp5;
	private double v5;
	private double tq5;
	private String vd5;
	
	private double cp5p;
	private double v5p;
	private double tq5p;
	
	private double cp6;
	private double v6;
	private double tq6;
	private String vd6;
	
	
	
	public String getOptType() {
		if(optType!=null)
			optType=optType.trim();
		
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public double getCp1p() {
		if(cp2!=0){
			cp1p=(cp1-cp2)*100/cp2;
		}
		return Math.floor(cp1p * 100) / 100;
	}
	public double getV1p() {
		if(v2!=0){
			v1p=(v1-v2)*100/v2;
		}
		return Math.floor(v1p* 100) / 100;
	}
	public double getTq1p() {
		if(tq2!=0){
			tq1p=(tq1-tq2)*100/tq2;
		}
		return Math.floor(tq1p* 100) / 100;
	}
	
	public double getCp2p() {
		if(cp2!=0 && cp3!=0){
			cp2p=(cp2-cp3)*100/cp3;
		}
		return Math.floor(cp2p* 100) / 100;
	}
	public double getV2p() {
		if(v2!=0 && v3!=0){
			v2p=(v2-v3)*100/v3;
		}
		return Math.floor(v2p* 100) / 100;
	}
	public double getTq2p() {
		if(tq2!=0 && tq3!=0){
			tq2p=(tq2-tq3)*100/tq3;
		}
		return Math.floor(tq2p* 100) / 100;
	}
	
	public double getCp3p() {
		if(cp3!=0 && cp4!=0){
			cp3p=(cp3-cp4)*100/cp4;
		}
		return Math.floor(cp3p* 100) / 100;
	}
	public double getV3p() {
		if(v3!=0 && v4!=0){
			v3p=(v3-v4)*100/v4;
		}
		return Math.floor(v3p* 100) / 100;
	}
	public double getTq3p() {
		if(tq3!=0 && tq4!=0){
			tq3p=(tq3-tq4)*100/tq4;
		}
		return Math.floor(tq3p* 100) / 100;
	}
	
	public double getCp4p() {
		if(cp4!=0 && cp5!=0){
			cp4p=(cp4-cp5)*100/cp5;
		}
		return Math.floor(cp4p* 100) / 100;
	}
	public double getV4p() {
		if(v4!=0 && v5!=0){
			v4p=(v4-v5)*100/v5;
		}
		return Math.floor(v4p* 100) / 100;
	}
	public double getTq4p() {
		if(tq4!=0 && tq5!=0){
			tq4p=(tq4-tq5)*100/tq5;
		}
		return Math.floor(tq4p* 100) / 100;
	}
	
	public double getCp5p() {
		if(cp5!=0 && cp6!=0){
			cp5p=(cp5-cp6)*100/cp6;
		}
		return Math.floor(cp5p* 100) / 100;
	}
	public double getV5p() {
		if(v5!=0 && v6!=0){
			v5p=(v5-v6)*100/v6;
		}
		return Math.floor(v5p* 100) / 100;
	}
	public double getTq5p() {
		if(tq5!=0 && tq6!=0){
			tq5p=(tq5-tq6)*100/tq6;
		}
		return Math.floor(tq5p* 100) / 100;
	}
	
	public String getSymbol() {
		if(symbol!=null)
			symbol=symbol.trim();
		
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public double getStrPrice() {
		return strPrice;
	}
	public void setStrPrice(double strPrice) {
		this.strPrice = strPrice;
	}
	public double getCp1() {
		return cp1;
	}
	public void setCp1(double cp1) {
		this.cp1 = cp1;
	}
	public double getV1() {
		return Math.floor(v1 * 100) / 100;
	}
	public void setV1(double v1) {
		this.v1 = v1;
	}
	public double getTq1() {
		return tq1;
	}
	public void setTq1(double tq1) {
		this.tq1 = tq1;
	}
	public String getVd1() {
		return vd1;
	}
	public void setVd1(String vd1) {
		this.vd1 = vd1;
	}
	public double getCp2() {
		return cp2;
	}
	public void setCp2(double cp2) {
		this.cp2 = cp2;
	}
	public double getV2() {
		return  Math.floor(v2 * 100) / 100;
	}
	public void setV2(double v2) {
		this.v2 = v2;
	}
	public double getTq2() {
		return tq2;
	}
	public void setTq2(double tq2) {
		this.tq2 = tq2;
	}
	public String getVd2() {
		return vd2;
	}
	public void setVd2(String vd2) {
		this.vd2 = vd2;
	}
	public double getCp3() {
		return cp3;
	}
	public void setCp3(double cp3) {
		this.cp3 = cp3;
	}
	public double getV3() {
		return Math.floor(v3 * 100) / 100;
	}
	public void setV3(double v3) {
		this.v3 = v3;
	}
	public double getTq3() {
		return tq3;
	}
	public void setTq3(double tq3) {
		this.tq3 = tq3;
	}
	public String getVd3() {
		return vd3;
	}
	public void setVd3(String vd3) {
		this.vd3 = vd3;
	}
	public double getCp4() {
		return cp4;
	}
	public void setCp4(double cp4) {
		this.cp4 = cp4;
	}
	public double getV4() {
		return Math.floor(v4 * 100) / 100;
	}
	public void setV4(double v4) {
		this.v4 = v4;
	}
	public double getTq4() {
		return tq4;
	}
	public void setTq4(double tq4) {
		this.tq4 = tq4;
	}
	public String getVd4() {
		return vd4;
	}
	public void setVd4(String vd4) {
		this.vd4 = vd4;
	}
	public double getCp5() {
		return cp5;
	}
	public void setCp5(double cp5) {
		this.cp5 = cp5;
	}
	public double getV5() {
		return Math.floor(v5 * 100) / 100;
	}
	public void setV5(double v5) {
		this.v5 = v5;
	}
	public double getTq5() {
		return tq5;
	}
	public void setTq5(double tq5) {
		this.tq5 = tq5;
	}
	public String getVd5() {
		return vd5;
	}
	public void setVd5(String vd5) {
		this.vd5 = vd5;
	}
	public double getCp6() {
		return cp6;
	}
	public void setCp6(double cp6) {
		this.cp6 = cp6;
	}
	public double getV6() {
		return Math.floor(v6 * 100) / 100;
	}
	public void setV6(double v6) {
		this.v6 = v6;
	}
	public double getTq6() {
		return tq6;
	}
	public void setTq6(double tq6) {
		this.tq6 = tq6;
	}
	public String getVd6() {
		return vd6;
	}
	public void setVd6(String vd6) {
		this.vd6 = vd6;
	}
	
	

}
