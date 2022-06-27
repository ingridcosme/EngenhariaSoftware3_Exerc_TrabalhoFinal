package model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pagamento")
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotNull
	private int id;
	
	@ManyToOne(targetEntity = Aluno.class)
	@JoinColumn(name = "id_aluno")
	@NotNull
	private Aluno aluno;
	
	@Transient
	private String nomeAluno;
	
	@Column(name = "data")
	@NotNull
	private LocalDate dataPagamento;
	
	@Column(name = "valor")
	@NotNull
	private double valorPagamento;
	
	@Column(name = "tipo")
	@NotNull
	private String tipoPagamento;
	
	@Column(name = "info_adicional")
	@NotNull
	private String infoAdicional;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Aluno getAluno() {
		return aluno;
	}
	
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
		this.nomeAluno = aluno.getNome();
	}
	
	public String getNomeAluno() {
		return nomeAluno;
	}
	
	public LocalDate getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public double getValorPagamento() {
		return valorPagamento;
	}
	
	public void setValorPagamento(double valorPagamento) {
		this.valorPagamento = valorPagamento;
	}
	
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	
	public String getInfoAdicional() {
		return infoAdicional;
	}
	
	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Pagamento");
		buffer.append("\nId: " + id);
		buffer.append("\nAluno: " + nomeAluno);
		buffer.append("\nData Pagamento: " + dataPagamento);
		buffer.append("\nValor: R$ " + valorPagamento);
		buffer.append("\nForma de Pagamento: " + tipoPagamento);
		buffer.append("\nInformações Adicionais: " + infoAdicional);
		return buffer.toString();
	}
	
}
