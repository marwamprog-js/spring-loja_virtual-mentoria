package com.maltadev.mentoria.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.maltadev.mentoria.lojavirtual.controller.PessoaController;
import com.maltadev.mentoria.lojavirtual.exception.ExceptionMentoria;
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
		pessoaJuridica.setNome("Administrador");
		pessoaJuridica.setEmail("admin@teste.com");
		pessoaJuridica.setTelefone("31654989898");
		pessoaJuridica.setInscEstadual("543131354643");
		pessoaJuridica.setInscMunicipal("546541346");
		pessoaJuridica.setNomeFantasia("Teste empresa");
		pessoaJuridica.setRazaoSocial("Teste empresa");
		
		pessoaController.salvarPj(pessoaJuridica);
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf("12345678944");
		pessoaFisica.setNome("Administrador");
		pessoaFisica.setEmail("admin@teste.com");
		pessoaFisica.setTelefone("31654989898");
		
		
	}
	
}
