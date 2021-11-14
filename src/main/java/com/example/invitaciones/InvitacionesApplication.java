package com.example.invitaciones;
import com.example.invitaciones.models.Referente;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.invitaciones.repositories.CampaniaRepositorio;
import com.example.invitaciones.repositories.ReferenteRepositorio;



@SpringBootApplication
public class InvitacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvitacionesApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(CampaniaRepositorio campaniaRepositorio,ReferenteRepositorio referenteRepositorio) {
		return (args) -> {
			referenteRepositorio.save(new Referente("Damian Zamora","damian.zamora@hotmail.com"));
			System.out.println("Compilacion exitosa");
	};
	
}
	
}