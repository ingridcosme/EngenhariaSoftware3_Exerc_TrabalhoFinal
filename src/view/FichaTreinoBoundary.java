package view;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import controller.FichaTreinoControl;
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

public class FichaTreinoBoundary extends Application {

	private TextField idTxt = new TextField();
	private TextField alunoTxt = new TextField();
	private TextField professorTxt = new TextField();
	private TextField dataTreinoTxt = new TextField();
	private TextField tipoTreinoTxt = new TextField();
	private TextField infoTxt = new TextField();

	private Button adicionarBt = new Button("Adicionar");
	private Button pesquisarBt = new Button("Pesquisar");
	private Button pesquisarAlunoBt = new Button("Pesquisar Aluno");
	private Button pesquisarProfessorBt = new Button("Pesquisar Professor");
	private Button atualizarBt = new Button("Atualizar");
	private Button excluirBt = new Button("Excluir");
	private Button listarBt = new Button("Listar");
	private Button voltarBt = new Button("Voltar");

	private FichaTreinoControl control = new FichaTreinoControl();

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Gestão Treinos - Academia Divas");

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10, 10, 10, 10));
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		HBox botoesBox = new HBox();
		botoesBox.setPadding(new Insets(10, 10, 10, 10));
		
		borderPane.setTop(gridPane);
		borderPane.setCenter(control.getTable());

		gridPane.add(voltarBt, 0, 0);
		
		gridPane.add(new Label("Id"), 0, 1);
		gridPane.add(idTxt, 1, 1);
		gridPane.add(new Label("Professor"), 0, 2);
		gridPane.add(professorTxt, 1, 2);
		gridPane.add(pesquisarProfessorBt, 2, 2);
		gridPane.add(new Label("Aluno"), 0, 3);
		gridPane.add(alunoTxt, 1, 3);
		gridPane.add(pesquisarAlunoBt, 2, 3);
		gridPane.add(new Label("Data Treino"), 0, 4);
		gridPane.add(dataTreinoTxt, 1, 4);
		gridPane.add(new Label("Tipo de Treino"), 0, 5);
		gridPane.add(tipoTreinoTxt, 1, 5);
		gridPane.add(new Label("Informações Adicionais"), 0, 6);
		gridPane.add(infoTxt, 1, 6);
		infoTxt.setPrefHeight(50d);
		infoTxt.setPrefWidth(300d);

		botoesBox.setSpacing(40);
		botoesBox.getChildren().addAll(adicionarBt, pesquisarBt, atualizarBt, excluirBt, listarBt);
		gridPane.add(botoesBox, 0, 7, 4, 1);
		
		gridPane.setVgap(10);
		gridPane.setHgap(20);
		
		StringConverter<LocalDate> converterDate = new LocalDateStringConverter();
		StringConverter<Number> converterNumber = new NumberStringConverter();

		Bindings.bindBidirectional(idTxt.textProperty(), control.idProperty(), converterNumber);
		Bindings.bindBidirectional(professorTxt.textProperty(), control.nomeProfessorProperty());
		Bindings.bindBidirectional(alunoTxt.textProperty(), control.nomeAlunoProperty());
		Bindings.bindBidirectional(dataTreinoTxt.textProperty(), control.dataProperty(), converterDate);
		Bindings.bindBidirectional(tipoTreinoTxt.textProperty(), control.tipoProperty());
		Bindings.bindBidirectional(infoTxt.textProperty(), control.infoAdicionalProperty());
		
		pesquisarProfessorBt.setOnAction((e) -> {
			try {
				control.pesquisarProfessor();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		pesquisarAlunoBt.setOnAction((e) -> {
			try {
				control.pesquisarAluno();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		adicionarBt.setOnAction((e) -> {
			try {
				control.adicionar();
				limparCampos();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		pesquisarBt.setOnAction((e) -> {
			try {
				control.pesquisar();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		atualizarBt.setOnAction((e) -> {
			try {
				control.atualizar();
				limparCampos();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		excluirBt.setOnAction((e) -> {
			try {
				control.excluir();
				limparCampos();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		listarBt.setOnAction((e) -> {
			try {
				control.listar();
				limparCampos();
			} catch (ClassNotFoundException | SQLException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});
		
		voltarBt.setOnAction((e) -> {
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
		idTxt.setText("");
		alunoTxt.setText("");
		professorTxt.setText("");
		dataTreinoTxt.setText("");
		dataTreinoTxt.setPromptText("dd/mm/aaaa");
		tipoTreinoTxt.setText("");
		infoTxt.setText("");
	}

	public static void main(String[] args) {
		Application.launch(FichaTreinoBoundary.class, args);
	}

}
