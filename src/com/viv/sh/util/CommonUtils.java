package com.viv.sh.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	
	public static String changeDateFormat(String inFormat,String outFormat, String date){
		String retVal=null;
		try{
			DateFormat originalFormat = new SimpleDateFormat(inFormat);
			DateFormat targetFormat = new SimpleDateFormat(outFormat);
			Date dateObj = originalFormat.parse(date);
			retVal = targetFormat.format(dateObj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

}
