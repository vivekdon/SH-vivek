package com.viv.sh.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.viv.sh.command.BuyForm;
import com.viv.sh.dto.BoughtDto;
import com.viv.sh.dto.DataComp;
import com.viv.sh.service.BuyService;

import net.sf.json.JSONSerializer;

public class BuyController extends MultiActionController {
	
	private BuyService buyService;

	public void setBuyService(BuyService buyService) {
		this.buyService = buyService;
	}
	
	public ModelAndView init(HttpServletRequest req, HttpServletResponse res, BuyForm buyForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("buyHome");
		model.addObject(buyForm);
		return model;
	}
	public ModelAndView initSale(HttpServletRequest req, HttpServletResponse res, BuyForm buyForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("saleHome");
		String numericCode=req.getParameter("numericCode");
		if(numericCode!=null){
			buyForm.setNumericCode(Integer.parseInt(numericCode));
		}
		model.addObject(buyForm);
		return model;
	}
	
	
	
	public ModelAndView submitSale(HttpServletRequest req, HttpServletResponse res, BuyForm buyForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("saleHome");
		if(buyService.isValidCode(buyForm.getNumericCode())){
			try{
				String retMess=buyService.saveSale(buyForm);
				
				buyForm.setError(retMess);
			}catch(Exception e){
				buyForm.setError("Error in saving");
				e.printStackTrace();
			}
			
		}else {
			buyForm.setError("Numeric code is not correct");
		}
		
		model.addObject(buyForm);
		return model;
	}
	public ModelAndView submit(HttpServletRequest req, HttpServletResponse res, BuyForm buyForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("buyHome");
		if(buyService.isValidCode(buyForm.getNumericCode())){
			try{
				buyService.saveBuy(buyForm);
				
				buyForm.setError("Success in saving");
			}catch(Exception e){
				buyForm.setError("Error in saving");
				e.printStackTrace();
			}
			
		}else {
			buyForm.setError("Numeric code is not correct");
		}
		
		model.addObject(buyForm);
		return model;
	}
	
	public ModelAndView showBuyTable(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		try{
			List<BoughtDto> tableList=buyService.getBoughtDataList();
			
			System.out.println("Buy json"+ JSONSerializer.toJSON(tableList));
			res.getWriter().println(JSONSerializer.toJSON(tableList));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
