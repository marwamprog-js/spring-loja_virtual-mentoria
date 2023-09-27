package com.maltadev.mentoria.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.maltadev.mentoria.lojavirtual.model.Acesso;
import com.maltadev.mentoria.lojavirtual.repository.AcessoRepository;
import com.maltadev.mentoria.lojavirtual.service.AcessoService;

@SpringBootTest(classes = SpringLojaVirtualMentoriaApplication.class)
class SpringLojaVirtualMentoriaApplicationTests {

	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Test
	public void testCadastraAcesso() {
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		acessoService.save(acesso);
	}

}
