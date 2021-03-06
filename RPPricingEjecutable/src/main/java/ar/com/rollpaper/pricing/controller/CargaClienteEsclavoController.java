package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.GeneradorDePrecios;
import ar.com.rollpaper.pricing.business.ListaBusiness;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.model.CargaClienteEsclavoModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.BuscarClienteEsclavoDialog;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rp.rpcutils.CSVExport;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaClienteEsclavoController
		extends BaseControllerMVC<PantPrincipalController, CargaClienteEsclavoView, CargaClienteEsclavoModel> {

	private boolean procesandoCliente = false;

	public CargaClienteEsclavoController(PantPrincipalController pantPrincipal, CargaClienteEsclavoView view,
			CargaClienteEsclavoModel model) throws Exception {
		super(pantPrincipal, view, model, null);

		view.txtNroCliente.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent evento) {
				try {
					perdioFocoCliente();
				} catch (Exception e1) {

					ManejoDeError.showError(e1, "Error al buscar cliente");
				}
			}
		});
	}

	protected void perdioFocoCliente() throws Exception {
		if (!procesandoCliente) {
			String msgError = "";
			procesandoCliente = true;
			try {
				PantPrincipalController.setCursorOcupado();
				try {
					CcobClie cliente = null;

					if (!getView().txtNroCliente.getText().equals("")) {
						String id = getView().txtNroCliente.getText();
						if (CommonUtils.isNumeric(id)) {
							cliente = CcobClieDAO.findById(Integer.valueOf(id));
						}

						if (cliente != null) {
							if ((getModel().getCliente() == null)
									|| (cliente.getClieCliente() != getModel().getCliente().getClieCliente())) {
								getModel().setCliente(cliente);
								getView().lblNombreCliente.setText(cliente.getClieNombre());
								getView().lblNombreLegal.setText(cliente.getClieNombreLegal());

								String factor = "Sin Multiplicador";
								if (getModel().getFator() != null) {
									factor = Common.double2String(getModel().getFator());
								}
								getView().lblMultiplicador.setText(factor);

								cargarListaPrecios(cliente);
								// cargo los esclavos de ese cliente
								cargarEsclavos(cliente);

								List<MaestroEsclavo> listaMaestroEsclavo = MaestroEsclavoDAO
										.getListaEsclavosByEsclavo(cliente);
								if (listaMaestroEsclavo.size() > 0) {
									String maestro = "(" + listaMaestroEsclavo.get(0).getPricMaestroCliente() + ") "
											+ CcobClieDAO
													.findById(Integer.valueOf(
															listaMaestroEsclavo.get(0).getPricMaestroCliente()))
													.getClieNombre();
									msgError = "No se puede usar este cliente porque ya es Esclavo de " + maestro;
								}

								if (getModel().getListaCliente() == null) {
									msgError = "No se puede usar este cliente porque no tiene Listas Asociadas";
								}
							}
						} else {
							msgError = "No existe el Cliente Ingresado";
						}
					} else {
						getModel().setCliente(null);
						getModel().setCambiosPendietes(false);
						limpiarPantalla();
					}
				} finally {
					PantPrincipalController.setRestoreCursor();
				}

				if (!msgError.equals("")) {
					Dialog.showMessageDialog(msgError, "Carga de Clientes Esclavos", JOptionPane.ERROR_MESSAGE);
					getModel().setCliente(null);
					getModel().setCambiosPendietes(false);
					limpiarPantalla();
				}
				setModoPantalla();
			} finally {
				procesandoCliente = false;
			}
		}
	}

	private void cargarEsclavos(CcobClie cliente) throws Exception {
		getView().tableEsclavo.clear();
		for (MaestroEsclavo me : MaestroEsclavoDAO.getListaEsclavosByCliente(cliente)) {
			agregarRegistro(me);
		}
	}

	private void agregarRegistro(CcobClie cliEsclavo, MaestroEsclavo meRegistro) {
		getView().tableEsclavo.addRow(new Object[] { cliEsclavo.getClieCliente(), cliEsclavo.getClieNombre(),
				cliEsclavo.getClieNombreLegal(), meRegistro });
		getView().tableEsclavo.adjustColumns();
	}

	private void agregarRegistro(MaestroEsclavo me) {
		CcobClie cliente = CcobClieDAO.findById(me.getPricEsclavoCliente());
		agregarRegistro(cliente, me);
	}

	private void cargarListaPrecios(CcobClie cliente) throws Exception {
		getModel().setListaCliente(null);
		List<VentCliv> listas = ListaBusiness.getListaPreciosByCliente(cliente);
		if (listas.size() > 1) {
			throw new Exception("El Cliente tiene mas de una lista asociada");
		}

		if ((listas.size() == 1) && (listas.get(0).getClivListaPrecvta() != null)) {
			VentLipv listaSeleccionada = VentLipvDAO.findById(listas.get(0).getClivListaPrecvta());

			getModel().setListaCliente(listaSeleccionada);
			getView().lblNombreLista.setText(getModel().getListaCliente().getLipvNombre());
			getView().lblNroLista.setText(String.valueOf(getModel().getListaCliente().getLipvListaPrecvta()));

		} else {
			getView().lblNroLista.setText("Sin lista");
			getView().lblNombreLista.setText("No tiene Lista de Precio Asociada");
		}

	}

	@Override
	protected String getNombrePantalla() {
		return "Carga de Precio por Cliente";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		if (getModel().getCliente() == null) {
			limpiarPantalla();
		}
	}

	private void setModoPantalla() {

		Boolean tieneCli = getModel().getCliente() != null;
		Boolean tieneLista = getModel().getListaCliente() != null;

		getView().txtNroCliente.setEnabled(!tieneCli);

		getView().tableEsclavo.setEnabled(tieneCli);
		getView().btnAgregar.setEnabled(tieneCli && tieneLista);
		getView().btnEliminar.setEnabled(tieneCli && tieneLista);

		getView().btnExpportar.setVisible(tieneCli);

		getView().setCerrarVisible(!tieneCli);
		getView().btnTerminarCarga.setVisible(getModel().isCambiosPendientes());
		getView().btnCancelar.setVisible(tieneCli);
		getView().btnCancelar.setEnabled(!getModel().isCambiosPendientes());

	}

	protected void limpiarPantalla() throws Exception {
		// reset lista precios
		getView().lblNombreLista.setText("S/D");
		getView().lblNroLista.setText("S/D");

		getView().lblNombreLegal.setText("S/D");
		getView().lblNombreCliente.setText("S/D");
		getView().lblMultiplicador.setText("S/D");

		getView().txtNroCliente.clear();
		getView().tableEsclavo.clear();
		setModoPantalla();
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);
		if (!retorno) {
			if ((ke.getKeyCode() == KeyEvent.VK_ENTER) && getView().txtNroCliente.hasFocus()) {
				try {
					perdioFocoCliente();
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
				}
			}

			if ((ke.getKeyCode() == KeyEvent.VK_F2) && getView().txtNroCliente.hasFocus()) {
				retorno = true;
				try {
					CcobClie cliente = buscarCliente();
					if (cliente != null) {
						getView().txtNroCliente.setText(String.valueOf(cliente.getClieCliente()));
					}

					perdioFocoCliente(); // fuerzo la actualizacion

				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
				}
			}

		}
		return retorno;
	}

	private CcobClie buscarCliente() throws Exception {
		BuscarClienteDialog buscarClienteDialog = new BuscarClienteDialog(getPantallaPrincipal());
		buscarClienteDialog.iniciar();
		if (buscarClienteDialog.getCliente() != null) {
			return buscarClienteDialog.getCliente();
		}

		return null;
	}

	private CcobClie buscarClienteEsclavo() throws Exception {
		BuscarClienteEsclavoDialog buscarClienteEsclavoDialog = new BuscarClienteEsclavoDialog(getPantallaPrincipal(),
				getView().tableEsclavo);
		buscarClienteEsclavoDialog.iniciar();
		if (buscarClienteEsclavoDialog.getCliente() != null) {
			return buscarClienteEsclavoDialog.getCliente();
		}

		return null;
	}

	@Override
	public void ejecutarAccion(String accion) {
		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.TERMINAR_CARGA.toString())) {
			if (WebOptionPane.showConfirmDialog(getView(), "�Terminamos la carga Actual e Impactamos los Procesios?",
					"Cancelacion de Carga", WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0) {
				try {

					PantPrincipalController.setCursorOcupado();
					try {
						GeneradorDePrecios.impactarPrecios(getModel().getCliente(), getModel().getListaCliente(),
								getModel().getFatorBig(), ArchivoDePropiedadesBusiness.getidListaEspecial());
					} finally {
						PantPrincipalController.setRestoreCursor();
					}

					Dialog.showMessageDialog("Se termino de aplicar los nuevos precios", "Aplicaci�n de Precios",
							JOptionPane.INFORMATION_MESSAGE);

					ejecutarAccion(ConstantesRP.PantCarClienteEsclabo.CANCELAR.toString());
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cancelar");
				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.CANCELAR.toString())) {
			try {
				getModel().setCliente(null);
				getModel().setCambiosPendietes(false);
				limpiarPantalla();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al limpiar pantalla");
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.AGREGAR.toString())) {
			try {
				CcobClie cliEsclavo = buscarClienteEsclavo();
				if (cliEsclavo != null) {
					if (isNuevoEscalvoValido(cliEsclavo)) {
						MaestroEsclavo maestroEsclavo = new MaestroEsclavo(getModel().getCliente().getClieCliente(),
								getModel().getListaCliente().getLipvListaPrecvta(), cliEsclavo.getClieCliente());
						agregarRegistro(cliEsclavo, maestroEsclavo);
						HibernateGeneric.persist(maestroEsclavo);
						agregoEditItem();
						getView().tableEsclavo.setSelectedRow(getView().tableEsclavo.getRowCount() - 1);
					}
				}
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al agregar registro");
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.BORRAR.toString())) {
			if (getView().tableEsclavo.getSelectedRow() >= 0) {
				if (WebOptionPane.showConfirmDialog(getView(),
						"IMPORTANTE: Si elimina este cliente como esclavo\n se eliminaran todos los precios que hereda de su maestro.\n�Borramos el registro?",
						"Eliminacion de registro", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == WebOptionPane.YES_OPTION) {

					// obtengo el ID del cliente Esclavo
					try {
						int row = getView().tableEsclavo.getSelectedRow();
						int modelRow = getView().tableEsclavo.convertRowIndexToModel(row);

						getView().tableEsclavo.setRowSorter(null);

						int idEsclavo = (int) getView().tableEsclavo.getModel().getValueAt(modelRow,
								CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO);
						int idMaestro = Integer.valueOf(getView().txtNroCliente.getText());
						MaestroEsclavo me = MaestroEsclavoDAO.findByClienteIdEsclavoID(idMaestro, idEsclavo);

						DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();
						dm.removeRow(modelRow);
						getView().tableEsclavo.adjustColumns();

						HibernateGeneric.remove(me);
						agregoEditItem();

						GeneradorDePrecios.eliminarListaCustomizada(CcobClieDAO.findById(idEsclavo),
								getModel().getListaCliente());

					} catch (Exception ex) {
						ManejoDeError.showError(ex, "Error al borrar");
					}
				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.EXPORTAR.toString())) {
			try {
				DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();

				String[] header = { "Nro Cliente", "Nombre del Cliente", "Nombre de Fantasia", "Nro Lista",
						"Nombre Lista", "Nro Cliente Hijo", "Nombre del Cliente Hijo", "Nombre de Fantasia" };
				String[][] data = { {} };

				RPTable tableParaExportar = new RPTable();
				tableParaExportar.setModel(new DefaultTableModel(data, header));

				Integer nroCliente = getModel().getCliente().getClieCliente();
				String nombreCliente = getModel().getCliente().getClieNombre();
				String fantasiaClietne = getModel().getCliente().getClieNombreLegal();
				Integer nroLista = getModel().getListaCliente().getLipvListaPrecvta();
				String nombreLista = getModel().getListaCliente().getLipvNombre();

				tableParaExportar.clear();
				for (int i = 0; i < dm.getRowCount(); i++) {
					tableParaExportar.addRow(new Object[] { nroCliente, nombreCliente, fantasiaClietne, nroLista,
							nombreLista, dm.getValueAt(i, 0), dm.getValueAt(i, 1), dm.getValueAt(i, 2) });
				}

				String nombreArchivo = String.format("Cliente%s_%s", getModel().getCliente().getClieCliente(),
						FechaManagerUtil.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), "yyyyMMdd_HHmmss"));

				CSVExport.exportToExcel(tableParaExportar, nombreArchivo, null);
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al exportar");
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.EXPORTAR_TODO.toString())) {

			try {
				PantPrincipalController.setCursorOcupado();
				try {

					String[] header = { "Nro Cliente", "Nombre del Cliente", "Nombre de Fantasia", "Nro Lista",
							"Nombre Lista", "Nro Cliente Hijo", "Nombre del Cliente Hijo", "Nombre de Fantasia" };
					String[][] data = { {} };

					RPTable tableParaExportar = new RPTable();
					tableParaExportar.setModel(new DefaultTableModel(data, header));

					tableParaExportar.clear();

					CcobClie clientePadre = null;
					VentLipv lista = null;

					for (MaestroEsclavo maestroEsclavo : MaestroEsclavoDAO.getListaMaestroEsclavos()) {

						if ((clientePadre == null)
								|| (clientePadre.getClieCliente() != maestroEsclavo.getPricMaestroCliente())) {
							clientePadre = CcobClieDAO
									.findById(Integer.valueOf(maestroEsclavo.getPricMaestroCliente()));
						}

						CcobClie clienteHijo = CcobClieDAO
								.findById(Integer.valueOf(maestroEsclavo.getPricEsclavoCliente()));

						if ((lista == null)
								|| (lista.getLipvListaPrecvta() != maestroEsclavo.getPricMEListaPrecvta())) {
							lista = VentLipvDAO.findById(maestroEsclavo.getPricMEListaPrecvta());
						}

						tableParaExportar.addRow(
								new Object[] { maestroEsclavo.getPricMaestroCliente(), clientePadre.getClieNombre(),
										clientePadre.getClieNombreLegal(), maestroEsclavo.getPricMEListaPrecvta(),
										lista.getLipvNombre(), clienteHijo.getClieCliente(),
										clienteHijo.getClieNombre(), clienteHijo.getClieNombreLegal() });
					}

					// SORT
					TableRowSorter<TableModel> sortertableParaExportar = new TableRowSorter<TableModel>(
							tableParaExportar.getModel());
					ArrayList<RowSorter.SortKey> sortKeysFamilia = new ArrayList<RowSorter.SortKey>();

					sortKeysFamilia.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
					sortKeysFamilia.add(new RowSorter.SortKey(6, SortOrder.ASCENDING));

					sortertableParaExportar.setSortKeys(sortKeysFamilia);

					tableParaExportar.setRowSorter(sortertableParaExportar);
					sortertableParaExportar.sort();

					String nombreArchivo = String.format("TodosClientesEsclavos_%s", FechaManagerUtil
							.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), "yyyyMMdd_HHmmss"));

					CSVExport.exportToExcel(tableParaExportar, nombreArchivo, null);
				} finally {
					PantPrincipalController.setRestoreCursor();
				}
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al exportar");
			}
		}

	}

	private boolean isNuevoEscalvoValido(CcobClie cliEsclavo) {

		if (esclavoTieneCargadoItems(cliEsclavo, getModel().getListaCliente().getLipvListaPrecvta())) {
			Dialog.showMessageDialog(String.format(
					"El cliente (%s) \"%s\" tiene datos previos. No puede ser usado como esclavo.\nPara poder seguir adelante, antes debe eliminar todos sus precios vigentes e hist�ricos.",
					cliEsclavo.getClieCliente(), cliEsclavo.getClieNombre()), "Cliente con datos previos",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// TODO REVISAR SAQUE ESATA VALIDACION
		// if (clienteNuevoTieneListaActual(cliEsclavo)) {
		// Dialog.showMessageDialog(String.format(
		// "El Cliente (%s) \"%s\" tiene asignada como Lista Principal \"%s\".\nNo se
		// puede asignar como Esclavo",
		// cliEsclavo.getClieCliente(), cliEsclavo.getClieNombre(),
		// getModel().getListaCliente().getLipvNombre()), "Esclavo no Valdio",
		// JOptionPane.ERROR_MESSAGE);
		// return false;
		// }

		return true;
	}

	// private boolean clienteNuevoTieneListaActual(CcobClie cliEsclavo) {
	// for (VentCliv ventCliv : ListaBusiness.getListaPreciosByCliente(cliEsclavo))
	// {
	// if
	// (ventCliv.getClivListaPrecvta().equals(getModel().getListaCliente().getLipvListaPrecvta()))
	// {
	// return true;
	// }
	// }
	// return false;
	// }

	private boolean esclavoTieneCargadoItems(CcobClie cliEsclavo, int idLista) {
		return !DescuentoXFamiliasDAO.getListaDescuentoByID(cliEsclavo.getClieCliente(), idLista).isEmpty()
				|| !PreciosEspecialesDAO.getListaPrecioEspeciaByID(cliEsclavo.getClieCliente(), idLista).isEmpty();
	}

	private void agregoEditItem() {
		getModel().setCambiosPendietes(true);
		setModoPantalla();
	}

	public boolean isPendienteImpactar() {
		return getModel().isCambiosPendientes();

	}

}
