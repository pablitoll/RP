package ar.com.rollpaper.pricing.business;

import java.io.File;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.ArchivoPropiedades;
import ar.com.rp.ui.exceptions.CSCPropiedadNoDefinida;

public class ArchivoDePropiedadesBusiness {

	private static final String ARCHI_CONF = "configuration.properties";
	private static final String ARCHI_PROPIEDADES_TABLA = "tablas.properties";
	private static ArchivoPropiedades<propiedades> pPropiedades = null;
	private static String pathToConfig;
	private static ArchivoPropiedades<String> pPropiedadesTabla = null;

	public static String getPathToConfig() {
		return pathToConfig;
	}

	public static void setPathToConfig(String pathToConfig) {
		ArchivoDePropiedadesBusiness.pathToConfig = pathToConfig;
	}

	public enum propiedades {
		conecctionString, pass, usr, separadorDecimal, separadorMiles, idListaEspecial
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

	private static ArchivoPropiedades<String> getPropiedadesAnchoTabla() throws Exception {
		if (pPropiedadesTabla == null) {

			String a = new File(pathToConfig).getParent() + File.separator + ARCHI_PROPIEDADES_TABLA;

			File archivo = new File(a);

			if (!archivo.exists()) {
				archivo.createNewFile();
			}

			pPropiedadesTabla = new ArchivoPropiedades<>(a);

		}
		return pPropiedadesTabla;
	}

	public static String getPass() throws Exception {
		return getPropiedades().getPropiedad(propiedades.pass);
	}

	public static String getSeparadorDecimales() throws Exception {
		return getPropiedades().getPropiedad(propiedades.separadorDecimal,
				Common.getGeneralSettings().getSeparadorDecimal());
	}

	public static void setSeparadorDecimales(String valor) throws Exception {
		getPropiedades().setProperty(propiedades.separadorDecimal, valor);
	}

	public static String getSeparadorMiles() throws Exception {
		return getPropiedades().getPropiedad(propiedades.separadorMiles,
				Common.getGeneralSettings().getSeparadorMiles());
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

	public static Integer getidListaEspecial() throws Exception {
		return Integer.valueOf(getPropiedades().getPropiedad(propiedades.idListaEspecial));
	}

	public static int[] getAnchoTabla(String nombreTabla) throws Exception {
		String valor = null;
		try {
			valor = getPropiedadesAnchoTabla().getPropiedad(nombreTabla);
		} catch (CSCPropiedadNoDefinida e) {
		}

		if (valor != null) {
			String registro[] = valor.split(";");

			int[] retorno = new int[registro.length];

			for (int i = 0; i < registro.length; i++) {
				retorno[i] = Integer.valueOf(registro[i]);
			}

			return retorno;
		}
		return null;
	}

	public static void setAnchoTabla(String nombreTabla, int[] vectorAncho) throws Exception {
		String registro = "";
		for (int i = 0; i < vectorAncho.length; i++) {
			registro += String.format("%s;", vectorAncho[i]);
		}

		getPropiedadesAnchoTabla().setProperty(nombreTabla, registro);
	}

}
