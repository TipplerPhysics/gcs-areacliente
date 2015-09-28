package com.gcs.servlet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.poi.ss.usermodel.DataFormat;
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
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gcs.beans.Cliente;
import com.gcs.beans.Conectividad;
import com.gcs.beans.Coste;
import com.gcs.beans.Estados;
import com.gcs.beans.Pais;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.ConectividadDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.DemandaDao;
import com.gcs.dao.EstadosDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.UserDao;
import com.gcs.utils.Utils;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class InformeExcelServlet extends HttpServlet  {

	public static final int[] diasMes = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
	public static final String[] months = new String[]{"ENE","FEB","MAR","ABR","MAY","JUN","JUL","AGO","SEP","OCT","NOV","DIC"};
	public void doGet(HttpServletRequest req, HttpServletResponse resp){


		
		
		
		 try {
			 
			HttpSession sesion = req.getSession();
			//int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			String accion = req.getParameter("accion");
			
			if(accion.equals("Paises"))informePaises(req,resp);
			
			if(accion.equals("Cartera"))informeCartera(req,resp);
			
			if(accion.equals("trabajo"))informeCarga(req,resp);
			
			if(accion.equals("implementaciones"))informeImplementaciones(req,resp);
			
			if(accion.equals("coste"))informeCoste(req,resp);
			
			if(accion.equals("complejo"))informeComplejo(req,resp);
			
			if(accion.equals("consult"))consulta(req,resp);

			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private void consulta(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		JSONObject json = new JSONObject();
		JSONArray jarray = new JSONArray();
		
		String[] clientes = req.getParameterValues("cliente");
		String[] entidades =req.getParameterValues("entidades");
		String[] variables =req.getParameterValues("variables");

		List<Cliente> clientesList =null;
		List<Conectividad> conectividades = null;
		List<Proyecto> proyectos = null;
		List<Servicio> servicios = null;
		
		ClienteDao clienteDao = ClienteDao.getInstance();
		
		if(clientes!=null){
			boolean todos = false;
			for(String cliente : clientes){
				if(cliente.equals("default"))todos=true;
			}
			
			if(todos){
				clientesList = clienteDao.getAllClientes();
			}else{
				clientesList = clienteDao.getClienteByIdIn(clientes);
			}
		}
		
		boolean containProyect = false;
		boolean containServicio = false;
		boolean containConect = false;
		boolean containTipoCliente = false;
		boolean containFechaInicio = false;
		boolean containImplementacion = false;
		boolean containProducto = false;
		boolean containFechaImplementacion = false;
		boolean containPaises = false;
		boolean containEstadosServicio = false;
		boolean containEstadosConectividad = false;
		
		//MAS RAPIDO QUE PASAR A UN ARRAYLIST PARA COMPROBAR	
		if(entidades!=null&&clientes!=null){
			for(String entidad:entidades){
				if(entidad.contains("proyectos"))containProyect=true;
				if(entidad.contains("conectividad"))containConect=true;
				if(entidad.contains("servicio"))containServicio=true;
				if(entidad.contains("tipoCliente"))containTipoCliente=true;
			}
		}
		if(variables!=null&&entidades!=null&&clientes!=null){
			for(String entidad:variables){
				
				if(entidad.contains("fechaInicio"))containFechaInicio=true;
				if(entidad.contains("implementacion"))containImplementacion=true;
				if(entidad.contains("producto"))containProducto=true;
				if(entidad.contains("fechaImplementacion"))containFechaImplementacion =true;
				if(entidad.contains("paises"))containPaises=true;
				if(entidad.contains("estadosServ"))containEstadosServicio=true;
				if(entidad.contains("estadosConect"))containEstadosConectividad=true;
			}
		}
		
		
		if(containProyect){
			jarray.put("<(*)head(*)>");
			jarray.put("Cliente");
			jarray.put("CÃ³digo proyecto");
			if(containTipoCliente)jarray.put("Tipo cliente");
			if(containFechaInicio)jarray.put("Fecha inicio");
			if(containImplementacion)jarray.put("Tipo implementaciÃ³n");
			if(containProducto)jarray.put("Producto");
			jarray.put("<(*)finHead(*)>");
		}
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		if(containProyect){
			List<Proyecto> proyectosAux = new ArrayList<Proyecto>();
			for(Cliente cliente : clientesList){
				proyectosAux = proyectoDao.getProjectsByClient(cliente.getKey().getId());
				for(Proyecto proyecto: proyectosAux){
					jarray.put(cliente.getNombre());
					jarray.put(proyecto.getCod_proyecto());
					if(containTipoCliente)jarray.put(cliente.getTipo());
					if(containFechaInicio)jarray.put(proyecto.getStr_fecha_inicio_valoracion());
					if(containImplementacion)jarray.put(proyecto.getTipo());
					if(containProducto)jarray.put(proyecto.getProducto());
					jarray.put("<(*)finLine(*)>");
				}
			}
		}
		
		if(containProyect)jarray.put("<(*)finTable(*)>");
		
		
		
		if(containServicio){
			jarray.put("<(*)head(*)>");
			jarray.put("Cliente");
			if(containTipoCliente)jarray.put("Tipo cliente");
			if(containFechaImplementacion)jarray.put("Fecha implementacion");
			if(containPaises)jarray.put("PaÃ­s");
			if(containEstadosServicio)jarray.put("Estado");
			jarray.put("<(*)finHead(*)>");
			ServicioDao servicioDao = ServicioDao.getInstance();
			servicios= new ArrayList<Servicio>();
			List<Servicio> serviciosAux = new ArrayList<Servicio>();
			for(Cliente cliente:clientesList){
				List<Proyecto> proyectosAux = proyectoDao.getProjectsByClient(cliente.getKey().getId());
				for(Proyecto proyecto:proyectosAux){
					serviciosAux = servicioDao.getServiciosByProject(proyecto.getKey().getId());
					for(Servicio servicio:serviciosAux){
						jarray.put(cliente.getNombre());
						if(containTipoCliente)jarray.put(cliente.getTipo());					
						if(containFechaImplementacion)jarray.put(servicio.getStr_fecha_implantacion_produccion());
						if(containPaises)jarray.put(servicio.getPais());
						if(containEstadosServicio)jarray.put(servicio.getEstado());
						jarray.put("<(*)finLine(*)>");
					}
				}
			}
			jarray.put("<(*)finTable(*)>");
		}
		
		if(containConect){
			
			jarray.put("<(*)head(*)>");
			jarray.put("Cliente");
			if(containTipoCliente)jarray.put("Tipo cliente");
			if(containEstadosConectividad)jarray.put("Estados");
			jarray.put("<(*)finHead(*)>");
			ConectividadDao conectividadDao = ConectividadDao.getInstance();
			conectividades= new ArrayList<Conectividad>();
			List<Conectividad> conectividadesAux = new ArrayList<Conectividad>();
			for(Cliente cliente:clientesList){
				List<Proyecto> proyectosAux = proyectoDao.getProjectsByClient(cliente.getKey().getId());
				for(Proyecto proyecto:proyectosAux){
					conectividadesAux = conectividadDao.getConectividadesByProject(proyecto.getKey().getId());
					for(Conectividad conectividad:conectividadesAux){
						jarray.put(cliente.getNombre());
						if(containTipoCliente)jarray.put(cliente.getTipo());	
						if(containEstadosConectividad)jarray.put(conectividad.getEstado());
						jarray.put("<(*)finLine(*)>");
					}
				}
			}
			jarray.put("<(*)finTable(*)>");
		}
		

		
		
		
		json.append("success","true");
		json.append("tableArray", jarray);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
		
	}
	
	private void informeComplejo(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformeDinamicoGCS.xlsx");
		String link= "/datadocs/templateComplejo.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));

		
		
		String[] clientes = req.getParameterValues("cliente");
		String[] entidades =req.getParameterValues("entidades");
		String[] variables =req.getParameterValues("variables");
		

		
		org.apache.poi.ss.usermodel.Sheet hoja = workbook.getSheet("Consulta");
		
	    Font fontBlue = workbook.createFont();
	    fontBlue.setFontName(XSSFFont.DEFAULT_FONT_NAME);
	    fontBlue.setFontHeightInPoints((short)12);
	    fontBlue.setColor(IndexedColors.WHITE.getIndex());
		CellStyle headerBlue = workbook.createCellStyle();
	    headerBlue.setFont(fontBlue);
		headerBlue.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerBlue.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBlue.setAlignment(CellStyle.ALIGN_CENTER);
		
		
		
		ClienteDao clienteDao  = ClienteDao.getInstance();
		
		
		
		
		List<Cliente> clientesList =null;
		List<Conectividad> conectividades = null;
		List<Proyecto> proyectos = null;
		List<Servicio> servicios = null;
		
		if(clientes!=null){
			boolean todos = false;
			for(String cliente : clientes){
				if(cliente.equals("default"))todos=true;
			}
			
			if(todos){
				clientesList = clienteDao.getAllClientes();
			}else{
				clientesList = clienteDao.getClienteByIdIn(clientes);
			}
		}
		
		boolean containProyect = false;
		boolean containServicio = false;
		boolean containConect = false;
		boolean containTipoCliente = false;
		boolean containFechaInicio = false;
		boolean containImplementacion = false;
		boolean containProducto = false;
		boolean containFechaImplementacion = false;
		boolean containPaises = false;
		boolean containEstadosServicio = false;
		boolean containEstadosConectividad = false;
		
		//MAS RAPIDO QUE PASAR A UN ARRAYLIST PARA COMPROBAR	
		if(entidades!=null){
			for(String entidad:entidades){
				if(entidad.contains("proyectos"))containProyect=true;
				if(entidad.contains("conectividad"))containConect=true;
				if(entidad.contains("servicio"))containServicio=true;
				if(entidad.contains("tipoCliente"))containTipoCliente=true;
			}
		}
		if(variables!=null){
			for(String entidad:variables){
				if(entidad.contains("fechaInicio"))containFechaInicio=true;
				if(entidad.contains("implementacion"))containImplementacion=true;
				if(entidad.contains("producto"))containProducto=true;
				if(entidad.contains("fechaImplementacion"))containFechaImplementacion =true;
				if(entidad.contains("paises"))containPaises=true;
				if(entidad.contains("estadosServ"))containEstadosServicio=true;
				if(entidad.contains("estadosConect"))containEstadosConectividad=true;
			}
		}
		/*
		List<String> variablesList = new ArrayList<String>();
		for(String variable: variables){
			variablesList.add(variable);
		}*/
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		proyectos = new ArrayList<Proyecto>();
		List<Proyecto> proyectosAux = new ArrayList<Proyecto>();
		/*
		if(containProyect||containConect||containServicio){

			
			for(Cliente cliente : clientesList){
				proyectosAux = proyectoDao.getProjectsByClient(cliente.getKey().getId());
				for(Proyecto proyecto: proyectosAux){
					proyectos.add(proyecto);
				}
			}
		}
		
		if(containServicio){
			ServicioDao servicioDao = ServicioDao.getInstance();
			servicios= new ArrayList<Servicio>();
			List<Servicio> serviciosAux = new ArrayList<Servicio>();
			for(Proyecto proyecto:proyectos){
				serviciosAux = servicioDao.getServiciosByProject(proyecto.getKey().getId());
				for(Servicio servicio:serviciosAux){
					servicios.add(servicio);
				}
			}
		}
		
		if(containConect){
			ConectividadDao conectividadDao = ConectividadDao.getInstance();
			conectividades= new ArrayList<Conectividad>();
			List<Conectividad> conectividadesAux = new ArrayList<Conectividad>();
			for(Proyecto proyecto:proyectos){
				conectividadesAux = conectividadDao.getConectividadesByProject(proyecto.getKey().getId());
				for(Conectividad conectividad:conectividadesAux){
					conectividades.add(conectividad);
				}
			}
		}		
		
		*/
		int head = 1 ;
		int column = 1;
		int totalColumn = 0;
		
		if(containProyect){
			
			hoja.createRow(head).createCell(column).setCellStyle(headerBlue);
			hoja.getRow(head).getCell(column).setCellValue("Cliente");
			column++;
			totalColumn++;
			
			hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
			hoja.getRow(head).getCell(column).setCellValue("ID proyecto");			
			column++;
			totalColumn++;
			
			if(containTipoCliente){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Tipo cliente");
				column++;
				totalColumn++;
			}
			
			if(containFechaInicio){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Fecha inicio");
				column++;
				totalColumn++;
			}
			
			if(containImplementacion){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Tipo implementacion");
				column++;
				totalColumn++;
			}
			
			if(containProducto){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Producto");
				column++;
				totalColumn++;
			}
			
			head++;
			
			for(Cliente cliente : clientesList){
				proyectosAux = proyectoDao.getProjectsByClient(cliente.getKey().getId());
				for(Proyecto proyecto: proyectosAux){
					column = 1;
					hoja.createRow(head).createCell(column).setCellValue(cliente.getNombre());
					column++;
					hoja.getRow(head).createCell(column).setCellValue(proyecto.getCod_proyecto());
					column++;
					if(containTipoCliente){
						hoja.getRow(head).createCell(column).setCellValue(cliente.getTipo());
						column++;
					}
					
					if(containFechaInicio){
						hoja.getRow(head).createCell(column).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
						column++;
					}
					
					if(containImplementacion){
						hoja.getRow(head).createCell(column).setCellValue(proyecto.getTipo());
						column++;
					}
					
					if(containProducto){
						hoja.getRow(head).createCell(column).setCellValue(proyecto.getProducto());
						
					}
					head++;
				}
			}	
		}
		
		head+=4;
		column=1;
		if(containServicio){
			hoja.createRow(head).createCell(column).setCellStyle(headerBlue);
			hoja.getRow(head).getCell(column).setCellValue("Cliente");
			column++;
			totalColumn++;
			
			if(containTipoCliente){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Tipo cliente");
				column++;
				totalColumn++;
			}
			
			if(containFechaImplementacion){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Fecha implantacion");
				column++;
				totalColumn++;
			}
			
			if(containPaises){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("PaÃ­s");
				column++;
				totalColumn++;
			}
			
			if(containEstadosServicio){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Estados");
				column++;
				totalColumn++;
			}
			
			head++;
			
			for(Cliente cliente : clientesList){
				proyectosAux = proyectoDao.getProjectsByClient(cliente.getKey().getId());
				for(Proyecto proyecto: proyectosAux){
					ServicioDao servicioDao = ServicioDao.getInstance();
					List<Servicio> serviciosAux = servicioDao.getServiciosByProject(proyecto.getKey().getId());
					for(Servicio servicio:serviciosAux){
						column = 1;
						hoja.createRow(head).createCell(column).setCellValue(cliente.getNombre());
						column++;
						if(containTipoCliente){
							hoja.getRow(head).createCell(column).setCellValue(cliente.getTipo());
							column++;
						}
						
						if(containFechaImplementacion){
							hoja.getRow(head).createCell(column).setCellValue(servicio.getStr_fecha_implantacion_produccion());
							column++;
						}
						
						if(containPaises){
							hoja.getRow(head).createCell(column).setCellValue(servicio.getPais());
							column++;
						}
						
						if(containEstadosServicio){
							hoja.getRow(head).createCell(column).setCellValue(servicio.getEstado());
							
						}
						head++;
					}
				}
			}
			
			head+=4;
			column=1;
		}
		
		if(containConect){
			hoja.createRow(head).createCell(column).setCellStyle(headerBlue);
			hoja.getRow(head).getCell(column).setCellValue("Cliente");
			column++;
			totalColumn++;
			
			if(containTipoCliente){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Tipo cliente");
				column++;
				totalColumn++;
			}
			
			if(containEstadosConectividad){
				hoja.getRow(head).createCell(column).setCellStyle(headerBlue);
				hoja.getRow(head).getCell(column).setCellValue("Estado");
				column++;
				totalColumn++;
			}
			
			head++;
			
			for(Cliente cliente : clientesList){
				proyectosAux = proyectoDao.getProjectsByClient(cliente.getKey().getId());
				for(Proyecto proyecto: proyectosAux){
					ConectividadDao conectividadDao = ConectividadDao.getInstance();
					conectividades= new ArrayList<Conectividad>();
					List<Conectividad> conectividadesAux = new ArrayList<Conectividad>();
					conectividadesAux = conectividadDao.getConectividadesByProject(proyecto.getKey().getId());
					for(Conectividad conectividad:conectividadesAux){
						column = 1;
						hoja.createRow(head).createCell(column).setCellValue(cliente.getNombre());
						column++;
						if(containTipoCliente){
							hoja.getRow(head).createCell(column).setCellValue(cliente.getTipo());
							column++;
						}
						
						if(containEstadosConectividad){
							hoja.getRow(head).createCell(column).setCellValue(conectividad.getEstado());
							column++;
						}
					
						head++;
					}
				}
			}
		}
		
		workbook.write(resp.getOutputStream());
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/*private void informeCartera(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformeCarteraGCS.xlsx");
		String link= "/datadocs/templateCartera.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		ServicioDao serviciosDao = ServicioDao.getInstance();
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		ConectividadDao conectividadDao = ConectividadDao.getInstance();
		ClienteDao clienteDao = ClienteDao.getInstance();
		

		org.apache.poi.ss.usermodel.Sheet hoja = workbook.getSheetAt(0);
		
		String desde = req.getParameter("desde");
		String hasta = req.getParameter("hasta");
		
		if(desde.equals("")){
			desde="01/01/1900";
		}
		if(hasta.equals("")){
			hasta="01/01/2500";
		}
		
		java.util.Date desdeDate = Utils.dateConverter(desde);
		java.util.Date hastaDate = Utils.dateConverter(hasta);
		
		List<Servicio> servicios = null;
		List<Conectividad> conectividades = null;
		
		
		List<Cliente> clientes = clienteDao.getAllClientes();
		
		int head = 1;
		
		
		for(Cliente cliente:clientes){
			List<Proyecto> proyectos = proyectoDao.getProjectsByClientByDates(cliente.getKey().getId(),desdeDate, hastaDate);
			if(proyectos.size()!=0){
				for(Proyecto proyecto:proyectos){
					servicios = serviciosDao.getServiciosByProject(proyecto.getKey().getId());
					conectividades = conectividadDao.getConectividadesByProject(proyecto.getKey().getId());
					if(servicios.size()!=0){
						if(conectividades.size()!=0){
							for(Servicio servicio:servicios){
								for(Conectividad conectividad:conectividades){
									hoja.createRow(head).createCell(0).setCellValue(cliente.getNombre());
									hoja.getRow(head).createCell(1).setCellValue(cliente.getClientId());
									hoja.getRow(head).createCell(2).setCellValue(cliente.getRef_global());
									hoja.getRow(head).createCell(3).setCellValue(cliente.getTipo());
									hoja.getRow(head).createCell(4).setCellValue(proyecto.getTipo());
									hoja.getRow(head).createCell(5).setCellValue(proyecto.getCod_proyecto());
									hoja.getRow(head).createCell(6).setCellValue(proyecto.getProducto());
									hoja.getRow(head).createCell(7).setCellValue(proyecto.getConectividad());
									hoja.getRow(head).createCell(8).setCellValue(conectividad.getSeguridad());
									hoja.getRow(head).createCell(9).setCellValue(servicio.getServicio());//servicio
									hoja.getRow(head).createCell(10).setCellValue(servicio.getPais());//pais
									hoja.getRow(head).createCell(11).setCellValue(servicio.getExtension());//extension
									hoja.getRow(head).createCell(12).setCellValue(servicio.getCod_servicio());//codigo
									hoja.getRow(head).createCell(13).setCellValue(servicio.getEstado());//estado
									hoja.getRow(head).createCell(14).setCellValue(proyecto.getGestor_it_name());
									hoja.getRow(head).createCell(15).setCellValue(proyecto.getGestor_negocio_name());
									hoja.getRow(head).createCell(16).setCellValue(proyecto.getFecha_alta_str());
									hoja.getRow(head).createCell(17).setCellValue(proyecto.getSubtipo());
									hoja.getRow(head).createCell(18).setCellValue(proyecto.getClasificacion());
									hoja.getRow(head).createCell(19).setCellValue(proyecto.getCoste());
									hoja.getRow(head).createCell(20).setCellValue(servicio.getObservaciones());//observaciones
									hoja.getRow(head).createCell(21).setCellValue(servicio.getFormato_intermedio());//formato intermedio
									hoja.getRow(head).createCell(22).setCellValue(servicio.getFormato_local());//formato local
									hoja.getRow(head).createCell(23).setCellValue(servicio.getReferencia_local1());//
									hoja.getRow(head).createCell(24).setCellValue(servicio.getReferencia_local2());//referencia local
									hoja.getRow(head).createCell(25).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
									hoja.getRow(head).createCell(26).setCellValue(proyecto.getStr_fecha_fin_valoracion());
									hoja.getRow(head).createCell(27).setCellValue(proyecto.getStr_envioC100());
									hoja.getRow(head).createCell(28).setCellValue(proyecto.getStr_OKNegocio());
									hoja.getRow(head).createCell(29).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
									hoja.getRow(head).createCell(30).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
									hoja.getRow(head).createCell(31).setCellValue(proyecto.getStr_fecha_plan_trabajo());
									hoja.getRow(head).createCell(32).setCellValue(proyecto.getStr_OKNegocioCheck());
									hoja.getRow(head).createCell(33).setCellValue(proyecto.getStr_fecha_disponible_conectividad());
									hoja.getRow(head).createCell(34).setCellValue(servicio.getStr_fecha_ini_pruebas());//fecha estimada pruebas
									hoja.getRow(head).createCell(35).setCellValue(servicio.getStr_fecha_fin_pruebas());//fecha estimada fin pruebas
									hoja.getRow(head).createCell(36).setCellValue(conectividad.getStr_fecha_ini_infraestructura());
									hoja.getRow(head).createCell(37).setCellValue(conectividad.getStr_fecha_fin_infraestructura());
									hoja.getRow(head).createCell(38).setCellValue(conectividad.getStr_fecha_implantacion());
									hoja.getRow(head).createCell(39).setCellValue(conectividad.getStr_fecha_ini_seguridad());
									hoja.getRow(head).createCell(40).setCellValue(conectividad.getStr_fecha_fin_seguridad());
									hoja.getRow(head).createCell(41).setCellValue(conectividad.getEstado());
									hoja.getRow(head).createCell(42).setCellValue(servicio.getStr_fecha_ini_integradas());//fecha inicio pruebas integradas
									hoja.getRow(head).createCell(43).setCellValue(servicio.getStr_fecha_fin_integradas());//fecha fin pruebas integradas
									hoja.getRow(head).createCell(44).setCellValue(servicio.getStr_fecha_ini_aceptacion());//fecha inicio pruebas aceptacion
									hoja.getRow(head).createCell(45).setCellValue(servicio.getStr_fecha_fin_aceptacion());//fecha fin pruebas aceptacion
									hoja.getRow(head).createCell(46).setCellValue(servicio.getStr_fecha_implantacion_produccion());//fehca implantacion servicio produccion
									hoja.getRow(head).createCell(47).setCellValue(servicio.getStr_fecha_ini_validacion());//fecha inicio validacion 
									hoja.getRow(head).createCell(48).setCellValue(servicio.getStr_fecha_fin_validacion());//fehca fin validacion
									hoja.getRow(head).createCell(49).setCellValue(servicio.getStr_fecha_ini_primera_operacion());//fecha inicio primera operacion
									hoja.getRow(head).createCell(50).setCellValue(servicio.getStr_fecha_fin_primera_operacion());//fehca fin primera operacion
									hoja.getRow(head).createCell(51).setCellValue(servicio.getStr_fecha_ini_op_cliente());//fecha inicio operacion cliente
									hoja.getRow(head).createCell(52).setCellValue(servicio.getStr_fecha_ANS());//fecha paso a ans
									head++;
								}
							}
						}else{
							for(Servicio servicio:servicios){							
								hoja.createRow(head).createCell(0).setCellValue(cliente.getNombre());
								hoja.getRow(head).createCell(1).setCellValue(cliente.getClientId());
								hoja.getRow(head).createCell(2).setCellValue(cliente.getRef_global());
								hoja.getRow(head).createCell(3).setCellValue(cliente.getTipo());
								hoja.getRow(head).createCell(4).setCellValue(proyecto.getTipo());
								hoja.getRow(head).createCell(5).setCellValue(proyecto.getCod_proyecto());
								hoja.getRow(head).createCell(6).setCellValue(proyecto.getProducto());
								hoja.getRow(head).createCell(7).setCellValue(proyecto.getConectividad());
								hoja.getRow(head).createCell(8).setCellValue("");//conectividad.getSeguridad());
								hoja.getRow(head).createCell(9).setCellValue(servicio.getServicio());//servicio
								hoja.getRow(head).createCell(10).setCellValue(servicio.getPais());//pais
								hoja.getRow(head).createCell(11).setCellValue(servicio.getExtension());//extension
								hoja.getRow(head).createCell(12).setCellValue(servicio.getCod_servicio());//codigo
								hoja.getRow(head).createCell(13).setCellValue(servicio.getEstado());//estado
								hoja.getRow(head).createCell(14).setCellValue(proyecto.getGestor_it_name());
								hoja.getRow(head).createCell(15).setCellValue(proyecto.getGestor_negocio_name());
								hoja.getRow(head).createCell(16).setCellValue(proyecto.getFecha_alta_str());
								hoja.getRow(head).createCell(17).setCellValue(proyecto.getSubtipo());
								hoja.getRow(head).createCell(18).setCellValue(proyecto.getClasificacion());
								hoja.getRow(head).createCell(19).setCellValue(proyecto.getCoste());
								hoja.getRow(head).createCell(20).setCellValue(servicio.getObservaciones());//observaciones
								hoja.getRow(head).createCell(21).setCellValue(servicio.getFormato_intermedio());//formato intermedio
								hoja.getRow(head).createCell(22).setCellValue(servicio.getFormato_local());//formato local
								hoja.getRow(head).createCell(23).setCellValue(servicio.getReferencia_local1());//
								hoja.getRow(head).createCell(24).setCellValue(servicio.getReferencia_local2());//referencia local
								hoja.getRow(head).createCell(25).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
								hoja.getRow(head).createCell(26).setCellValue(proyecto.getStr_fecha_fin_valoracion());
								hoja.getRow(head).createCell(27).setCellValue(proyecto.getStr_envioC100());
								hoja.getRow(head).createCell(28).setCellValue(proyecto.getStr_OKNegocio());
								hoja.getRow(head).createCell(29).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
								hoja.getRow(head).createCell(30).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
								hoja.getRow(head).createCell(31).setCellValue(proyecto.getStr_fecha_plan_trabajo());
								hoja.getRow(head).createCell(32).setCellValue(proyecto.getStr_OKNegocioCheck());
								hoja.getRow(head).createCell(33).setCellValue(proyecto.getStr_fecha_disponible_conectividad());
								hoja.getRow(head).createCell(34).setCellValue(servicio.getStr_fecha_ini_pruebas());//fecha estimada pruebas
								hoja.getRow(head).createCell(35).setCellValue(servicio.getStr_fecha_fin_pruebas());//fecha estimada fin pruebas
								hoja.getRow(head).createCell(36).setCellValue("");//conectividad.getStr_fecha_ini_infraestructura());
								hoja.getRow(head).createCell(37).setCellValue("");//conectividad.getStr_fecha_fin_infraestructura());
								hoja.getRow(head).createCell(38).setCellValue("");//conectividad.getStr_fecha_implantacion());
								hoja.getRow(head).createCell(39).setCellValue("");//conectividad.getStr_fecha_ini_seguridad());
								hoja.getRow(head).createCell(40).setCellValue("");//conectividad.getStr_fecha_fin_seguridad());
								hoja.getRow(head).createCell(41).setCellValue("");//conectividad.getEstado());
								hoja.getRow(head).createCell(42).setCellValue(servicio.getStr_fecha_ini_integradas());//fecha inicio pruebas integradas
								hoja.getRow(head).createCell(43).setCellValue(servicio.getStr_fecha_fin_integradas());//fecha fin pruebas integradas
								hoja.getRow(head).createCell(44).setCellValue(servicio.getStr_fecha_ini_aceptacion());//fecha inicio pruebas aceptacion
								hoja.getRow(head).createCell(45).setCellValue(servicio.getStr_fecha_fin_aceptacion());//fecha fin pruebas aceptacion
								hoja.getRow(head).createCell(46).setCellValue(servicio.getStr_fecha_implantacion_produccion());//fehca implantacion servicio produccion
								hoja.getRow(head).createCell(47).setCellValue(servicio.getStr_fecha_ini_validacion());//fecha inicio validacion 
								hoja.getRow(head).createCell(48).setCellValue(servicio.getStr_fecha_fin_validacion());//fehca fin validacion
								hoja.getRow(head).createCell(49).setCellValue(servicio.getStr_fecha_ini_primera_operacion());//fecha inicio primera operacion
								hoja.getRow(head).createCell(50).setCellValue(servicio.getStr_fecha_fin_primera_operacion());//fehca fin primera operacion
								hoja.getRow(head).createCell(51).setCellValue(servicio.getStr_fecha_ini_op_cliente());//fecha inicio operacion cliente
								hoja.getRow(head).createCell(52).setCellValue(servicio.getStr_fecha_ANS());//fecha paso a ans
								head++;
							}
						}	
					}else{
						if(conectividades.size()!=0){
							for(Conectividad conectividad:conectividades){
								hoja.createRow(head).createCell(0).setCellValue(cliente.getNombre());
								hoja.getRow(head).createCell(1).setCellValue(cliente.getClientId());
								hoja.getRow(head).createCell(2).setCellValue(cliente.getRef_global());
								hoja.getRow(head).createCell(3).setCellValue(cliente.getTipo());
								hoja.getRow(head).createCell(4).setCellValue(proyecto.getTipo());
								hoja.getRow(head).createCell(5).setCellValue(proyecto.getCod_proyecto());
								hoja.getRow(head).createCell(6).setCellValue(proyecto.getProducto());
								hoja.getRow(head).createCell(7).setCellValue(proyecto.getConectividad());
								hoja.getRow(head).createCell(8).setCellValue(conectividad.getSeguridad());
								hoja.getRow(head).createCell(9).setCellValue("");//servicio
								hoja.getRow(head).createCell(10).setCellValue("");//pais
								hoja.getRow(head).createCell(11).setCellValue("");//extension
								hoja.getRow(head).createCell(12).setCellValue("");//codigo
								hoja.getRow(head).createCell(13).setCellValue("");//estado
								hoja.getRow(head).createCell(14).setCellValue(proyecto.getGestor_it_name());
								hoja.getRow(head).createCell(15).setCellValue(proyecto.getGestor_negocio_name());
								hoja.getRow(head).createCell(16).setCellValue(proyecto.getFecha_alta_str());
								hoja.getRow(head).createCell(17).setCellValue(proyecto.getSubtipo());
								hoja.getRow(head).createCell(18).setCellValue(proyecto.getClasificacion());
								hoja.getRow(head).createCell(19).setCellValue(proyecto.getCoste());
								hoja.getRow(head).createCell(20).setCellValue("");//observaciones
								hoja.getRow(head).createCell(21).setCellValue("");//formato intermedio
								hoja.getRow(head).createCell(22).setCellValue("");//formato local
								hoja.getRow(head).createCell(23).setCellValue("");//referencia local integ
								hoja.getRow(head).createCell(24).setCellValue("");//referencia local
								hoja.getRow(head).createCell(25).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
								hoja.getRow(head).createCell(26).setCellValue(proyecto.getStr_fecha_fin_valoracion());
								hoja.getRow(head).createCell(27).setCellValue(proyecto.getStr_envioC100());
								hoja.getRow(head).createCell(28).setCellValue(proyecto.getStr_OKNegocio());
								hoja.getRow(head).createCell(29).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
								hoja.getRow(head).createCell(30).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
								hoja.getRow(head).createCell(31).setCellValue(proyecto.getStr_fecha_plan_trabajo());
								hoja.getRow(head).createCell(32).setCellValue(proyecto.getStr_OKNegocioCheck());
								hoja.getRow(head).createCell(33).setCellValue(proyecto.getStr_fecha_disponible_conectividad());
								hoja.getRow(head).createCell(34).setCellValue("");//fecha estimada pruebas
								hoja.getRow(head).createCell(35).setCellValue("");//fecha estimada fin pruebas
								hoja.getRow(head).createCell(36).setCellValue(conectividad.getStr_fecha_ini_infraestructura());
								hoja.getRow(head).createCell(37).setCellValue(conectividad.getStr_fecha_fin_infraestructura());
								hoja.getRow(head).createCell(38).setCellValue(conectividad.getStr_fecha_implantacion());
								hoja.getRow(head).createCell(39).setCellValue(conectividad.getStr_fecha_ini_seguridad());
								hoja.getRow(head).createCell(40).setCellValue(conectividad.getStr_fecha_fin_seguridad());
								hoja.getRow(head).createCell(41).setCellValue(conectividad.getEstado());
								hoja.getRow(head).createCell(42).setCellValue("");//fecha inicio pruebas integradas
								hoja.getRow(head).createCell(43).setCellValue("");//fecha fin pruebas integradas
								hoja.getRow(head).createCell(44).setCellValue("");//fecha inicio pruebas aceptacion
								hoja.getRow(head).createCell(45).setCellValue("");//fecha fin pruebas aceptacion
								hoja.getRow(head).createCell(46).setCellValue("");//fehca implantacion servicio produccion
								hoja.getRow(head).createCell(47).setCellValue("");//fecha inicio validacion 
								hoja.getRow(head).createCell(48).setCellValue("");//fehca fin validacion
								hoja.getRow(head).createCell(49).setCellValue("");//fecha inicio primera operacion
								hoja.getRow(head).createCell(50).setCellValue("");//fehca fin primera operacion
								hoja.getRow(head).createCell(51).setCellValue("");//fecha inicio operacion cliente
								hoja.getRow(head).createCell(52).setCellValue("");//fecha paso a ans
								head++;
							}
						}else{
							hoja.createRow(head).createCell(0).setCellValue(cliente.getNombre());
							hoja.getRow(head).createCell(1).setCellValue(cliente.getClientId());
							hoja.getRow(head).createCell(2).setCellValue(cliente.getRef_global());
							hoja.getRow(head).createCell(3).setCellValue(cliente.getTipo());
							hoja.getRow(head).createCell(4).setCellValue(proyecto.getTipo());
							hoja.getRow(head).createCell(5).setCellValue(proyecto.getCod_proyecto());
							hoja.getRow(head).createCell(6).setCellValue(proyecto.getProducto());
							hoja.getRow(head).createCell(7).setCellValue(proyecto.getConectividad());
							hoja.getRow(head).createCell(8).setCellValue("");
							hoja.getRow(head).createCell(9).setCellValue("");//servicio
							hoja.getRow(head).createCell(10).setCellValue("");//pais
							hoja.getRow(head).createCell(11).setCellValue("");//extension
							hoja.getRow(head).createCell(12).setCellValue("");//codigo
							hoja.getRow(head).createCell(13).setCellValue("");//estado
							hoja.getRow(head).createCell(14).setCellValue(proyecto.getGestor_it_name());
							hoja.getRow(head).createCell(15).setCellValue(proyecto.getGestor_negocio_name());
							hoja.getRow(head).createCell(16).setCellValue(proyecto.getFecha_alta_str());
							hoja.getRow(head).createCell(17).setCellValue(proyecto.getSubtipo());
							hoja.getRow(head).createCell(18).setCellValue(proyecto.getClasificacion());
							hoja.getRow(head).createCell(19).setCellValue(proyecto.getCoste());
							hoja.getRow(head).createCell(20).setCellValue("");//observaciones
							hoja.getRow(head).createCell(21).setCellValue("");//formato intermedio
							hoja.getRow(head).createCell(22).setCellValue("");//formato local
							hoja.getRow(head).createCell(23).setCellValue("");//referencia local integ
							hoja.getRow(head).createCell(24).setCellValue("");//referencia local
							hoja.getRow(head).createCell(25).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
							hoja.getRow(head).createCell(26).setCellValue(proyecto.getStr_fecha_fin_valoracion());
							hoja.getRow(head).createCell(27).setCellValue(proyecto.getStr_envioC100());
							hoja.getRow(head).createCell(28).setCellValue(proyecto.getStr_OKNegocio());
							hoja.getRow(head).createCell(29).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
							hoja.getRow(head).createCell(30).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
							hoja.getRow(head).createCell(31).setCellValue(proyecto.getStr_fecha_plan_trabajo());
							hoja.getRow(head).createCell(32).setCellValue(proyecto.getStr_OKNegocioCheck());
							hoja.getRow(head).createCell(33).setCellValue(proyecto.getStr_fecha_disponible_conectividad());
							hoja.getRow(head).createCell(34).setCellValue("");//fecha estimada pruebas
							hoja.getRow(head).createCell(35).setCellValue("");//fecha estimada fin pruebas
							hoja.getRow(head).createCell(36).setCellValue("");
							hoja.getRow(head).createCell(37).setCellValue("");
							hoja.getRow(head).createCell(38).setCellValue("");
							hoja.getRow(head).createCell(39).setCellValue("");
							hoja.getRow(head).createCell(40).setCellValue("");
							hoja.getRow(head).createCell(41).setCellValue("");
							hoja.getRow(head).createCell(42).setCellValue("");//fecha inicio pruebas integradas
							hoja.getRow(head).createCell(43).setCellValue("");//fecha fin pruebas integradas
							hoja.getRow(head).createCell(44).setCellValue("");//fecha inicio pruebas aceptacion
							hoja.getRow(head).createCell(45).setCellValue("");//fecha fin pruebas aceptacion
							hoja.getRow(head).createCell(46).setCellValue("");//fehca implantacion servicio produccion
							hoja.getRow(head).createCell(47).setCellValue("");//fecha inicio validacion 
							hoja.getRow(head).createCell(48).setCellValue("");//fehca fin validacion
							hoja.getRow(head).createCell(49).setCellValue("");//fecha inicio primera operacion
							hoja.getRow(head).createCell(50).setCellValue("");//fehca fin primera operacion
							hoja.getRow(head).createCell(51).setCellValue("");//fecha inicio operacion cliente
							hoja.getRow(head).createCell(52).setCellValue("");//fecha paso a ans
							head++;
						}
					}
				}
				
				
			}
			
		}
		
		workbook.write(resp.getOutputStream());		
	}*/
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
private void informeCartera(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformeCarteraGCS.xlsx");
		String link= "/datadocs/templateCartera.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		ServicioDao serviciosDao = ServicioDao.getInstance();
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		ConectividadDao conectividadDao = ConectividadDao.getInstance();
		ClienteDao clienteDao = ClienteDao.getInstance();
		

		org.apache.poi.ss.usermodel.Sheet hoja = workbook.getSheetAt(0);
		
		String desde = req.getParameter("desde");
		String hasta = req.getParameter("hasta");
		
		if(desde.equals("")){
			desde="01/01/1900";
		}
		if(hasta.equals("")){
			hasta="01/01/2500";
		}
		
		java.util.Date desdeDate = Utils.dateConverter(desde);
		java.util.Date hastaDate = Utils.dateConverter(hasta);
		
		List<Servicio> servicios = null;
		List<Conectividad> conectividades = null;
		
		
		List<Cliente> clientes = clienteDao.getAllClientesSortedName();
		
		int head = 1;
		
		
		for(Cliente cliente:clientes){
			List<Proyecto> proyectos = proyectoDao.getProjectsByClientByDatesSortedCode(cliente.getKey().getId(),desdeDate, hastaDate);
			if(proyectos.size()!=0){
				for(Proyecto proyecto:proyectos){
					servicios = serviciosDao.getServiciosByProject(proyecto.getKey().getId());
					conectividades = conectividadDao.getConectividadesByProject(proyecto.getKey().getId());
					if(servicios.size()!=0){
						if(conectividades.size()!=0){
							for(Servicio servicio:servicios){
								for(Conectividad conectividad:conectividades){
									hoja.createRow(head).createCell(0).setCellValue(cliente.getNombre());
									hoja.getRow(head).createCell(1).setCellValue(cliente.getClientId());
									hoja.getRow(head).createCell(2).setCellValue(cliente.getRef_global());
									hoja.getRow(head).createCell(3).setCellValue(cliente.getTipo());
									hoja.getRow(head).createCell(4).setCellValue(proyecto.getTipo());
									hoja.getRow(head).createCell(5).setCellValue(proyecto.getCod_proyecto());
									hoja.getRow(head).createCell(6).setCellValue(proyecto.getProducto());
									hoja.getRow(head).createCell(7).setCellValue(proyecto.getSubtipo());
									hoja.getRow(head).createCell(8).setCellValue(proyecto.getConectividad());
									hoja.getRow(head).createCell(9).setCellValue(conectividad.getSeguridad());
									hoja.getRow(head).createCell(10).setCellValue(servicio.getServicio());//servicio
									hoja.getRow(head).createCell(11).setCellValue(servicio.getPais());//pais
									hoja.getRow(head).createCell(12).setCellValue(servicio.getExtension());//extension
									hoja.getRow(head).createCell(13).setCellValue(servicio.getCod_servicio());//codigo
									hoja.getRow(head).createCell(14).setCellValue(servicio.getEstado());//estado
									hoja.getRow(head).createCell(15).setCellValue(proyecto.getGestor_it_name());
									hoja.getRow(head).createCell(16).setCellValue(proyecto.getGestor_negocio_name());
									hoja.getRow(head).createCell(17).setCellValue(proyecto.getFecha_alta_str());
									
									hoja.getRow(head).createCell(18).setCellValue(proyecto.getClasificacion());
									hoja.getRow(head).createCell(19).setCellValue(proyecto.getCoste());
									if(proyecto.getUrl_doc_google_drive() != null){
										hoja.getRow(head).createCell(20).setCellValue(proyecto.getUrl_doc_google_drive());
									}
									hoja.getRow(head).createCell(21).setCellValue(servicio.getObservaciones());//observaciones
									hoja.getRow(head).createCell(22).setCellValue(servicio.getFormato_intermedio());//formato intermedio
									hoja.getRow(head).createCell(23).setCellValue(servicio.getFormato_local());//formato local
									hoja.getRow(head).createCell(24).setCellValue(servicio.getReferencia_local1());//
									hoja.getRow(head).createCell(25).setCellValue(servicio.getReferencia_local2());//referencia local
									hoja.getRow(head).createCell(26).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
									hoja.getRow(head).createCell(27).setCellValue(proyecto.getStr_fecha_fin_valoracion());
									hoja.getRow(head).createCell(28).setCellValue(proyecto.getStr_envioC100());
									hoja.getRow(head).createCell(29).setCellValue(proyecto.getStr_OKNegocio());
									hoja.getRow(head).createCell(30).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
									hoja.getRow(head).createCell(31).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
									hoja.getRow(head).createCell(32).setCellValue(proyecto.getStr_fecha_plan_trabajo());
									hoja.getRow(head).createCell(33).setCellValue(proyecto.getStr_OKNegocioCheck());
									hoja.getRow(head).createCell(34).setCellValue(proyecto.getStr_fecha_disponible_conectividad());
									hoja.getRow(head).createCell(35).setCellValue(servicio.getStr_fecha_ini_pruebas());//fecha estimada pruebas
									hoja.getRow(head).createCell(36).setCellValue(servicio.getStr_fecha_fin_pruebas());//fecha estimada fin pruebas
									hoja.getRow(head).createCell(37).setCellValue(conectividad.getStr_fecha_ini_infraestructura());
									hoja.getRow(head).createCell(38).setCellValue(conectividad.getStr_fecha_fin_infraestructura());
									hoja.getRow(head).createCell(39).setCellValue(conectividad.getStr_fecha_implantacion());
									hoja.getRow(head).createCell(40).setCellValue(conectividad.getStr_fecha_ini_seguridad());
									hoja.getRow(head).createCell(41).setCellValue(conectividad.getStr_fecha_fin_seguridad());
									hoja.getRow(head).createCell(42).setCellValue(conectividad.getEstado());
									hoja.getRow(head).createCell(43).setCellValue(servicio.getStr_fecha_ini_integradas());//fecha inicio pruebas integradas
									hoja.getRow(head).createCell(44).setCellValue(servicio.getStr_fecha_fin_integradas());//fecha fin pruebas integradas
									hoja.getRow(head).createCell(45).setCellValue(servicio.getStr_fecha_ini_aceptacion());//fecha inicio pruebas aceptacion
									hoja.getRow(head).createCell(46).setCellValue(servicio.getStr_fecha_fin_aceptacion());//fecha fin pruebas aceptacion
									hoja.getRow(head).createCell(47).setCellValue(servicio.getStr_fecha_implantacion_produccion());//fehca implantacion servicio produccion
									hoja.getRow(head).createCell(48).setCellValue(servicio.getStr_fecha_ini_validacion());//fecha inicio validacion 
									hoja.getRow(head).createCell(49).setCellValue(servicio.getStr_fecha_fin_validacion());//fehca fin validacion
									hoja.getRow(head).createCell(50).setCellValue(servicio.getStr_fecha_ini_primera_operacion());//fecha inicio primera operacion
									hoja.getRow(head).createCell(51).setCellValue(servicio.getStr_fecha_fin_primera_operacion());//fehca fin primera operacion
									hoja.getRow(head).createCell(52).setCellValue(servicio.getStr_fecha_ini_op_cliente());//fecha inicio operacion cliente
									hoja.getRow(head).createCell(53).setCellValue(servicio.getStr_fecha_ANS());//fecha paso a ans
									head++;
								}
							}
						}else{
							for(Servicio servicio:servicios){							
								hoja.createRow(head).createCell(0).setCellValue(cliente.getNombre());
								hoja.getRow(head).createCell(1).setCellValue(cliente.getClientId());
								hoja.getRow(head).createCell(2).setCellValue(cliente.getRef_global());
								hoja.getRow(head).createCell(3).setCellValue(cliente.getTipo());
								hoja.getRow(head).createCell(4).setCellValue(proyecto.getTipo());
								hoja.getRow(head).createCell(5).setCellValue(proyecto.getCod_proyecto());
								hoja.getRow(head).createCell(6).setCellValue(proyecto.getProducto());
								hoja.getRow(head).createCell(7).setCellValue(proyecto.getSubtipo());
								hoja.getRow(head).createCell(8).setCellValue(proyecto.getConectividad());
								hoja.getRow(head).createCell(9).setCellValue("");//conectividad.getSeguridad());
								hoja.getRow(head).createCell(10).setCellValue(servicio.getServicio());//servicio
								hoja.getRow(head).createCell(11).setCellValue(servicio.getPais());//pais
								hoja.getRow(head).createCell(12).setCellValue(servicio.getExtension());//extension
								hoja.getRow(head).createCell(13).setCellValue(servicio.getCod_servicio());//codigo
								hoja.getRow(head).createCell(14).setCellValue(servicio.getEstado());//estado
								hoja.getRow(head).createCell(15).setCellValue(proyecto.getGestor_it_name());
								hoja.getRow(head).createCell(16).setCellValue(proyecto.getGestor_negocio_name());
								hoja.getRow(head).createCell(17).setCellValue(proyecto.getFecha_alta_str());
								
								hoja.getRow(head).createCell(18).setCellValue(proyecto.getClasificacion());
								hoja.getRow(head).createCell(19).setCellValue(proyecto.getCoste());
								if(proyecto.getUrl_doc_google_drive() != null){
									hoja.getRow(head).createCell(20).setCellValue(proyecto.getUrl_doc_google_drive());
								}						
								hoja.getRow(head).createCell(21).setCellValue(servicio.getObservaciones());//observaciones
								hoja.getRow(head).createCell(22).setCellValue(servicio.getFormato_intermedio());//formato intermedio
								hoja.getRow(head).createCell(23).setCellValue(servicio.getFormato_local());//formato local
								hoja.getRow(head).createCell(24).setCellValue(servicio.getReferencia_local1());//
								hoja.getRow(head).createCell(25).setCellValue(servicio.getReferencia_local2());//referencia local
								hoja.getRow(head).createCell(26).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
								hoja.getRow(head).createCell(27).setCellValue(proyecto.getStr_fecha_fin_valoracion());
								hoja.getRow(head).createCell(28).setCellValue(proyecto.getStr_envioC100());
								hoja.getRow(head).createCell(29).setCellValue(proyecto.getStr_OKNegocio());
								hoja.getRow(head).createCell(30).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
								hoja.getRow(head).createCell(31).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
								hoja.getRow(head).createCell(32).setCellValue(proyecto.getStr_fecha_plan_trabajo());
								hoja.getRow(head).createCell(33).setCellValue(proyecto.getStr_OKNegocioCheck());
								hoja.getRow(head).createCell(34).setCellValue(proyecto.getStr_fecha_disponible_conectividad());
								hoja.getRow(head).createCell(35).setCellValue(servicio.getStr_fecha_ini_pruebas());//fecha estimada pruebas
								hoja.getRow(head).createCell(36).setCellValue(servicio.getStr_fecha_fin_pruebas());//fecha estimada fin pruebas
								hoja.getRow(head).createCell(37).setCellValue("");//conectividad.getStr_fecha_ini_infraestructura());
								hoja.getRow(head).createCell(38).setCellValue("");//conectividad.getStr_fecha_fin_infraestructura());
								hoja.getRow(head).createCell(39).setCellValue("");//conectividad.getStr_fecha_implantacion());
								hoja.getRow(head).createCell(40).setCellValue("");//conectividad.getStr_fecha_ini_seguridad());
								hoja.getRow(head).createCell(41).setCellValue("");//conectividad.getStr_fecha_fin_seguridad());
								hoja.getRow(head).createCell(42).setCellValue("");//conectividad.getEstado());
								hoja.getRow(head).createCell(43).setCellValue(servicio.getStr_fecha_ini_integradas());//fecha inicio pruebas integradas
								hoja.getRow(head).createCell(44).setCellValue(servicio.getStr_fecha_fin_integradas());//fecha fin pruebas integradas
								hoja.getRow(head).createCell(45).setCellValue(servicio.getStr_fecha_ini_aceptacion());//fecha inicio pruebas aceptacion
								hoja.getRow(head).createCell(46).setCellValue(servicio.getStr_fecha_fin_aceptacion());//fecha fin pruebas aceptacion
								hoja.getRow(head).createCell(47).setCellValue(servicio.getStr_fecha_implantacion_produccion());//fehca implantacion servicio produccion
								hoja.getRow(head).createCell(48).setCellValue(servicio.getStr_fecha_ini_validacion());//fecha inicio validacion 
								hoja.getRow(head).createCell(49).setCellValue(servicio.getStr_fecha_fin_validacion());//fehca fin validacion
								hoja.getRow(head).createCell(50).setCellValue(servicio.getStr_fecha_ini_primera_operacion());//fecha inicio primera operacion
								hoja.getRow(head).createCell(51).setCellValue(servicio.getStr_fecha_fin_primera_operacion());//fehca fin primera operacion
								hoja.getRow(head).createCell(52).setCellValue(servicio.getStr_fecha_ini_op_cliente());//fecha inicio operacion cliente
								hoja.getRow(head).createCell(53).setCellValue(servicio.getStr_fecha_ANS());//fecha paso a ans
								head++;
							}
						}	
					}else{
						if(conectividades.size()!=0){
							for(Conectividad conectividad:conectividades){
								hoja.createRow(head).createCell(0).setCellValue(cliente.getNombre());
								hoja.getRow(head).createCell(1).setCellValue(cliente.getClientId());
								hoja.getRow(head).createCell(2).setCellValue(cliente.getRef_global());
								hoja.getRow(head).createCell(3).setCellValue(cliente.getTipo());
								hoja.getRow(head).createCell(4).setCellValue(proyecto.getTipo());
								hoja.getRow(head).createCell(5).setCellValue(proyecto.getCod_proyecto());
								hoja.getRow(head).createCell(6).setCellValue(proyecto.getProducto());
								hoja.getRow(head).createCell(7).setCellValue(proyecto.getSubtipo());
								hoja.getRow(head).createCell(8).setCellValue(proyecto.getConectividad());
								hoja.getRow(head).createCell(9).setCellValue(conectividad.getSeguridad());
								hoja.getRow(head).createCell(10).setCellValue("");//servicio
								hoja.getRow(head).createCell(11).setCellValue("");//pais
								hoja.getRow(head).createCell(12).setCellValue("");//extension
								hoja.getRow(head).createCell(13).setCellValue("");//codigo
								hoja.getRow(head).createCell(14).setCellValue("");//estado
								hoja.getRow(head).createCell(15).setCellValue(proyecto.getGestor_it_name());
								hoja.getRow(head).createCell(16).setCellValue(proyecto.getGestor_negocio_name());
								hoja.getRow(head).createCell(17).setCellValue(proyecto.getFecha_alta_str());
								
								hoja.getRow(head).createCell(18).setCellValue(proyecto.getClasificacion());
								hoja.getRow(head).createCell(19).setCellValue(proyecto.getCoste());
								if(proyecto.getUrl_doc_google_drive() != null){
									hoja.getRow(head).createCell(20).setCellValue(proyecto.getUrl_doc_google_drive());
								}							
								hoja.getRow(head).createCell(21).setCellValue("");//observaciones
								hoja.getRow(head).createCell(22).setCellValue("");//formato intermedio
								hoja.getRow(head).createCell(23).setCellValue("");//formato local
								hoja.getRow(head).createCell(24).setCellValue("");//referencia local integ
								hoja.getRow(head).createCell(25).setCellValue("");//referencia local
								hoja.getRow(head).createCell(26).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
								hoja.getRow(head).createCell(27).setCellValue(proyecto.getStr_fecha_fin_valoracion());
								hoja.getRow(head).createCell(28).setCellValue(proyecto.getStr_envioC100());
								hoja.getRow(head).createCell(29).setCellValue(proyecto.getStr_OKNegocio());
								hoja.getRow(head).createCell(30).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
								hoja.getRow(head).createCell(31).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
								hoja.getRow(head).createCell(32).setCellValue(proyecto.getStr_fecha_plan_trabajo());
								hoja.getRow(head).createCell(33).setCellValue(proyecto.getStr_OKNegocioCheck());
								hoja.getRow(head).createCell(34).setCellValue(proyecto.getStr_fecha_disponible_conectividad());
								hoja.getRow(head).createCell(35).setCellValue("");//fecha estimada pruebas
								hoja.getRow(head).createCell(36).setCellValue("");//fecha estimada fin pruebas
								hoja.getRow(head).createCell(37).setCellValue(conectividad.getStr_fecha_ini_infraestructura());
								hoja.getRow(head).createCell(38).setCellValue(conectividad.getStr_fecha_fin_infraestructura());
								hoja.getRow(head).createCell(39).setCellValue(conectividad.getStr_fecha_implantacion());
								hoja.getRow(head).createCell(40).setCellValue(conectividad.getStr_fecha_ini_seguridad());
								hoja.getRow(head).createCell(41).setCellValue(conectividad.getStr_fecha_fin_seguridad());
								hoja.getRow(head).createCell(42).setCellValue(conectividad.getEstado());
								hoja.getRow(head).createCell(43).setCellValue("");//fecha inicio pruebas integradas
								hoja.getRow(head).createCell(44).setCellValue("");//fecha fin pruebas integradas
								hoja.getRow(head).createCell(45).setCellValue("");//fecha inicio pruebas aceptacion
								hoja.getRow(head).createCell(46).setCellValue("");//fecha fin pruebas aceptacion
								hoja.getRow(head).createCell(47).setCellValue("");//fecha implantacion servicio produccion
								hoja.getRow(head).createCell(48).setCellValue("");//fecha inicio validacion 
								hoja.getRow(head).createCell(49).setCellValue("");//fehca fin validacion
								hoja.getRow(head).createCell(50).setCellValue("");//fecha inicio primera operacion
								hoja.getRow(head).createCell(51).setCellValue("");//fehca fin primera operacion
								hoja.getRow(head).createCell(52).setCellValue("");//fecha inicio operacion cliente
								hoja.getRow(head).createCell(53).setCellValue("");//fecha paso a ans
								head++;
							}
						}else{
							hoja.createRow(head).createCell(0).setCellValue(cliente.getNombre());
							hoja.getRow(head).createCell(1).setCellValue(cliente.getClientId());
							hoja.getRow(head).createCell(2).setCellValue(cliente.getRef_global());
							hoja.getRow(head).createCell(3).setCellValue(cliente.getTipo());
							hoja.getRow(head).createCell(4).setCellValue(proyecto.getTipo());
							hoja.getRow(head).createCell(5).setCellValue(proyecto.getCod_proyecto());
							hoja.getRow(head).createCell(6).setCellValue(proyecto.getProducto());
							hoja.getRow(head).createCell(7).setCellValue(proyecto.getSubtipo());
							hoja.getRow(head).createCell(8).setCellValue(proyecto.getConectividad());
							hoja.getRow(head).createCell(9).setCellValue("");
							hoja.getRow(head).createCell(10).setCellValue("");//servicio
							hoja.getRow(head).createCell(11).setCellValue("");//pais
							hoja.getRow(head).createCell(12).setCellValue("");//extension
							hoja.getRow(head).createCell(13).setCellValue("");//codigo
							hoja.getRow(head).createCell(14).setCellValue("");//estado
							hoja.getRow(head).createCell(15).setCellValue(proyecto.getGestor_it_name());
							hoja.getRow(head).createCell(16).setCellValue(proyecto.getGestor_negocio_name());
							hoja.getRow(head).createCell(17).setCellValue(proyecto.getFecha_alta_str());
							
							hoja.getRow(head).createCell(18).setCellValue(proyecto.getClasificacion());
							hoja.getRow(head).createCell(19).setCellValue(proyecto.getCoste());
							if(proyecto.getUrl_doc_google_drive() != null){
								hoja.getRow(head).createCell(20).setCellValue(proyecto.getUrl_doc_google_drive());
							}					
							hoja.getRow(head).createCell(21).setCellValue("");//observaciones
							hoja.getRow(head).createCell(22).setCellValue("");//formato intermedio
							hoja.getRow(head).createCell(23).setCellValue("");//formato local
							hoja.getRow(head).createCell(24).setCellValue("");//referencia local integ
							hoja.getRow(head).createCell(25).setCellValue("");//referencia local
							hoja.getRow(head).createCell(26).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
							hoja.getRow(head).createCell(27).setCellValue(proyecto.getStr_fecha_fin_valoracion());
							hoja.getRow(head).createCell(28).setCellValue(proyecto.getStr_envioC100());
							hoja.getRow(head).createCell(29).setCellValue(proyecto.getStr_OKNegocio());
							hoja.getRow(head).createCell(30).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
							hoja.getRow(head).createCell(31).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
							hoja.getRow(head).createCell(32).setCellValue(proyecto.getStr_fecha_plan_trabajo());
							hoja.getRow(head).createCell(33).setCellValue(proyecto.getStr_OKNegocioCheck());
							hoja.getRow(head).createCell(34).setCellValue(proyecto.getStr_fecha_disponible_conectividad());
							hoja.getRow(head).createCell(35).setCellValue("");//fecha estimada pruebas
							hoja.getRow(head).createCell(36).setCellValue("");//fecha estimada fin pruebas
							hoja.getRow(head).createCell(37).setCellValue("");
							hoja.getRow(head).createCell(38).setCellValue("");
							hoja.getRow(head).createCell(39).setCellValue("");
							hoja.getRow(head).createCell(40).setCellValue("");
							hoja.getRow(head).createCell(41).setCellValue("");
							hoja.getRow(head).createCell(42).setCellValue("");
							hoja.getRow(head).createCell(43).setCellValue("");//fecha inicio pruebas integradas
							hoja.getRow(head).createCell(44).setCellValue("");//fecha fin pruebas integradas
							hoja.getRow(head).createCell(45).setCellValue("");//fecha inicio pruebas aceptacion
							hoja.getRow(head).createCell(46).setCellValue("");//fecha fin pruebas aceptacion
							hoja.getRow(head).createCell(47).setCellValue("");//fehca implantacion servicio produccion
							hoja.getRow(head).createCell(48).setCellValue("");//fecha inicio validacion 
							hoja.getRow(head).createCell(49).setCellValue("");//fehca fin validacion
							hoja.getRow(head).createCell(50).setCellValue("");//fecha inicio primera operacion
							hoja.getRow(head).createCell(51).setCellValue("");//fehca fin primera operacion
							hoja.getRow(head).createCell(52).setCellValue("");//fecha inicio operacion cliente
							hoja.getRow(head).createCell(53).setCellValue("");//fecha paso a ans
							head++;
						}
					}
				}
				
				
			}
			
		}
		
		workbook.write(resp.getOutputStream());		
	}
	
	private void informePaises(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		
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
			
			hoja.getRow(head).getCell(1).setCellValue("AÃ‘O");
			hoja.getRow(head).getCell(2).setCellValue("TRIMESTRE");
			hoja.getRow(head).getCell(3).setCellValue("CLIENTE");
			hoja.getRow(head).getCell(4).setCellValue("BANCA");
			hoja.getRow(head).getCell(5).setCellValue("PRODUCTO");
			hoja.getRow(head).getCell(6).setCellValue("SERVICIOS");
			hoja.getRow(head).getCell(7).setCellValue("ETAPA");
			hoja.getRow(head).getCell(8).setCellValue("COMENTARIOS");
			
			
			List<Servicio> servicios = new ArrayList<Servicio>(); 
			
			for(Estados estado:estados){
				if(!estado.getName().equals("EXCLUIDO POR TIMEOUT")&&
						!estado.getName().equals("IMPLEMENTADO CON OK")&&
						!estado.getName().equals("IMPLEMENTADO SIN OK")&&
						!estado.getName().equals("PARADO POR NEGOCIO")&&
						!estado.getName().equals("PARADO POR IT")&&
						!estado.getName().equals("PARADO POR PRODUCTO")&&
						!estado.getName().equals("EXCLUIDO POR TIMEOUT")&&
						!estado.getName().equals("EXCLUIDO POR NEGOCIO")
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
					
					hoja.getRow(head).createCell(2).setCellStyle(bodyStyle);
					
					//////////////////////////////////////////
					////el campo fecha implantacion produccion esta vacio para la mayoria de los campos 
					/*if(servicio.getStr_fecha_implantacion_produccion()!=null&&!servicio.getStr_fecha_implantacion_produccion().equals("")){
						int mes= Integer.parseInt(servicio.getStr_fecha_implantacion_produccion().substring(3,5));
						int trimestre = (int)((mes-1)/3)+1;
						
						if(servicio.getEstado().equals("PDTE DOC ALCANCE EN GCS")||servicio.getEstado().equals("C100 EN CONFECCIÃ“N")||servicio.getEstado().equals("PDTE VALORACIÃ“N IT")
								||servicio.getEstado().equals("PDTE PLAN DE TRABAJO IT")||servicio.getEstado().equals("PDTE VISTO BUENO DEL CL DEL PLAN DE TRABAJO")
								||servicio.getEstado().equals("EN TEST - CONECTIVIDAD")||servicio.getEstado().equals("EN TEST - INTEGRACIÃ“N")){
							trimestre = trimestre+2;
						}else{
							if(servicio.getEstado().equals("EN TEST - ACEPTACIÃ“N")||servicio.getEstado().equals("EN DESARROLLO")){
								trimestre++;
							}
						}
						
						if(trimestre==5)trimestre=1;
						if(trimestre==6)trimestre=2;
						
						hoja.getRow(head).getCell(2).setCellValue(trimestre+"Âº");
					}*/
					///////////////////////////////////////////
					int mes;
					int trimestre;
					
					java.util.Date fecha = new Date();
					mes = fecha.getMonth();
					trimestre =	(int)(mes/3)+1;	
					if(servicio.getEstado().equals("PDTE DOC ALCANCE EN GCS")||servicio.getEstado().equals("C100 EN CONFECCIÃ“N")||servicio.getEstado().equals("PDTE VALORACIÃ“N IT")
							||servicio.getEstado().equals("PDTE PLAN DE TRABAJO IT")||servicio.getEstado().equals("PDTE VISTO BUENO DEL CL DEL PLAN DE TRABAJO")
							||servicio.getEstado().equals("EN TEST - CONECTIVIDAD")||servicio.getEstado().equals("EN TEST - INTEGRACIÃ“N")){
						trimestre = trimestre+2;							
					}else{
						if(servicio.getEstado().equals("EN TEST - ACEPTACIÃ“N")||servicio.getEstado().equals("EN DESARROLLO")){
							trimestre++;
						}else{
							if(servicio.getEstado().equals("PARADO POR PRODUCTO")||servicio.getEstado().equals("PARADO POR NEGOCIO")||servicio.getEstado().equals("PARADO POR IT")
									||servicio.getEstado().equals("EXCLUIDO POR NEGOCIO")||servicio.getEstado().equals("EXCLUIDO POR TIMEOUT")||servicio.getEstado().equals("EN PENNY TEST")
									||servicio.getEstado().equals("IMPLEMENTADO CON OK")||servicio.getEstado().equals("IMPLEMENTADO SIN OK")){
								trimestre = 0;
							}
						}
					}
						
					if(trimestre == 1 || trimestre == 2 || trimestre == 3 || trimestre == 4){
						hoja.getRow(head).getCell(2).setCellValue(trimestre+"Âº");
					}else{
						if(trimestre == 5 || trimestre == 6){
							hoja.getRow(head).getCell(2).setCellValue("PrÃ³ximo aÃ±o");
						}else{
							hoja.getRow(head).getCell(2).setCellValue("");
						}
					}	
						
					//////////////////////////////////////////////
										
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
			servicios.addAll(servDao.getAllServiciosByEstadoPais(pais.getName(),"IMPLEMENTADO CON OK"));
			servicios.addAll(servDao.getAllServiciosByEstadoPais(pais.getName(),"IMPLEMENTADO SIN OK"));
			
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

			
			
			hoja.getRow(head).getCell(1).setCellValue("AÃ‘O");
			hoja.getRow(head).getCell(2).setCellValue("FECHA\n IMPLANTACIÃ“N");
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
	/*
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
		hoja.getRow(head).getCell(9).setCellValue("FECHA\n INICIO\n VALORACIÃ“N");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n FIN\n VALORACIÃ“N");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n INICIO\n VIABILIDAD");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n FIN\n VIABILIDAD");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n ENVIO\n C100");
		hoja.getRow(head).getCell(10).setCellValue("FECHA\n OK\n NEGOCIO");
		
		
		
		
		
		workbook.write(resp.getOutputStream());
	}
	
	*/
	
	private void informeImplementaciones(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformeImplementacionesGCS.xlsx");
		String link= "/datadocs/templateImplementacion.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		
		Date now = new Date();
		int year = now.getYear()+1900;
		int month = now.getMonth()+1;
		
		
		
		Integer[][] dates = new Integer[4][2];
		
		if(month==1){
			dates[0][0]=11;
			dates[1][0]=12;
			dates[2][0]=1;
			dates[3][0]=10;
			dates[0][1]=year-1;
			dates[1][1]=year-1;
			dates[2][1]=year;
			dates[3][1]=year-1;
		}else{
			if(month==2){
				dates[0][0]=12;
				dates[1][0]=1;
				dates[2][0]=2;
				dates[3][0]=11;
				dates[0][1]=year-1;
				dates[1][1]=year;
				dates[2][1]=year;
				dates[3][1]=year-1;
			}else{
				if(month==3){
					dates[0][0]=month-2;
					dates[1][0]=month-1;
					dates[2][0]=month;
					dates[3][0]=12;
					dates[0][1]=year;
					dates[1][1]=year;
					dates[2][1]=year;
					dates[3][1]=year-1;
					
				}else{
					dates[3][0]=month-3;
					dates[0][0]=month-2;
					dates[1][0]=month-1;
					dates[2][0]=month;
					dates[3][1]=year;
					dates[0][1]=year;
					dates[1][1]=year;
					dates[2][1]=year;
				}
			}
		}
		
		org.apache.poi.ss.usermodel.Sheet hoja = workbook.getSheetAt(0);
		org.apache.poi.ss.usermodel.Sheet hoja2 = workbook.getSheetAt(1);
		org.apache.poi.ss.usermodel.Sheet hoja3 = workbook.getSheetAt(2);
		org.apache.poi.ss.usermodel.Sheet hoja4 = workbook.getSheetAt(3);
		
		for(int j=0;j<3;j++){
			int i =dates[j][0]-1;
			hoja.getRow(2).getCell(j+2).setCellValue(months[dates[j][0]-1]);
			hoja.getRow(14).getCell(j+2).setCellValue(months[dates[j][0]-1]);
			hoja.getRow(14).getCell(j+10).setCellValue(months[dates[j][0]-1]);

			hoja4.getRow(2).getCell(j+2).setCellValue(months[dates[j][0]-1]);
		}
		
		hoja3.getRow(3).getCell(14).setCellValue(months[dates[0][0]-1]+" - "+dates[0][1]);
		hoja3.getRow(3).getCell(20).setCellValue(months[dates[1][0]-1]+" - "+dates[1][1]);
		hoja3.getRow(3).getCell(26).setCellValue(months[dates[2][0]-1]+" - "+dates[2][1]);
		

		DemandaDao demandaDao = DemandaDao.getInstance();
		ServicioDao servicioDao = ServicioDao.getInstance();
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		
		hoja.getRow(3).getCell(2).setCellValue(demandaDao.countSolicBetweenDates("01/"+dates[0][0]+"/"+dates[0][1], diasMes[dates[0][0]-1]+"/"+dates[0][0]+"/"+dates[0][1]));
		hoja.getRow(3).getCell(3).setCellValue(demandaDao.countSolicBetweenDates("01/"+dates[1][0]+"/"+dates[1][1], diasMes[dates[1][0]-1]+"/"+dates[1][0]+"/"+dates[1][1]));
		hoja.getRow(3).getCell(4).setCellValue(demandaDao.countSolicBetweenDates("01/"+dates[2][0]+"/"+dates[2][1], diasMes[dates[2][0]-1]+"/"+dates[2][0]+"/"+dates[2][1]));
		hoja.getRow(3).getCell(5).setCellValue(demandaDao.countSolicBetweenDates("01/01/"+dates[1][1], "31/12/"+dates[1][1]));
		
		
		hoja.getRow(4).getCell(2).setCellValue(servicioDao.countSolicBetweenDates("01/"+dates[0][0]+"/"+dates[0][1], diasMes[dates[0][0]-1]+"/"+dates[0][0]+"/"+dates[0][1]));
		hoja.getRow(4).getCell(3).setCellValue(servicioDao.countSolicBetweenDates("01/"+dates[1][0]+"/"+dates[1][1], diasMes[dates[1][0]-1]+"/"+dates[1][0]+"/"+dates[1][1]));
		hoja.getRow(4).getCell(4).setCellValue(servicioDao.countSolicBetweenDates("01/"+dates[2][0]+"/"+dates[2][1], diasMes[dates[2][0]-1]+"/"+dates[2][0]+"/"+dates[2][1]));
		hoja.getRow(4).getCell(5).setCellValue(servicioDao.countSolicBetweenDates("01/01/"+dates[1][1], "31/12/"+dates[1][1]));
		
		
		String[] estados = new String[]{
				"PDTE DOC ALCANCE EN GCS",
				"ANÃ�LISIS",
				"PDTE VISTO BUENO DEL CL DEL PLAN DE TRABAJO",
				"EN DESARROLLO",
				"EN TEST - CONECTIVIDAD",
				"EN TEST - INTEGRACIÃ“N",
				"EN TEST - ACEPTACIÃ“N",
				"PDTE IMPLANTAR",
				"EN PENNY TEST",
				"PARADO POR NEGOCIO",
				"PARADO POR PRODUCTO"};
		int i = 0 ;
		for(String estado:estados){
			hoja.getRow(i+15).getCell(2).setCellValue(servicioDao.countSolicBetweenDatesAndState("01/"+dates[0][0]+"/"+dates[0][1], diasMes[dates[0][0]-1]+"/"+dates[0][0]+"/"+dates[0][1],estado));
			hoja.getRow(i+15).getCell(3).setCellValue(servicioDao.countSolicBetweenDatesAndState("01/"+dates[1][0]+"/"+dates[1][1], diasMes[dates[1][0]-1]+"/"+dates[1][0]+"/"+dates[1][1],estado));
			hoja.getRow(i+15).getCell(4).setCellValue(servicioDao.countSolicBetweenDatesAndState("01/"+dates[2][0]+"/"+dates[2][1], diasMes[dates[2][0]-1]+"/"+dates[2][0]+"/"+dates[2][1],estado));
			hoja.getRow(i+15).getCell(5).setCellValue(servicioDao.countSolicBetweenDatesAndState("01/01/"+dates[1][1], "31/12/"+dates[1][1],estado));
			i++;
		}
		
		
		int []totalesProducto = new int[]{0,0,0,0};
		String [] productos = new String[]{"SWIFT-FILEACT","H2H","","GLOBAL NETCASH"};
		
		List<Proyecto> proyectos = null;
		i = 0;
		for(String producto: productos){
			proyectos = proyectoDao.getProyectKeyForState(producto);
			for(Proyecto proyecto:proyectos){
				totalesProducto[i] += servicioDao.getServiciosByProject(proyecto.getKey().getId()).size();
			}
			i++;
		}
		
		hoja3.getRow(9).getCell(4).setCellValue(totalesProducto[0]);
		hoja3.getRow(9).getCell(5).setCellValue(totalesProducto[1]);
		hoja3.getRow(9).getCell(6).setCellValue(totalesProducto[2]);
		hoja3.getRow(9).getCell(7).setCellValue(totalesProducto[3]);
		
		String [] paises = new String[]{"ARGENTINA","COLOMBIA","PERÃš","VENEZUELA","CHILE","MÃ‰XICO","ESPAÃ‘A","FRANCIA","REINO UNIDO","USA","ITALIA"};
		i=0;
		for(String pais: paises){
			hoja3.getRow(i+19).getCell(4).setCellValue(servicioDao.countByPais(pais));
			i++;
		}
		
		
		totalesProducto = new int[]{0,0,0,0};
		
		
		proyectos = null;
		
		for(int j=0;j<3;j++){
			int k =  0;
			for(String pais: paises){
				totalesProducto = new int[]{0,0,0,0};
				i = 0;
				for(String producto: productos){
					proyectos = proyectoDao.getProyectKeyBetweenDatesAndState("01/"+dates[j][0]+"/"+dates[j][1], diasMes[dates[j][0]-1]+"/"+dates[j][0]+"/"+dates[j][1], producto);
					for(Proyecto proyecto:proyectos){
						totalesProducto[i] += servicioDao.countByProjectAndPais(proyecto.getKey().getId(),pais);
					}
					i++;
				}
				for(int w=0;w<4;w++){
					hoja3.getRow(k+5).getCell(w+14+j*6).setCellValue(totalesProducto[w]);
				}
				k++;
			}
			
		
			
		}	
		
		
		
		
		String [] tipos = new String[]{"EVOL-C","IMPL","MIGR-OB-C","PRUC"};
		i=0;
		for(String tipo:tipos){
			for(int j=0;j<3;j++){
				hoja4.getRow(3+i).getCell(j+2).setCellValue(demandaDao.countSolicBetweenDatesEstado("01/"+dates[j][0]+"/"+dates[j][1], diasMes[dates[j][0]-1]+"/"+dates[j][0]+"/"+dates[j][1], tipo));
			}
			i++;
		}
		//TODO  tabla de servicios por anio
		//hoja2.getRow(7).getCell(19).setCellValue(servicioDao.get);
		
		XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook) workbook);
		workbook.write(resp.getOutputStream());
	}
	
	
	@SuppressWarnings("deprecation")
	private void informeCoste(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformeCostesGCS.xlsx");
		String link= "/datadocs/templateCoste.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		CosteDao costeDao = CosteDao.getInstance();
		
		
		List<Proyecto> proyectos = proyectoDao.getAllProjects();
		List<Coste> costes = null; 
		
		Date dateNow = new Date();
		
//		List<int[][]> totales = new ArrayList<int[][]>();
//		int[][] totaltotal = new int [6][2];
//		int[][] totalCap = new int [6][2];
//		int[][] totalInovery = new int [6][2];
//		
//		totales.add(totaltotal);
//		totales.add(totalCap);
//		totales.add(totalInovery);
		
		double[][][] resultados = new double [3][6][2];
		for(int i = 0; i< 3;i++){
			for(int j = 0; i< 6;i++){
				for(int k = 0; i< 2;i++){
					resultados[i][j][k]= 0;
				}
			}
		}
		
CellStyle styleexce = workbook.createCellStyle();
DataFormat format = workbook.createDataFormat();

styleexce.setDataFormat(format.getFormat("dd/mm/yyyy"));
		
		org.apache.poi.ss.usermodel.Sheet hojaProy = workbook.getSheetAt(3);	
		
		org.apache.poi.ss.usermodel.Sheet hojaProv = workbook.getSheetAt(1);
		
		org.apache.poi.ss.usermodel.Sheet hojaTotal = workbook.getSheetAt(0);
		
		org.apache.poi.ss.usermodel.Sheet hojaCost = workbook.getSheetAt(2);
		
		int headProy = 1;
		int headCoste = 1;
		String fechaString="";
		
		for(Proyecto proyecto: proyectos){
			hojaProy.createRow(headProy).createCell(0).setCellValue(proyecto.getFecha_alta_str());
			hojaProy.getRow(headProy).createCell(1).setCellValue(proyecto.getCod_proyecto());
			hojaProy.getRow(headProy).createCell(2).setCellValue(proyecto.getTipo());
			hojaProy.getRow(headProy).createCell(3).setCellValue(proyecto.getClienteName());
			hojaProy.getRow(headProy).createCell(4).setCellValue(Integer.toString(proyecto.getClasificacion()));
			hojaProy.getRow(headProy).createCell(5).setCellValue(proyecto.getGestor_it_name());
			hojaProy.getRow(headProy).createCell(6).setCellValue(proyecto.getGestor_negocio_name());
			hojaProy.getRow(headProy).createCell(7).setCellValue(proyecto.getCoste()+" â‚¬");
			hojaProy.getRow(headProy).createCell(8).setCellValue(proyecto.getProducto());
			hojaProy.getRow(headProy).createCell(9).setCellValue(proyecto.getConectividad());
			hojaProy.getRow(headProy).createCell(10).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
			hojaProy.getRow(headProy).createCell(11).setCellValue(proyecto.getStr_fecha_fin_valoracion());
			hojaProy.getRow(headProy).createCell(12).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
			hojaProy.getRow(headProy).createCell(13).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
			hojaProy.getRow(headProy).createCell(14).setCellValue(proyecto.getStr_envioC100());
			hojaProy.getRow(headProy).createCell(15).setCellValue(proyecto.getStr_OKNegocio());			
			headProy++;
			Long proyec = proyecto.getKey().getId();
			costes = costeDao.getCostesByProject(proyecto.getKey().getId());
			
			
			
			for(Coste coste : costes){
				hojaCost.createRow(headCoste).createCell(0).setCellValue(proyecto.getTipo());
				hojaCost.getRow(headCoste).createCell(1).setCellValue(coste.getCliente_name());
				hojaCost.getRow(headCoste).createCell(2).setCellValue(coste.getProject_name());
				if(!coste.getNum_control().equals("")&&coste.getNum_control()!=null)
				hojaCost.getRow(headCoste).createCell(3).setCellValue(coste.getNum_control());
				hojaCost.getRow(headCoste).createCell(4).setCellValue(coste.getEquipos());
				hojaCost.getRow(headCoste).createCell(5).setCellStyle(styleexce);
				fechaString = coste.getStr_fecha_alta();
				if(fechaString.indexOf("2015")>-1){
					hojaCost.getRow(headCoste).getCell(5).setCellValue(coste.getFecha_alta());
				}else{
					hojaCost.getRow(headCoste).getCell(5).setCellValue("");
				}
				hojaCost.getRow(headCoste).createCell(6).setCellValue(coste.getGestor_it_name());
				hojaCost.getRow(headCoste).createCell(7).setCellValue(coste.getNum_valoracion());
				if(!coste.getComentarios().equals("")&&coste.getComentarios()!=null)
				hojaCost.getRow(headCoste).createCell(8).setCellValue(coste.getComentarios());
				if(coste.getStr_fecha_solicitud_valoracion()!=null)
				hojaCost.getRow(headCoste).createCell(9).setCellValue(coste.getStr_fecha_solicitud_valoracion());		
				if(coste.getStr_fecha_recepcion_valoracion()!=null)
				hojaCost.getRow(headCoste).createCell(10).setCellValue(coste.getStr_fecha_recepcion_valoracion());			
				if (!"".equals(coste.getHoras_analisis())&&coste.getHoras_analisis()!=null)
				hojaCost.getRow(headCoste).createCell(11).setCellValue(coste.getHoras_analisis());
				if (!"".equals(coste.getCoste_analisis())&&coste.getCoste_analisis()!=null)
				hojaCost.getRow(headCoste).createCell(12).setCellValue(coste.getCoste_analisis());
				hojaCost.getRow(headCoste).createCell(13).setCellValue(coste.getHoras_diseño());
				hojaCost.getRow(headCoste).createCell(14).setCellValue(coste.getCoste_diseño());
				hojaCost.getRow(headCoste).createCell(15).setCellValue(coste.getHoras_construccion());
				hojaCost.getRow(headCoste).createCell(16).setCellValue(coste.getCoste_construccion());
				hojaCost.getRow(headCoste).createCell(17).setCellValue(coste.getHoras_pruebas());
				hojaCost.getRow(headCoste).createCell(18).setCellValue(coste.getCoste_pruebas());
				hojaCost.getRow(headCoste).createCell(19).setCellValue(coste.getHoras_gestion());
				hojaCost.getRow(headCoste).createCell(20).setCellValue(coste.getCoste_gestion());
				if(coste.getHoras_total()!=null&&!coste.getHoras_total().equals(""))hojaCost.getRow(headCoste).createCell(21).setCellValue(Double.parseDouble(coste.getHoras_total()));
				if(coste.getCoste_total()!=null&&!coste.getCoste_total().equals(""))hojaCost.getRow(headCoste).createCell(22).setCellValue(Double.parseDouble(coste.getCoste_total()));
				headCoste++;
				Double costeTotal= null;
				Double horasTotales= null;
				if(coste.getHoras_total()!=null&&!coste.getHoras_total().equals(""))horasTotales = Double.parseDouble(coste.getHoras_total().replace(",", ".").replace(" ", "").trim());
				if(coste.getCoste_total()!=null&&!coste.getCoste_total().equals(""))costeTotal = Double.parseDouble(coste.getCoste_total().replace(",", ".").replace(" ", "").trim());
				
				
				if(coste.getFecha_alta().getYear()==dateNow.getYear()){
					if(costeTotal!=null){
						if(proyecto.getTipo().equals("IMPLEMENTACIÃ“N")){
							resultados[0][0][0] +=  costeTotal;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][0][0] +=  costeTotal;								
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][0][0] +=  costeTotal;
						}
						if(proyecto.getTipo().equals("EVOLUTIVO")){
							resultados[0][1][0] +=  costeTotal;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][1][0] +=  costeTotal;
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][1][0] +=  costeTotal;
						}
						if(proyecto.getTipo().equals("PRUEBA CLIENTE")){
							resultados[0][2][0] +=  costeTotal;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][2][0] +=  costeTotal;
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][3][0] +=  costeTotal;
						}
						if(proyecto.getTipo().equals("MIGRACIÃ“N")){
							resultados[0][3][0] +=  costeTotal;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][3][0] +=  costeTotal;
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][3][0] +=  costeTotal;
						}
						if(proyecto.getTipo().equals("CONSULTA")){
							resultados[0][4][0] +=  costeTotal;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][4][0] +=  costeTotal;
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][4][0] +=  costeTotal;							
						}
						if(proyecto.getTipo().equals("VIABILIDAD")){
							resultados[0][5][0] +=  costeTotal;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][5][0] +=  costeTotal;
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][5][0] +=  costeTotal;
						}
						
					}
					
					if(horasTotales!=null){
						
						
						if(proyecto.getTipo().equals("IMPLEMENTACIÃ“N")){
							resultados[0][0][1] +=  horasTotales;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][0][1] +=  horasTotales;							
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][0][1] +=  horasTotales;
						}
						if(proyecto.getTipo().equals("EVOLUTIVO")){
							resultados[0][1][1] +=  horasTotales;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][1][1] +=  horasTotales;							
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][1][1] +=  horasTotales;
						}
						if(proyecto.getTipo().equals("PRUEBA CLIENTE")){
							resultados[0][2][1] +=  horasTotales;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][2][1] +=  horasTotales;							
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][3][1] +=  horasTotales;
						}
						if(proyecto.getTipo().equals("MIGRACIÃ“N")){
							resultados[0][3][1] +=  horasTotales;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][3][1] +=  horasTotales;							
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][3][1] +=  horasTotales;
						}
						if(proyecto.getTipo().equals("CONSULTA")){
							resultados[0][4][1] +=  horasTotales;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][4][1] +=  horasTotales;							
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][4][1] +=  horasTotales;
						}
						if(proyecto.getTipo().equals("VIABILIDAD")){
							resultados[0][5][1] +=  horasTotales;
							if(coste.getEquipos().equals("CAPGEMINI"))resultados[1][5][1] +=  horasTotales;							
							if(coste.getEquipos().equals("INNOVERY"))resultados[2][5][1] +=  horasTotales;
						}
					}
				}
			}
		}
		
		
		
		for(int headtotal = 11; headtotal<17;headtotal++){
			hojaTotal.getRow(headtotal).getCell(2).setCellValue(resultados[0][headtotal-11][0]);
		}
		
		for(int headtotal = 12; headtotal<18;headtotal++){
			hojaProv.getRow(headtotal).getCell(2).setCellValue(resultados[0][headtotal-12][0]);
			hojaProv.getRow(headtotal).getCell(4).setCellValue(resultados[0][headtotal-12][1]);
		}
		
		
		for(int headtotal = 25; headtotal<31;headtotal++){
			hojaProv.getRow(headtotal).getCell(2).setCellValue(resultados[1][headtotal-25][0]);
			hojaProv.getRow(headtotal).getCell(4).setCellValue(resultados[1][headtotal-25][1]);
		}
		
		for(int headtotal = 39; headtotal<45;headtotal++){
			hojaProv.getRow(headtotal).getCell(2).setCellValue(resultados[2][headtotal-39][0]);
			hojaProv.getRow(headtotal).getCell(4).setCellValue(resultados[2][headtotal-39][1]);
		}
		
		
		
		
		
		
		workbook.write(resp.getOutputStream());
	}
	
	/*
	private void informeCarga(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformeCargaGCS.xlsx");
		String link= "/datadocs/templatePaises.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook();
		
		
		PaisDao paisDao =  PaisDao.getInstance();
		List<Pais> paises = paisDao.getAllPaises();
		
		EstadosDao estadosDao =  EstadosDao.getInstance();
		List<Estados> estados = estadosDao.getAllEstados();
		
		ClienteDao clienteDao = ClienteDao.getInstance();
		
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		
		ServicioDao servicioDao = ServicioDao.getInstance();
		
		ConectividadDao conectividadDao = ConectividadDao.getInstance();
		
		
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
		
		
		hoja.setColumnWidth(1, 10000);
		hoja.setColumnWidth(2, 7000);
		hoja.setColumnWidth(3, 7000);
		hoja.setColumnWidth(4, 7000);
		hoja.setColumnWidth(5, 7000);
		hoja.setColumnWidth(6, 7000);
		hoja.setColumnWidth(7, 7000);
		hoja.setColumnWidth(10, 4000);
		hoja.setColumnWidth(11, 4000);
		hoja.setColumnWidth(12, 10000);
		
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
		hoja.getRow(head).getCell(3).setCellValue("PDTE ValoraciÃ³n IT");
		hoja.getRow(head).getCell(4).setCellValue("En Desarrollo");
		hoja.getRow(head).getCell(5).setCellValue("En Test");
		hoja.getRow(head).getCell(6).setCellValue("En Penny Test");
		hoja.getRow(head).getCell(7).setCellValue("Total general");
		
		hoja.getRow(head).createCell(10).setCellStyle(headerBlue);
		hoja.getRow(head).createCell(11).setCellStyle(headerGreen);
		
		hoja.getRow(head).getCell(10).setCellValue("TOT CURSO");
		hoja.getRow(head).getCell(11).setCellValue("AsignaciÃ³n");
		
		
		head++;
		UserDao userDao = UserDao.getInstance();
		List<User> gestores = userDao.getUsersByPermisoStr(3);
		
		
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
			
			int[] resultados = servicioDao.getServiciosForExcelGestor(String.valueOf(gestor.getKey().getId()));
			
			hoja.getRow(head).getCell(1).setCellValue(gestor.getNombre()+" " +gestor.getApellido1()+" "+gestor.getApellido2());
			hoja.getRow(head).getCell(2).setCellValue(resultados[0]);
			hoja.getRow(head).getCell(3).setCellValue(resultados[1]);
			hoja.getRow(head).getCell(4).setCellValue(resultados[2]);
			hoja.getRow(head).getCell(5).setCellValue(resultados[3]);
			hoja.getRow(head).getCell(6).setCellValue(resultados[4]);
			hoja.getRow(head).getCell(7).setCellFormula("SUM(B"+head+":G"+head+")");
			
			hoja.getRow(head).getCell(10).setCellValue(resultados[5]);
			
			hoja.getRow(head).getCell(12).setCellValue(gestor.getNombre()+" " +gestor.getApellido1()+" "+gestor.getApellido2());
			head++;
		}
		
		
		
		
		
		workbook.write(resp.getOutputStream());
	}*/
	
	private void informeCarga(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformeCargaGCS.xlsx");
		String link= "/datadocs/templateCarga.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		ServicioDao serviciosDao = ServicioDao.getInstance();
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		//List<Servicio> servicios = serviciosDao.getAllServicios();
		List<Servicio> servicios = new ArrayList<Servicio>();
		org.apache.poi.ss.usermodel.Sheet hoja = workbook.getSheetAt(0);
		
		int head = 1;
		
		List<Proyecto> proyectos = proyectoDao.getAllProjects();
		for(Proyecto proyecto: proyectos){
			servicios = serviciosDao.getServiciosByProject(proyecto.getKey().getId());
			for(Servicio servicio:servicios){
				
				
				
				
				hoja.createRow(head).createCell(0).setCellValue(proyecto.getFecha_alta_str());
				hoja.getRow(head).createCell(1).setCellValue(proyecto.getTipo());
				hoja.getRow(head).createCell(2).setCellValue(servicio.getCliente_name());
				hoja.getRow(head).createCell(3).setCellValue(proyecto.getClasificacion());
				hoja.getRow(head).createCell(4).setCellValue(proyecto.getGestor_it_name());
				hoja.getRow(head).createCell(5).setCellValue(proyecto.getGestor_negocio_name());
				hoja.getRow(head).createCell(6).setCellValue(proyecto.getCoste());
				hoja.getRow(head).createCell(7).setCellValue(proyecto.getProducto());
				hoja.getRow(head).createCell(8).setCellValue(proyecto.getConectividad());
				hoja.getRow(head).createCell(9).setCellValue(proyecto.getStr_fecha_inicio_valoracion());
				hoja.getRow(head).createCell(10).setCellValue(proyecto.getStr_fecha_fin_valoracion());
				hoja.getRow(head).createCell(11).setCellValue(proyecto.getStr_fecha_inicio_viabilidad());
				hoja.getRow(head).createCell(12).setCellValue(proyecto.getStr_fecha_fin_viabilidad());
				hoja.getRow(head).createCell(13).setCellValue(proyecto.getStr_envioC100());
				hoja.getRow(head).createCell(14).setCellValue(proyecto.getStr_OKNegocio());
				hoja.getRow(head).createCell(15).setCellValue(proyecto.getCod_proyecto());
				hoja.getRow(head).createCell(16).setCellValue(servicio.getPais());
				hoja.getRow(head).createCell(17).setCellValue(servicio.getServicio());
				hoja.getRow(head).createCell(18).setCellValue(servicio.getEstado());
				hoja.getRow(head).createCell(19).setCellValue(servicio.getCod_servicio());
				hoja.getRow(head).createCell(20).setCellValue(servicio.getObservaciones());
				hoja.getRow(head).createCell(21).setCellValue(servicio.getFormato_intermedio());
				hoja.getRow(head).createCell(22).setCellValue(servicio.getFormato_local());
				hoja.getRow(head).createCell(23).setCellValue(servicio.getReferencia_local1());
				hoja.getRow(head).createCell(24).setCellValue(servicio.getReferencia_local2());
				hoja.getRow(head).createCell(25).setCellValue(servicio.getStr_fecha_ini_integradas());
				hoja.getRow(head).createCell(26).setCellValue(servicio.getStr_fecha_fin_integradas());
				hoja.getRow(head).createCell(27).setCellValue(servicio.getStr_fecha_ini_aceptacion());
				hoja.getRow(head).createCell(28).setCellValue(servicio.getStr_fecha_fin_aceptacion());
				hoja.getRow(head).createCell(29).setCellValue(servicio.getStr_fecha_ini_validacion());
				hoja.getRow(head).createCell(30).setCellValue(servicio.getStr_fecha_fin_validacion());
				hoja.getRow(head).createCell(31).setCellValue(servicio.getStr_fecha_implantacion_produccion());
				hoja.getRow(head).createCell(32).setCellValue(servicio.getStr_fecha_ini_primera_operacion());
				hoja.getRow(head).createCell(33).setCellValue(servicio.getStr_fecha_fin_primera_operacion());
				hoja.getRow(head).createCell(34).setCellValue(servicio.getStr_fecha_ini_op_cliente());
				hoja.getRow(head).createCell(35).setCellValue(servicio.getStr_fecha_ANS());
				hoja.getRow(head).createCell(36).setCellValue(servicio.getStr_fecha_ini_pruebas());
				hoja.getRow(head).createCell(37).setCellValue(servicio.getStr_fecha_fin_pruebas());
				head++;
			}
		}

		hoja = workbook.getSheetAt(2);
		
		
		//Estilo de celda del encabezado
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
		
		head=1;
		
		
		
		hoja.addMergedRegion(new CellRangeAddress(head,head,1,7));
		
		
		hoja.setColumnWidth(1, 10000);
		hoja.setColumnWidth(2, 7000);
		hoja.setColumnWidth(3, 7000);
		hoja.setColumnWidth(4, 7000);
		hoja.setColumnWidth(5, 7000);
		hoja.setColumnWidth(6, 7000);
		hoja.setColumnWidth(7, 7000);
		hoja.setColumnWidth(10, 4000);
		hoja.setColumnWidth(11, 4000);
		hoja.setColumnWidth(12, 10000);
		
		hoja.createRow(head);
		hoja.getRow(head).setHeightInPoints(20);
		hoja.getRow(head).createCell(1).setCellStyle(headerBlue);
		hoja.getRow(head).getCell(1).setCellValue("NÂº Servicios activos (en curso) por Gestor 2015");;
		
		head++;
		/*
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
		*/
		
		List<List<String>> tabla = new ArrayList<List<String>>();
		
		UserDao userDao = UserDao.getInstance();
		ServicioDao servicioDao = ServicioDao.getInstance();
		List<User> gestores = userDao.getUsersByPermisoStr(3);
		List<Servicio> serviciosUser = null;
		int filas = 0;
		for(User gestor:gestores){
			serviciosUser = servicioDao.getServiciosByGestorIT(String.valueOf(gestor.getKey().getId()));
			for(Servicio servicio:serviciosUser){
//				if(tabla.get(filas).contains(servicio.gets)){
//					
//				}
			}
			
			
			filas++;
		}

		
		
		
//		hoja.createRow(head);
//		hoja.getRow(head).createCell(1).setCellStyle(headerBlue);
//		hoja.getRow(head).createCell(2).setCellStyle(headerBlue);
//		hoja.getRow(head).createCell(3).setCellStyle(headerBlue);
//		hoja.getRow(head).createCell(4).setCellStyle(headerBlue);
//		hoja.getRow(head).createCell(5).setCellStyle(headerBlue);
//		hoja.getRow(head).createCell(6).setCellStyle(headerBlue);
//		hoja.getRow(head).createCell(7).setCellStyle(headerBlue);
//		
//		hoja.getRow(head).getCell(1).setCellValue("Etiquetas de fila");
//		hoja.getRow(head).getCell(2).setCellValue("PDTE Doc Alcance en GCS");
//		hoja.getRow(head).getCell(3).setCellValue("PDTE ValoraciÃ³n IT");
//		hoja.getRow(head).getCell(4).setCellValue("En Desarrollo");
//		hoja.getRow(head).getCell(5).setCellValue("En Test");
//		hoja.getRow(head).getCell(6).setCellValue("En Penny Test");
//		hoja.getRow(head).getCell(7).setCellValue("Total general");
//		
//		hoja.getRow(head).createCell(10).setCellStyle(headerBlue);
//		hoja.getRow(head).createCell(11).setCellStyle(headerGreen);
//		
//		hoja.getRow(head).getCell(10).setCellValue("TOT CURSO");
//		hoja.getRow(head).getCell(11).setCellValue("AsignaciÃ³n");
//		
//		
//		head++;

		
		/*
		String sheetName = workbook.getSheetName(0);
		Name rangeTable = workbook.getName("Entradas");
		rangeTable.setRefersToFormula(sheetName+"!$A$"+2+":$AL$"+head);
		*/
		
		
		workbook.write(resp.getOutputStream());		
	}
}