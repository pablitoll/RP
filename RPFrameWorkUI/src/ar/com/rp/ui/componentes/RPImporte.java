package ar.com.rp.ui.componentes;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;

public class RPImporte extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean soloEnteros = false;
	private Integer cantDecimales = 2;

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
					String nuevoValor = formatearImporteInterno(valorConCaracterInsertado);

					super.remove(0, getLength());
					super.insertString(0, nuevoValor, attr);

					ponerCursorEnPosicion(valorOrginal, nuevoValor, off);
				}
			}

			public void remove(int offs, int len) throws BadLocationException {
				String valorOrginal = getText(0, getLength());

				if (valorOrginal.substring(offs, offs + len).equals(Common.getGeneralSettings().getSeparadorDecimal())) { // Por si esto parado a la
																															// izquierda del simbolo
					offs--;
				}

				super.remove(offs, len);

				String valor = getText(0, getLength());
				valor = formatearImporteInterno(valor);
				String nuevoValor = valor;

				super.remove(0, getLength());
				super.insertString(0, valor, null);

				ponerCursorEnPosicion(valorOrginal, nuevoValor, offs + 1);
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

	private String limpiarString(String valor) {
		return valor.replace(Common.getGeneralSettings().getSeparadorMiles(), "").replace(Common.getGeneralSettings().getSeparadorDecimal(), "");
	}

	public String formatearImporte(Double valor) {
		return formatearImporte(Common.double2String(valor), true);
	}

	public String formatearImporte(String valor, boolean conSeparadorMiles) {
		return formatearImporte(valor, conSeparadorMiles, true);
	}

	public String formatearImporte(String valor, boolean conSeparadorMiles, boolean mostrarDecimales) {
		return formatearImporteInterno(valor, conSeparadorMiles, mostrarDecimales, false);
	}

	private String formatearImporteInterno(String valor) {
		return formatearImporteInterno(valor, true, true, isSoloEnteros());
	}

	private String formatearImporteInterno(String valor, boolean conSeparadorMiles, boolean mostrarDecimales, boolean soloEnteros) {
		String parteEntrera = "";
		String parteDecimal = "";

		valor = limpiarString(valor).trim();

		if (valor.equals("")) {
			return "";
		} else {
			valor = String.valueOf(Long.valueOf(valor)); // saco los ceros a izquierda
			boolean isNegativo = valor.indexOf("-") > -1;
			valor = valor.replace("-", "");

			if (soloEnteros) {
				parteEntrera = formatearImporteParteEntera(valor, conSeparadorMiles);
			} else {
				if (valor.length() < (cantDecimales + 1)) { // Primero formateo la parte derecha de los decimales
					parteEntrera = "0";
					parteDecimal = CommonUtils.strRigth("00000000" + valor, cantDecimales);
				} else {
					parteEntrera = formatearImporteParteEntera(CommonUtils.strLeft(valor, valor.length() - cantDecimales), conSeparadorMiles);
					parteDecimal = CommonUtils.strRigth(valor, cantDecimales);
				}
			}

			if (mostrarDecimales && !soloEnteros) {
				return (isNegativo ? "-" : "") + parteEntrera + Common.getGeneralSettings().getSeparadorDecimal() + parteDecimal;
			} else {
				return (isNegativo ? "-" : "") + parteEntrera;
			}
		}
	}

	private String formatearImporteParteEntera(String valor, boolean conSeparadorMiles) {
		if (valor.length() < (cantDecimales + 2)) { // Grupos de a tres para la parte entera
			return valor;
		} else {
			return formatearImporteParteEntera(CommonUtils.strLeft(valor, valor.length() - (cantDecimales + 1)), conSeparadorMiles)
					+ (conSeparadorMiles ? Common.getGeneralSettings().getSeparadorMiles() : "") + CommonUtils.strRigth(valor, (cantDecimales + 1));
		}
	}

	public void limpiar() {
		setText("");
	}

	public boolean isEmpty() {
		return getText().equals("");
	}

	public Double getImporte() {
		String strImporte = getText().trim();
		if (!strImporte.equals("")) {
			if (soloEnteros) { // Le agrego los decimales para que no explote la converison
				strImporte = strImporte + Common.getGeneralSettings().getSeparadorDecimal() + "00";
			}
			return Common.String2Double(strImporte);
		} else {
			return 0.0;
		}
	}

	public void setImporte(Double importe) {
		// Obtengo la parte decimal para saber si estan todos los decimales, caso
		// contrario le agego 0
		String strImporte = Common.double2String(importe);
		String decimal = strImporte.split(Common.getGeneralSettings().getSeparadorDecimal())[1];

		for (int i = 0; i < cantDecimales - decimal.length(); i++) {
			strImporte += "0";
		}

		setText(strImporte);
	}

	public Integer getEnteros() {
		return getImporte().intValue();
	}

	public Integer getCantDecimales() {
		return cantDecimales;
	}

	public void setCantDecimales(Integer cantDecimales) {
		this.cantDecimales = cantDecimales;
	}

	public Boolean isSoloEnteros() {
		return soloEnteros;
	}

	public void setSoloEnteros(Boolean soloEnteros) {
		this.soloEnteros = soloEnteros;
	}

}
