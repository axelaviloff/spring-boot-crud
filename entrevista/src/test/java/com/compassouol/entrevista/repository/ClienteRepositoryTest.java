package com.compassouol.entrevista.repository;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.compassouol.entrevista.model.Cliente;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TestEntityManager em;
	
	
	@Test
	public void deveriaAcharUmClientePeloNome() {
		Cliente c = new Cliente();
		c.setNome("Carlos");
		c.setSexo("Masculino");
		c.setIdade(20);
		
		em.persist(c);
		
		Optional<List<Cliente>> clientesRetornados = clienteRepository.findByNomeIgnoreCase(c.getNome());
		Assert.assertTrue(clientesRetornados.isPresent());
		Assert.assertEquals(clientesRetornados.get().size(), 1);
		Assert.assertEquals(clientesRetornados.get().get(0).getNome(), "Carlos");
		Assert.assertEquals(clientesRetornados.get().get(0).getSexo(), "Masculino");
		Assert.assertEquals(clientesRetornados.get().get(0).getIdade(), 20);
		Assert.assertNull(clientesRetornados.get().get(0).getDataNascimento());
		Assert.assertEquals(clientesRetornados.get().get(0).getId(), 1);
	}
	
	@Test
	public void naoDeveriaAcharUmClientePeloNome() {
		Optional<List<Cliente>> clientesRetornados = clienteRepository.findByNomeIgnoreCase("Alberto");
		Assert.assertTrue(clientesRetornados.isPresent());
		Assert.assertTrue(clientesRetornados.get().isEmpty());

	}
	
	@Test
	public void deveriaAcharUmClientePeloId() {
		Cliente c = new Cliente();
		c.setNome("Paula");
		c.setSexo("Feminino");
		c.setIdade(20);
		em.persist(c);
		
		Optional<Cliente> clienteRetornado = clienteRepository.findById(c.getId());
		Assert.assertTrue(clienteRetornado.isPresent());
		Assert.assertTrue(clienteRetornado.get().getNome().equals("Paula"));
		Assert.assertTrue(clienteRetornado.get().getSexo().equals("Feminino"));
		Assert.assertNull(clienteRetornado.get().getDataNascimento());
	}
	
	@Test
	public void naoDeveriaAcharUmClientePeloId() {
		Optional<Cliente> clienteRetornado = clienteRepository.findById((long)1);
		Assert.assertTrue(!clienteRetornado.isPresent());

	}
		
	
}