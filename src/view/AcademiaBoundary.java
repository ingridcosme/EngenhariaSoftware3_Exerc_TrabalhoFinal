package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AcademiaBoundary extends Application {

	private Label menuLb = new Label("ACADEMIA DIVAS - MENU");
	private Button alunoBt = new Button("ALUNOS");
	private Button professorBt = new Button("PROFESSORES");
	private Button pagamentoBt = new Button("PAGAMENTOS");
	private Button fichaTreinoBt = new Button("FICHAS DE TREINO");

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Sistema Academia Divas");

		VBox coluna1 = new VBox();
		coluna1.setSpacing(40);
		coluna1.setAlignment(Pos.CENTER);
		coluna1.setPadding(new Insets(30, 30, 30, 30));
		
		VBox coluna2 = new VBox();
		coluna2.setSpacing(40);
		coluna2.setAlignment(Pos.CENTER);
		coluna2.setPadding(new Insets(30, 30, 30, 30));
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(30, 30, 30, 30));
		grid.setAlignment(Pos.CENTER);
		grid.add(menuLb, 0, 0, 2, 1);
		grid.add(coluna1, 0, 1);
		grid.add(coluna2, 1, 1);
		
		Scene scene = new Scene(grid, 1200, 700);
		double altura = (scene.getHeight() / 2d);
		double largura = (scene.getWidth() / 4d);

		menuLb.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		alunoBt.setFont(Font.font(20d));
		alunoBt.setPrefSize(altura, largura);
		
		professorBt.setFont(Font.font(20d));
		professorBt.setPrefSize(altura, largura);
		
		pagamentoBt.setFont(Font.font(20d));
		pagamentoBt.setPrefSize(altura, largura);
		
		fichaTreinoBt.setFont(Font.font(20d));
		fichaTreinoBt.setPrefSize(altura, largura);
		
		coluna1.getChildren().addAll(alunoBt, professorBt);
		coluna2.getChildren().addAll(pagamentoBt, fichaTreinoBt);
		
		alunoBt.setOnAction(e -> {
			BoundaryAluno boundaryAluno = new BoundaryAluno();
			try {
				boundaryAluno.start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		professorBt.setOnAction(e -> {
			BoundaryProfessor boundaryProfessor = new BoundaryProfessor();
			try {
				boundaryProfessor.start(stage);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		pagamentoBt.setOnAction(e -> {
			PagamentoBoundary pagamentoBoundary = new PagamentoBoundary();
			try {
				pagamentoBoundary.start(stage);
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		});
		
		fichaTreinoBt.setOnAction(e -> {
			FichaTreinoBoundary fichaTreinoBoundary = new FichaTreinoBoundary();
			try {
				fichaTreinoBoundary.start(stage);
			} catch (Exception e4) {
				e4.printStackTrace();
			}
		});
		
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(AcademiaBoundary.class, args);
	}

}

