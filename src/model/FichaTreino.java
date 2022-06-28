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
@Table(name = "ficha_treino")
public class FichaTreino {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotNull
	private int id;
	
	@Column(name = "data")
	@NotNull
	private LocalDate data;
	
	@ManyToOne(targetEntity = Professor.class)
	@JoinColumn(name = "id_professor")
	@NotNull
	private Professor professor;
	
	@Transient
	private String nomeProfessor;
	
	@ManyToOne(targetEntity = Aluno.class)
	@JoinColumn(name = "id_aluno")
	@NotNull
	private Aluno aluno;
	
	@Transient
	private String nomeAluno;
	
	@Column(name = "tipo")
	@NotNull
	private String tipo;
	
	@Column(name = "info_adicional")
	@NotNull
	private String infoAdicional;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
		this.nomeProfessor = professor.getNome();
	}
	
	public String getNomeProfessor() {
		return nomeProfessor;
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
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		buffer.append("Ficha de Treino");
		buffer.append("\nId: " + id);
		buffer.append("\nProfessor: " + nomeProfessor);
		buffer.append("\nAluno: " + nomeAluno);
		buffer.append("\nData Treino: " + data);
		buffer.append("\nTipo de Treino: " + tipo);
		buffer.append("\nInformações Adicionais: " + infoAdicional);
		return buffer.toString();
	}
	
}
