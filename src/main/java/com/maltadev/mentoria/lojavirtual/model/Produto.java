package com.maltadev.mentoria.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;

	@NotNull(message = "O TIPO DA UNIDADE deve ser informado")
	@Column(name = "tipo_unidade", nullable = false)
	private String tipoUnidade;

	@NotNull(message = "Nome do PRODUTO deve ser informado")
	@Size(min = 10, message = "Nome do produto deve ter mais de {min} letras")
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotNull(message = "Descrição do PRODUTO deve ser informado")
	@Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
	private String descricao;

	/** Nota item nota produto */
//	@ManyToOne
//	@JoinColumn(name = "nota_item_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_item_produto_fk"))
//	private NotaItemProduto notaItemProduto;

	@NotNull(message = "Peso do PRODUTO deve ser informado")
	@Column(name = "peso", nullable = false)
	private Double peso;

	@NotNull(message = "Largura do PRODUTO deve ser informado")
	@Column(name = "largura", nullable = false)
	private Double largura;

	@NotNull(message = "ALtura do PRODUTO deve ser informado")
	@Column(name = "altura", nullable = false)
	private Double altura;

	@NotNull(message = "Profundidade do PRODUTO deve ser informado")
	@Column(name = "profundidade", nullable = false)
	private Double profundidade;

	@NotNull(message = "Valor da venda do PRODUTO deve ser informado")
	@Column(name = "valor_venda", nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;

	@Column(name = "qtd_estoque", nullable = false)
	private Integer qtdEstoque = 0;

	@Column(name = "senha")
	private Integer qtdAlertaEstoque = 0;

	@Column(name = "link_youtube")
	private String linkYoutube;

	@Column(name = "alerta_qtde_estoque")
	private Boolean alertaQtdeEstoque = Boolean.FALSE;

	@Column(name = "qtde_clique")
	private Integer qtdeClique = 0;

	@Column(name = "ativo", nullable = false)
	private Boolean ativo = Boolean.TRUE;

	@NotNull(message = "Empresa responsável pelo PRODUTO deve ser informado")
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
	private PessoaJuridica empresa;
	
	@NotNull(message = "Categoria do PRODUTO deve ser informado")
	@ManyToOne(targetEntity = CategoriaProduto.class)
	@JoinColumn(name = "categoria_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoria_produto_id_fk"))
	private CategoriaProduto categoriaProduto;
	
	@NotNull(message = "Marca do PRODUTO deve ser informado")
	@ManyToOne(targetEntity = MarcaProduto.class)
	@JoinColumn(name = "marca_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "marca_produto_id_fk"))
	private MarcaProduto marcaProduto;
	
	@NotNull(message = "Nota Item do PRODUTO deve ser informado")
	@ManyToOne(targetEntity = NotaItemProduto.class)
	@JoinColumn(name = "nota_item_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_item_produto_id_fk"))
	private NotaItemProduto notaItemProduto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public NotaItemProduto getNotaItemProduto() {
		return notaItemProduto;
	}
	
	public void setNotaItemProduto(NotaItemProduto notaItemProduto) {
		this.notaItemProduto = notaItemProduto;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public String getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public Integer getQtdAlertaEstoque() {
		return qtdAlertaEstoque;
	}

	public void setQtdAlertaEstoque(Integer qtdAlertaEstoque) {
		this.qtdAlertaEstoque = qtdAlertaEstoque;
	}

	public String getLinkYoutube() {
		return linkYoutube;
	}

	public void setLinkYoutube(String linkYoutube) {
		this.linkYoutube = linkYoutube;
	}

	public Boolean getAlertaQtdeEstoque() {
		return alertaQtdeEstoque;
	}

	public void setAlertaQtdeEstoque(Boolean alertaQtdeEstoque) {
		this.alertaQtdeEstoque = alertaQtdeEstoque;
	}

	public Integer getQtdeClique() {
		return qtdeClique;
	}

	public void setQtdeClique(Integer qtdeClique) {
		this.qtdeClique = qtdeClique;
	}

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}
	
	public MarcaProduto getMarcaProduto() {
		return marcaProduto;
	}

	public void setMarcaProduto(MarcaProduto marcaProduto) {
		this.marcaProduto = marcaProduto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + "]";
	}

}
