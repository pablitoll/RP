package ar.com.rollpaper.pricing.business;

public class ConstantesRP {

	public enum Acciones {
		CALCULADORA, SALIR, LISTA_PRECIO_X_CLIENTE, CARGA_PRECIO_CLIENTE, CARGA_CLIENTE_ESCLAVO, GENERAR_PRECIOS, CAMBIAR_DB, LISTA_PRECIO_GENERALES, CAMBIAR_SEPARADOR_DECIMAL, CAMBIAR_SEPARADOR_MILES
	}

	public enum PantCarPrecio {
		AGREGAR, ELIMINAR, MODIFICAR, AGREGAR_LISTA, ELIMINAR_LISTA, IMPACTAR_PRECIOS, CANCELAR
	}

	public enum PantCarClienteEsclabo {
		AGREGAR, BORRAR, EXPORTAR, EXPORTAR_TODO, TERMINAR_CARGA, CANCELAR
	};

	public enum AccionesCargaItemFamilia {
		ACEPTAR, CANCELAR
	}

	public enum PantListaPrecio {
		GENERAR_PDF, GENERAR_EXCEL, CANCELAR, RECARGAR
	}

	public enum PantListaPrecioXLista {
		GENERAR_PDF, GENERAR_EXCEL, RECARGAR
	}



	public static String IMG_EDIT_ICON = "images/edit-icon.png";
	public static String IMG_OK = "images/ok.png";
	public static String IMG_SEARCH = "images/search.png";
	public static String IMG_ERROR = "images/error.png";
	public static String IMG_ICONO_APP = "images/vector.png";
	public static String IMG_EXIT = "images/exit.png";
	public static String IMG_CAL = "images/Calculator.png";
	public static String IMG_MASTER_SLAVE = "images/masterSlave.png";
	public static String IMG_PRICE_CONFIG = "images/priceConfig.png";
	public static String IMG_PESOS = "images/pesos.png";
	public static String IMG_RETORNO = "images/retorno.jpg";
	public static String IMG_EXCEL = "images/ExcelIcono.png";
	public static String IMG_PDF = "images/descargaPDF.png";
	public static String IMG_RECARGAR = "images/reload.jpg";

	public static String REPO_LISTA_PRECIO_ORIGINAL = "/reporte/reportOriginal.jasper";
	public static String REPO_LISTA_PRECIO = "/reporte/report1.jasper";
	public static String REPO_LISTA_PRECIO_DETALLE = "/reporte/";
}
