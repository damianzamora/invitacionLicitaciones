package com.example.invitaciones.models;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name="campania_referente")
public class CampaniaReferente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
  	//@GenericGenerator(name = "native", strategy = "native")    
    private long id;    
    private LocalDateTime fechaGenerado;    
    private LocalDateTime fechaVisitado;    
    private LocalDateTime fechaDescargado;    
    private String tituloCampania;    
    private String emailReferente;    
    private Boolean descargado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="campania_id")
    private Campania campania;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="referente_id")
    private Referente referente;

    public CampaniaReferente() {
    }

    
	public CampaniaReferente(LocalDateTime fechaGenerado, Boolean descargado, Campania campania, Referente referente) {
        this.fechaGenerado = fechaGenerado;
        this.descargado = descargado;
        this.campania = campania;
        this.referente = referente;
        this.tituloCampania=campania.getTitulo();
        this.emailReferente=referente.getEmail();        		
    }


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getFechaGenerado() {
		return fechaGenerado;
	}


	public void setFechaGenerado(LocalDateTime fechaGenerado) {
		this.fechaGenerado = fechaGenerado;
	}


	public LocalDateTime getFechaVisitado() {
		return fechaVisitado;
	}


	public void setFechaVisitado(LocalDateTime fechaVisitado) {
		this.fechaVisitado = fechaVisitado;
	}


	public LocalDateTime getFechaDescargado() {
		return fechaDescargado;
	}


	public void setFechaDescargado(LocalDateTime fechaDescargado) {
		this.fechaDescargado = fechaDescargado;
	}


	public String getTituloCampania() {
		return tituloCampania;
	}


	public void setTituloCampania(String tituloCampania) {
		this.tituloCampania = tituloCampania;
	}

	
	

	public String getEmailReferente() {
		return emailReferente;
	}


	public void setEmailReferente(String emailReferente) {
		this.emailReferente = emailReferente;
	}


	public Boolean getDescargado() {
		return descargado;
	}


	public void setDescargado(Boolean descargado) {
		this.descargado = descargado;
	}


	public Campania getCampania() {
		return campania;
	}


	public void setCampania(Campania campania) {
		this.campania = campania;
	}


	public Referente getReferente() {
		return referente;
	}


	public void setReferente(Referente referente) {
		this.referente = referente;
	}

    
	
    
    
}
