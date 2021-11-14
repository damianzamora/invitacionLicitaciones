package com.example.invitaciones.repositories;


import com.example.invitaciones.models.Campania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CampaniaRepositorio extends JpaRepository<Campania, Long> {
    Campania findByTitulo(String titulo);
}
