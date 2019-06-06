package ar.com.rollpaper.pricing.dto;

import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.StocArts;

public class PreciosEspecialesExDTO {

	private PreciosEspeciales precioEspecial;
	private StocArts stockArts;

	public PreciosEspecialesExDTO(PreciosEspeciales precioEspecial, StocArts stockArts) {
		super();
		this.precioEspecial = precioEspecial;
		this.stockArts = stockArts;
	}

	public PreciosEspeciales getPrecioEspecial() {
		return precioEspecial;
	}

	public StocArts getStockArts() {
		return stockArts;
	}

}
