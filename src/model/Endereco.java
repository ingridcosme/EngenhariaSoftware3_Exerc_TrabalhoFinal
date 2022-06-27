package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "endereco")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotNull
	private int id;
	
	@Column(name = "logradouro", length = 150)
	@NotNull
	private String logradouro = "";
	
	@Column(name = "numero")
	@NotNull
	private int numero = 0;
	
	@Column(name = "bairro", length = 80)
	@NotNull
	private String bairro = "";
	
	@Column(name = "cep", length = 80)
	@NotNull
	private String cep = "";
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "EnderecoPessoa [logradouro=" + logradouro + ", numero=" + numero
				+ ", bairro=" + bairro + ", cep=" + cep + "]";
	}

}