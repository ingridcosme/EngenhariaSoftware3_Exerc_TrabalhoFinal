package controller;

import java.time.LocalDate;

import model.Pagamento;

public class LiberadorAcesso implements IObservador, ILiberadorAcesso {
	
	@Override
	public void liberar(boolean liberado, Pagamento pag) {
		pag.getAluno().setLiberado(true);
		LocalDate dataLimite = pag.getData().plusDays(30);
		pag.getAluno().setDataLimiteDeAcesso(dataLimite);
	}

	@Override
	public void update(boolean liberado, Pagamento pag) {
		liberar(liberado, pag);
	}

}
