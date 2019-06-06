package ar.com.rollpaper.pricing.model;

import java.util.ArrayList;
import java.util.List;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.business.ListaBusiness;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaPrecioModel extends BaseModel {

	private CcobClie clienteCargado;
	private ListaDTO listaCargada;
	private List<ListaDTO> listasCargadas = null;

	public CcobClie getClienteCargado() {
		return clienteCargado;
	}

	public void setClienteCargado(CcobClie clienteCargado) {
		this.clienteCargado = clienteCargado;
		listaCargada = null;
		listasCargadas = null;
	}

	public PreciosEspeciales getRegistroArticuloEmpty() {
		PreciosEspeciales preciosEspeciales = new PreciosEspeciales();
		preciosEspeciales.setPricCliente(clienteCargado.getClieCliente());
		preciosEspeciales.setPricListaPrecvta(listaCargada.getVentLipv().getLipvListaPrecvta());
		return preciosEspeciales;
	}

	public ListaDTO getListaCargada() {
		return listaCargada;
	}

	public void setListaCargada(ListaDTO listaCargada) {
		this.listaCargada = listaCargada;
	}

	public List<ListaDTO> getListasToShow() {
		if (listasCargadas == null) {
			listasCargadas = ListaBusiness.getListaToShow(getClienteCargado());
		}
		return listasCargadas;
	}

	public void agregarLista(ListaDTO nuevaLista) {
		if (listaCargada == null) {
			listasCargadas = new ArrayList<ListaDTO>();
		}
		listasCargadas.add(nuevaLista);
	}

}
