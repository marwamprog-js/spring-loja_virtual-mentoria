package com.maltadev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.maltadev.mentoria.lojavirtual.model.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query("SELECT p FROM Produto p WHERE UPPER(TRIM(p.nome)) LIKE %?1%")
	List<Produto> buscarProdutoNome(String nome);
	
	@Query("SELECT p FROM Produto p WHERE UPPER(TRIM(p.nome)) LIKE %?1% AND empresa_id = ?2")
	List<Produto> buscarProdutoNomeEEmpresa(String nome, Long idEmpresa);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM produto WHERE empresa_id = ?2 AND UPPER(TRIM(nome)) LIKE %?1%")
	List<Produto> ProdutoPorNomeEEmpresa(String nomeCategoria, Long idEmpresa);

}
