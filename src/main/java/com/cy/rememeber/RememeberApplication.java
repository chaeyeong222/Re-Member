package com.cy.rememeber;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class RememeberApplication {

	public static void main(String[] args) {
		SpringApplication.run(RememeberApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "index";
	}
}
