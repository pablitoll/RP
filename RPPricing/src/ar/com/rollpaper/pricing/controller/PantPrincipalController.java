package ar.com.rollpaper.pricing.controller;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.hibernate.Hibernate;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.LogBusiness;
import ar.com.rollpaper.pricing.data.HibernateUtil;
import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.model.Consulta1Model;
import ar.com.rollpaper.pricing.model.PantPrincipalModel;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rollpaper.pricing.view.Consulta1View;
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
		
		getView().lblUsr.setText(System.getProperty("user.name"));
	}


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
		
		if (accion.equals(ConstantesRP.Acciones.CONSULTA1.toString())) {
			if (!cmGestordeVentanas.isAlreadyCreated("consulta1")) {
				// Creo los controladores de VerLogOperacionesDiaria
				try {
					Consulta1View vista = new Consulta1View();
					Consulta1Model modelo = new Consulta1Model();
					Consulta1Controller consulta1 = new Consulta1Controller(this, vista, modelo);
					cmGestordeVentanas.add(consulta1, "consulta1");
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al crear pantalla de consulta 1");
				}
			}
			try {
				cmGestordeVentanas.findOrCreated("consulta1");
			} catch (Exception e) {
				ManejoDeError.showError(e, "No se puede acceder a la pantalla de consulta 1");
			}
		}
		
		if (accion.equals(ConstantesRP.Acciones.CARGA_PRECIO_CLIENTE.toString())) {
			if (!cmGestordeVentanas.isAlreadyCreated("CargaPrecioController")) {
				// Creo los controladores de VerLogOperacionesDiaria
				try {
					CargaPrecioView vista = new CargaPrecioView();
					CargaPrecioModel modelo = new CargaPrecioModel();
					CargaPrecioController controlador = new CargaPrecioController(this, vista, modelo);
					cmGestordeVentanas.add(controlador, "CargaPrecioController");
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al crear pantalla de carga de precio por cliente");
				}
			}
			try {
				cmGestordeVentanas.findOrCreated("CargaPrecioController");
			} catch (Exception e) {
				ManejoDeError.showError(e, "No se puede acceder a la pantalla de carga de precio por clietne");
			}
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
				HibernateUtil.shutdown();
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