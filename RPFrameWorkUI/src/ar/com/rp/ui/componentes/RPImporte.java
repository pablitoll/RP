//TODO CAMBIO DECIMAL
package ar.com.rp.ui.componentes;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;

public class RPImporte extends JTextField {

	/**max
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cantidadMaxDecimales = 2;

	public Integer getCantidadMaxDecimales() {
		return cantidadMaxDecimales;
	}

	public void setCantidadMaxDecimales(Integer cantidadMaxDecimales) {
		this.cantidadMaxDecimales = cantidadMaxDecimales;
	}

	public RPImporte() {
		super();
		setHorizontalAlignment(JTextField.RIGHT);
		setDocument(new PlainDocument() {
			private static final long serialVersionUID = 1L;

			public void insertString(int off, String str, AttributeSet attr) throws BadLocationException {

				String valorOrginal = getText(0, getLength());
				String valorConCaracterInsertado = valorOrginal.substring(0, off) + str + valorOrginal.substring(off, valorOrginal.length()); // Inserto el numero en la posicion
																																				// donde
																																				// estoy parado
				String valoraValidar = limpiarString(valorConCaracterInsertado);

				if (CommonUtils.isOnlyNumeric(valoraValidar) && (valoraValidar.length() < 12)) { // 9 + 2 Es por
																									// restrcciones de
																									// la coboleta
					valoraValidar = "000000000000" + valoraValidar;
					valorConCaracterInsertado = valoraValidar.substring(0, valoraValidar.length() - cantidadMaxDecimales) + Common.getGeneralSettings().getSeparadorDecimal()
							+ valoraValidar.substring(valoraValidar.length() - cantidadMaxDecimales); // le pongo la
																										// coma donde
																										// corresponde,
																										// de esta
																										// manera estoy
																										// haciendo el
																										// corrimiento
																										// de la misma
					String nuevoValor = formatearImporteInterno(valorConCaracterInsertado);

					super.remove(0, getLength());
					super.insertString(0, nuevoValor, attr);

					ponerCursorEnPosicion(valorOrginal, nuevoValor, off);
				}
			}

			public void remove(int offs, int len) throws BadLocationException {
				String valorOrginal = getText(0, getLength());

				// Por si esto parado a la
				// derecha del simbolo
				if (valorOrginal.substring(offs, offs + len).equals(Common.getGeneralSettings().getSeparadorDecimal())
						|| valorOrginal.substring(offs, offs + len).equals(Common.getGeneralSettings().getSeparadorMiles())) {
					offs--;
				}

				super.remove(offs, len);
				String valor = getText(0, getLength());

				valor = valor.replace(Common.getGeneralSettings().getSeparadorDecimal(), "").replace(Common.getGeneralSettings().getSeparadorMiles(), "").trim();
				if (!valor.equals("")) {
					if (cantidadMaxDecimales > 0) {

						if (valor.length() <= cantidadMaxDecimales) {
							valor = CommonUtils.strRigth("0000000000000" + valor, cantidadMaxDecimales + 1);
						}

						valor = valor.substring(0, valor.length() - cantidadMaxDecimales) + Common.getGeneralSettings().getSeparadorDecimal()
								+ valor.substring(valor.length() - cantidadMaxDecimales);

					}

					valor = formatearImporteInterno(valor);
				}
				super.remove(0, getLength());
				super.insertString(0, valor, null);
				ponerCursorEnPosicion(valorOrginal, valor, offs + 1);
			}

		});
	}

	private void ponerCursorEnPosicion(String valorOrginal, String nuevoValor, int off) {
		if (valorOrginal.length() == nuevoValor.length()) { // Si no cambia el largo, uso el offset original, sino lo
															// recalculo
			setSelectionStart(off);
			setSelectionEnd(off);
		} else {
			if (valorOrginal.length() == 0) {
				setSelectionStart(nuevoValor.length());
				setSelectionEnd(nuevoValor.length());
			} else {
				int cantNumerosOrg = limpiarString(valorOrginal).length();
				int cantNumerosNuevo = limpiarString(nuevoValor).length();

				int cantSimboloOrg = contarSimbolo(valorOrginal);
				int cantSimboloNuevo = contarSimbolo(nuevoValor);
				setSelectionStart(off + (cantNumerosNuevo - cantNumerosOrg) + (cantSimboloNuevo - cantSimboloOrg));
				setSelectionEnd(off + (cantNumerosNuevo - cantNumerosOrg) + (cantSimboloNuevo - cantSimboloOrg));
			}
		}
	}

	private int contarSimbolo(String valor) {
		if (valor.length() == 0) {
			return 0;
		} else {
			if (!CommonUtils.isOnlyNumeric(valor.substring(0, 1))) {
				return 1 + contarSimbolo(CommonUtils.strRigth(valor, valor.length() - 1));
			} else {
				return contarSimbolo(CommonUtils.strRigth(valor, valor.length() - 1));
			}
		}
	}

	private static String limpiarString(String valor) {
		return valor.replace(Common.getGeneralSettings().getSeparadorMiles(), "").replace(Common.getGeneralSettings().getSeparadorDecimal(), "");
	}

	public static String formatearImporte(Double valor) {
		return formatearImporte(Common.double2String(valor), true);
	}

	public static String formatearImporte(String valor, boolean conSeparadorMiles) {
		return formatearImporte(valor, conSeparadorMiles, 2);
	}

	public static String formatearImporte(String valor, boolean conSeparadorMiles, int cantidadMaxDecimales) {
		return formatearImporteInterno(valor, conSeparadorMiles, cantidadMaxDecimales);
	}

	private String formatearImporteInterno(String valor) {
		return formatearImporteInterno(valor, true, cantidadMaxDecimales);
	}

	public static String formatearImporte(Double valor, Integer redondearA) {
		valor = CommonUtils.redondear(valor, redondearA);
		return formatearImporte(Common.double2String(valor), true);
	}

	/*
	 * private static String formatearImporteInterno(String valor, boolean
	 * conSeparadorMiles, boolean mostrarDecimales, boolean soloEnteros) { String
	 * parteEntrera = ""; String parteDecimal = "";
	 * 
	 * valor = limpiarString(valor).trim();
	 * 
	 * if (valor.equals("")) { return ""; } else { valor =
	 * String.valueOf(Long.valueOf(valor)); // saco los ceros a izquierda boolean
	 * isNegativo = valor.indexOf("-") > -1; valor = valor.replace("-", "");
	 * 
	 * if(soloEnteros) { parteEntrera = formatearImporteParteEntera(valor,
	 * conSeparadorMiles); } else { if (valor.length() < 3) { // Primero formateo la
	 * parte derecha de los decimales parteEntrera = "0"; parteDecimal =
	 * CommonUtils.strRigth("00" + valor, 2); } else { parteEntrera =
	 * formatearImporteParteEntera(CommonUtils.strLeft(valor, valor.length() - 2),
	 * conSeparadorMiles); parteDecimal = CommonUtils.strRigth(valor, 2); } }
	 * 
	 * if (mostrarDecimales && !soloEnteros) { return (isNegativo ? "-" : "") +
	 * parteEntrera + Common.getGeneralSettings().getSeparadorDecimal() +
	 * parteDecimal; } else { return (isNegativo ? "-" : "") + parteEntrera; } } }
	 */

	private static String sacarCeroADerecha(String numero) {
		numero = numero.trim();
		if ((numero.length() > 0) && (numero.substring(numero.length() - 1).equals("0"))) {
			return sacarCeroADerecha(numero.substring(0, numero.length() - 1));
		}
		return numero;
	}

	// private static String formatearImporteInterno(String valor, boolean
	// conSeparadorMiles, boolean mostrarDecimales) {
	// valor = valor.trim();
	// if (valor.equals("")) {
	// return "";
	// } else {
	// int cantidadDecimales = 2; // por default
	// String splitValor[] =
	// valor.split(Common.getGeneralSettings().getSeparadorDecimal());
	//
	// if (splitValor.length > 1) {
	// String parteDecimal = sacarCeroADerecha(splitValor[1]);
	// if (parteDecimal.length() > cantidadDecimales) {
	// cantidadDecimales = parteDecimal.length();
	// }
	// valor = String.valueOf(Long.valueOf(splitValor[0])) +
	// Common.getGeneralSettings().getSeparadorDecimal()
	// + parteDecimal; // saco los ceros a izquierda y derecha
	// } else {
	// valor = String.valueOf(Long.valueOf(valor)); // saco los ceros a izquierda
	// }
	//
	// String parteEntrera = "";
	// String parteDecimal = "";
	// valor = limpiarString(valor).trim();
	//
	// boolean isNegativo = valor.indexOf("-") > -1;
	// valor = valor.replace("-", "");
	//
	// if (!mostrarDecimales) {
	// parteEntrera = formatearImporteParteEntera(valor, conSeparadorMiles);
	// return (isNegativo ? "-" : "") + parteEntrera;
	//
	// } else {
	// if (valor.length() < (cantidadDecimales + 1)) { // Primero formateo la parte
	// derecha de los decimales
	// parteEntrera = "0";
	// parteDecimal = CommonUtils.strRigth("00000000000" + valor,
	// cantidadDecimales);
	// } else {
	// parteEntrera = formatearImporteParteEntera(
	// CommonUtils.strLeft(valor, valor.length() - cantidadDecimales),
	// conSeparadorMiles);
	// parteDecimal = CommonUtils.strRigth(valor, cantidadDecimales);
	// }
	// return (isNegativo ? "-" : "") + parteEntrera +
	// Common.getGeneralSettings().getSeparadorDecimal()
	// + parteDecimal;
	// }
	// }
	// }
	//TODO DECIMAL
	private static String formatearImporteInterno(String valor, boolean conSeparadorMiles, int cantidadMaxDecimales) {
		boolean mostrarDecimales = cantidadMaxDecimales > 0;
		valor = valor.trim();
		if (valor.equals("")) {
			return "";
		} else {
			int cantidadDecimales = cantidadMaxDecimales; // por default
			String splitValor[] = valor.split("\\" + Common.getGeneralSettings().getSeparadorDecimal());

			splitValor[0] = String.valueOf(Long.valueOf(splitValor[0].replace(Common.getGeneralSettings().getSeparadorMiles(), ""))); // saco los ceros a izquierda
			if (splitValor.length > 1) {
				splitValor[1] = splitValor[1].trim();

				String parteDecimal = sacarCeroADerecha(splitValor[1]);
				if (parteDecimal.length() > cantidadDecimales) {
					cantidadDecimales = parteDecimal.length();
				}
			} else {
				String tmp = splitValor[0];
				splitValor = new String[2];
				splitValor[0] = tmp;
				splitValor[1] = "000000000000".substring(0, cantidadMaxDecimales);
			}

			boolean isNegativo = splitValor[0].indexOf("-") > -1;
			splitValor[0] = splitValor[0].replace("-", "");

			// Formateamos la parte entera
			splitValor[0] = (isNegativo ? "-" : "") + formatearImporteParteEntera(splitValor[0], conSeparadorMiles);

			if (!mostrarDecimales) {

				return splitValor[0];

			} else {
				// Formateamos la parte decimal
				splitValor[1] = CommonUtils.strLeft(splitValor[1] + "00000000000", cantidadDecimales);
				return splitValor[0] + Common.getGeneralSettings().getSeparadorDecimal() + splitValor[1];
			}
		}
	}

	private static String formatearImporteParteEntera(String valor, boolean conSeparadorMiles) {
		if (valor.length() < 4) { // Grupos de a tres para la parte entera
			return valor;
		} else {
			return formatearImporteParteEntera(CommonUtils.strLeft(valor, valor.length() - 3), conSeparadorMiles)
					+ (conSeparadorMiles ? Common.getGeneralSettings().getSeparadorMiles() : "") + CommonUtils.strRigth(valor, 3);
		}

	}

	public void limpiar() {
		setText("");
	}

	public boolean isEmpty() {
		return getText().equals("");
	}

	public Double getImporte() throws Exception {
		String strImporte = getText().trim();
		if (!strImporte.equals("")) {
			if (cantidadMaxDecimales == 0) { // Le agrego los decimales para que no explote la converison
				strImporte = strImporte + Common.getGeneralSettings().getSeparadorDecimal() + "00";
			}
			return Common.String2Double(strImporte);
		} else {
			return 0.0;
		}
	}

	public void setImporte(Double importe) {
		setText(Common.double2String(importe));
	}

	public Integer getEnteros() throws Exception {
		return getImporte().intValue();
	}

}
