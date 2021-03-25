package com.compassouol.entrevista.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FormAlteraCliente {

	private Long id;

	@NotNull @NotEmpty(message = "O nome é obrigatório")
	private String nome;
	
	// Início Getters and Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	// Fim Getters and Setters
}
