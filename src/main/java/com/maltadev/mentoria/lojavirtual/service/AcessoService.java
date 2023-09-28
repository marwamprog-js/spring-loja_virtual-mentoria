package com.maltadev.mentoria.lojavirtual.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maltadev.mentoria.lojavirtual.model.Acesso;
import com.maltadev.mentoria.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {

	private AcessoRepository acessoRepository;

	public AcessoService(AcessoRepository acessoRepository) {
		this.acessoRepository = acessoRepository;
	}
	
	
	@Transactional
	public Acesso save(Acesso acesso) {
		return acessoRepository.save(acesso);
	}

	@Transactional
	public void deleteById(Long id) {
		acessoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Acesso findById(Long id) {
		return acessoRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public List<Acesso> buscarAcessoDescricao(String descricao) {
		return acessoRepository.buscarAcessoDescricao(descricao);
	}
	
}
