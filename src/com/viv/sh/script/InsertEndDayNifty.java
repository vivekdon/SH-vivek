package com.viv.sh.script;

import java.io.File;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.viv.sh.service.DataEntryService;
import com.viv.sh.util.Constants;

public class InsertEndDayNifty {

	public static void main(String[] args) {
		
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/viv/sh/script/admin-script.xml");
		BeanFactory factory = (BeanFactory) context;
		DataEntryService dataEntryService = (DataEntryService) factory.getBean("dataEntryService");
		try{
			File folder=new File(Constants.niftBhavCopy+"un-processed");
			for ( File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		            System.out.println("Dont read");
		        } else {
		           String fileName= fileEntry.getName();
		           int ext=fileName.lastIndexOf(".");
		           System.out.println(fileName.substring(ext+1));
		           if(fileName.substring(ext+1).equalsIgnoreCase("csv")){
		        	   System.out.println(fileName);
		        	   dataEntryService.insertNiftyData(fileEntry,fileName, true);
		        	   
		        	   
		        	   
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
