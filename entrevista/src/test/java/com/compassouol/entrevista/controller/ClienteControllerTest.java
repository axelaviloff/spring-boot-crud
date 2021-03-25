package com.compassouol.entrevista.controller;

import java.net.URI;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.compassouol.entrevista.controller.form.FormCadastroCidade;
import com.compassouol.entrevista.controller.form.FormCadastroCliente;
import com.compassouol.entrevista.model.Cidade;
import com.compassouol.entrevista.model.Cliente;
import com.compassouol.entrevista.repository.CidadeRepository;
import com.compassouol.entrevista.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) -- Maven Test ignora essa anotação
public class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired 
	private CidadeRepository cidadeRepository;
	
	private Cliente cliente;
	private Cidade cidade;
	
	@Before
	public void criaClienteECidade() throws ParseException {
		this.cidade = new Cidade();
		this.cidade.setEstado("SC");
		this.cidade.setNome("Xanxerê");
		cidadeRepository.save(this.cidade);
		
//		FormCadastroCliente formCliente = new FormCadastroCliente();
//		formCliente.setSexo("Masculino");
//		formCliente.setIdade(18);
//		formCliente.setCidade("Xanxerê");
//		formCliente.setDataNascimento("07/04/1999");
//		formCliente.setNome("Abimael");
//		
//		this.cliente = formCliente.toCliente(cidadeRepository);
		
		this.cliente = new Cliente();
		this.cliente.setCidade(this.cidade);
		this.cliente.setIdade(18);
		this.cliente.setNome("Abimael");
		this.cliente.setSexo("Masculino");
		this.cliente.setDataNascimento(null);
		clienteRepository.save(this.cliente);
	}
	

	
	@Test
	public void buscarClientePeloNomeDeveRetornar200() throws Exception {
		URI uri = new URI("/cliente/buscarPeloNome/Abimael");
		mockMvc
		.perform(MockMvcRequestBuilders
			.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(200));
		
	}
	
	@Test
	public void buscarClientePeloNomeDeveRetornar404() throws Exception {	
		URI uri = new URI("/cliente/buscarPeloNome/Carlos");
		mockMvc
		.perform(MockMvcRequestBuilders
			.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(404));
		
	}
	
	
	@Test
	public void buscarClientePeloIdDeveRetornar200() throws Exception {
		URI uri = new URI("/cliente/buscarPeloId/1");
		mockMvc
		.perform(MockMvcRequestBuilders
			.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(200));
		
	}
	
	@Test
	public void buscarClientePeloIdDeveRetornar404() throws Exception {
		URI uri = new URI("/cliente/buscarPeloId/10");
		mockMvc
		.perform(MockMvcRequestBuilders
			.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(404));
		
	}
	
	@Test
	public void cadastrarClienteDeveRetornar200() throws Exception {
		Cidade cidadeTeste = new Cidade();
		cidadeTeste.setNome("Concórdia");
		cidadeTeste.setEstado("SC");
		cidadeRepository.save(cidadeTeste);
		
		String json = "{\"nome\":\"Axel\",\"sexo\":\"Masculino\",\"dataNascimento\":\"07/04/1999\",\"idade\":\"21\",\"cidade\":\"Concórdia\"}";
		URI uri = new URI("/cliente");
		mockMvc
		.perform(MockMvcRequestBuilders
			.post(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(200));
	}
	
	@Test
	public void cadastrarClienteDeveRetornar404() throws Exception {
		String json = "{\"nome\":\"Axel\",\"sexo\":\"Masculino\",\"dataNascimento\":\"07/04/1999\",\"idade\":\"21\",\"cidade\":\"Irati\"}";
		
		URI uri = new URI("/cliente");
		mockMvc
		.perform(MockMvcRequestBuilders
			.post(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(404));
	}
	
	@Test
	public void cadastrarClienteDeveRetornar400() throws Exception {
		String json = "{\"nome\":\"\",\"sexo\":\"Masculino\",\"dataNascimento\":\"07/04/1999\",\"idade\":\"21\",\"cidade\":\"Irati\"}";
		URI uri = new URI("/cliente");
		mockMvc
		.perform(MockMvcRequestBuilders
			.post(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(400));
	}
	
	@Test
	public void alterarClienteDeveRetornar200() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\" : \"Abimael\", \"id\": 1}";
		mockMvc
		.perform(MockMvcRequestBuilders
			.put(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(200));
	}
	
	@Test
	public void alterarClienteDeveRetornar404() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\" : \"Abimael\", \"id\": 10}";
		mockMvc
		.perform(MockMvcRequestBuilders
			.put(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(404));
	}
	
	@Test
	public void alterarClienteDeveRetornar400() throws Exception {
		URI uri = new URI("/cliente");
		String json = "{\"nome\" : \"\", \"id\": 1}";
		mockMvc
		.perform(MockMvcRequestBuilders
			.put(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(400));
	}
	
	@Test
	public void removerClienteDeveRetornar200() throws Exception {
		URI uri = new URI("/cliente/removerPeloId/1");
		mockMvc
		.perform(MockMvcRequestBuilders
			.delete(uri))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(200));
	}
	
	@Test
	public void removerClienteDeveRetornar404() throws Exception {
		URI uri = new URI("/cliente/removerPeloId/20");
		mockMvc
		.perform(MockMvcRequestBuilders
			.delete(uri))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(404));
	}
	
}
