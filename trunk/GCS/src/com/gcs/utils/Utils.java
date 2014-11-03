package com.gcs.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gcs.beans.Log;
import com.gcs.beans.User;
import com.gcs.dao.LogsDao;
import com.gcs.dao.UserDao;

public class Utils {
	
	public static void writeLog(String usermail, String accion, String entidad, String nombre_entidad){
		
		if (!"".equals(usermail)){
			Log log = new Log();
			LogsDao lDao = LogsDao.getInstance();
				
			UserDao uDao = UserDao.getInstance();
			
			User u = uDao.getUserByMail(usermail);
			
			log.setNombre_entidad(nombre_entidad);
			log.setAccion(accion);
			log.setEntidad(entidad);
			log.setUsuario(u.getNombre() + " " + u.getApellido1() + " " + u.getApellido2());
			log.setUsuario_mail(u.getEmail());
			
			
			lDao.createLog(log);
		}
	
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

	public static String getPermisoStr(int permiso){
		String s = "";
		
		switch(permiso){
		case 1: s="Admin";
			break;
		case 2: s="Gestor Demanda";
			break;
		case 3: s="Gestor IT";
			break;
		case 4: s="Gestor Negocio";
			break;
		case 5: s="Consulta";
			break;
		}
		
		return s;
	}
	
	public static Date dateConverter(String cadena) throws ParseException{
		DateFormat formatter = null;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = (Date) formatter.parse(cadena);
        
        return convertedDate;
	}
	
	public static String dateConverterToStr(Date fecha){
		
		String convertedDate = fecha.getDate() + "/" + (fecha.getMonth()+1) + "/" + (fecha.getYear() + 1900);
		
        return convertedDate;
	}
	
public static String CalendarConverterToStr(Calendar fecha){
	  
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      
      String fecha_str =  dateFormat.format(fecha.getTime());
      
      return fecha_str;
	}
	
	
	
	public static List<String> getHorasList(){
		
		List<String> listaHoras = new ArrayList();
		
		for (Integer a=0; a<24; a++){
			if (a.toString().length()==1){
				listaHoras.add("0"+a.toString());
			}else{
				listaHoras.add(a.toString());
			}
		}
		
		return listaHoras;
	}
	
public static List<String> getMinutosList(){
		
		List<String> listaMinutos = new ArrayList();
		
		for (Integer a=0; a<60; a=a+5){
			if (a.toString().length()==1){
				listaMinutos.add("0"+a.toString());
			}else{
				listaMinutos.add(a.toString());
			}
		}
		
		return listaMinutos;
	}

	
}
