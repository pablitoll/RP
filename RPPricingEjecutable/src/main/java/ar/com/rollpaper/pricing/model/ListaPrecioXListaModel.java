package ar.com.rollpaper.pricing.model;

import java.util.List;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.business.ListaBusiness;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rollpaper.pricing.jasper.ListaPrecioReporteDTO;
import ar.com.rollpaper.pricing.jasper.Reportes;
import ar.com.rollpaper.pricing.jasper.ReportesListas;
import ar.com.rp.ui.pantalla.BaseModel;

public class ListaPrecioXListaModel extends BaseModel {
	private ListaDTO listaCargada;
	private ListaPrecioReporteDTO listaPrecioReporte = null;

	public ListaDTO getListaCargada() {
		return listaCargada;
	}

	public void setListaCargada(ListaDTO listaCargada) {
		this.listaCargada = listaCargada;
		listaPrecioReporte = null;
	}

	public List<ListaDTO> getListasToShow() { 
		return ListaBusiness.getTodasListas();
	}

	

	public ListaPrecioReporteDTO getListaArticulosImpactados() {
		if (listaPrecioReporte == null) {
			listaPrecioReporte = ReportesListas.getDatosReporte(getListaCargada().getVentLipv());
		}
		return listaPrecioReporte;
	}

}
