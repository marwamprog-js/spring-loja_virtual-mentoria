package com.maltadev.mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maltadev.mentoria.lojavirtual.enums.TipoPessoa;
import com.maltadev.mentoria.lojavirtual.exception.ExceptionMentoria;
import com.maltadev.mentoria.lojavirtual.model.Endereco;
import com.maltadev.mentoria.lojavirtual.model.PessoaFisica;
import com.maltadev.mentoria.lojavirtual.model.PessoaJuridica;
import com.maltadev.mentoria.lojavirtual.model.dto.CepDTO;
import com.maltadev.mentoria.lojavirtual.model.dto.ConsultaCnpjDTO;
import com.maltadev.mentoria.lojavirtual.repository.EnderecoRepository;
import com.maltadev.mentoria.lojavirtual.repository.PessoaFisicaRepository;
import com.maltadev.mentoria.lojavirtual.repository.PessoaJuridicaRepository;
import com.maltadev.mentoria.lojavirtual.service.PessoaUserService;
import com.maltadev.mentoria.lojavirtual.service.ServiceContagemAcessoApi;
import com.maltadev.mentoria.lojavirtual.util.ValidaCNPJ;
import com.maltadev.mentoria.lojavirtual.util.ValidaCPF;

@RestController
public class PessoaController {

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;	
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private ServiceContagemAcessoApi apiContagemAcessoApi;
	
		
	
	@ResponseBody
	@GetMapping(value = "**/consultaPessoaFisicaPorNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPessoaFisicaPorNome(@PathVariable("nome") String nome) {
		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorNome(nome.trim().toUpperCase());
		
		apiContagemAcessoApi.atualizaAcessoEndPointPessoaFisica("END-POINT-NOME-PESSOA-FISICA");
		
		return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPessoaFisicaPorCpf/{cpf}")
	public ResponseEntity<List<PessoaFisica>> consultaPessoaFisicaPorCpf(@PathVariable("cpf") String cpf) {
		List<PessoaFisica> fisicas = pessoaFisicaRepository.existeCpfCadastradoList(cpf);
		return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPessoaJuridicaPorNome/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaPessoaJuridicaPorNome(@PathVariable("nome") String nome) {
		List<PessoaJuridica> juridica = pessoaJuridicaRepository.pesquisaPorNome(nome.trim().toUpperCase());
		return new ResponseEntity<List<PessoaJuridica>>(juridica, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPessoaJuridicaPorCnpj/{cnpj}")
	public ResponseEntity<List<PessoaJuridica>> consultaPessoaJuridicaPorCnpj(@PathVariable("cnpj") String cnpj) {
		List<PessoaJuridica> juridicas = pessoaJuridicaRepository.existeCnpjCadastradoList(cnpj);
		return new ResponseEntity<List<PessoaJuridica>>(juridicas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) {
		return new ResponseEntity<CepDTO>(pessoaUserService.consultaCep(cep), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCnpjReceitaWS/{cnpj}")
	public ResponseEntity<ConsultaCnpjDTO> consultaCnpjReceitaWS(@PathVariable("cnpj") String cnpj) {
		return new ResponseEntity<ConsultaCnpjDTO>(pessoaUserService.consultaCnpjReceitaWS(cnpj), HttpStatus.OK);
	}
	
	

	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica)
			throws ExceptionMentoria {

		if (pessoaJuridica == null) {
			throw new ExceptionMentoria("Pessoa Jurídica não pode ser null");
		}
		
		if(pessoaJuridica.getTipoPessoa() == null) {
			throw new ExceptionMentoria("Informe o tipo Jurídico ou Fornecedor da Loja.");
		}
		

		if (pessoaJuridica.getId() == null
				&& pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new ExceptionMentoria("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
		}

		if (pessoaJuridica.getId() == null
				&& pessoaJuridicaRepository.existeInscEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoria(
					"Já existe INSCRIÇÃO ESTADUAL cadastrado com o número: " + pessoaJuridica.getInscEstadual());
		}

		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoria("CNPJ '" + pessoaJuridica.getCnpj() + "' está inválido.");
		}

		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {

			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

				CepDTO dto = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

				pessoaJuridica.getEnderecos().get(p).setBairro(dto.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(dto.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(dto.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(dto.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(dto.getUf());
			}
		} else {

			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();

				if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
					CepDTO dto = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

					pessoaJuridica.getEnderecos().get(p).setBairro(dto.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(dto.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(dto.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setRuaLogradouro(dto.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUf(dto.getUf());
				}
			}

		}

		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping(value = "**/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf(@RequestBody @Valid PessoaFisica pessoaFisica)
			throws ExceptionMentoria {

		if (pessoaFisica == null) {
			throw new ExceptionMentoria("Pessoa Física não pode ser null");
		}
		
		if(pessoaFisica.getTipoPessoa() == null) {
			pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
		}

		if (pessoaFisica.getId() == null && pessoaFisicaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
			throw new ExceptionMentoria("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
		}

		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoria("CNPJ '" + pessoaFisica.getCpf() + "' está inválido.");
		}

		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);

	}

}
