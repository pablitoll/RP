package ar.com.rollpaper.pricing.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ar.com.rollpaper.pricing.business.CommonPricing;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rollpaper.pricing.jasper.ProductoDTO;
import ar.com.rollpaper.pricing.jasper.Reportes;
import ar.com.rollpaper.pricing.model.ListaPrecioXListaModel;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.ListaPrecioClienteView;
import ar.com.rollpaper.pricing.view.ListaPrecioXListaView;
import ar.com.rp.rpcutils.CSVExport;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class ListaPrecioXListaController
		extends BaseControllerMVC<PantPrincipalController, ListaPrecioXListaView, ListaPrecioXListaModel> {

	private TableRowSorter<TableModel> sorterTablaResultado;

	public ListaPrecioXListaController(PantPrincipalController pantPrincipal, ListaPrecioXListaView view,
			ListaPrecioXListaModel model) throws Exception {
		super(pantPrincipal, view, model, null);

		view.cbNroLista.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				perdioFocoNroLista();
			}
		});

		cargarLista();
	}

	@SuppressWarnings("unchecked")
	private void cargarLista() throws Exception {
		getView().cbNroLista.removeAllItems();
		for (ListaDTO lista : getModel().getListasToShow()) {
			getView().cbNroLista.addItem(lista);
		}
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
		Boolean tieneCli = !getView().lblNombreLista.getText().equals("S/D");

		getView().cbNroLista.setEnabled(tieneCli);
		getView().tableResultado.setEnabled(tieneCli);
		getView().btnExportarExcel.setEnabled(tieneCli);
		getView().btnGenerarPDF.setEnabled(tieneCli);
	}

	protected void resetearDatosDePantalla() throws Exception {
		if (getModel().getListaCargada() == null) {
			getView().lblNombreLista.setText("S/D");
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
		return "Lista de Precio de Listas";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		resetearDatosDePantalla();
	}

	@Override
	public void ejecutarAccion(String accion) {

		if (accion.equals(ConstantesRP.PantListaPrecioXLista.GENERAR_EXCEL.toString())) {
			try {
				String nombreArchivo = String.format("ListaPrecioGenerales_%s_%s",
						getModel().getListaCargada().getVentLipv().getLipvListaPrecvta(),
						FechaManagerUtil.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), "yyyyMMdd_HHmmss"));

				CSVExport.exportToExcel(getView().tableResultado, nombreArchivo, null);

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al exportar");
			}
		}

		if (accion.equals(ConstantesRP.PantListaPrecioXLista.GENERAR_PDF.toString())) {
			try {
				Reportes.getReporteListaPreciosOriginales(getModel().getListaArticulosImpactados());
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al generar Reprote");
			}
		}

		if (accion.equals(ConstantesRP.PantListaPrecioXLista.RECARGAR.toString())) {
			perdioFocoNroLista();
		}
	}

	private void cargarProductos() {
		resetearTabla();

		for (ProductoDTO stock : getModel().getListaArticulosImpactados().getListaProductos()) {
			getView().tableResultado.addRow(new Object[] { stock.getFamiliaCod(), stock.getCodArticulo(),
					stock.getDescArticulo(), stock.getUnidadArticulo(), stock.getMonedaArticulo(),
					CommonPricing.formatearImporte(stock.getPrecioArticulo()) });
		}

		sorterTablaResultado.sort();

		getView().tableResultado.adjustColumns();
		getView().tableResultado.getColumnModel().getColumn(ListaPrecioXListaView.COL_DESC).setPreferredWidth(600);
		getView().tableResultado.getColumnModel().getColumn(ListaPrecioXListaView.COL_DESC).setMinWidth(600);
		getView().tableResultado.getColumnModel().getColumn(ListaPrecioXListaView.COL_DESC).setWidth(300);

		setModoPantalla();
	}

	@Override
	protected void cerrarPantalla() {
		super.cerrarPantalla();
	}
}
