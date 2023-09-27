package com.maltadev.mentoria.lojavirtual.service;

import org.springframework.stereotype.Service;

import com.maltadev.mentoria.lojavirtual.model.Acesso;
import com.maltadev.mentoria.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {

	private AcessoRepository acessoRepository;

	public AcessoService(AcessoRepository acessoRepository) {
		this.acessoRepository = acessoRepository;
	}
	
	
	public Acesso save(Acesso acesso) {
		return acessoRepository.save(acesso);
	}
	
}
