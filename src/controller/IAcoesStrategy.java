package controller;

import java.sql.SQLException;

import javafx.scene.control.TableView;

public interface IAcoesStrategy<T> {

	public void adicionar() throws ClassNotFoundException, SQLException;
	public void pesquisar() throws ClassNotFoundException, SQLException;
	public void atualizar() throws ClassNotFoundException, SQLException;
	public void excluir() throws ClassNotFoundException, SQLException;
	public void listar() throws ClassNotFoundException, SQLException;
	public T getEntidade();
	public void setEntidade(T T);
	public TableView<T> getTable();
	
}
