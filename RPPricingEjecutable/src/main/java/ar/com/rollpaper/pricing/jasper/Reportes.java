package ar.com.rollpaper.pricing.jasper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.SistMone;
import ar.com.rollpaper.pricing.beans.SistUnim;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.StocCa01;
import ar.com.rollpaper.pricing.beans.VentArpc;
import ar.com.rollpaper.pricing.beans.VentArpv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.CommonPricing;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.SistMoneDAO;
import ar.com.rollpaper.pricing.dao.SistUnimDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.StocCa01DAO;
import ar.com.rollpaper.pricing.dao.VentArpcDAO;
import ar.com.rollpaper.pricing.dao.VentArpvDAO;
import ar.com.rollpaper.pricing.ui.Main;
import ar.com.rp.rpcutils.FechaManagerUtil;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reportes {

	private static final String MSG_LEYENDA_FECHA = "Valido al %s";

	public static void getReporteListaPreciosOriginales(ListaPrecioReporteDTO listaPrecioReporte) throws Exception {
		getReporteLista(true, listaPrecioReporte, ConstantesRP.REPO_LISTA_PRECIO_ORIGINAL);
	}

	public static void getReporteListaPrecios(ListaPrecioReporteDTO listaPrecioReporte) throws Exception {
		getReporteLista(false, listaPrecioReporte, ConstantesRP.REPO_LISTA_PRECIO);
	}

	private static void getReporteLista(boolean isListaOriginal, ListaPrecioReporteDTO listaPrecioReporte,
			String reporteArchivo) throws Exception {
		// Parametros
		HashMap<String, Object> SIMPLE_DATA = new HashMap<String, Object>();
		SIMPLE_DATA.put("SubReportPath", Main.class.getResource(ConstantesRP.REPO_LISTA_PRECIO_DETALLE));

		// Detalle
		List<Map<String, ?>> detalleProductos = new ArrayList<>();

		// Lo ordenamos por familia y articulo
		Collections.sort(listaPrecioReporte.getListaProductos(), new SortByProducto());

		String codFamilia = "";
		for (ProductoDTO producto : listaPrecioReporte.getListaProductos()) {

			Map<String, String> map_renglon = new HashMap<String, String>();
			if (!codFamilia.equals(producto.getFamiliaCod())) {

				StocCa01 familiaClass = StocCa01DAO.findById(producto.getFamiliaCod());

				map_renglon.put("nombreFamilia",
						String.format("%s - %s", producto.getFamiliaCod(), familiaClass.getCa01Nombre()));
				codFamilia = producto.getFamiliaCod();
			} else {
				map_renglon.put("nombreFamilia", "");
			}
			map_renglon.put("codArticulo", producto.getCodArticulo());
			map_renglon.put("nomArticulo", producto.getNomArticulo());
			map_renglon.put("descArticulo", producto.getDescArticulo());
			map_renglon.put("monedaArticulo", producto.getMonedaArticulo());
			map_renglon.put("precioArticulo", producto.getPrecioArticulo());
			detalleProductos.add(map_renglon);
		}

		JRMapCollectionDataSource coleccion = new JRMapCollectionDataSource(detalleProductos);
		SIMPLE_DATA.put("detalleProductos", coleccion);

		// solo tengo un socio
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codCliente", listaPrecioReporte.getId().toString());
		map.put("nomCliente", listaPrecioReporte.getNomCliente());
		map.put("nomLegal", listaPrecioReporte.getNomLegal());
		map.put("listaProductos", listaPrecioReporte.getNroListaProducto());
		map.put("leyendaFechaValida", listaPrecioReporte.getLeyendaFechaValida());

		map.put("idTC", "detalleProductos");

		// Reporte
		List<Map<String, ?>> mapsDataSource = new ArrayList<Map<String, ?>>();
		mapsDataSource.add(map);
		JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(mapsDataSource);

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SIMPLE_DATA", SIMPLE_DATA);

		// compile report
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(Main.class.getResource(reporteArchivo));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

		// view report to UI
		JasperViewer.viewReport(jasperPrint, false);
	}

	public static ListaPrecioReporteDTO getDatosReporteFiltrado(CcobClie cliente, VentLipv lista,
			List<ProductoDTO> listaProductos) {
		List<ProductoDTO> listaProductosToAdd = new ArrayList<ProductoDTO>();

		for (ProductoDTO prod : listaProductos) {

			StocArts stock = StocArtsDAO.getArticuloByID(prod.getArtsArticulo());
			SistUnim unidad = SistUnimDAO.findById(stock.getArtsUnimedStock());
			SistMone moneda = SistMoneDAO.findById(prod.getMonedaArticulo());

			ProductoDTO producto = new ProductoDTO(stock.getArtsArticulo(), stock.getArtsArticuloEmp(),
					stock.getArtsNombre(), stock.getArtsDescripcion(), unidad.getUnimNombre(), moneda.getMoneNombre(),
					prod.getPrecioArticulo(), stock.getArtsClasif1(), false);
			listaProductosToAdd.add(producto);
		}

		String leyendaFecha = String.format(MSG_LEYENDA_FECHA,
				FechaManagerUtil.Date2String(FechaManagerUtil.getDateTimeFromPC()));

		return new ListaPrecioReporteDTO(cliente.getClieCliente(), cliente.getClieNombre(),
				cliente.getClieNombreLegal(), lista.getLipvNombre(), leyendaFecha, listaProductosToAdd);
	}

	public static ListaPrecioReporteDTO getDatosReporte(CcobClie cliente, VentLipv lista) {
		List<ProductoDTO> listaProductos = new ArrayList<ProductoDTO>();

		// Lista de precios customizados
		List<VentArpc> listaVentaCustomizada = VentArpcDAO.findByListaByClient(cliente.getClieCliente(),
				lista.getLipvListaPrecvta());
		for (VentArpc ventaCustomizada : listaVentaCustomizada) {

			StocArts stock = StocArtsDAO.getArticuloByID(ventaCustomizada.getId().getArpcArticulo());
			SistUnim unidad = SistUnimDAO.findById(stock.getArtsUnimedStock());
			SistMone moneda = SistMoneDAO.findById(ventaCustomizada.getArpcMoneda());

			ProductoDTO producto = new ProductoDTO(stock.getArtsArticulo(), stock.getArtsArticuloEmp(),
					stock.getArtsNombre(), stock.getArtsDescripcion(), unidad.getUnimNombre(), moneda.getMoneNombre(),
					CommonPricing.formatearImporte(ventaCustomizada.getArpcPrecioVta().doubleValue()),
					stock.getArtsClasif1(), false);
			listaProductos.add(producto);
		}

		// Esta es la lista de precios bases
		for (VentArpv ventaBase : VentArpvDAO.findByListaID(lista.getLipvListaPrecvta())) {

			if (!estaArticuloEnListaCustomizada(listaVentaCustomizada, ventaBase)) {

				StocArts stock = StocArtsDAO.getArticuloByID(ventaBase.getId().getArpvArticulo());
				SistUnim unidad = SistUnimDAO.findById(stock.getArtsUnimedStock());
				SistMone moneda = SistMoneDAO.findById(ventaBase.getSistMoneByArpvMoneda().getMoneSimbolo());

				ProductoDTO producto = new ProductoDTO(stock.getArtsArticulo(), stock.getArtsArticuloEmp(),
						stock.getArtsNombre(), stock.getArtsDescripcion().trim(), unidad.getUnimNombre(),
						moneda.getMoneNombre(),
						CommonPricing.formatearImporte(ventaBase.getArpvPrecioVta().doubleValue()),
						stock.getArtsClasif1(), true);

				listaProductos.add(producto);
			} else {
				actualizarProductoEnLista(listaProductos, ventaBase);
			}
		}
		String leyendaFecha = String.format(MSG_LEYENDA_FECHA,
				FechaManagerUtil.Date2String(FechaManagerUtil.getDateTimeFromPC()));

		return new ListaPrecioReporteDTO(cliente.getClieCliente(), cliente.getClieNombre(),
				cliente.getClieNombreLegal(), lista.getLipvNombre(), leyendaFecha, listaProductos);
	}

	private static void actualizarProductoEnLista(List<ProductoDTO> listaProductos, VentArpv ventaBase) {
		StocArts stockABuscar = StocArtsDAO.getArticuloByID(ventaBase.getId().getArpvArticulo());
		for (ProductoDTO prod : listaProductos) {
			if (prod.getCodArticulo().equals(stockABuscar.getArtsArticuloEmp())) {
				prod.setProdListaBase(true);
			}
		}
	}

	private static boolean estaArticuloEnListaCustomizada(List<VentArpc> listaVentaCustomizada, VentArpv ventaBase) {
		for (VentArpc venta : listaVentaCustomizada) {
			if (venta.getId().getArpcArticulo() == ventaBase.getId().getArpvArticulo()) {
				return true;
			}
		}
		return false;
	}
}

class SortByProducto implements Comparator<ProductoDTO> {

	@Override
	public int compare(ProductoDTO prod0, ProductoDTO prod1) {
		int value1 = prod0.getFamiliaCod().compareTo(prod1.getFamiliaCod());
		if (value1 == 0) {
			return prod0.getArtsArticulo() - prod1.getArtsArticulo();
		} else {
			return value1;
		}
	}

}