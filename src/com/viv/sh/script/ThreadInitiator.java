package com.viv.sh.script;

import org.springframework.beans.factory.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.viv.sh.service.DataEntryService;
import com.viv.sh.util.Constants;

public class ThreadInitiator {

	public static void main(String[] args) {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/viv/sh/script/admin-script.xml");
//		//new String[] {"applicationContext.xml", "applicationContext-part2.xml"});
//
//		BeanFactory factory = (BeanFactory) context;
//		DataEntryService dataEntryService = (DataEntryService) factory.getBean("dataEntryService");
//		//List<CountBean> cBean = 
//		dataEntryService.insertSampleData("vivek");
//		System.out.println("Inserted");
		// Below thread will insert the raw data in table
		(new Thread(new DataEntryThread(new String[]{"a1","a2","a3"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a4","a5","a6"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a7","a8","a9"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a10","a11","a12"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a13","a14","a15"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a16","a17","a18"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a19","a20","a21"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a22","a23","a24"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a23","a24","a25"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a26","a27","a28"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a29","a30","a31"}, Constants.aGroupTimeDiff))).start();
		(new Thread(new DataEntryThread(new String[]{"a32"}, Constants.aGroupTimeDiff))).start();
		
		
		//Below code will manufacture the data for output
		(new Thread(new ProcessDataThread(new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a10","a11","a12","a13","a14","a15","a16","a17","a18","a19","a20","a21","a22","a23","a24","a25","a26","a27","a28","a29","a30","a31","a32",}, Constants.aGroupTimeDiff))).start();
		
		
		
	}

}
