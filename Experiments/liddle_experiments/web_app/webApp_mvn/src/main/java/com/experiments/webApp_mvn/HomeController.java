package com.experiments.webApp_mvn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

	@GetMapping("/")
	public String index()
	{
		return "This is a beginning web page bro";
	}

}
