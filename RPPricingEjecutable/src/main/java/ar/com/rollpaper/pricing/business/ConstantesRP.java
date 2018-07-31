package ar.com.rollpaper.pricing.business;

public class ConstantesRP {

	public enum Acciones {
		CALCULADORA, SALIR, CONSULTA1, CARGA_PRECIO_CLIENTE, CARGA_CLIENTE_ESCLAVO ,GENERAR_PRECIOS
	}

	public enum PantCarPrecio {
		AGREGAR, ELIMINAR, CANCELAR, MODIFICAR, AGREGAR_LISTA, ELIMINAR_LISTA
	}

	public enum PantCarClienteEsclabo {
		AGREGAR, BORRAR, EXPORTAR, EXPORTAR_TODO, CANCELAR
	};

	public enum AccionesCargaItemFamilia {
		ACEPTAR, CANCELAR
	}

	public static String IMG_ERROR = "/images/error.png";
	public static String IMG_ICONO_APP = "/images/vector.png";
	public static String IMG_EXIT = "/images/exit.png";
	public static String IMG_CAL = "/images/Calculator.png";
	public static String IMG_MASTER_SLAVE = "/images/masterSlave.png";
	public static String IMG_PRICE_CONFIG = "/images/priceConfig.png";
}
