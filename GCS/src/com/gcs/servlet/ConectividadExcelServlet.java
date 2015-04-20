package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.gcs.beans.Conectividad;
import com.gcs.beans.Servicio;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.ServicioDao;

public class ConectividadExcelServlet extends HttpServlet {
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=ConectividadesGCS.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());
			
			ConectividadDao sDao = ConectividadDao.getInstance();
			List<Conectividad> conectividades = sDao.getAllConectividades();
			
			WritableSheet s = w.createSheet("Conectividad", 0);
		
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.WHITE);
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.BLUE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);			
			
		    s.setColumnView(0, 20);
		    s.setColumnView(1, 20);
		    s.setColumnView(2, 42);
		    s.setColumnView(3, 30);
		    s.setColumnView(4, 42);
		    s.setColumnView(5, 30);
		    s.setColumnView(6, 30);
		    s.setColumnView(7, 30);
		    
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "COD. PROYECTO",cellFormat));
			s.addCell(new Label(1, 0, "SEGURIDAD",cellFormat));
			s.addCell(new Label(2, 0, "FECHA INICIO INFRAESTRUCTURA",cellFormat));
			s.addCell(new Label(3, 0, "FECHA INICIO SEGURIDAD",cellFormat));
			s.addCell(new Label(4, 0, "FECHA FIN INFRAESTRUCTURA",cellFormat));
			s.addCell(new Label(5, 0, "FECHA FIN SEGURIDAD",cellFormat));
			s.addCell(new Label(6, 0, "FECHA IMPLANTACIÓN",cellFormat));
			s.addCell(new Label(7, 0, "ESTADO",cellFormat));
			
			int aux=1;

			for (Conectividad conectividad:conectividades){
				
								
				s.addCell(new Label(0, aux, conectividad.getNombre_proyecto()));
				s.addCell(new Label(1, aux, conectividad.getSeguridad()));
				s.addCell(new Label(2, aux, conectividad.getStr_fecha_ini_infraestructura()));
				s.addCell(new Label(3, aux, conectividad.getStr_fecha_ini_seguridad()));
				s.addCell(new Label(4, aux, conectividad.getStr_fecha_fin_infraestructura()));
				s.addCell(new Label(5, aux, conectividad.getStr_fecha_fin_seguridad()));
				s.addCell(new Label(6, aux, conectividad.getStr_fecha_implantacion()));
				s.addCell(new Label(7, aux, conectividad.getEstado()));
				
				
				
				aux++;
			}		
			
			w.write();
			w.close();
		} catch (Exception e) {
			throw new ServletException("Exception in Excel", e);
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doPost(req,resp);
	}

}
