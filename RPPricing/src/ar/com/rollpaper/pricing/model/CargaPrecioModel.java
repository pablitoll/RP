package ar.com.rollpaper.pricing.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alee.laf.table.WebTable;

import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaPrecioModel extends BaseModel {

	public void grabar(WebTable tabla) throws Exception {
		for (int i = 0; i < tabla.getRowCount(); i++) {

			int pricArticulo = (int) tabla.getValueAt(i, CargaPrecioView.COL_ID_ESPECIFICO);
			BigDecimal pricDescuento1 = null; // (BigDecimal) tabla.getValueAt(i, CargaPrecioView.COL_1DESC_ESPECIFICO);
			BigDecimal pricDescuento2 = null; // (BigDecimal) tabla.getValueAt(i, CargaPrecioView.COL_2DESC_ESPECIFICO);
			String pricMoneda = (String) tabla.getValueAt(i, CargaPrecioView.COL_MONEDA_ESPECIFICO);
			BigDecimal pricPrecio = null; // (BigDecimal) tabla.getValueAt(i, CargaPrecioView.COL_PRECIO_ESPECIFICO);
			Date pricFechaDesde = new Date(); // tabla.getValueAt(i, CargaPrecioView.COL_ID_ESPECIFICO);
			Date pricFechaHasta = new Date(); // tabla.getValueAt(i, CargaPrecioView.COL_ID_ESPECIFICO);
			String pricReferencia = (String) tabla.getValueAt(i, CargaPrecioView.COL_REFERENCIA_ESPECIFICO);

			PreciosEspeciales preciosEspeciales = new PreciosEspeciales(84, -1, pricArticulo, pricDescuento1,
					pricDescuento2, pricMoneda, pricPrecio, pricFechaDesde, pricFechaHasta, pricReferencia);

			HibernateGeneric.persist(preciosEspeciales);
		}
	}

	public PreciosEspeciales getRegistroPedidoEspecial(WebTable tablaPedidoEspecial, int row) {

		int pricArticulo = (int) tablaPedidoEspecial.getValueAt(row, CargaPrecioView.COL_ID_ESPECIFICO);
		BigDecimal pricDescuento1 = null; // (BigDecimal) tabla.getValueAt(i, CargaPrecioView.COL_1DESC_ESPECIFICO);
		BigDecimal pricDescuento2 = null; // (BigDecimal) tabla.getValueAt(i, CargaPrecioView.COL_2DESC_ESPECIFICO);
		String pricMoneda = (String) tablaPedidoEspecial.getValueAt(row, CargaPrecioView.COL_MONEDA_ESPECIFICO);
		BigDecimal pricPrecio = null; // (BigDecimal) tabla.getValueAt(i, CargaPrecioView.COL_PRECIO_ESPECIFICO);
		Date pricFechaDesde = new Date(); // tabla.getValueAt(i, CargaPrecioView.COL_ID_ESPECIFICO);
		Date pricFechaHasta = new Date(); // tabla.getValueAt(i, CargaPrecioView.COL_ID_ESPECIFICO);
		String pricReferencia = (String) tablaPedidoEspecial.getValueAt(row, CargaPrecioView.COL_REFERENCIA_ESPECIFICO);

		PreciosEspeciales preciosEspeciales = new PreciosEspeciales(84, -1, pricArticulo, pricDescuento1,
				pricDescuento2, pricMoneda, pricPrecio, pricFechaDesde, pricFechaHasta, pricReferencia);

		return preciosEspeciales;
	}

	public PreciosEspeciales getRegistroPedidoEspecialEmpty() {
		PreciosEspeciales preciosEspeciales = new PreciosEspeciales();
		preciosEspeciales.setPricCliente(84);
		return preciosEspeciales;
	}
}
