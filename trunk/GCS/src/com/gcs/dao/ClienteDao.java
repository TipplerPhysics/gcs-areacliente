package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.gcs.beans.Cliente;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class ClienteDao {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static ClienteDao getInstance() {
		return new ClienteDao();
	}

	public void createGestorIt(Cliente u) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(u);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getAllCliente() {

		List<Cliente> clientes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryStr = "select from " + Cliente.class.getName();
		clientes = (List<Cliente>) pm.newQuery(queryStr).execute();

		return clientes;
	}

}
