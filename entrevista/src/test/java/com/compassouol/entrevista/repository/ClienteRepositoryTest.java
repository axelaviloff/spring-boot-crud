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
import com.compassouol.entrevista.model.Cliente;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	private FormCadastroCliente formCliente = new FormCadastroCliente();
	
	private FormCadastroCidade formCidade = new FormCadastroCidade();
	
	@Test
	public void deveriaAcharUmClientePeloNome() throws ParseException {
		formCidade.setEstado("SC");
		formCidade.setNome("Chapecó");
		cidadeRepository.save(formCidade.toCidade());
		
		formCliente.setIdade(20);
		formCliente.setNome("Carlos");
		formCliente.setSexo("Masculino");
		formCliente.setDataNascimento("07/04/1999");
		formCliente.setCidade("Chapecó");
		clienteRepository.save(formCliente.toCliente(cidadeRepository));
		
		Optional<List<Cliente>> clientesRetornados = clienteRepository.findByNomeIgnoreCase(formCliente.getNome());
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
		formCidade.setEstado("SC");
		formCidade.setNome("Chapecó");
		cidadeRepository.save(formCidade.toCidade());
		
		formCliente.setIdade(22);
		formCliente.setNome("Paula");
		formCliente.setSexo("Feminino");
		formCliente.setDataNascimento("07/03/1998");
		formCliente.setCidade("Chapecó");
		Cliente c = formCliente.toCliente(cidadeRepository);
		clienteRepository.save(c);

		Optional<Cliente> clienteRetornado = clienteRepository.findById(c.getId());
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