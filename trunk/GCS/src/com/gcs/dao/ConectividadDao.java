package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.Cliente;
import com.gcs.beans.Conectividad;
import com.gcs.persistence.PMF;

public class ConectividadDao {
	
	public static ConectividadDao getInstance() {
		return new ConectividadDao();
	}
	
	
	public void createConectividad(Conectividad c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try {
			pm.makePersistent(c);
		} finally {
			pm.close();
		}
	}
	
	public Conectividad getConectividadByProject(String id) {

		Conectividad c = new Conectividad();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Conectividad.class.getName()
				+ " where key_proyecto  == :p1";

		List<Conectividad> conectividad = (List<Conectividad>) pManager.newQuery(queryStr).execute(id);

		if (!conectividad.isEmpty()) {
			c = conectividad.get(0);
		} else {
			c = null;
		}

		transaction.commit();
		pManager.close();

		return c;

	}

}
