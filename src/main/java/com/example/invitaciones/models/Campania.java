package com.example.invitaciones.models;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="campania")
public class Campania {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
  	//@GenericGenerator(name = "native", strategy = "native")  	
    private long id;  
    private String titulo;
    private String descripcion;
  
    @Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="archivo_campania", columnDefinition="blob", nullable=true) 
	private byte[] archivoCampania;

    
	@OneToMany(mappedBy = "campania", fetch=FetchType.EAGER)
    Set<CampaniaReferente> campaniaReferentes = new HashSet<>();

    public Campania() {
    }

    public Campania(String titulo, String descripcion, byte[] archivoCampania) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.archivoCampania = archivoCampania;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public byte[] getArchivoCampania() {
		return archivoCampania;
	}

	public void setArchivoCampania(byte[] archivoCampania) {
		this.archivoCampania = archivoCampania;
	}

    

    
}
