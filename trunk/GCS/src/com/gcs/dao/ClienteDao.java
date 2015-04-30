package com.gcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.gcs.beans.Cliente;
import com.gcs.beans.Proyecto;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;



public class ClienteDao {

	public static final int DATA_SIZE = 10;
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static ClienteDao getInstance() {
		return new ClienteDao();
	}
	
	public void createClienteRaw(Cliente c) {
		String nombre = c.getNombre();
		String letra = nombre.substring(0,1).toUpperCase();
		String nombreMayus = letra + nombre.substring(1,nombre.length());
		c.setErased(false);
		c.setNombre(nombreMayus);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(c);
		} finally {
			pm.close();
		}
		
	}
	public void initCounters() {
		ClienteDao clienteDao = ClienteDao.getInstance();
		List<Cliente> clientes = clienteDao.getAllClientes();
		for(Cliente cliente : clientes){
			cliente.setProject_number(1);
			clienteDao.createClienteRaw(cliente);
		}
	}

	public void createCliente(Cliente c, String usermail) {
		ContadorClienteDao ccDao = ContadorClienteDao.getInstance();
		Integer cont = ccDao.getContadorValue();
		
		String nombre = c.getNombre();
		String letra = nombre.substring(0,1).toUpperCase();
		String nombreMayus = letra + nombre.substring(1,nombre.length());
		
		c.setNombre(nombreMayus);
		
		boolean isNewClient = false;
		if (c.getKey()==null){
			
			c.setClientId("IDGLOBAL"+ String.format("%04d", cont));
			isNewClient = true;
			c.setErased(false);
		}
		
		
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		

		try {
			pm.makePersistent(c);
		} finally {
			pm.close();
			if (isNewClient)
			ccDao.increaseCont();
			
			if (!usermail.equals("")){
				if (c.isErased()){
					Utils.writeLog(usermail, "Eliminó", "Cliente", c.getNombre());
				}else{
					if (isNewClient)
						Utils.writeLog(usermail, "Creó", "Cliente", c.getNombre());
					else{
						Utils.writeLog(usermail, "Editó", "Cliente", c.getNombre());
					}
				}
			}
			
			
			
		}
	}
	
	public Cliente getClienteByName(String name) {

		Cliente c = new Cliente();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Cliente.class.getName()
				+ " where nombre  == :p1";

		List<Cliente> clientes = (List<Cliente>) pManager.newQuery(queryStr).execute(name);

		if (!clientes.isEmpty()) {
			c = clientes.get(0);
		} else {
			c = null;
		}

		transaction.commit();
		pManager.close();

		return c;
	}
	
	
	public List<Cliente> getClientesByName(String name) {

		Cliente c = new Cliente();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Cliente.class.getName()+ " where nombre== :p1";

		List<Cliente> clientes = (List<Cliente>) pManager.newQuery(queryStr).execute(name);

		return clientes;
	}
	
	
	public List<Cliente> getClientesByNameLow(String name) {

		Cliente c = new Cliente();

		ClienteDao cliDao = ClienteDao.getInstance();
		List<Cliente> clientes = cliDao.getAllClientes();
		List<Cliente> clientesRev = new ArrayList<Cliente>();
		
		for (Cliente cl:clientes){
			if (cl.getNombre().toLowerCase().equals(name.toLowerCase())){
				clientesRev.add(cl);
			}
		}
		

		return clientesRev;
	}

	public Cliente getClienteByRefGlobal(String ref) {

		Cliente c = new Cliente();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Cliente.class.getName()
				+ " where ref_global  == :p1";

		List<Cliente> clientes = (List<Cliente>) pManager.newQuery(queryStr).execute(ref);

		if (!clientes.isEmpty()) {
			c = clientes.get(0);
		} else {
			c = null;
		}

		transaction.commit();
		pManager.close();

		return c;

	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getAllClientes() {

		List<Cliente> clientes;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("select from " + Cliente.class.getName());		
		q.setOrdering("fecha_alta_cliente desc");
		clientes = (List<Cliente>) q.execute();
		
		pm.close();

		return clientes;
	}
	
	
public List<Cliente> getAllNonDeletedClients(){
		

		List<Cliente> clients_jdo = getAllClientes();
		List<Cliente> clients = new ArrayList<Cliente>();
		
		
		for (Cliente c:clients_jdo)
			if (!c.isErased())
				clients.add(c);

		return clients;
	}
@SuppressWarnings("unchecked")
public List<Cliente> getAllNonDeletedClientsAlphabet(){
	
	List<Cliente> clients_jdo;
	PersistenceManager pm = PMF.get().getPersistenceManager();		
	
	Query q = pm.newQuery("select from " + Cliente.class.getName());		
	q.setOrdering("nombre asc");
	clients_jdo = (List<Cliente>) q.execute();
	
	

	List<Cliente> clients = new ArrayList<Cliente>();
	
	
	for (Cliente c:clients_jdo)
		if (!c.isErased())
			clients.add(c);

	pm.close();
	return clients;
}


	public void logicalDelete(Cliente c, String usermail){
		c.setErased(true);
		createCliente(c, usermail);
	}
	
	public Cliente getClienteById(long l) {
           PersistenceManager pManager = PMF.get().getPersistenceManager();
           Cliente cliente_temp = pManager.getObjectById(Cliente.class, l);
          
           Cliente cliente = pManager.detachCopy(cliente_temp);  
            pManager.close();

          
           return cliente;
   }
	   
		public void deleteClient(Cliente c, String usermail){
			ProyectoDao pDao = ProyectoDao.getInstance();
			List<Proyecto> projects = pDao.getProjectsByClient(c.getKey().getId());
			
			for (Proyecto p:projects){
				pDao.deleteProject(p, usermail);
			}
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			pm.deletePersistent( pm.getObjectById( c.getClass(), c.getKey().getId())); 
			pm.close();
			
		}
		
		public void deleteClientAll(String usermail){
			ClienteDao cDao= ClienteDao.getInstance();
			List<Cliente> clientes = cDao.getAllClientes();
				for (Cliente cl:clientes){
					cDao.deleteClient(cl, usermail);
			}
						
		}
		
		public List<Cliente> getClienteByAllParam(String fechaEntrada, String idCliente, String nCliente, String refGlobal, String tipo, String criticidad,  Integer page){
			List<Cliente> clientes= null;
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Cliente");
			List<Filter> finalFilters = new ArrayList<>();
			
			int filters =0;
			if(!fechaEntrada.equals("")){
				filters++;
			}
			if(!idCliente.equals("")){
				filters++;
			}
			if(!nCliente.equals("")){
				filters++;
			}
			if(!refGlobal.equals("")){
				filters++;
			}
			if(!tipo.equals("")){
				filters++;
			}
			if(!criticidad.equals("")){
				filters++;
			}

			if(filters<=1){
				if(!fechaEntrada.equals("")){
					finalFilters.add(new FilterPredicate("str_fecha_alta_cliente",FilterOperator.GREATER_THAN_OR_EQUAL, fechaEntrada));
					finalFilters.add(new FilterPredicate("str_fecha_alta_cliente",FilterOperator.LESS_THAN, fechaEntrada+"\ufffd"));
				}
				if(!idCliente.equals("")){
					finalFilters.add(new FilterPredicate("clientId",FilterOperator.GREATER_THAN_OR_EQUAL, idCliente));
					finalFilters.add(new FilterPredicate("clientId",FilterOperator.LESS_THAN, idCliente+"\ufffd"));
				}
				if(!nCliente.equals("")){
					finalFilters.add(new FilterPredicate("nombre",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
					finalFilters.add(new FilterPredicate("nombre",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
				}
				if(!refGlobal.equals("")){
					finalFilters.add(new FilterPredicate("ref_global",FilterOperator.GREATER_THAN_OR_EQUAL, refGlobal));
					finalFilters.add(new FilterPredicate("ref_global",FilterOperator.LESS_THAN, refGlobal+"\ufffd"));
				}
				if(!tipo.equals("")){
					finalFilters.add(new FilterPredicate("tipo",FilterOperator.GREATER_THAN_OR_EQUAL, tipo));
					finalFilters.add(new FilterPredicate("tipo",FilterOperator.LESS_THAN, tipo+"\ufffd"));
				}
				if(!criticidad.equals("")){
					finalFilters.add(new FilterPredicate("criticidad",FilterOperator.GREATER_THAN_OR_EQUAL, criticidad));
					finalFilters.add(new FilterPredicate("criticidad",FilterOperator.LESS_THAN, criticidad+"\ufffd"));
				}
				
				Filter finalFilter = null;
				if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
				if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
				if(finalFilters.size()!=0)q.setFilter(finalFilter);
				
				List<Entity> entities = null;
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				if(page != null) {
					Integer offset = page * DATA_SIZE;
					fetchOptions.limit(DATA_SIZE);	
					fetchOptions.offset(offset);
				}
				
				entities = datastore.prepare(q).asList(fetchOptions);
				clientes = new ArrayList<>();
				for(Entity result:entities){
					clientes.add(buildCliente(result));
				}
				Cliente impPage = new Cliente();
				impPage.setDetalle("0");
				clientes.add(impPage);
			
			}else{
				
				List<List<Entity>> Entities = new ArrayList<List<Entity>>();
				
				if(!fechaEntrada.equals("")){
					q = new com.google.appengine.api.datastore.Query("Cliente");
					finalFilters = new ArrayList<>();
					finalFilters.add(new FilterPredicate("str_fecha_alta_cliente",FilterOperator.GREATER_THAN_OR_EQUAL, fechaEntrada));
					finalFilters.add(new FilterPredicate("str_fecha_alta_cliente",FilterOperator.LESS_THAN, fechaEntrada+"\ufffd"));
					Filter finalFilter = CompositeFilterOperator.and(finalFilters);
					q.setFilter(finalFilter);
					FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
					Entities.add(datastore.prepare(q).asList(fetchOptions));
				}
				if(!idCliente.equals("")){
					q = new com.google.appengine.api.datastore.Query("Cliente");
					finalFilters = new ArrayList<>();
					finalFilters.add(new FilterPredicate("clientId",FilterOperator.GREATER_THAN_OR_EQUAL, idCliente));
					finalFilters.add(new FilterPredicate("clientId",FilterOperator.LESS_THAN, idCliente+"\ufffd"));
					Filter finalFilter = CompositeFilterOperator.and(finalFilters);
					q.setFilter(finalFilter);
					FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
					Entities.add(datastore.prepare(q).asList(fetchOptions));
				}
				if(!nCliente.equals("")){
					q = new com.google.appengine.api.datastore.Query("Cliente");
					finalFilters = new ArrayList<>();
					finalFilters.add(new FilterPredicate("client_name",FilterOperator.GREATER_THAN_OR_EQUAL, nCliente));
					finalFilters.add(new FilterPredicate("client_name",FilterOperator.LESS_THAN, nCliente+"\ufffd"));
					Filter finalFilter = CompositeFilterOperator.and(finalFilters);
					q.setFilter(finalFilter);
					FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
					Entities.add(datastore.prepare(q).asList(fetchOptions));
				}
				if(!refGlobal.equals("")){
					q = new com.google.appengine.api.datastore.Query("Cliente");
					finalFilters = new ArrayList<>();
					finalFilters.add(new FilterPredicate("ref_global",FilterOperator.GREATER_THAN_OR_EQUAL, refGlobal));
					finalFilters.add(new FilterPredicate("ref_global",FilterOperator.LESS_THAN, refGlobal+"\ufffd"));
					Filter finalFilter = CompositeFilterOperator.and(finalFilters);
					q.setFilter(finalFilter);
					FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
					Entities.add(datastore.prepare(q).asList(fetchOptions));
				}
				if(!tipo.equals("")){
					q = new com.google.appengine.api.datastore.Query("Cliente");
					finalFilters = new ArrayList<>();
					finalFilters.add(new FilterPredicate("tipo",FilterOperator.GREATER_THAN_OR_EQUAL, tipo));
					finalFilters.add(new FilterPredicate("tipo",FilterOperator.LESS_THAN, tipo+"\ufffd"));
					Filter finalFilter = CompositeFilterOperator.and(finalFilters);
					q.setFilter(finalFilter);
					FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
					Entities.add(datastore.prepare(q).asList(fetchOptions));
				}
				if(!criticidad.equals("")){
					q = new com.google.appengine.api.datastore.Query("Cliente");
					finalFilters = new ArrayList<>();
					finalFilters.add(new FilterPredicate("criticidad",FilterOperator.GREATER_THAN_OR_EQUAL, criticidad));
					finalFilters.add(new FilterPredicate("criticidad",FilterOperator.LESS_THAN, criticidad+"\ufffd"));
					Filter finalFilter = CompositeFilterOperator.and(finalFilters);
					q.setFilter(finalFilter);
					FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
					Entities.add(datastore.prepare(q).asList(fetchOptions));
				}
				
				List<Entity> clientesFinal = new ArrayList<>();
				int lowRowsIndex = 0;
				int lowRowsNumber = Entities.get(0).size();
				
				for(int i=1;i<Entities.size();i++){
					if(lowRowsNumber>Entities.get(i).size()){
						lowRowsIndex=i;
						lowRowsNumber=Entities.get(i).size();
					}
				}
				/*
				clientesFinal = Entities.get(lowRowsIndex);
				for(int i=0;i<Entities.size();i++){
					if(i!=lowRowsIndex){
						int j = 0;
						for (Entity result : clientesFinal) {
							if(!Entities.get(i).contains(result)){
								clientesFinal.remove(j);
							}
							j++;
						}
					}
				}
				*/
				
				clientesFinal = Entities.get(lowRowsIndex);
				List<Entity> indexDel = new ArrayList<Entity>();
				for(int i=0;i<Entities.size();i++){
					if(i!=lowRowsIndex){
						int j = 0;
						for (Entity result : clientesFinal) {
							if(!Entities.get(i).contains(result)){
								Entity auxEnty = clientesFinal.get(j);
								if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
							}
							j++;
						}
					}
				}
				
				for (Entity impborr : indexDel){
					clientesFinal.remove(impborr);
				}
				
				clientes = new ArrayList<Cliente>();
				int clientesPages  = clientesFinal.size();
				for(int i = page*10; i< (page*10)+10&&i<clientesFinal.size();i++){
					clientes.add(buildCliente(clientesFinal.get(i)));
				}
				Cliente pages = new Cliente();
				pages.setDetalle(Integer.toString(clientesPages));
				clientes.add(pages);
				
			}
			return clientes;
		}
		
		public List<Cliente> getAllClientePagin(Integer page) {

			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Cliente");
			
			List<Entity> entities = null;
			FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
			if(page != null) {
				Integer offset = page * DATA_SIZE;
				fetchOptions.limit(DATA_SIZE);	
				fetchOptions.offset(offset);
			}
			entities = datastore.prepare(q).asList(fetchOptions);
			
			List<Cliente> Clientes = new ArrayList<Cliente>();	;
			
			for (Entity result : entities){
				Clientes.add(buildCliente(result));
			}

			return Clientes;
		}
		
		private Cliente buildCliente(Entity entity) {
			Cliente cliente = new Cliente();
			
			cliente.setKey(entity.getKey());
			
			String client_Id =  getString(entity, "clientId");
			if(client_Id != null) {
				cliente.setClientId(client_Id);
			}
			
			String criticidad =  getString(entity, "criticidad");
			if(criticidad != null) {
				cliente.setCriticidad(criticidad);
			}
			
			String detalle =  getString(entity, "detalle");
			if(detalle != null) {
				cliente.setDetalle(detalle);
			}
			
			cliente.setErased((boolean) entity.getProperty("erased"));
			
			Date fecha_alta_cliente = getDate(entity, "fecha_alta_cliente");
			if(fecha_alta_cliente != null) {
				cliente.setFecha_alta_cliente(fecha_alta_cliente);
			}
			
			String logo_url =  getString(entity, "logo_url");
			if(logo_url != null) {
				cliente.setLogo_url(logo_url);
			}
			
			String nombre =  getString(entity, "nombre");
			if(nombre != null) {
				cliente.setNombre(nombre);
			}
			
			Integer project_number = getInteger(entity, "project_number");
			if(project_number != null) {
				cliente.setProject_number(project_number);
			}
			
			String ref_global = getString(entity, "ref_global");
			if(ref_global != null) {
				cliente.setRef_global(ref_global);
			}
			
			String str_fecha_alta_cliente = getString(entity, "str_fecha_alta_cliente");
			if(str_fecha_alta_cliente != null) {
				cliente.setStr_fecha_alta_cliente(str_fecha_alta_cliente);
			}
			
			String tipo = getString(entity, "tipo");
			if(tipo != null) {
				cliente.setTipo(tipo);
			}
			
			String[] paises_str =  getStringArr(entity, "paises");
			if(paises_str != null) {
				Set<String> paises = new HashSet<String>();
			
				for (String p:paises_str){
					paises.add(p);
				}
				cliente.setPaises(paises);
			}
			
			/*
			 * image
			 * imageType
			 */
			
			
			return cliente;
			
		}
		
		private String getString(Entity e, String field) {
			try {
				return (String) e.getProperty(field);
			}
			catch(Exception exp) {
				return null;
			}
		}
		
		private String[] getStringArr(Entity e, String field) {
			try {
				return (String[]) e.getProperty(field);
			}
			catch(Exception exp) {
				return null;
			}
		}
		
		
		private Integer getInteger(Entity e, String field) {
			try {
				return (Integer) e.getProperty(field);
			}
			catch(Exception exp) {
				return null;
			}
		}
		
		private Date getDate(Entity e, String field) {
			try {
				return (Date) e.getProperty(field);
			}
			catch(Exception exp) {
				return null;
			}
		}

}
