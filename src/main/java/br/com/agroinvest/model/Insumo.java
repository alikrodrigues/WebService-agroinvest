package br.com.agroinvest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.agroinvest.util.AdaptadorCategoria;
import br.com.agroinvest.util.AdaptadorUnidadeMedida;

@Entity
@XmlRootElement
public class Insumo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column
	private String descricao;

	@Column(name = "v_canoinhas")
	private BigDecimal valorCanoinhas;

	@Column(name = "v_chapeco")
	private BigDecimal valorChapeco;

	@Column(name = "v_jaragua")
	private BigDecimal valorJaragua;

	@Column(name = "v_joacaba")
	private BigDecimal valorJoacaba;

	@Column(name = "v_lages")
	private BigDecimal valorLages;

	@Column(name = "v_rio_sul")
	private BigDecimal valorRioSul;

	@Column(name = "v_sul_catarinense")
	private BigDecimal valorSulCatarinense;

	@Column(name = "v_sao_mig_oeste")
	private BigDecimal valorSaoMiguelOeste;

	@ManyToOne
	@JoinColumn(name = "unidade_id")
	@XmlJavaTypeAdapter(AdaptadorUnidadeMedida.class)
	private UnidadeMedida unidade;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	@XmlJavaTypeAdapter(AdaptadorCategoria.class)
	private Categoria categoria;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "periodo_id")
	private Periodo periodo;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Insumo)) {
			return false;
		}
		Insumo other = (Insumo) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorCanoinhas() {
		return valorCanoinhas;
	}

	public void setValorCanoinhas(BigDecimal valorCanoinhas) {
		this.valorCanoinhas = valorCanoinhas;
	}

	public BigDecimal getValorChapeco() {
		return valorChapeco;
	}

	public void setValorChapeco(BigDecimal valorChapeco) {
		this.valorChapeco = valorChapeco;
	}

	public BigDecimal getValorJaragua() {
		return valorJaragua;
	}

	public void setValorJaragua(BigDecimal valorJaragua) {
		this.valorJaragua = valorJaragua;
	}

	public BigDecimal getValorJoacaba() {
		return valorJoacaba;
	}

	public void setValorJoacaba(BigDecimal valorJoacaba) {
		this.valorJoacaba = valorJoacaba;
	}

	public BigDecimal getValorLages() {
		return valorLages;
	}

	public void setValorLages(BigDecimal valorLages) {
		this.valorLages = valorLages;
	}

	public BigDecimal getValorRioSul() {
		return valorRioSul;
	}

	public void setValorRioSul(BigDecimal valorRioSul) {
		this.valorRioSul = valorRioSul;
	}

	public BigDecimal getValorSulCatarinense() {
		return valorSulCatarinense;
	}

	public void setValorSulCatarinense(BigDecimal valorSulCatarinense) {
		this.valorSulCatarinense = valorSulCatarinense;
	}

	public BigDecimal getValorSaoMiguelOeste() {
		return valorSaoMiguelOeste;
	}

	public void setValorSaoMiguelOeste(BigDecimal valorSaoMiguelOeste) {
		this.valorSaoMiguelOeste = valorSaoMiguelOeste;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (descricao != null && !descricao.trim().isEmpty())
			result += "descricao: " + descricao;
		return result;
	}

	public UnidadeMedida getUnidade() {
		return this.unidade;
	}

	public void setUnidade(final UnidadeMedida unidade) {
		this.unidade = unidade;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(final Categoria categoria) {
		this.categoria = categoria;
	}

	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(final Periodo periodo) {
		this.periodo = periodo;
	}
}