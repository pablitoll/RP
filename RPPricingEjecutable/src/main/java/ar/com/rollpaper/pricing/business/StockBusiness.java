package ar.com.rollpaper.pricing.business;

import java.util.List;

import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;

public class StockBusiness {

	public static List<StocArts> getListaStockxFamilia(String familiaID) {
		return StocArtsDAO.getListaArticulosxFamilia(familiaID);
	}

}
