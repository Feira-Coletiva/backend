package br.edu.ifsc.sistemafeiracoletiva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SistemaFeiraColetivaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaFeiraColetivaApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode(""));
	}

}
