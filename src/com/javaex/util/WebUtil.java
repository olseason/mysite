package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {

	//필드
	
	//생성자
	
	//메소드gs
	
	//메소드 일반
	
	//Forward
	public static void forward( HttpServletRequest request,
								HttpServletResponse response,
								String path) throws ServletException, IOException{
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
	
	//Redirect
	public static void redirect(HttpServletRequest request,
								HttpServletResponse response,
								String url) throws ServletException, IOException{
		response.sendRedirect(url);
	}
}