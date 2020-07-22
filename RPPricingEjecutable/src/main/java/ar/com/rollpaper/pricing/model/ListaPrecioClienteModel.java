package ar.com.rollpaper.pricing.model;

import java.util.ArrayList;
import java.util.List;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.ClienteFactor;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.business.CommonPricing;
import ar.com.rollpaper.pricing.business.ListaBusiness;
import ar.com.rollpaper.pricing.business.MaestroEsclavoBusinnes;
import ar.com.rollpaper.pricing.dao.ClienteFactorDAO;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
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
	private ClienteFactor factor = null;

	public CcobClie getClienteCargado() {
		return clienteCargado;
	}

	public void setClienteCargado(CcobClie clienteCargado) {
		this.clienteCargado = clienteCargado;
		listaCargada = null;
		listaPrecioReporte = null;
		if (clienteCargado != null) {
			factor = ClienteFactorDAO.findById(clienteCargado.getClieCliente());
		} else {
			factor = null;
		}
	}

	public Double getFactor() {
		if (factor != null) {
			return factor.getPricFactor();
		}

		return null;
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
			// getDatosReporte debe trar t
			listaPrecioReporte = Reportes.getDatosReporte(getClienteCargado(), getListaCargada().getVentLipv());

			List<ProductoDTO> listaProductoAux = listaPrecioReporte.getListaProductos();

			if ((listaProductoAux.size() > 0) && (listaProductoAux.get(0).getDescuento1() == null)) {
				// Cargo lo extra
				// Me traigo todos los precios espciales de un saque
				int idPadre = getClienteCargado().getClieCliente();

				if (getListaCargada().isListaHeredada()) {
					idPadre = MaestroEsclavoBusinnes.getPadreId(getClienteCargado(), getListaCargada());
				}

				List<PreciosEspeciales> listPrecioEspecial = PreciosEspecialesDAO.getListaPrecioEspeciaByID(idPadre,
						getListaCargada().getVentLipv().getLipvListaPrecvta());

				List<DescuentoXFamilias> ListaDescuentoxFamilia = DescuentoXFamiliasDAO.getListaDescuentoByID(
						getClienteCargado().getClieCliente(), getListaCargada().getVentLipv().getLipvListaPrecvta());

				for (ProductoDTO prod : listaProductoAux) {
					PreciosEspeciales precioEspecial = getPrecioEspecia(prod, listPrecioEspecial);

					// Primero me fijo si es un producto especial
					if (precioEspecial != null) {
						prod.cargarExtras(precioEspecial.getPricFechaDesde(), precioEspecial.getPricFechaHasta(),
								precioEspecial.getPricComision(), precioEspecial.getPricReferencia(),
								precioEspecial.getPricDescuento1(), precioEspecial.getPricDescuento2());
					} else {
						// Segundo me fijo si es un producto dentro de una famialia con descunto
						DescuentoXFamilias descuentoxFamilia = getDescuentoFamilia(prod, ListaDescuentoxFamilia);

						if (descuentoxFamilia != null) {
							prod.cargarExtras(descuentoxFamilia.getPricFamiliaFechaDesde(),
									descuentoxFamilia.getPricFamiliaFechaHasta(),
									descuentoxFamilia.getPricFamiliaComision(), descuentoxFamilia.getPricReferencia(),
									descuentoxFamilia.getPricFamiliaDescuento1(),
									descuentoxFamilia.getPricFamiliaDescuento2());
						}
						// Ultimo si no es nada es porque es de una lista heredada
					}
				}

				listaPrecioReporte.setListaProductos(listaProductoAux);
			}
		}
		return listaPrecioReporte;
	}

	private DescuentoXFamilias getDescuentoFamilia(ProductoDTO prod, List<DescuentoXFamilias> listaDescuentoxFamilia) {
		for (DescuentoXFamilias familia : listaDescuentoxFamilia) {
			if (prod.getFamiliaCod().equalsIgnoreCase(familia.getPricCa01Clasif1()) && CommonPricing
					.estaVigente(familia.getPricFamiliaFechaDesde(), familia.getPricFamiliaFechaHasta())) {
				return familia;
			}
		}
		return null;
	}

	private PreciosEspeciales getPrecioEspecia(ProductoDTO prod, List<PreciosEspeciales> listPrecioEspecial) {
		for (PreciosEspeciales precio : listPrecioEspecial) {
			if ((prod.getArtsArticulo() == precio.getPricArticulo())
					&& CommonPricing.estaVigente(precio.getPricFechaDesde(), precio.getPricFechaHasta())) {
				return precio;
			}
		}
		return null;
	}

	public ListaPrecioReporteDTO getListProductosReporte(boolean isArticuloLista, boolean isArticuloEspecifico,
			String textoBusqueda, boolean busquedaCodFamilia, boolean busquedaCodProducto,
			boolean busquedaDescripcion) {

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
				String[] vecTextoBusqueda = textoBusqueda.trim().split(" ");
				if ((busquedaDescripcion && contieneTexto(prod.getDescArticulo().toLowerCase(), vecTextoBusqueda))
						|| (busquedaCodProducto && contieneTexto(prod.getCodArticulo().toLowerCase(), vecTextoBusqueda))
						|| (busquedaCodFamilia
								&& contieneTexto(prod.getFamiliaCod().toLowerCase(), vecTextoBusqueda))) {
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

	private boolean contieneTexto(String codArticulo, String[] vecTextoBusqueda) {
		for (String registro : vecTextoBusqueda) {
			if (!codArticulo.contains(registro)) {
				return false;
			}
		}
		return true;
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
			if (prod.getCodArticulo()
					.equals(((String) tableResultado.getValueAt(i, ListaPrecioClienteView.COL_COD_ARTICULO)))) {
				return true;
			}
		}
		return false;
	}

}
