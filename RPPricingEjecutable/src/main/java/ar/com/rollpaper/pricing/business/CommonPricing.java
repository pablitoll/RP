package ar.com.rollpaper.pricing.business;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.RPImporte;

public class CommonPricing {

	public static String formatearImporte(double valor) {
		 return RPImporte.formatearImporte(Common.double2String(valor), true, 4);
	}
	
	public static String formatearImporte(String valor) {
		 return RPImporte.formatearImporte(valor, true, 4);
	}

}
