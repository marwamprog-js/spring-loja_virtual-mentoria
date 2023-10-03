package com.maltadev.mentoria.lojavirtual.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.maltadev.mentoria.lojavirtual.config.ApplicationContextLoad;
import com.maltadev.mentoria.lojavirtual.model.Usuario;
import com.maltadev.mentoria.lojavirtual.repository.UsuarioRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/*Criar a autenticacao e retornar também a autenticacao JWT*/
@Service
public class JWTTokenAutenticacaoService {

	/* Token de validade de 30 dias */
	private static final long EXPIRATION_TIME = 959990000;

	/* Chave de senha para juntar com o JWT */
	private static final String SECRET = "sskhgkj---ihgk-sajdf1456sd";

	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	/**
	 * GERA TOKEN
	 * 
	 * Gera token e da a resposta para o cliente com JWT
	 * 
	 * @param response
	 * @param username
	 * @throws Exception
	 */
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {

		/* Montagem do token */
		String JWT = Jwts.builder() /* Chama o gerador de token */
				.setSubject(username) /* Adiciona o iser */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* Tempo de expiração */
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		/* Bearer f4*+98544... */
		String token = TOKEN_PREFIX + " " + JWT;

		/*
		 * Dá resposta para tela e para o cliente, outra api, navegador, aplicativo...
		 */
		response.addHeader(HEADER_STRING, token);

		/* Liberando o Cors */
		liberacaoCors(response);

		/* Usado para ver no postman para teste */
		response.getWriter().write("{\"Authorization\":\"" + token + "\"}");
	}

	/**
	 * Pega Token da requisição
	 * 
	 * Método que retorna o usuário com token ou caso não seja validado retorna null
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public Authentication getAuthenticacao(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String token = request.getHeader(HEADER_STRING);

		try {

			if (token != null) {

				/* Retira o Bearer */
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

				/* Faz a validação do token do usuário na requisição e obter o USER */
				String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(tokenLimpo).getBody().getSubject();

				if (user != null) {

					Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
							.findUserByLogin(user);

					if (usuario != null) {
						return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),
								usuario.getAuthorities());
					}

				}

			}

		} catch (SignatureException e) {
			response.getWriter().write("Token está inválido");
		} catch (ExpiredJwtException e) {
			response.getWriter().write("Token está expirado, efetue o login novamente.");
		} finally {
			liberacaoCors(response);
		}

		return null;
	}

	/* Fazendo liberação contra erro de Cors no navegador */
	private void liberacaoCors(HttpServletResponse response) {

		/* Origin */
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}

		/* Headers */
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}

		/* Request Headers */
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}

		/* Method */
		if (response.getHeader("Access-Control-Allow-Method") == null) {
			response.addHeader("Access-Control-Allow-Method", "*");
		}
	}

}
