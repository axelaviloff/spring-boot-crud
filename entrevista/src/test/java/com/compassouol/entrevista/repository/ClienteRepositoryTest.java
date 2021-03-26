package com.compassouol.entrevista.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.compassouol.entrevista.controller.form.FormCadastroCidade;
import com.compassouol.entrevista.controller.form.FormCadastroCliente;
import com.compassouol.entrevista.model.Cidade;
import com.compassouol.entrevista.model.Cliente;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	private Cliente cliente;
	
	// Início Testes
	@Test
	public void deveriaAcharUmClientePeloNome() throws ParseException {
		cidadeRepository.save(new FormCadastroCidade("Chapecó", "SC").toCidade());
		this.cliente = new FormCadastroCliente("Carlos", "Chapecó", "Masculino", "07/04/1999", 20).toCliente(cidadeRepository);
		clienteRepository.save(this.cliente);
		
		Optional<List<Cliente>> clientesRetornados = clienteRepository.findByNomeIgnoreCase(this.cliente.getNome());
		Assert.assertTrue(clientesRetornados.isPresent());
		Assert.assertEquals(clientesRetornados.get().size(), 1);
		Assert.assertEquals(clientesRetornados.get().get(0).getNome(), "Carlos");
		Assert.assertEquals(clientesRetornados.get().get(0).getSexo(), "Masculino");
		Assert.assertEquals(clientesRetornados.get().get(0).getIdade(), 20);
		Assert.assertNotNull(clientesRetornados.get().get(0).getDataNascimento());
		Assert.assertEquals(clientesRetornados.get().get(0).getId(), 1);
	}
	
	@Test
	public void naoDeveriaAcharUmClientePeloNome() {
		Optional<List<Cliente>> clientesRetornados = clienteRepository.findByNomeIgnoreCase("Alberto");
		Assert.assertTrue(clientesRetornados.isPresent());
		Assert.assertTrue(clientesRetornados.get().isEmpty());

	}
	
	@Test
	public void deveriaAcharUmClientePeloId() throws ParseException {
		cidadeRepository.save(new FormCadastroCidade("Chapecó", "SC").toCidade());
		this.cliente = new FormCadastroCliente("Paula", "Chapecó", "Feminino", "07/03/1998", 22).toCliente(cidadeRepository);
		clienteRepository.save(this.cliente);
		System.out.println("ID" + this.cliente.getId());

		Optional<Cliente> clienteRetornado = clienteRepository.findById(this.cliente.getId());
		Assert.assertTrue(clienteRetornado.isPresent());
		Assert.assertTrue(clienteRetornado.get().getNome().equals("Paula"));
		Assert.assertTrue(clienteRetornado.get().getSexo().equals("Feminino"));
		Assert.assertNotNull(clienteRetornado.get().getDataNascimento());
		Assert.assertEquals(clienteRetornado.get().getCidade().getNome(), "Chapecó");
	}
	
	@Test
	public void naoDeveriaAcharUmClientePeloId() {
		Optional<Cliente> clienteRetornado = clienteRepository.findById((long)1);
		Assert.assertTrue(!clienteRetornado.isPresent());

	}
		
	
}