package ar.com.rollpaper.pricing.controller;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ar.com.rollpaper.pricing.business.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.LogBusiness;
import ar.com.rollpaper.pricing.data.HibernateUtil;
import ar.com.rollpaper.pricing.model.CargaClienteEsclavoModel;
import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.model.ListaPrecioClienteModel;
import ar.com.rollpaper.pricing.model.PantPrincipalModel;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rollpaper.pricing.view.ListaPrecioClienteView;
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
		// Agrego los botones de acceso rapido de codigo
		cargarPermisos();

		// Lanzo panalla principal
		super.iniciar();

		refrescarBarra();
	}

	private void refrescarBarra() throws Exception {
		getView().lblUsr.setText(System.getProperty("user.name"));
		getView().lblDB.setText(ArchivoDePropiedadesBusiness.getConecctionString());
	}

	@Override
	public void ejecutarAccion(String accion) {

		if (accion.equals(ConstantesRP.Acciones.CALCULADORA.toString())) {
			if (calculadora == null) {
				calculadora = new VentanaCalculadora(ConstantesRP.IMG_ICONO_APP, getView());
			}
			calculadora.visualizar(getView().desktopPane);
		}

		if (accion.equals(ConstantesRP.Acciones.SALIR.toString())) {
			salir();
		}

		if (accion.equals(ConstantesRP.Acciones.CAMBIAR_DB.toString())) {
			try {
				ArchivoDePropiedadesBusiness.recargar();
				HibernateUtil.reConectar();
				
				refrescarBarra();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al refrescar DB");
			}
		}

		if (accion.equals(ConstantesRP.Acciones.GENERAR_PRECIOS.toString())) {
			procesoPrecios();
		}

		if (accion.equals(ConstantesRP.Acciones.LISTA_PRECIO_X_CLIENTE.toString())) {
			if (!cmGestordeVentanas.isAlreadyCreated("consulta1")) {
				try {
					ListaPrecioClienteView vista = new ListaPrecioClienteView();
					ListaPrecioClienteModel modelo = new ListaPrecioClienteModel();
					ListaPrecioClienteController consulta1 = new ListaPrecioClienteController(this, vista, modelo);
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

		if (accion.equals(ConstantesRP.Acciones.CARGA_CLIENTE_ESCLAVO.toString())) {
			if (!cmGestordeVentanas.isAlreadyCreated("CargaClienteEsclavoController")) {
				// Creo los controladores de VerLogOperacionesDiaria
				try {
					CargaClienteEsclavoView vista = new CargaClienteEsclavoView();
					CargaClienteEsclavoModel modelo = new CargaClienteEsclavoModel();
					CargaClienteEsclavoController controlador = new CargaClienteEsclavoController(this, vista, modelo);
					cmGestordeVentanas.add(controlador, "CargaClienteEsclavoController");
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al crear pantalla de carga de precio por cliente");
				}
			}
			try {
				cmGestordeVentanas.findOrCreated("CargaClienteEsclavoController");
			} catch (Exception e) {
				ManejoDeError.showError(e, "No se puede acceder a la pantalla de carga de precio por clietne");
			}
		}

	}

	private void procesoPrecios() {
		// TODO Afalta procesoPrecios() {

	}

	@Override
	protected void salir() {
		try {

			int confirm = Dialog.showConfirmDialog("¿Esta Seguro que quiere salir de la aplicacion?", "Confirmacion de Salida", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);

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