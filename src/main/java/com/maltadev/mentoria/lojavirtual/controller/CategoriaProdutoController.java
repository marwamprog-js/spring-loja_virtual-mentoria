package com.maltadev.mentoria.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maltadev.mentoria.lojavirtual.exception.ExceptionMentoria;
import com.maltadev.mentoria.lojavirtual.model.CategoriaProduto;
import com.maltadev.mentoria.lojavirtual.model.dto.CategoriaProdutoDTO;
import com.maltadev.mentoria.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
public class CategoriaProdutoController {

	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@ResponseBody
	@GetMapping(value = "**/obterPorDescricaoCategoria/{descricao}")
	public ResponseEntity<List<CategoriaProduto>> obterPorIdCategoria(@PathVariable("descricao") String descricao) {
		
		List<CategoriaProduto> categoriaProduto = categoriaProdutoRepository.buscarCategoriaDescricao(descricao.toUpperCase());
		
		return new ResponseEntity<List<CategoriaProduto>>(categoriaProduto, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@PostMapping(value = "**/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDTO> salvarCategoria(@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoria {
		
		if(categoriaProduto.getEmpresa() == null || (categoriaProduto.getEmpresa().getId() == null || categoriaProduto.getEmpresa().getId() < 0)) {
			throw new ExceptionMentoria("A empresa deve ser informada.");
		}
		
		if(categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase().trim())) {
			throw new ExceptionMentoria("Não pode cadastrar CATEGORIA com o mesmo nome.");
		}
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		CategoriaProdutoDTO dto = new CategoriaProdutoDTO();
		dto.setId(categoriaSalva.getId());
		dto.setNomeDesc(categoriaSalva.getNomeDesc());
		dto.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
		
		return new ResponseEntity<CategoriaProdutoDTO>(dto, HttpStatus.OK);
	}
	
	
	@ResponseBody /*Poder dar um retorno da API*/
	@PostMapping(value = "**/deleteCategoria")
	public ResponseEntity<?> deleteAcesso(@RequestBody CategoriaProduto categoriaProduto) {
		
		categoriaProdutoRepository.deleteById(categoriaProduto.getId());
		
		return new ResponseEntity("Categoria removido.", HttpStatus.OK);
	}
	
}
