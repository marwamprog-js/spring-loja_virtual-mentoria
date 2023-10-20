package com.maltadev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maltadev.mentoria.lojavirtual.model.PessoaFisica;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

	@Query("SELECT pf FROM PessoaFisica pf WHERE UPPER(TRIM(pf.nome)) LIKE %?1%")
	public List<PessoaFisica> pesquisaPorNome(String nome);
	
	@Query("SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
	public PessoaFisica existeCpfCadastrado(String cpf);
	
	@Query("SELECT pf FROM PessoaFisica pf WHERE pf.cpf = ?1")
	public List<PessoaFisica> existeCpfCadastradoList(String cpf);
	
}
