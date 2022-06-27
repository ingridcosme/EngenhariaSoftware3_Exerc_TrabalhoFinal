package persistence;

import java.util.List;

import model.FichaTreino;

public interface IFichaTreinoDao {

	public List<FichaTreino> selectOneAluno(FichaTreino treino);
	public List<FichaTreino> selectOneProfessor(FichaTreino treino);
	
}
