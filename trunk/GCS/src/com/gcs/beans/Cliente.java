package com.gcs.beans;

import java.util.Date;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;


@PersistenceCapable
public class Cliente {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private String nombre;

	@Persistent
	private String clientId;
	
	@Persistent
	private String criticidad;
	
	@Persistent
	private String str_fecha_alta_cliente;
	
	@Persistent
	private Date fecha_alta_cliente;
	
	@Persistent
	private String logo_url;
		
	@Persistent
	private String ref_global;
	
	@Persistent
	private String tipo;
	
	@Persistent
	private Set<String> paises;
	
	@Persistent
	private Integer project_number;
	
	@Persistent
	private Boolean erased;
	
	

    @Persistent
    @Extension
    (vendorName="datanucleus", key="gae.unindexed", value="true")
    private String imageType;

    @Persistent
    private Blob image;

	
	
	public Boolean isErased() {
		return erased;
	}

	public void setErased(Boolean erased) {
		this.erased = erased;
	}

	public Integer getProject_number() {
		return project_number;
	}

	public void setProject_number(Integer project_number) {
		this.project_number = project_number;
	}

	public Set<String> getPaises() {
		return paises;
	}

	public void setPaises(Set<String> paises) {
		this.paises = paises;
	}

	public String getCriticidad() {
		return criticidad;
	}

	public void setCriticidad(String criticidad) {
		this.criticidad = criticidad;
	}

	public String getStr_fecha_alta_cliente() {
		return str_fecha_alta_cliente;
	}

	public void setStr_fecha_alta_cliente(String str_fecha_alta_cliente) {
		this.str_fecha_alta_cliente = str_fecha_alta_cliente;
	}

	public Date getFecha_alta_cliente() {
		return fecha_alta_cliente;
	}

	public void setFecha_alta_cliente(Date fecha_alta_cliente) {
		this.fecha_alta_cliente = fecha_alta_cliente;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	
	public String getRef_global() {
		return ref_global;
	}

	public void setRef_global(String ref_global) {
		this.ref_global = ref_global;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	

	public Cliente() {
		super();
	};

	public Key getKey() {
		return key;
	}		

	public void setKey(Key key) {
		this.key = key;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	

    public String getImageType() {
        return imageType;
    }

    public byte[] getImage() {
        if (image == null) {
            return null;
        }

        return image.getBytes();
    }
    
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setImage(byte[] bytes) {
        this.image = new Blob(bytes);
    }


    
	
	@Override
	public boolean equals(Object object_b) {
		 if (!(object_b instanceof Cliente)) {
		        return false;
		    }

		    Cliente object_a = (Cliente) object_b;
		    
		 // Custom equality check here.
		    return this.nombre.equals(object_a.nombre)
		        && this.ref_global.equals(object_a.ref_global);
	}	
}
