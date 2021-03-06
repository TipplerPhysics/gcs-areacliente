package com.gcs.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.gcs.beans.Coste;
import com.gcs.beans.Pais;
import com.gcs.beans.Proyecto;
import com.gcs.beans.Servicio;
import com.gcs.beans.ServicioFile;
import com.gcs.beans.User;
import com.gcs.dao.ClienteDao;
import com.gcs.dao.CosteDao;
import com.gcs.dao.PaisDao;
import com.gcs.dao.ProyectoDao;
import com.gcs.dao.ServicioDao;
import com.gcs.dao.ServicioFileDao;
import com.gcs.dao.UserDao;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ServicioServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1879747622689943880L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		String accion = req.getParameter("accion");
		
		HttpSession sesion = req.getSession();
		
		String usermail = (String)sesion.getAttribute("usermail");
		
		try {
		
			if ("new".equals(accion)){				
				createService(req,resp,usermail);				
			}else if ("getServicesByCountry".equals(accion)){
				getServicesByCountry(req,resp);
			}else if ("delete".equals(accion)){
				deleteServicio(req,resp,usermail);
			}else if ("update".equals(accion)){
				updateServicio(req,resp,usermail);
			}else if ("xls".equals(accion)){
				generateXLS(req,resp);
			}else if ("getExtensionesByService".equals(accion)){
				getExtensionesByService(req,resp);
			}else if ("getProjectsByClient".equals(accion)){
				getProjectsByClient(req,resp);
			}else if ("clone".equals(accion)){
				cloneService(req,resp,usermail);
			}
		
		} catch (IOException | JSONException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=ServiciosGCS.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());
			
			ServicioDao sDao = ServicioDao.getInstance();
			List<Servicio> servicios = sDao.getAllServicios();
			
			WritableSheet s = w.createSheet("Servicios", 0);
		
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.WHITE);
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.BLUE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);			
			
		    s.setColumnView(0, 20);
		    s.setColumnView(1, 11);
		    s.setColumnView(2, 20);
		    s.setColumnView(3, 30);
		    s.setColumnView(4, 30);
		    s.setColumnView(5, 30);
		    s.setColumnView(6, 30);
		    s.setColumnView(7, 20);
		    s.setColumnView(8, 30);
		    s.setColumnView(9, 30);
		    s.setColumnView(10, 20);
		    
		    s.setColumnView(11, 30);
		    s.setColumnView(12, 30);
		    s.setColumnView(13, 30);
		    s.setColumnView(14, 30);
		    s.setColumnView(15, 30);
		    s.setColumnView(16, 30);
		    s.setColumnView(17, 30);
		    s.setColumnView(18, 30);
		    s.setColumnView(19, 30);
		    s.setColumnView(20, 30);
		    
		    //s.setColumnView(23, 35);
		    //s.setColumnView(24, 35);
		    
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "COD. PROYECTO",cellFormat));
			s.addCell(new Label(1, 0, "BEI",cellFormat));
			s.addCell(new Label(2, 0, "PAIS",cellFormat));
			s.addCell(new Label(3, 0, "SERVICIO",cellFormat));
			s.addCell(new Label(4, 0, "EXTENSIÓN",cellFormat));
			s.addCell(new Label(5, 0, "FLUJO",cellFormat));
			s.addCell(new Label(6, 0, "ESTADO",cellFormat));
			s.addCell(new Label(7, 0, "GESTOR PRUEBAS",cellFormat));
			s.addCell(new Label(8, 0, "COD. REDMINE",cellFormat));
			s.addCell(new Label(9, 0, "OBSERVACIONES",cellFormat));
			s.addCell(new Label(10, 0, "FORMATO INTERMEDIO",cellFormat));
			s.addCell(new Label(11, 0, "FORMATO LOCAL",cellFormat));
			
			s.addCell(new Label(12, 0, "REF. LOCAL",cellFormat));
			s.addCell(new Label(13, 0, "REF. LOCAL INTEGRADO",cellFormat));
			s.addCell(new Label(14, 0, "TIPO DESARROLLO",cellFormat));
			s.addCell(new Label(15, 0, "ESCENARIO OPI",cellFormat));
			
			
			s.addCell(new Label(16, 0, "FECHA INICIO INTEGRADAS",cellFormat));
			s.addCell(new Label(17, 0, "FECHA FIN ACEPTACIÓN",cellFormat));
			s.addCell(new Label(18, 0, "FECHA IMPLANTACION-PRODUCCIÓN",cellFormat));
			
			s.addCell(new Label(19, 0, "FECHA INICIO PRIMERA OPERACIÓN",cellFormat));
			s.addCell(new Label(20, 0, "FECHA FIN PRIMERA OPERACIÓN",cellFormat));
			//s.addCell(new Label(23, 0, "FECHA MIGRACION CHANNELING",cellFormat));
			//s.addCell(new Label(24, 0, "FECHA MIGRACION INFRAESTRUCTURA",cellFormat));

			
						
			
			
			int aux=1;
			
			
			
			for (Servicio serv:servicios){
				
								
				s.addCell(new Label(0, aux, serv.getCod_proyecto()));
				s.addCell(new Label(1, aux, serv.getCod_bei()));
				s.addCell(new Label(2, aux, serv.getPais()));
				s.addCell(new Label(3, aux, serv.getServicio()));
				s.addCell(new Label(4, aux, serv.getExtension() ));
				s.addCell(new Label(5, aux, serv.getFlujo() ));
				s.addCell(new Label(6, aux, serv.getEstado()));
				s.addCell(new Label(7, aux, serv.getGestor_pruebas_name()));
				s.addCell(new Label(8, aux, serv.getCod_servicio()));
				s.addCell(new Label(9, aux, serv.getObservaciones()));
				s.addCell(new Label(10, aux, serv.getFormato_intermedio()));
				s.addCell(new Label(11, aux, serv.getFormato_local()));
				
				s.addCell(new Label(12, aux, serv.getReferencia_local1() ));
				s.addCell(new Label(13, aux, serv.getReferencia_local2() ));
				s.addCell(new Label(14, aux, serv.getTipo_desarrollo() ));
				s.addCell(new Label(15, aux, serv.getEscenario_opi() ));
				s.addCell(new Label(16, aux, serv.getStr_fecha_ini_integradas() ));
				s.addCell(new Label(17, aux, serv.getStr_fecha_fin_aceptacion() ));
				s.addCell(new Label(18, aux, serv.getStr_fecha_implantacion_produccion() ));
				s.addCell(new Label(19, aux, serv.getStr_fecha_ini_primera_operacion() ));
				s.addCell(new Label(20, aux, serv.getStr_fecha_fin_primera_operacion() ));
				//s.addCell(new Label(23, aux, serv.getStr_migracion_channeling() ));
				//s.addCell(new Label(24, aux, serv.getStr_migracion_infra() ));
				
				
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
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		doPost(req,resp);
	}
	
	public void updateServicio(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException{
		String id = req.getParameter("id");
		JSONObject json = new JSONObject();
		
		ServicioDao sDao = ServicioDao.getInstance();
		Servicio s = sDao.getServicioById(Long.parseLong(id));
		
		try{
			//String project_id = req.getParameter("cod_proyecto");
			

			//Proyecto p = pDao.getProjectbyId(Long.parseLong(project_id));
			
			String estado = req.getParameter("estado");
			String servicio = req.getParameter("servicio");			
			
			String cod_servicio = req.getParameter("cod_servicio");
			
			String observaciones = req.getParameter("observaciones");
			String formato_intermedio = req.getParameter("formato_intermedio");		
			String formato_local = req.getParameter("formato_local");
			String gestor_pruebas_key = req.getParameter("gestor_pruebas");
			
			
			String referencia_local1 = req.getParameter("ref_local1");
			String referencia_local2 = req.getParameter("ref_local2");
			
			String str_fecha_ini_integradas = req.getParameter("fecha_inicio_integradas");
			String str_fecha_fin_integradas = req.getParameter("fecha_fin_integradas");
			
			String str_fecha_ini_aceptacion = req.getParameter("fecha_inicio_aceptacion");
			String str_fecha_fin_aceptacion = req.getParameter("fecha_fin_aceptacion");

			String str_fecha_ini_validacion = req.getParameter("fecha_inicio_validacion");
			String str_fecha_fin_validacion = req.getParameter("fecha_fin_validacion");
			
			String str_fecha_implantacion_prod = req.getParameter("fecha_implantacion_produccion");
			
			String str_fecha_ini_primera_op = req.getParameter("fecha_inicio_primera_op");
			String str_fecha_fin_primera_op = req.getParameter("fecha_fin_primera_op");

			String str_fecha_inicio_op_cliente = req.getParameter("fecha_inicio_op_cliente");
			String str_fecha_ans = req.getParameter("ans");
			
			
			String str_fecha_inicio_pruebas = req.getParameter("fecha_inicio_pruebas");
			String str_fecha_fin_pruebas = req.getParameter("fecha_fin_pruebas");
			if (!gestor_pruebas_key.equals("default")){
				UserDao uDao = new UserDao();
				User user = uDao.getUserbyId((Long.parseLong(gestor_pruebas_key)));
				String gestor_pruebas_name = user.getFullName();
				s.setGestor_pruebas_name(gestor_pruebas_name);
			}
			else{
				gestor_pruebas_key="0";
				String gestor_pruebas_name ="";
				s.setGestor_pruebas_name(gestor_pruebas_name);
			}
			
			/*CosteDao cDao = CosteDao.getInstance();
			List<Coste> coste = cDao.select();
			cDao.update(coste);*/
			//Descomentar para crear servicio
			/*long pais_id = 5640928240861184L;
			String name = "SALDOS Y MOVIMIENTOS";
			ArrayList<String> extensiones = new ArrayList<String>();
			extensiones.add(".MT940");
			
			ServicioFile imp = new ServicioFile();
			imp.setExtensiones(extensiones);
			imp.setName(name);
			imp.setPaisId(pais_id);
			ServicioFileDao sfDao = ServicioFileDao.getInstance();
			sfDao.createServicioFile(imp);*/
		
			
		    //String str_fecha_mig_channeling = req.getParameter("fecha_mig_channeling");
			//String str_fecha_mig_infraestructura = req.getParameter("fecha_mig_infraestructura");
			String extension = req.getParameter("extension");
			String pais = req.getParameter("pais");
			String cod_bei = req.getParameter("cod_bei");
			String tipo_desarrollo = req.getParameter("tipo_desarrollo");
			String escenario_opi = req.getParameter("escenario_opi");
			String flujo = req.getParameter("flujo");

			
			//s.setId_proyecto(p.getKey().getId());
			//s.setCod_proyecto(p.getCod_proyecto());
			s.setPais(pais);
			
			s.setServicio(servicio);
			s.setEstado(estado);
			s.setCod_servicio(cod_servicio);
			
			s.setObservaciones(observaciones);
			s.setFormato_intermedio(formato_intermedio);
			s.setFormato_local(formato_local);
			
			s.setGestor_pruebas_key(Long.valueOf(gestor_pruebas_key));
			s.setReferencia_local1(referencia_local1);
			s.setReferencia_local2(referencia_local2);
			
			s.setStr_fecha_ini_integradas(str_fecha_ini_integradas);
			s.setStr_fecha_fin_integradas(str_fecha_fin_integradas);
			
			s.setStr_fecha_ini_aceptacion(str_fecha_ini_aceptacion);
			s.setStr_fecha_fin_aceptacion(str_fecha_fin_aceptacion);
			
			s.setStr_fecha_ini_validacion(str_fecha_ini_validacion);
			s.setStr_fecha_fin_validacion(str_fecha_fin_validacion);
			
			s.setStr_fecha_implantacion_produccion(str_fecha_implantacion_prod);
			
			s.setStr_fecha_ini_primera_operacion(str_fecha_ini_primera_op);
			s.setStr_fecha_fin_primera_operacion(str_fecha_fin_primera_op);
			
			s.setStr_fecha_ini_op_cliente(str_fecha_inicio_op_cliente);
			s.setStr_fecha_ANS(str_fecha_ans);
			
			s.setStr_fecha_ini_pruebas(str_fecha_inicio_pruebas);
			s.setStr_fecha_fin_pruebas(str_fecha_fin_pruebas);
			s.setCod_bei(cod_bei);
			if (!"default".equals(tipo_desarrollo)) {
				s.setTipo_desarrollo(tipo_desarrollo);
				}
			
			if (!"default".equals(escenario_opi)) {
				s.setEscenario_opi(escenario_opi);
				}
			
			if (!"default".equals(flujo)) {
				s.setFlujo(flujo);
				}
			
			//s.setStr_migracion_channeling(str_fecha_mig_channeling);
			//s.setStr_migracion_infra(str_fecha_mig_infraestructura);
			if(extension == null ){extension = "";}
			s.setExtension(extension);
			
			
			sDao.createServicio(s,usermail);
			json.append("success", "true");
			
		}catch (Exception e){
			e.printStackTrace();
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
	private void deleteServicio(HttpServletRequest req, HttpServletResponse resp, String usermail){
		String id = req.getParameter("id");
		
		ServicioDao sDao = ServicioDao.getInstance();
		Servicio s = sDao.getServicioById(Long.parseLong(id));
		sDao.deleteServicio(s,usermail);
		
		try{
		
			JSONObject json = new JSONObject();
			json.append("success", "true");
			
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<ServicioFile> getServicesByCountryJSON(Pais pais) throws JSONException{

		
		ServicioFileDao servicioFileDao = ServicioFileDao.getInstance();
		List<ServicioFile> servicioFiles = servicioFileDao.getAllServiciosForPais(pais);
		
		return servicioFiles;
		
		/*ArrayList<Config> servicios = new ArrayList<>();
		
		ArrayList<String> result = new ArrayList<String>();
		
			switch(pais){
				case "Argentina":
					servicios = StaticConfig.servicios_argentina; 
					for (int a =0; a<=servicios.size()-1; a++){						
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "Bélgica":
					servicios = StaticConfig.servicios_belgica; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "Chile":
					servicios = StaticConfig.servicios_chile; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "Colombia":
					servicios = StaticConfig.servicios_colombia; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "España":
					servicios = StaticConfig.servicios_espania; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "UK":
					servicios = StaticConfig.servicios_uk; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "México":
					servicios = StaticConfig.servicios_mexico; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "EEUU":
					servicios = StaticConfig.servicios_eeuu; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "Francia":
					servicios = StaticConfig.servicios_francia; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "Perú":
					servicios = StaticConfig.servicios_peru; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "Portugal":
					servicios = StaticConfig.servicios_portugal; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;
				case "Venezuela":
					servicios = StaticConfig.servicios_venezuela; 
					for (int a =0; a<=servicios.size()-1; a++){
						result.add(servicios.get(a).getValue());
					}				
					break;		
			}*/

		
	}
	private void getProjectsByClient(HttpServletRequest req, HttpServletResponse resp){
		String client_str = req.getParameter("client");
		long idClient = Long.parseLong(client_str);
		ProyectoDao proyectoDao = ProyectoDao.getInstance();
		List<Proyecto> proyectos = proyectoDao.getProjectsByClient(idClient);
		
				
		try{
		
			
			JSONObject json = new JSONObject();
			JSONArray jarray = new JSONArray();
			
			for (Proyecto o:proyectos){
				jarray.put(o.getKey().getId());
				jarray.put(o.getCod_proyecto());
			}
			
			json.append("success","true");
			json.append("proyectos", jarray);
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getServicesByCountry(HttpServletRequest req, HttpServletResponse resp){
		String pais_str = req.getParameter("pais");
		PaisDao paisDao = PaisDao.getInstance();
		Pais pais = paisDao.getPaisByName(pais_str);
				
		try{
		
			List<ServicioFile> list = getServicesByCountryJSON(pais);
			JSONObject json = new JSONObject();
			JSONArray jarray = new JSONArray();
			
			for (ServicioFile o:list){
				jarray.put(o.getKey().getId());
				jarray.put(o.getName());
				jarray.put(o.getPaisId());
				jarray.put(o.getExtensiones());
			}
			
			json.append("success","true");
			json.append("servicios", jarray);
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	private void getExtensionesByService(HttpServletRequest req, HttpServletResponse resp){
		String service_str = req.getParameter("service");
		String pais_str = req.getParameter("pais");
		
		//long idService = Long.parseLong(service_str);
		PaisDao paisDao = PaisDao.getInstance();
		Pais paisenty = paisDao.getPaisByName(pais_str);
		ServicioFileDao servDao = ServicioFileDao.getInstance();
		ServicioFile servicio = servDao.getServicioFileByNamePais(service_str,paisenty.getKey().getId());
		ArrayList<String> extensiones = servicio.getExtensiones();  
		try{
		
			JSONObject json = new JSONObject();
			JSONArray jarray = new JSONArray();
			
			for (String o:extensiones){
				jarray.put(o);
			}
			
			json.append("success","true");
			json.append("extensiones", jarray);
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	private void createService(HttpServletRequest req, HttpServletResponse resp, String usermail) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		
		try{
			String project_id = req.getParameter("cod_proyecto");
			
			ProyectoDao pDao = ProyectoDao.getInstance(); 
			Proyecto p = pDao.getProjectbyId(Long.parseLong(project_id));
			
			String servicio = req.getParameter("servicio");
			String estado = req.getParameter("estado");
			String cod_servicio = req.getParameter("cod_servicio");
			String gestor_pruebas_key = req.getParameter("gestor_pruebas");
			
			String observaciones = req.getParameter("observaciones");
			String formato_intermedio = req.getParameter("formato_intermedio");		
			String formato_local = req.getParameter("formato_local");
			
			String referencia_local1 = req.getParameter("ref_local1");
			String referencia_local2 = req.getParameter("ref_local2");
			
			String str_fecha_ini_integradas = req.getParameter("fecha_inicio_integradas");
			String str_fecha_fin_integradas = req.getParameter("fecha_fin_integradas");
			
			String str_fecha_ini_aceptacion = req.getParameter("fecha_inicio_aceptacion");
			String str_fecha_fin_aceptacion = req.getParameter("fecha_fin_aceptacion");

			String str_fecha_ini_validacion = req.getParameter("fecha_inicio_validacion");
			String str_fecha_fin_validacion = req.getParameter("fecha_fin_validacion");
			
			String str_fecha_implantacion_prod = req.getParameter("fecha_implantacion_produccion");
			
			String str_fecha_ini_primera_op = req.getParameter("fecha_inicio_primera_op");
			String str_fecha_fin_primera_op = req.getParameter("fecha_fin_primera_op");

			String str_fecha_inicio_op_cliente = req.getParameter("fecha_inicio_op_cliente");
			String str_fecha_ans = req.getParameter("ans");
			
			String str_fecha_inicio_pruebas = req.getParameter("fecha_inicio_pruebas");
			String str_fecha_fin_pruebas = req.getParameter("fecha_fin_pruebas");
			
			
			//String str_fecha_mig_channeling = req.getParameter("fecha_mig_channeling");
			//String str_fecha_mig_infraestructura = req.getParameter("fecha_mig_infraestructura");
			String extension = req.getParameter("extension");
			String pais = req.getParameter("pais");
			String cod_bei = req.getParameter("cod_bei");
			String tipo_desarrollo = req.getParameter("tipo_desarrollo");
			String escenario_opi = req.getParameter("escenario_opi");
			String flujo = req.getParameter("flujo");

			Servicio s = new Servicio();
			s.setId_proyecto(p.getKey().getId());
			s.setCod_proyecto(p.getCod_proyecto());
			s.setPais(pais);
			if (!gestor_pruebas_key.equals("default")){
				UserDao uDao = new UserDao();
				User user = uDao.getUserbyId((Long.parseLong(gestor_pruebas_key)));
				String gestor_pruebas_name = user.getFullName();
				s.setGestor_pruebas_name(gestor_pruebas_name);
				s.setGestor_pruebas_key(Long.parseLong(gestor_pruebas_key));
			}
			else{
				gestor_pruebas_key="0";
				String gestor_pruebas_name ="";
				s.setGestor_pruebas_name(gestor_pruebas_name);
				s.setGestor_pruebas_key((Long.parseLong(gestor_pruebas_key)));
			}
			
			
			s.setServicio(servicio);
			s.setEstado(estado);
			s.setCod_servicio(cod_servicio);
			
			s.setObservaciones(observaciones);
			s.setFormato_intermedio(formato_intermedio);
			s.setFormato_local(formato_local);
			s.setReferencia_local1(referencia_local1);
			s.setReferencia_local2(referencia_local2);
			
			s.setStr_fecha_ini_integradas(str_fecha_ini_integradas);
			s.setStr_fecha_fin_integradas(str_fecha_fin_integradas);
			
			s.setStr_fecha_ini_aceptacion(str_fecha_ini_aceptacion);
			s.setStr_fecha_fin_aceptacion(str_fecha_fin_aceptacion);
			
			s.setStr_fecha_ini_validacion(str_fecha_ini_validacion);
			s.setStr_fecha_fin_validacion(str_fecha_fin_validacion);
			
			s.setStr_fecha_implantacion_produccion(str_fecha_implantacion_prod);
			
			s.setStr_fecha_ini_primera_operacion(str_fecha_ini_primera_op);
			s.setStr_fecha_fin_primera_operacion(str_fecha_fin_primera_op);
			
			s.setStr_fecha_ini_op_cliente(str_fecha_inicio_op_cliente);
			s.setStr_fecha_ANS(str_fecha_ans);
			
			s.setStr_fecha_ini_pruebas(str_fecha_inicio_pruebas);
			s.setStr_fecha_fin_pruebas(str_fecha_fin_pruebas);
			if (!"default".equals(tipo_desarrollo)) {
				s.setTipo_desarrollo(tipo_desarrollo);
				}
			
			if (!"default".equals(escenario_opi)) {
				s.setEscenario_opi(escenario_opi);
				}
			
			if (!"default".equals(flujo)) {
				s.setFlujo(flujo);
				}
			
			//s.setStr_migracion_channeling(str_fecha_mig_channeling);
			//s.setStr_migracion_infra(str_fecha_mig_infraestructura);
			s.setExtension(extension);
			s.setCod_bei(cod_bei);
			
			
			
			
			ServicioDao sDao = ServicioDao.getInstance();
			sDao.createServicio(s,usermail);
			json.append("success", "true");
			
		}catch (Exception e){
			e.printStackTrace();
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");
		}
		
		
		
		
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
	
private void cloneService(HttpServletRequest req, HttpServletResponse resp, String usermail) throws IOException, JSONException{
		
		JSONObject json = new JSONObject();
		
		try{
			String project_id = req.getParameter("cod_proyecto");
			
			ProyectoDao pDao = ProyectoDao.getInstance(); 
			Proyecto p = pDao.getProjectsByCode(project_id).get(0);
			
			String servicio = req.getParameter("servicio");
			String estado = req.getParameter("estado");
			String cod_servicio = req.getParameter("cod_servicio");
			
			String observaciones = req.getParameter("observaciones");
			String formato_intermedio = req.getParameter("formato_intermedio");		
			String formato_local = req.getParameter("formato_local");
			
			
			String referencia_local1 = req.getParameter("ref_local1");
			String referencia_local2 = req.getParameter("ref_local2");
			
			String str_fecha_ini_integradas = req.getParameter("fecha_inicio_integradas");
			String str_fecha_fin_integradas = req.getParameter("fecha_fin_integradas");
			
			String str_fecha_ini_aceptacion = req.getParameter("fecha_inicio_aceptacion");
			String str_fecha_fin_aceptacion = req.getParameter("fecha_fin_aceptacion");

			String str_fecha_ini_validacion = req.getParameter("fecha_inicio_validacion");
			String str_fecha_fin_validacion = req.getParameter("fecha_fin_validacion");
			
			String str_fecha_implantacion_prod = req.getParameter("fecha_implantacion_produccion");
			
			String str_fecha_ini_primera_op = req.getParameter("fecha_inicio_primera_op");
			String str_fecha_fin_primera_op = req.getParameter("fecha_fin_primera_op");

			String str_fecha_inicio_op_cliente = req.getParameter("fecha_inicio_op_cliente");
			String str_fecha_ans = req.getParameter("ans");
			
			String str_fecha_inicio_pruebas = req.getParameter("fecha_inicio_pruebas");
			String str_fecha_fin_pruebas = req.getParameter("fecha_fin_pruebas");
			//String gestor_pruebas_key = req.getParameter("gestor_pruebas");
			
			//String str_fecha_mig_channeling = req.getParameter("fecha_mig_channeling");
			//String str_fecha_mig_infraestructura = req.getParameter("fecha_mig_infraestructura");
			String extension = req.getParameter("extension");
			String pais = req.getParameter("pais");
			String cod_bei = req.getParameter("cod_bei");
			String tipo_desarrollo = req.getParameter("tipo_desarrollo");
			String escenario_opi = req.getParameter("escenario_opi");
			String flujo = req.getParameter("flujo");

			Servicio s = new Servicio();
			s.setId_proyecto(p.getKey().getId());
			s.setCod_proyecto(p.getCod_proyecto());
			s.setPais(pais);
			
			s.setServicio(servicio);
			s.setEstado(estado);
			s.setCod_servicio(cod_servicio);
			
			s.setObservaciones(observaciones);
			s.setFormato_intermedio(formato_intermedio);
			s.setFormato_local(formato_local);
			s.setReferencia_local1(referencia_local1);
			s.setReferencia_local2(referencia_local2);
			
			s.setStr_fecha_ini_integradas(str_fecha_ini_integradas);
			s.setStr_fecha_fin_integradas(str_fecha_fin_integradas);
			
			s.setStr_fecha_ini_aceptacion(str_fecha_ini_aceptacion);
			s.setStr_fecha_fin_aceptacion(str_fecha_fin_aceptacion);
			
			s.setStr_fecha_ini_validacion(str_fecha_ini_validacion);
			s.setStr_fecha_fin_validacion(str_fecha_fin_validacion);
			
			s.setStr_fecha_implantacion_produccion(str_fecha_implantacion_prod);
			
			s.setStr_fecha_ini_primera_operacion(str_fecha_ini_primera_op);
			s.setStr_fecha_fin_primera_operacion(str_fecha_fin_primera_op);
			
			s.setStr_fecha_ini_op_cliente(str_fecha_inicio_op_cliente);
			s.setStr_fecha_ANS(str_fecha_ans);
			
			s.setStr_fecha_ini_pruebas(str_fecha_inicio_pruebas);
			s.setStr_fecha_fin_pruebas(str_fecha_fin_pruebas);
			if (!"default".equals(tipo_desarrollo)) {
				s.setTipo_desarrollo(tipo_desarrollo);
				}
			
			if (!"default".equals(escenario_opi)) {
				s.setEscenario_opi(escenario_opi);
				}
			
			if (!"default".equals(flujo)) {
				s.setFlujo(flujo);;
				}
			/*s.setGestor_pruebas_key((Long.parseLong(gestor_pruebas_key)));
			
			if(gestor_pruebas_key != null){
				UserDao uDao = new UserDao();
				User user = uDao.getUserbyId((Long.parseLong(gestor_pruebas_key)));
				String gestor_pruebas_name = user.getFullName();
				s.setGestor_pruebas_name(gestor_pruebas_name);
			}
			*/
			//s.setStr_migracion_channeling(str_fecha_mig_channeling);
			//s.setStr_migracion_infra(str_fecha_mig_infraestructura);
			s.setExtension(extension);
			s.setCod_bei(cod_bei);
			
			ServicioDao sDao = ServicioDao.getInstance();
			sDao.createServicio(s,usermail);
			json.append("success", "true");
			
		}catch (Exception e){
			e.printStackTrace();
			json.append("failure", "true");
			json.append("error", "Se ha producido un error inesperado");
		}
		
		
		
		
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.getWriter().println(json);
	}
}
