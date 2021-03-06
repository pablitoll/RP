package ar.com.rollpaper.pricing.controller;

// TODO no entiendo el proposito de la lista de precios en esta tabla
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.model.CargaClienteEsclavoModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteEsclavoDialog;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rp.rpcutils.CSVExport;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaClienteEsclavoController extends BaseControllerMVC<PantPrincipalController, CargaClienteEsclavoView, CargaClienteEsclavoModel> {

	public CargaClienteEsclavoController(PantPrincipalController pantPrincipal, CargaClienteEsclavoView view, CargaClienteEsclavoModel model) throws Exception {
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
		CcobClie cliente = null;
		getModel().setEsEscalvoEnAlgunaLista(false);

		if (!getView().txtNroCliente.getText().equals("")) {
			String id = getView().txtNroCliente.getText();
			if (CommonUtils.isNumeric(id)) {
				cliente = CcobClieDAO.findById(Integer.valueOf(id));
			}
		}

		if (cliente != null) {
			if ((getModel().getCliente() == null) || (cliente.getClieCliente() != getModel().getCliente().getClieCliente())) {
				getModel().setCliente(cliente);
				getView().lblNombreCliente.setText(cliente.getClieNombre());
				getView().lblNombreLegal.setText(cliente.getClieNombreLegal());

				cargarListaPrecios(cliente);
				// cargo los esclavos de ese cliente
				cargarEsclavos(cliente);

				if (MaestroEsclavoDAO.getListaEsclavosByEsclavo(cliente).size() > 0) {
					Dialog.showMessageDialog("No se puede usar este cliente porque ya es escalvo");
					getModel().setEsEscalvoEnAlgunaLista(true);
				}
			}
		} else {
			getModel().setCliente(null);
			limpiarPantalla();
		}

		setModoPantalla();

	}

	private void cargarEsclavos(CcobClie cliente) throws Exception {
		for (MaestroEsclavo me : MaestroEsclavoDAO.getListaEsclavosByCliente(cliente)) {
			agregarRegistro(me);
		}
	}

	private void agregarRegistro(CcobClie cliente, MaestroEsclavo me) {
		getView().tableEsclavo.addRow(new Object[] { cliente.getClieCliente(), cliente.getClieNombre(), cliente.getClieNombreLegal(), me });
	}

	private void agregarRegistro(MaestroEsclavo me) {
		CcobClie cliente = CcobClieDAO.findById(me.getPricEsclavoCliente());
		agregarRegistro(cliente, me);
	}

	private void cargarListaPrecios(CcobClie cliente) throws Exception {
		getModel().setListaCliente(null);
		List<VentCliv> listas = VentClivDAO.getListaPreciosByCliente(cliente);
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

	private void setModoPantalla() {
		Boolean tieneCli = getModel().getCliente() != null;
		Boolean tieneLista = getModel().getListaCliente() != null;

		getView().txtNroCliente.setEnabled(!tieneCli);

		getView().tableEsclavo.setEnabled(tieneCli);
		getView().btnAgregar.setEnabled(tieneCli && tieneLista && !getModel().isEsEscalvoEnAlgunaLista());
		getView().btnEliminar.setEnabled(tieneCli && tieneLista && !getModel().isEsEscalvoEnAlgunaLista());

		getView().btnCancelar.setVisible(tieneCli);
		getView().btnExpportar.setVisible(tieneCli);
		getView().btnExportarTodo.setVisible(tieneCli);

		getView().setCerrarVisible(!tieneCli);
	}

	@Override
	protected String getNombrePantalla() {
		return "Carga de Precio por Cliente";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		getView().txtNroCliente.clear();
		limpiarPantalla();
	}

	protected void limpiarPantalla() throws Exception {
		// reset lista precios
		getView().lblNombreLista.setText("S/D");
		getView().lblNroLista.setText("S/D");

		getView().lblNombreLegal.setText("S/D");
		getView().lblNombreCliente.setText("S/D");

		getModel().setCliente(null);
		getView().txtNroCliente.requestFocus();
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
		BuscarClienteEsclavoDialog buscarClienteEsclavoDialog = new BuscarClienteEsclavoDialog(getPantallaPrincipal(), getView().tableEsclavo);
		buscarClienteEsclavoDialog.iniciar();
		if (buscarClienteEsclavoDialog.getCliente() != null) {
			return buscarClienteEsclavoDialog.getCliente();
		}

		return null;
	}

	@Override
	public void ejecutarAccion(String accion) {
		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.CANCELAR.toString())) {
			if (WebOptionPane.showConfirmDialog(getView(), "¿Cancelamos la carga Actual?", "Cancelacion de Carga", WebOptionPane.YES_NO_OPTION,
					WebOptionPane.QUESTION_MESSAGE) == 0) {
				try {
					resetearPantalla();
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cancelar");
				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.AGREGAR.toString())) {
			try {
				CcobClie cliente = buscarCliente();
				if (cliente != null) {
					MaestroEsclavo maestroEsclavo = new MaestroEsclavo(getModel().getCliente().getClieCliente(), getModel().getListaCliente().getLipvListaPrecvta(),
							cliente.getClieCliente());
					agregarRegistro(cliente, maestroEsclavo);
					HibernateGeneric.persist(maestroEsclavo);
					getView().tableEsclavo.setSelectedRow(getView().tableEsclavo.getRowCount() - 1);
				}
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al agregar registro");
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.BORRAR.toString())) {
			if (getView().tableEsclavo.getSelectedRow() >= 0) {
				if (WebOptionPane.showConfirmDialog(getView(), "¿Borramos el registro?", "Eliminacion de registro", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == WebOptionPane.YES_OPTION) {

					// obtengo el ID del cliente Esclavo
					int idEsclavo = (int) getView().tableEsclavo.getModel().getValueAt(getView().tableEsclavo.getSelectedRow(), CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO);
					int idMaestro = Integer.valueOf(getView().txtNroCliente.getText());
					MaestroEsclavo me = MaestroEsclavoDAO.findByClienteIdEsclavoID(idMaestro, idEsclavo);

					try {
						HibernateGeneric.remove(me);

						DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();
						dm.removeRow(getView().tableEsclavo.getSelectedRow());

					} catch (Exception ex) {
						ManejoDeError.showError(ex, "Error al borrar");
					}

				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.EXPORTAR.toString())) {
			try {
				String[] header = { "Nro Cliente", "Nombre del Cliente", "Nombre de Fantasia", "Nro Lista", "Nombre Lista", "Nro Cliente Hijo", "Nombre del Cliente Hijo",
						"Nombre de Fantasia" };
				String[][] data = { {} };

				RPTable tableParaExportar = new RPTable();
				tableParaExportar.setModel(new DefaultTableModel(data, header));

				DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();

				Integer nroCliente = getModel().getCliente().getClieCliente();
				String nombreCliente = getModel().getCliente().getClieNombre();
				String fantasiaClietne = getModel().getCliente().getClieNombreLegal();
				Integer nroLista = getModel().getListaCliente().getLipvListaPrecvta();
				String nombreLista = getModel().getListaCliente().getLipvNombre();

				tableParaExportar.clear();
				for (int i = 0; i < dm.getRowCount(); i++) {
					tableParaExportar.addRow(
							new Object[] { nroCliente, nombreCliente, fantasiaClietne, nroLista, nombreLista, dm.getValueAt(i, 0), dm.getValueAt(i, 1), dm.getValueAt(i, 2) });
				}

				String nombreArchivo = String.format("Cliente%s_%s", getModel().getCliente().getClieCliente(),
						FechaManagerUtil.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), "yyyyMMdd_HHmmss"));

				CSVExport.exportToExcel(tableParaExportar, nombreArchivo);

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al exportar");
			}
		}
	}

}
