package ar.com.rollpaper.pricing.model;

import java.util.ArrayList;
import java.util.List;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.business.ListaBusiness;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rollpaper.pricing.jasper.ListaPrecioReporteDTO;
import ar.com.rollpaper.pricing.jasper.ProductoDTO;
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

	public ListaPrecioReporteDTO getListaArticulosImpactadosReporte() {
		if (listaPrecioReporte == null) {
			listaPrecioReporte = Reportes.getDatosReporte(getClienteCargado(), getListaCargada().getVentLipv());
		}

		return listaPrecioReporte;
	}

	private List<ProductoDTO> getListaArticulosImpactados(boolean extraData) {
		List<ProductoDTO> retorno = getListaArticulosImpactadosReporte().getListaProductos();

		if (extraData && (retorno.size() > 0) && (retorno.get(0).getDescuento1() == null)) {
			// Cargo lo extra
			// Me traigo todos los precios espciales de un saque
			List<PreciosEspeciales> listPrecioEspecial = PreciosEspecialesDAO.getListaPrecioEspeciaByID(
					getClienteCargado().getClieCliente(), getListaCargada().getVentLipv().getLipvListaPrecvta());

			for (ProductoDTO prod : retorno) {
				PreciosEspeciales precioEspecial = getPrecioEspecia(prod, listPrecioEspecial);

				if (precioEspecial != null) {
					prod.cargarExtras(precioEspecial.getPricFechaDesde(), precioEspecial.getPricFechaHasta(),
							precioEspecial.getPricComision(), precioEspecial.getPricReferencia(),
							precioEspecial.getPricDescuento1(), precioEspecial.getPricDescuento2());
				}
			}
		}

		return retorno;
	}

	private PreciosEspeciales getPrecioEspecia(ProductoDTO prod, List<PreciosEspeciales> listPrecioEspecial) {
		for (PreciosEspeciales precio : listPrecioEspecial) {
			if (prod.getArtsArticulo() == precio.getPricArticulo()) {
				return precio;
			}
		}
		return null;
	}

	public List<ProductoDTO> getListProductos(boolean isArticuloLista, boolean isArticuloEspecifico,
			String textoBusqueda) {
		textoBusqueda = textoBusqueda.toLowerCase();
		List<ProductoDTO> retorno = new ArrayList<ProductoDTO>();
		for (ProductoDTO prod : getListaArticulosImpactados(true)) { // TODO CAMBIAR EL TRUE
			// Primero que pase el filto de lista o no lista
			ProductoDTO prodCandidato = null;

			if (isArticuloLista && prod.isProdListaBase()) {
				prodCandidato = prod;
			}

			if (isArticuloEspecifico && !prod.isProdListaBase()) {
				prodCandidato = prod;
			}

			if ((prodCandidato != null) && !textoBusqueda.trim().equals("")) {
				if (prod.getDescArticulo().toLowerCase().contains(textoBusqueda.trim())
						|| prod.getCodArticulo().contains(textoBusqueda.trim())) {
					prodCandidato = prod;
				} else {
					prodCandidato = null;
				}
			}

			if (prodCandidato != null) {
				retorno.add(prodCandidato);
			}

		}
		return retorno;
	}

}
