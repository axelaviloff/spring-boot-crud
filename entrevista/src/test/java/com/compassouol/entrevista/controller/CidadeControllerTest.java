package com.compassouol.entrevista.controller;
import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.compassouol.entrevista.controller.form.FormCadastroCidade;
import com.compassouol.entrevista.repository.CidadeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations="classpath:application-test.properties")

public class CidadeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	// Início Testes
	@Test
	public void consultarCidadePeloNomeDeveRetornar200() throws Exception {
		criarESalvarCidade("Itapema", "SC");
		
		URI uri = new URI("/cidade/buscarPeloNome/Itapema");
		
		mockMvc
		.perform(MockMvcRequestBuilders
			.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(200));
		
	}
	
	@Test
	public void consultarCidadePeloNomeDeveRetornar404() throws Exception {
		URI uri = new URI("/cidade/buscarPeloNome/Chapecó");
		mockMvc
		.perform(MockMvcRequestBuilders
			.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(404));
		
	}
	
	@Test
	public void consultarCidadePeloEstadoDeveRetornar200() throws Exception {
		criarESalvarCidade("Curitiba", "PR");
		criarESalvarCidade("Maringá", "PR");
		
		URI uri = new URI("/cidade/buscarPeloEstado/PR");
		
		mockMvc
		.perform(MockMvcRequestBuilders
			.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(200));
		
	}
	
	@Test
	public void consultarCidadePeloEstadoDeveRetornar404() throws Exception {
		
		URI uri = new URI("/cidade/buscarPeloEstado/MS");
		
		mockMvc
		.perform(MockMvcRequestBuilders
			.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(404));
		
	}
	
	@Test
	public void salvarCidadeRetorna201() throws Exception {
		URI uri = new URI("/cidade");
		
		String json = "{\"nome\":\"Xaxim\",\"estado\":\"SC\"}";
		
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
	public void salvarCidadeRetorna400() throws Exception {
		URI uri = new URI("/cidade");
		
		String json = "{\"nome\":\"\",\"estado\":\"SC\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
			.post(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
			.status()
			.is(400));
		
	}
	
	private void criarESalvarCidade(String nome, String estado) {
		cidadeRepository.save(new FormCadastroCidade(nome, estado).toCidade());
	}

}
