package com.gcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserDao {
	
	public static final int DATA_SIZE = 10;
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static UserDao getInstance() {
		return new UserDao();
	}

	public void createUser(User u, String usermail) {
		
		u.setApellido1(u.getApellido1().toUpperCase());
		u.setApellido2(u.getApellido2().toUpperCase());
		u.setAreas(u.getAreas().toUpperCase());
		u.setDepartamento(u.getDepartamento().toUpperCase());
		u.setNombre(u.getNombre().toUpperCase());
		u.setPermisoStr(u.getPermisoStr().toUpperCase());
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Boolean isNew = false;
		ContadorUserDao cuDao = ContadorUserDao.getInstance();
		if (u.getKey()==null)
			isNew = true;

		try {
			pm.makePersistent(u);
		} finally {
			pm.close();
			
			if(u.getErased()==true){
				Utils.writeLog(usermail, "Eliminó", "Usuario", u.getEmail());
				cuDao.decrementCont();
			}else if (isNew){
				Utils.writeLog(usermail, "Creó", "Usuario", u.getEmail());
				cuDao.increaseCont();
			}else{
				Utils.writeLog(usermail, "Editó", "Usuario", u.getEmail());
			}
		}
	}

	public void deleteUser(User u) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(u.getClass(), u.getKey().getId()));
		pm.close();

	}
	
	public void logicalDelete(User u, String usermail){
		u.setErased(true);
		createUser(u,usermail);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {

		List<User> usuarios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryStr = "select from " + User.class.getName();
		usuarios = (List<User>) pm.newQuery(queryStr).execute();
		
		pm.close();

		return usuarios;
	}
	
	public List<User> getAllNonDeletedUsers(){
		

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + User.class.getName()
				+ " where erased  == false && activo == true";

		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) pManager.newQuery(queryStr).execute();		

		transaction.commit();
		pManager.close();

		return users;
	}

	public User getUserByMail(String email) {

		User u = new User();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + User.class.getName()
				+ " where email  == :p1";

		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) pManager.newQuery(queryStr).execute(email);

		if (!users.isEmpty()) {
			u = users.get(0);
		} else {
			u = null;
		}

		transaction.commit();
		pManager.close();

		return u;

	}

	public User getUserbyId(long l) {
		
		User user;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		User user_temp = pManager.getObjectById(User.class, l);

		user = pManager.detachCopy(user_temp);
		pManager.close();

		}catch(Exception e){
			user=null;
		}
		
		return user;
		
		
	}

	public User getUserbyKey(Key k) {
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		User user_temp = pManager.getObjectById(User.class, k);

		User user = pManager.detachCopy(user_temp);
		pManager.close();

		return user;
	}
	
	public List<User> getUsersByName(String name) {
		PersistenceManager pManager = PMF.get().getPersistenceManager();

		String queryStr = "select from " + User.class.getName()+ " WHERE name == "+name;

		@SuppressWarnings("unchecked")
		List<User> agrupations = (List<User>) pManager.newQuery(queryStr).execute();



		pManager.close();

		return agrupations;
	}
	
	
	public List<User> getUsersByCompleteName(String[] gestorItComplete) {
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		String name="";
		String surname="";
		String lastname=""; 
		if (gestorItComplete.length==3){
			name=gestorItComplete[0].replace("-", " ");
			surname=gestorItComplete[1];
			lastname=gestorItComplete[2]; 
		}else{
			if(gestorItComplete.length==2){
				name=gestorItComplete[0];
				surname=gestorItComplete[1];
			}else{
				name=gestorItComplete[0];
				
			}
		}
		String queryStr ="";
		if(lastname.equals("")){
			if(surname.equals("")){
				queryStr = "select from " + User.class.getName()+ " where nombre  == '"+ name+"'";
			}else{
				queryStr = "select from " + User.class.getName()+ " where nombre  == '"+ name+"' && apellido1 =='"+surname+ "'";
			}
		}else{
			queryStr = "select from " + User.class.getName()+ " where nombre  == '"+ name+"' && apellido1 =='"+surname+ "' && apellido2 =='"+lastname+"'" ;
		}
		

		@SuppressWarnings("unchecked")
		List<User> agrupations = (List<User>) pManager.newQuery(queryStr).execute();



		pManager.close();

		return agrupations;
	}
	

	public List<User> getUsersByPermisoStr(int permiso) {
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + User.class.getName()
				+ " WHERE permiso == :permiso && erased == false";

		@SuppressWarnings("unchecked")
		List<User> agrupations = (List<User>) pManager.newQuery(queryStr).execute(permiso);

		transaction.commit();

		pManager.close();

		return agrupations;
	}
	
	public void deleteAll(){
		UserDao usrDao = UserDao.getInstance();
		List<User> usuarios = usrDao.getAllUsers();
		for(User usr:usuarios){
			usrDao.deleteUser(usr);
		}
	}
	
	public List<User> getUserByAllParam(String nombre, String apellido1, String apellido2, String departamento, String permisoStr, Integer page){
		List<User> users= null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("User");
		List<Filter> finalFilters = new ArrayList<>();
		
		int filters =0;
		if(!nombre.equals("")){
			filters++;
		}
		if(!apellido1.equals("")){
			filters++;
		}
		if(!apellido2.equals("")){
			filters++;
		}
		if(!departamento.equals("")){
			filters++;
		}
		if(!permisoStr.equals("")){
			filters++;
		}
		
		if(filters<=1){
			if(!nombre.equals("")){
				finalFilters.add(new FilterPredicate("nombre",FilterOperator.GREATER_THAN_OR_EQUAL, nombre));
				finalFilters.add(new FilterPredicate("nombre",FilterOperator.LESS_THAN, nombre+"\ufffd"));
			}
			if(!apellido1.equals("")){
				finalFilters.add(new FilterPredicate("apellido1",FilterOperator.GREATER_THAN_OR_EQUAL, apellido1));
				finalFilters.add(new FilterPredicate("apellido1",FilterOperator.LESS_THAN, apellido1+"\ufffd"));
			}
			if(!apellido2.equals("")){
				finalFilters.add(new FilterPredicate("apellido2",FilterOperator.GREATER_THAN_OR_EQUAL, apellido2));
				finalFilters.add(new FilterPredicate("apellido2",FilterOperator.LESS_THAN, apellido2+"\ufffd"));
			}
			if(!departamento.equals("")){
				finalFilters.add(new FilterPredicate("departamento",FilterOperator.GREATER_THAN_OR_EQUAL, departamento));
				finalFilters.add(new FilterPredicate("departamento",FilterOperator.LESS_THAN, departamento+"\ufffd"));
			}
			if(!permisoStr.equals("")){
				finalFilters.add(new FilterPredicate("permisoStr",FilterOperator.GREATER_THAN_OR_EQUAL, permisoStr));
				finalFilters.add(new FilterPredicate("permisoStr",FilterOperator.LESS_THAN, permisoStr+"\ufffd"));
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
			users = new ArrayList<>();
			for(Entity result:entities){
				users.add(buildUser(result));
			}
			User impPage = new User();
			impPage.setDetalle("0");
			users.add(impPage);
		
		}else{
			
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!nombre.equals("")){
				q = new com.google.appengine.api.datastore.Query("User");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("nombre",FilterOperator.GREATER_THAN_OR_EQUAL, nombre));
				finalFilters.add(new FilterPredicate("nombre",FilterOperator.LESS_THAN, nombre+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!apellido1.equals("")){
				q = new com.google.appengine.api.datastore.Query("User");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("apellido1",FilterOperator.GREATER_THAN_OR_EQUAL, apellido1));
				finalFilters.add(new FilterPredicate("apellido1",FilterOperator.LESS_THAN, apellido1+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!apellido2.equals("")){
				q = new com.google.appengine.api.datastore.Query("User");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("apellido2",FilterOperator.GREATER_THAN_OR_EQUAL, apellido2));
				finalFilters.add(new FilterPredicate("apellido2",FilterOperator.LESS_THAN, apellido2+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!departamento.equals("")){
				q = new com.google.appengine.api.datastore.Query("User");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("departamento",FilterOperator.GREATER_THAN_OR_EQUAL, departamento));
				finalFilters.add(new FilterPredicate("departamento",FilterOperator.LESS_THAN, departamento+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			if(!permisoStr.equals("")){
				q = new com.google.appengine.api.datastore.Query("User");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("permisoStr",FilterOperator.GREATER_THAN_OR_EQUAL, permisoStr));
				finalFilters.add(new FilterPredicate("permisoStr",FilterOperator.LESS_THAN, permisoStr+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			List<Entity> usersFinal = new ArrayList<>();
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
			
			usersFinal = Entities.get(lowRowsIndex);
			List<Entity> indexDel = new ArrayList<Entity>();
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int j = 0;
					for (Entity result : usersFinal) {
						if(!Entities.get(i).contains(result)){
							Entity auxEnty = usersFinal.get(j);
							if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
						}
						j++;
					}
				}
			}
			
			for (Entity impborr : indexDel){
				usersFinal.remove(impborr);
			}
			
			users = new ArrayList<User>();
			int usersPages  = usersFinal.size();
			for(int i = page*10; i< (page*10)+10&&i<usersFinal.size();i++){
				users.add(buildUser(usersFinal.get(i)));
			}
			User pages = new User();
			pages.setDetalle(Integer.toString(usersPages));
			users.add(pages);
		}
		return users;
	}
	
	public List<User> getAllUserPagin(Integer page) {

		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("User");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<User> users = new ArrayList<User>();	;
		
		for (Entity result : entities){
			users.add(buildUser(result));
		}

		return users;
	}
	
	private User buildUser(Entity entity) {
		User users = new User();
		
		users.setKey(entity.getKey());
		
		users.setActivo((boolean) entity.getProperty("activo"));
		
		String apellido1 =  getString(entity, "apellido1");
		if(apellido1 != null) {
			users.setApellido1(apellido1);
		}
		
		String apellido2 =  getString(entity, "apellido2");
		if(apellido2 != null) {
			users.setApellido2(apellido2);
		}
		
		String areas =  getString(entity, "areas");
		if(areas != null) {
			users.setAreas(areas);
		}
		
		String departamento =  getString(entity, "departamento");
		if(departamento != null) {
			users.setDepartamento(departamento);
		}
		
		String detalle =  getString(entity, "detalle");
		if(detalle != null) {
			users.setDetalle(detalle);
		}
		
		String email =  getString(entity, "email");
		if(email != null) {
			users.setEmail(email);
		}
		
		users.setErased((boolean) entity.getProperty("erased"));
		
		String nombre =  getString(entity, "nombre");
		if(nombre != null) {
			users.setNombre(nombre);
		}
		
		Integer permiso  =  getInteger(entity, "permiso");
		if(permiso != null) {
			users.setPermiso(permiso);
		}		
		
		String permisoStr =  getString(entity, "permisoStr");
		if(permisoStr != null) {
			users.setPermisoStr(permisoStr);
		}
		
		return users;
		
	}
	
	private String getString(Entity e, String field) {
		try {
			return (String) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
	
	private Long getLong(Entity e, String field) {
		try {
			return (Long) e.getProperty(field);
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



