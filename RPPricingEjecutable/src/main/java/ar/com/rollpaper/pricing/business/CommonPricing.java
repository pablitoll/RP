package ar.com.rollpaper.pricing.business;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.RPImporte;

public class CommonPricing {

	public static String formatearImporte(double valor) {
		return RPImporte.formatearImporte(Common.double2String(valor), true, 4);
	}

	public static String formatearImporte(double valor, int cantDecmales) {
		return RPImporte.formatearImporte(Common.double2String(valor), true, cantDecmales);
	}

	public static String formatearImporte(String valor) {
		return RPImporte.formatearImporte(valor, true, 4);
	}

	public static String formatearImporte(String valor, int cantDecmales) {
		return RPImporte.formatearImporte(valor, true, cantDecmales);
	}

	public static Boolean estaVigente(Date pricFechaDesde, Date pricFechaHasta) {
		return (FechaManagerUtil.getDateDiff(pricFechaDesde, FechaManagerUtil.getDateTimeFromPC(),
				TimeUnit.MINUTES) <= 0)
				&& (FechaManagerUtil.getDateDiff(pricFechaHasta, FechaManagerUtil.getDateTimeFromPC(),
						TimeUnit.MINUTES) >= 0);
	}
}
