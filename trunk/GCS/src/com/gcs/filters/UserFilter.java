package com.gcs.filters;

import java.io.IOException;
import java.util.List;

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
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		UserDao uDao = UserDao.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String url = request.getRequestURL().toString();

		if (user != null) {
			if (user.getEmail().contains("@bbva.com")
					|| url.contains("localhost") || url.contains("8888")) {

				String email = "";

				email = user.getEmail();

				com.gcs.beans.User usuario = uDao.getUserByMail(email);
				HttpSession sesion = request.getSession();

				if (url.contains("localhost") || url.contains("8888")) {
					req.setAttribute("entorno", "http://localhost:8888");
				} else {
					if (url.contains("pre.")) {
						req.setAttribute("entorno",
								"https://pre.gcs-areacliente.appspot.com");

					}else{
						if (url.contains("rob.")) {
							req.setAttribute("entorno",
									"https://rob.gcs-areacliente.appspot.com");
	
							}else {
								req.setAttribute("entorno",
									"https://gcs-areacliente.appspot.com");
						}
					}
				}

				if (usuario != null) {
					Integer permiso = usuario.getPermiso();
					String fullName = usuario.getNombre() + " " + usuario.getApellido1() + " " + usuario.getApellido2();
					String usermail = user.getEmail();
					
					sesion.setAttribute("usermail", usermail);
					sesion.setAttribute("permiso", permiso);
					chain.doFilter(req, resp);
				} else {
					if (url.contains("login")){
						chain.doFilter(req, resp);
					}else{
						response.sendRedirect("http://intranet.bbva.com/");
					}					
				}				
			}
		} else {
			if (url.contains("login")) {
				chain.doFilter(req, resp);
			} else {
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
