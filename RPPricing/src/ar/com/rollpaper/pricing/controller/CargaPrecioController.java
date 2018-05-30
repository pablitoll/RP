package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
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
			if ((getModel().getClienteCargado() == null) || (getModel().getClienteCargado().getClieCliente() != cliente.getClieCliente())) {
				if ((getModel().getClienteCargado() == null) || (WebOptionPane.showConfirmDialog(getView(), "Esta cargando otro Cliente, ¿Cancelamos la carga del actual?",
						"Cambio de Cliente", WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0)) {
					getView().lblNombreCliente.setText(cliente.getClieNombre());
					getView().lblNombreLegal.setText(cliente.getClieNombreLegal());
					getModel().setClienteCargado(cliente);
					cargarLista(cliente);
					setModoPantalla();
				} else {
					getView().txtNroCliente.setText(String.valueOf(getModel().getClienteCargado().getClieCliente()));
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
		getView().btnModificar.setEnabled(tieneCli);
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

		getModel().setClienteCargado(null);
		getView().txtNroCliente.clear();
		getView().txtNroLista.clear();
		getView().lblError.setText("");

		// falta borrar lista
		getView().tableDescEspecifico.clear();
		getView().tableDescFamilia.clear();

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
				itemEspecial.setRegistro(getModel().getRegistroEmpty(getClassRegistro()));
				resutlado = itemEspecial.iniciar();

				if (!resutlado.equals("")) {

					Object registro = itemEspecial.getRegistro();

					agregarRegistroATabla(getTableActivo(), registro, itemEspecial.getNombreItem(), itemEspecial.getDescripcionItem(), itemEspecial.getUnidadItem());

					getTableActivo().setSelectedRow(getTableActivo().getRowCount() - 1);

					HibernateGeneric.persist(registro);

				}

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al obtener registro");
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.MODIFICAR.toString())) {
			String resutlado = "";
			if ((getTableActivo().getRowCount() > 0) && (getTableActivo().getSelectedRow() >= 0)) {
				try {
					int row = getTableActivo().getSelectedRow();
					itemEspecial.setRegistro(getModel().getRegistroPedidoEspecial(getTableActivo(), row));
					resutlado = itemEspecial.iniciar();

					if (!resutlado.equals("")) {

						Object registro = itemEspecial.getRegistro();

						modificarRegistroATabla(getTableActivo(), registro, row);

						HibernateGeneric.persist(registro);
					}

				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al actualizar registro");
				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.ELIMINAR.toString())) {
			if (getTableActivo().getSelectedRow() >= 0) {
				if (WebOptionPane.showConfirmDialog(getView(), "¿Borramos el registro?", "Eliminacion de registro", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == WebOptionPane.YES_OPTION) {

					DefaultTableModel dm = getModelActivo();
					dm.removeRow(getTableActivo().getSelectedRow());
				}
			}
		}
	}

	private void modificarRegistroATabla(RPTable tableActivo, Object registro, int row) {

		if (registro instanceof PreciosEspeciales) {
			PreciosEspeciales registroPedido = (PreciosEspeciales) registro;

			tableActivo.setValueAt(registroPedido.getPricDescuento1() != null ? Common.double2String(registroPedido.getPricDescuento1().doubleValue()) : "", row,
					CargaPrecioView.COL_1DESC_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricDescuento2() != null ? Common.double2String(registroPedido.getPricDescuento2().doubleValue()) : "", row,
					CargaPrecioView.COL_2DESC_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricMoneda(), row, CargaPrecioView.COL_MONEDA_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricPrecio() != null ? Common.double2String(registroPedido.getPricPrecio().doubleValue()) : "", row,
					CargaPrecioView.COL_PRECIO_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricFechaDesde() != null ? FechaManagerUtil.Date2String(registroPedido.getPricFechaDesde()) : "", row,
					CargaPrecioView.COL_DESDE_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricFechaHasta() != null ? FechaManagerUtil.Date2String(registroPedido.getPricFechaHasta()) : "", row,
					CargaPrecioView.COL_HASTA_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricReferencia(), row, CargaPrecioView.COL_REFERENCIA_ESPECIFICO);
			tableActivo.setValueAt(registroPedido, row, CargaPrecioView.COL_REGISTRO_ESPECIFICO);
		} else {
			DescuentoXFamilias registroFamilia = (DescuentoXFamilias) registro;

//			tableActivo.setValueAt(registroFamilia.getPricFamiliaDescuento1() != null ? Common.double2String(registroPedido.getPricDescuento1().doubleValue()) : "", row,
//					CargaPrecioView.COL_1DESC_ESPECIFICO);
			tableActivo.setValueAt(registroFamilia.getPricFamiliaDescuento2()!= null ? Common.double2String(registroFamilia.getPricFamiliaDescuento2().doubleValue()) : "", row,
					CargaPrecioView.COL_2DESC_FAMILIA);
			tableActivo.setValueAt(registroFamilia.getPricFamiliaFechaDesde() != null ? FechaManagerUtil.Date2String(registroFamilia.getPricFamiliaFechaDesde()) : "", row,
					CargaPrecioView.COL_DESDE_FAMIIA);
			tableActivo.setValueAt(registroFamilia.getPricFamiliaFechaHasta() != null ? FechaManagerUtil.Date2String(registroFamilia.getPricFamiliaFechaHasta()) : "", row,
					CargaPrecioView.COL_HASTA_FAMILIA);
			tableActivo.setValueAt(registroFamilia.getPricReferencia(), row, CargaPrecioView.COL_REFERENCIA_FAMILIA);
			tableActivo.setValueAt(registroFamilia, row, CargaPrecioView.COL_REGISTRO_FAMILIA);

		}

	}

	private void agregarRegistroATabla(RPTable tabla, Object registro, String nombreItem, String descItem, String unidadItem) {

		if (registro instanceof PreciosEspeciales) {

			PreciosEspeciales registroPedido = (PreciosEspeciales) registro;
			tabla.addRow(new Object[] { registroPedido.getPricArticulo(), nombreItem, descItem, unidadItem,
					registroPedido.getPricDescuento1() != null ? Common.double2String(registroPedido.getPricDescuento1().doubleValue()) : "",
					registroPedido.getPricDescuento2() != null ? Common.double2String(registroPedido.getPricDescuento2().doubleValue()) : "", registroPedido.getPricMoneda(),
					registroPedido.getPricPrecio() != null ? Common.double2String(registroPedido.getPricPrecio().doubleValue()) : "",
					registroPedido.getPricFechaDesde() != null ? FechaManagerUtil.Date2String(registroPedido.getPricFechaDesde()) : "",
					registroPedido.getPricFechaHasta() != null ? FechaManagerUtil.Date2String(registroPedido.getPricFechaHasta()) : "", registroPedido.getPricReferencia(),
					registroPedido });
		} else {
			// TODO FALTA
		}

	}

	private Class<?> getClassRegistro() {
		if (getView().tabPanel.getSelectedIndex() == 0) {
			return DescuentoXFamilias.class;
		} else {
			return PreciosEspeciales.class;
		}
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
