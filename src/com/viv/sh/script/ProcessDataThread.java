package com.viv.sh.script;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.viv.sh.dto.PriceDto;
import com.viv.sh.service.DataEntryService;

public class ProcessDataThread implements Runnable {

	private String[] tableList;
	private int threadInterval;

	public ProcessDataThread(String[] ar, int interval) {
		tableList = ar;
		threadInterval = interval;
	}

	@Override
	public void run() {
		try{
		Thread.sleep(20000);
		}catch(Exception e){
			e.printStackTrace();
		}
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/viv/sh/script/admin-script.xml");
		BeanFactory factory = (BeanFactory) context;
		DataEntryService dataEntryService = (DataEntryService) factory.getBean("dataEntryService");
		//

		Date startDate;
		// int i=0;
		while (true) {

			// Initiate time of starting thread
			startDate = new Date();
			// delete data in data_comp table
			dataEntryService.delDataComp();
			// Insert data in data_comp by iterating table name
			for (String tableName : tableList) {
				if (tableName != null) {
					// Get process the data
					dataEntryService.manufactureData(tableName);

				}
			}
			//Updating data percentage in data_comp table
			dataEntryService.updateDataComp();
			System.out.println("Data processed at "+Calendar.getInstance().getTime());
			//checking for sleep 
			Date endDate = new Date();
			long differance = (endDate.getTime() - startDate.getTime()) / 1000;
			if (differance < threadInterval) {
				try {
					Thread.sleep(threadInterval - differance);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Exception throws in thread :" + tableList);
				}
			}

			if ((endDate.getHours() >= 15 && endDate.getMinutes() > 30) || (endDate.getHours() <9 )) {
				System.out.println("Exiting the data data process thread at "+endDate);
				break;
			}
		}
		// i++;
		// if(i==5) break;

	}

}
