package com.gcs.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String getPermisoStr(int permiso){
		String s = "";
		
		switch(permiso){
		case 1: s="Super";
			break;
		case 2: s="App Admin";
			break;
		case 3: s="User Admin";
			break;
		case 4: s="Gestor Demanda";
			break;
		case 5: s="Gestor IT";
			break;
		}
		
		return s;
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