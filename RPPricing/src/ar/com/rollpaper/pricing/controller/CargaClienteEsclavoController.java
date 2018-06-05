package ar.com.rollpaper.pricing.controller;
// TODO en el alta de una nueva relacion maestro esclavo no deberiamos controlar que un esclavo no sea maestro?
// TODO no entiendo el proposito de la lista de precios en esta tabla
// TODO 
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
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
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.model.CargaClienteEsclavoModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.Dialog;
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
						&& e.getColumn() == CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO))) {
					try {

						actualizarDescripcion(e);
						if (e.getType() == TableModelEvent.UPDATE) {
							System.out.println("update");
						} else {
							System.out.println("no update");
						}

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
			if ((clienteCargado == null) || (clienteCargado.getClieCliente() != cliente.getClieCliente())) 
			{
				
				if ((clienteCargado == null) || (WebOptionPane.showConfirmDialog(getView(),
						"Esta cargando otro Cliente, ¿Cancelamos la carga del actual?", "Cambio de Cliente",
						WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0))
				{
					// reset lista precios
					getView().lblNombreLista.setText("S/D");
					getView().lblNroLista.setText("S/D");

			// reset tabla
					DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();
					dm.getDataVector().removeAllElements();

					getView().lblNombreCliente.setText(cliente.getClieNombre());
					getView().lblNombreLegal.setText(cliente.getClieNombreLegal());
					clienteCargado = cliente;
					
					cargarListaPrecios(cliente);
					// cargo los esclavos de ese cliente
					cargarListaEsclavos(cliente);
					setModoPantalla();
				} else
				{
					getView().txtNroCliente.setText(String.valueOf(clienteCargado.getClieCliente()));
				}
			}

		} 
		
		else
		{
			resetearPantalla();
		}
	}

	private void cargarListaEsclavos(CcobClie cliente) throws Exception {
		DefaultTableModel model = (DefaultTableModel) getView().tableEsclavo.getModel();
		for (MaestroEsclavo me : MaestroEsclavoDAO.getListaEsclavosByCliente(cliente)) {
			model.addRow(new Object[] { me.getPricEsclavoCliente(), me.getPricMEListaPrecvta(),
					me.getPricEsclavoCliente() });
		}
		setModoPantalla();

	}

	private void cargarListaPrecios(CcobClie cliente) throws Exception {
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
		
		// reset lista precios
		getView().lblNombreLista.setText("S/D");
		getView().lblNroLista.setText("S/D");

// reset tabla
		DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();
		dm.getDataVector().removeAllElements();

		
		getView().lblNombreLegal.setText("S/D");
		getView().lblNombreCliente.setText("S/D");
		

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

	private void actualizarDescripcion(TableModelEvent e) throws Exception {

		String nombre = "";
		String nombreLegal = "";
		int selectedRow = e.getLastRow();
		Object id = getView().tableEsclavo.getModel().getValueAt(selectedRow,
				CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO);

		CcobClie cliente = null;
		if (id != null) {
			int idInt;
			if (id.getClass() == Integer.class) {
				idInt = (Integer) id;
			} else {
				idInt = Integer.valueOf((String) id);
			}
			cliente = CcobClieDAO.findById(idInt);
			if (cliente != null) {
				nombre = cliente.getClieNombre();
				nombreLegal = cliente.getClieNombreLegal();
				
			} else {
				Dialog.showMessageDialog("No existe este cliente",
						"Error al cargar relacion MAESTRO-ESCLAVO", JOptionPane.CANCEL_OPTION);
				getView().tableEsclavo.setValueAt(null, selectedRow, CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO);
				
			}
		}

		getView().tableEsclavo.setValueAt(nombre, selectedRow, CargaClienteEsclavoView.COL_DESC);
		getView().tableEsclavo.setValueAt(nombreLegal, selectedRow, CargaClienteEsclavoView.COL_DESC_LEGAL);

		if (e.getType() == TableModelEvent.UPDATE && cliente != null) {
			// TODO : De donde saco la listade precios ??? Un cliente no puede tener mas de una lista de precios??
			// para que es la lista en esta tabla?
			List<VentCliv> listaPrecio = VentClivDAO.getListaPreciosByCliente(cliente);
			Integer lp;
			if (listaPrecio.size()==0) {lp = 0;}else {lp = listaPrecio.get(0).getClivListaPrecvta();}
			MaestroEsclavo maestroEsclavo = new MaestroEsclavo(Integer.valueOf(getView().txtNroCliente.getText()),
					
				lp	, cliente.getClieCliente());
		
			try {
				HibernateGeneric.persist(maestroEsclavo);

			} catch (PersistenceException ex) {
				if (ex.getCause().getCause().getMessage().contains("CHK_NO_SELF_SLAVE")) {
					Dialog.showMessageDialog("No puede ser esclavo de si mismo",
							"Error al cargar relacion MAESTRO-ESCLAVO", JOptionPane.CANCEL_OPTION);
					getView().tableEsclavo.setValueAt(null, selectedRow, CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO);
					

				}
				if (ex.getCause().getCause().getMessage().contains("CHK_MASTER_SLAVE_U")) {
					Dialog.showMessageDialog("Esta relacion ya existe!",
							"Error al cargar relacion MAESTRO-ESCLAVO", JOptionPane.CANCEL_OPTION);
					getView().tableEsclavo.setValueAt(null, selectedRow, CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO);
				}
			} catch (Exception ex) {
				ManejoDeError.showError(ex, "Error al grabar");
			}
		}

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
	public void ejecutarAccion(String accion) {
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
				if (WebOptionPane.showConfirmDialog(getView(), "¿Borramos el registro?", "Eliminacion de registro", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == WebOptionPane.YES_OPTION) {

				// obtengo el ID del cliente Esclavo
				int idEsclavo = (int) getView().tableEsclavo.getModel().getValueAt(
						getView().tableEsclavo.getSelectedRow(), CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO);
				int idMaestro = Integer.valueOf(getView().txtNroCliente.getText());
				MaestroEsclavo me = MaestroEsclavoDAO.findByClienteIdEsclavoID(idMaestro, idEsclavo);

				try {
					HibernateGeneric.remove(me);

					DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();
					dm.removeRow(getView().tableEsclavo.getSelectedRow());

				} catch (Exception ex) {
					ManejoDeError.showError(ex, "Error al borrar");
				}

			}}
		}

		if (accion.equals(ConstantesRP.PantCarClienteEsclabo.GRABAR.toString())) {
			// for (int i = 0; i < getView().tableEsclavo.getRowCount(); i++) {
			// Integer id = (Integer) getView().tableEsclavo.getValueAt(i,
			// CargaClienteEsclavoView.COL_ID_CLIENTE_ESCLAVO);
			// // TODO : PMV que va en el campo LISTA
			// MaestroEsclavo maestroEsclavo = new
			// MaestroEsclavo(Integer.valueOf(getView().txtNroCliente.getText()),
			// 1, id);
			//
			// try {
			// HibernateGeneric.persist(maestroEsclavo);
			//
			// } catch (Exception e) {
			// ManejoDeError.showError(e, "Error al grabar");
			// }
			//
			// }

		}

	}
}
