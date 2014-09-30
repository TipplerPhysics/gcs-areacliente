package com.gcs.dao;

import javax.jdo.PersistenceManager;

import com.gcs.beans.Equipo;
import com.gcs.persistence.PMF;

public class EquipoDao {
	
	public void createEquipo(Equipo e) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try {
			pm.makePersistent(e);
		} finally {
			pm.close();
		}
	}
}
