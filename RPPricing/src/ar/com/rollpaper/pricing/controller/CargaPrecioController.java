package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.hibernate.mapping.Array;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.table.WebTable;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaPrecioController
		extends BaseControllerMVC<PantPrincipalController, CargaPrecioView, CargaPrecioModel> {

	private CcobClie clienteCargado;

	public CargaPrecioController(PantPrincipalController pantPrincipal, CargaPrecioView view, CargaPrecioModel model)
			throws Exception {
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

		view.tableDescFamilia.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if ((e != null) && ((e.getType() == TableModelEvent.INSERT) || ((e.getType() == TableModelEvent.UPDATE)
						&& e.getColumn() == CargaClienteEsclavoView.COL_ID))) {
					try {
						actualizarDescripcionFamilia(e.getLastRow());
					} catch (Exception e1) {
						ManejoDeError.showError(e1, "Error al refrescar Tabla");
					}
				}

			}
		});

		view.tableDescEspecifico.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if ((e != null) && ((e.getType() == TableModelEvent.INSERT) || ((e.getType() == TableModelEvent.UPDATE)
						&& e.getColumn() == CargaClienteEsclavoView.COL_ID))) {
					try {
						actualizarDescripcionEspecifico(e.getLastRow());
					} catch (Exception e1) {
						ManejoDeError.showError(e1, "Error al refrescar Tabla");
					}
				}

			}
		});
	}

	protected void actualizarDescripcionEspecifico(int selectedRow) {
		// TODO Auto-generated method stub
		
		String nombre= "";
		String descripcion = "";
		

		Object id = getView().tableDescEspecifico.getModel().getValueAt(selectedRow, CargaPrecioView.COL_ID_ESPECIFICO);

		if (id != null) {
			int idInt;
			if (id.getClass() == Integer.class) {
				idInt = (Integer) id;
			} else {
				idInt = Integer.valueOf((String) id);
			}
			StocArts articulo = StocArtsDAO.findById(idInt);
			if (articulo != null) {
				descripcion = articulo.getArtsDescripcion();
				//nombreLegal = articulo.getClieNombreLegal();
			} else {
				getView().tableDescEspecifico.setValueAt(null, selectedRow, CargaPrecioView.COL_ID_ESPECIFICO);
			}
		}

		getView().tableDescEspecifico.setValueAt(descripcion, nombre, CargaPrecioView.COL_NOMBRE_ESPECIFICO);
		getView().tableDescEspecifico.setValueAt(descripcion, selectedRow, CargaPrecioView.COL_DESC_ESPECIFICO);


	}

	protected void actualizarDescripcionFamilia(int lastRow) {
		// TODO Auto-generated method stub

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
			if (listas.get(0).getClivListaPrecvta() != null) {
				getView().txtNroLista.setText(String.valueOf(listas.get(0).getClivListaPrecvta()));

				VentLipv lista = VentLipvDAO.findById(listas.get(0).getClivListaPrecvta());
				if (lista != null) {
					getView().lblNombreLista.setText(lista.getLipvNombre());
				} else {
					throw new Exception("No existe la lista " + listas.get(0).getClivListaPrecvta());
				}
			}

		}

		setModoPantalla();
	}

	private void setModoPantalla() {
		Boolean tieneCli = !getView().lblNombreCliente.getText().equals("S/D");
		Boolean tieneLista = !getView().lblNombreLista.getText().equals("S/D");

		getView().txtNroLista.setEnabled(tieneCli);
		getView().tableDescEspecifico.setEnabled(tieneCli);
		getView().tableDescFamilia.setEnabled(tieneCli);
		getView().btnAgregar.setEnabled(tieneCli);
		getView().btnEliminar.setEnabled(tieneCli);

		getView().btnBorrar.setVisible(tieneCli);
		getView().btnGrabar.setVisible(tieneCli);
		getView().btnCancelar.setVisible(tieneCli);
		getView().setCerrarVisible(!tieneCli);

		// getView().txtNroLista.setEnabled(!sinDatos);

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

		clienteCargado = null;
		getView().txtNroCliente.clear();
		getView().txtNroLista.clear();

		// falta borrar lista

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
					buscarCliente();
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
				}
			}
		}
		return retorno;
	}

	private void buscarCliente() throws Exception {
		BuscarClienteDialog buscarClienteDialog = new BuscarClienteDialog(getPantallaPrincipal());
		buscarClienteDialog.iniciar();
		if (buscarClienteDialog.getNroCliente() != null) {
			getView().txtNroCliente.setValue(buscarClienteDialog.getNroCliente());
		}
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

		if (accion.equals(ConstantesRP.PantCarPrecio.AGREGAR.toString())) {
			DefaultTableModel model = getModelActivo();

			Object[] registroVacio = new Object[getTableActivo().getColumnCount()];
			for (int i = 0; i < getTableActivo().getColumnCount(); i++) {
				registroVacio[i] = null;
			}

			model.addRow(registroVacio);
			getTableActivo().setSelectedRow(getTableActivo().getRowCount() - 1);
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.ELIMINAR.toString())) {
			if (getTableActivo().getSelectedRow() >= 0) {
				DefaultTableModel dm = getModelActivo();
				dm.removeRow(getTableActivo().getSelectedRow());
			}
		}
	}

	private WebTable getTableActivo() {
		if (getView().tabPanel.getSelectedIndex() == 0) {
			return getView().tableDescFamilia;
		} else {
			return getView().tableDescEspecifico;
		}
	}

	private DefaultTableModel getModelActivo() {
		if (getView().tabPanel.getSelectedIndex() == 0) {
			return (DefaultTableModel) getView().tableDescFamilia.getModel();
		} else {
			return (DefaultTableModel) getView().tableDescEspecifico.getModel();
		}
	}
}
