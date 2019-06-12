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
import ar.com.rollpaper.pricing.view.ListaPrecioClienteView;
import ar.com.rp.ui.componentes.RPTable;
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

	private ListaPrecioReporteDTO getListaArticulosImpactados() {
		if (listaPrecioReporte == null) {
			listaPrecioReporte = Reportes.getDatosReporte(getClienteCargado(), getListaCargada().getVentLipv());

			List<ProductoDTO> listaProductoAux = listaPrecioReporte.getListaProductos();

			if ((listaProductoAux.size() > 0) && (listaProductoAux.get(0).getDescuento1() == null)) {
				// Cargo lo extra
				// Me traigo todos los precios espciales de un saque
				List<PreciosEspeciales> listPrecioEspecial = PreciosEspecialesDAO.getListaPrecioEspeciaByID(
						getClienteCargado().getClieCliente(), getListaCargada().getVentLipv().getLipvListaPrecvta());

				for (ProductoDTO prod : listaProductoAux) {
					PreciosEspeciales precioEspecial = getPrecioEspecia(prod, listPrecioEspecial);

					if (precioEspecial != null) {
						prod.cargarExtras(precioEspecial.getPricFechaDesde(), precioEspecial.getPricFechaHasta(),
								precioEspecial.getPricComision(), precioEspecial.getPricReferencia(),
								precioEspecial.getPricDescuento1(), precioEspecial.getPricDescuento2());
					}
				}

				listaPrecioReporte.setListaProductos(listaProductoAux);
			}
		}
		return listaPrecioReporte;
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

		return getListProductosReporte(isArticuloLista, isArticuloEspecifico, textoBusqueda).getListaProductos();

	}

	public ListaPrecioReporteDTO getListProductosReporte(boolean isArticuloLista, boolean isArticuloEspecifico,
			String textoBusqueda) {

		textoBusqueda = textoBusqueda.toLowerCase();
		List<ProductoDTO> listaProductoAux = new ArrayList<ProductoDTO>();

		ListaPrecioReporteDTO retorno = getListaArticulosImpactados().clone();

		for (ProductoDTO prod : retorno.getListaProductos()) {
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
				listaProductoAux.add(prodCandidato);
			}

		}

		retorno.setListaProductos(listaProductoAux);
		return retorno;
	}

	public ListaPrecioReporteDTO getListProductosReporte(RPTable tableResultado) {
		List<ProductoDTO> listaProductoAux = new ArrayList<ProductoDTO>();
		ListaPrecioReporteDTO retorno = getListaArticulosImpactados().clone();

		for (ProductoDTO prod : retorno.getListaProductos()) {
			if (estaSeleccionado(prod, tableResultado)) {
				listaProductoAux.add(prod);
			}
		}

		retorno.setListaProductos(listaProductoAux);
		return retorno;
	}

	private boolean estaSeleccionado(ProductoDTO prod, RPTable tableResultado) {
		for (int i : tableResultado.getSelectedRows()) {
			if (prod.getCodArticulo().equals(((String) tableResultado.getValueAt(i, ListaPrecioClienteView.COL_COD_ARTICULO)))) {
				return true;
			}
		}
		return false;
	}

}
