package com.zealot.mail.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
	
	public void writeJson(HttpServletResponse response, String json) throws IOException
	{
		String contentType = "application/json";  
        response.setContentType(contentType);  
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();  
        out.print(json);  
        out.flush();  
        out.close();
	}
}
