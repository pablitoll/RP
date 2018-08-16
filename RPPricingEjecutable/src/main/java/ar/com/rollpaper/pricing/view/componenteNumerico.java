//TODO ULI FALTA, de la IDE
/*
* Terminar este componente

* poner el icono en espera

*en la pantalla de gestion de precios >descuentos y precios especifico  al dar de alta un articulo que esta en una lista
falla la validacion del descuento (no deja cargar y no deja eliminar el porcentaje) entra en loop

* en Descuento por familia > en alta de familia > solo mostrar familias que esten en la lista seleccionada 

* en el reporte ver de generar un reporte de todos los precios y otro de solo los precios customizados.

*/

package ar.com.rollpaper.pricing.view;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import ar.com.rp.ui.common.Common;

public class componenteNumerico extends JFormattedTextField {

	/**
	 * 
	 */
	private int cantEnteros = 2;
	private int cantDecimales = 0;

	private static final long serialVersionUID = 1L;

	public componenteNumerico() {
		setFont(Common.getStandarFont());
		setHorizontalAlignment(JLabel.RIGHT);
		setInputVerifier(new InputVerifier() {

			@Override
			public boolean verify(JComponent arg0) {
				JFormattedTextField tf = (JFormattedTextField) arg0;
				return numeroValido(tf.getText());
			}
		});

		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (!getText().equals("") && numeroValido(getText()) && (cantDecimales > 0)) {
					String decimales = getDecimales();
					String punto = "";
					if (Integer.valueOf(decimales) == 0) {
						punto = Common.getGeneralSettings().getSeparadorDecimal();
					}

					setText((getText() + punto + "00000000").substring(0, cantDecimales + cantEnteros));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (!getText().equals("")) {
					String decimales = getDecimales();
//					if (decimales.length()) { // le saco los decimales
						setText(getText().substring(0, cantEnteros));
//					}
				}
			}
		});

		setDocument(new PlainDocument() {

			private static final long serialVersionUID = 1L;

			public void insertString(int off, String str, AttributeSet attr) throws BadLocationException {

				String valorOrginal = getText(0, getLength());
				String numeroConCaracteres = valorOrginal.substring(0, off) + str + valorOrginal.substring(off, valorOrginal.length()); // inserto el instrin

				if (numeroValido(numeroConCaracteres)) {
					super.remove(0, getLength());
					super.insertString(0, numeroConCaracteres, attr);
				}
			}
		});

	}

	protected String getEnteros() {
		if (!getText().equals("")) {
			Double d = Common.String2Double(getText()); // Que sea un double valido
			String aux = Common.double2String(d);
			String valor[] = aux.split(Common.getGeneralSettings().getSeparadorDecimal());
			return valor[0].trim();
		}
		return "";
	}

	protected String getDecimales() {
		if (!getText().equals("")) {
			Double d = Common.String2Double(getText()); // Que sea un double valido
			String aux = Common.double2String(d);
			String valor[] = aux.split(Common.getGeneralSettings().getSeparadorDecimal());
			if (valor.length > 1) {
				if (Integer.valueOf(valor[1].trim()) == 0) {
					return "";
				}

				return valor[1].trim();
			}
		}
		return "";

	}

	protected boolean numeroValido(String numero) {
		try {
			if (!numero.equals("")) {

				Double.valueOf(numero); // Que sea un double valido
				if (getEnteros().length() > cantEnteros) {
					return false;
				}

				if (getDecimales().length() > cantDecimales) {
					return false;
				}

			}
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public int getCantEnteros() {
		return cantEnteros;
	}

	public void setCantEnteros(int cantEnteros) {
		this.cantEnteros = cantEnteros;
	}

	public int getCantDecimales() {
		return cantDecimales;
	}

	public void setCantDecimales(int cantDecimales) {
		this.cantDecimales = cantDecimales;
	}
}
