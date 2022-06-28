package controller;

import java.sql.SQLException;

public interface IAcoes<T> {

	public void adicionar() throws ClassNotFoundException, SQLException;
	public void pesquisar() throws ClassNotFoundException, SQLException;
	public void atualizar() throws ClassNotFoundException, SQLException;
	public void excluir() throws ClassNotFoundException, SQLException;
	public void listar() throws ClassNotFoundException, SQLException;
	
}
