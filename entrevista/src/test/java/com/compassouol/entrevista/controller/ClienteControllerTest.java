package com.compassouol.entrevista.controller;

import java.net.URI;
import java.text.ParseException;

import org.junit.After;
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
@AutoConfigureMockMvc(addFilters = false)
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
	
	// Início Testes
	@Before
	public void criaClienteECidade() throws ParseException {
		FormCadastroCidade formCidade = new FormCadastroCidade("Xanxerê", "SC");
		this.cidade = formCidade.toCidade();
		cidadeRepository.save(this.cidade);
		
		FormCadastroCliente formCliente = new FormCadastroCliente("Abimael", "Xanxerê", "Masculino", "07/04/1999", 20);
		this.cliente = formCliente.toCliente(cidadeRepository);
		clienteRepository.save(this.cliente);
	}
	
	@After
	public void deletaClienteECidade() {
		clienteRepository.delete(this.cliente);
		cidadeRepository.delete(this.cidade);
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
		URI uri = new URI("/cliente/buscarPeloId/" + this.cliente.getId());
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
	public void cadastrarClienteDeveRetornar201() throws Exception {
		cidadeRepository.save(new FormCadastroCidade("Concórdia", "SC").toCidade());
		
		String json = "{\"nome\":\"Axel\",\"sexo\":\"Masculino\",\"dataNascimento\":\"07/04/1999\",\"idade\":\"21\",\"cidade\":\"Concórdia\"}";
		URI uri = new URI("/cliente");
		mockMvc
		.perform(MockMvcRequestBuilders
			.post(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(201));
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
		URI uri = new URI("/cliente/removerPeloId/" + cliente.getId());
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
