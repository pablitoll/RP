package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.SistUnim;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.StocCa01;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dao.SistMoneDAO;
import ar.com.rollpaper.pricing.dao.SistUnimDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.StocCa01DAO;
import ar.com.rollpaper.pricing.business.GeneradorDePrecios;
import ar.com.rollpaper.pricing.model.CargaItemEspecialArticuloModel;
import ar.com.rollpaper.pricing.model.CargaItemEspecialFamiliaModel;
import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.BuscarListaDialog;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaItemEspecialView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaPrecioController
		extends BaseControllerMVC<PantPrincipalController, CargaPrecioView, CargaPrecioModel> {

	private CargaItemEspecialArticuloModel itemEspecialArticuloModel = new CargaItemEspecialArticuloModel();
	private CargaItemEspecialView itemEspecialArticuloView = new CargaItemEspecialView();
	private CargaItemEspecialArticulo itemEspecialArticulo = new CargaItemEspecialArticulo(
			PantPrincipalController.getPantallaPrincipal(), itemEspecialArticuloView, itemEspecialArticuloModel, null);

	private CargaItemEspecialFamiliaModel itemEspecialFamiliaModel = new CargaItemEspecialFamiliaModel();
	private CargaItemEspecialView itemEspecialFamiliaView = new CargaItemEspecialView();
	private CargaItemEspecialFamilia itemEspecialFamilia = new CargaItemEspecialFamilia(
			PantPrincipalController.getPantallaPrincipal(), itemEspecialFamiliaView, itemEspecialFamiliaModel, null);

	private TableRowSorter<TableModel> sorterTablaDesEspecifico;
	private TableRowSorter<TableModel> sorterTablaDesFamilia;

	public CargaPrecioController(PantPrincipalController pantPrincipal, CargaPrecioView view, CargaPrecioModel model)
			throws Exception {
		super(pantPrincipal, view, model, null);

		view.txtNroCliente.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {

					if (!getView().txtNroCliente.getText().equals("")) {
						String id = getView().txtNroCliente.getText();

						if (CommonUtils.isNumeric(id)) {
							perdioFocoCliente(Integer.valueOf(id));
						}
					}

				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error al buscar cliente");
				}
			}
		});

		view.cbNroLista.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				perdioFocoNroLista();
			}
		});
	}

	protected void perdioFocoNroLista() {
		if (getView().cbNroLista.getSelectedIndex() > -1) {
			VentLipv lista = (VentLipv) getView().cbNroLista.getSelectedItem();
			if (lista != null) {
				getView().lblNombreLista.setText(lista.getLipvNombre());
				getModel().setListaCargada(lista);
				cargarProductos();
			}
		}
	}

	private void cargarProductos() {
		resetearTablaFamilia();
		resetearTablaEspecifico();

		for (DescuentoXFamilias familia : DescuentoXFamiliasDAO.getListaDescuentoByID(
				getModel().getClienteCargado().getClieCliente(), getModel().getListaCargada().getLipvListaPrecvta())) {
			StocCa01 familiaClass = StocCa01DAO.findById(familia.getPricCa01Clasif1());
			agregarRegistroATablaFamilia(getView().tableDescFamilia, familia, familiaClass.getCa01Nombre());
		}
		sorterTablaDesFamilia.sort();
		for (PreciosEspeciales desc : PreciosEspecialesDAO.getListaPrecioEspeciaByID(
				getModel().getClienteCargado().getClieCliente(), getModel().getListaCargada().getLipvListaPrecvta())) {
			StocArts arti = StocArtsDAO.findById(desc.getPricArticulo());
			SistUnim unidad = SistUnimDAO.findById(arti.getArtsUnimedStock());
			agregarRegistroATablaArticulo(getView().tableDescEspecifico, desc, arti.getArtsArticuloEmp(),
					arti.getArtsNombre(), arti.getArtsDescripcion(), unidad.getUnimNombre());
		}
		sorterTablaDesEspecifico.sort();

		setModoPantalla();
	}

	protected void perdioFocoCliente(int id) throws Exception {
		PantPrincipalController.setCursorOcupado();
		try {
			CcobClie cliente = CcobClieDAO.findById(Integer.valueOf(id));

			if (cliente != null) {
				getView().lblNombreCliente.setText(cliente.getClieNombre());
				getView().lblNombreLegal.setText(cliente.getClieNombreLegal());
				getModel().setClienteCargado(cliente);
				cargarLista();
				setModoPantalla();

			} else {
				resetearDatosDePantalla();
			}
		} finally {
			PantPrincipalController.setRestoreCursor();
		}
	}

	private void cargarLista() throws Exception {
		getView().cbNroLista.removeAllItems();
		for (VentLipv lista : getModel().getListasToShow()) {
			getView().cbNroLista.addItem(lista);
		}

		setModoPantalla();

	}

	private void setModoPantalla() {
		Boolean tieneCli = !getView().lblNombreCliente.getText().equals("S/D");

		Boolean isListaHeredada = false;
		Boolean habilitaEliminar = tieneCli;

		if (getModel().getListaCargada() != null) {
			isListaHeredada = getModel().getListaCargada().isListaHeredada();
			habilitaEliminar = !getModel().getListaCargada().isListaPrincipal() && !isListaHeredada;
		}
		getView().txtNroCliente.setEnabled(!tieneCli);
		getView().cbNroLista.setEnabled(tieneCli);
		getView().tableDescEspecifico.setEnabled(tieneCli);
		getView().tableDescFamilia.setEnabled(tieneCli);
		getView().btnAgregar.setEnabled(tieneCli && !isListaHeredada);
		getView().btnModificar.setEnabled(tieneCli && !isListaHeredada);
		getView().btnEliminar.setEnabled(tieneCli && !isListaHeredada);

		getView().btnEliminarLista.setEnabled(habilitaEliminar);
		getView().btnAgregarLista.setEnabled(tieneCli);

		getView().btnCancelar.setVisible(tieneCli);
		getView().setCerrarVisible(!tieneCli);
		getView().btnImpactarPrecios.setVisible(tieneCli);
	}

	@Override
	protected String getNombrePantalla() {
		return "Carga de Precio por Cliente";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		resetearDatosDePantalla();
	}

	protected void resetearDatosDePantalla() throws Exception {
		if (getModel().getClienteCargado() == null) {
			getView().txtNroCliente.clear();

			getView().lblNombreLista.setText("S/D");
			getView().lblNombreLegal.setText("S/D");
			getView().lblNombreCliente.setText("S/D");

			getModel().setClienteCargado(null);
			getView().cbNroLista.removeAllItems();
			getView().lblError.setText("");

			resetearTablaEspecifico();
			resetearTablaFamilia();
		}

		setModoPantalla();
	}

	private void resetearTablaFamilia() {
		getView().tableDescFamilia.clear();
		// Le seteo el order
		sorterTablaDesFamilia = new TableRowSorter<TableModel>(getView().tableDescFamilia.getModel());
		getView().tableDescFamilia.setRowSorter(sorterTablaDesFamilia);
		ArrayList<RowSorter.SortKey> sortKeysFamilia = new ArrayList<RowSorter.SortKey>();

		sortKeysFamilia.add(new RowSorter.SortKey(CargaPrecioView.COL_NOMBRE_FAMILIA, SortOrder.ASCENDING));
		sortKeysFamilia.add(new RowSorter.SortKey(CargaPrecioView.COL_DESDE_FAMILIA, SortOrder.ASCENDING));

		sorterTablaDesFamilia.setSortKeys(sortKeysFamilia);

	}

	private void resetearTablaEspecifico() {
		getView().tableDescEspecifico.clear();

		// Le seteo el order
		sorterTablaDesEspecifico = new TableRowSorter<TableModel>(getView().tableDescEspecifico.getModel());
		getView().tableDescEspecifico.setRowSorter(sorterTablaDesEspecifico);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();

		sortKeys.add(new RowSorter.SortKey(CargaPrecioView.COL_NOMBRE_ESPECIFICO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(CargaPrecioView.COL_DESDE_ESPECIFICO, SortOrder.ASCENDING));

		sorterTablaDesEspecifico.setComparator(CargaPrecioView.COL_DESDE_ESPECIFICO, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				Date date1 = FechaManagerUtil.String2Date(s1);
				Date date2 = FechaManagerUtil.String2Date(s2);

				return (int) FechaManagerUtil.getDateDiff(date2, date1, TimeUnit.SECONDS);
			}
		});

		sorterTablaDesEspecifico.setSortKeys(sortKeys);
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_ENTER) && getView().txtNroCliente.hasFocus()) {
			try {
				retorno = true;
				String id = getView().txtNroCliente.getText();
				if (!id.equals("") && CommonUtils.isNumeric(id)) {
					perdioFocoCliente(Integer.valueOf(id));
				}

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
			}
		}

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F2) && getView().txtNroCliente.hasFocus()) {
			retorno = true;
			try {
				buscarCliente();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
			}
		}

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F2) && getView().cbNroLista.hasFocus()) {
			retorno = true;
			try {
				agregarLista();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al cargar la busqueda de Cliente");
			}
		}
		return retorno;
	}

	private void agregarLista() throws Exception {
		BuscarListaDialog buscarListaDialog = new BuscarListaDialog(getPantallaPrincipal(),
				getView().cbNroLista.getModel());
		buscarListaDialog.iniciar();
		if (buscarListaDialog.getNroLista() != null) {
			VentLipv lista = buscarListaDialog.getNroLista();
			getView().cbNroLista.addItem(lista);
			getView().cbNroLista.setSelectedIndex(getView().cbNroLista.getItemCount() - 1);
		}
	}

	private void buscarCliente() throws Exception {
		BuscarClienteDialog buscarClienteDialog = new BuscarClienteDialog(getPantallaPrincipal());
		buscarClienteDialog.iniciar();
		if (buscarClienteDialog.getCliente() != null) {
			CcobClie cliente = buscarClienteDialog.getCliente();
			if (cliente != null) {
				getView().txtNroCliente.setValue(cliente.getClieCliente());
				perdioFocoCliente(cliente.getClieCliente());
			}
		}
	}

	@Override
	public void ejecutarAccion(String accion) {

		if (accion.equals(ConstantesRP.PantCarPrecio.CANCELAR.toString())) {
			try {
				getModel().setClienteCargado(null); // Elimino el cliente actual y reseteo
				resetearDatosDePantalla();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al cancelar");
			}
		}
		if (accion.equals(ConstantesRP.PantCarPrecio.IMPACTAR_PRECIOS.toString())) {
			if (Dialog.showConfirmDialog(String.format("¿Quiere impactar los precios del cliente %s, para la lista %s?",
					getModel().getClienteCargado().getClieNombre(), getModel().getListaCargada().getLipvNombre()),
					"Impacto de Precios", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
					null) == JOptionPane.YES_OPTION) {

				GeneradorDePrecios.impactarPrecios(getModel().getClienteCargado(),getModel().getListaCargada());

			}

			Dialog.showMessageDialog("Se termino de aplicar nuevos precios", "Aplicacion de Precios",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.AGREGAR.toString()))

		{

			if (getModel().getListaCargada() != null) {

				String resutlado = "";
				try {
					if (isPanelActivoFamilia()) {
						itemEspecialFamilia.setRegistro(getModel().getRegistroFamilaiEmpty());
						itemEspecialFamiliaModel
								.setTableModel((DefaultTableModel) getView().tableDescFamilia.getModel());
						itemEspecialFamiliaModel.setListaID(getModel().getListaCargada());
						resutlado = itemEspecialFamilia.iniciar();

						if (!resutlado.equals("")) {

							DescuentoXFamilias registro = itemEspecialFamilia.getRegistro();

							HibernateGeneric.persist(registro);

							agregarRegistroATablaFamilia(getView().tableDescFamilia, registro,
									itemEspecialFamilia.getNombreItem());

							sorterTablaDesFamilia.sort();

							buscarRegisro(getView().tableDescFamilia, CargaPrecioView.COL_NOMBRE_FAMILIA,
									itemEspecialFamilia.getNombreItem(), CargaPrecioView.COL_DESDE_FAMILIA,
									registro.getPricFamiliaFechaDesde());

						}

					} else {
						itemEspecialArticuloModel
								.setTableModel((DefaultTableModel) getView().tableDescEspecifico.getModel());
						itemEspecialArticulo.setRegistro(getModel().getRegistroArticuloEmpty());
						resutlado = itemEspecialArticulo.iniciar();

						if (!resutlado.equals("")) {

							PreciosEspeciales registro = itemEspecialArticulo.getRegistro();

							HibernateGeneric.persist(registro);

							SistUnim unidad = SistUnimDAO.findById(itemEspecialArticulo.getUnidadItem());

							agregarRegistroATablaArticulo(getView().tableDescEspecifico, registro,
									itemEspecialArticulo.getArticuloIDMostrar(), itemEspecialArticulo.getNombreItem(),
									itemEspecialArticulo.getDescripcionItem(), unidad.getUnimNombre());

							sorterTablaDesEspecifico.sort();

							buscarRegisro(getView().tableDescEspecifico, CargaPrecioView.COL_NOMBRE_ESPECIFICO,
									itemEspecialArticulo.getNombreItem(), CargaPrecioView.COL_DESDE_ESPECIFICO,
									registro.getPricFechaDesde());

						}
					}
				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al obtener registro");
				}
			} else {
				Dialog.showMessageDialog("No se puede agregar Item hasta que se seleccione una lista");
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.MODIFICAR.toString())) {
			String resutlado = "";
			try {
				if (isPanelActivoFamilia()) {
					if ((getView().tableDescFamilia.getRowCount() > 0)
							&& (getView().tableDescFamilia.getSelectedRow() >= 0)) {
						int row = getView().tableDescFamilia.getSelectedRow();
						itemEspecialFamilia.setRegistro((DescuentoXFamilias) getView().tableDescFamilia.getValueAt(row,
								CargaPrecioView.COL_REGISTRO_FAMILIA));
						itemEspecialFamiliaModel
								.setTableModel((DefaultTableModel) getView().tableDescFamilia.getModel());
						itemEspecialFamiliaModel.setListaID(getModel().getListaCargada());
						resutlado = itemEspecialFamilia.iniciar();

						if (!resutlado.equals("")) {

							DescuentoXFamilias registro = itemEspecialFamilia.getRegistro();

							//HibernateGeneric.persist(registro);
							HibernateGeneric.attachDirty(registro);
							
							modificarRegistroATabla(getView().tableDescFamilia, registro, row);

							sorterTablaDesFamilia.sort();

							buscarRegisro(getView().tableDescFamilia, CargaPrecioView.COL_NOMBRE_FAMILIA,
									itemEspecialFamilia.getNombreItem(), CargaPrecioView.COL_DESDE_FAMILIA,
									registro.getPricFamiliaFechaDesde());

						}
					}

				} else {
					if ((getView().tableDescEspecifico.getRowCount() > 0)
							&& (getView().tableDescEspecifico.getSelectedRow() >= 0)) {

						int row = getView().tableDescEspecifico.getSelectedRow();
						itemEspecialArticulo.setRegistro((PreciosEspeciales) getView().tableDescEspecifico
								.getValueAt(row, CargaPrecioView.COL_REGISTRO_ESPECIFICO));
						itemEspecialArticuloModel
								.setTableModel((DefaultTableModel) getView().tableDescEspecifico.getModel());

						resutlado = itemEspecialArticulo.iniciar();

						if (!resutlado.equals("")) {

							PreciosEspeciales registro = itemEspecialArticulo.getRegistro();

							// HibernateGeneric.persist(registro);
							PreciosEspecialesDAO.merge(registro);
							modificarRegistroATabla(getView().tableDescEspecifico, registro, row);

							sorterTablaDesEspecifico.sort();

							buscarRegisro(getView().tableDescEspecifico, CargaPrecioView.COL_NOMBRE_ESPECIFICO,
									itemEspecialArticulo.getNombreItem(), CargaPrecioView.COL_DESDE_ESPECIFICO,
									registro.getPricFechaDesde());
						}

					}
				}
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al actualizar registro");
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.ELIMINAR.toString())) {
			RPTable tabla;
			int col_registro;
			if (isPanelActivoFamilia()) {
				tabla = getView().tableDescFamilia;
				col_registro = CargaPrecioView.COL_REGISTRO_FAMILIA;
			} else {
				tabla = getView().tableDescEspecifico;
				col_registro = CargaPrecioView.COL_REGISTRO_ESPECIFICO;
			}

			if (tabla.getSelectedRow() >= 0) {
				if (WebOptionPane.showConfirmDialog(getView(), "¿Borramos el registro?", "Eliminacion de registro",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == WebOptionPane.YES_OPTION) {

					int row = tabla.getSelectedRow();
					Object regis = tabla.getValueAt(row, col_registro);
					HibernateGeneric.remove(regis);
					DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
					dm.removeRow(tabla.getSelectedRow());
				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.AGREGAR_LISTA.toString()))

		{
			try {
				agregarLista();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al agregar lista");
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.ELIMINAR_LISTA.toString())) {
			if (WebOptionPane.showConfirmDialog(getView(), "¿Eliminamos la lista?", "Eliminacion de Lista",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == WebOptionPane.YES_OPTION) {

				for (int i = 0; i < getView().tableDescFamilia.getRowCount(); i++) {
					Object regis = getView().tableDescFamilia.getValueAt(i, CargaPrecioView.COL_REGISTRO_FAMILIA);
					HibernateGeneric.remove(regis);
				}

				for (int i = 0; i < getView().tableDescEspecifico.getRowCount(); i++) {
					Object regis = getView().tableDescEspecifico.getValueAt(i, CargaPrecioView.COL_REGISTRO_ESPECIFICO);
					HibernateGeneric.remove(regis);
				}

				try {
					perdioFocoCliente(getModel().getClienteCargado().getClieCliente());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	/**
	 * 
	 */
	


	private void buscarRegisro(RPTable tableActivo, int col_nombre, String nombre, int col_desde, Date fechaDesde) {
		String strDesde = FechaManagerUtil.Date2String(fechaDesde);

		for (int i = 0; i < tableActivo.getRowCount(); i++) {
			if ((tableActivo.getValueAt(i, col_nombre).equals(nombre))
					&& tableActivo.getValueAt(i, col_desde).equals(strDesde)) {
				tableActivo.setSelectedRow(i);
				i = tableActivo.getRowCount() + 2;
			}
		}
	}

	private void modificarRegistroATabla(RPTable tableActivo, Object registro, int row) {

		if (registro instanceof PreciosEspeciales) {
			PreciosEspeciales registroPedido = (PreciosEspeciales) registro;

			String descMoneda = "";
			if (registroPedido.getPricMoneda() != null) {
				descMoneda = SistMoneDAO.findById(registroPedido.getPricMoneda()).getMoneNombre();
			}

			tableActivo.setValueAt(registroPedido.getPricDescuento1() != null
					? Common.double2String(registroPedido.getPricDescuento1().doubleValue())
					: "", row, CargaPrecioView.COL_1DESC_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricDescuento2() != null
					? Common.double2String(registroPedido.getPricDescuento2().doubleValue())
					: "", row, CargaPrecioView.COL_2DESC_ESPECIFICO);
			tableActivo.setValueAt(descMoneda, row, CargaPrecioView.COL_MONEDA_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricPrecio() != null
					? Common.double2String(registroPedido.getPricPrecio().doubleValue())
					: "", row, CargaPrecioView.COL_PRECIO_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricFechaDesde() != null
					? FechaManagerUtil.Date2String(registroPedido.getPricFechaDesde())
					: "", row, CargaPrecioView.COL_DESDE_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricFechaHasta() != null
					? FechaManagerUtil.Date2String(registroPedido.getPricFechaHasta())
					: "", row, CargaPrecioView.COL_HASTA_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricComision() != null
					? Common.double2String(registroPedido.getPricComision().doubleValue())
					: "", row, CargaPrecioView.COL_COMISION_ESPECIFICO);
			tableActivo.setValueAt(registroPedido.getPricReferencia(), row, CargaPrecioView.COL_REFERENCIA_ESPECIFICO);
			// tableActivo.getModel().setValueAt(registroPedido, row,
			// CargaPrecioView.COL_REGISTRO_ESPECIFICO);
		} else {
			DescuentoXFamilias registroFamilia = (DescuentoXFamilias) registro;

			tableActivo.setValueAt(registroFamilia.getPricFamiliaDescuento1() != null
					? Common.double2String(registroFamilia.getPricFamiliaDescuento1().doubleValue())
					: "", row, CargaPrecioView.COL_1DESC_FAMILIA);
			tableActivo.setValueAt(registroFamilia.getPricFamiliaDescuento2() != null
					? Common.double2String(registroFamilia.getPricFamiliaDescuento2().doubleValue())
					: "", row, CargaPrecioView.COL_2DESC_FAMILIA);
			tableActivo.setValueAt(registroFamilia.getPricFamiliaFechaDesde() != null
					? FechaManagerUtil.Date2String(registroFamilia.getPricFamiliaFechaDesde())
					: "", row, CargaPrecioView.COL_DESDE_FAMILIA);
			tableActivo.setValueAt(registroFamilia.getPricFamiliaFechaHasta() != null
					? FechaManagerUtil.Date2String(registroFamilia.getPricFamiliaFechaHasta())
					: "", row, CargaPrecioView.COL_HASTA_FAMILIA);
			tableActivo.setValueAt(registroFamilia.getPricFamiliaComision() != null
					? Common.double2String(registroFamilia.getPricFamiliaComision().doubleValue())
					: "", row, CargaPrecioView.COL_COMSISION_FAMILIA);
			tableActivo.setValueAt(registroFamilia.getPricReferencia(), row, CargaPrecioView.COL_REFERENCIA_FAMILIA);
			// tableActivo.getModel().setValueAt(registroFamilia, row,
			// CargaPrecioView.COL_REGISTRO_FAMILIA);
		}
	}

	private void agregarRegistroATablaArticulo(RPTable tabla, PreciosEspeciales registro, String id_Articulo,
			String nombreItem, String descItem, String unidadItem) {

		tabla.addRow(new Object[] { id_Articulo, nombreItem, descItem, unidadItem,
				registro.getPricDescuento1() != null ? Common.double2String(registro.getPricDescuento1().doubleValue())
						: "",
				registro.getPricDescuento2() != null ? Common.double2String(registro.getPricDescuento2().doubleValue())
						: "",
				registro.getPricMoneda() != null ? SistMoneDAO.findById(registro.getPricMoneda()).getMoneNombre() : "",
				registro.getPricPrecio() != null ? Common.double2String(registro.getPricPrecio().doubleValue()) : "",
				registro.getPricFechaDesde() != null ? FechaManagerUtil.Date2String(registro.getPricFechaDesde()) : "",
				registro.getPricFechaHasta() != null ? FechaManagerUtil.Date2String(registro.getPricFechaHasta()) : "",
				Common.double2String(registro.getPricComision().doubleValue()), registro.getPricReferencia(),
				registro });
	}

	private void agregarRegistroATablaFamilia(RPTable tabla, DescuentoXFamilias registro, String nombreItem) {
		tabla.addRow(new Object[] { registro.getPricCa01Clasif1(), nombreItem,
				registro.getPricFamiliaDescuento1() != null
						? Common.double2String(registro.getPricFamiliaDescuento1().doubleValue())
						: "",
				registro.getPricFamiliaDescuento2() != null
						? Common.double2String(registro.getPricFamiliaDescuento2().doubleValue())
						: "",
				registro.getPricFamiliaFechaDesde() != null
						? FechaManagerUtil.Date2String(registro.getPricFamiliaFechaDesde())
						: "",
				registro.getPricFamiliaFechaHasta() != null
						? FechaManagerUtil.Date2String(registro.getPricFamiliaFechaHasta())
						: "",
				Common.double2String(registro.getPricFamiliaComision().doubleValue()), registro.getPricReferencia(),
				registro });
	}

	private boolean isPanelActivoFamilia() {
		return (getView().tabPanel.getSelectedIndex() == 0);
	}

}
