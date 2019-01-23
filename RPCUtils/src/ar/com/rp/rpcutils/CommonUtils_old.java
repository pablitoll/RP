package ar.com.rp.rpcutils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;

import javax.swing.ImageIcon;

public class CommonUtils_old {

	public static boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	public static boolean isOnlyNumeric(String s) {
		return s.matches("\\d*\\d+");
	}

	public static String SetHTMLColor(String texto, String color, boolean conHTML) {
		return SetHTML(texto, null, color, null, conHTML);
	}

	public static String SetHTMLColor(String texto, String color) {
		return SetHTML(texto, null, color, null, true);
	}

	public static String SetHTMLCenter(String texto) {
		return SetHTML(texto, null, null, "center", true);
	}

	public static String SetHTML(String texto, Integer size, String color, String aling, boolean conHTML) {
		String[] nuevoTexto = new String[1];
		nuevoTexto[0] = texto;

		return SetHTML(nuevoTexto, size, color, aling, conHTML);
	}

	public static String SetHTML(String[] texto) {
		return SetHTML(texto, null, null, null, true);
	}

	public static String SetHTML(String[] texto, Integer size, String color, String aling, boolean conHTML) {
		String retorno = "";
		if (conHTML) {
			retorno = "<html>";
		}

		// Alineacion
		if (aling != null) {
			retorno += String.format("<%s>", aling);
		}

		// Font
		if ((size != null) || (color != null)) {
			String strSize = (size != null) ? "size=" + size : "";
			String strColor = (color != null) ? "color=" + color : "";

			retorno += String.format("<font %s %s>", strSize, strColor);
		}

		// Texto
		for (int i = 0; i < texto.length; i++) {
			retorno = retorno + texto[i] + "<br>";
		}

		// Fin Font
		if ((size != null) || (color != null)) {
			retorno += "</font>";
		}

		// Fin Alineacion
		if (aling != null) {
			retorno += String.format("</%s>", aling);
		}

		if (conHTML) {
			retorno += "</html>";
		}

		return retorno;
	}

	public static String PadLeft(Integer valor, Integer Cant, String Pad) {
		return PadLeft(String.valueOf(valor), Cant, Pad);
	}

	public static String PadLeftOrOriginal(String valor, Integer Cant, String Pad) {
		if (valor.length() > Cant) {
			return valor;
		} else {
			return PadLeft(valor, Cant, Pad);
		}
	}

	public static String PadLeft(String valor, Integer Cant, String Pad) {
		String repeated = new String(new char[Cant]).replace("\0", Pad);
		String newValor = repeated + valor;
		return newValor.substring(newValor.length() - Cant, newValor.length());
	}

	public static String PadRigth(String valor, Integer Cant, String Pad) {
		String repeated = new String(new char[Cant]).replace("\0", Pad);
		String newValor = valor + repeated;
		return newValor.substring(0, Cant);
	}

	public static String strLeft(String texto, Integer longitud) {
		return texto.substring(0, longitud);
	}

	public static String strRigth(String texto, Integer longitud) {
		Integer desde = texto.length() - longitud;
		if (desde < 0) {
			desde = 1;
		}

		return texto.substring(desde, desde + longitud);
	}

	public static void copyToClipboard(String texto) {
		StringSelection stringSelection = new StringSelection(texto.trim());
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}

	public static Image loadImage(String nombrePicture, Integer alto, Integer ancho) {
		Image img = new ImageIcon(nombrePicture).getImage();
		Image newimg = img.getScaledInstance(alto, ancho, java.awt.Image.SCALE_SMOOTH);

		return newimg;
	}
	
	public static Image loadImage(URL nombrePictureURL, Integer alto, Integer ancho) {
		Image img = new ImageIcon(nombrePictureURL).getImage();
		Image newimg = img.getScaledInstance(alto, ancho, java.awt.Image.SCALE_SMOOTH);

		return newimg;
	}


	public static ImageIcon loadIcon(URL iconoURL, Integer alto, Integer ancho) {
		ImageIcon icon = new ImageIcon(iconoURL);
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(alto, ancho, java.awt.Image.SCALE_SMOOTH);

		return new ImageIcon(newimg);
	}

	public static ImageIcon loadIcon(String nombrePicture, Integer alto, Integer ancho) {
		Image img = new ImageIcon(nombrePicture).getImage();
		Image newimg = img.getScaledInstance(alto, ancho, java.awt.Image.SCALE_SMOOTH);

		return new ImageIcon(newimg);
	}

	public static Integer getHash(String texto) {
		int hash = 7;
		for (int i = 0; i < texto.length(); i++) {
			hash = hash * 31 + texto.charAt(i);
		}

		return hash;
	}

	public static String nullIfEmty(String valor) {
		if ((valor == null) || valor.equals("")) {
			return null;
		} else {
			return valor;
		}
	}

	public static boolean isPrintableChar(char c) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		return (!Character.isISOControl(c)) && c != KeyEvent.CHAR_UNDEFINED && block != null
				&& block != Character.UnicodeBlock.SPECIALS;
	}

	public static String agregarComillas(String valor) {
		return "\"" + valor.trim() + "\"";
	}

	public static String boolean2String(boolean valor) {
		return valor ? "1" : "0";
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static String emtyIfNull(String valor) {
		if (valor == null) {
			return "";
		} else {
			return valor;
		}
	}

	public static String emtyIfNull(Integer valor) {
		if (valor == null) {
			return "";
		} else {
			return String.valueOf(valor);
		}
	}

	public static String emtyIfNull(Short valor) {
		if (valor == null) {
			return "";
		} else {
			return String.valueOf(valor);
		}
	}

	public static String emtyIfNull(Long valor) {
		if (valor == null) {
			return "";
		} else {
			return String.valueOf(valor);
		}
	}

	
	public static Double String2Double(String valor, String separadorMiles) {
		// Si el signo negativo viene al final lo paso a la primera poscion
		if (valor.substring(valor.length() - 1).equals("-")) {
			valor = "-" + valor.substring(0, valor.length() - 1).trim();
		}
		valor = valor.replace(separadorMiles, ""); // Saco el separador de miles del sistema
		valor = valor.replace(",", "."); // Si tengo una coma la paso a punto que es el separador que usa el Double
		return Double.valueOf(valor);
	}

	public static String double2String(Double valor, String separadorMiles, String separadorDecimal) {
		String string = Double.toString(valor);
		String[] b = string.split( "\\.");
		int cantDecimales = 2;
		if(b.length > 1) {
			cantDecimales = b[1].length();
		}
		
		String retorno = String.format("%." + cantDecimales + "f", valor);
		retorno = retorno.replace(separadorMiles, separadorDecimal); // El String.format("%f", valor) solo agrega el
																		// separador decimal, me aseguro que
																		// sea el que usa el sismeta
		return retorno;
	}

	public static boolean existeArchivo(String nombreArchivo) {
		File archProp = new File(nombreArchivo);
		return archProp.exists();
	}

	// Method for getting the maximum value
	public static <T extends Comparable<T>> T maximo(T[] inputArray) {
		T max = inputArray[0];
		for (int i = 1; i < inputArray.length; i++) {
			if (inputArray[i].compareTo(max) > 0) {
				max = inputArray[i]; // y is the largest so far
			}
		}
		return max; // returns the largest object
	}

	// Method for getting the maximum value
	public static <T extends Comparable<T>> T minimo(T[] inputArray) {
		T min = inputArray[0];
		for (int i = 1; i < inputArray.length; i++) {
			if (inputArray[i].compareTo(min) < 0) {
				min = inputArray[i]; // y is the largest so far
			}
		}
		return min; // returns the largest object
	}

}
