package com.example.invitaciones.controllers;


import com.example.invitaciones.models.Referente;
import com.example.invitaciones.repositories.ReferenteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ReferenteControlador {

    @Autowired
    private ReferenteRepositorio referenteRepositorio;

    @GetMapping("/referentes")
    public List<Referente> getReferentes() {
        return referenteRepositorio.findAll().stream().collect(Collectors.toList());
    }
    
    @GetMapping(path = "/referenteFiltro")
    public List<Referente> getReferenteFiltrados(@RequestParam String filtro) {  
        return referenteRepositorio.findFiltro(filtro.toUpperCase());
    }
    
    @GetMapping(path = "/buscarReferente")
    public String getReferente(@RequestParam String emailReferente) {  
    	Referente refEncontrado = referenteRepositorio.findByEmail(emailReferente);
    	if(refEncontrado!=null)
    	{
    		String nombre=refEncontrado.getNombre();
    		return nombre;
    	}
    	else
        return null;
    }
}
