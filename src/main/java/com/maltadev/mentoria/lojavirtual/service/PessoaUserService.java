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

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {
		
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
			
		}
		
		return pessoaJuridica;
		
	}
	
}
