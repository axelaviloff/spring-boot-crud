package com.compassouol.entrevista.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

import com.compassouol.entrevista.controller.dto.FormCliente;
import com.compassouol.entrevista.model.Cliente;
import com.compassouol.entrevista.repository.CidadeRepository;
import com.compassouol.entrevista.repository.ClienteRepository;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid FormCliente formCliente) throws ParseException {
		Cliente cliente = formCliente.toCliente(cidadeRepository);
		if (cliente != null) {
			clienteRepository.save(cliente);
			return ResponseEntity.ok(cliente);
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@GetMapping("buscarPeloNome/{nome}")
	public ResponseEntity<List<Cliente>> buscarClientePeloNome(@PathVariable String nome) {
		Optional<List<Cliente>> clientes =  clienteRepository.findByNomeIgnoreCase(nome);
		if (clientes.isPresent()) {
			return ResponseEntity.ok(clientes.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("buscarPeloId/{id}")
	public ResponseEntity<Cliente> buscarClientePeloId(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("removerCliente/{id}")
	@Transactional
	public ResponseEntity<Cliente> removerClientePeloId(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			clienteRepository.deleteById(id);
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("removerCliente")
	@Transactional
	public ResponseEntity<Cliente> removerCliente(@RequestBody Cliente cliente) {
		clienteRepository.delete(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	
	@PutMapping
	public ResponseEntity<Cliente> alterarNomeCliente(@RequestBody @Valid Cliente cliente) {
		Optional<Cliente> clienteAlterado = clienteRepository.findById(cliente.getId());
		if (clienteAlterado.isPresent()) {
			clienteAlterado.get().setNome(cliente.getNome());
			clienteRepository.save(clienteAlterado.get());
			return ResponseEntity.ok(clienteAlterado.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	
	

}
