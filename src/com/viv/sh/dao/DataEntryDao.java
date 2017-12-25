package com.viv.sh.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.StringDatatypeValidator;
import com.viv.sh.dto.PriceDto;
import com.viv.sh.util.CommonUtils;
import com.viv.sh.util.Constants;

public class DataEntryDao {
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void insertSampleData(String name) {
		try {
			String sql = "insert into sample (name) values(?)";
			Object[] args = null;
			args = new Object[] { name };
			jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Object[]> getQuoteAndTableName(String tableName) {
		try {
			String mainQuery = "select table_name, numeric_code from master where table_name=?";
			List<Object[]> list = jdbcTemplate.query(mainQuery, new Object[] { tableName }, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					return new Object[] { rs.getString("table_name"), rs.getInt("numeric_code") };
				}
			});

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void insertStockData(PriceDto dto) {
		try {
			String dateInsertQuery = "insert into " + dto.getTableName()
					+ " (numeric_code, value, open_value) values (?,?,?)";
			jdbcTemplate.update(dateInsertQuery,
					new Object[] { dto.getNumericCode(), dto.getCurrentPrice(), dto.getOpenPrice() });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertHistoryData(File file) throws Exception {
		try {
			String fileName = file.getName();
			String entryDate = "20" + fileName.substring(6, 8) + "-" + fileName.substring(4, 6) + "-"
					+ fileName.substring(2, 4);
			System.out.println(entryDate);
			String query = "select count(*) from history_data where value_date='" + entryDate + "'";
			if (jdbcTemplate.queryForInt(query) > 0) {
				jdbcTemplate.update("delete from history_data where value_date='" + entryDate + "'");
			}

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				boolean skipLine = true;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (!skipLine) {
						String[] splitAr = line.split(",");
						String dateInsertQuery = "insert into history_data (numeric_code, name,`group`,type_val,open_val, high_val, low_val, close_val, no_trde, no_shrs, net_turnover, value_date)"
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
						jdbcTemplate.update(dateInsertQuery,
								new Object[] { splitAr[0], splitAr[1], splitAr[2], splitAr[3], splitAr[4], splitAr[5],
										splitAr[6], splitAr[7], splitAr[10], splitAr[11], splitAr[12], entryDate });
					}
					skipLine = false;
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void insertNIftEquityData(BufferedReader br,String fileName) throws Exception {
		try {
//			String fileName = file.getName();
			String entryDate = fileName.substring(7, 11) + "-" + Constants.monthNumberMap.get(fileName.substring(4, 7))
					+ "-" + fileName.substring(2, 4);
			System.out.println(entryDate);
			String query = "select count(*) from nifty_data where value_date='" + entryDate + "'";
			if (jdbcTemplate.queryForInt(query) > 0) {
				jdbcTemplate.update("delete from nifty_data where value_date='" + entryDate + "'");
			}

//			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				boolean skipLine = true;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (!skipLine) {
						System.out.println(line);
						String[] splitAr = line.split(",");
						String dateInsertQuery = "insert into nifty_data (symbol, `series`,open_val, high_val, low_val, close_val,last_val,prev_close, tot_trd_qty, tot_trd_val, value_date,tot_trd, isin)"
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
						jdbcTemplate.update(dateInsertQuery,
								new Object[] { splitAr[0], splitAr[1], splitAr[2], splitAr[3], splitAr[4], splitAr[5],
										splitAr[6], splitAr[7], splitAr[8], splitAr[9], entryDate, splitAr[11],
										splitAr[12] });
					}
					skipLine = false;
				}
//			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public void insertNIftySecurityData(BufferedReader br,String fileName) throws Exception {
		try {
//			String fileName = file.getName();
			String valueDate = "20"+fileName.substring(6, 8) + "-" + fileName.substring(4, 6) + "-"
					+ fileName.substring(2, 4);
			System.out.println(valueDate);
			String query = "select count(*) from nifty_equity where value_date='" + valueDate + "'";
			if (jdbcTemplate.queryForInt(query) > 0) {
				jdbcTemplate.update("delete from nifty_equity where value_date='" + valueDate + "'");
				jdbcTemplate.update("delete from nifty_index where value_date='" + valueDate + "'");
			}

//			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				boolean skipLine = true;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (!skipLine) {
						System.out.println(line);
						String[] splitAr = line.split(",");
						if(!splitAr[0].trim().equals("")){
							//enter in nifty index
							if(splitAr[1].trim().equals("") && splitAr[2].trim().equals("") && !splitAr[3].trim().equals("")){
								String niftyIndexQuery="INSERT INTO nifty_index (MKT,`SECURITY` ,PREV_CL_PR,OPEN_PRICE,HIGH_PRICE,LOW_PRICE,CLOSE_PRICE,NET_TRDVAL,NET_TRDQTY ,IND_SEC ,CORP_IND ,TRADES  ,HI_52_WK ,LO_52_WK ,`value_date` ) "+
										"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
										jdbcTemplate.update(niftyIndexQuery, new Object[]{splitAr[0],splitAr[3],splitAr[4],splitAr[5],splitAr[6],splitAr[7],splitAr[8],splitAr[9],splitAr[10],splitAr[11],splitAr[12],splitAr[13],splitAr[14],splitAr[15],valueDate});
							}else if (!splitAr[1].trim().equals("") && !splitAr[2].trim().equals("") ){
								//enter in nifty equity
								String niftyEquityQuery="INSERT INTO nifty_equity (MKT,SERIES,SYMBOL,`SECURITY` ,PREV_CL_PR,OPEN_PRICE,HIGH_PRICE,LOW_PRICE,CLOSE_PRICE,NET_TRDVAL,NET_TRDQTY ,IND_SEC ,CORP_IND ,TRADES  ,HI_52_WK ,LO_52_WK ,`value_date` ) "+
													"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
								jdbcTemplate.update(niftyEquityQuery, new Object[]{splitAr[0],splitAr[1],splitAr[2],splitAr[3],splitAr[4],splitAr[5],splitAr[6],splitAr[7],splitAr[8],splitAr[9],splitAr[10],splitAr[11],splitAr[12],splitAr[13],splitAr[14],splitAr[15],valueDate});
							}
							
						}
						
					}
					skipLine = false;
				}
//			}
		} catch (Exception e) {
			throw e;
		}
	}

	
	public void insertNIftOptionData(BufferedReader br,String fileName) throws Exception {
		try {
//			String fileName = file.getName();
			String entryDate = fileName.substring(6, 10) + "-" + fileName.substring(4, 6) + "-"
					+ fileName.substring(2, 4);
			System.out.println(entryDate);
			String query = "select count(*) from nifty_option where value_date='" + entryDate + "'";
			if (jdbcTemplate.queryForInt(query) > 0) {
				jdbcTemplate.update("delete from nifty_option where value_date='" + entryDate + "'");
			}

//			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				boolean skipLine = true;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if(line.contains("* - OPEN_INT")){
						skipLine = true;
					}
					if (!skipLine) {
						System.out.println(line);
						String[] splitAr = line.split(",");
						String dateInsertQuery = "insert into nifty_option (instrument, `symbol`,exp_date,str_price,opt_type, open_price, high_price, low_price,close_price,open_int, trd_qty, no_of_cont,no_of_trade,notion_val,pr_val, value_date)"
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						jdbcTemplate.update(dateInsertQuery, new Object[] { splitAr[0], splitAr[1],
								CommonUtils.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", splitAr[2]), splitAr[3],
								splitAr[4], splitAr[5], splitAr[6], splitAr[7], splitAr[8], splitAr[9], splitAr[10],
								splitAr[11], splitAr[12], splitAr[13], splitAr[14], entryDate });
					}
					skipLine = false;
				}
//			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void insertNIftFutureData(BufferedReader br,String fileName) throws Exception {
		try {
//			String fileName = file.getName();
			String entryDate = fileName.substring(6, 10) + "-" + fileName.substring(4, 6) + "-"
					+ fileName.substring(2, 4);
			System.out.println(entryDate);
			String query = "select count(*) from nifty_future where value_date='" + entryDate + "'";
			if (jdbcTemplate.queryForInt(query) > 0) {
				jdbcTemplate.update("delete from nifty_future where value_date='" + entryDate + "'");
			}

//			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				boolean skipLine = true;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if(line.contains("* - OPEN_INT")){
						skipLine = true;
					}
					if (!skipLine) {
						System.out.println(line);
						String[] splitAr = line.split(",");
						String dateInsertQuery = "insert into nifty_future (instrument, `symbol`,exp_date, open_price, high_price, low_price,close_price,open_int, trd_val, trd_qty, no_of_cont,no_of_trade, value_date)"
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
						jdbcTemplate.update(dateInsertQuery,
								new Object[] { splitAr[0], splitAr[1],
										CommonUtils.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", splitAr[2]),
										splitAr[3], splitAr[4], splitAr[5], splitAr[6], splitAr[7], splitAr[8],
										splitAr[9], splitAr[10], splitAr[11], entryDate });
					}
					skipLine = false;
				}
//			}
		} catch (Exception e) {
			throw e;
		}
	}

	// Delete all data from data_comp table before inserting
	public void delDataComp() {
		try {
			jdbcTemplate.execute(Constants.delDataCompQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Calculate the percentage change
	public void updateDataComp() {
		try {
			jdbcTemplate.update(Constants.updateDataCompQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Take data from all table and insert in single table name data_comp
	public void manufactureData(String tableName) {
		try {
			String dataInsertQuery = Constants.dataManQuery;
			dataInsertQuery = dataInsertQuery.replaceAll("tableName", tableName);
			System.out.println(dataInsertQuery);
			jdbcTemplate.update(dataInsertQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
