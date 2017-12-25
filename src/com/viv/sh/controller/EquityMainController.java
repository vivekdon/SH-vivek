package com.viv.sh.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.viv.sh.command.EquityForm;
import com.viv.sh.dto.EquityDataDto;
import com.viv.sh.dto.EquityGraphDto;
import com.viv.sh.service.DataRetrivalService;

import net.sf.json.JSONSerializer;

public class EquityMainController extends MultiActionController{
	
	private DataRetrivalService dataRetrivalService;
	

	public void setDataRetrivalService(DataRetrivalService dataRetrivalService) {
		this.dataRetrivalService = dataRetrivalService;
	}

	public ModelAndView init(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("initEquity");
		return model;
	}
	public ModelAndView example(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("example");
		return model;
	}
	public ModelAndView show(HttpServletRequest req, HttpServletResponse res, EquityForm equityForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("showEquity");
		model.addObject("nav", "equity");
		
		model.addObject("sectorList",dataRetrivalService.getSectorName());
		model.addObject(equityForm);
		return model;
	}
	
	
	
	
	public ModelAndView showTable(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		System.out.println(req.getParameter("patternType"));
		String sector=req.getParameter("sector");
		String symbol=req.getParameter("symbol");
		String high52=req.getParameter("high52");
		String low52=req.getParameter("low52");
		String limit=req.getParameter("limit");
		
		List<EquityDataDto> tableList=dataRetrivalService.getEquityTableData(sector,symbol,high52,low52,limit);
//		System.out.println( JSONSerializer.toJSON(tableList));
		res.getWriter().println(JSONSerializer.toJSON(tableList));
		return null;
	}
	
	public ModelAndView getGraphData(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		String symbol=req.getParameter("symbol");
		
		List<EquityGraphDto> tableList=dataRetrivalService.getEquityGraphData(symbol);
		System.out.println(tableList.size());
//		System.out.println( JSONSerializer.toJSON(tableList));
		res.getWriter().println(JSONSerializer.toJSON(tableList));
		return null;
	}
	
	
	
	
}
