package ar.com.rollpaper.pricing.jasper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.ui.Main;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reportes {

	public static void getReporteListaPrecios(ListaPrecioReporteDTO listaPrecioReporte) throws JRException {
		// Parametros
		HashMap<String, Object> SIMPLE_DATA = new HashMap<String, Object>();
		SIMPLE_DATA.put("SubReportPath", Main.class.getResource(ConstantesRP.REPO_LISTA_PRECIO_DETALLE));

		// Detalle
		List<Map<String, ?>> detalleProductos = new ArrayList<>();

		for (ProductoDTO producto : listaPrecioReporte.getListaProductos()) {

			Map<String, String> map_renglon = new HashMap<String, String>();
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
		
		map.put("idTC", "detalleProductos");

		
		// Reporte
		List<Map<String, ?>> mapsDataSource = new ArrayList<Map<String, ?>>();
		mapsDataSource.add(map);
		JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(mapsDataSource);

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("SIMPLE_DATA", SIMPLE_DATA);

		// compile report
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(Main.class.getResource(ConstantesRP.REPO_LISTA_PRECIO));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

		// view report to UI
		JasperViewer.viewReport(jasperPrint, false);
	}
}
