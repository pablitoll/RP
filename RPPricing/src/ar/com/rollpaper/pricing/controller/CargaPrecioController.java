package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.model.CargaItemEspecialModel;
import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaItemEspecialView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaPrecioController extends BaseControllerMVC<PantPrincipalController, CargaPrecioView, CargaPrecioModel> {

	private CcobClie clienteCargado;

	private CargaItemEspecialModel itemEspecialModel = new CargaItemEspecialModel();
	private CargaItemEspecialView itemEspecialView = new CargaItemEspecialView();
	private CargaItemEspecial itemEspecial = new CargaItemEspecial(PantPrincipalController.getPantallaPrincipal(), itemEspecialView, itemEspecialModel, null);

	public CargaPrecioController(PantPrincipalController pantPrincipal, CargaPrecioView view, CargaPrecioModel model) throws Exception {
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

		if (!getView().txtNroCliente.getText().equals("")) {
			String id = getView().txtNroCliente.getText();
			if (CommonUtils.isNumeric(id)) {
				cliente = CcobClieDAO.findById(Integer.valueOf(id));
			}
		}

		if (cliente != null) {
			if ((clienteCargado == null) || (clienteCargado.getClieCliente() != cliente.getClieCliente())) {
				if ((clienteCargado == null) || (WebOptionPane.showConfirmDialog(getView(), "Esta cargando otro Cliente, ¿Cancelamos la carga del actual?", "Cambio de Cliente",
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

		if (accion.equals(ConstantesRP.PantCarPrecio.AGREGAR.toString())) {

			String resutlado = "";
			try {
				itemEspecial.setRegistro(getModel().getRegistroPedidoEspecialEmpty());
				resutlado = itemEspecial.iniciar();

				if (!resutlado.equals("")) {

					PreciosEspeciales registro = itemEspecial.getRegistro();

					agregarRegistroATabla(getTableActivo(), registro, itemEspecial.getNombreItem(), itemEspecial.getDescripcionItem(), itemEspecial.getUnidadItem());

					getTableActivo().setSelectedRow(getTableActivo().getRowCount() - 1);

					// grabar
				}

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al obtener registro");
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.MODIFICAR.toString())) {
			String resutlado = "";
			try {
				int row = getTableActivo().getSelectedRow();
				itemEspecial.setRegistro(getModel().getRegistroPedidoEspecial(getTableActivo(), row));
				resutlado = itemEspecial.iniciar();

				if (!resutlado.equals("")) {

					PreciosEspeciales registro = itemEspecial.getRegistro();

					modificarRegistroATabla(getTableActivo(), registro, row);

					// getModel().grabar(getView().tableDescEspecifico);
				}

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al actualizar registro");
			}

		}

		if (accion.equals(ConstantesRP.PantCarPrecio.ELIMINAR.toString())) {
			if (getTableActivo().getSelectedRow() >= 0) {
				DefaultTableModel dm = getModelActivo();
				dm.removeRow(getTableActivo().getSelectedRow());
			}
		}

	}

	private void modificarRegistroATabla(RPTable tableActivo, PreciosEspeciales registro, int row) {

		tableActivo.setValueAt(registro.getPricDescuento1() != null ? Common.double2String(registro.getPricDescuento1().doubleValue()) : "", row,
				CargaPrecioView.COL_1DESC_ESPECIFICO);
		tableActivo.setValueAt(registro.getPricDescuento2() != null ? Common.double2String(registro.getPricDescuento2().doubleValue()) : "", row,
				CargaPrecioView.COL_2DESC_ESPECIFICO);
		tableActivo.setValueAt(registro.getPricMoneda(), row, CargaPrecioView.COL_MONEDA_ESPECIFICO);
		tableActivo.setValueAt(registro.getPricPrecio() != null ? Common.double2String(registro.getPricPrecio().doubleValue()) : "", row, CargaPrecioView.COL_PRECIO_ESPECIFICO);
		tableActivo.setValueAt(registro.getPricFechaDesde() != null ? FechaManagerUtil.Date2String(registro.getPricFechaDesde()) : "", row, CargaPrecioView.COL_DESDE_ESPECIFICO);
		tableActivo.setValueAt(registro.getPricFechaHasta() != null ? FechaManagerUtil.Date2String(registro.getPricFechaHasta()) : "", row, CargaPrecioView.COL_HASTA_ESPECIFICO);
		tableActivo.setValueAt(registro.getPricReferencia(), row, CargaPrecioView.COL_REFERENCIA_ESPECIFICO);
		tableActivo.setValueAt(registro, row, CargaPrecioView.COL_REGISTRO_ESPECIFICO);

	}

	private void agregarRegistroATabla(RPTable tabla, PreciosEspeciales registro, String nombreItem, String descItem, String unidadItem) {

		tabla.addRow(new Object[] { registro.getPricArticulo(), nombreItem, descItem, unidadItem,
				registro.getPricDescuento1() != null ? Common.double2String(registro.getPricDescuento1().doubleValue()) : "",
				registro.getPricDescuento2() != null ? Common.double2String(registro.getPricDescuento2().doubleValue()) : "", registro.getPricMoneda(),
				registro.getPricPrecio() != null ? Common.double2String(registro.getPricPrecio().doubleValue()) : "",
				registro.getPricFechaDesde() != null ? FechaManagerUtil.Date2String(registro.getPricFechaDesde()) : "",
				registro.getPricFechaHasta() != null ? FechaManagerUtil.Date2String(registro.getPricFechaHasta()) : "", registro.getPricReferencia(), registro });

	}

	private RPTable getTableActivo() {
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
