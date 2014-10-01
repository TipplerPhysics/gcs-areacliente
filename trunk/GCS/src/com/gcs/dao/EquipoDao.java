package com.gcs.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.gcs.beans.Equipo;
import com.gcs.beans.User;
import com.gcs.persistence.PMF;

public class EquipoDao {
	
	public static EquipoDao getInstance() {
		return new EquipoDao();
	}
	
	public void createEquipo(Equipo e) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try {
			pm.makePersistent(e);
		} finally {
			pm.close();
		}
	}
	
	public Equipo getEquipoByName(String name) {

		Equipo e = new Equipo();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Equipo.class.getName()
				+ " where nombre  == :p1";

		List<Equipo> equipos = (List<Equipo>) pManager.newQuery(queryStr).execute(name);

		if (!equipos.isEmpty()) {
			e = equipos.get(0);
		} else {
			e = null;
		}

		transaction.commit();
		pManager.close();

		return e;

	}
}
