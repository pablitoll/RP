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

		// fill report
		List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
		// solo tengo un socio
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codArticulos", listaPrecioReporte.getId().toString());
//		map.put("nomArticulos", "nomArticulos1");
//		map.put("descArticulos", "nomArticulos2");
//		map.put("monedaArticulos", "nomArticulos3");
//		map.put("precioArticulos", "nomArticulos4");
		maps.add(map);

		JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);

		// compile report
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject( Main.class.getResource(ConstantesRP.REPO_LISTA_PRECIO));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(), dataSource);

		// view report to UI
		JasperViewer.viewReport(jasperPrint, false);
	}
}
