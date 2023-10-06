package com.maltadev.mentoria.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maltadev.mentoria.lojavirtual.model.PessoaFisica;
import com.maltadev.mentoria.lojavirtual.model.PessoaJuridica;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

	
	@Query("SELECT p FROM PessoaFisica p WHERE p.cpf = ?1")
	public PessoaJuridica existeCpfCadastrado(String cpf);
	
}
