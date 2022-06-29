package controller;

import model.Pagamento;

public interface ILiberadorAcesso {

	public void liberar(boolean liberado, Pagamento pag);
	
}
