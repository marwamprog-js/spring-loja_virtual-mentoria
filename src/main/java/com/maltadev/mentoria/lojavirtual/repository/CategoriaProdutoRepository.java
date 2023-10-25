package com.maltadev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maltadev.mentoria.lojavirtual.model.CategoriaProduto;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM categoria_produto WHERE UPPER(TRIM(nome_desc)) = :categoria")
	public boolean existeCategoria(@Param("categoria") String  categoria);

	@Query("SELECT c FROM CategoriaProduto c WHERE UPPER(TRIM(c.nomeDesc)) like %:nomeDesc%")
	public List<CategoriaProduto> buscarCategoriaDescricao(@Param("nomeDesc") String nomeDesc);

	
}
