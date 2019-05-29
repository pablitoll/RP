package ar.com.rollpaper.pricing.jasper;

import java.util.ArrayList;
import java.util.List;

import ar.com.rollpaper.pricing.beans.SistMone;
import ar.com.rollpaper.pricing.beans.SistUnim;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentArpv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.CommonPricing;
import ar.com.rollpaper.pricing.dao.SistMoneDAO;
import ar.com.rollpaper.pricing.dao.SistUnimDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.VentArpvDAO;
import ar.com.rp.rpcutils.FechaManagerUtil;

public class ReportesListas {

	private static final String MSG_LEYENDA_FECHA = "Valido al %s";

	// public static void getReporteListaPrecios(ListaPrecioReporteDTO
	// listaPrecioReporte) throws JRException {
	// // Parametros
	// HashMap<String, Object> SIMPLE_DATA = new HashMap<String, Object>();
	// SIMPLE_DATA.put("SubReportPath",
	// Main.class.getResource(ConstantesRP.REPO_LISTA_PRECIO_DETALLE));
	//
	// // Detalle
	// List<Map<String, ?>> detalleProductos = new ArrayList<>();
	//
	// for (ProductoDTO producto : listaPrecioReporte.getListaProductos()) {
	//
	// Map<String, String> map_renglon = new HashMap<String, String>();
	// map_renglon.put("codArticulo", producto.getCodArticulo());
	// map_renglon.put("nomArticulo", producto.getNomArticulo());
	// map_renglon.put("descArticulo", producto.getDescArticulo());
	// map_renglon.put("monedaArticulo", producto.getMonedaArticulo());
	// map_renglon.put("precioArticulo", producto.getPrecioArticulo());
	// detalleProductos.add(map_renglon);
	// }
	//
	// JRMapCollectionDataSource coleccion = new
	// JRMapCollectionDataSource(detalleProductos);
	// SIMPLE_DATA.put("detalleProductos", coleccion);
	//
	// // solo tengo un socio
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("codCliente", listaPrecioReporte.getId().toString());
	// map.put("nomCliente", listaPrecioReporte.getNomCliente());
	// map.put("nomLegal", listaPrecioReporte.getNomLegal());
	// map.put("listaProductos", listaPrecioReporte.getNroListaProducto());
	// map.put("leyendaFechaValida", listaPrecioReporte.getLeyendaFechaValida());
	//
	// map.put("idTC", "detalleProductos");
	//
	// // Reporte
	// List<Map<String, ?>> mapsDataSource = new ArrayList<Map<String, ?>>();
	// mapsDataSource.add(map);
	// JRMapCollectionDataSource dataSource = new
	// JRMapCollectionDataSource(mapsDataSource);
	//
	// HashMap<String, Object> parametros = new HashMap<String, Object>();
	// parametros.put("SIMPLE_DATA", SIMPLE_DATA);
	//
	// // compile report
	// JasperReport jasperReport = (JasperReport)
	// JRLoader.loadObject(Main.class.getResource(ConstantesRP.REPO_LISTA_PRECIO));
	// JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
	// parametros, dataSource);
	//
	// // view report to UI
	// JasperViewer.viewReport(jasperPrint, false);
	// }

	public static ListaPrecioReporteDTO getDatosReporte(VentLipv lista) {
		List<ProductoDTO> listaProductos = new ArrayList<ProductoDTO>();

		// Esta es la lista de precios bases
		for (VentArpv ventaBase : VentArpvDAO.findByListaID(lista.getLipvListaPrecvta())) {

			StocArts stock = StocArtsDAO.getArticuloByID(ventaBase.getId().getArpvArticulo());
			SistUnim unidad = SistUnimDAO.findById(stock.getArtsUnimedStock());
			SistMone moneda = SistMoneDAO.findById(ventaBase.getSistMoneByArpvMoneda().getMoneSimbolo());

			ProductoDTO producto = new ProductoDTO(stock.getArtsArticulo(), stock.getArtsArticuloEmp(),
					stock.getArtsNombre(), stock.getArtsDescripcion(), unidad.getUnimNombre(), moneda.getMoneNombre(),
					CommonPricing.formatearImporte(ventaBase.getArpvPrecioVta().doubleValue()), stock.getArtsClasif1(),
					true);

			listaProductos.add(producto);
		}
		String leyendaFecha = String.format(MSG_LEYENDA_FECHA,
				FechaManagerUtil.Date2String(FechaManagerUtil.getDateTimeFromPC()));

		return new ListaPrecioReporteDTO(-1, "", "", lista.getLipvNombre(), leyendaFecha, listaProductos);
	}

}
