package com.gcs.servlet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gcs.beans.Cliente;
import com.gcs.beans.Estados;
import com.gcs.beans.Pais;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.EstadosDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;

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
		resp.setHeader("Content-Disposition","attachment; filename=InformePaisesSTE.xlsx");
		String link= "/datadocs/templatePaises.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook();
		
		
		PaisDao paisDao =  PaisDao.getInstance();
		List<Pais> paises = paisDao.getAllPaises();
		
		EstadosDao estadosDao =  EstadosDao.getInstance();
		List<Estados> estados = estadosDao.getAllEstados();
		
		ClienteDao clienteDao = ClienteDao.getInstance();
		
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		
		//Estilo de celda del encabezado
		byte[] rgb = new byte[]{(byte)0x006699};
		org.apache.poi.xssf.usermodel.XSSFColor azul = new XSSFColor(rgb); 
		short width = 3;
		CellStyle cellStyle = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
	    font.setFontHeightInPoints((short)12);
	    font.setColor(IndexedColors.WHITE.getIndex());
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(CellStyle.BORDER_DOUBLE);
		cellStyle.setBorderLeft(CellStyle.BORDER_DOUBLE);
		cellStyle.setBorderRight(CellStyle.BORDER_DOUBLE);
		cellStyle.setBorderTop(CellStyle.BORDER_DOUBLE);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle headerGreen = workbook.createCellStyle();
	    headerGreen.setFont(font);
		headerGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		headerGreen.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerGreen.setBorderBottom(CellStyle.BORDER_DOUBLE);
		headerGreen.setBorderLeft(CellStyle.BORDER_DOUBLE);
		headerGreen.setBorderRight(CellStyle.BORDER_DOUBLE);
		headerGreen.setBorderTop(CellStyle.BORDER_DOUBLE);
		headerGreen.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
		bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
		bodyStyle.setAlignment(CellStyle.BORDER_THIN);
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle titleStyle = workbook.createCellStyle();
	    Font font1 = workbook.createFont();
	    font1.setFontName(XSSFFont.DEFAULT_FONT_NAME);
	    font1.setFontHeightInPoints((short)14);
	    font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font1);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		ServicioDao servDao = ServicioDao.getInstance();
		for(Pais pais : paises){
			org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet(pais.getName());
			int head = 2;
			
			hoja.createRow(head).createCell(1).setCellStyle(titleStyle);
			hoja.getRow(head).createCell(2).setCellStyle(titleStyle);
			hoja.getRow(head).getCell(1).setCellValue("PIPELINE");
			
			
			head++;
			
			hoja.setColumnWidth(1, 3000);
			hoja.setColumnWidth(2, 3000);
			hoja.setColumnWidth(3, 7000);
			hoja.setColumnWidth(4, 3000);
			hoja.setColumnWidth(5, 5000);
			hoja.setColumnWidth(6, 12000);
			hoja.setColumnWidth(7, 9000);
			hoja.setColumnWidth(8, 15000);
			
			
			
			hoja.createRow(head).createCell(1).setCellStyle(cellStyle);
			hoja.getRow(head).setHeightInPoints(20);
			hoja.getRow(head).createCell(2).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(3).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(4).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(5).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(6).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(7).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(8).setCellStyle(headerGreen);
			
			hoja.getRow(head).getCell(1).setCellValue("AÑO");
			hoja.getRow(head).getCell(2).setCellValue("TRIMESTRE");
			hoja.getRow(head).getCell(3).setCellValue("CLIENTE");
			hoja.getRow(head).getCell(4).setCellValue("BANCA");
			hoja.getRow(head).getCell(5).setCellValue("PRODUCTO");
			hoja.getRow(head).getCell(6).setCellValue("SERVICIOS");
			hoja.getRow(head).getCell(7).setCellValue("ETAPA");
			hoja.getRow(head).getCell(8).setCellValue("COMENTARIOS");
			
			
			List<Servicio> servicios = new ArrayList<Servicio>(); 
			
			for(Estados estado:estados){
				if(!estado.getName().equals("Excluido por Timeout")&&
						!estado.getName().equals("Implementado con OK")&&
						!estado.getName().equals("Implementado sin OK")&&
						!estado.getName().equals("Parado por negocio")&&
						!estado.getName().equals("Parado por IT")&&
						!estado.getName().equals("Parado por producto")&&
						!estado.getName().equals("Excluido por Timeout")&&
						!estado.getName().equals("Excluido por negocio")
						){
					servicios.addAll(servDao.getAllServiciosByEstadoPais(pais.getName(),estado.getName()));
				}
			}
			
			head++;
			
			for(Servicio servicio:servicios){
				if(servicio.getCliente_key()!=null&&servicio.getId_proyecto()!=null){
					hoja.createRow(head);
					Proyecto proyecto = proyectoDao.getProjectbyId(servicio.getId_proyecto());
					
					hoja.getRow(head).createCell(1).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(1).setCellValue(proyecto.getFecha_alta_str().substring(6, 10));
					
					int mes = Integer.parseInt(proyecto.getFecha_alta_str().substring(3,5));
					
					hoja.getRow(head).createCell(2).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(2).setCellValue((mes/3+1)+"º");
					
					hoja.getRow(head).createCell(3).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(3).setCellValue(servicio.getCliente_name());
					
					Cliente cliente = clienteDao.getClienteById(servicio.getCliente_key());
					
					hoja.getRow(head).createCell(4).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(4).setCellValue(cliente.getTipo());
					
					hoja.getRow(head).createCell(5).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(5).setCellValue(proyecto.getProducto());
					
					hoja.getRow(head).createCell(6).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(6).setCellValue(servicio.getServicio());
					
					hoja.getRow(head).createCell(7).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(7).setCellValue(servicio.getEstado());
					
					hoja.getRow(head).createCell(8).setCellStyle(bodyStyle);
					
					
					
					head++;
				}
			}
			
			servicios = new ArrayList<>();
			servicios.addAll(servDao.getAllServiciosByEstadoPais(pais.getName(),"Implementado con OK"));
			servicios.addAll(servDao.getAllServiciosByEstadoPais(pais.getName(),"Implementado sin OK"));
			
			head++;
			head++;
			
			
			hoja.createRow(head).createCell(1).setCellStyle(titleStyle);
			hoja.getRow(head).createCell(2).setCellStyle(titleStyle);
			hoja.addMergedRegion(new CellRangeAddress(head,head,1,2));
			hoja.getRow(head).getCell(1).setCellValue("IMPLANTADOS");
			
			
			
			head++;
			hoja.createRow(head).createCell(1).setCellStyle(cellStyle);
			hoja.getRow(head).setHeightInPoints(20);
			hoja.getRow(head).createCell(2).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(3).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(4).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(5).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(6).setCellStyle(cellStyle);

			
			
			hoja.getRow(head).getCell(1).setCellValue("AÑO");
			hoja.getRow(head).getCell(2).setCellValue("TRIMESTRE");
			hoja.getRow(head).getCell(3).setCellValue("CLIENTE");
			hoja.getRow(head).getCell(4).setCellValue("BANCA");
			hoja.getRow(head).getCell(5).setCellValue("PRODUCTO");
			hoja.getRow(head).getCell(6).setCellValue("SERVICIOS");

			head++;
			
			
			for(Servicio servicio:servicios){
				if(servicio.getCliente_key()!=null&&servicio.getId_proyecto()!=null){
					hoja.createRow(head);
					Proyecto proyecto = proyectoDao.getProjectbyId(servicio.getId_proyecto());
					
					hoja.getRow(head).createCell(1).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(1).setCellValue(proyecto.getFecha_alta_str().substring(6, 10));
					
					int mes = Integer.parseInt(proyecto.getFecha_alta_str().substring(3,5));
					
					hoja.getRow(head).createCell(2).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(2).setCellValue((mes/3+1)+"º");
					
					hoja.getRow(head).createCell(3).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(3).setCellValue(servicio.getCliente_name());
					
					Cliente cliente = clienteDao.getClienteById(servicio.getCliente_key());
					
					hoja.getRow(head).createCell(4).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(4).setCellValue(cliente.getTipo());
					
					hoja.getRow(head).createCell(5).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(5).setCellValue(proyecto.getProducto());
					
					hoja.getRow(head).createCell(6).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(6).setCellValue(servicio.getServicio());
	
					
					head++;
				
				}
			}
			
		}
		
		
		workbook.write(resp.getOutputStream());
		
	}
	
	
}
