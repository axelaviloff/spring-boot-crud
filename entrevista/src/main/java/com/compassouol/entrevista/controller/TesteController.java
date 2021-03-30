package com.compassouol.entrevista.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/teste")
public class TesteController {

	@GetMapping
	public String test() {
		return "Teste";
	}

}
