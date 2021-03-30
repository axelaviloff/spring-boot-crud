package com.compassouol.entrevista.config.validation;

import java.time.LocalDate;

public class ErrorFormDto {
	private String campo;
	private String mensagem;
	private LocalDate date;
	
	public ErrorFormDto(String campo, String mensagem, LocalDate date) {
		this.campo = campo;
		this.mensagem = mensagem;
		this.date = date;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	
}
