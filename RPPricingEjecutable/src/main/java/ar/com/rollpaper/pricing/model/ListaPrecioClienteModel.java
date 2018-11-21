package ar.com.rollpaper.pricing.model;

import java.util.List;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ListaBusiness;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rollpaper.pricing.jasper.ListaPrecioReporteDTO;
import ar.com.rollpaper.pricing.jasper.Reportes;
import ar.com.rp.ui.pantalla.BaseModel;

public class ListaPrecioClienteModel extends BaseModel {

	private CcobClie clienteCargado;
	private ListaDTO listaCargada;
	private ListaPrecioReporteDTO listaPrecioReporte = null;

	public CcobClie getClienteCargado() {
		return clienteCargado;
	}

	public void setClienteCargado(CcobClie clienteCargado) {
		this.clienteCargado = clienteCargado;
		listaCargada = null;
		listaPrecioReporte = null;
	}

	public PreciosEspeciales getRegistroArticuloEmpty() {
		PreciosEspeciales preciosEspeciales = new PreciosEspeciales();
		preciosEspeciales.setPricCliente(clienteCargado.getClieCliente());
		preciosEspeciales.setPricListaPrecvta(listaCargada.getVentLipv().getLipvListaPrecvta());
		return preciosEspeciales;
	}

	public DescuentoXFamilias getRegistroFamilaiEmpty() {
		DescuentoXFamilias descuentoXFamilias = new DescuentoXFamilias();
		descuentoXFamilias.setPricFamiliaListaPrecvta(listaCargada.getVentLipv().getLipvListaPrecvta());
		descuentoXFamilias.setPricFamiliaCliente(clienteCargado.getClieCliente());
		return descuentoXFamilias;

	}

	public ListaDTO getListaCargada() {
		return listaCargada;
	}

	public void setListaCargada(ListaDTO listaCargada) {
		this.listaCargada = listaCargada;
		listaPrecioReporte = null;
	}

	public List<ListaDTO> getListasToShow() {
		return ListaBusiness.getListaToShow(getClienteCargado());
	}

	

	public ListaPrecioReporteDTO getListaArticulosImpactados() {
		if (listaPrecioReporte == null) {
			listaPrecioReporte = Reportes.getDatosReporte(getClienteCargado(), getListaCargada().getVentLipv());
		}
		return listaPrecioReporte;
	}

}
