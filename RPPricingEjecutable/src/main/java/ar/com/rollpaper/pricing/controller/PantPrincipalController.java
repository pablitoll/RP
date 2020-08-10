package ar.com.rollpaper.pricing.controller;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.business.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.LogBusiness;
import ar.com.rollpaper.pricing.data.HibernateUtil;
import ar.com.rollpaper.pricing.model.BusquedaVencidoModel;
import ar.com.rollpaper.pricing.model.CargaClienteEsclavoModel;
import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.model.ListaPrecioClienteModel;
import ar.com.rollpaper.pricing.model.ListaPrecioXListaModel;
import ar.com.rollpaper.pricing.model.PantPrincipalModel;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.BusquedaVencidoView;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rollpaper.pricing.view.ListaPrecioClienteView;
import ar.com.rollpaper.pricing.view.ListaPrecioXListaView;
import ar.com.rollpaper.pricing.view.PantPrincipalView;
import ar.com.rollpaper.pricing.workers.BarraDeProgreso;
import ar.com.rollpaper.pricing.workers.WorkerProcesar;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;
import ar.com.rp.ui.pantalla.VentanaCalculadora;

public class PantPrincipalController extends BasePantallaPrincipal<PantPrincipalView, PantPrincipalModel> {

	private static final String VERSION_INFO = "Versión: %s.\nFecha de Liberación: %s";
	private VentanaCalculadora calculadora = null;
	private CargaPrecioController cargaPrecioControlador = null;
	private CargaClienteEsclavoController clienteEsclavoControlador = null;
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
				HibernateUtil.reConectar(ArchivoDePropiedadesBusiness.getConecctionString(),
						ArchivoDePropiedadesBusiness.getUsr(), ArchivoDePropiedadesBusiness.getPass());
				refrescarBarra();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al refrescar DB");
			}
		}

		if (accion.equals(ConstantesRP.Acciones.CAMBIAR_SEPARADOR_DECIMAL.toString())
				|| accion.equals(ConstantesRP.Acciones.CAMBIAR_SEPARADOR_MILES.toString())) {
			try {
				String msg = "Decimal";
				Boolean separadorDecimal = true;
				String valorDefault = ArchivoDePropiedadesBusiness.getSeparadorDecimales();

				if (accion.equals(ConstantesRP.Acciones.CAMBIAR_SEPARADOR_MILES.toString())) {
					separadorDecimal = false;
					msg = "de Miles";
					valorDefault = ArchivoDePropiedadesBusiness.getSeparadorMiles();
				}

				Object separadorNuevoObj = JOptionPane.showInputDialog(null,
						String.format("Ingrese el Separador %s a Utilizar", msg),
						String.format("Configuracion de Separador %s", msg), JOptionPane.QUESTION_MESSAGE, null, null,
						valorDefault);
				if (separadorNuevoObj != null) {
					String separadorNuevo = String.valueOf(separadorNuevoObj).trim();
					if (separadorNuevo.length() != 1) {
						Dialog.showMessageDialog("El separador debe ser de un solo caracter");
					} else {
						if (separadorDecimal) {
							ArchivoDePropiedadesBusiness.setSeparadorDecimales(separadorNuevo);
							Common.getGeneralSettings().setSeparadorDecimal(separadorNuevo);
						} else {
							ArchivoDePropiedadesBusiness.setSeparadorMiles(separadorNuevo);
							Common.getGeneralSettings().setSeparadorMiles(separadorNuevo);
						}
					}
				}
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al cambiar de Separador");
			}
		}

		if (accion.equals(ConstantesRP.Acciones.GENERAR_PRECIOS.toString())) {
			procesoPrecios();
		}

		if (accion.equals(ConstantesRP.Acciones.VERSION.toString())) {
			Dialog.showMessageDialog(String.format(VERSION_INFO, "1.0.12", "10/08/2020"), "Version del Sistema",
					WebOptionPane.INFORMATION_MESSAGE);
		}

		if (accion.equals(ConstantesRP.Acciones.LISTA_PRECIO_GENERALES.toString())) {
			if (!cmGestordeVentanas.isAlreadyCreated(ListaPrecioXListaController.class.getName())) {
				try {
					ListaPrecioXListaView vista = new ListaPrecioXListaView();
					ListaPrecioXListaModel modelo = new ListaPrecioXListaModel();
					ListaPrecioXListaController consulta1 = new ListaPrecioXListaController(this, vista, modelo);
					cmGestordeVentanas.add(consulta1, ListaPrecioXListaController.class.getName());
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al crear pantalla de consulta de Lista");
				}
			}
			try {
				cmGestordeVentanas.findOrCreated(ListaPrecioXListaController.class.getName());
			} catch (Exception e) {
				ManejoDeError.showError(e, "No se puede acceder a la pantalla de consulta de Lista");
			}
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

		if (accion.equals(ConstantesRP.Acciones.BUSQUEDA_VENCIDOS.toString())) {
			if (!cmGestordeVentanas.isAlreadyCreated(BusquedaVencidoController.class.getName())) {
				try {
					BusquedaVencidoView vista = new BusquedaVencidoView();
					BusquedaVencidoModel modelo = new BusquedaVencidoModel();
					BusquedaVencidoController busqueda = new BusquedaVencidoController(this, vista, modelo);
					cmGestordeVentanas.add(busqueda, BusquedaVencidoController.class.getName());
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al crear pantalla de Busqueda de Vencidos");
				}
			}
			try {
				cmGestordeVentanas.findOrCreated(BusquedaVencidoController.class.getName());
			} catch (Exception e) {
				ManejoDeError.showError(e, "No se puede acceder a la pantalla de Busqueda de Vencidos");
			}
		}

		if (accion.equals(ConstantesRP.Acciones.CARGA_PRECIO_CLIENTE.toString())) {
			if (!cmGestordeVentanas.isAlreadyCreated("CargaPrecioController")) {
				// Creo los controladores de VerLogOperacionesDiaria
				try {
					CargaPrecioView vista = new CargaPrecioView();
					CargaPrecioModel modelo = new CargaPrecioModel();
					cargaPrecioControlador = new CargaPrecioController(this, vista, modelo);
					cmGestordeVentanas.add(cargaPrecioControlador, "CargaPrecioController");
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
					clienteEsclavoControlador = new CargaClienteEsclavoController(this, vista, modelo);
					cmGestordeVentanas.add(clienteEsclavoControlador, "CargaClienteEsclavoController");
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
		try {

			int confirm = Dialog.showConfirmDialog("¿Esta Seguro que quiere Generar los precios de TODOS los Clientes?",
					"Confirmacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (confirm == JOptionPane.YES_OPTION) {
//				PantPrincipalController.setCursorOcupado();
				try {
//					GeneradorDePrecios.generarPrecios();
					BarraDeProgreso pantalla = new BarraDeProgreso("");
					WorkerProcesar wp = new WorkerProcesar(pantalla);
					pantalla.setWorker(wp);
					pantalla.inicializar();
				} finally {
	//				PantPrincipalController.setRestoreCursor();
				}

		//		Dialog.showMessageDialog("Los precios fueron generados exitosamente.");
			}
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al Generar Precios");
			System.exit(0);
		}
	}

	@Override
	protected void salir() {
		if ((cargaPrecioControlador != null) && cargaPrecioControlador.isPendienteImpactar()) {
			Dialog.showMessageDialog("Antes de salir debe impactar los precios de la pantalla \"Precio de Cliente\"",
					"Cambios Pendientes", JOptionPane.ERROR_MESSAGE);
		} else {
			if ((clienteEsclavoControlador != null) && clienteEsclavoControlador.isPendienteImpactar()) {
				Dialog.showMessageDialog("Antes de salir debe impactar los cambios de la pantalla \"Cliente/Esclavo\"",
						"Cambios Pendientes", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					int confirm = Dialog.showConfirmDialog("¿Esta Seguro que quiere salir de la aplicacion?",
							"Confirmacion de Salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							null, null);

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