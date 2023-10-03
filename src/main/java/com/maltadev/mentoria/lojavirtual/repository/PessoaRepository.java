package com.maltadev.mentoria.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maltadev.mentoria.lojavirtual.model.PessoaJuridica;

public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {

	@Query("SELECT p FROM PessoaJuridica p WHERE p.cnpj = ?1")
	public PessoaJuridica existeCnpjCadastrado(String cnpj);
	
}
