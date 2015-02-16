package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

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

}
