package com.gcs.beans;
import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class ConectividadProyecto {
		@PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    private Key key;
		
		@Persistent
		private String name;
		
		@Persistent
		private long productoId;
		
		
		public Key getKey() {
			return key;
		}

		public void setKey(Key key) {
			this.key = key;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getProductoId() {
			return productoId;
		}

		public void setProductoId(long id) {
			this.productoId = id;
		}
	
}
