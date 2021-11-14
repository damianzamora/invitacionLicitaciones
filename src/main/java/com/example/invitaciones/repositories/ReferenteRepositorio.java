package com.example.invitaciones.repositories;
import com.example.invitaciones.models.Referente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ReferenteRepositorio extends JpaRepository<Referente, Long>{
    Referente findByNombre(String nombre);
    Referente findByEmail(String email);   
    @Query (value = "SELECT * FROM referente WHERE upper(nombre) LIKE %?1% OR upper(email) LIKE %?1% ",nativeQuery = true)
    List<Referente> findFiltro(@Param("filtro") String filtro );
    
}
