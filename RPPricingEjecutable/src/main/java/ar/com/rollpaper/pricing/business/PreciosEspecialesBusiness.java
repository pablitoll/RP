package ar.com.rollpaper.pricing.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dto.PreciosEspecialesExDTO;

public class PreciosEspecialesBusiness {

	public static List<PreciosEspecialesExDTO> getListaPrecioEspeciaByFiltros(Integer idCliente, Date fechaVencidosAl,
			Date fechaVencidosDesde, String busqueda, Double tcDesde, Double tcHasta) {

		List<PreciosEspecialesExDTO> retorno = new ArrayList<PreciosEspecialesExDTO>();

		for (PreciosEspeciales precioEspeciales : PreciosEspecialesDAO.getListaPrecioEspeciaByFiltros(idCliente,
				fechaVencidosAl, fechaVencidosDesde)) {

			StocArts stockArts = StocArtsDAO.findById(precioEspeciales.getPricArticulo());

			if ((busqueda == null) || (stockArts.getArtsDescripcion().toLowerCase().contains(busqueda.toLowerCase()))
					|| (stockArts.getArtsArticuloEmp().toLowerCase().contains(busqueda.toLowerCase()))
					|| (stockArts.getArtsNombre().toLowerCase().contains(busqueda.toLowerCase()))) {

				if ((precioEspeciales.getPricValorTC() == null)
						|| ((tcDesde == null) || (precioEspeciales.getPricValorTC() >= tcDesde))) {

					if ((precioEspeciales.getPricValorTC() == null)
							|| ((tcHasta == null) || (precioEspeciales.getPricValorTC() <= tcHasta))) {
						retorno.add(new PreciosEspecialesExDTO(precioEspeciales, stockArts));

					}
				}

			}

		}
		return retorno;
	}

}
