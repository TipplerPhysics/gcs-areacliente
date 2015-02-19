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
	
	public static Date dateConverterComplete(String cadena) throws ParseException{
		DateFormat formatter = null;
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date convertedDate = (Date) formatter.parse(cadena);
        
        return convertedDate;
	}
	
	public static String dateConverterToStr(Date fecha){
		int anio = (fecha.getYear() + 1900);
		int mes = (fecha.getMonth()+1);
		int dia = fecha.getDate();
		
		String convertedDate = "";
		if(dia<10){
			convertedDate = "0"+dia + "/" + mes+"/" + anio;
		}
		if(mes<10){
			convertedDate = dia + "/0" + mes+"/" + anio;
		}
		if(dia<10&&mes<10){
			convertedDate = "0"+dia + "/0" + mes+"/" + anio;
		}
		if(dia>=10&&mes>=10){
			convertedDate = dia + "/" + mes+"/" + anio;
		}
        return convertedDate;
	}
	
	public static String dateConverterToStrHour(Date fecha){
		
		int horas = fecha.getHours();
		int minutos = fecha.getMinutes();
		String convertedDate = "";
		if(horas<10){
			convertedDate = "0"+horas + ":" + minutos;
		}
		if(minutos<10){
			convertedDate = horas + ":0" + minutos;
		}
		if(horas<10&&minutos<10){
			convertedDate = "0"+horas + ":0" + minutos;
		}
		if(horas>=10&&minutos>=10){
			convertedDate = horas + ":" + minutos;
		}
		
		
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
