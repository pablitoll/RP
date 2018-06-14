package ar.com.rollpaper.pricing.model;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaPrecioModel extends BaseModel {

	private CcobClie clienteCargado;
	private VentLipv listaCargada;

	public CcobClie getClienteCargado() {
		return clienteCargado;
	}

	public void setClienteCargado(CcobClie clienteCargado) {
		this.clienteCargado = clienteCargado;
		listaCargada = null;
	}

	public PreciosEspeciales getRegistroArticuloEmpty() {
		PreciosEspeciales preciosEspeciales = new PreciosEspeciales();
		preciosEspeciales.setPricCliente(clienteCargado.getClieCliente());
		preciosEspeciales.setPricListaPrecvta(listaCargada.getLipvListaPrecvta());
		return preciosEspeciales;
	}

	public DescuentoXFamilias getRegistroFamilaiEmpty() {
		DescuentoXFamilias descuentoXFamilias = new DescuentoXFamilias();
		descuentoXFamilias.setPricFamiliaCliente(clienteCargado.getClieCliente());
		return descuentoXFamilias;

	}

	public VentLipv getListaCargada() {
		return listaCargada;
	}

	public void setListaCargada(VentLipv listaCargada) {
		this.listaCargada = listaCargada;
		
	}
}
