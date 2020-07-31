package de.thd.pms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FahrtenbuchApplication {
	@SuppressWarnings("unused")
	private static Logger log = LogManager.getLogger(FahrtenbuchApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FahrtenbuchApplication.class, args);
	}

}
