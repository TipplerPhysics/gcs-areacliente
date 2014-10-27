package com.gcs.dao;

import javax.jdo.PersistenceManager;

import com.gcs.beans.Log;
import com.gcs.persistence.PMF;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class LogsDao {


	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static LogsDao getInstance() {
		return new LogsDao();
	}

	private void createLog(Log log){
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(log);
		} finally {
			pm.close();
		}
	}
}
