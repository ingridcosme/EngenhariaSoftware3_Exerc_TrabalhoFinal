package model;

import java.time.LocalDate;

public class AlunoBuilder {

    private Aluno aluno;
    private Endereco endereco;
    
    public AlunoBuilder() {
        this.aluno = new Aluno();
        this.endereco = new Endereco();
    }
    
    public static AlunoBuilder builder() {
        return new AlunoBuilder();
    }
    
    public AlunoBuilder addId(int id) {
    	this.aluno.setId(id);
    	return this;
    }
    
    public AlunoBuilder addNome(String nome) {
        this.aluno.setNome(nome);
        return this;
    }
    
    public AlunoBuilder addCpf(String cpf) {
    	this.aluno.setCpf(cpf);
    	return this;
    }

    public AlunoBuilder addEndereco(String logradouro, int numero, String bairro, String cep) {
    	this.endereco.setLogradouro(logradouro);
    	this.endereco.setNumero(numero);
    	this.endereco.setBairro(bairro);
    	this.endereco.setCep(cep);
    	return this;
    }
    
    public AlunoBuilder addEndereco(int id, String logradouro, int numero, String bairro, String cep) {
    	this.endereco.setId(id);
    	this.endereco.setLogradouro(logradouro);
    	this.endereco.setNumero(numero);
    	this.endereco.setBairro(bairro);
    	this.endereco.setCep(cep);
    	return this;
    }
    
    public AlunoBuilder addTelefone(String numero) {
        this.aluno.setTelefone(numero);
        return this;
    }
    
    public AlunoBuilder addNascimento(LocalDate nascimento) {
        this.aluno.setNascimento(nascimento);
        return this;
    }
    
    public Aluno get() {
        this.aluno.setEndereco(this.endereco);
        return this.aluno;
    }
    
}