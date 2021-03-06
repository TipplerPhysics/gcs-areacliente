package com.gcs.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class ContadorPais {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	Integer num;

	public ContadorPais(Integer num) {
		super();
		this.num = num;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

}
