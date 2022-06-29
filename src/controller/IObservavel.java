package controller;

import model.Pagamento;

public interface IObservavel {

	public void notificaMudanca(boolean liberado, Pagamento pag);
	
}
