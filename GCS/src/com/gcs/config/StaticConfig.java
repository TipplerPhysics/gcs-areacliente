package com.gcs.config;

import java.util.ArrayList;
import java.util.Arrays;

public class StaticConfig {

	static public ArrayList<Config> permisos =
			new ArrayList<Config>(Arrays.asList(
					new Config("5", "Consulta"),
					new Config("4", "Gestor Negocio"),
					new Config("3", "Gestor IT"),
					new Config("2", "Gestor Demanda"),
					new Config("1", "Admin")));
	
	static public ArrayList<Config> departamentos =
			new ArrayList<Config>(Arrays.asList(
					new Config("Negocio - Global Customer Service (Incluye HDR)", "Negocio - Global Customer Service (Incluye HDR)"),
					new Config("Negocio - Global Product", "Negocio - Global Product"),
					new Config("Negocio - Global Sales", "Negocio - Global Sales"),
					new Config("IT C&IB - CTO - Soluciones T&eacute;cnicas", "IT C&IB - CTO - Soluciones T&eacute;cnicas"),
					new Config("IT C&IB - CTO - Arquitectura Funcional", "IT C&IB - CTO - Arquitectura Funcional"),
					new Config("IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)", "IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)"),
					new Config("IT C&IB - Control y Gesti&oacute;n", "IT C&IB - Control y Gesti&oacute;n"),
					new Config("IT C&IB - E- commerce C&IB", "IT C&IB - E- commerce C&IB"),
					new Config("IT C&IB - GCC Lending GTB & CFO", "IT C&IB - GCC Lending GTB & CFO"),
					new Config("IT C&IB - GTB - Global Customer Solutions", "IT C&IB - GTB - Global Customer Solutions"),
					new Config("IT C&IB - Global Transactional Product", "IT C&IB - Global Transactional Product"),
					new Config("IT C&IB - B2B Global Support", "IT C&IB - B2B Global Support")
					));	
}
