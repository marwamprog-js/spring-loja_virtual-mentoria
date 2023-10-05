package com.maltadev.mentoria.lojavirtual.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maltadev.mentoria.lojavirtual.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "SELECT u FROM Usuario u WHERE u.login = ?1")
	Usuario findUserByLogin(String login);

	@Query(value = "SELECT u FROM Usuario u WHERE u.pessoa.id = ?1 OR u.login = ?2")
	Usuario findUserByPessoa(Long id, String email);

	@Query(value = "SELECT constraint_name FROM information_schema.constraint_column_usage \n"
			+ "WHERE table_name = 'usuarios_acesso' and column_name = 'acesso_id'\n"
			+ "and constraint_name <> 'unique_acesso_user'; ", nativeQuery = true)
	String consultaConstraintAcesso();

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO usuarios_acesso(usuario_id, acesso_id) VALUES (?1, (SELECT id FROM acesso WHERE descricao = 'ROLE_USER'))", nativeQuery = true)
	void insereAcessoUserPj(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO usuarios_acesso(usuario_id, acesso_id) VALUES (?1, (SELECT id FROM acesso WHERE descricao = ?2))", nativeQuery = true)
	void insereAcessoUserPj(Long id, String acesso);
	
	@Query("SELECT u FROM Usuario u WHERE u.dataAtualSenha <= CURRENT_DATE - 90")
	List<Usuario> usuarioSenhaVencida();
	
}
