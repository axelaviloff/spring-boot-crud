package com.compassouol.entrevista.service;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.compassouol.entrevista.controller.form.FormAlteraCliente;
import com.compassouol.entrevista.controller.form.FormCadastroCliente;
import com.compassouol.entrevista.model.Cliente;
import com.compassouol.entrevista.repository.CidadeRepository;
import com.compassouol.entrevista.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	public ResponseEntity<FormCadastroCliente> cadastrarCliente(@Valid FormCadastroCliente formCliente,
			UriComponentsBuilder uriBuilder) throws ParseException {
		Cliente cliente = formCliente.toCliente(cidadeRepository);
		if (cliente != null) {
			clienteRepository.save(cliente);
			URI uri = uriBuilder.path("/cliente/buscarPeloNome/{nome}").buildAndExpand(formCliente.getNome()).toUri();
			return ResponseEntity.created(uri).body(formCliente);
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<List<Cliente>> buscarClientePeloNome(String nome) {
		Optional<List<Cliente>> clientes = clienteRepository.findByNomeIgnoreCase(nome);
		if (clientes.isPresent() && !clientes.get().isEmpty()) {
			return ResponseEntity.ok(clientes.get());
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Cliente> buscarClientePeloId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Cliente> removerClientePeloId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			clienteRepository.deleteById(id);
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Cliente> alterarNomeCliente(@Valid FormAlteraCliente formCliente) {
		Optional<Cliente> clienteAlterado = clienteRepository.findById(formCliente.getId());
		if (clienteAlterado.isPresent()) {
			clienteAlterado.get().setNome(formCliente.getNome());
			return ResponseEntity.ok(clienteAlterado.get());
		}
		return ResponseEntity.notFound().build();
	}

}
