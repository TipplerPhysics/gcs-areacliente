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
					new Config("IT C&IB - CTO - Soluciones T�cnicas", "IT C&IB - CTO - Soluciones T�cnicas"),
					new Config("IT C&IB - CTO - Arquitectura Funcional", "IT C&IB - CTO - Arquitectura Funcional"),
					new Config("IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)", "IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)"),
					new Config("IT C&IB - Control y Gesti�n", "IT C&IB - Control y Gesti�n"),
					new Config("IT C&IB - E- commerce C&IB", "IT C&IB - E- commerce C&IB"),
					new Config("IT C&IB - GCC Lending GTB & CFO", "IT C&IB - GCC Lending GTB & CFO"),
					new Config("IT C&IB - GTB - Global Customer Solutions", "IT C&IB - GTB - Global Customer Solutions"),
					new Config("IT C&IB - Global Transactional Product", "IT C&IB - Global Transactional Product"),
					new Config("IT C&IB - B2B Global Support", "IT C&IB - B2B Global Support")
					));	
	
	static public ArrayList<Config> servicios_argentina =
			new ArrayList<Config>(Arrays.asList(
					new Config("Saldos y movimientos", "Saldos y movimientos"),
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias", "Transferencias"),
					new Config("N�minas", "N�minas"),
					new Config("D�bito Directo", "D�bito Directo"),
					new Config("Publicaci�n de deuda de recaudos (Cliente-Banco)", "Publicaci�n de deuda de recaudos (Cliente-Banco)"),
					new Config("Custodia de t�tulos", "Custodia de t�tulos"),
					new Config("Recaudaciones Hist�rico", "Recaudaciones Hist�rico"),
					new Config("Recaudo Deuda Publi", "Recaudo Deuda Publi"),
					new Config("Recaudo FMT Extendido", "Recaudo FMT Extendido"),
					new Config("Publicaci�n Recaudadora CUR", "Publicaci�n Recaudadora CUR"),
					new Config("Informaci�n de cheques", "Informaci�n de cheques")
					));
	
	static public ArrayList<Config> servicios_belgica =
			new ArrayList<Config>(Arrays.asList(
					new Config("Saldos y movimientos", "Saldos y movimientos"),
					new Config("Pagos domesticos - bruselas", "Pagos domesticos - bruselas"),
					new Config("Pago internacionales - bruselas", "Pago internacionales - bruselas")					
					));
	
	static public ArrayList<Config> servicios_chile =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias Dom�sticas", "Transferencias Dom�sticas"),
					new Config("N�minas", "N�minas")					
					));
	
	static public ArrayList<Config> servicios_colombia =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias", "Transferencias"),
					new Config("Recaudos", "Recaudos"),
					new Config("N�minas", "N�minas")					
					));
	
	static public ArrayList<Config> servicios_espania =
			new ArrayList<Config>(Arrays.asList(
					new Config("Devol. recibos en fichero", "Devol. recibos en fichero"),
					new Config("Recibos domiciliados", "Recibos domiciliados"),
					new Config("Devol.efectos descontados en fichero", "Devol.efectos descontados en fichero"),
					new Config("Efectos al descuento", "Efectos al descuento"),	
					new Config("N�minas", "N�minas"),	
					new Config("Transferencias dom�sticas", "Transferencias dom�sticas"),	
					new Config("Cheques", "Cheques"),	
					new Config("Transferencias urgentes (OMF)", "Transferencias urgentes (OMF)"),	
					new Config("N�minas en fichero 34.1", "N�minas en fichero 34.1"),	
					new Config("Cheques en fichero 34.1", "Cheques en fichero 34.1"),	
					new Config("Transferencias en fichero 34.1", "Transferencias en fichero 34.1"),	
					new Config("Transferencia sepa 34,12", "Transferencia sepa 34,12"),	
					new Config("Transferencias EUR (FIT)", "Transferencias EUR (FIT)"),	
					new Config("N�minas (FIM)", "N�minas (FIM)"),	
					new Config("Saldos y movimientos", "Saldos y movimientos"),	
					new Config("Anticipos de credito", "Anticipos de credito"),	
					new Config("Devoluciones de antic.cred.via fichero", "Devoluciones de antic.cred.via fichero"),	
					new Config("Saldos y movimientos", "Saldos y movimientos"),	
					new Config("Pagos certificados", "Pagos certificados"),	
					new Config("Confirming", "Confirming"),
					new Config("Pagos domiciliados", "Pagos domiciliados"),
					new Config("Informaci�n facturas factoring", "Informaci�n facturas factoring"),
					new Config("Factoring", "Factoring"),
					new Config("Confirming en divisa", "Confirming en divisa"),
					new Config("Informaci�n l�nea confirming", "Informaci�n l�nea confirming"),
					new Config("Mvtos.tarjeta cr�dito en fichero", "Mvtos.tarjeta cr�dito en fichero"),
					new Config("Transferencias Internacionales", "Transferencias Internacionales"),
					new Config("Euroconfirming", "Euroconfirming"),
					new Config("Adeudos directos C19.14 SEPA", "Adeudos directos C19.14 SEPA"),
					new Config("C19.14 Cancelaciones/Retrocesiones SEPA", "C19.14 Cancelaciones/Retrocesiones SEPA"),
					new Config("Impagados Cuaderno 19.14 (SEPA)", "Impagados Cuaderno 19.14 (SEPA)"),
					new Config("Adeudos directos C19.44 SEPA", "Adeudos directos C19.44 SEPA"),
					new Config("C19.44 Cancelaciones/Retrocesiones SEPA", "C19.44 Cancelaciones/Retrocesiones SEPA"),
					new Config("Impag.Cuaderno 19.44 (SEPA B2B)", "Impag.Cuaderno 19.44 (SEPA B2B)"),
					new Config("Mvtos.del d�a en fichero (MT942)", "Mvtos.del d�a en fichero (MT942)"),
					new Config("Infor Facturaci�n Comercios en fichero", "Infor Facturaci�n Comercios en fichero")				
					));
	
	static public ArrayList<Config> servicios_uk =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pagos chaps-tesoreria mismo d�a londres", "Pagos chaps-tesoreria mismo d�a londres"),
					new Config("Pagos nacionales londres", "Pagos nacionales londres"),
					new Config("Pagos n�minas bacs londres", "Pagos n�minas bacs londres"),
					new Config("Pagos internacionales londres", "Pagos internacionales londres"),
					new Config("Transferencias SEPA Londres", "Transferencias SEPA Londres"),
					new Config("Mvtos. Ctas. Pers. en Fichero", "Mvtos. Ctas. Pers. en Fichero")
					));
	
	static public ArrayList<Config> servicios_mexico =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores (SIT)", "Pago a Proveedores (SIT)"),
					new Config("N�minas", "N�minas"),
					new Config("Protecci�n de Cheques", "Protecci�n de Cheques"),
					new Config("Domiciliaciones", "Domiciliaciones"),			
					new Config("ABC Proveedores (crediproveedores/Confirming)", "ABC Proveedores (crediproveedores/Confirming)"),	
					new Config("ABC Documentos (crediproveedores/Confirming)", "ABC Documentos (crediproveedores/Confirming)"),	
					new Config("CREDPRO CONSULTA SCRED DOCUMENTOS", "CREDPRO CONSULTA SCRED DOCUMENTOS"),	
					new Config("CREDIPRO CONSULTA RED PROPROVEEDORES", "CREDIPRO CONSULTA RED PROPROVEEDORES"),	
					new Config("CREDIPRO CONSULTA DE COMISIONES DRE COM", "CREDIPRO CONSULTA DE COMISIONES DRE COM")
					));
	
	static public ArrayList<Config> servicios_eeuu =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pagos (payment) - n.y.", "Pagos (payment) - n.y."),
					new Config("Pagos Dom�sticos ACH NY", "Pagos Dom�sticos ACH NY"),
					new Config("Saldos y movimientos", "Saldos y movimientos")
					));
	
	static public ArrayList<Config> servicios_francia =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago n�minas - Paris", "Pago n�minas - Paris"),
					new Config("Mvtos. Ctas. Pers. en Fichero", "Mvtos. Ctas. Pers. en Fichero"),
					new Config("Saldos y movimientos- ETEBAC", "Saldos y movimientos- ETEBAC"),
					new Config("Transferencias tesoreria - paris", "Transferencias tesoreria - paris"),
					new Config("Pago proveedores - paris", "Pago proveedores - paris"),
					new Config("SEPA Par�s", "SEPA Par�s"),
					new Config("N�minas SEPA Par�s", "N�minas SEPA Par�s"),
					new Config("Pagos internacionales - paris", "Pagos internacionales - paris")
					
					
					));
	
	static public ArrayList<Config> servicios_peru =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias Domesticas", "Transferencias Domesticas"),
					new Config("N�minas", "N�minas"),
					new Config("Factoring", "Factoring"),
					new Config("Transferencias Internacionales (TEX Macug)", "Transferencias Internacionales (TEX Macug)"),
					new Config("TEC Macug (confirmaci�n de trf. Int.)/CONFIRMACION MCUG", "TEC Macug (confirmaci�n de trf. Int.)/CONFIRMACION MCUG")
				));
	

	static public ArrayList<Config> servicios_portugal =
			new ArrayList<Config>(Arrays.asList(
					new Config("Transferencias Domesticas", "Transferencias Domesticas"),
					new Config("Ret. Tra. domestic", "Ret. Tra. domestic"),
					new Config("N�minas (ordenados)", "N�minas (ordenados)"),
					new Config("Ret. Ordenados", "Ret. Ordenados"),
					new Config("Transferencias Dom�sticas Urgentes", "Transferencias Dom�sticas Urgentes"),
					new Config("Transferencias SEPA", "Transferencias SEPA"),
					new Config("Retrocesiones Transferencias SEPA", "Retrocesiones Transferencias SEPA"),
					new Config("N�minas SEPA", "N�minas SEPA"),
					new Config("Retrocesiones N�minas SEPA", "Retrocesiones N�minas SEPA"),
					new Config("Transferencias urgentes", "Transferencias urgentes"),
					new Config("TRF Urgentes (retorno)", "TRF Urgentes (retorno)"),
					new Config("N�minas urgentes", "N�minas urgentes"),
					new Config("Transferencias Internacionales", "Transferencias Internacionales"),
					new Config("Retorno TRF Intern", "Retorno TRF Intern"),
					new Config("Confirming", "Confirming"),
					new Config("Retorno confirming", "Retorno confirming"),
					new Config("SEPA DD - Rejei�oes", "SEPA DD - Rejei�oes"),
					new Config("SEPA DD - Devolu��es", "SEPA DD - Devolu��es"),
					new Config("SEPA DD - Reembolsos", "SEPA DD - Reembolsos"),
					new Config("SEPA DD - Devolu�ao Reversoes", "SEPA DD - Devolu�ao Reversoes"),
					new Config("Saldos y movimientos", "Saldos y movimientos")
				));
	
	static public ArrayList<Config> servicios_venezuela =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias", "Transferencias"),
					new Config("N�minas", "N�minas")					
				));
	
	
}
