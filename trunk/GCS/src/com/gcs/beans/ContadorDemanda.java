package com.gcs.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class ContadorDemanda {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	Integer num;

	public Integer getNum() {
		return num;
	}

	public ContadorDemanda(Integer num) {
		super();
		this.num = num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
