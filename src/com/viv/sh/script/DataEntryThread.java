package com.viv.sh.script;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.jmx.snmp.Timestamp;
import com.viv.sh.dto.PriceDto;
import com.viv.sh.service.DataEntryService;

public class DataEntryThread implements Runnable{
	
	private String[] tableList;
	private int threadInterval;
	public DataEntryThread(String[] ar, int interval){
		tableList=ar;
		threadInterval=interval;
	}
	
	@Override
	public void run(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/viv/sh/script/admin-script.xml");
		BeanFactory factory = (BeanFactory) context;
		DataEntryService dataEntryService = (DataEntryService) factory.getBean("dataEntryService");
//		
		//List l--> new object[]{a1,545566 } 
		ArrayList< Object[]> shNumericCodeList=new ArrayList<Object[]>();
		for(String table: tableList){
			shNumericCodeList.addAll(dataEntryService.getQuoteAndTableName(table));
			
		}
		
		Date startDate;
		 int i=0;
		while(true){
			//Initiate time of starting thread
			startDate=new Date();
			//Iterate list of quote and table name
			for(Object[] obj:shNumericCodeList){
				if(obj!=null){
					//Get Current price from feed http://www.bseindia.com/stock-share-price/SiteCache/EQHeaderData.aspx?text=532667
					PriceDto dto=dataEntryService.getCurrentPriceObject(Integer.parseInt(obj[1].toString()));
					
//					double db=1.1;
//					PriceDto dto=new PriceDto();
					if(dto!=null){
					dto.setTableName(obj[0].toString());
					dto.setNumericCode(Integer.parseInt(obj[1].toString()));
					//Insert current data in database of table name
					dataEntryService.insertStockData(dto);
					}
//					System.out.println(obj[1]+ ":: Data entry complete");
				}
			}
			Date endDate=new Date();
			long differance=(endDate.getTime()-startDate.getTime())/1000;
			if(differance<threadInterval){
				try{
					Thread.sleep(threadInterval-differance);
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Exception throws in thread :" +tableList);
				}
			}
			
			if((endDate.getHours()>=15 && endDate.getMinutes()>30) || (endDate.getHours()<9)){
				System.out.println("Exiting the data insert thread "+tableList +" at "+endDate);
				break;
			}
//			i++;
//			if(i==5) break;
		}
	}

}
