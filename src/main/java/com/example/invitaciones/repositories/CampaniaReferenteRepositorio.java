package com.example.invitaciones.repositories;
import com.example.invitaciones.models.CampaniaReferente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface CampaniaReferenteRepositorio extends JpaRepository<CampaniaReferente, Long>{
	
	
	  @Query (value = "SELECT * FROM campania_referente ",nativeQuery = true)
	  List<CampaniaReferente> traerTodo();
	 
		
	   @Query (value = "SELECT * FROM campania_referente WHERE upper(email_referente) LIKE %?1%  ",nativeQuery = true)
	    List<CampaniaReferente> findFiltroReferente(@Param("filtroReferente") String filtro );
	   
	   
	   
	   @Query (value = "SELECT * FROM campania_referente WHERE titulo_campania=? ",nativeQuery = true)
	    List<CampaniaReferente> findFiltroCampania(@Param("filtroCampania") String filtro );
	   
	   @Query (value = "SELECT * FROM campania_referente WHERE upper(email_referente) LIKE %?1% AND titulo_campania=?2 ",nativeQuery = true)
	    List<CampaniaReferente> findFiltroCampaniaReferente(@Param("filtroReferente") String filtro1 , @Param("filtroCampania") String filtro2);
	   
	   
}


//