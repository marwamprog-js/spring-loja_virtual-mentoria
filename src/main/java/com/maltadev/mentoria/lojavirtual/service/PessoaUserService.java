package com.maltadev.mentoria.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.maltadev.mentoria.lojavirtual.model.PessoaJuridica;
import com.maltadev.mentoria.lojavirtual.model.Usuario;
import com.maltadev.mentoria.lojavirtual.repository.PessoaRepository;
import com.maltadev.mentoria.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUserService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {
		
		//pessoaJuridica = pessoaRepository.save(pessoaJuridica);
		
		for(int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}
		
		pessoaJuridica = pessoaRepository.save(pessoaJuridica);
		
		Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());
		
		if(usuarioPj == null) {
			
			String constraint = usuarioRepository.consultaConstraintAcesso();
			
			if(constraint != null) {
				jdbcTemplate.execute("BEGIN; ALTER TABLE usuarios_acesso DROP CONSTRAINT " + constraint + "; commit;");
			}
			
			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(pessoaJuridica);
			usuarioPj.setPessoa(pessoaJuridica);
			usuarioPj.setLogin(pessoaJuridica.getEmail());
			
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			usuarioPj.setSenha(senhaCript);
			usuarioPj = usuarioRepository.save(usuarioPj);
			
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId());
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");
			
			/* Fazer o envio de e-mail do login e da senha */
			StringBuilder menssagemHtml = new StringBuilder();
			
			menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>");
			menssagemHtml.append("<b>Login: </b>" + pessoaJuridica.getEmail() + "</br>");
			menssagemHtml.append("<b>Senha: </b>" + senha + "</br></br>");
			menssagemHtml.append("Obrigado!</br>");
			
//			try {
//				serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual.", menssagemHtml.toString(), pessoaJuridica.getEmail());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
		}
		
		return pessoaJuridica;
		
	}
	
}
