package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.SessionFactory;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Aluno;
import model.AlunoBuilder;
import model.Endereco;
import persistence.AlunoDao;
import util.HibernateUtil;

public class ControlAluno implements IAcoesStrategy<Aluno> {

	private Endereco endereco;
	private ObservableList<Aluno> alunosLista = FXCollections.observableArrayList();
	private TableView<Aluno> table = new TableView<>();

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cpf = new SimpleStringProperty("");
	private StringProperty logradouro = new SimpleStringProperty("");
	private IntegerProperty numero = new SimpleIntegerProperty();
	private StringProperty bairro = new SimpleStringProperty("");
	private StringProperty cep = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> nascimento = new SimpleObjectProperty<>();
	private StringProperty telefone = new SimpleStringProperty("");
	
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
	
	@SuppressWarnings("unchecked")
	public ControlAluno() {
		TableColumn<Aluno, String> colunaId = new TableColumn<>("ID");
		TableColumn<Aluno, String> colunaNome = new TableColumn<>("Nome");
		TableColumn<Aluno, String> colunaCpf = new TableColumn<>("CPF");
		TableColumn<Aluno, String> colunaEndereco = new TableColumn<>("Endereço");
		TableColumn<Aluno, String> colunaNascimento = new TableColumn<>("Data de Nascimento");
		TableColumn<Aluno, String> colunaTelefone = new TableColumn<>("Telefone");

		colunaId.setCellValueFactory(new PropertyValueFactory<Aluno, String>("id"));
		colunaCpf.setCellValueFactory(new PropertyValueFactory<Aluno, String>("cpf"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<Aluno, String>("telefone"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		colunaNascimento.setCellValueFactory(data -> {
			LocalDate dataNasc = data.getValue().getNascimento();
			return new ReadOnlyStringWrapper(dataNasc.format(formatter));
		});
		colunaNome.setCellValueFactory(new PropertyValueFactory<Aluno, String>("nome"));
		colunaEndereco.setCellValueFactory(e -> {
			String end = e.getValue().getEndereco().getLogradouro() + ", " + e.getValue().getEndereco().getNumero()
					+ " - " + e.getValue().getEndereco().getBairro();
			return new ReadOnlyStringWrapper(end);
		});
		

		table.getColumns().addAll(colunaId, colunaNome, colunaCpf, colunaNascimento, colunaTelefone, colunaEndereco);
		table.setItems(alunosLista);
	}

	public void adicionar() throws ClassNotFoundException, SQLException {
		Aluno aluno = getEntidade();
		aluno.getEndereco().setId(0);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		
		dao.insert(aluno);
		alunosLista.clear();
		List<Aluno> als = dao.selectAll();
		alunosLista.addAll(als);
	}

	public void pesquisar() throws ClassNotFoundException, SQLException {
		Aluno aluno = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		
		if(id.get() != 0 || !nome.get().isEmpty()) {
			Aluno al = dao.selectOne(aluno);
			alunosLista.clear();
			alunosLista.add(al);
			setEntidade(al);
			
			this.endereco = al.getEndereco();
		}
	}
	
	public void atualizar() throws ClassNotFoundException, SQLException {
		Aluno aluno = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		
		dao.update(aluno);
		alunosLista.clear();
		List<Aluno> als = dao.selectAll();
		alunosLista.addAll(als);
	}
	
	public void excluir() throws ClassNotFoundException, SQLException {
		Aluno aluno = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		
		dao.delete(aluno);
		alunosLista.clear();
		List<Aluno> als = dao.selectAll();
		alunosLista.addAll(als);
	}
	
	public void listar() throws ClassNotFoundException, SQLException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		
		alunosLista.clear();
		List<Aluno> als = dao.selectAll();
		alunosLista.addAll(als);
	}
	
	public Aluno getEntidade() {
		Aluno aluno = AlunoBuilder.builder()
					.addId(id.get())
					.addNome(nome.get())
					.addCpf(cpf.get())
					.addNascimento(nascimento.get())
					.addEndereco(logradouro.get(), numero.get(), bairro.get(), cep.get())
					.addTelefone(telefone.get())
					.get();
		
		if(this.endereco != null) {
			aluno.getEndereco().setId(this.endereco.getId());
		}
		return aluno;
	}
	
	public void setEntidade(Aluno al) {
		id.set(al.getId());
		nome.set(al.getNome());
		cpf.set(al.getCpf());
		nascimento.set(al.getNascimento());
		telefone.set(al.getTelefone());
		logradouro.set(al.getEndereco().getLogradouro());
		numero.set(al.getEndereco().getNumero());
		bairro.set(al.getEndereco().getBairro());
		cep.set(al.getEndereco().getCep());
	}

	public TableView<Aluno> getTable() {
		return table;
	}

}
