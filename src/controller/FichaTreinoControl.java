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
import model.FichaTreino;
import model.Professor;
import model.ProfessorBuilder;
import persistence.AlunoDao;
import persistence.FichaTreinoDao;
import persistence.ProfessorDao;
import util.HibernateUtil;

public class FichaTreinoControl implements IAcoesStrategy<FichaTreino> {
	
	private ObservableList<FichaTreino> treinosList = FXCollections.observableArrayList();
	private TableView<FichaTreino> table = new TableView<>();
	private Aluno aluno;
	private Professor professor;
	
	private IntegerProperty idTreino = new SimpleIntegerProperty();
	private StringProperty nomeAluno = new SimpleStringProperty("");
	private StringProperty nomeProfessor = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> dataTreino = new SimpleObjectProperty<>();
	private StringProperty tipoTreino = new SimpleStringProperty("");
	private StringProperty infoAdicional = new SimpleStringProperty("");

	public IntegerProperty idTreinoProperty() {
		return idTreino;
	}
	
	public StringProperty nomeAlunoProperty() {
		return nomeAluno;
	}
	
	public StringProperty nomeProfessorProperty() {
		return nomeProfessor;
	}
	
	public ObjectProperty<LocalDate> dataTreinoProperty() {
		return dataTreino;
	}

	public StringProperty tipoTreinoProperty() {
		return tipoTreino;
	}

	public StringProperty infoAdicionalProperty() {
		return infoAdicional;
	}

	@SuppressWarnings("unchecked")
	public FichaTreinoControl() {
		TableColumn<FichaTreino, String> idCol = new TableColumn<>("Id");
		TableColumn<FichaTreino, String> alunoCol = new TableColumn<>("Aluno");
		TableColumn<FichaTreino, String> professorCol = new TableColumn<>("Professor");
		TableColumn<FichaTreino, String> dataTreinoCol = new TableColumn<>("Data Treino");
		TableColumn<FichaTreino, String> tipoTreinoCol = new TableColumn<>("Tipo de Treino");
		TableColumn<FichaTreino, String> infoCol = new TableColumn<>("Informações Adicionais");

		idCol.setCellValueFactory(new PropertyValueFactory<FichaTreino, String>("idTreino"));
		alunoCol.setCellValueFactory(new PropertyValueFactory<FichaTreino, String>("nomeAluno"));
		professorCol.setCellValueFactory(new PropertyValueFactory<FichaTreino, String>("nomeProfessor"));
		dataTreinoCol.setCellValueFactory(data -> {
			LocalDate dataT = data.getValue().getDataTreino();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return new ReadOnlyStringWrapper(dataT.format(formatter));
		});
		tipoTreinoCol.setCellValueFactory(new PropertyValueFactory<FichaTreino, String>("tipoTreino"));
		infoCol.setCellValueFactory(new PropertyValueFactory<FichaTreino, String>("infoAdicional"));
		
		table.getColumns().addAll(idCol, alunoCol, professorCol, dataTreinoCol, tipoTreinoCol, infoCol);
		table.setItems(treinosList);
	}
	
	public TableView<FichaTreino> getTable() {
		return table;
	}
	
	public void adicionar() throws ClassNotFoundException, SQLException {
		FichaTreino tr = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		FichaTreinoDao dao = new FichaTreinoDao(sessionFactory);
		
		dao.insert(tr);
		treinosList.clear();
		List<FichaTreino> lista = dao.selectAll();
		treinosList.addAll(lista);
	}

	public void pesquisar() throws ClassNotFoundException, SQLException {
		FichaTreino tr = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		FichaTreinoDao dao = new FichaTreinoDao(sessionFactory);
		
		if(idTreino.get() != 0) {
			FichaTreino treino = dao.selectOne(tr);
			treinosList.clear();
			treinosList.add(treino);
			setEntidade(treino);
		}
		if(!nomeAluno.get().isEmpty()) {
			List<FichaTreino> lista = dao.selectOneAluno(tr);
			treinosList.clear();
			treinosList.addAll(lista);
		}
		if(!nomeProfessor.get().isEmpty()) {
			List<FichaTreino> lista = dao.selectOneProfessor(tr);
			treinosList.clear();
			treinosList.addAll(lista);
		}
	}

	public void pesquisarAluno() throws ClassNotFoundException, SQLException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		Aluno al = AlunoBuilder.builder().addNome(nomeAluno.get()).get();
		this.aluno = dao.selectOne(al);
		nomeAluno.set(aluno.getNome());
	}
	
	public void pesquisarProfessor() throws ClassNotFoundException, SQLException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		ProfessorDao dao = new ProfessorDao(sessionFactory);
		Professor prof = ProfessorBuilder.builder().addNome(nomeProfessor.get()).get();
		this.professor = dao.selectOne(prof);
		nomeProfessor.set(professor.getNome());
	}
	
	public void atualizar() throws ClassNotFoundException, SQLException {
		FichaTreino tr = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		FichaTreinoDao dao = new FichaTreinoDao(sessionFactory);
		
		dao.update(tr);
		treinosList.clear();
		List<FichaTreino> lista = dao.selectAll();
		treinosList.addAll(lista);
	}
	
	public void excluir() throws ClassNotFoundException, SQLException {
		FichaTreino tr = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		FichaTreinoDao dao = new FichaTreinoDao(sessionFactory);
		
		dao.delete(tr);
		treinosList.clear();
		List<FichaTreino> lista = dao.selectAll();
		treinosList.addAll(lista);
	}
	
	public void listar() throws ClassNotFoundException, SQLException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		FichaTreinoDao dao = new FichaTreinoDao(sessionFactory);
		
		treinosList.clear();
		List<FichaTreino> lista = dao.selectAll();
		treinosList.addAll(lista);
	}
	
	public FichaTreino getEntidade() {
		FichaTreino tr = new FichaTreino();
		tr.setId(idTreino.get());
		tr.setDataTreino(dataTreino.get());
		tr.setTipoTreino(tipoTreino.get());
		tr.setInfoAdicional(infoAdicional.get());
		
		if(!nomeAluno.get().isEmpty()) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(this.aluno.getId())
					.addNome(this.aluno.getNome())
					.addCpf(this.aluno.getCpf())
					.addNascimento(this.aluno.getNascimento())
					.addEndereco(this.aluno.getEndereco().getId(), this.aluno.getEndereco().getLogradouro(), this.aluno.getEndereco().getNumero(), this.aluno.getEndereco().getBairro(), this.aluno.getEndereco().getCep())
					.addTelefone(this.aluno.getTelefone())
					.get();
			tr.setAluno(aluno);
		}
		if(!nomeProfessor.get().isEmpty()) {
			Professor professor = ProfessorBuilder.builder()
					.addId(this.professor.getId())
					.addNome(this.professor.getNome())
					.addCpf(this.professor.getCpf())
					.addNascimento(this.professor.getNascimento())
					.addEndereco(this.professor.getEndereco().getId(), this.professor.getEndereco().getLogradouro(), this.professor.getEndereco().getNumero(), this.professor.getEndereco().getBairro(), this.professor.getEndereco().getCep())
					.addTelefone(this.professor.getTelefone())
					.addSalario(this.professor.getSalario())
					.get();
			tr.setProfessor(professor);
		}
		
		return tr;
	}
	
	public void setEntidade(FichaTreino tr) {
		idTreino.set(tr.getId());
		nomeAluno.set(tr.getAluno().getNome());
		nomeProfessor.set(tr.getProfessor().getNome());
		dataTreino.set(tr.getDataTreino());
		tipoTreino.set(tr.getTipoTreino());
		infoAdicional.set(tr.getInfoAdicional());
	}
	
}
