/*
* poner el icono en espera

* en Descuento por familia > en alta de familia > solo mostrar familias que esten en la lista seleccionada 

*/

package ar.com.rollpaper.pricing.view;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.alee.laf.text.WebTextField;

import ar.com.rollpaper.pricing.business.CommonPricing;
import ar.com.rp.ui.common.Common;

public class componenteNumerico extends WebTextField {

	/**
	 * 
	 */
	private int cantEnteros = 2;
	private int cantDecimales = 2;

	private static final long serialVersionUID = 1L;

	public componenteNumerico() {
		setFont(Common.getStandarFont());
		setHorizontalAlignment(JLabel.RIGHT);
		setInputVerifier(new InputVerifier() {

			@Override
			public boolean verify(JComponent arg0) {
				WebTextField tf = (WebTextField) arg0;
				return numeroValido(tf.getText());
			}
		});

		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (!getText().equals("") && numeroValido(getText()) && (cantDecimales > 0)) {
					String decimales = getDecimales(getText());
					String entero = getEnteros(getText());
					setText(CommonPricing.formatearImporte(entero + Common.getGeneralSettings().getSeparadorDecimal() + (decimales + "00000").substring(0, cantDecimales)));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (!getText().equals("")) {
					String decimales = getDecimales(getText());
					String entero = getEnteros(getText());

					String parteDecimal = "";

					if (!decimales.equals("")) {
						parteDecimal = "." + String.valueOf(Integer.valueOf(decimales)); // saco los ceros
					}
					setText(entero + parteDecimal);
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

	protected String getEnteros(String numero) {
		if (!numero.equals("")) {
			Double d = Common.String2Double(numero); // Que sea un double valido
			String aux = Common.double2String(d);
			String valor[] = aux.split(Common.getGeneralSettings().getSeparadorDecimal());
			return valor[0].trim();
		}
		return "";
	}

	protected String getDecimales(String numero) {
		if (!numero.equals("")) {
			Double d = Common.String2Double(numero); // Que sea un double valido
			String aux = Common.double2String(d);
			String valor[] = aux.split(Common.getGeneralSettings().getSeparadorDecimal());
			if (valor.length > 1) {
				if (Long.valueOf(valor[1].trim()) == 0) {
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

				// Double.valueOf(numero); // Que sea un double valido
				Common.String2Double(numero);

				if (getEnteros(numero).length() > cantEnteros) {
					return false;
				}
				if (getDecimales(numero).length() > cantDecimales) {
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

	public void setNumero(double valor) {
		setText(CommonPricing.formatearImporte(valor));
	}
}
