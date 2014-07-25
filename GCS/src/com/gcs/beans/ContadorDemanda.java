package com.gcs.beans;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class ContadorDemanda {
	
	@PrimaryKey
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
