package com.compassouol.entrevista.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compassouol.entrevista.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	Optional<Cidade> findByNomeIgnoreCase(String nome);

	Optional<List<Cidade>> findByEstadoIgnoreCase(String estado);

}
