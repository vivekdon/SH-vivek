package com.viv.sh.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.viv.sh.dto.CandleGaphDto;
import com.viv.sh.dto.DataComp;
import com.viv.sh.dto.EquityData;
import com.viv.sh.dto.GraphDto;
import com.viv.sh.dto.PriceDto;
import com.viv.sh.service.DataRetrivalService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

//import net.sf.js

public class TableCreator extends MultiActionController{
	
	private DataRetrivalService dataRetrivalService;
	
	
	
	public void setDataRetrivalService(DataRetrivalService dataRetrivalService) {
		this.dataRetrivalService = dataRetrivalService;
	}



	public ModelAndView viewlistlp1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		System.out.println(req.getParameter("sortBy"));
		String sortType=req.getParameter("sortType");
		String sortBy=req.getParameter("sortBy");
		
		List<DataComp> tableList=dataRetrivalService.getTableData(sortBy,sortType);
//		System.out.println( JSONSerializer.toJSON(tableList));
		res.getWriter().println(JSONSerializer.toJSON(tableList));
		return null;
	}
	
	public ModelAndView showSingleData(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		System.out.println(req.getParameter("patternType"));
		String sortType=req.getParameter("sortType");
		String patternType=req.getParameter("patternType");
		String group=req.getParameter("group");
		String date=req.getParameter("date");
		
		List<EquityData> tableList=dataRetrivalService.getSingleCandleData(patternType,sortType,group,date);
//		System.out.println( JSONSerializer.toJSON(tableList));
		res.getWriter().println(JSONSerializer.toJSON(tableList));
		return null;
	}
	
	
	public ModelAndView getGraphData(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		
		String numericCode=req.getParameter("numericCode");
		
		
		List<GraphDto> graphList=dataRetrivalService.getGraphData(numericCode);
//		System.out.println( JSONSerializer.toJSON(tableList));
		System.out.println(JSONSerializer.toJSON(graphList));
		res.getWriter().println(JSONSerializer.toJSON(graphList));
		return null;
	}
	
	public ModelAndView getCandleChartData(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		
		String symbol=req.getParameter("symbol");
		
		
		List<CandleGaphDto> graphList=dataRetrivalService.getCandleChartData(symbol);
//		System.out.println( JSONSerializer.toJSON(tableList));
		System.out.println("i am vivvek");
		System.out.println(JSONSerializer.toJSON(graphList));
		res.getWriter().println(JSONSerializer.toJSON(graphList));
		return null;
	}

}
