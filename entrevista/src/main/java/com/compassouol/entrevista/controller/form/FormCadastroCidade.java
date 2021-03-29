package com.compassouol.entrevista.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compassouol.entrevista.model.Cidade;

public class FormCadastroCidade {

	@NotNull
	@NotEmpty(message = "O nome da cidade é obrigatório")
	private String nome;

	@NotNull
	@NotEmpty(message = "O estado é obrigatório")
	private String estado;

	// Início Construtores
	public FormCadastroCidade() {

	}

	public FormCadastroCidade(String nome, String estado) {
		this.nome = nome;
		this.estado = estado;
	}

	// Início Getters and Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	// Fim Getters and Setters

	public Cidade toCidade() {
		Cidade cidade = new Cidade();
		cidade.setEstado(this.getEstado());
		cidade.setNome(this.getNome());
		return cidade;
	}

}
