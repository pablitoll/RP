package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
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
					actualizarDescripcion(e.getLastRow());
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
			getView().lblNombreCliente.setText("S/D");
			getView().lblNombreLegal.setText("S/D");
			clienteCargado = cliente; // es decir null;
			setModoPantalla();
		}
	}

	private void cargarLista(CcobClie cliente) throws Exception {
		List<VentCliv> listas = VentClivDAO.getListaPreciosByCliente(cliente);
		if (listas.size() > 1) {
			throw new Exception("El Cliente tiene mas de una lista asociada");
		}

		if (listas.size() == 1) {
			getView().txtNroLista.setText(String.valueOf(listas.get(0).getClivListaPrecvta()));
			getView().lblNombreLista.setText("FALTA EL NOMBRE DE LA LISTA");
		}

		setModoPantalla();
	}

	private void setModoPantalla() {
		Boolean tieneCli = !getView().lblNombreCliente.getText().equals("S/D");
		Boolean tieneLista = !getView().lblNombreLista.getText().equals("S/D");

		getView().txtNroLista.setEnabled(tieneCli);
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
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);
		if (!retorno) {
			if ((ke.getKeyCode() == KeyEvent.VK_F2) && getView().txtNroCliente.hasFocus()) {
				retorno = true;
				try {
					getView().txtNroCliente.setText(buscarCliente());

				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
				}
			}
			if ((ke.getKeyCode() == KeyEvent.VK_F2) && getView().tableEsclavo.hasFocus()) {
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

	private void actualizarDescripcion(int selectedRow) {

		String nombre = "";
		String nombreLegal = "";

		String id = String
				.valueOf(getView().tableEsclavo.getModel().getValueAt(selectedRow, CargaClienteEsclavoView.COL_ID));

		if (!id.equals("")) {
			nombre = "cambio";
			nombreLegal = "cambio";
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
		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.AGREGAR.toString())) {
			DefaultTableModel model = (DefaultTableModel) getView().tableEsclavo.getModel();
			model.addRow(new Object[] { null, "", "" });
		}
	}
}
