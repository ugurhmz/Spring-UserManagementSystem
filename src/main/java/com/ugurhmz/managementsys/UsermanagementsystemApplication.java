package com.ugurhmz.managementsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.ugurhmz.managementsys.entity","com.ugurhmz.managementsys.repositories"})
public class UsermanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementsystemApplication.class, args);
	}

}
