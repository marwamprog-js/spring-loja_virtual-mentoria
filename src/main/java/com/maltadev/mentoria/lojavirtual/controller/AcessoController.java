package com.maltadev.mentoria.lojavirtual.controller;

import org.springframework.stereotype.Controller;

import com.maltadev.mentoria.lojavirtual.model.Acesso;
import com.maltadev.mentoria.lojavirtual.service.AcessoService;

@Controller
public class AcessoController {

	private AcessoService acessoService;

	public AcessoController(AcessoService acessoService) {
		super();
		this.acessoService = acessoService;
	}
	
	public Acesso salvarAcesso(Acesso acesso) {
		return acessoService.save(acesso);
	}
	
}
