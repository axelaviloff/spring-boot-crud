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

import com.compassouol.entrevista.model.Cidade;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CidadeRepositoryTest {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	public void deveBuscarCidadePeloNome() {
		Cidade xaxim = new Cidade();
		xaxim.setEstado("SC");
		xaxim.setNome("Xaxim");
		em.persist(xaxim);
		Optional<Cidade> cidadeRetornada = cidadeRepository.findByNomeIgnoreCase("Xaxim");
		Assert.assertNotNull(cidadeRetornada.get());
		Assert.assertEquals(xaxim.getNome(), cidadeRetornada.get().getNome());
		
	}
	
	@Test
	public void naoDeveEncontrarCidadePeloNome() {
		Optional<Cidade> cidadeRetornada = cidadeRepository.findByNomeIgnoreCase("Florianópolis");
		Assert.assertTrue(!cidadeRetornada.isPresent());
	}
	
	@Test
	public void deveBuscarCidadesPeloEstado() {
		Cidade pelotas = new Cidade();
		pelotas.setEstado("RS");
		pelotas.setNome("Pelotas");
		Cidade canoas = new Cidade();
		canoas.setEstado("RS");
		canoas.setNome("Canoas");
		Cidade canela = new Cidade();
		canela.setEstado("RS");
		canela.setNome("Canela");
;
		em.persist(pelotas);
		em.persist(canoas);
		em.persist(canela);
		
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
		Cidade chapeco = new Cidade();
		chapeco.setEstado("SC");
		chapeco.setNome("Chapecó");
		em.persist(chapeco);
		
		Optional<Cidade> cidadeRetornada = cidadeRepository.findById(chapeco.getId());
		Assert.assertTrue(cidadeRetornada.isPresent());
		Assert.assertTrue(cidadeRetornada.get().getEstado().equals("SC"));
		Assert.assertTrue(cidadeRetornada.get().getNome().equals("Chapecó"));
	}
	
}
