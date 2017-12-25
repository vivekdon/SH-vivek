package com.viv.sh.service;

import java.util.List;

import com.viv.sh.command.BuyForm;
import com.viv.sh.dao.BuyDao;
import com.viv.sh.dto.BoughtDto;

public class BuyService {
	private BuyDao buyDao;

	public void setBuyDao(BuyDao buyDao) {
		this.buyDao = buyDao;
	}
	
	public boolean isValidCode(int numericCode){
		return buyDao.isValidCode(numericCode);
	}
	
	public void saveBuy(BuyForm buyForm) throws Exception{
		buyDao.saveBuy(buyForm);
	}
	
	public String saveSale(BuyForm buyForm) throws Exception{
		return buyDao.saveSale(buyForm) ;
	}
	
	
	public List<BoughtDto> getBoughtDataList(){
		List<BoughtDto> boughtList=buyDao.getBoughtList();
		for(BoughtDto dto: boughtList){
			dto.setCurrentPriceList(buyDao.getCurrentPriceList(dto.getNumericCode()));
			dto.setPercentList();
		}
		return boughtList;
		
	}
}
