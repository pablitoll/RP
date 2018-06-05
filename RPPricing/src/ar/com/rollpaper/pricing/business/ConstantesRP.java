package ar.com.rollpaper.pricing.business;

public class ConstantesRP {

	public enum Acciones {
		CALCULADORA, SALIR, CONSULTA1, CARGA_PRECIO_CLIENTE, CARGA_CLIENTE_ESCLAVO
	}

	public enum PantCarPrecio {
		AGREGAR, ELIMINAR, CANCELAR, MODIFICAR
	}

	public enum PantCarClienteEsclabo {
		AGREGAR, BORRAR, GRABAR, IMPRIMIR, IMPRIMIR_TODO, CANCELAR
	};

	public enum AccionesCargaItemFamilia {
		ACEPTAR, CANCELAR
	}

	public static String IMG_ERROR = "/resource/error.png";
	public static String IMG_ICONO_APP = "/resource/vector.png";
	public static String IMG_EXIT = "/resource/exit.png";
	public static String IMG_CAL = "/resource/Calculator.png";
	public static String IMG_MASTER_SLAVE = "/resource/masterSlave.png";
	public static String IMG_PRICE_CONFIG = "/resource/priceConfig.png";
}
