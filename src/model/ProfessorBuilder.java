package model;

import java.time.LocalDate;

public class ProfessorBuilder {

    private Professor professor;
    private Endereco endereco;
    
    public ProfessorBuilder() {
        this.professor = new Professor();
        this.endereco = new Endereco();
    }
    
    public static ProfessorBuilder builder() {
        return new ProfessorBuilder();
    }
    
    public ProfessorBuilder addId(int id) {
    	this.professor.setId(id);
    	return this;
    }
    
    public ProfessorBuilder addNome(String nome) {
        this.professor.setNome(nome);
        return this;
    }
    
    public ProfessorBuilder addCpf(String cpf) {
    	this.professor.setCpf(cpf);
    	return this;
    }

    public ProfessorBuilder addEndereco(String logradouro, int numero, String bairro, String cep) {
    	this.endereco.setLogradouro(logradouro);
    	this.endereco.setNumero(numero);
    	this.endereco.setBairro(bairro);
    	this.endereco.setCep(cep);
    	return this;
    }
    
    public ProfessorBuilder addEndereco(int id, String logradouro, int numero, String bairro, String cep) {
    	this.endereco.setId(id);
    	this.endereco.setLogradouro(logradouro);
    	this.endereco.setNumero(numero);
    	this.endereco.setBairro(bairro);
    	this.endereco.setCep(cep);
    	return this;
    }
    
    public ProfessorBuilder addTelefone(String numero) {
        this.professor.setTelefones(numero);
        return this;
    }
    
    public ProfessorBuilder addNascimento(LocalDate nascimento) {
        this.professor.setNascimento(nascimento);
        return this;
    }
    
    public ProfessorBuilder addSalario(double salario) {
        this.professor.setSalario(salario);
        return this;
    }
    
    public Professor get() {
        this.professor.setEndereco(this.endereco);
        return this.professor;
    }
    
}