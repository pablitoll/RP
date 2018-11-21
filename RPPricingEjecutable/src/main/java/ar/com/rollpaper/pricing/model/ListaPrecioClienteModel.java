package ar.com.rollpaper.pricing.model;

import java.util.ArrayList;
import java.util.List;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.jasper.ListaPrecioReporteDTO;
import ar.com.rollpaper.pricing.jasper.Reportes;
import ar.com.rp.ui.pantalla.BaseModel;

public class ListaPrecioClienteModel extends BaseModel {

	private CcobClie clienteCargado;
	private VentLipv listaCargada;
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
		preciosEspeciales.setPricListaPrecvta(listaCargada.getLipvListaPrecvta());
		return preciosEspeciales;
	}

	public DescuentoXFamilias getRegistroFamilaiEmpty() {
		DescuentoXFamilias descuentoXFamilias = new DescuentoXFamilias();
		descuentoXFamilias.setPricFamiliaListaPrecvta(listaCargada.getLipvListaPrecvta());
		descuentoXFamilias.setPricFamiliaCliente(clienteCargado.getClieCliente());
		return descuentoXFamilias;

	}

	public VentLipv getListaCargada() {
		return listaCargada;
	}

	public void setListaCargada(VentLipv listaCargada) {
		this.listaCargada = listaCargada;
		listaPrecioReporte = null;
	}

	public List<VentLipv> getListasToShow() {
		List<VentLipv> retorno = new ArrayList<VentLipv>();

		// Busco la lista principal
		for (VentCliv listaClienteLista : VentClivDAO.getListaPreciosByCliente(getClienteCargado())) {
			VentLipv lista = VentLipvDAO.findById(listaClienteLista.getClivListaPrecvta());
			lista.setIsListaPrincipal(true);
			retorno.add(lista);
		}

		// Busco las lista que tenga en las otras tablas
		for (DescuentoXFamilias familia : DescuentoXFamiliasDAO.getByCliente(getClienteCargado().getClieCliente())) {
			VentLipv lista = VentLipvDAO.findById(familia.getPricFamiliaListaPrecvta());
			if (lista != null) {
				if (!isInLista(retorno, lista)) {
					retorno.add(lista);
				}
			}
		}

		for (PreciosEspeciales especial : PreciosEspecialesDAO.getByCliente(getClienteCargado().getClieCliente())) {
			VentLipv lista = VentLipvDAO.findById(especial.getPricPreciosEspecialesId());
			if (lista != null) {
				if (!isInLista(retorno, lista)) {
					retorno.add(lista);
				}
			}
		}

		// TODO falta los heredados
		for (MaestroEsclavo maestro : MaestroEsclavoDAO.getListaEsclavosByEsclavo(getClienteCargado())) {
			VentLipv lista = VentLipvDAO.findById(maestro.getPricMEListaPrecvta());
			if (lista != null) {
				if (!isInLista(retorno, lista)) {
					lista.setIsListaHeredada(true);
					retorno.add(lista);
				}
			}
		}

		return retorno;
	}

	private boolean isInLista(List<VentLipv> lista, VentLipv registro) {
		for (VentLipv aux : lista) {
			if (aux.getLipvListaPrecvta() == registro.getLipvListaPrecvta()) {
				return true;
			}
		}
		return false;
	}

	public ListaPrecioReporteDTO getListaArticulosImpactados() {
		if (listaPrecioReporte == null) {
			listaPrecioReporte = Reportes.getDatosReporte(getClienteCargado(), getListaCargada());
		}
		return listaPrecioReporte;
	}

}
