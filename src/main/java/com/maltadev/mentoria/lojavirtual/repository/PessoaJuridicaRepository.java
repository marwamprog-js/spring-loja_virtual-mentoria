package com.maltadev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maltadev.mentoria.lojavirtual.model.PessoaJuridica;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

	@Query("SELECT pj FROM PessoaJuridica pj WHERE UPPER(TRIM(pj.nome)) LIKE %?1%")
	public List<PessoaJuridica> pesquisaPorNome(String nome);
	
	@Query("SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
	public PessoaJuridica existeCnpjCadastrado(String cnpj);
	
	@Query("SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
	public List<PessoaJuridica> existeCnpjCadastradoList(String cnpj);
	
	@Query("SELECT pj FROM PessoaJuridica pj WHERE pj.inscEstadual = ?1")
	public PessoaJuridica existeInscEstadualCadastrado(String inscEstadual);
	
	@Query("SELECT pj FROM PessoaJuridica pj WHERE pj.inscEstadual = ?1")
	public List<PessoaJuridica> existeInscEstadualCadastradoList(String inscEstadual);
		
}
