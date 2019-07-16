package ar.com.rollpaper.pricing.business;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dto.ListaDTO;

public class MaestroEsclavoBusinnes {

	public static Integer getPadreId(CcobClie escalvo, ListaDTO listaCargada) {
		for (MaestroEsclavo reg : MaestroEsclavoDAO.getListaEsclavosByEsclavo(escalvo)) {
			if (listaCargada.getVentLipv().getLipvListaPrecvta() == reg.getPricMEListaPrecvta()) {
				return reg.getPricMaestroCliente();
			}
		}
		return null;
	}

}
