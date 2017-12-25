package com.viv.sh.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.viv.sh.dto.CandleGaphDto;
import com.viv.sh.dto.DataComp;
import com.viv.sh.dto.EquityData;
import com.viv.sh.dto.EquityDataDto;
import com.viv.sh.dto.EquityGraphDto;
import com.viv.sh.dto.GraphDto;
import com.viv.sh.dto.OptionDataDto;
import com.viv.sh.util.Constants;


public class DataRetrievalDao {
	
private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new  JdbcTemplate(dataSource);
	}
	public List<CandleGaphDto> getCandleChartData(String symbol){
		try{
			
			String query="select open_val, high_val, low_val, close_val,value_date from nifty_data where symbol =? order by value_date;";
			List<CandleGaphDto> list = jdbcTemplate.query(query,new Object[]{symbol}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					CandleGaphDto dto=new CandleGaphDto();
					System.out.println( rs.getDate("value_date").getTime()+","+rs.getDouble("close_val"));
					dto.setX(rs.getString("value_date"));
					dto.setY(new double[]{rs.getDouble("open_val"),rs.getDouble("high_val"),rs.getDouble("low_val"),rs.getDouble("close_val")});
					return dto;
				}
			});
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<GraphDto> getGraphData(String numericCode){
		try{
			
			String sql="select table_name from master where numeric_code=?";
			List<String> tableNameList= jdbcTemplate.query(sql, new Object[]{numericCode}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("table_name");
				}
			});
			String tableName=null;
			if(tableNameList!=null && tableNameList.size()>0){
				tableName=tableNameList.get(0);
			}
			if(tableName!=null){
				String query="select value, entrytime from "+tableName+" where numeric_code=? order by id";
				List<GraphDto> list = jdbcTemplate.query(query,new Object[]{numericCode}, new RowMapper() {
					public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
						GraphDto dto=new GraphDto();
						System.out.println(  rs.getTimestamp("entrytime")+","+ rs.getDate("entrytime").getTime()+","+rs.getDouble("value"));
						dto.setX(rs.getTimestamp("entrytime").getTime());
						dto.setY(rs.getDouble("value"));
						
						return dto;
					}
				});
				return list;
			}
			
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public List<EquityData> getDojiData(String sortType, String group,String date ){
		return null;
	}
	
	
	
	public List<EquityData> getPerChangeData(String sortType, String group,String date ){
		try{
			if(sortType==null){
				sortType="DESC";
			}
			String groupSql="";
			/*if(!group.equalsIgnoreCase("all")){
				groupSql=" and `group`="+group;
			}*/
			
			
			String mainQuery="select symbol, open_val, high_val, low_val, close_val, last_val, prev_close, tot_trd_qty, tot_trd_val, tot_trd,TRUNCATE((close_val-open_val)*100/open_val,2) perc_Change"+
					" from nifty_data h where open_val>5 and series='EQ' and tot_trd> 5000 and "+
					" value_date=?"+
					"  order by (close_val-open_val)/open_val "+sortType +" limit 50 ";
			List<EquityData> list = jdbcTemplate.query(mainQuery,new Object[]{date}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					EquityData dto=new EquityData();
					dto.setSymbol(rs.getString("symbol"));
					dto.setOpenValue(rs.getDouble("open_val"));
					dto.setHighValue(rs.getDouble("high_val"));
					dto.setLowValue(rs.getDouble("low_val"));
					dto.setCloseValue(rs.getDouble("close_val"));
					dto.setLastVal(rs.getDouble("last_val"));
					dto.setPrevClose(rs.getDouble("prev_close"));
					dto.setTotTradeQty(rs.getInt("tot_trd_qty"));
					dto.setTotTradeVal(rs.getDouble("tot_trd_val"));
					dto.setTotTrade(rs.getInt("tot_trd"));
					dto.setPercChange(rs.getDouble("perc_Change"));
					return dto;
				}
			});
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<EquityData> getMarubuzoData(String sortType, String group,String date ){
		try{
			if(sortType==null){
				sortType="DESC";
			}
			String groupSql="";
			/*if(!group.equalsIgnoreCase("all")){
				groupSql=" and `group`="+group;
			}*/
			
			
			String mainQuery="select symbol, open_val, high_val, low_val, close_val, last_val, prev_close, tot_trd_qty, tot_trd_val, tot_trd,TRUNCATE((close_val-open_val)*100/open_val,2) perc_Change"+
					" from nifty_data h where"+
					"(high_val-close_val)/close_val>-0.01 and (high_val-close_val)/close_val<0.01"+
					"and (low_val-open_val)/open_val>-0.01 and (low_val-open_val)/open_val<0.01"+
					"and (high_val-low_val)/low_val >0.03"+
					"and tot_trd>1000 and value_date=?"+groupSql+
					"  order by (close_val-open_val)/open_val "+sortType;
			List<EquityData> list = jdbcTemplate.query(mainQuery,new Object[]{date}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					EquityData dto=new EquityData();
					dto.setSymbol(rs.getString("symbol"));
					dto.setOpenValue(rs.getDouble("open_val"));
					dto.setHighValue(rs.getDouble("high_val"));
					dto.setLowValue(rs.getDouble("low_val"));
					dto.setCloseValue(rs.getDouble("close_val"));
					dto.setLastVal(rs.getDouble("last_val"));
					dto.setPrevClose(rs.getDouble("prev_close"));
					dto.setTotTradeQty(rs.getInt("tot_trd_qty"));
					dto.setTotTradeVal(rs.getDouble("tot_trd_val"));
					dto.setTotTrade(rs.getInt("tot_trd"));
					dto.setPercChange(rs.getDouble("perc_Change"));
					return dto;
				}
			});
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<DataComp> getTableData(String sortBy,String sortType){ 
		try{
			
			if(sortBy==null){
				sortBy="lp1";
			}
			
			if(sortType==null){
				sortType="ASC";
			}
			String mainQuery = "select * from data_comp order by "+sortBy+" "+sortType+" limit 100 ";
//			final DecimalFormat fd=new DecimalFormat("#.###");
			List<DataComp> list = jdbcTemplate.query(mainQuery,new Object[]{}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					DataComp dto=new DataComp();
					dto.setNumericCode(rs.getInt("numeric_code"));
					dto.setOpenValue(rs.getDouble("open_value"));
					dto.setL1(  rs.getDouble("l1"));
					dto.setL2(rs.getDouble("l2"));
					dto.setL3(rs.getDouble("l3"));
					dto.setL4(rs.getDouble("l4"));
					dto.setL5(rs.getDouble("l5"));
					
					dto.setLp1(Math.floor( rs.getDouble("lp1")*1000)/1000);
					dto.setLp2(Math.floor( rs.getDouble("lp2")*1000)/1000);
					dto.setLp3(Math.floor( rs.getDouble("lp3")*1000)/1000);
					dto.setLp4(Math.floor( rs.getDouble("lp4")*1000)/1000);
					
					dto.setTotalChange(Math.floor( rs.getDouble("total_change")*1000)/1000  );
					return dto;
				}
			});
			
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public List<EquityGraphDto> getEquityGraphData(String symbol){
		 
		try{
			
			
			String mainQuery = "select OPEN_PRICE, HIGH_PRICE,LOW_PRICE, CLOSE_PRICE, NET_TRDQTY, TRADES, value_date from nifty_equity where SYMBOL='"+symbol+"' order by value_date";

			List<EquityGraphDto> list = jdbcTemplate.query(mainQuery,new Object[]{}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					EquityGraphDto dto=new EquityGraphDto();
					dto.setOpenVal(rs.getDouble("OPEN_PRICE"));
					dto.setHighVal(rs.getDouble("HIGH_PRICE"));
					dto.setLowVal(rs.getDouble("LOW_PRICE"));
					dto.setCloseVal(rs.getDouble("CLOSE_PRICE"));
					dto.setVolume(rs.getInt("NET_TRDQTY"));
					dto.setTradeVal(rs.getDouble("TRADES"));
					dto.setValueDate(rs.getString("value_date"));
					return dto;
				}
			});
			
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<EquityGraphDto> getOptionGraphData(String symbol, String expDate,String strPrice,String optType){
try{
			
			
			String mainQuery = "select open_price, high_price, low_price,close_price, open_int*close_price as volume, no_of_trade, value_date "+
							" from nifty_option where symbol='"+symbol+"' and exp_date='"+expDate+"' and str_price="+strPrice+" and opt_type='"+optType+"' order by value_date";

			List<EquityGraphDto> list = jdbcTemplate.query(mainQuery,new Object[]{}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					EquityGraphDto dto=new EquityGraphDto();
					dto.setOpenVal(rs.getDouble("open_price"));
					dto.setHighVal(rs.getDouble("high_price"));
					dto.setLowVal(rs.getDouble("low_price"));
					dto.setCloseVal(rs.getDouble("close_price"));
					dto.setVolume(rs.getInt("no_of_trade"));
					dto.setTradeVal(rs.getDouble("volume"));
					dto.setValueDate(rs.getString("value_date"));
					return dto;
				}
			});
			
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	public ArrayList getIndexExpiry(String symbol){
		
		
		return null;
	}
	public List<OptionDataDto> getOptionTableData(String symbol, String index,String  expDate,String  type){
		
		try{
			String querySymbol="";
			if(symbol!=null && !symbol.trim().equalsIgnoreCase("")){
				querySymbol=" and t1.symbol='"+symbol+"'";
			}
			String queryIndex="";
			if(index!=null && !index.trim().equalsIgnoreCase("")){
				queryIndex=" and t1.str_price='"+index+"'";
			}
			String queryExpDate="";
			if(expDate!=null && !expDate.trim().equalsIgnoreCase("")){
				queryExpDate=" and t1.exp_date='"+expDate+"'";
			}
			String queryType="";
			if(type!=null && !type.trim().equalsIgnoreCase("")&& !type.trim().equalsIgnoreCase("Both")){
				queryType="and t1.opt_type='"+type+"'";
			}
			
			String dateQuery="select distinct value_date from nifty_option t1 where 1=1 "+querySymbol+queryIndex+queryType+queryExpDate +" order by value_date desc limit 6;";
			List<String> list = jdbcTemplate.query(dateQuery,new Object[]{}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					
					return rs.getString("value_date");
				}
			});
			if(list.size()==6){
				String mainSql="select '"+list.get(0)+"' as d1,'"+list.get(1)+"' as d2,'"+list.get(2)+"' as d3,'"+list.get(3)+"' as d4,'"+list.get(4)+"' as d5, t1.symbol symbol,t1.exp_date exp_date,t1.opt_type, t1.str_price str_price, t1.close_price cp1, t1.volume v1, t1.trd_qty tq1, t1.value_date vd1, t2.close_price cp2, t2.volume v2, t2.trd_qty tq2, t2.value_date vd2, t3.close_price cp3, t3.volume v3, t3.trd_qty tq3, t3.value_date vd3, t4.close_price cp4, t4.volume v4, t4.trd_qty tq4, t4.value_date vd4, t5.close_price cp5, t5.volume v5, t5.trd_qty tq5, t5.value_date vd5, t6.close_price cp6, t6.volume v6, t6.trd_qty tq6, t6.value_date vd6 from "+
					"(select symbol, exp_date,str_price, opt_type, close_price, TRUNCATE((close_price*open_int)/"+Constants.volumeDivide+",2) as volume, trd_qty, value_date from nifty_option where value_date='"+list.get(0)+"')  t1 "+
					"left outer join (select symbol, exp_date,str_price,opt_type, close_price, TRUNCATE((close_price*open_int)/"+Constants.volumeDivide+",2) as volume, trd_qty, value_date from nifty_option where value_date='"+list.get(1)+"') t2 "+
					"on (t1.symbol=t2.symbol and t1.exp_date=t2.exp_date and t1.str_price=t2.str_price and t1.opt_type=t2.opt_type) "+
					"left outer join (select symbol, exp_date,str_price,opt_type, close_price, TRUNCATE((close_price*open_int)/"+Constants.volumeDivide+",2) as volume, trd_qty, value_date from nifty_option where value_date='"+list.get(2)+"') t3 "+
					"on (t1.symbol=t3.symbol and t1.exp_date=t3.exp_date and t1.str_price=t3.str_price and t1.opt_type=t3.opt_type) "+
					"left outer join (select symbol, exp_date,str_price,opt_type, close_price, TRUNCATE((close_price*open_int)/"+Constants.volumeDivide+",2) as volume, trd_qty, value_date from nifty_option where value_date='"+list.get(3)+"') t4 "+
					"on (t1.symbol=t4.symbol and t1.exp_date=t4.exp_date and t1.str_price=t4.str_price and t1.opt_type=t4.opt_type) "+
					"left outer join (select symbol, exp_date,str_price,opt_type, close_price, TRUNCATE((close_price*open_int)/"+Constants.volumeDivide+",2) as volume, trd_qty, value_date from nifty_option where value_date='"+list.get(4)+"') t5 "+
					"on (t1.symbol=t5.symbol and t1.exp_date=t5.exp_date and t1.str_price=t5.str_price and t1.opt_type=t5.opt_type) "+
					"left outer join (select symbol, exp_date,str_price,opt_type, close_price, TRUNCATE((close_price*open_int)/"+Constants.volumeDivide+",2) as volume, trd_qty, value_date from nifty_option where value_date='"+list.get(5)+"') t6 "+
					"on (t1.symbol=t6.symbol and t1.exp_date=t6.exp_date and t1.str_price=t6.str_price and t1.opt_type=t6.opt_type) "+
					" where 1=1 "+querySymbol+queryIndex+queryType+queryExpDate +" limit 100";
				List<OptionDataDto> retList = jdbcTemplate.query(mainSql,new Object[]{}, new RowMapper() {
					public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
						OptionDataDto dto=new OptionDataDto();
						dto.setSymbol(rs.getString("symbol"));
						dto.setExpDate(rs.getString("exp_date"));
						dto.setStrPrice(rs.getDouble("str_price"));
						dto.setOptType(rs.getString("opt_type"));
						
						dto.setCp1(rs.getDouble("cp1"));
						dto.setV1(rs.getDouble("v1"));
						dto.setTq1(rs.getDouble("tq1"));
						dto.setVd1(rs.getString("d1"));
						
						dto.setCp2(rs.getDouble("cp2"));
						dto.setV2(rs.getDouble("v2"));
						dto.setTq2(rs.getDouble("tq2"));
						dto.setVd2(rs.getString("d2"));
						
						dto.setCp3(rs.getDouble("cp3"));
						dto.setV3(rs.getDouble("v3"));
						dto.setTq3(rs.getDouble("tq3"));
						dto.setVd3(rs.getString("d3"));
						
						dto.setCp4(rs.getDouble("cp4"));
						dto.setV4(rs.getDouble("v4"));
						dto.setTq4(rs.getDouble("tq4"));
						dto.setVd4(rs.getString("d4"));
						
						dto.setCp5(rs.getDouble("cp5"));
						dto.setV5(rs.getDouble("v5"));
						dto.setTq5(rs.getDouble("tq5"));
						dto.setVd5(rs.getString("d5"));
						
						dto.setCp6(rs.getDouble("cp6"));
						dto.setV6(rs.getDouble("v6"));
						dto.setTq6(rs.getDouble("tq6"));
						dto.setVd6(rs.getString("vd6"));
						
						return dto;
					}
				});	
				return retList;
			}
			
			return null;
			
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public List<String>getSectorName(){
		String mainQuery="Select distinct sector from symbol_sector  order by sector";
		try{
			List<String> list = jdbcTemplate.query(mainQuery,new Object[]{}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					
					return rs.getString("sector");
				}
			});
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<EquityDataDto> getEquityTableData(String sector,String symbol, String high52, String low52, String limitStr){ 
		try{
			int limit =50;
			if(limitStr==null && !limitStr.trim().equalsIgnoreCase("")){
				try{
					limit =Integer.parseInt(limitStr);
				}catch (Exception e) {

				}
			}
			
			
			String symbolStr="";
			if(symbol!=null && !symbol.trim().equalsIgnoreCase("")){
				symbolStr=" and symbol='"+symbol+"'";
			}
			String sectorTable="";
			String sectorWhere="";
			if(sector!=null && !sector.equals("All")){
				sectorTable=" , symbol_sector ";
				sectorWhere =" and a1.SYMBOL=symbol_sector.SYMBOL and symbol_sector.sector='"+sector+"' ";
			}
			String high52Query="";
			if(high52!=null && !high52.trim().equalsIgnoreCase("")){
				high52Query="and (HI_52_WK-CLOSE_PRICE)*100/CLOSE_PRICE<"+high52;
			}
			String low52Query="";
			if(low52!=null && !low52.trim().equalsIgnoreCase("")){
				low52Query="and (CLOSE_PRICE-LO_52_WK)*100/CLOSE_PRICE<"+low52;
			}
			String mainQuery = "select a1.SYMBOL,"+
								"a1.value_date vd1,a1.CLOSE_PRICE cv1, a1.NET_TRDQTY ttq1, a1.TRADES tt1, TRUNCATE((a1.CLOSE_PRICE-a2.CLOSE_PRICE)*100/a2.CLOSE_PRICE,2) close_perc1, TRUNCATE((a1.NET_TRDQTY-a2.NET_TRDQTY)*100/a2.NET_TRDQTY,2) tot_trd_qty_perc1, TRUNCATE((a1.TRADES-a2.TRADES)*100/a2.TRADES,2) tot_trd_perc1, "+
								"a2.value_date vd2,a2.CLOSE_PRICE cv2, a2.NET_TRDQTY ttq2, a2.TRADES tt2, TRUNCATE((a2.CLOSE_PRICE-a3.CLOSE_PRICE)*100/a3.CLOSE_PRICE,2) close_perc2, TRUNCATE((a2.NET_TRDQTY-a3.NET_TRDQTY)*100/a3.NET_TRDQTY,2) tot_trd_qty_perc2, TRUNCATE((a2.TRADES-a3.TRADES)*100/a3.TRADES,2) tot_trd_perc2, "+
								"a3.value_date vd3,a3.CLOSE_PRICE cv3, a3.NET_TRDQTY ttq3, a3.TRADES tt3, TRUNCATE((a3.CLOSE_PRICE-a4.CLOSE_PRICE)*100/a4.CLOSE_PRICE,2) close_perc3, TRUNCATE((a3.NET_TRDQTY-a4.NET_TRDQTY)*100/a4.NET_TRDQTY,2) tot_trd_qty_perc3, TRUNCATE((a3.TRADES-a4.TRADES)*100/a4.TRADES,2) tot_trd_perc3, "+
								"a4.value_date vd4,a4.CLOSE_PRICE cv4, a4.NET_TRDQTY ttq4, a4.TRADES tt4, TRUNCATE((a4.CLOSE_PRICE-a5.CLOSE_PRICE)*100/a5.CLOSE_PRICE,2) close_perc4, TRUNCATE((a4.NET_TRDQTY-a5.NET_TRDQTY)*100/a5.NET_TRDQTY,2) tot_trd_qty_perc4, TRUNCATE((a4.TRADES-a5.TRADES)*100/a5.TRADES,2) tot_trd_perc4, "+
								"a5.value_date vd5,a5.CLOSE_PRICE cv5, a5.NET_TRDQTY ttq5, a5.TRADES tt5, TRUNCATE((a5.CLOSE_PRICE-a6.CLOSE_PRICE)*100/a6.CLOSE_PRICE,2) close_perc5, TRUNCATE((a5.NET_TRDQTY-a6.NET_TRDQTY)*100/a6.NET_TRDQTY,2) tot_trd_qty_perc5, TRUNCATE((a5.TRADES-a6.TRADES)*100/a6.TRADES,2) tot_trd_perc5 "+
								 "from "+
								"(select SYMBOL, CLOSE_PRICE, NET_TRDQTY,TRADES, value_date  from nifty_equity  where CLOSE_PRICE>10 and SERIES='EQ' "+symbolStr+high52Query+" and value_date=(select distinct value_date from nifty_equity where 1=1  "+symbolStr+" order by value_date  desc limit 0,1)) a1, "+
								"(select SYMBOL, CLOSE_PRICE, NET_TRDQTY,TRADES, value_date  from nifty_equity  where SERIES='EQ' "+symbolStr+" and value_date=(select distinct value_date from nifty_equity where 1=1 "+symbolStr+" order by value_date desc limit 1,1)) a2, "+
								"(select SYMBOL, CLOSE_PRICE, NET_TRDQTY,TRADES, value_date  from nifty_equity where SERIES='EQ' "+symbolStr+" and value_date=(select distinct value_date from nifty_equity where 1=1 "+symbolStr+" order by value_date desc limit 2,1)) a3, "+
								"(select SYMBOL, CLOSE_PRICE, NET_TRDQTY,TRADES, value_date  from nifty_equity where SERIES='EQ' "+symbolStr+" and value_date=(select distinct value_date from nifty_equity where 1=1 "+symbolStr+" order by value_date desc limit 3,1)) a4, "+
								"(select SYMBOL, CLOSE_PRICE, NET_TRDQTY,TRADES, value_date  from nifty_equity where  SERIES='EQ' "+symbolStr+" and value_date=(select distinct value_date from nifty_equity where 1=1 "+symbolStr+" order by value_date desc limit 4,1)) a5, "+
								"(select SYMBOL, CLOSE_PRICE, NET_TRDQTY,TRADES, value_date  from nifty_equity  where SERIES='EQ' "+symbolStr+" and value_date=(select distinct value_date from nifty_equity where 1=1 "+symbolStr+" order by value_date desc limit 5,1)) a6 "+
								sectorTable+
								"where a1.SYMBOL=a2.SYMBOL and a2.SYMBOL=a3.SYMBOL and a3.SYMBOL=a4.SYMBOL and a4.SYMBOL=a5.SYMBOL and a5.SYMBOL=a6.SYMBOL "+
								sectorWhere+
								 " order by (a1.CLOSE_PRICE-a2.CLOSE_PRICE)/a2.CLOSE_PRICE desc limit "+limit +";";
			System.out.println(mainQuery);
			 
//			final DecimalFormat fd=new DecimalFormat("#.###");
			List<EquityDataDto> list = jdbcTemplate.query(mainQuery,new Object[]{}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					EquityDataDto dto=new EquityDataDto();
					dto.setSymbol(rs.getString("SYMBOL"));
					
					dto.setVd1(rs.getString("vd1"));
					dto.setCv1(rs.getDouble("cv1"));
					dto.setTtq1(rs.getDouble("ttq1"));
					dto.setTt1(rs.getDouble("tt1"));
					dto.setClosePerc1(rs.getDouble("close_perc1"));
					dto.setTotTrdQtyPerc1(rs.getDouble("tot_trd_qty_perc1"));
					dto.setTotTrdPerc1(rs.getDouble("tot_trd_perc1"));
					
					dto.setVd2(rs.getString("vd2"));
					dto.setCv2(rs.getDouble("cv2"));
					dto.setTtq2(rs.getDouble("ttq2"));
					dto.setTt2(rs.getDouble("tt2"));
					dto.setClosePerc2(rs.getDouble("close_perc2"));
					dto.setTotTrdQtyPerc2(rs.getDouble("tot_trd_qty_perc2"));
					dto.setTotTrdPerc2(rs.getDouble("tot_trd_perc2"));
					
					dto.setVd3(rs.getString("vd3"));
					dto.setCv3(rs.getDouble("cv3"));
					dto.setTtq3(rs.getDouble("ttq3"));
					dto.setTt3(rs.getDouble("tt3"));
					dto.setClosePerc3(rs.getDouble("close_perc3"));
					dto.setTotTrdQtyPerc3(rs.getDouble("tot_trd_qty_perc3"));
					dto.setTotTrdPerc3(rs.getDouble("tot_trd_perc3"));
					
					dto.setVd4(rs.getString("vd4"));
					dto.setCv4(rs.getDouble("cv4"));
					dto.setTtq4(rs.getDouble("ttq4"));
					dto.setTt4(rs.getDouble("tt4"));
					dto.setClosePerc4(rs.getDouble("close_perc4"));
					dto.setTotTrdQtyPerc4(rs.getDouble("tot_trd_qty_perc4"));
					dto.setTotTrdPerc4(rs.getDouble("tot_trd_perc4"));
					
					dto.setVd5(rs.getString("vd5"));
					dto.setCv5(rs.getDouble("cv5"));
					dto.setTtq5(rs.getDouble("ttq5"));
					dto.setTt5(rs.getDouble("tt5"));
					dto.setClosePerc5(rs.getDouble("close_perc5"));
					dto.setTotTrdQtyPerc5(rs.getDouble("tot_trd_qty_perc5"));
					dto.setTotTrdPerc5(rs.getDouble("tot_trd_perc5"));
					
					return dto;
				}
			});
			
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
}
