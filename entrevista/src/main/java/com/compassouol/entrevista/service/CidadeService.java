package com.compassouol.entrevista.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.compassouol.entrevista.controller.form.FormCadastroCidade;
import com.compassouol.entrevista.model.Cidade;
import com.compassouol.entrevista.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	CidadeRepository cidadeRepository;

	public ResponseEntity<FormCadastroCidade> cadastrarCidade(@Valid FormCadastroCidade formCidade,
			UriComponentsBuilder uriBuilder) {
		Cidade cidade = formCidade.toCidade();
		cidadeRepository.save(cidade);
		URI uri = uriBuilder.path("/cidade/buscarPeloNome/{nome}").buildAndExpand(cidade.getNome()).toUri();
		return ResponseEntity.created(uri).body(formCidade);
	}

	public ResponseEntity<Cidade> buscarCidadePeloNome(String nome) {
		Optional<Cidade> cidade = cidadeRepository.findByNomeIgnoreCase(nome);
		if (cidade.isPresent()) {
			return ResponseEntity.ok(cidade.get());
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<List<Cidade>> buscarCidadePeloEstado(String estado) {
		Optional<List<Cidade>> cidades = cidadeRepository.findByEstadoIgnoreCase(estado);
		if (cidades.isPresent() && !cidades.get().isEmpty()) {
			return ResponseEntity.ok(cidades.get());
		}
		return ResponseEntity.notFound().build();
	}

}
