package ar.com.rollpaper.pricing.model;

import java.math.BigDecimal;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.ClienteFactor;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.dao.ClienteFactorDAO;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaClienteEsclavoModel extends BaseModel {

	private CcobClie cliente;
	private VentLipv listaCliente;
	private boolean cambiosPendientes = false;
	private ClienteFactor factor = null;

	public CcobClie getCliente() {
		return cliente;
	}

	public void setCliente(CcobClie cliente) {
		this.cliente = cliente;
		if (cliente == null) {
			factor = null;
		} else {
			factor = ClienteFactorDAO.findById(cliente.getClieCliente());
		}
	}

	public Double getFator() {
		if (factor != null) {
			return factor.getPricFactor();
		}
		return null;
	}

	public void setListaCliente(VentLipv lista) {
		this.listaCliente = lista;
	}

	public VentLipv getListaCliente() {
		return listaCliente;
	}

	public void setCambiosPendietes(boolean cambiosPendientes) {
		this.cambiosPendientes = cambiosPendientes;
	}

	public boolean isCambiosPendientes() {
		return cambiosPendientes;
	}

	public BigDecimal getFatorBig() {
		if (getFator() != null) {
			return new BigDecimal(getFator());
		}
		return null;
	}

}
