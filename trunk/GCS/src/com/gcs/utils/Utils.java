package com.gcs.utils;

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
}
