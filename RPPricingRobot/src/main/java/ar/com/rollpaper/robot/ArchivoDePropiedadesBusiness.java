package ar.com.rollpaper.robot;

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
		hora, minuto, idListaEspecial, conecctionString, pass, usr
	};

	private static ArchivoPropiedades<propiedades> getPropiedades() throws Exception {
		if (pPropiedades == null) {
			pPropiedades = new ArchivoPropiedades<>(new File(pathToConfig).getParent() + File.separator + ARCHI_CONF);

		}
		return pPropiedades;
	}

	public static String getHora() throws Exception {
		return getPropiedades().getPropiedad(propiedades.hora);
	}

	public static String getMinuto() throws Exception {
		return getPropiedades().getPropiedad(propiedades.minuto, Common.getGeneralSettings().getSeparadorDecimal());
	}

	public static void setHora(String valor) throws Exception {
		getPropiedades().setProperty(propiedades.hora, valor);
	}

	public static void setMinuto(String valor) throws Exception {
		getPropiedades().setProperty(propiedades.minuto, valor);
	}

	public static void recargar() {
		pPropiedades = null;
	}

	public static Integer getidListaEspecial() throws Exception {
		return Integer.valueOf(getPropiedades().getPropiedad(propiedades.idListaEspecial));
	}

	public static String getPass() throws Exception {
		return getPropiedades().getPropiedad(propiedades.pass);
	}

	public static String getUsr() throws Exception {
		return getPropiedades().getPropiedad(propiedades.usr);
	}

	public static String getConecctionString() throws Exception {
		return getPropiedades().getPropiedad(propiedades.conecctionString);
	}

}
