package view;

import java.sql.SQLException;
import java.time.LocalDate;

import controller.AcessoLiberadoControl;
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

public class AcessoLiberadoBoundary extends Application {

	private TextField txtNome = new TextField();
	private TextField txtData = new TextField();
	
	private Button pesquisarBt = new Button("Pesquisar");
	private Button listarBt = new Button("Atualizar Lista");
	private Button voltarBt = new Button("Voltar");

	private AcessoLiberadoControl control = new AcessoLiberadoControl();

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Alunos Liberados - Academia Divas ");

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10, 10, 10, 10));
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		HBox botoesBox = new HBox();
		botoesBox.setPadding(new Insets(10, 10, 10, 10));
		
		borderPane.setTop(gridPane);
		borderPane.setCenter(control.getTable());

		gridPane.add(voltarBt, 0, 0);

		gridPane.add(new Label("Pesquisar Aluna"), 0, 1);
		gridPane.add(txtNome, 1, 1);
		gridPane.add(new Label("Acesso válido até"), 0, 2);
		gridPane.add(txtData, 1, 2);
		
		botoesBox.setSpacing(40);
		botoesBox.getChildren().addAll(pesquisarBt, listarBt);
		gridPane.add(botoesBox, 0, 3, 4, 1);
		
		gridPane.setVgap(10);
		gridPane.setHgap(20);

		StringConverter<LocalDate> converterDate = new LocalDateStringConverter();

		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtData.textProperty(), control.dataProperty(), converterDate);
		
		pesquisarBt.setOnAction((e) -> {
			control.pesquisar();
		});
		
		listarBt.setOnAction((e) -> {
			try {
				control.listar();
			} catch (SQLException e1) {
				e1.printStackTrace();
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
	}
	
	public static void main(String[] args) {
		Application.launch(AcessoLiberadoBoundary.class, args);

	}

}
