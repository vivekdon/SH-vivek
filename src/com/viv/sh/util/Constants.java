package com.viv.sh.util;

import java.util.HashMap;

public class Constants {
	//response contain previous close, open, high , low, curretn
	public static final String curDataURL = "http://www.bseindia.com/stock-share-price/SiteCache/EQHeaderData.aspx?text=";
	public static final int aGroupTimeDiff = 60 * 1000;// number of second to
														// repead the thread

	public static final String dataManQuery = "insert into data_comp ( numeric_code, open_value, l1, l2, l3, l4, l5) select numeric_code,"
			+ "(select open_value from tableName o1 where o1.numeric_code =m.numeric_code order by id desc limit 1) ,"
			+ "(select value from tableName t1 where t1.numeric_code =m.numeric_code order by id desc limit 4, 1), "
			+ "(select value from tableName t2 where t2.numeric_code=m.numeric_code order by id desc limit 3,1) ,"
			+ "(select value from tableName t3 where t3.numeric_code=m.numeric_code order by id desc limit 2,1) ,"
			+ "(select value from tableName t4 where t4.numeric_code=m.numeric_code order by id desc limit 1,1) ,"
			+ "(select value from tableName t5 where t5.numeric_code=m.numeric_code order by id desc limit 1) "
			+ "from tableName m group by numeric_code;";
	
	public static final String delDataCompQuery="delete from data_comp";
	public static final String updateDataCompQuery="update data_comp set lp1=(l2-l1)*100/l1, lp2=(l3-l2)*100/l2, lp3=(l4-l3)*100/l3, lp4=(l5-l4)*100/l4, total_change=(l5-open_value)*100/open_value;";
	
	public static final String WeekHighLowURL="http://www.bseindia.com/stock-share-price/SiteCache/52WeekHigh.aspx?Type=EQ&text=500048";
	
	public static final String getQuote="http://www.bseindia.com/getquote.htm";
	
	public static final String bhavCopyPath="F:\\Share-vivek\\Bhav-copy\\";
	
	public static final String niftBhavCopy="F:\\Share-vivek\\Nift-Bhav-copy\\";;
	
	
	public static final String bulkCurrentData="http://www.bseindia.com/markets/equity/EQReports/MarketWatch.aspx?expandable=2";
	
	public static final HashMap<String, String> monthNumberMap=createMonthMap();
	
	private static HashMap<String, String> createMonthMap(){
		HashMap<String, String> monthMap=new HashMap<>();
		monthMap.put("JAN", "01");
		monthMap.put("FEB", "02");
		monthMap.put("MAR", "03");
		monthMap.put("APR", "04");
		monthMap.put("MAY", "05");
		monthMap.put("JUN", "06");
		monthMap.put("JUL", "07");
		monthMap.put("AUG", "08");
		monthMap.put("SEP", "09");
		monthMap.put("OCT", "10");
		monthMap.put("NOV", "11");
		monthMap.put("DEC", "12");
		return monthMap;
	}
	
	public static final String volumeDivide="1000000";
}
