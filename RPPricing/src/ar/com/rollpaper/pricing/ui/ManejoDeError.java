package ar.com.rollpaper.pricing.ui;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ar.com.rollpaper.pricing.business.LogBusiness;
import ar.com.rollpaper.pricing.controller.PantPrincipalController;
import ar.com.rp.ui.error.ErrorManager;

public class ManejoDeError {


	public static void showError(Exception error, String titulo) {
		showError(error, titulo, "");
	}

	public static void showError(Exception error, String titulo, String mensaje) {
		error.printStackTrace();

		boolean mostrarError = true;

		
		if (mostrarError && SwingUtilities.isEventDispatchThread()) {
			try {
				ErrorManager.showError(PantPrincipalController.getPantallaPrincipal(), titulo, mensaje, error);
			} catch (Exception e) {
				e.printStackTrace();
				Dialog.showMessageDialog("Hubo un error al visualizar el msg de error.\nError original : " + error.getMessage(), "Error al visualizar mensage de Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// guardo en el log el error.
		try {
			LogBusiness.logearError(error);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			LogBusiness.forzarEscrituraLogs();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void showErrorIfExist(ReturnOperacionSAFE msgRetorno, String titulo, String detalle, Exception errorInterno) {
		if (msgRetorno.getCodigoRetorno() != 0) {
			if (msgRetorno.getCodigoRetorno().equals(CodRespuestaSAFE.DEFAULT.getCodError())) { // si es un errro mio interno, muestro el trace
				if (errorInterno != null) {
					ManejoDeError.showError(errorInterno, titulo, detalle);
				} else {
					ManejoDeError.showError(new Exception(msgRetorno.getDescripcionError()), titulo, detalle);
				}
			} else {
				if (msgRetorno.getCodigoRetorno().equals(CodRespuestaSAFE.TIMEOUT.getCodError())) {
					Dialog.showMessageDialog(null, "TIME OUT. La transaccion no fue confirmada.", titulo, JOptionPane.ERROR_MESSAGE);
				} else {
					Dialog.showMessageDialog(null, detalle, titulo, JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

}
