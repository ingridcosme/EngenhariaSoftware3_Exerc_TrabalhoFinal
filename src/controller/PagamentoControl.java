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
import model.Aluno;
import model.AlunoBuilder;
import model.Pagamento;
import persistence.AlunoDao;
import persistence.PagamentoDao;
import util.HibernateUtil;

public class PagamentoControl implements IAcoesStrategy<Pagamento> {
	
	private ObservableList<Pagamento> pagamentosList = FXCollections.observableArrayList();
	private TableView<Pagamento> table = new TableView<>();
	private Aluno aluno;
	
	private IntegerProperty idPagamento = new SimpleIntegerProperty();
	private StringProperty nomeAluno = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> dataPagamento = new SimpleObjectProperty<>();
	private DoubleProperty valorPagamento = new SimpleDoubleProperty();
	private StringProperty tipoPagamento = new SimpleStringProperty("");
	private StringProperty infoAdicional = new SimpleStringProperty("");

	public IntegerProperty idPagamentoProperty() {
		return idPagamento;
	}
	
	public StringProperty nomeAlunoProperty() {
		return nomeAluno;
	}
	
	public ObjectProperty<LocalDate> dataPagamentoProperty() {
		return dataPagamento;
	}

	public DoubleProperty valorPagamentoProperty() {
		return valorPagamento;
	}
	
	public StringProperty tipoPagamentoProperty() {
		return tipoPagamento;
	}

	public StringProperty infoAdicionalProperty() {
		return infoAdicional;
	}
	
	@SuppressWarnings("unchecked")
	public PagamentoControl() {
		TableColumn<Pagamento, String> idCol = new TableColumn<>("Id");
		TableColumn<Pagamento, String> alunoCol = new TableColumn<>("Aluno");
		TableColumn<Pagamento, String> dataPagCol = new TableColumn<>("Data Pagamento");
		TableColumn<Pagamento, String> valorPagCol = new TableColumn<>("Valor Pago");
		TableColumn<Pagamento, String> tipoPagCol = new TableColumn<>("Forma de Pagamento");
		TableColumn<Pagamento, String> infoCol = new TableColumn<>("Informações Adicionais");

		idCol.setCellValueFactory(new PropertyValueFactory<Pagamento, String>("idPagamento"));
		alunoCol.setCellValueFactory(new PropertyValueFactory<Pagamento, String>("nomeAluno"));
		dataPagCol.setCellValueFactory(data -> {
			LocalDate dataPag = data.getValue().getDataPagamento();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return new ReadOnlyStringWrapper(dataPag.format(formatter));
		});
		valorPagCol.setCellValueFactory(new PropertyValueFactory<Pagamento, String>("valorPagamento"));
		tipoPagCol.setCellValueFactory(new PropertyValueFactory<Pagamento, String>("tipoPagamento"));
		infoCol.setCellValueFactory(new PropertyValueFactory<Pagamento, String>("infoAdicional"));
		
		table.getColumns().addAll(idCol, alunoCol, dataPagCol, valorPagCol, tipoPagCol, infoCol);
		table.setItems(pagamentosList);
	}

	public TableView<Pagamento> getTable() {
		return table;
	}

	public void adicionar() throws ClassNotFoundException, SQLException {
		Pagamento pag = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		PagamentoDao dao = new PagamentoDao(sessionFactory);
		
		dao.insert(pag);
		pagamentosList.clear();
		List<Pagamento> pags = dao.selectAll();
		pagamentosList.addAll(pags);
	}
	
	public void pesquisar() throws ClassNotFoundException, SQLException {
		Pagamento pag = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		PagamentoDao dao = new PagamentoDao(sessionFactory);
		
		if(idPagamento.get() != 0) {
			Pagamento pagamento = dao.selectOne(pag);
			pagamentosList.clear();
			pagamentosList.add(pagamento);
			setEntidade(pagamento);
		}
		if(!nomeAluno.get().isEmpty()) {
			List<Pagamento> lista = dao.selectOneAluno(pag);
			pagamentosList.clear();
			pagamentosList.addAll(lista);
		}
	}
	
	public void pesquisarAluno() throws ClassNotFoundException, SQLException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		Aluno al = AlunoBuilder.builder().addNome(nomeAluno.get()).get();
		this.aluno = dao.selectOne(al);
		nomeAluno.set(aluno.getNome());
	}
	
	public void atualizar() throws ClassNotFoundException, SQLException {
		Pagamento pag = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		PagamentoDao dao = new PagamentoDao(sessionFactory);
		
		dao.update(pag);
		pagamentosList.clear();
		List<Pagamento> pags = dao.selectAll();
		pagamentosList.addAll(pags);
	}
	
	public void excluir() throws ClassNotFoundException, SQLException {
		Pagamento pag = getEntidade();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		PagamentoDao dao = new PagamentoDao(sessionFactory);
		
		dao.delete(pag);
		pagamentosList.clear();
		List<Pagamento> pags = dao.selectAll();
		pagamentosList.addAll(pags);
	}
	
	public void listar() throws ClassNotFoundException, SQLException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		PagamentoDao dao = new PagamentoDao(sessionFactory);
		
		pagamentosList.clear();
		List<Pagamento> pags = dao.selectAll();
		pagamentosList.addAll(pags);
	}
	
	public Pagamento getEntidade() {
		Pagamento pag = new Pagamento();
		pag.setId(idPagamento.get());
		pag.setDataPagamento(dataPagamento.get());
		pag.setValorPagamento(valorPagamento.get());
		pag.setTipoPagamento(tipoPagamento.get());
		pag.setInfoAdicional(infoAdicional.get());
		
		if(!nomeAluno.get().isEmpty()) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(this.aluno.getId())
					.addNome(this.aluno.getNome())
					.addCpf(this.aluno.getCpf())
					.addNascimento(this.aluno.getNascimento())
					.addEndereco(this.aluno.getEndereco().getId(), this.aluno.getEndereco().getLogradouro(), this.aluno.getEndereco().getNumero(), this.aluno.getEndereco().getBairro(), this.aluno.getEndereco().getCep())
					.addTelefone(this.aluno.getTelefone())
					.get();
			pag.setAluno(aluno);
		}
		
		return pag;
	}
	
	public void setEntidade(Pagamento pag) {
		idPagamento.set(pag.getId());
		nomeAluno.set(pag.getAluno().getNome());
		dataPagamento.set(pag.getDataPagamento());
		valorPagamento.set(pag.getValorPagamento());
		tipoPagamento.set(pag.getTipoPagamento());
		infoAdicional.set(pag.getInfoAdicional());
	}
	
}
