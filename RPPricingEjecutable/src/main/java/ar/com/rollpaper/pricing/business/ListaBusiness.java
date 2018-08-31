package ar.com.rollpaper.pricing.business;

import java.util.List;

import ar.com.rollpaper.pricing.beans.VentArpv;
import ar.com.rollpaper.pricing.dao.VentArpvDAO;

public class ListaBusiness {

	public static List<VentArpv> getListaStockxLista(int listaID) {
		return VentArpvDAO.findByListaID(listaID);
	}


}
