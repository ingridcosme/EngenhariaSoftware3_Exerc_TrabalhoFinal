package view;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import controller.ControlAluno;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

public class BoundaryAluno extends Application {

	private TextField txtId = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtCPF = new TextField();
	private TextField txtLogradouro = new TextField();
	private TextField txtNumero = new TextField();
	private TextField txtBairro = new TextField();
	private TextField txtCep = new TextField();
	private TextField txtDataNascimento = new TextField();
	private TextField txtTelefone = new TextField();

	private Button botaoAdicionar = new Button("Adicionar");
	private Button botaoPesquisar = new Button("Pesquisar");
	private Button botaoAtualizar = new Button("Atualizar");
	private Button botaoExcluir = new Button("Excluir");
	private Button botaoListar = new Button("Listar");
	private Button botaoVoltar = new Button("Voltar");

	private ControlAluno control = new ControlAluno();

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Gestão de Aluno - Academia Divas ");

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10, 10, 10, 10));
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		HBox botoesBox = new HBox();
		botoesBox.setPadding(new Insets(10, 10, 10, 10));
		
		borderPane.setTop(gridPane);
		borderPane.setCenter(control.getTable());

		gridPane.add(botaoVoltar, 0, 0);

		gridPane.add(new Label("ID"), 0, 1);
		gridPane.add(txtId, 1, 1);
		gridPane.add(new Label("Nome"), 0, 2);
		gridPane.add(txtNome, 1, 2);
		gridPane.add(new Label("CPF"), 0, 3);
		gridPane.add(txtCPF, 1, 3);
		gridPane.add(new Label("Data de Nascimento"), 0, 4);
		txtDataNascimento.setPromptText("dd/mm/aaaa");
		gridPane.add(txtDataNascimento, 1, 4);
		gridPane.add(new Label("Logradouro"), 0, 5);
		gridPane.add(txtLogradouro, 1, 5);
		gridPane.add(new Label("Número"), 3, 5);
		gridPane.add(txtNumero, 4, 5);
		gridPane.add(new Label("Bairro"), 0, 6);
		gridPane.add(txtBairro, 1, 6);
		gridPane.add(new Label("CEP"), 3, 6);
		gridPane.add(txtCep, 4, 6);
		gridPane.add(new Label("Telefone"), 0, 7);
		gridPane.add(txtTelefone, 1, 7);
		
		botoesBox.setSpacing(40);
		botoesBox.getChildren().addAll(botaoAdicionar, botaoPesquisar, botaoAtualizar, botaoExcluir, botaoListar);
		gridPane.add(botoesBox, 0, 8, 4, 1);
		
		gridPane.setVgap(10);
		gridPane.setHgap(20);

		StringConverter<LocalDate> converterDate = new LocalDateStringConverter();
		StringConverter<Number> converterNum = new NumberStringConverter();

		Bindings.bindBidirectional(txtId.textProperty(), control.idProperty(), converterNum);
		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtCPF.textProperty(), control.cpfProperty());
		Bindings.bindBidirectional(txtLogradouro.textProperty(), control.logradouroProperty());
		Bindings.bindBidirectional(txtNumero.textProperty(), control.numeroProperty(), converterNum);
		Bindings.bindBidirectional(txtBairro.textProperty(), control.bairroProperty());
		Bindings.bindBidirectional(txtCep.textProperty(), control.cepProperty());
		Bindings.bindBidirectional(txtDataNascimento.textProperty(), control.nascimentoProperty(), converterDate);
		Bindings.bindBidirectional(txtTelefone.textProperty(), control.telefoneProperty());
		
		botaoAdicionar.setOnAction((e) -> {
			try {
				control.adicionar();
				limparCampos();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		botaoPesquisar.setOnAction((e) -> {
			try {
				control.pesquisar();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		botaoAtualizar.setOnAction((e) -> {
			try {
				control.atualizar();
				limparCampos();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		botaoExcluir.setOnAction((e) -> {
			try {
				control.excluir();
				limparCampos();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		botaoListar.setOnAction((e) -> {
			try {
				control.listar();
				limparCampos();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		botaoVoltar.setOnAction((e) -> {
			AcademiaBoundary academiaBoundary = new AcademiaBoundary();
			try {
				academiaBoundary.start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		Scene scene = new Scene(borderPane, 1200, 700);
		stage.setScene(scene);
		stage.show();
		limparCampos();
	}
	
	private void limparCampos() {
		txtId.setText("");
		txtNome.setText("");
		txtCPF.setText("");
		txtLogradouro.setText("");
		txtNumero.setText("");
		txtBairro.setText("");
		txtCep.setText("");
		txtDataNascimento.setText("");
		txtDataNascimento.setPromptText("dd/mm/aaaa");
		txtTelefone.setText("");
	}

	public static void main(String[] args) {
		Application.launch(BoundaryAluno.class, args);

	}

}
