package com.compassouol.entrevista.controller;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.compassouol.entrevista.controller.form.FormAlteraCliente;
import com.compassouol.entrevista.controller.form.FormCadastroCliente;
import com.compassouol.entrevista.model.Cliente;
import com.compassouol.entrevista.service.ClienteService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping
	@Transactional
	@ApiOperation(value = "Cadastrar um cliente")
	public ResponseEntity<FormCadastroCliente> cadastrarCliente(@RequestBody @Valid FormCadastroCliente formCliente,
			UriComponentsBuilder uriBuilder) throws ParseException {
		return clienteService.cadastrarCliente(formCliente, uriBuilder);
	}

	@GetMapping("buscarPeloNome/{nome}")
	@ApiOperation(value = "Buscar cliente pelo nome")
	public ResponseEntity<List<Cliente>> buscarClientePeloNome(@PathVariable String nome) {
		return clienteService.buscarClientePeloNome(nome);
	}

	@GetMapping("buscarPeloId/{id}")
	@ApiOperation(value = "Buscar cliente pelo id")
	public ResponseEntity<Cliente> buscarClientePeloId(@PathVariable @Min(1) Long id) {
		return clienteService.buscarClientePeloId(id);
	}

	@DeleteMapping("removerPeloId/{id}")
	@Transactional
	@ApiOperation(value = "Remover cliente pelo id")
	public ResponseEntity<Cliente> removerClientePeloId(@PathVariable @Min(1) Long id) {
		return clienteService.removerClientePeloId(id);
	}

	@PutMapping
	@Transactional
	@ApiOperation(value = "Alterar nome de um cliente")
	public ResponseEntity<Cliente> alterarNomeCliente(@RequestBody @Valid FormAlteraCliente formCliente) {
		return clienteService.alterarNomeCliente(formCliente);
	}

}
