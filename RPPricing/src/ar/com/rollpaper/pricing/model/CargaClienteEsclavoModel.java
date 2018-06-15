package ar.com.rollpaper.pricing.model;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaClienteEsclavoModel extends BaseModel {

	private CcobClie cliente;
	private VentLipv listaCliente;
	private boolean setEsEscalvoEnAlgunaLista = false;

	public CcobClie getCliente() {
		return cliente;
	}

	public void setCliente(CcobClie cliente) {
		this.cliente = cliente;

	}

	public void setListaCliente(VentLipv lista) {
		this.listaCliente = lista;
	}

	public VentLipv getListaCliente() {
		return listaCliente;
	}

	public void setEsEscalvoEnAlgunaLista(boolean esEscalvoEnAlgunaLista) {
		this.setEsEscalvoEnAlgunaLista = esEscalvoEnAlgunaLista;
	}

	public boolean isEsEscalvoEnAlgunaLista() {
		return setEsEscalvoEnAlgunaLista;
	}

}
