package persistence;

import java.util.List;

import model.Pagamento;

public interface IPagamentoDao {

	public List<Pagamento> selectOneAluno(Pagamento pag);
	
}
