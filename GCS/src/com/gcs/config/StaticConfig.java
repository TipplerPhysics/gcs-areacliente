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
					new Config("Negocio-Global Customer Service (Incluye HDR)", "Negocio-Global Customer Service (Incluye HDR)"),
					new Config("Negocio-Global Product", "Negocio-Global Product"),
					new Config("Negocio-Global Sales", "Negocio-Global Sales"),
					new Config("IT C&IB - CTO - Soluciones Técnicas", "IT C&IB - CTO - Soluciones Técnicas"),
					new Config("IT C&IB - CTO - Arquitectura Funcional", "IT C&IB - CTO - Arquitectura Funcional"),
					new Config("IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)", "IT C&IB - CTO - Operaciones y Soporte (Sop Swift, CAU)"),
					new Config("IT C&IB - Control y Gestión", "IT C&IB - Control y Gestión"),
					new Config("IT C&IB - E- commerce C&IB", "IT C&IB - E- commerce C&IB"),
					new Config("IT C&IB - GCC Lending GTB & CFO", "IT C&IB - GCC Lending GTB & CFO"),
					new Config("IT C&IB-GTB-Global Customer Solutions ", "IT C&IB-GTB-Global Customer Solutions "),
					new Config("IT C&IB - Global Transactional Product", "IT C&IB - Global Transactional Product"),
					new Config("IT C&IB - B2B Global Support", "IT C&IB - B2B Global Support")
					));	
	
	static public ArrayList<Config> servicios_argentina =
			new ArrayList<Config>(Arrays.asList(
					new Config("Saldos y movimientos", "Saldos y movimientos"),
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias", "Transferencias"),
					new Config("Nóminas", "Nóminas"),
					new Config("Débito Directo", "Débito Directo"),
					new Config("Publicación de deuda de recaudos (Cliente-Banco)", "Publicación de deuda de recaudos (Cliente-Banco)"),
					new Config("Custodia de títulos", "Custodia de títulos"),
					new Config("Recaudaciones Histórico", "Recaudaciones Histórico"),
					new Config("Recaudo Deuda Publi", "Recaudo Deuda Publi"),
					new Config("Recaudo FMT Extendido", "Recaudo FMT Extendido"),
					new Config("Publicación Recaudadora CUR", "Publicación Recaudadora CUR"),
					new Config("Información de cheques", "Información de cheques")
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
					new Config("Transferencias Domésticas", "Transferencias Domésticas"),
					new Config("Nóminas", "Nóminas")					
					));
	
	static public ArrayList<Config> servicios_colombia =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias", "Transferencias"),
					new Config("Recaudos", "Recaudos"),
					new Config("Nóminas", "Nóminas")					
					));
	
	static public ArrayList<Config> servicios_espania =
			new ArrayList<Config>(Arrays.asList(
					new Config("Devol. recibos en fichero", "Devol. recibos en fichero"),
					new Config("Recibos domiciliados", "Recibos domiciliados"),
					new Config("Devol.efectos descontados en fichero", "Devol.efectos descontados en fichero"),
					new Config("Efectos al descuento", "Efectos al descuento"),	
					new Config("Nóminas", "Nóminas"),	
					new Config("Transferencias domésticas", "Transferencias domésticas"),	
					new Config("Cheques", "Cheques"),	
					new Config("Transferencias urgentes (OMF)", "Transferencias urgentes (OMF)"),	
					new Config("Nóminas en fichero 34.1", "Nóminas en fichero 34.1"),	
					new Config("Cheques en fichero 34.1", "Cheques en fichero 34.1"),	
					new Config("Transferencias en fichero 34.1", "Transferencias en fichero 34.1"),	
					new Config("Transferencia sepa 34,12", "Transferencia sepa 34,12"),	
					new Config("Transferencias EUR (FIT)", "Transferencias EUR (FIT)"),	
					new Config("Nóminas (FIM)", "Nóminas (FIM)"),	
					new Config("Saldos y movimientos", "Saldos y movimientos"),	
					new Config("Anticipos de credito", "Anticipos de credito"),	
					new Config("Devoluciones de antic.cred.via fichero", "Devoluciones de antic.cred.via fichero"),	
					new Config("Saldos y movimientos", "Saldos y movimientos"),	
					new Config("Pagos certificados", "Pagos certificados"),	
					new Config("Confirming", "Confirming"),
					new Config("Pagos domiciliados", "Pagos domiciliados"),
					new Config("Información facturas factoring", "Información facturas factoring"),
					new Config("Factoring", "Factoring"),
					new Config("Confirming en divisa", "Confirming en divisa"),
					new Config("Información línea confirming", "Información línea confirming"),
					new Config("Mvtos.tarjeta crédito en fichero", "Mvtos.tarjeta crédito en fichero"),
					new Config("Transferencias Internacionales", "Transferencias Internacionales"),
					new Config("Euroconfirming", "Euroconfirming"),
					new Config("Adeudos directos C19.14 SEPA", "Adeudos directos C19.14 SEPA"),
					new Config("C19.14 Cancelaciones/Retrocesiones SEPA", "C19.14 Cancelaciones/Retrocesiones SEPA"),
					new Config("Impagados Cuaderno 19.14 (SEPA)", "Impagados Cuaderno 19.14 (SEPA)"),
					new Config("Adeudos directos C19.44 SEPA", "Adeudos directos C19.44 SEPA"),
					new Config("C19.44 Cancelaciones/Retrocesiones SEPA", "C19.44 Cancelaciones/Retrocesiones SEPA"),
					new Config("Impag.Cuaderno 19.44 (SEPA B2B)", "Impag.Cuaderno 19.44 (SEPA B2B)"),
					new Config("Mvtos.del día en fichero (MT942)", "Mvtos.del día en fichero (MT942)"),
					new Config("Infor Facturación Comercios en fichero", "Infor Facturación Comercios en fichero")				
					));
	
	static public ArrayList<Config> servicios_uk =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pagos chaps-tesoreria mismo día londres", "Pagos chaps-tesoreria mismo día londres"),
					new Config("Pagos nacionales londres", "Pagos nacionales londres"),
					new Config("Pagos nóminas bacs londres", "Pagos nóminas bacs londres"),
					new Config("Pagos internacionales londres", "Pagos internacionales londres"),
					new Config("Transferencias SEPA Londres", "Transferencias SEPA Londres"),
					new Config("Mvtos. Ctas. Pers. en Fichero", "Mvtos. Ctas. Pers. en Fichero")
					));
	
	static public ArrayList<Config> servicios_mexico =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores (SIT)", "Pago a Proveedores (SIT)"),
					new Config("Nóminas", "Nóminas"),
					new Config("Protección de Cheques", "Protección de Cheques"),
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
					new Config("Pagos Domésticos ACH NY", "Pagos Domésticos ACH NY"),
					new Config("Saldos y movimientos", "Saldos y movimientos")
					));
	
	static public ArrayList<Config> servicios_francia =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago nóminas - Paris", "Pago nóminas - Paris"),
					new Config("Mvtos. Ctas. Pers. en Fichero", "Mvtos. Ctas. Pers. en Fichero"),
					new Config("Saldos y movimientos- ETEBAC", "Saldos y movimientos- ETEBAC"),
					new Config("Transferencias tesoreria - paris", "Transferencias tesoreria - paris"),
					new Config("Pago proveedores - paris", "Pago proveedores - paris"),
					new Config("CANCEL./RETR. CORE (SEPA) PARIS", "CANCEL./RETR. CORE (SEPA) PARIS"),
					new Config("ADEUDOS CORE (SEPA) PARIS", "ADEUDOS CORE (SEPA) PARIS"),
					new Config("ADEUDOS B2B (SEPA) PARIS", "ADEUDOS B2B (SEPA) PARIS"),
					new Config("SEPA París", "SEPA París"),
					new Config("Nóminas SEPA París", "Nóminas SEPA París"),
					new Config("Pagos internacionales - paris", "Pagos internacionales - paris")
					));
	
	static public ArrayList<Config> servicios_peru =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias Domesticas", "Transferencias Domesticas"),
					new Config("Nóminas", "Nóminas"),
					new Config("Factoring", "Factoring"),
					new Config("Transferencias Internacionales (TEX Macug)", "Transferencias Internacionales (TEX Macug)"),
					new Config("TEC Macug (confirmación de trf. Int.)/CONFIRMACION MCUG", "TEC Macug (confirmación de trf. Int.)/CONFIRMACION MCUG")
				));
	

	static public ArrayList<Config> servicios_portugal =
			new ArrayList<Config>(Arrays.asList(
					new Config("Transferencias Domesticas", "Transferencias Domesticas"),
					new Config("Ret. Tra. domestic", "Ret. Tra. domestic"),
					new Config("Nóminas (ordenados)", "Nóminas (ordenados)"),
					new Config("Ret. Ordenados", "Ret. Ordenados"),
					new Config("Transferencias Domésticas Urgentes", "Transferencias Domésticas Urgentes"),
					new Config("Transferencias SEPA", "Transferencias SEPA"),
					new Config("Retrocesiones Transferencias SEPA", "Retrocesiones Transferencias SEPA"),
					new Config("Nóminas SEPA", "Nóminas SEPA"),
					new Config("Retrocesiones Nóminas SEPA", "Retrocesiones Nóminas SEPA"),
					new Config("Transferencias urgentes", "Transferencias urgentes"),
					new Config("TRF Urgentes (retorno)", "TRF Urgentes (retorno)"),
					new Config("Nóminas urgentes", "Nóminas urgentes"),
					new Config("Transferencias Internacionales", "Transferencias Internacionales"),
					new Config("Retorno TRF Intern", "Retorno TRF Intern"),
					new Config("Confirming", "Confirming"),
					new Config("Retorno confirming", "Retorno confirming"),
					new Config("SEPA DD - Rejeiçoes", "SEPA DD - Rejeiçoes"),
					new Config("SEPA DD - Devoluções", "SEPA DD - Devoluções"),
					new Config("SEPA DD - Reembolsos", "SEPA DD - Reembolsos"),
					new Config("SEPA DD - Devoluçao Reversoes", "SEPA DD - Devoluçao Reversoes"),
					new Config("Saldos y movimientos", "Saldos y movimientos")
				));
	
	static public ArrayList<Config> servicios_venezuela =
			new ArrayList<Config>(Arrays.asList(
					new Config("Pago a Proveedores", "Pago a Proveedores"),
					new Config("Transferencias", "Transferencias"),
					new Config("Nóminas", "Nóminas")					
				));
	
	
}
