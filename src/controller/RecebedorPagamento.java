package controller;

import model.Pagamento;

public class RecebedorPagamento implements IReceberPagamento, IObservavel {

	private LiberadorAcesso liberador;
	private boolean liberado;
	
	public RecebedorPagamento(LiberadorAcesso liberador) {
		this.liberador = liberador;
	}

	@Override
	public void notificaMudanca(boolean liberado, Pagamento pag) {
		liberador.update(liberado, pag);
	}

	@Override
	public void receber(Pagamento pag) {
		liberado = true;
		notificaMudanca(liberado, pag);
	}
	
	
}
