package com.example.demo;

import com.example.demo.mon.Monster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping
	public List<Monster> hello(){
		return List.of(
				new Monster(
						"Charizard",
						100,
						25,
						25,
						50,
						50,
						33
				)
		);
	}

}
