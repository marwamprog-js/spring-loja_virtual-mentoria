/*Campo -> pessoa_id*/
CREATE OR REPLACE FUNCTION validaChavePessoa()
	RETURNS TRIGGER
	LANGUAGE PLPGSQL
AS $$

declare existe integer;

BEGIN
	existe = (SELECT COUNT(1) FROM pessoa_fisica WHERE id = NEW.pessoa_id);
	if(existe <= 0) then
		existe = (SELECT COUNT(1) FROM pessoa_juridica WHERE id = NEW.pessoa_id);
	if(existe <= 0) then
		RAISE EXCEPTION 'Não foi encontrado o ID ou PK da pessoa para realizar a associação.';
	end if;
	end if;
	RETURN NEW;
END;
$$

/*Campo -> pessoa_forn_fk*/
CREATE OR REPLACE FUNCTION validaChavePessoaFornFk()
	RETURNS TRIGGER
	LANGUAGE PLPGSQL
AS $$

declare existe integer;

BEGIN
	existe = (SELECT COUNT(1) FROM pessoa_fisica WHERE id = NEW.pessoa_forn_fk);
	if(existe <= 0) then
		existe = (SELECT COUNT(1) FROM pessoa_juridica WHERE id = NEW.pessoa_forn_fk);
	if(existe <= 0) then
		RAISE EXCEPTION 'Não foi encontrado o ID ou PK da pessoa para realizar a associação.';
	end if;
	end if;
	RETURN NEW;
END;
$$

/*NEW = Carrega os dados de insert e update*/
/*OLD = Carrega os dados da linha antiga antes de atualizar*/

/*Avaliacao Produto*/
CREATE TRIGGER validaChavePessoaUpdate
BEFORE UPDATE
ON avaliacao_produto
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaInsert
BEFORE INSERT
ON avaliacao_produto
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

/*Conta Pagar*/
CREATE TRIGGER validaChavePessoaUpdate
BEFORE UPDATE
ON conta_pagar
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaInsert
BEFORE INSERT
ON conta_pagar
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaUpdateFk
BEFORE UPDATE
ON conta_pagar
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoaFornFk();

CREATE TRIGGER validaChavePessoaInsertFk
BEFORE INSERT
ON conta_pagar
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoaFornFk();

/*Conta Receber*/
CREATE TRIGGER validaChavePessoaUpdate
BEFORE UPDATE
ON conta_receber
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaInsert
BEFORE INSERT
ON conta_receber
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

/*Endereco*/
CREATE TRIGGER validaChavePessoaUpdate
BEFORE UPDATE
ON endereco
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaInsert
BEFORE INSERT
ON endereco
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

/*Nota Fiscal de Compra*/
CREATE TRIGGER validaChavePessoaUpdate
BEFORE UPDATE
ON nota_fiscal_compra
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaInsert
BEFORE INSERT
ON nota_fiscal_compra
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

/*Usuário*/
CREATE TRIGGER validaChavePessoaUpdate
BEFORE UPDATE
ON usuario
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaInsert
BEFORE INSERT
ON usuario
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

/*Venda Compra Loja Virtual*/
CREATE TRIGGER validaChavePessoaUpdate
BEFORE UPDATE
ON vd_cp_loja_virt
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaInsert
BEFORE INSERT
ON vd_cp_loja_virt
FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();