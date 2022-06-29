package controller;

import model.Pagamento;

public interface IObservador {

	public void update(boolean liberado, Pagamento pag);
	
}
