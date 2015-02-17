package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.Cliente;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.gcs.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;

public class UserDao {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static UserDao getInstance() {
		return new UserDao();
	}

	public void createUser(User u, String usermail) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Boolean isNew = false;
		
		if (u.getKey()==null)
			isNew = true;

		try {
			pm.makePersistent(u);
		} finally {
			pm.close();
			
			if(u.getErased()==true)
				Utils.writeLog(usermail, "Eliminó", "Usuario", u.getEmail());
			else if (isNew)
					Utils.writeLog(usermail, "Creó", "Usuario", u.getEmail());
				else
					Utils.writeLog(usermail, "Editó", "Usuario", u.getEmail());
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

}



