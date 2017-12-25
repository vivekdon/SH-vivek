package com.viv.sh.script;

import java.io.File;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.viv.sh.service.DataEntryService;
import com.viv.sh.util.Constants;

public class InsertEndDay {

	public static void main(String[] args) {
		
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/viv/sh/script/admin-script.xml");
		BeanFactory factory = (BeanFactory) context;
		DataEntryService dataEntryService = (DataEntryService) factory.getBean("dataEntryService");
		try{
			File folder=new File(Constants.bhavCopyPath+"un-processed");
			for ( File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		            System.out.println("Dont read");
		        } else {
		           String fileName= fileEntry.getName();
		           if(fileName.contains(".CSV")){
		        	   System.out.println(fileName);
		        	   dataEntryService.insertHistoryData(fileEntry);
		        	   
		        	   //moveFileToProcesseFolder(fileEntry);
		           }
		        }
		    }
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
