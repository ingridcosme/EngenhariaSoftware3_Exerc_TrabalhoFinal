package model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "aluno")
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotNull
	private int id;
	
	@Column(name = "cpf", length = 11)
	@NotNull
	private String cpf = "";
	
	@Column(name = "nome", length = 100)
	@NotNull
	private String nome = "";
	
	@Column(name = "nascimento")
	@NotNull
	private LocalDate nascimento = LocalDate.now();
	
	@OneToOne(targetEntity = Endereco.class)
	@JoinColumn(name = "id_endereco")
	@NotNull
	private Endereco endereco;
	
	@Column(name = "telefone", length = 11)
	@NotNull
	private String telefone;
	
	@Transient
	private boolean liberado;
	
	@Column(name = "data_max_acesso")
	@NotNull
	private LocalDate dataLimiteDeAcesso;
	
	public Aluno() {
		this.liberado = false;
		this.dataLimiteDeAcesso = LocalDate.of(1990, 01, 01);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public LocalDate getNascimento() {
		return nascimento;
	}
	
	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public boolean isLiberado() {
		return liberado;
	}

	public void setLiberado(boolean liberado) {
		this.liberado = liberado;
	}
	
	public LocalDate getDataLimiteDeAcesso() {
		return dataLimiteDeAcesso;
	}
	
	public void setDataLimiteDeAcesso(LocalDate dataLimiteDeAcesso) {
		this.dataLimiteDeAcesso = dataLimiteDeAcesso;
	}
	
	@Override
	public String toString() {
		return "Aluno [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", nascimento="
				+ nascimento + ", endereco=" + endereco + ", telefone=" + telefone + "]";
	}
	
}
