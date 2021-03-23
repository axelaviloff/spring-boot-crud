package com.compassouol.entrevista.controller.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.compassouol.entrevista.model.Cidade;
import com.compassouol.entrevista.model.Cliente;
import com.compassouol.entrevista.repository.CidadeRepository;

public class FormCliente {	
	
	@NotEmpty(message = "O nome é obrigatório")
	@NotNull
	private String nome;
	
	@NotEmpty(message = "O nome da cidade é obrigatório")
	@NotNull
	private String nomeCidade;
	
	@NotEmpty(message = "O sexo é obrigatório")
	@NotNull
	private String sexo;
	
	private String dataNascimento;
	
	private int idade;
	
	
	
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getCidade() {
		return nomeCidade;
	}



	public void setCidade(String cidade) {
		this.nomeCidade = cidade;
	}



	public String getSexo() {
		return sexo;
	}



	public void setSexo(String sexo) {
		this.sexo = sexo;
	}



	public String getDataNascimento() {
		return dataNascimento;
	}



	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}



	public int getIdade() {
		return idade;
	}



	public void setIdade(int idade) {
		this.idade = idade;
	}



	public Cliente toCliente(CidadeRepository cidadeRepository) throws ParseException {
		Cliente cliente = new Cliente();
		Optional<Cidade> cidade = cidadeRepository.findByNomeIgnoreCase(this.getCidade());
		if (cidade.isPresent()) {
			cliente.setNome(this.getNome());
			cliente.setSexo(this.getSexo());
			cliente.setIdade(this.getIdade());
			cliente.setCidade(cidade.get());
			cliente.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(this.getDataNascimento()));
			return cliente;
		}
		return null;
	}
	
	
	
	
}
