package com.example.invitaciones.controllers;
import com.example.invitaciones.models.*;
import com.example.invitaciones.repositories.CampaniaReferenteRepositorio;
import com.example.invitaciones.repositories.CampaniaRepositorio;
import com.example.invitaciones.repositories.ReferenteRepositorio;
import com.example.invitaciones.services.EmailSenderService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api")
public class CampaniaReferenteControlador {
	
    @Autowired
    private CampaniaReferenteRepositorio campaniaReferenteRepositorio;

    @Autowired
    private ReferenteRepositorio referenteRepositorio;

    @Autowired
    private CampaniaRepositorio campaniaRepositorio;

	@Autowired
	private EmailSenderService senderService;

    @GetMapping("/campaniaReferente")
    public List<CampaniaReferente> getCampaniasReferentes() {
        return campaniaReferenteRepositorio.traerTodo();
    }
    
    @GetMapping("/verCampaniaReferente/{id}")
    public CampaniaReferente getCampaniaPorReferente(@PathVariable Long id){
        CampaniaReferente campaniaReferente = campaniaReferenteRepositorio.findById(id).orElse(null);       
		  if(campaniaReferente!=null){
		  campaniaReferente.setFechaVisitado(LocalDateTime.now());		  
		  campaniaReferenteRepositorio.save(campaniaReferente);	  
		  return campaniaReferente;
		  }
		  else
			  return null;
    }

    @PostMapping(path = "/enviarCampaniasPorMailOpcionEnvio1")
    public ResponseEntity<Object> crearCampania(@RequestParam String campaniaSeleccionada,
            								   @RequestParam String asuntoMail,
            								   @RequestParam String cuerpoMail,
            								   @RequestParam String[]referentesSeleccionadosArr) throws Exception
    {
        if (campaniaSeleccionada.isEmpty() || asuntoMail.isEmpty() || cuerpoMail.isEmpty() ) {
            return new ResponseEntity<Object>("Datos incompletos", HttpStatus.FORBIDDEN);
        }        
        Campania campania = campaniaRepositorio.findByTitulo(campaniaSeleccionada);  
        if(campania!=null)
        {
        	for(Integer i=0 ; i<referentesSeleccionadosArr.length;i++)
        	{
        		tratarReferente(campania,referentesSeleccionadosArr[i],cuerpoMail,asuntoMail);
        	}
	      	return new ResponseEntity<>("Campanias ingresadas con éxito",HttpStatus.CREATED);
        }
        else        	       	
        	return new ResponseEntity<>("Campanias no encontrada, consulte a su administrador.",HttpStatus.FORBIDDEN);        
   }
    
    
    
    private void tratarReferente(Campania campania,String linea,String cuerpoMail,String asuntoMail) throws Exception
    {
    	List<String> lineaAux = new ArrayList<String>(Arrays.asList(linea.split(";")));
    	String email=lineaAux.get(0);
    	String nombre=lineaAux.get(1);
    	Referente referente = referenteRepositorio.findByEmail(email);
    	if (referente != null) {        			
    			referente.setNombre(nombre);
    			referenteRepositorio.save(referente);
    			CampaniaReferente nuevaCampaniaReferente =campaniaReferenteRepositorio.save(new CampaniaReferente(LocalDateTime.now(),false,campania,referente));
    			/*	GenerarDatos para envio de mail */	    			
    			String mailAenviar = referente.getEmail();
				String cuerpoAenviarA= (cuerpoMail+"Campaña:"+" "+nuevaCampaniaReferente.getCampania().getTitulo());
				String cuerpoAenviarB = "http://localhost:8080/linkcampania.html?id="+nuevaCampaniaReferente.getId();
				senderService.sendMail(mailAenviar, asuntoMail + " " + nuevaCampaniaReferente.getCampania().getTitulo(), cuerpoAenviarA+" "+cuerpoAenviarB);
    	}
    	else
    	{    		
    		referenteRepositorio.save(new Referente (nombre,email));
    		Referente referenteNuevo = referenteRepositorio.findByEmail(email);
    		if(referenteNuevo!=null)
    		{
    			CampaniaReferente nuevaCampaniaReferente = campaniaReferenteRepositorio.save(new CampaniaReferente(LocalDateTime.now(),false,campania,referenteNuevo));
    		/*	GenerarDatos para envio de mail */	    			
				String mailAenviar = referenteNuevo.getEmail();
				String cuerpoAenviarA= (cuerpoMail+"Campaña:"+" "+nuevaCampaniaReferente.getCampania().getTitulo());
				String cuerpoAenviarB = "http://localhost:8080/linkcampania.html?id="+nuevaCampaniaReferente.getId();
				senderService.sendMail(mailAenviar, asuntoMail + " " + nuevaCampaniaReferente.getCampania().getTitulo(), cuerpoAenviarA+" "+cuerpoAenviarB);
    		}    		
    	}
     }

    @PostMapping(path = "/enviarCampaniasPorMailOpcionEnvio2")
    public ResponseEntity<Object> crearCampaniaOpcion2(@RequestParam String campaniaSeleccionada,
            								   @RequestParam String asuntoMail,
            								   @RequestParam String cuerpoMail,
            								   @RequestParam String mailReferente,
            								   @RequestParam String nombreReferente) throws Exception {
    	
        if (campaniaSeleccionada.isEmpty() || asuntoMail.isEmpty() || cuerpoMail.isEmpty() || mailReferente.isEmpty()
        		|| nombreReferente.isEmpty()) {
            return new ResponseEntity<Object>("Datos incompletos", HttpStatus.FORBIDDEN);
        }      
        Campania campania = campaniaRepositorio.findByTitulo(campaniaSeleccionada); 
        if(campania!=null)
        {
	        Referente referente = referenteRepositorio.findByEmail(mailReferente);
	    	if (referente != null) {    			
	    			referente.setNombre(nombreReferente);
	    			referenteRepositorio.save(referente);
	    			CampaniaReferente nuevaCampaniaReferente = campaniaReferenteRepositorio.save(new CampaniaReferente(LocalDateTime.now(),false,campania,referente));	    			
	    			/*	GenerarDatos para envio de mail */
					String mailAenviar = referente.getEmail();
					String cuerpoAenviarA= (cuerpoMail+"Campaña:"+" "+nuevaCampaniaReferente.getCampania().getTitulo());
					String cuerpoAenviarB = "http://localhost:8080/linkcampania.html?id="+nuevaCampaniaReferente.getId();
					senderService.sendMail(mailAenviar, asuntoMail + " " + nuevaCampaniaReferente.getCampania().getTitulo(), cuerpoAenviarA+" "+cuerpoAenviarB);
	    	}
	    	else
	    	{
	    		Referente referenteNuevo = referenteRepositorio.save(new Referente (nombreReferente,mailReferente));
	    		CampaniaReferente nuevaCampaniaReferente = campaniaReferenteRepositorio.save(new CampaniaReferente(LocalDateTime.now(),false,campania,referenteNuevo));
	    		/*	GenerarDatos para envio de mail */	    			
    			String mailAenviarNuevo = referenteNuevo.getEmail();
				String cuerpoAenviarA= (cuerpoMail+"Campaña:"+" "+nuevaCampaniaReferente.getCampania().getTitulo() +"<br/>" );
				String cuerpoAenviarB = "http://localhost:8080/linkcampania.html?id="+nuevaCampaniaReferente.getId()+"<br/>";
				senderService.sendMail(mailAenviarNuevo, asuntoMail + " " + nuevaCampaniaReferente.getCampania().getTitulo(), cuerpoAenviarA+" "+cuerpoAenviarB);
	    	}        
	    	return new ResponseEntity<>("Campañas ingresadas con éxito",HttpStatus.CREATED);
        }
        else
        	return new ResponseEntity<>("Campaña inexistente, consulte administrador",HttpStatus.FORBIDDEN);
        }
     
    @PostMapping("/campaniaReferenteExportPdf")
    public ResponseEntity<Object> ExportingToPDF(HttpServletResponse response,
    											 @RequestParam String tituloCampania,
    											 @RequestParam Long id) throws DocumentException, IOException {
        Campania campania = campaniaRepositorio.findByTitulo(tituloCampania);        
        if(campania!=null) {
            byte[]archivo = null;        
            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=" + campania.getTitulo() + ".pdf";
            response.setHeader(headerKey, headerValue);            
            //Chequear que exista documento
            if(campania.getArchivoCampania()!=null)
            {
            archivo=campania.getArchivoCampania();
            InputStream bos = new ByteArrayInputStream(archivo);
            int tamañoInput = bos.available();
            byte[] datosPdf= new byte[tamañoInput];
            bos.read(datosPdf,0,tamañoInput);
            response.getOutputStream().write(datosPdf);     
	        //Registro descarga de pdf y horario.
	        CampaniaReferente campaniaReferente = campaniaReferenteRepositorio.findById(id).orElse(null);
		        if (campaniaReferente != null) {
		            campaniaReferente.setDescargado(true);
		            campaniaReferente.setFechaDescargado(LocalDateTime.now());
		            campaniaReferenteRepositorio.save(campaniaReferente);
		            return new ResponseEntity<>(HttpStatus.CREATED);
		        } else 
		        	return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);		
            }
            else{
            	return new ResponseEntity<Object> (HttpStatus.FORBIDDEN);	
            	}
        }
        else{
        	return new ResponseEntity<Object>(HttpStatus.FORBIDDEN); 
        	}
    }

    @GetMapping(path = "/campaniaReferenteFiltro")
    public List<CampaniaReferente> getCampaniaReferenteFiltrados(String filtroCampania,String filtroReferente) { 
    	
    	if(filtroCampania.isEmpty() && filtroReferente.isEmpty())
    	{
    		return null;
    	}
    	if(!filtroCampania.isEmpty() && filtroReferente.isEmpty())
    	{    		
    		return campaniaReferenteRepositorio.findFiltroCampania(filtroCampania);
    	}
    	if(filtroCampania.isEmpty() && !filtroReferente.isEmpty())
    	{    		
    		return campaniaReferenteRepositorio.findFiltroReferente(filtroReferente.toUpperCase());
    	}
    	else    	 	
    	return campaniaReferenteRepositorio.findFiltroCampaniaReferente(filtroReferente.toUpperCase(),filtroCampania);
    	
    	
        
    }
    
    
    @PostMapping("/validarArchivoInput") // //new annotation since 4.3
    public List<String> singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {
    	List<String> referentesArchivo = new ArrayList<String>();
    	List<String> referentesArchivoErrror = new ArrayList<String>();
    	referentesArchivoErrror.add("0");
        if (file.isEmpty() || file==null) {
            return referentesArchivoErrror;           
        	}
        else
        {
        	byte[] files= file.getBytes();
        	ByteArrayInputStream byteArrInStrm = new ByteArrayInputStream(files);
        	InputStreamReader inStrmReader = new InputStreamReader(byteArrInStrm);
        	BufferedReader buffReader = new BufferedReader(inStrmReader);    
        	String line;        	
        	while ((line = buffReader.readLine()) != null)
        	{     	          	    	 
        	    if(!tratarLinea(line.toString())) {
        	    	return referentesArchivoErrror;
        	    }
        	    else {
        	    	referentesArchivo.add(line.toString());
        	    }
        	}   	        	
        }       
        return referentesArchivo;
    }
       
        private boolean tratarLinea(String arrAux) {  
    		if ((arrAux.contains("#") || arrAux.contains("%") || arrAux.contains("*") || arrAux.contains("+") 
    				|| arrAux.contains("|") || arrAux.contains("$") || arrAux.contains("/") || arrAux.contains("°")
    				|| arrAux.contains("(") || arrAux.contains("=") || arrAux.contains("!") || arrAux.contains("¡")
    				|| arrAux.contains(")") || arrAux.contains("'") || arrAux.contains("¡") || arrAux.contains("?")
    				|| arrAux.contains("{") || arrAux.contains("}") || arrAux.contains("[") || arrAux.contains("]")
    				|| arrAux.contains("<") || arrAux.contains(">") || arrAux.contains("*") 
    				) || (!arrAux.contains(";")))
    			{
    				return false;
    			}
    		else
    		{
       			String[] lineaArr = arrAux.split(";");  
       			
       			if(lineaArr.length<2) {
       				return false;
       			}
    			//en posicion 0 se alojo el mail .. parts[0];
    			if (!validarFormatoMail(lineaArr[0])){    				
    				return false;
    			}
    			else
    			return true;
    		}
    	}
    	
    	private boolean validarFormatoMail(String mailAvalidar) {
            // Patrón para validar el email    	
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            // El email a validar        
            Matcher mather = pattern.matcher(mailAvalidar);
            if (mather.find() == true) {            	
                return true;
            }  
            else               
               return false;           
            }    
    	
    	

    }
        
	

    
