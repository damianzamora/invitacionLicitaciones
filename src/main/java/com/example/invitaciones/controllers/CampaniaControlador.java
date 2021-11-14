package com.example.invitaciones.controllers;


import com.example.invitaciones.models.Campania;
import com.example.invitaciones.repositories.CampaniaRepositorio;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CampaniaControlador {

    @Autowired
    private CampaniaRepositorio campaniaRepositorio;

    @GetMapping("/campanias")
    public List<Campania> getCampanias() {
        return campaniaRepositorio.findAll().stream().collect(Collectors.toList());
    }

    @PostMapping(path = "/crearCampania")
    public ResponseEntity<Object> crearCampania(@RequestParam("nombreCampania") String nombreCampania, 
    										   @RequestParam("descripcionCampania") String descripcionCampania,    										   
    										   @RequestParam ("fileCampania")MultipartFile fileCampania,
    		                                   RedirectAttributes redirectAttributes) throws IOException {
    	 if (nombreCampania.isEmpty() || descripcionCampania.isEmpty() || fileCampania.isEmpty() ) {
             return new ResponseEntity<Object>("No ha ingresado todos los datos necesarios.", HttpStatus.FORBIDDEN);
         }
    	 Campania campaniaEncontrada = campaniaRepositorio.findByTitulo(nombreCampania);
    	if (campaniaEncontrada !=  null) {
            return new ResponseEntity<>("Nombre de campaña existente, no se pudo crear.", HttpStatus.FORBIDDEN);
         }
    	campaniaRepositorio.save(new Campania(nombreCampania,descripcionCampania,fileCampania.getBytes()));
		return new ResponseEntity<>(HttpStatus.CREATED);    	
    }

    //Funcionalidad PDF Exports
    @PostMapping("/campania/export/pdf")
    public void ExportingToPDF(HttpServletResponse response,  @RequestParam String tituloCampania) throws DocumentException, IOException {
        Campania campania = campaniaRepositorio.findByTitulo(tituloCampania);
        if(campania!=null)
        {
	        byte[]archivo = null;
	        response.setContentType("application/pdf");
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=" + campania.getTitulo() + ".pdf";
	        response.setHeader(headerKey, headerValue);        
	        archivo=campania.getArchivoCampania();
	        InputStream bos = new ByteArrayInputStream(archivo);
	        int tamañoInput = bos.available();
	        byte[] datosPdf= new byte[tamañoInput];
	        bos.read(datosPdf,0,tamañoInput);
	        response.getOutputStream().write(datosPdf);
        }
        else
        System.out.println("ERROR PDF");            
    }
}
