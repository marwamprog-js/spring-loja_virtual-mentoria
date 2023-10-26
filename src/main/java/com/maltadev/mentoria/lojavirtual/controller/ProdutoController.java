package com.maltadev.mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.maltadev.mentoria.lojavirtual.model.Produto;
import com.maltadev.mentoria.lojavirtual.service.ProdutoService;

@Controller
@RestController
public class ProdutoController {

	private ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		super();
		this.produtoService = produtoService;
	}
	
	
	@ResponseBody /*Poder dar um retorno da API*/
	@PostMapping(value = "**/salvarProduto")
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoria {
		
		if(produto.getEmpresa() == null || produto.getEmpresa().getId() < 0) {
			throw new ExceptionMentoria("Empresa responsável deve ser informada");
		}
		
		if(produto.getId() == null) {			
			List<Produto> produtos = produtoService.buscarProdutoNomeEEmpresa(produto.getNome().toUpperCase(), produto.getEmpresa().getId());
			
			if(!produtos.isEmpty()) {
				throw new ExceptionMentoria("Já existe Produto com o nome: " + produto.getNome());
			}
		}
		
		
		if(produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() < 0) {
			throw new ExceptionMentoria("Categoria deve ser informada");
		}
		
		if(produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() < 0) {
			throw new ExceptionMentoria("Marca deve ser informada");
		}
		
		
		Produto produtoSalvo = produtoService.save(produto);
		
		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*Poder dar um retorno da API*/
	@PostMapping(value = "**/deleteProduto")
	public ResponseEntity<String> deleteProduto(@RequestBody Produto produto) {
		
		produtoService.deleteById(produto.getId());
		
		return new ResponseEntity<String>("Produto removido.", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteProdutoPorId/{id}")
	public ResponseEntity<String> deletePorIdProduto(@PathVariable("id") Long id) {
		
		produtoService.deleteById(id);
		
		return new ResponseEntity<String>("Produto removido.", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterProduto/{id}")
	public ResponseEntity<Produto> obterPorIdProduto(@PathVariable("id") Long id) throws ExceptionMentoria {
		
		Produto produto = produtoService.findById(id)
				.orElseThrow( () -> new ExceptionMentoria("Não encontrou produto com código: " + id));
		
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterPorNomeProduto/{nome}")
	public ResponseEntity<List<Produto>> obterPorIdProduto(@PathVariable("nome") String nome) {
		
		List<Produto> produtos = produtoService.buscarProdutoNome(nome.toUpperCase());
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
}
