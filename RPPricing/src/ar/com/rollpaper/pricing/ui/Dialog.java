package ar.com.rollpaper.pricing.ui;

import java.awt.Image;

import javax.swing.Icon;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.controller.PantPrincipalController;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.pantalla.DialogMessage;

public class Dialog {

	private static Image iconoAplicacion = null;

	public static void showMessageDialog(String message) {
		try {
			DialogMessage.showMessageDialog(message, getIconoAplicacion(), null,
					PantPrincipalController.getPantallaPrincipal());
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al mostrar dialog");
		}
	}

	public static void showMessageDialog(Object message, String title, int messageType) {
		try {
			DialogMessage.showMessageDialog(message, title, messageType, getIconoAplicacion(), null,
					PantPrincipalController.getPantallaPrincipal());
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al mostrar dialog");
		}
	}

	public static void showMessageDialog(Object dummy, Object message, String title, int messageType) {
		try {
			DialogMessage.showMessageDialog(message, title, messageType, getIconoAplicacion(), null,
					PantPrincipalController.getPantallaPrincipal());
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al mostrar dialog");
		}
	}

	public static Integer showConfirmDialog(Object message, String title, int optionType, int messageType, Icon icon,
			Object[] options, Object initialValue) {
		try {
			return DialogMessage.showConfirmDialog(message, title, optionType, messageType, icon, options, initialValue,
					getIconoAplicacion(), null, PantPrincipalController.getPantallaPrincipal());
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al mostrar dialog");
			return 0;
		}
	}

	public static Object showConfirmDialogObject(Object message, String title, int optionType, int messageType,
			Icon icon, Object[] options, Object initialValue) {
		try {
			return DialogMessage.showConfirmDialogObject(message, title, optionType, messageType, icon, options,
					initialValue, getIconoAplicacion(), null, PantPrincipalController.getPantallaPrincipal());
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al mostrar dialog");
			return 0;
		}
	}

	private static Image getIconoAplicacion() {
		if (iconoAplicacion == null) {
			iconoAplicacion = CommonUtils.loadImage(ConstantesRP.IMG_ICONO_APP, 80, 80);
		}

		return iconoAplicacion;
	}
}
