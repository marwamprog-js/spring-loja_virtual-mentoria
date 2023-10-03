package com.maltadev.mentoria.lojavirtual.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maltadev.mentoria.lojavirtual.exception.ExceptionMentoria;
import com.maltadev.mentoria.lojavirtual.model.Acesso;
import com.maltadev.mentoria.lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {

	private AcessoService acessoService;

	public AcessoController(AcessoService acessoService) {
		super();
		this.acessoService = acessoService;
	}
	
	
	@ResponseBody /*Poder dar um retorno da API*/
	@PostMapping(value = "**/salvarAcesso")
	public ResponseEntity<?> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoria {
		
		if(acesso.getId() == null) {			
			List<Acesso> acessos = acessoService.buscarAcessoDescricao(acesso.getDescricao().toUpperCase());
			
			if(!acessos.isEmpty()) {
				throw new ExceptionMentoria("Já existe Acesso com a descrição: " + acesso.getDescricao());
			}
		}
		
		
		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*Poder dar um retorno da API*/
	@PostMapping(value = "**/deleteAcesso")
	public ResponseEntity<Acesso> deleteAcesso(@RequestBody Acesso acesso) {
		
		acessoService.deleteById(acesso.getId());
		
		return new ResponseEntity("Acesso removido.", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteAcessoPorId/{id}")
	public ResponseEntity<Acesso> deletePorIdAcesso(@PathVariable("id") Long id) {
		
		acessoService.deleteById(id);
		
		return new ResponseEntity("Acesso removido.", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterPorIdAcesso(@PathVariable("id") Long id) throws ExceptionMentoria {
		
		Acesso acesso = acessoService.findById(id).orElse(null);
		
		if(acesso == null) {
			throw new ExceptionMentoria("Não encontrou acesso com código: " + id);
		}
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterPorDescricaoAcesso/{descricao}")
	public ResponseEntity<List<Acesso>> obterPorIdAcesso(@PathVariable("descricao") String descricao) {
		
		List<Acesso> acessos = acessoService.buscarAcessoDescricao(descricao.toUpperCase());
		
		return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
	}
	
}
