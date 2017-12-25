package com.viv.sh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class SingleController extends MultiActionController {
	
	public ModelAndView init(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		return new ModelAndView("singleCandle");
	}
	
	

}
