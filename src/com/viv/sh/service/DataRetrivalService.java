package com.viv.sh.service;

import java.util.ArrayList;
import java.util.List;

import com.viv.sh.dao.DataRetrievalDao;
import com.viv.sh.dto.CandleGaphDto;
import com.viv.sh.dto.DataComp;
import com.viv.sh.dto.EquityData;
import com.viv.sh.dto.EquityDataDto;
import com.viv.sh.dto.EquityGraphDto;
import com.viv.sh.dto.GraphDto;
import com.viv.sh.dto.OptionDataDto;
import com.viv.sh.dto.PriceDto;

public class DataRetrivalService {
	
	private DataRetrievalDao dataRetrievalDao;
	
	
	public void setDataRetrievalDao(DataRetrievalDao dataRetrievalDao) {
		this.dataRetrievalDao = dataRetrievalDao;
	}


	public List<DataComp> getTableData(String sortBy,String sortType){ 
		return dataRetrievalDao.getTableData(sortBy,sortType);
	}
	public List<EquityData> getSingleCandleData(String patternType,String sortType, String group,String date ){
		if(patternType!=null && patternType.equalsIgnoreCase("doji")){
			return dataRetrievalDao.getDojiData(sortType,group,date);
		}else if(patternType!=null && patternType.equalsIgnoreCase("pChange")){
			return dataRetrievalDao.getPerChangeData(sortType,group,date);
		}else{
			return dataRetrievalDao.getMarubuzoData(sortType,group,date);
		}
	}
	
	
	public List<GraphDto> getGraphData(String numericCode){
		return dataRetrievalDao.getGraphData(numericCode);
	}
	
	public List<CandleGaphDto> getCandleChartData(String symbol){
		System.out.println(symbol);
		return dataRetrievalDao.getCandleChartData(symbol);
	}
	
	public List<String>getSectorName(){
		return dataRetrievalDao.getSectorName();
	}
	public List<EquityDataDto> getEquityTableData(String sector,String symbol, String high52, String low52, String limit){
		return dataRetrievalDao.getEquityTableData(sector,symbol,high52,low52,limit);
	}
	
	public List<OptionDataDto> getOptionTableData(String symbol, String index,String  expDate,String  type){
		return dataRetrievalDao.getOptionTableData(symbol,index, expDate, type);
	}
	
	public ArrayList getIndexExpiry(String symbol){
		return dataRetrievalDao.getIndexExpiry(symbol);
	}
	
	public List<EquityGraphDto> getEquityGraphData(String symbol){
		return dataRetrievalDao.getEquityGraphData(symbol);
		
	}

	public List<EquityGraphDto> getOptionGraphData(String symbol, String expDate,String strPrice,String optType){
		return dataRetrievalDao.getOptionGraphData(symbol, expDate,strPrice,optType);
		
	}

}
