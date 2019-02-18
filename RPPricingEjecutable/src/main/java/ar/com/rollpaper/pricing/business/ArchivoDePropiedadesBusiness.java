package ar.com.rollpaper.pricing.business;

import java.io.File;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.ArchivoPropiedades;

public class ArchivoDePropiedadesBusiness {

	private static final String ARCHI_CONF = "configuration.properties";
	private static ArchivoPropiedades<propiedades> pPropiedades = null;
	private static String pathToConfig;

	public static String getPathToConfig() {
		return pathToConfig;
	}

	public static void setPathToConfig(String pathToConfig) {
		ArchivoDePropiedadesBusiness.pathToConfig = pathToConfig;
	}

	public enum propiedades {
		conecctionString, pass, usr, separadorDecimal, separadorMiles
	};

	public static String getConecctionString() throws Exception {
		return getPropiedades().getPropiedad(propiedades.conecctionString);
	}

	private static ArchivoPropiedades<propiedades> getPropiedades() throws Exception {
		if (pPropiedades == null) {
			pPropiedades = new ArchivoPropiedades<>(new File(pathToConfig).getParent() + File.separator + ARCHI_CONF);

		}
		return pPropiedades;
	}

	public static String getPass() throws Exception {
		return getPropiedades().getPropiedad(propiedades.pass);
	}

	public static String getSeparadorDecimales() throws Exception {
		return getPropiedades().getPropiedad(propiedades.separadorDecimal, Common.getGeneralSettings().getSeparadorDecimal());
	}

	public static void setSeparadorDecimales(String valor) throws Exception {
		getPropiedades().setProperty(propiedades.separadorDecimal, valor);
	}

	public static String getSeparadorMiles() throws Exception {
		return getPropiedades().getPropiedad(propiedades.separadorMiles, Common.getGeneralSettings().getSeparadorMiles());
	}

	public static void setSeparadorMiles(String valor) throws Exception {
		getPropiedades().setProperty(propiedades.separadorMiles, valor);
	}

	public static String getUsr() throws Exception {
		return getPropiedades().getPropiedad(propiedades.usr);
	}

	public static void recargar() {
		pPropiedades = null;

	}

}
