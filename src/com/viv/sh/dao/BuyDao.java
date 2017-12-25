package com.viv.sh.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.viv.sh.command.BuyForm;
import com.viv.sh.dto.BoughtDto;
import com.viv.sh.dto.DataComp;

import net.sf.json.processors.JsDateJsonBeanProcessor;

public class BuyDao {
private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new  JdbcTemplate(dataSource);
	}
	
	public List<Double> getCurrentPriceList(int numericCode){
		try{
			String tableName=getTableNameByNuericCode(numericCode);
			String sql="select value from "+tableName+" where numeric_code=? order by id desc limit 5;";
			List<Double> retList=jdbcTemplate.query(sql, new Object[]{numericCode}, new RowMapper(){
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					return rs.getDouble("value");
				} 
			});
			return retList; 
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getTableNameByNuericCode(int numericCode){
		try{
			String sql="select table_name from master where numeric_code =?";
			String tableName=jdbcTemplate.queryForObject(sql,new Object[]{numericCode}, new RowMapper(){
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("table_name");
				} 
			}).toString();
			return tableName;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<BoughtDto> getBoughtList(){
		
		try{
			String sql="select b.numeric_code, m.stock, b.price, b.quantity, b.total_amount from buy b, master  m where b.numeric_code=m.numeric_code ;";
			List<BoughtDto> list = jdbcTemplate.query(sql,new Object[]{}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					BoughtDto dto=new BoughtDto();
					dto.setNumericCode(rs.getInt("numeric_code"));
					dto.setPrice(rs.getDouble("price"));
					dto.setQuantity(rs.getDouble("quantity"));
					dto.setTotalAmount(rs.getDouble("total_amount"));
					dto.setStock(rs.getString("stock"));
					return dto;
				}
				
			});
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
	}
	public boolean isValidCode(int numericCode){
		boolean retVal=false;
		try{
			String sql="select * from master where numeric_code=?";
			List list= jdbcTemplate.queryForList(sql, new Object[]{numericCode});
			if(list.size()>0){
				retVal=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retVal;
		
	}
	
	public void saveBuy(BuyForm buyForm) throws Exception{
		try{
			BoughtDto databaseDto=getBuyByNumericCode(buyForm.getNumericCode());
			if(databaseDto!=null){
				double newQuantity=databaseDto.getQuantity()+buyForm.getQuantity();
				double newAmount=databaseDto.getQuantity()*databaseDto.getPrice()+buyForm.getQuantity()*buyForm.getBuyVal();
				double newprice=newAmount/newQuantity;
				String sql="update buy set price=?, quantity=?,total_amount=? where numeric_code=?";
				jdbcTemplate.update(sql, new Object[]{newprice, newQuantity, newAmount, databaseDto.getNumericCode() });
				
			}else{
				String sql="insert into buy(numeric_code,price, quantity,total_amount) values(?,?,?,?);";
				jdbcTemplate.update(sql, new Object[]{buyForm.getNumericCode(), buyForm.getBuyVal(), buyForm.getQuantity(),buyForm.getBuyVal()* buyForm.getQuantity() });
				
			}
			
			String sql1="insert into trade(numeric_code,price, quantity,total_amount, type) values(?,?,?,?, 'B');";
			jdbcTemplate.update(sql1, new Object[]{buyForm.getNumericCode(), buyForm.getBuyVal(), buyForm.getQuantity(),buyForm.getBuyVal()* buyForm.getQuantity() });
			
		}catch(Exception e){
//			e.printStackTrace();
			throw e;
		}
	}
	public BoughtDto getBuyByNumericCode(int numericCode){
		try{
			String sql="Select * from buy where numeric_code=?";
			List<BoughtDto> list = jdbcTemplate.query(sql,new Object[]{numericCode}, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
					BoughtDto dto=new BoughtDto();
					dto.setNumericCode(rs.getInt("numeric_code"));
					dto.setPrice(rs.getDouble("price"));
					dto.setQuantity(rs.getDouble("quantity"));
					dto.setTotalAmount(rs.getDouble("total_amount"));
					return dto;
				}
				
			});
			if(list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public String saveSale(BuyForm buyForm) throws Exception{
		try{
			BoughtDto databaseDto=getBuyByNumericCode(buyForm.getNumericCode());
			if(databaseDto!=null){
				if(databaseDto.getQuantity()>buyForm.getQuantity()){
					//Update buy table by subtracting & update trade table
					String sql ="update buy set quantity=?, total_amount=quantity*price  where numeric_code =?";
					jdbcTemplate.update(sql,new Object[]{databaseDto.getQuantity()-buyForm.getQuantity() ,buyForm.getNumericCode()});
					
					String tradeSql="insert into trade (numeric_code, price, quantity, total_amount, type) values(?,?,?,?,'S')";
					jdbcTemplate.update(tradeSql, new Object[]{buyForm.getNumericCode(), buyForm.getBuyVal(), buyForm.getQuantity(), buyForm.getBuyVal()* buyForm.getQuantity()});
					
					return "Updated Successfully";
					
					
				}else if(databaseDto.getQuantity()==buyForm.getQuantity()){
					//delete from buy table and insert into trade table
					String sql ="delete from buy where numeric_code =?";
					jdbcTemplate.update(sql,new Object[]{ buyForm.getNumericCode()});
					
					String tradeSql="insert into trade (numeric_code, price, quantity, total_amount, type) values(?,?,?,?,'S')";
					jdbcTemplate.update(tradeSql, new Object[]{buyForm.getNumericCode(), buyForm.getBuyVal(), buyForm.getQuantity(), buyForm.getBuyVal()* buyForm.getQuantity()});
					
					return "Updated Successfully";
				}else{
					return "Quantity mismatch, please check again";
				}
			}else{
				return "There is no such share in buy list";
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
//		return null;
	}
}
