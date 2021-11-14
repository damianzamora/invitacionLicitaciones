package com.example.invitaciones.models;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="referente")
public class Referente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
  	//@GenericGenerator(name = "native", strategy = "native") 
	private long id;	
    private String nombre;
    private String email;

    @OneToMany(mappedBy = "campania", fetch=FetchType.EAGER)
    Set<CampaniaReferente> campaniaReferentes = new HashSet<>();

    public Referente() {
    }

    public Referente(String nombre, String email) {
    	this.nombre = nombre;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



