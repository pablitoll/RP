package ar.com.rollpaper.pricing.business;

import java.util.ArrayList;
import java.util.List;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.VentArpv;
import ar.com.rollpaper.pricing.beans.VentArpvId;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dao.VentArpvDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.dto.ListaDTO;

public class ListaBusiness {

	public static List<VentArpv> getListaStockxLista(int listaID) {
		return VentArpvDAO.findByListaID(listaID);
	}

	public static List<ListaDTO> getTodasListas() {
		List<ListaDTO> retorno = new ArrayList<ListaDTO>();

		for (VentLipv listaClienteLista : VentLipvDAO.getAllLists()) {
			retorno.add(new ListaDTO(listaClienteLista, false, false));
		}

		return retorno;
	}

	public static List<ListaDTO> getListaToShow(CcobClie clienteCargado) {
		List<ListaDTO> retorno = new ArrayList<ListaDTO>();
		VentLipv lista;
		// Busco la lista principal
		for (VentCliv listaClienteLista : VentClivDAO.getListaPreciosByCliente(clienteCargado)) {
			if (listaClienteLista.getClivListaPrecvta() != null) {
				lista = VentLipvDAO.findById(listaClienteLista.getClivListaPrecvta());
				retorno.add(new ListaDTO(lista, true, false));
			}
		}

		// Busco las lista que tenga en las otras tablas
		for (DescuentoXFamilias familia : DescuentoXFamiliasDAO.getByCliente(clienteCargado.getClieCliente())) {
			lista = VentLipvDAO.findById(familia.getPricFamiliaListaPrecvta());
			if (lista != null) {
				if (!isInLista(retorno, lista)) {
					retorno.add(new ListaDTO(lista, false, false));
				}
			}
		}

		for (PreciosEspeciales especial : PreciosEspecialesDAO.getByCliente(clienteCargado.getClieCliente())) {
			lista = VentLipvDAO.findById(especial.getPricListaPrecvta());
			if (lista != null) {
				if (!isInLista(retorno, lista)) {
					retorno.add(new ListaDTO(lista, false, false));
				}
			}
		}

		for (MaestroEsclavo maestro : MaestroEsclavoDAO.getListaEsclavosByEsclavo(clienteCargado)) {
			lista = VentLipvDAO.findById(maestro.getPricMEListaPrecvta());
			if (lista != null) {
				if (!isInLista(retorno, lista)) {
					retorno.add(new ListaDTO(lista, false, true));
				}
			}
		}

		return retorno;
	}

	private static boolean isInLista(List<ListaDTO> lista, VentLipv registro) {
		for (ListaDTO aux : lista) {
			if (aux.getVentLipv().getLipvListaPrecvta() == registro.getLipvListaPrecvta()) {
				return true;
			}
		}
		return false;
	}

	public static Boolean isArticuloEnLista(Integer idArticulo, Integer idLista) {
		VentArpvId id = new VentArpvId(idArticulo, idLista);
		VentArpv resp = VentArpvDAO.findById(id);
		return resp != null;
	}

	public static List<VentCliv> getListaPreciosByCliente(CcobClie cliente) {
		return VentClivDAO.getListaPreciosByCliente(cliente);
	}

}
