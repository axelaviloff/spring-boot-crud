package com.compassouol.entrevista.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.compassouol.entrevista.controller.form.FormCadastroCidade;
import com.compassouol.entrevista.model.Cidade;
import com.compassouol.entrevista.service.CidadeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@PostMapping
	@Transactional
	@ApiOperation(value = "Cadastrar uma cidade")
	public ResponseEntity<FormCadastroCidade> cadastrarCidade(@RequestBody @Valid FormCadastroCidade formCidade,
			UriComponentsBuilder uriBuilder) {
		return cidadeService.cadastrarCidade(formCidade, uriBuilder);
	}

	@GetMapping("/buscarPeloNome/{nome}")
	@ApiOperation(value = "Buscar cidade através do nome")
	public ResponseEntity<Cidade> buscarCidadePeloNome(@PathVariable String nome) {
		return cidadeService.buscarCidadePeloNome(nome);
	}

	@GetMapping("/buscarPeloEstado/{estado}")
	@ApiOperation(value = "Buscar todas cidades de um estado")
	public ResponseEntity<List<Cidade>> buscarCidadePeloEstado(@PathVariable String estado) {
		return cidadeService.buscarCidadePeloEstado(estado);
	}

}
