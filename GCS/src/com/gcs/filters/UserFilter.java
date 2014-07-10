package com.gcs.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gcs.dao.UserDao;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserFilter implements Filter {
	
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		UserDao uDao = UserDao.getInstance();
		
		 UserService userService = UserServiceFactory.getUserService();
		 User user = userService.getCurrentUser();
		 
		 HttpServletRequest request = (HttpServletRequest)req;
		 HttpServletResponse response = (HttpServletResponse)resp;
		 
		 String url = request.getRequestURL().toString();
		 
		
		 
		 if (user!=null) {
			 if (user.getEmail().contains("@bbva.com") ||  url.contains("localhost")) {
				
				 String email ="";
				
				 email=user.getEmail();
				 
				 
				 com.gcs.beans.User usuario = uDao.getUserByMail(email);
				 HttpSession sesion = request.getSession();
				 
				 if (url.contains("localhost")){
					 req.setAttribute("entorno", "http://localhost:8888");
				 }else{
					 req.setAttribute("entorno", "https://gcs-areacliente.appspot.com");
				 }
				 
				 if (usuario!=null){
					 Integer permiso = usuario.getPermiso();
				 	 sesion.setAttribute("permiso", permiso);					 
				 }else{					
					 sesion.setAttribute("permiso", 0);					 
				 }
				 
				
		
				 
				 chain.doFilter(req, resp);
			 }
		 }else{
			 if (url.contains("localhost")){
				 chain.doFilter(req, resp);
			 }else{
				 response.sendRedirect("http://intranet.bbva.com/");	
			 }
		 }
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		 UserService userService = UserServiceFactory.getUserService();
		 
		 User user = userService.getCurrentUser();

		 if (user != null) {
		     String email = user.getEmail();
		 }
		
	}
}
