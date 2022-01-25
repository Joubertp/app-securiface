package com.backAppSecuriface.api;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackAppSecurifaceApplication {

	public static void main(String[] args) {
		// SpringApplication.run(BackAppSecurifaceApplication.class, args);
		SpringApplication application = new SpringApplication(BackAppSecurifaceApplication.class);
        application.setDefaultProperties(Map.of("server.port", 9000));
        application.run(args);
	}
// si problème de modification de données non prises en charges :
// taper dans la console: mvn clean spring-boot:run
// si erreur suite à ça, cliquer sur Project, clean et clean all project. au cas ou, reouvrir spring boot
}
