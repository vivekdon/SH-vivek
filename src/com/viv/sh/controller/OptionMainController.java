package com.viv.sh.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.viv.sh.command.EquityForm;
import com.viv.sh.dto.EquityDataDto;
import com.viv.sh.dto.EquityGraphDto;
import com.viv.sh.dto.OptionDataDto;
import com.viv.sh.service.DataRetrivalService;

import net.sf.json.JSONSerializer;

public class OptionMainController extends MultiActionController{
	
	private DataRetrivalService dataRetrivalService;
	

	public void setDataRetrivalService(DataRetrivalService dataRetrivalService) {
		this.dataRetrivalService = dataRetrivalService;
	}

	public ModelAndView init(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("initEquity");
		return model;
	}
	
	public ModelAndView show(HttpServletRequest req, HttpServletResponse res, EquityForm equityForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("showOption");
		model.addObject("nav", "option");
		model.addObject(equityForm);
		return model;
	}
	
	public ModelAndView showD3(HttpServletRequest req, HttpServletResponse res, EquityForm equityForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("showOptionD3");
		model.addObject("nav", "option");
		model.addObject(equityForm);
		return model;
	}
	
	
	
	public ModelAndView showTable(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		
		
		String symbol=req.getParameter("symbol");
		String index=req.getParameter("index");
		String expDate=req.getParameter("expDate");
		String type=req.getParameter("type");
		
		List<OptionDataDto> tableList=dataRetrivalService.getOptionTableData(symbol,index, expDate, type);
//		System.out.println( JSONSerializer.toJSON(tableList));
		res.getWriter().println(JSONSerializer.toJSON(tableList));
		return null;
	}
	
	public ModelAndView updateIndexExpiry(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		
		
		String symbol=req.getParameter("symbol");
		ArrayList retVal=  dataRetrivalService.getIndexExpiry(symbol);
//		List<OptionDataDto> tableList=dataRetrivalService.getOptionTableData(symbol,index, expDate, type);
//		System.out.println( JSONSerializer.toJSON(tableList));
		res.getWriter().println(JSONSerializer.toJSON(retVal));
		return null;
	}
	
	public ModelAndView getGraphData(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		ManageAdminUsers command = new ManageAdminUsers();
		String symbol=req.getParameter("symbol");
		String expDate=req.getParameter("expDate");
		String strPrice=req.getParameter("strPrice");
		String optType=req.getParameter("optType");
		
		
		List<EquityGraphDto> tableList=dataRetrivalService.getOptionGraphData(symbol, expDate,strPrice,optType);
		System.out.println(tableList.size());
//		System.out.println( JSONSerializer.toJSON(tableList));
		res.getWriter().println(JSONSerializer.toJSON(tableList));
		return null;
	}
	
	
	
	
}
