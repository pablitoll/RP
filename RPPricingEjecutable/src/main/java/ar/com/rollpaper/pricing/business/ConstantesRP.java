package ar.com.rollpaper.pricing.business;

public class ConstantesRP {

	public enum Acciones {
		CALCULADORA, SALIR, LISTA_PRECIO_X_CLIENTE, CARGA_PRECIO_CLIENTE, CARGA_CLIENTE_ESCLAVO, GENERAR_PRECIOS
	}

	public enum PantCarPrecio {
		AGREGAR, ELIMINAR, CANCELAR, MODIFICAR, AGREGAR_LISTA, ELIMINAR_LISTA, IMPACTAR_PRECIOS
	}

	public enum PantCarClienteEsclabo {
		AGREGAR, BORRAR, EXPORTAR, EXPORTAR_TODO, CANCELAR
	};

	public enum AccionesCargaItemFamilia {
		ACEPTAR, CANCELAR
	}

	public enum PantListaPrecio {
		GENERAR_PDF, GENERAR_EXCEL, CANCELAR
	};

	public static String IMG_ERROR = "/images/error.png";
	public static String IMG_ICONO_APP = "/images/vector.png";
	public static String IMG_EXIT = "/images/exit.png";
	public static String IMG_CAL = "/images/Calculator.png";
	public static String IMG_MASTER_SLAVE = "/images/masterSlave.png";
	public static String IMG_PRICE_CONFIG = "/images/priceConfig.png";
	public static String IMG_PESOS = "/images/pesos.png";
	public static String IMG_RETORNO = "/images/retorno.jpg";
	
	public static String REPO_LISTA_PRECIO = "/reporte/report1.jasper";
}
