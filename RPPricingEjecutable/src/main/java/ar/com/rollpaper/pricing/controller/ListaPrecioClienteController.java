package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentArpv;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.VentArpvDAO;
import ar.com.rollpaper.pricing.model.ListaPrecioClienteModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.ListaPrecioClienteView;
import ar.com.rp.rpcutils.CSVExport;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class ListaPrecioClienteController extends BaseControllerMVC<PantPrincipalController, ListaPrecioClienteView, ListaPrecioClienteModel> {

	private TableRowSorter<TableModel> sorterTablaResultado;

	public ListaPrecioClienteController(PantPrincipalController pantPrincipal, ListaPrecioClienteView view, ListaPrecioClienteModel model) throws Exception {
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

	protected void perdioFocoCliente(int id) throws Exception {
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
	}

	private void cargarLista() throws Exception {
		getView().cbNroLista.removeAllItems();
		for (VentLipv lista : getModel().getListasToShow()) {
			getView().cbNroLista.addItem(lista);
		}

		setModoPantalla();

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

	private void setModoPantalla() {
		Boolean tieneCli = !getView().lblNombreCliente.getText().equals("S/D");

		getView().txtNroCliente.setEnabled(!tieneCli);
		getView().cbNroLista.setEnabled(tieneCli);
		getView().tableResultado.setEnabled(tieneCli);
		getView().btnExportarExcel.setEnabled(tieneCli);
		getView().btnGenerarPDF.setEnabled(tieneCli);
		getView().btnCancelar.setEnabled(tieneCli);
	}

	protected void resetearDatosDePantalla() throws Exception {
		if (getModel().getClienteCargado() == null) {
			getView().txtNroCliente.clear();

			getView().lblNombreLista.setText("S/D");
			getView().lblNombreLegal.setText("S/D");
			getView().lblNombreCliente.setText("S/D");

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

	private void cargarProductos() {
		resetearTabla();

		// TODO ACA
		// Codigo Articulo", "Nombre", "Descripcion", "Unidad", "moneda""Precio Venta"
		// };
		for (VentArpv venta : VentArpvDAO.findByListaID(getModel().getListaCargada().getLipvListaPrecvta())) {
			StocArts stock = StocArtsDAO.getArticuloByID(venta.getId().getArpvArticulo());
			getView().tableResultado.addRow(new Object[] { stock.getArtsArticuloEmp(), stock.getArtsNombre(), stock.getArtsDescripcion(), stock.getArtsUnimedDim(),
					venta.getSistMoneByArpvMoneda().getMoneNombre(), Common.double2String(venta.getArpvPrecioVta().doubleValue()) });
		}

		sorterTablaResultado.sort();

		// TODO falta la tabla [VENT_ARPC]
		setModoPantalla();
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
//				String[] header = { "Nro Cliente", "Nombre del Cliente", "Nombre de Fantasia", "Nro Lista", "Nombre Lista", "Nro Cliente Hijo", "Nombre del Cliente Hijo",
//						"Nombre de Fantasia" };
//				String[][] data = { {} };
//
//				RPTable tableParaExportar = new RPTable();
//				tableParaExportar.setModel(new DefaultTableModel(data, header));
//
//				DefaultTableModel dm = (DefaultTableModel) getView().tableEsclavo.getModel();
//
//				Integer nroCliente = getModel().getCliente().getClieCliente();
//				String nombreCliente = getModel().getCliente().getClieNombre();
//				String fantasiaClietne = getModel().getCliente().getClieNombreLegal();
//				Integer nroLista = getModel().getListaCliente().getLipvListaPrecvta();
//				String nombreLista = getModel().getListaCliente().getLipvNombre();
//
//				tableParaExportar.clear();
//				for (int i = 0; i < dm.getRowCount(); i++) {
//					tableParaExportar.addRow(
//							new Object[] { nroCliente, nombreCliente, fantasiaClietne, nroLista, nombreLista, dm.getValueAt(i, 0), dm.getValueAt(i, 1), dm.getValueAt(i, 2) });
//				}

				String nombreArchivo = String.format("ListaPrecioCliente%s_%s", getModel().getClienteCargado().getClieCliente(),
						FechaManagerUtil.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), "yyyyMMdd_HHmmss"));

				CSVExport.exportToExcel(getView().tableResultado, nombreArchivo);

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al exportar");
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
}