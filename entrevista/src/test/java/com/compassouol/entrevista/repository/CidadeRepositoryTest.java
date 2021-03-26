package com.compassouol.entrevista.repository;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.compassouol.entrevista.controller.form.FormCadastroCidade;
import com.compassouol.entrevista.model.Cidade;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CidadeRepositoryTest {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	private Cidade cidade;
	
	// Início Teste
	@Test
	public void deveBuscarCidadePeloNome() {
		this.cidade = new FormCadastroCidade("Xaxim", "SC").toCidade();
		cidadeRepository.save(this.cidade);
		Optional<Cidade> cidadeRetornada = cidadeRepository.findByNomeIgnoreCase("Xaxim");
		Assert.assertNotNull(cidadeRetornada.get());
		Assert.assertEquals(this.cidade.getNome(), cidadeRetornada.get().getNome());
	}
	
	@Test
	public void naoDeveEncontrarCidadePeloNome() {
		Optional<Cidade> cidadeRetornada = cidadeRepository.findByNomeIgnoreCase("Florianópolis");
		Assert.assertTrue(!cidadeRetornada.isPresent());
	}
	
	@Test
	public void deveBuscarCidadesPeloEstado() {
		this.cidade = new FormCadastroCidade("Pelotas", "RS").toCidade();
		cidadeRepository.save(this.cidade);
		
		this.cidade = new FormCadastroCidade("Canoas", "RS").toCidade();
		cidadeRepository.save(this.cidade);
		
		this.cidade = new FormCadastroCidade("Canela", "RS").toCidade();
		cidadeRepository.save(this.cidade);

		Optional<List<Cidade>> cidadesRetornada = cidadeRepository.findByEstadoIgnoreCase("RS");
		Assert.assertEquals(cidadesRetornada.get().get(0).getNome(), "Pelotas");
		Assert.assertEquals(cidadesRetornada.get().get(1).getNome(), "Canoas");
		Assert.assertEquals(cidadesRetornada.get().get(2).getNome(), "Canela");
		Assert.assertEquals(cidadesRetornada.get().size(), 3);
	}
	
	@Test
	public void naoDeveBuscarCidadePeloEstado() {
		Optional<List<Cidade>> cidadesRetornada = cidadeRepository.findByEstadoIgnoreCase("PA");
		Assert.assertEquals(cidadesRetornada.get().size(), 0);
	}
	
	@Test
	public void deveBuscarCidadePeloId() {
		this.cidade = new FormCadastroCidade("Chapecó", "SC").toCidade();
		cidadeRepository.save(this.cidade);
		
		Optional<Cidade> cidadeRetornada = cidadeRepository.findById(this.cidade.getId());
		Assert.assertTrue(cidadeRetornada.isPresent());
		Assert.assertTrue(cidadeRetornada.get().getEstado().equals("SC"));
		Assert.assertTrue(cidadeRetornada.get().getNome().equals("Chapecó"));
	}
	
}
