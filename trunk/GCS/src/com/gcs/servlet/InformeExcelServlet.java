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
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.EstadosDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.UserDao;

public class InformeExcelServlet extends HttpServlet  {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){


		
		
		
		 try {
			 
			HttpSession sesion = req.getSession();
			//int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			String accion = req.getParameter("accion");
			
			if(accion.equals("Cartera"))informeCartera(req,resp);
			
			if(accion.equals("trabajo"))informeCarga(req,resp);

			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	
	private void informeCartera(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePaisesGCS.xlsx");
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
			hoja.setColumnWidth(2, 7000);
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
			
			hoja.createRow(head);
			hoja.getRow(head).setHeightInPoints(20);
			hoja.getRow(head).createCell(1).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(2).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(3).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(4).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(5).setCellStyle(cellStyle);
			hoja.getRow(head).createCell(6).setCellStyle(cellStyle);

			
			
			hoja.getRow(head).getCell(1).setCellValue("AÑO");
			hoja.getRow(head).getCell(2).setCellValue("FECHA\n IMPLANTACIÓN");
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
					
					
					
					hoja.getRow(head).createCell(2).setCellStyle(bodyStyle);
					hoja.getRow(head).getCell(2).setCellValue(servicio.getStr_fecha_implantacion_produccion());
					
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
	
	private void informeImplementaciones(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePaisesGCS.xlsx");
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
		cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
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
		
		
		
		org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet("Cartera");
		hoja.setColumnWidth(0, 4000);		
		hoja.setColumnWidth(1, 7000);
		hoja.setColumnWidth(2, 7000);
		hoja.setColumnWidth(3, 7000);
		hoja.setColumnWidth(4, 7000);
		hoja.setColumnWidth(5, 7000);
		hoja.setColumnWidth(6, 7000);
		hoja.setColumnWidth(7, 7000);
		hoja.setColumnWidth(8, 7000);

		
		int head = 0;
		
		hoja.createRow(head);
		hoja.getRow(head).setHeightInPoints(20);
		hoja.getRow(head).createCell(0).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(1).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(2).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(3).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(4).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(5).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(6).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(7).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(8).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(9).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(10).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(11).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(12).setCellStyle(cellStyle);
		hoja.getRow(head).createCell(13).setCellStyle(cellStyle);
		
		hoja.getRow(head).getCell(0).setCellValue("FECHA\n ALTA");
		hoja.getRow(head).getCell(1).setCellValue("TIPO");
		hoja.getRow(head).getCell(2).setCellValue("CLIENTE");
		hoja.getRow(head).getCell(3).setCellValue("CLASIFICACION");
		hoja.getRow(head).getCell(4).setCellValue("GESTOR IT");
		hoja.getRow(head).getCell(5).setCellValue("GESTOR NEGOCIO");
		hoja.getRow(head).getCell(6).setCellValue("COSTE");
		hoja.getRow(head).getCell(7).setCellValue("PRODUCTO");
		hoja.getRow(head).getCell(8).setCellValue("CONECTIVIDAD");
		hoja.getRow(head).getCell(9).setCellValue("FECHA\n INICIO\n VALORACIÓN");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n FIN\n VALORACIÓN");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n INICIO\n VIABILIDAD");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n FIN\n VIABILIDAD");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n ENVIO\n C100");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n OK\n NEGOCIO");
		
		
		
		
		
		workbook.write(resp.getOutputStream());
	}
	
	
	private void informeCarga(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePaisesGCS.xlsx");
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
	    font.setColor(IndexedColors.AUTOMATIC.getIndex());
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    Font fontBlue = workbook.createFont();
	    fontBlue.setFontName(XSSFFont.DEFAULT_FONT_NAME);
	    fontBlue.setFontHeightInPoints((short)12);
	    fontBlue.setColor(IndexedColors.WHITE.getIndex());
		CellStyle headerBlue = workbook.createCellStyle();
	    headerBlue.setFont(fontBlue);
		headerBlue.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerBlue.setFillPattern(CellStyle.SOLID_FOREGROUND);

		headerBlue.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
		bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
		bodyStyle.setAlignment(CellStyle.BORDER_THIN);
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle greenLightStyle = workbook.createCellStyle();
		greenLightStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		greenLightStyle.setAlignment(CellStyle.ALIGN_CENTER);
		greenLightStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		greenLightStyle.setBorderBottom(CellStyle.BORDER_THIN);
		greenLightStyle.setBorderLeft(CellStyle.BORDER_THIN);
		greenLightStyle.setBorderRight(CellStyle.BORDER_THIN);
		greenLightStyle.setBorderTop(CellStyle.BORDER_THIN);
		
		
		
		
		CellStyle titleStyle = workbook.createCellStyle();
	    Font font1 = workbook.createFont();
	    font1.setFontName(XSSFFont.DEFAULT_FONT_NAME);
	    font1.setFontHeightInPoints((short)14);
	    font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font1);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle cellStylehead = workbook.createCellStyle();
	    Font fonthead = workbook.createFont();
	    fonthead.setFontName(XSSFFont.DEFAULT_FONT_NAME);
	    fonthead.setFontHeightInPoints((short)12);
	    fonthead.setColor(IndexedColors.WHITE.getIndex());
		cellStylehead.setFont(font);
		cellStylehead.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		cellStylehead.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStylehead.setBorderBottom(CellStyle.BORDER_DOUBLE);
		cellStylehead.setBorderLeft(CellStyle.BORDER_DOUBLE);
		cellStylehead.setBorderRight(CellStyle.BORDER_DOUBLE);
		cellStylehead.setBorderTop(CellStyle.BORDER_DOUBLE);
		cellStylehead.setAlignment(CellStyle.ALIGN_CENTER);
		
		CellStyle headerGreen = workbook.createCellStyle();
	    headerGreen.setFont(fontBlue);
		headerGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		headerGreen.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerGreen.setAlignment(CellStyle.ALIGN_CENTER);
		
		int head = 1;
		
		org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet("Informe de Carga");
		
		
		
		hoja.addMergedRegion(new CellRangeAddress(head,head,1,7));
		
		
		hoja.setColumnWidth(1, 7000);
		hoja.setColumnWidth(2, 7000);
		hoja.setColumnWidth(3, 7000);
		hoja.setColumnWidth(4, 7000);
		hoja.setColumnWidth(5, 7000);
		hoja.setColumnWidth(6, 7000);
		hoja.setColumnWidth(7, 7000);
		hoja.setColumnWidth(10, 4000);
		hoja.setColumnWidth(11, 4000);
		hoja.setColumnWidth(12, 7000);
		
		hoja.createRow(head);
		hoja.getRow(head).setHeightInPoints(20);
		hoja.getRow(head).createCell(1).setCellStyle(cellStyle);
		hoja.getRow(head).getCell(1).setCellValue("IMPLEMENTACION");;
		
		head++;
		
		hoja.createRow(head);
		hoja.getRow(head).createCell(1).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(2).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(3).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(4).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(5).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(6).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(7).setCellStyle(headerBlue);
		
		hoja.getRow(head).getCell(1).setCellValue("Cuenta de ESTADO");
		
		head++;
		
		hoja.createRow(head);
		hoja.getRow(head).createCell(1).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(2).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(3).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(4).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(5).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(6).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(7).setCellStyle(headerBlue);
		
		hoja.getRow(head).getCell(1).setCellValue("Etiquetas de fila");
		hoja.getRow(head).getCell(2).setCellValue("PDTE Doc Alcance en GCS");
		hoja.getRow(head).getCell(3).setCellValue("PDTE Valoración IT");
		hoja.getRow(head).getCell(4).setCellValue("Cuenta de ESTADO");
		hoja.getRow(head).getCell(5).setCellValue("En Desarrollo");
		hoja.getRow(head).getCell(6).setCellValue("En Test");
		hoja.getRow(head).getCell(7).setCellValue("Total general");
		
		hoja.getRow(head).createCell(10).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(11).setCellStyle(headerGreen);
		
		hoja.getRow(head).getCell(10).setCellValue("TOT CURSO");
		hoja.getRow(head).getCell(11).setCellValue("Asignaci'on");
		
		
		head++;
		UserDao userDao = UserDao.getInstance();
		List<User> gestores = userDao.getUsersByPermisoStr(3);
		
		ServicioDao servicioDao = ServicioDao.getInstance();
		
		for(User gestor:gestores){
			hoja.createRow(head);
			hoja.getRow(head).createCell(1).setCellStyle(bodyStyle);
			hoja.getRow(head).createCell(2).setCellStyle(bodyStyle);
			hoja.getRow(head).createCell(3).setCellStyle(bodyStyle);
			hoja.getRow(head).createCell(4).setCellStyle(bodyStyle);
			hoja.getRow(head).createCell(5).setCellStyle(bodyStyle);
			hoja.getRow(head).createCell(6).setCellStyle(bodyStyle);
			hoja.getRow(head).createCell(7).setCellStyle(bodyStyle);
			hoja.getRow(head).createCell(10).setCellStyle(bodyStyle);
			hoja.getRow(head).createCell(11).setCellStyle(greenLightStyle);
			hoja.getRow(head).createCell(12).setCellStyle(bodyStyle);
			
			hoja.getRow(head).getCell(1).setCellValue(gestor.getNombre()+" " +gestor.getApellido1()+" "+gestor.getApellido2());
			/*hoja.getRow(head).getCell(2).setCellValue();
			hoja.getRow(head).getCell(3).setCellValue();
			hoja.getRow(head).getCell(4).setCellValue();
			hoja.getRow(head).getCell(5).setCellValue();
			hoja.getRow(head).getCell(6).setCellValue();
			hoja.getRow(head).getCell(7).setCellValue();*/
			
			hoja.getRow(head).getCell(12).setCellValue(gestor.getNombre()+" " +gestor.getApellido1()+" "+gestor.getApellido2());
			
		}
		
		
		
		workbook.write(resp.getOutputStream());
	}
}
