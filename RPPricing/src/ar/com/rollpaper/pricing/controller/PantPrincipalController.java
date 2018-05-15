package ar.com.rollpaper.pricing.controller;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.LogBusiness;
import ar.com.rollpaper.pricing.model.PantPrincipalModel;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.PantPrincipalView;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;
import ar.com.rp.ui.pantalla.VentanaCalculadora;

public class PantPrincipalController extends BasePantallaPrincipal<PantPrincipalView, PantPrincipalModel> {

	private VentanaCalculadora calculadora = null;
	private static PantPrincipalController pantallaPrincipal = null;

	public PantPrincipalController(PantPrincipalView view, PantPrincipalModel model) throws Exception {

		super(view, model, null);

		pantallaPrincipal = this;
	}

	@Override
	public void iniciar() throws Exception {

		//refreshBarraDeEstadoYBotonos();

		// Agrego los botones de acceso rapido de codigo
		//agregarBotones();
		cargarPermisos();

		// Lanzo panalla principal
		super.iniciar();
	}

//	private void agregarBotones() {
//		int valorF1 = KeyEvent.VK_F1;
//		int i = 0;
//		for (codigosAccesoRapido func : codigosAccesoRapido.values()) {
//			JButtonCSC btn = new JButtonCSC(func.getDescripcion(), "Acceso Directo a cod. " + func.getCodigo());
//			btn.setFont(Common.getStandarFont(10));
//			btn.setFocusable(false);
//			btn.setMnemonic(valorF1 + i);
//			btn.setToolTipText(func.getDescripcionCompleta());
//			btn.setVerticalTextPosition(SwingConstants.BOTTOM);
//			btn.setHorizontalTextPosition(SwingConstants.CENTER);
//			btn.setActionCommand(ConstantesC.Acciones.BUSCAR_COD_PARTICULAR.toString() + func.getCodigo() + ";"
//					+ func.getCodigoOffline());
//			btn.setIcon(Common.loadIcon(func.getIcono()));
//			btn.addActionListener(this);
//			getView().toolBarSuperior.add(btn);
//			i++;
//		}
//	}

	@Override
	public void ejecuarAccion(String accion) {

		if (accion.equals(ConstantesRP.Acciones.CALCULADORA.toString())) {
			if (calculadora == null) {
				calculadora = new VentanaCalculadora(ConstantesRP.IMG_ICONO_APP, getView());
			}
			calculadora.visualizar(getView().desktopPane);
		}

		if (accion.equals(ConstantesRP.Acciones.SALIR.toString())) {
			salir();
		}

	}

	@Override
	protected void salir() {
		try {

			int confirm = Dialog.showConfirmDialog("¿Esta Seguro que quiere salir de la aplicacion?",
					"Confirmacion de Salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
					null);

			if (confirm == JOptionPane.YES_OPTION) {

				LogBusiness.forzarEscrituraLogs();

				System.exit(0);
			}
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al salir del sistema");
			System.exit(0);
		}
	}

	@Override
	protected String getNombrePantalla() {
		return "Pantalla Principal";
	}

	public static void setCursorOcupado() {
		if (SwingUtilities.isEventDispatchThread()) {
			if (pantallaPrincipal != null) {
				pantallaPrincipal.cursorOcupado();
			}
		}
	}

	public static void setRestoreCursor() {
		if (SwingUtilities.isEventDispatchThread()) {
			if (pantallaPrincipal != null) {
				pantallaPrincipal.restoreCursor();
			}
		}
	}

	public static PantPrincipalController getPantallaPrincipal() {
		return pantallaPrincipal;
	}

	public void refrescar() {
		getView().revalidate();
		getView().repaint();
	}

}