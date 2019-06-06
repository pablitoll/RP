package ar.com.rollpaper.pricing.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.StocCa01;
import ar.com.rollpaper.pricing.beans.VentArpv;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
import ar.com.rollpaper.pricing.dao.StocCa01DAO;

public class FamiliaBusiness {

	public static boolean estaFamiliaEnLista(String familiaID, int nroLista) {
		List<StocCa01> listaFamilia = new ArrayList<StocCa01>();
		StocCa01 familia = StocCa01DAO.findById(familiaID); // Busco la familia a verificar
		if (familia != null) {
			listaFamilia.add(familia);

			return getListaFamilia(listaFamilia, nroLista).size() > 0; // si hay algun producto para la familia,
																		 // entonces esta
		}
		return false;
	}

	public static List<StocCa01> getListaFamilia(String nombre, int nroLista) {
		return getListaFamilia(StocCa01DAO.getListaFamiliaByDesc_ID(nombre), nroLista);
	}

	private static List<StocCa01> getListaFamilia(List<StocCa01> listaFamilia, int nroLista) {
		// StocCa01 -> Familias
		// VentArpv -> Articulo / Lista
		// StocArts -> Articulo / Familia

		List<StocCa01> retorno = new ArrayList<StocCa01>();

		List<VentArpv> listaStockxLista = ListaBusiness.getListaStockxLista(nroLista);

		for (StocCa01 familia : listaFamilia) { // Todas las familias
			boolean estaArtEnLista = false;
			List<StocArts> listaArticulosFamilia = StockBusiness.getListaStockxFamilia(familia.getCa01Clasif1());
			for (int i = 0; i < listaArticulosFamilia.size() && !estaArtEnLista; i++) { // Articulos de la familia
				StocArts articuloFamilia = listaArticulosFamilia.get(i);
				if (estaEnLista(articuloFamilia.getArtsArticulo(), listaStockxLista)) { // Si esta el articulo en la
																						 // lista, la familia se puede
																						 // seleccionar
					retorno.add(familia);
					estaArtEnLista = true;
				}
			}

		}
		return retorno;
	}

	private static boolean estaEnLista(int articuloId, List<VentArpv> listaStockxLista) {
		for (VentArpv art : listaStockxLista) {
			if (art.getId().getArpvArticulo() == articuloId) {
				return true;
			}
		}
		return false;
	}

	public static List<DescuentoXFamilias> getListaDescuentoByFiltros(Integer idCliente, Date fechaVencidosAl,
			Date fechaVencidosDesde, String busqueda) {

		List<DescuentoXFamilias> aux = DescuentoXFamiliasDAO.getListaDescuentoByFiltros(idCliente, fechaVencidosAl,
				fechaVencidosDesde);
		List<DescuentoXFamilias> retorno = new ArrayList<DescuentoXFamilias>();

		if (busqueda != null) {
			busqueda = busqueda.toLowerCase();
			for (DescuentoXFamilias des : aux) {
				if (des.getNombreFamilia().toLowerCase().contains(busqueda)
						|| des.getPricCa01Clasif1().toLowerCase().contains(busqueda)) {
					retorno.add(des);
				}
			}

		} else {
			retorno = aux;
		}
		return retorno;
	}

}
