package com.maltadev.mentoria.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceContagemAcessoApi {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void atualizaAcessoEndPointPessoaFisica(String nomeEndPoint) {
		jdbcTemplate.execute("begin; UPDATE tabela_acesso_end_point SET qtd_acesso_end_point = qtd_acesso_end_point + 1 " 
					+ " WHERE nome_end_point = " + nomeEndPoint + "; commit;");
	}
	
}
