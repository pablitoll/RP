package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.business.CommonPricing;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.TableAnchoManager;
import ar.com.rollpaper.pricing.business.TableColumnHider;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rollpaper.pricing.jasper.ProductoDTO;
import ar.com.rollpaper.pricing.jasper.Reportes;
import ar.com.rollpaper.pricing.model.ListaPrecioClienteModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.ListaPrecioClienteView;
import ar.com.rp.rpcutils.CSVExport;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class ListaPrecioClienteController
		extends BaseControllerMVC<PantPrincipalController, ListaPrecioClienteView, ListaPrecioClienteModel> {

	private TableRowSorter<TableModel> sorterTablaResultado;
	private TableColumnHider hider;

	public ListaPrecioClienteController(PantPrincipalController pantPrincipal, ListaPrecioClienteView view,
			ListaPrecioClienteModel model) throws Exception {
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

		view.chkArticuloEspecifico.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (getModel().getClienteCargado() != null) {
					cargarProductos();
				}
			}
		});

		view.chkArticuloLista.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (getModel().getClienteCargado() != null) {
					cargarProductos();
				}
			}
		});

		view.chkFechaVigencia.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				evaluarColDinamica(ListaPrecioClienteView.COL_DES_FECHA_VIGENCIA, view.chkFechaVigencia.isSelected());
			}
		});

		view.chkComision.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				evaluarColDinamica(ListaPrecioClienteView.COL_DES_COMISION, view.chkComision.isSelected());
			}
		});

		view.chkReferencia.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				evaluarColDinamica(ListaPrecioClienteView.COL_DES_REFERENCIA, view.chkReferencia.isSelected());
			}
		});

		view.chkDtoAplicados.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				evaluarColDinamica(ListaPrecioClienteView.COL_DES_DESCUENTOS, view.chkDtoAplicados.isSelected());
			}
		});

		view.txtBusqueda.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent evento) {
				if (getModel().getClienteCargado() != null) {
					cargarProductos();
				}
			}
		});

		view.txtBusqueda.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (getModel().getClienteCargado() != null) {
						cargarProductos();
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		view.chkBusquedaCodFamilia.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (getModel().getClienteCargado() != null) {
					cargarProductos();
				}
			}
		});

		view.chkBusquedaCodProducto.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (getModel().getClienteCargado() != null) {
					cargarProductos();
				}
			}
		});
		view.chkBusquedaDescrip.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (getModel().getClienteCargado() != null) {
					cargarProductos();
				}
			}
		});

		hider = new TableColumnHider(view.tableResultado);

		TableAnchoManager.registrarEvento(view.tableResultado, "tablaPrecioCliente");
	}

	private void evaluarColDinamica(String nombre_col, boolean seleccionada) {
		if (seleccionada) {
			hider.show(nombre_col);
		} else {
			hider.hide(nombre_col);
		}
		ajustarAnchoCol();
	}

	protected void perdioFocoCliente(int id) throws Exception {
		PantPrincipalController.setCursorOcupado();
		try {
			CcobClie cliente = CcobClieDAO.findById(Integer.valueOf(id));

			if (cliente != null) {
				getModel().setClienteCargado(cliente);
				getView().lblNombreCliente.setText(cliente.getClieNombre());
				getView().lblNombreLegal.setText(cliente.getClieNombreLegal());

				String factor = "Sin Multiplicador";
				if (getModel().getFactor() != null) {
					factor = Common.double2String(getModel().getFactor());
				}
				getView().lblFactor.setText(factor);
				cargarLista();
				setModoPantalla();

			} else {
				resetearDatosDePantalla();
			}

		} finally {
			PantPrincipalController.setRestoreCursor();
		}
	}

	@SuppressWarnings("unchecked")
	private void cargarLista() throws Exception {
		getView().cbNroLista.removeAllItems();
		for (ListaDTO lista : getModel().getListasToShow()) {
			getView().cbNroLista.addItem(lista);
		}

		setModoPantalla();
	}

	protected void perdioFocoNroLista() {
		if (getView().cbNroLista.getSelectedIndex() > -1) {
			ListaDTO lista = (ListaDTO) getView().cbNroLista.getSelectedItem();
			if (lista != null) {
				getView().lblNombreLista.setText(lista.getVentLipv().getLipvNombre());
				getModel().setListaCargada(lista);
				cargarProductos();
			}
		}
	}

	private void setModoPantalla() {
		Boolean tieneCli = !getView().lblNombreCliente.getText().equals("S/D");
		Boolean tieneLista = getModel().getListaCargada() != null;

		getView().txtNroCliente.setEnabled(!tieneCli);
		getView().cbNroLista.setEnabled(tieneCli);
		getView().tableResultado.setEnabled(tieneCli);
		getView().btnExportarExcel.setEnabled(tieneLista);
		getView().btnGenerarPDF.setEnabled(tieneLista);
		getView().btnCancelar.setEnabled(tieneCli);
	}

	protected void resetearDatosDePantalla() throws Exception {
		if (getModel().getClienteCargado() == null) {
			getView().txtBusqueda.setText("");
			getView().chkArticuloEspecifico.setSelected(true);
			getView().chkArticuloLista.setSelected(true);

			getView().chkComision.setSelected(false);
			evaluarColDinamica(ListaPrecioClienteView.COL_DES_COMISION, false);
			getView().chkDtoAplicados.setSelected(false);
			evaluarColDinamica(ListaPrecioClienteView.COL_DES_DESCUENTOS, false);
			getView().chkFechaVigencia.setSelected(false);
			evaluarColDinamica(ListaPrecioClienteView.COL_DES_FECHA_VIGENCIA, false);
			getView().chkReferencia.setSelected(false);
			evaluarColDinamica(ListaPrecioClienteView.COL_DES_REFERENCIA, false);

			getView().txtNroCliente.clear();

			getView().lblNombreLista.setText("S/D");
			getView().lblNombreLegal.setText("S/D");
			getView().lblNombreCliente.setText("S/D");
			getView().lblFactor.setText("S/D");

			getView().chkBusquedaCodFamilia.setSelected(false);
			getView().chkBusquedaCodProducto.setSelected(true);
			getView().chkBusquedaDescrip.setSelected(true);

			getModel().setClienteCargado(null);
			getView().cbNroLista.removeAllItems();

			resetearTabla();
		}

		setModoPantalla();
	}

	private void resetearTabla() {
		getView().tableResultado.clear();

		// Le seteo el order
		sorterTablaResultado = new TableRowSorter<TableModel>(getView().tableResultado.getModel());
		getView().tableResultado.setRowSorter(sorterTablaResultado);
		ArrayList<RowSorter.SortKey> sortKeysResultado = new ArrayList<RowSorter.SortKey>();

		sortKeysResultado.add(new RowSorter.SortKey(ListaPrecioClienteView.COL_COD_ARTICULO, SortOrder.ASCENDING));

		sorterTablaResultado.setSortKeys(sortKeysResultado);

	}

	@Override
	protected String getNombrePantalla() {
		return "Lista de Precio x Cliente";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		resetearDatosDePantalla();
	}

	@Override
	public void ejecutarAccion(String accion) {
		if (accion.equals(ConstantesRP.PantListaPrecio.CANCELAR.toString())) {
			try {
				getModel().setClienteCargado(null); // Elimino el cliente actual y reseteo
				resetearDatosDePantalla();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al limpiar");
			}
		}

		if (accion.equals(ConstantesRP.PantListaPrecio.GENERAR_EXCEL.toString())) {
			try {
				String nombreArchivo = String.format("ListaPrecioCliente%s_%s",
						getModel().getClienteCargado().getClieCliente(),
						FechaManagerUtil.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), "yyyyMMdd_HHmmss"));

				Class<?> vectorClases[] = new Class[getView().tableResultado.getModel().getColumnCount()];
				vectorClases[0] = String.class;
				vectorClases[1] = String.class;

				CSVExport.exportToExcel(getView().tableResultado, nombreArchivo, vectorClases);

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al exportar");
			}
		}

		if (accion.equals(ConstantesRP.PantListaPrecio.GENERAR_PDF.toString())) {
			try {
				Boolean exportTodos = true;

				if (getView().tableResultado.getSelectedRowCount() > 0) {
					String[] op = { "Todos", "Solo Selección" };
					exportTodos = Dialog.showConfirmDialogObject(
							"¿Exportamos Todos los Registros o Solo los Seleccionados?", "Exportación",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, op[0]) == op[0];
				}

				if (exportTodos) {
					Reportes.getReporteListaPrecios(getModel().getListProductosReporte(
							getView().chkArticuloLista.isSelected(), getView().chkArticuloEspecifico.isSelected(),
							getView().txtBusqueda.getText(), getView().chkBusquedaCodFamilia.isSelected(),
							getView().chkBusquedaCodProducto.isSelected(), getView().chkBusquedaDescrip.isSelected()));
				} else {
					Reportes.getReporteListaPrecios(getModel().getListProductosReporte(getView().tableResultado));
				}

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al generar Reprote");
			}
		}

		if (accion.equals(ConstantesRP.PantListaPrecio.RECARGAR.toString())) {
			try {
				recargar();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al Recargar Pantalla");
			}
		}

	}

	private void recargar() throws Exception {
		ListaDTO listaActual = getModel().getListaCargada();
		CcobClie clienteCargado = getModel().getClienteCargado();
		getModel().setClienteCargado(null);

		if (clienteCargado != null) {
			getModel().setClienteCargado(clienteCargado);
			getView().txtNroCliente.setText(String.valueOf(clienteCargado.getClieCliente()));
			perdioFocoCliente(clienteCargado.getClieCliente());

			for (int i = 0; i < getView().cbNroLista.getModel().getSize(); i++) {
				if (((ListaDTO) getView().cbNroLista.getModel().getElementAt(i)).equals(listaActual)) {
					getView().cbNroLista.setSelectedIndex(i);
				}
			}
		}
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

		return retorno;
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

	private void cargarProductos() {
		resetearTabla();

		for (ProductoDTO stock : getModel().getListProductosReporte(getView().chkArticuloLista.isSelected(),
				getView().chkArticuloEspecifico.isSelected(), getView().txtBusqueda.getText(),
				getView().chkBusquedaCodFamilia.isSelected(), getView().chkBusquedaCodProducto.isSelected(),
				getView().chkBusquedaDescrip.isSelected()).getListaProductos()) {

			String fechaVigencia = "";
			if (stock.getVigenciaDesde() != null) {
				fechaVigencia = String.format("%s - %s", FechaManagerUtil.Date2String(stock.getVigenciaDesde()).trim(),
						FechaManagerUtil.Date2String(stock.getVigenciaHasta()).trim());
			}

			String descuento = "";
			if (stock.getDescuento1() != null) {
				descuento = String.valueOf(CommonUtils.round(stock.getDescuento1().doubleValue(), 3)) + "%";
				if (stock.getDescuento2() != null) {
					descuento += " - " + String.valueOf(CommonUtils.round(stock.getDescuento2().doubleValue(), 3))
							+ "%";
				}
			}

			String comision = stock.getComision() != null
					? Common.double2String(CommonUtils.round(stock.getComision().doubleValue(), 3))
					: "";

			getView().tableResultado.addRow(new Object[] { stock.getFamiliaCod(), stock.getCodArticulo(),
					stock.getDescArticulo(), stock.getUnidadArticulo(), stock.getMonedaArticulo(),
					CommonPricing.formatearImporte(stock.getPrecioArticulo()), fechaVigencia, descuento, comision,
					stock.getReferencia() == null ? "" : stock.getReferencia().trim() });
		}

		sorterTablaResultado.sort();

		ajustarAnchoCol();

		setModoPantalla();

	}

	private void ajustarAnchoCol() {
		getView().tableResultado.adjustColumns();
	}

	@Override
	protected void cerrarPantalla() {
		ejecutarAccion(ConstantesRP.PantListaPrecio.CANCELAR.toString());
		super.cerrarPantalla();
	}
}
