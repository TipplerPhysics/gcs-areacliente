package com.gcs.servlet;

import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InformeExcelServlet extends HttpServlet  {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){


		
		
		
		 try {
			 
			HttpSession sesion = req.getSession();
			//int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			String accion = req.getParameter("accion");
			
			if(accion.equals("Cartera"))informeCartera(req,resp);

			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	
	private void informeCartera(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePruebasSTE.xlsx");
		String link= "/datadocs/templatePruebas.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
	}
	
	
}
