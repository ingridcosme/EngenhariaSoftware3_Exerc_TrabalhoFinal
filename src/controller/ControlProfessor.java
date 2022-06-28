package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.SessionFactory;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Endereco;
import model.Professor;
import model.ProfessorBuilder;
import persistence.ProfessorDao;
import util.HibernateUtil;

public class ControlProfessor implements IAcoes<Professor> {

	private ObservableList<Professor> professoresLista = FXCollections.observableArrayList();
	private TableView<Professor> table = new TableView<>();
	private Endereco endereco;

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cpf = new SimpleStringProperty("");
	private StringProperty logradouro = new SimpleStringProperty("");
	private IntegerProperty numero = new SimpleIntegerProperty();
	private StringProperty bairro = new SimpleStringProperty("");
	private StringProperty cep = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> nascimento = new SimpleObjectProperty<>();
	private StringProperty telefone = new SimpleStringProperty("");
	private DoubleProperty salario = new SimpleDoubleProperty();
	
	public IntegerProperty idProperty() {
		return id;
	}
	public StringProperty nomeProperty() {
		return nome;
	}
	public StringProperty cpfProperty() {
		return cpf;
	}
	public StringProperty logradouroProperty() {
		return logradouro;
	}
	public IntegerProperty numeroProperty() {
		return numero;
	}
	public StringProperty bairroProperty() {
		return bairro;
	}
	public StringProperty cepProperty() {
		return cep;
	}
	public ObjectProperty<LocalDate> nascimentoProperty() {
		return nascimento;
	}
	public StringProperty telefoneProperty() {
		return telefone;
	}
	public DoubleProperty salarioProperty() {
		return salario;
	}

	@SuppressWarnings("unchecked")
	public ControlProfessor() {

		TableColumn<Professor, String> colunaId = new TableColumn<>("ID");
		TableColumn<Professor, String> colunaNome = new TableColumn<>("Nome");
		TableColumn<Professor, String> colunaCpf = new TableColumn<>("CPF");
		TableColumn<Professor, String> colunaEndereco = new TableColumn<>("Endereço");
		TableColumn<Professor, String> colunaNascimento = new TableColumn<>("Data de Nascimento");
		TableColumn<Professor, String> colunaTelefone = new TableColumn<>("Telefone");
		TableColumn<Professor, String> colunaSalario = new TableColumn<>("Salário");

		colunaId.setCellValueFactory(new PropertyValueFactory<Professor, String>("id"));
		colunaCpf.setCellValueFactory(new PropertyValueFactory<Professor, String>("cpf"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<Professor, String>("telefone"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		colunaNascimento.setCellValueFactory(data -> {
			LocalDate dataNasc = data.getValue().getNascimento();
			return new ReadOnlyStringWrapper(dataNasc.format(formatter));
		});
		colunaNome.setCellValueFactory(new PropertyValueFactory<Professor, String>("nome"));
		colunaSalario.setCellValueFactory(new PropertyValueFactory<Professor, String>("salario"));
		colunaEndereco.setCellValueFactory(e -> {
			String end = e.getValue().getEndereco().getLogradouro() + ", " + e.getValue().getEndereco().getNumero()
					+ " - " + e.getValue().getEndereco().getBairro();
			return new ReadOnlyStringWrapper(end);
		});
		

		table.getColumns().addAll(colunaId, colunaNome, colunaCpf, colunaNascimento, colunaSalario, colunaTelefone, colunaEndereco);
		table.setItems(professoresLista);
	}

	@Override
	public void adicionar() throws ClassNotFoundException, SQLException {
		Professor professor = getEntidade();
		professor.getEndereco().setId(0);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		ProfessorDao dao = new ProfessorDao(sessionFactory);
		
		dao.insert(professor);
		professoresLista.clear();
		List<Professor> profs = dao.selectAll();
		professoresLista.addAll(profs);
	}

	@Override
	public void pesquisar() throws ClassNotFoundException, SQLException {
		Professor professor = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		ProfessorDao dao = new ProfessorDao(sessionFactory);
		
		if(!nome.get().isEmpty()) {
			Professor prof = dao.selectOne(professor);
			professoresLista.clear();
			professoresLista.add(prof);
			setEntidade(prof);
			
			this.endereco = prof.getEndereco();
		}
	}
	
	@Override
	public void atualizar() throws ClassNotFoundException, SQLException {
		Professor professor = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		ProfessorDao dao = new ProfessorDao(sessionFactory);
		
		dao.update(professor);
		professoresLista.clear();
		List<Professor> profs = dao.selectAll();
		professoresLista.addAll(profs);
	}
	
	@Override
	public void excluir() throws ClassNotFoundException, SQLException {
		Professor professor = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		ProfessorDao dao = new ProfessorDao(sessionFactory);
		
		dao.delete(professor);
		professoresLista.clear();
		List<Professor> profs = dao.selectAll();
		professoresLista.addAll(profs);
	}
	
	@Override
	public void listar() throws ClassNotFoundException, SQLException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		ProfessorDao dao = new ProfessorDao(sessionFactory);
		
		professoresLista.clear();
		List<Professor> profs = dao.selectAll();
		professoresLista.addAll(profs);
	}
	
	public Professor getEntidade() {
		Professor professor = ProfessorBuilder.builder()
					.addId(id.get())
					.addNome(nome.get())
					.addCpf(cpf.get())
					.addNascimento(nascimento.get())
					.addEndereco(logradouro.get(), numero.get(), bairro.get(), cep.get())
					.addTelefone(telefone.get())
					.addSalario(salario.get())
					.get();
		
		if(this.endereco != null) {
			professor.getEndereco().setId(this.endereco.getId()); 
		}
		
		return professor;
	}
	
	public void setEntidade(Professor prof) {
		id.set(prof.getId());
		nome.set(prof.getNome());
		cpf.set(prof.getCpf());
		nascimento.set(prof.getNascimento());
		telefone.set(prof.getTelefone());
		salario.set(prof.getSalario());
		logradouro.set(prof.getEndereco().getLogradouro());
		numero.set(prof.getEndereco().getNumero());
		bairro.set(prof.getEndereco().getBairro());
		cep.set(prof.getEndereco().getCep());
	}

	public TableView<Professor> getTable() {
		return table;
	}

}
