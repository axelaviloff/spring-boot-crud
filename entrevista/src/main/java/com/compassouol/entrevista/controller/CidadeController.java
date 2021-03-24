package com.compassouol.entrevista.controller;

import java.util.List;
import java.util.Optional;

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

import com.compassouol.entrevista.model.Cidade;
import com.compassouol.entrevista.repository.CidadeRepository;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<Cidade> cadastrarCidade(@RequestBody @Valid Cidade cidade) {
		cidadeRepository.save(cidade);
		return ResponseEntity.ok(cidade);
	}

	@GetMapping("/buscarPeloNome/{nome}")
	public ResponseEntity<Cidade> buscarCidadePeloNome(@PathVariable String nome) {
		Optional<Cidade> cidade = cidadeRepository.findByNomeIgnoreCase(nome);
		if (cidade.isPresent()) {
			return ResponseEntity.ok(cidade.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/buscarPeloEstado/{estado}")
	public ResponseEntity<List<Cidade>> buscarCidadePeloEstado(@PathVariable String estado) {
		Optional<List<Cidade>> cidades = cidadeRepository.findByEstadoIgnoreCase(estado);
		if (cidades.isPresent() && !cidades.get().isEmpty()) {
			return ResponseEntity.ok(cidades.get());
		}
		return ResponseEntity.notFound().build();
	}

}
