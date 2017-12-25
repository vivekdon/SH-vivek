package com.viv.sh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.viv.sh.command.UploadForm;
import com.viv.sh.service.DataEntryService;

public class DataUploadController extends MultiActionController{
	private DataEntryService dataEntryService;
	
	
	
	public void setDataEntryService(DataEntryService dataEntryService) {
		this.dataEntryService = dataEntryService;
	}

	public ModelAndView init(HttpServletRequest req, HttpServletResponse res, UploadForm uploadForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("uploadData");
		model.addObject(uploadForm);
		model.addObject("nav", "upload");
		return model;
	}
	
	public ModelAndView upload(HttpServletRequest req, HttpServletResponse res, UploadForm uploadForm) throws ServletException, IOException {
		ModelAndView model=new ModelAndView("uploadData");
		UploadForm  up=uploadForm;
		System.out.println(uploadForm.getFile().getOriginalFilename());
		model.addObject("nav", "upload");
		
		
		dataEntryService.insertNiftyData(uploadForm.getFile(),uploadForm.getFile().getOriginalFilename(), false);
		System.out.println("vivek");
		model.addObject(uploadForm);
		return model;
	}
	

}
