package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.User;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;

public class UserDao {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public static UserDao getInstance() {
        return new UserDao();
    }
	
	public void  createUser(User u){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			pm.makePersistent(u);
		}finally{
			pm.close();
		}
	}
	
	public void deleteUser(User u){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( u.getClass(), u.getKey().getId() ) ); 
		pm.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(){
		
		List<User> usuarios;		
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		String queryStr = "select from " + User.class.getName();		
		usuarios = (List<User>) pm.newQuery(queryStr).execute();
		
		return usuarios;
	}
	
	public User getUserByMail(String email){
		
		User u = new User();
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
        Transaction transaction = pManager.currentTransaction();
        transaction.begin();    
       
        String queryStr = "select from " + User.class.getName() + " where email  == :p1";              

        List<User> users = (List<User>) pManager.newQuery(queryStr).execute(email);

        if (!users.isEmpty()){
        	u= users.get(0);
        }else{
        	u=null;
        }
        
        
        transaction.commit();
         pManager.close();

		
		return u;
		
	}
	
	   public User getUserbyId(long l) {
           PersistenceManager pManager = PMF.get().getPersistenceManager();
           User user_temp = pManager.getObjectById(User.class, l);
          
           User user = pManager.detachCopy(user_temp);  
            pManager.close();

          
           return user;
   }
	   
	   public User getUserbyKey(Key k) {
           PersistenceManager pManager = PMF.get().getPersistenceManager();
           User user_temp = pManager.getObjectById(User.class, k);
          
           User user = pManager.detachCopy(user_temp);  
            pManager.close();

          
           return user;
   }
	   
	   
	   
	   
	   
	     public List<User> getUsersByPermisoStr (int permiso){
             PersistenceManager pManager = PMF.get().getPersistenceManager();
             Transaction transaction = pManager.currentTransaction();
             transaction.begin();
            
             String queryStr = "select from " + User.class.getName() + " WHERE permiso == :permiso";
            
             List<User> agrupations = (List<User>) pManager.newQuery(queryStr).execute(permiso);
            
             transaction.commit();
            
              pManager.close();
            
             return agrupations;
            
     }



}
