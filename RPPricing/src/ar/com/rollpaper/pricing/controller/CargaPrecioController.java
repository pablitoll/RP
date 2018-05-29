package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.table.WebTable;
import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.model.CargaItemEspecialModel;
import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rollpaper.pricing.view.CargaItemEspecialView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaPrecioController
		extends BaseControllerMVC<PantPrincipalController, CargaPrecioView, CargaPrecioModel> {

	private CcobClie clienteCargado;

	private CargaItemEspecialModel itemEspecialModel = new CargaItemEspecialModel();
	private CargaItemEspecialView itemEspecialView = new CargaItemEspecialView();
	private CargaItemEspecial itemEspecial = new CargaItemEspecial(PantPrincipalController.getPantallaPrincipal(),
			itemEspecialView, itemEspecialModel, null);

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
		getView().lblError.setText("");

		getView().tableDescEspecifico.removeAll();

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

			String resutlado = "";
			try {
				itemEspecial.setRegistro(getModel().getRegistroPedidoEspecialEmpty());
				resutlado = itemEspecial.iniciar();

				if (!resutlado.equals("")) {

					PreciosEspeciales registro = itemEspecial.getRegistro();

					DefaultTableModel model = getModelActivo();

					model.addRow(new Object[] { registro.getPricArticulo(), itemEspecial.getNombreItem(), itemEspecial.getDescripcionItem(), itemEspecial.getUnidadItem(),
							registro.getPricDescuento1(), registro.getPricDescuento2(), registro.getPricMoneda(),
							registro.getPricPrecio(), registro.getPricFechaDesde(), registro.getPricFechaHasta(),
							registro.getPricReferencia() });

					getTableActivo().setSelectedRow(getTableActivo().getRowCount() - 1);
				}

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al obtener registro");
			}

		}

		if (accion.equals(ConstantesRP.PantCarPrecio.ELIMINAR.toString())) {
			if (getTableActivo().getSelectedRow() >= 0) {
				DefaultTableModel dm = getModelActivo();
				dm.removeRow(getTableActivo().getSelectedRow());
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.GRABAR.toString())) {
			if (validarTablaEspecifico()) {
				try {
					getModel().grabar(getView().tableDescEspecifico);
					resetearPantalla();
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al Guardar Precios");
				}
			}
		}

	}

	private boolean validarTablaEspecifico() {
		// Primero valido que los campos esten bien cargados
		for (int i = 0; i < getView().tableDescEspecifico.getRowCount(); i++) {
			Object fechaDesde = getView().tableDescEspecifico.getValueAt(i, CargaPrecioView.COL_DESDE_ESPECIFICO);
			Object fechaHasta = getView().tableDescEspecifico.getValueAt(i, CargaPrecioView.COL_HASTA_ESPECIFICO);

			if (fechaDesde == null) {
				getView().lblError.setText("La fecha Desde no puede estar Vacia");
				getView().tableDescEspecifico.setSelectedRow(i);
				return false;
			}

			if (fechaHasta == null) {
				getView().lblError.setText("La fecha Hasta no puede estar Vacia");
				getView().tableDescEspecifico.setSelectedRow(i);
				return false;
			}

			Date dFechaDesde = FechaManagerUtil.String2Date(fechaDesde.toString());
			Date dFechaHasta = FechaManagerUtil.String2Date(fechaHasta.toString());

			if (FechaManagerUtil.getDateDiff(dFechaDesde, dFechaHasta, TimeUnit.DAYS) < 1) {
				getView().lblError.setText("La fecha desde debe ser menor a la hasta");
				getView().tableDescEspecifico.setSelectedRow(i);
				return false;
			}

			Object desc1 = getView().tableDescEspecifico.getValueAt(i, CargaPrecioView.COL_1DESC_ESPECIFICO);
			// Object desc2 = getView().tableDescEspecifico.getValueAt(i,
			// CargaPrecioView.COL_2DESC_ESPECIFICO);

			Object moneda = getView().tableDescEspecifico.getValueAt(i, CargaPrecioView.COL_MONEDA_ESPECIFICO);
			Object precio = getView().tableDescEspecifico.getValueAt(i, CargaPrecioView.COL_PRECIO_ESPECIFICO);

			if ((desc1 == null) && (precio == null)) {
				getView().lblError.setText("Falta cargar el porcentage de descuento o el precio");
				getView().tableDescEspecifico.setSelectedRow(i);
				return false;
			}

			if ((desc1 != null) && (precio != null)) {
				getView().lblError.setText("No se puede cargar un decuento y precio simultaneamente");
				getView().tableDescEspecifico.setSelectedRow(i);
				return false;
			}

			if ((precio != null) && (moneda == null)) {
				getView().lblError.setText("Falta cargar la moneda");
				getView().tableDescEspecifico.setSelectedRow(i);
				return false;
			}

		}

		return true;
	}

	private WebTable getTableActivo() {
		if (getView().tabPanel.getSelectedIndex() == 0) {
			return getView().tableDescFamilia;
		} else {
			return getView().tableDescEspecifico;
		}
	}

	private DefaultTableModel getModelActivo() {
		return (DefaultTableModel) getTableActivo().getModel();
	}
}
