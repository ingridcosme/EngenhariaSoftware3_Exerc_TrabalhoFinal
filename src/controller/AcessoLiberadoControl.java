package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.SessionFactory;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Aluno;
import model.Pagamento;
import persistence.AlunoDao;
import util.HibernateUtil;

public class AcessoLiberadoControl {

	private ObservableList<Aluno> liberadosLista = FXCollections.observableArrayList();
	private TableView<Aluno> table = new TableView<>();

	private StringProperty nome = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> data = new SimpleObjectProperty<>();
	
	public StringProperty nomeProperty() {
		return nome;
	}
	public ObjectProperty<LocalDate> dataProperty() {
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public AcessoLiberadoControl() {
		TableColumn<Aluno, String> nomeCol = new TableColumn<>("Nome");
		TableColumn<Aluno, String> dataCol = new TableColumn<>("Acesso liberado até");

		nomeCol.setCellValueFactory(new PropertyValueFactory<Aluno, String>("nome"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		dataCol.setCellValueFactory(data -> {
			LocalDate dataL = data.getValue().getDataLimiteDeAcesso();
			return new ReadOnlyStringWrapper(dataL.format(formatter));
		});

		table.getColumns().addAll(nomeCol, dataCol);
		table.setItems(liberadosLista);
	}

	public void atualizarAcesso(Pagamento pag) throws SQLException {
		LiberadorAcesso liberador = new LiberadorAcesso();
		RecebedorPagamento recebedor = new RecebedorPagamento(liberador);
		recebedor.receber(pag);
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		dao.update(pag.getAluno());
	}

	public void pesquisar() {
		boolean achou = false;
		for(Aluno al : liberadosLista) {
			if(al.getNome().toLowerCase().contains(nome.get().toLowerCase())) {
				nome.set(al.getNome());
				data.set(al.getDataLimiteDeAcesso());
				achou = true;
			}
		}
		if(achou == false) {
			JOptionPane.showMessageDialog(null, "Aluna não encontrada");
		}
	}
	
	public void listar() throws SQLException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		AlunoDao dao = new AlunoDao(sessionFactory);
		
		liberadosLista.clear();
		List<Aluno> als = dao.selectAll();
		for(Aluno a : als) {
			if(a.getDataLimiteDeAcesso().isAfter(LocalDate.now())) {
				liberadosLista.add(a);
			} else {
				a.setLiberado(false);
			}
		}
	}
	
	public TableView<Aluno> getTable() {
		return table;
	}
	
}
