package ar.com.rollpaper.pricing.model;

import java.util.ArrayList;
import java.util.List;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.ClienteFactor;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.business.ListaBusiness;
import ar.com.rollpaper.pricing.dao.ClienteFactorDAO;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaPrecioModel extends BaseModel {

	private CcobClie clienteCargado;
	private ClienteFactor clienteFactor = null;
	private ListaDTO listaCargada;
	private List<ListaDTO> listasCargadas = null;
	private Boolean isClienteCargado = false;

	public Boolean isClienteCargado() {
		return isClienteCargado;
	}

	public void setIsClienteCargado(Boolean isClienteCargado) {
		this.isClienteCargado = isClienteCargado;
	}

	public CcobClie getClienteCargado() {
		return clienteCargado;
	}

	public void setClienteCargado(CcobClie clienteCargado) {
		this.clienteCargado = clienteCargado;
		listaCargada = null;
		listasCargadas = null;
		if (clienteCargado != null) {
			clienteFactor = ClienteFactorDAO.findById(clienteCargado.getClieCliente());
		} else {
			clienteFactor = null;
		}
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

	public void setFactor(Double valor) {
		if (clienteCargado != null) {
			if (clienteFactor == null) {
				clienteFactor = new ClienteFactor(clienteCargado.getClieCliente(), valor);
			}

			clienteFactor.setPricFactor(valor);

			HibernateGeneric.persist(clienteFactor);
		}
	}

	public Double getFactor() {
		if (clienteFactor != null) {
			return clienteFactor.getPricFactor();
		}

		return null;
	}

}
