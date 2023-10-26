package com.maltadev.mentoria.lojavirtual.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maltadev.mentoria.lojavirtual.model.Produto;
import com.maltadev.mentoria.lojavirtual.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private ProdutoRepository produtoRepository;

	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	
	@Transactional
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}

	@Transactional
	public void deleteById(Long id) {
		produtoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<Produto> findById(Long id) {
		return produtoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<Produto> buscarProdutoNome(String nome) {
		return produtoRepository.buscarProdutoNome(nome);
	}
	
	@Transactional(readOnly = true)
	public List<Produto> buscarProdutoNomeEEmpresa(String nome, Long idEmpresa) {
		return produtoRepository.buscarProdutoNomeEEmpresa(nome, idEmpresa);
	}
	
}
