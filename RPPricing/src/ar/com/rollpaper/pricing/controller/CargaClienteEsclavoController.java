package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.model.CargaClienteEsclavoModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaClienteEsclavoController
		extends BaseControllerMVC<PantPrincipalController, CargaClienteEsclavoView, CargaClienteEsclavoModel> {

	private CcobClie clienteCargado;

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

		view.tableEsclavo.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if ((e != null) && ((e.getType() == TableModelEvent.INSERT) || ((e.getType() == TableModelEvent.UPDATE)
						&& e.getColumn() == CargaClienteEsclavoView.COL_ID))) {
					try {
						actualizarDescripcion(e.getLastRow());
					} catch (Exception e1) {
						ManejoDeError.showError(e1, "Error al refrescar Tabla");
					}
				}

			}
		});

	}

	protected void perdioFocoCliente() throws Exception {
		CcobClie cliente = null;

		if (!getView().txtNroCliente.getText().equals("")) {
			String id = getView().txtNroCliente.getText();
			if (CommonUtils.isNumeric(id)) {
				cliente = CcobClieDAO.findById(Integer.valueOf(id));
			}
		}

		if (cliente != null) {
			if ((clienteCargado == null) || (clienteCargado.getClieCliente() != cliente.getClieCliente())) {
				if ((clienteCargado == null) || (WebOptionPane.showConfirmDialog(getView(),
						"Esta cargando otro Cliente, ¿Cancelamos la carga del actual?", "Cambio de Cliente",
						WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0)) {
					getView().lblNombreCliente.setText(cliente.getClieNombre());
					getView().lblNombreLegal.setText(cliente.getClieNombreLegal());
					clienteCargado = cliente;
					cargarLista(cliente);
					setModoPantalla();
				} else {
					getView().txtNroCliente.setText(String.valueOf(clienteCargado.getClieCliente()));
				}
			}

		} else {
			resetearPantalla();
		}
	}

	private void cargarLista(CcobClie cliente) throws Exception {
		List<VentCliv> listas = VentClivDAO.getListaPreciosByCliente(cliente);
		if (listas.size() > 1) {
			throw new Exception("El Cliente tiene mas de una lista asociada");
		}

		if (listas.size() == 1) {
			getView().lblNroLista.setText(String.valueOf(listas.get(0).getClivListaPrecvta()));

			if (listas.get(0).getClivListaPrecvta() != null) {
				VentLipv lista = VentLipvDAO.findById(listas.get(0).getClivListaPrecvta());
				if (lista != null) {
					getView().lblNombreLista.setText(lista.getLipvNombre());
				} else {
					throw new Exception("No existe la lista " + listas.get(0).getClivListaPrecvta());
				}
			} else {
				getView().lblNroLista.setText("Sin lista");
				getView().lblNombreLista.setText("No tiene Lista de Precio Asociada");
			}
		}

		setModoPantalla();
	}

	private void setModoPantalla() {
		Boolean tieneCli = !getView().lblNombreCliente.getText().equals("S/D");

		getView().tableEsclavo.setEnabled(tieneCli);
		getView().btnAgregar.setEnabled(tieneCli);
		getView().btnEliminar.setEnabled(tieneCli);

		getView().btnGrabar.setVisible(tieneCli);
		getView().btnCancelar.setVisible(tieneCli);
		getView().btnImprimir.setVisible(tieneCli);
		getView().btnImprimirTodo.setVisible(tieneCli);

		getView().setCerrarVisible(!tieneCli);
	}

	@Override
	protected String getNombrePantalla() {
		return "Carga de Precio por Cliente";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		getView().lblNombreLista.setText("S/D");
		getView().lblNombreLegal.setText("S/D");
		getView().lblNombreCliente.setText("S/D");
		getView().lblNroLista.setText("S/D");

		DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();
		dm.getDataVector().removeAllElements();

		clienteCargado = null;
		getView().txtNroCliente.clear();
		getView().txtNroCliente.requestFocus();
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

			if ((ke.getKeyCode() == KeyEvent.VK_F3) && getView().txtNroCliente.hasFocus()) {
				retorno = true;
				try {
					getView().txtNroCliente.setText(buscarCliente());
					perdioFocoCliente(); // fuerzo la actualizacion

				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
				}
			}

			if ((ke.getKeyCode() == KeyEvent.VK_F3) && getView().tableEsclavo.hasFocus()) {
				retorno = true;
				try {
					getView().tableEsclavo.setValueAt(buscarCliente(), getView().tableEsclavo.getSelectedRow(),
							getView().tableEsclavo.getSelectedColumn());

				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
				}
			}

		}
		return retorno;
	}

	private void actualizarDescripcion(int selectedRow) throws Exception {

		String nombre = "";
		String nombreLegal = "";

		Object id = getView().tableEsclavo.getModel().getValueAt(selectedRow, CargaClienteEsclavoView.COL_ID);

		if (id != null) {
			int idInt;
			if (id.getClass() == Integer.class) {
				idInt = (Integer) id;
			} else {
				idInt = Integer.valueOf((String) id);
			}
			CcobClie cliente = CcobClieDAO.findById(idInt);
			if( cliente != null) {
				nombre = cliente.getClieNombre();
				nombreLegal = cliente.getClieNombreLegal();
			} else {
				getView().tableEsclavo.setValueAt(null, selectedRow, CargaClienteEsclavoView.COL_ID);
			}
		}

		getView().tableEsclavo.setValueAt(nombre, selectedRow, CargaClienteEsclavoView.COL_DESC);
		getView().tableEsclavo.setValueAt(nombreLegal, selectedRow, CargaClienteEsclavoView.COL_DESC_LEGAL);
	}

	private String buscarCliente() throws Exception {
		BuscarClienteDialog buscarClienteDialog = new BuscarClienteDialog(getPantallaPrincipal());
		buscarClienteDialog.iniciar();
		if (buscarClienteDialog.getNroCliente() != null) {
			return String.valueOf(buscarClienteDialog.getNroCliente());
		}

		return "";
	}

	@Override
	public void ejecuarAccion(String accion) {
		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.CANCELAR.toString())) {
			if (WebOptionPane.showConfirmDialog(getView(), "¿Cancelamos la carga Actual?", "Cancelacion de Carga",
					WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0) {
				try {
					resetearPantalla();
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cancelar");
				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.AGREGAR.toString())) {
			DefaultTableModel model = (DefaultTableModel) getView().tableEsclavo.getModel();
			model.addRow(new Object[] { null, "", "" });
			getView().tableEsclavo.setSelectedRow(getView().tableEsclavo.getRowCount() - 1);
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.BORRAR.toString())) {
			if (getView().tableEsclavo.getSelectedRow() >= 0) {
				DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();
				dm.removeRow(getView().tableEsclavo.getSelectedRow());
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.GRABAR.toString())) {
			for (int i = 0; i < getView().tableEsclavo.getRowCount(); i++) {
				Integer id = (Integer) getView().tableEsclavo.getValueAt(i, CargaClienteEsclavoView.COL_ID);

				MaestroEsclavo maestroEsclavo = new MaestroEsclavo(1,
						Integer.valueOf(getView().txtNroCliente.getText()), 1, id);

				//TODO FALLA ACA
				MaestroEsclavoDAO.persist(maestroEsclavo);
			}

		}

	}
}
