package com.maltadev.mentoria.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.maltadev.mentoria.lojavirtual.controller.PessoaController;
import com.maltadev.mentoria.lojavirtual.enums.TipoEndereco;
import com.maltadev.mentoria.lojavirtual.exception.ExceptionMentoria;
import com.maltadev.mentoria.lojavirtual.model.Endereco;
import com.maltadev.mentoria.lojavirtual.model.PessoaFisica;
import com.maltadev.mentoria.lojavirtual.model.PessoaJuridica;
import com.maltadev.mentoria.lojavirtual.repository.PessoaRepository;
import com.maltadev.mentoria.lojavirtual.service.PessoaUserService;

import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = SpringLojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase{

	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaController pessoaController;
	
	
	@Test
	public void testCadPessoaFisica() throws ExceptionMentoria {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Marwam Malta");
		pessoaJuridica.setEmail("marwamprog.js@gmail.com");
		pessoaJuridica.setTelefone("31654989898");
		pessoaJuridica.setInscEstadual("543131354643");
		pessoaJuridica.setInscMunicipal("546541346");
		pessoaJuridica.setNomeFantasia("Teste empresa");
		pessoaJuridica.setRazaoSocial("Teste empresa");
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("1324567987");
		endereco1.setComplemento("Casa");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("36");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogradouro("Rua teste");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setCidade("Curitiba");
		endereco1.setUf("PR");
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Carlos Chaves");
		endereco2.setCep("1155285");
		endereco2.setComplemento("Casa 2");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("38");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogradouro("Rua mais um");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setCidade("Curitiba");
		endereco2.setUf("PR");
		
		pessoaJuridica.getEnderecos().add(endereco1);
		pessoaJuridica.getEnderecos().add(endereco2);
		
		pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();
		
		assertEquals(true, pessoaJuridica.getId() > 0);
		
		for(Endereco endereco : pessoaJuridica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaJuridica.getEnderecos().size());
		
		
	}
	
}
