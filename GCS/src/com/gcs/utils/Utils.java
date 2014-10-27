package com.gcs.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gcs.beans.Log;
import com.gcs.dao.LogsDao;

public class Utils {
	
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

	public static void writeLog (){
		
		Log log = new Log();
		LogsDao lDao = LogsDao.getInstance();
		Date fecha = new Date();
		
		log.setFecha(fecha);
		
		
		
	}

}
