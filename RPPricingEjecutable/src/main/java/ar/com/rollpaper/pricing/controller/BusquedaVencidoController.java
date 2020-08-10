
package ar.com.rollpaper.pricing.controller;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.SistUnim;
import ar.com.rollpaper.pricing.business.CommonPricing;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.FamiliaBusiness;
import ar.com.rollpaper.pricing.business.PreciosEspecialesBusiness;
import ar.com.rollpaper.pricing.business.TableAnchoManager;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.SistMoneDAO;
import ar.com.rollpaper.pricing.dao.SistUnimDAO;
import ar.com.rollpaper.pricing.dto.PreciosEspecialesExDTO;
import ar.com.rollpaper.pricing.model.BusquedaVencidoModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.BusquedaVencidoView;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class BusquedaVencidoController
		extends BaseControllerMVC<PantPrincipalController, BusquedaVencidoView, BusquedaVencidoModel> {

	private TableRowSorter<TableModel> sorterTablaDesEspecifico;
	private TableRowSorter<TableModel> sorterTablaDesFamilia;

	public BusquedaVencidoController(PantPrincipalController pantPrincipal, BusquedaVencidoView view,
			BusquedaVencidoModel model) throws Exception {
		super(pantPrincipal, view, model, null);

		view.txtNroCliente.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {
					perdioFocoComponenteBusqueda();
				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error al buscar cliente");
				}
			}
		});

		view.txtBusqueda.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {
					perdioFocoComponenteBusqueda();
				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error perder foco");
				}
			}
		});

		view.txtVencidosAl.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {
					if (getView().txtVencidosAl.getDate() == null) {
						Dialog.showMessageDialog("Debe haber una fecha valida en \"Vencidos Al\"",
								"Error de parametros", JOptionPane.ERROR_MESSAGE);
						getView().txtVencidosAl.requestFocus();
					} else {
						perdioFocoComponenteBusqueda();
					}
				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error perder foco");
				}
			}
		});
		view.txtVencidosDesde.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {
					perdioFocoComponenteBusqueda();
				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error perder foco");
				}
			}
		});

		view.txtTCMayor.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {
					perdioFocoComponenteBusqueda();
				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error perder foco");
				}
			}
		});

		view.txtTCMenor.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {
					perdioFocoComponenteBusqueda();
				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error perder foco");
				}
			}
		});
		TableAnchoManager.registrarEvento(view.tableDescEspecifico, "tablaBusquedaVencidosEspecifico");
		TableAnchoManager.registrarEvento(view.tableDescFamilia, "tablaBusquedaVencidosFamilia");

	}

	protected void perdioFocoComponenteBusqueda() throws Exception {
		boolean limpiar = false;

		// cliente
		if (!getView().txtNroCliente.getText().equals(getModel().getClienteID())) {
			if (CommonUtils.isNumeric(getView().txtNroCliente.getText())) {

				PantPrincipalController.setCursorOcupado();
				try {
					CcobClie cliente = CcobClieDAO.findById(Integer.valueOf(getView().txtNroCliente.getText()));

					if (cliente != null) {
						getView().lblNombreCliente.setText(cliente.getClieNombre());
						getView().lblNombreLegal.setText(cliente.getClieNombreLegal());
					} else {
						getView().lblNombreLegal.setText("S/D");
						getView().lblNombreCliente.setText("S/D");
					}
					getModel().setClienteID(getView().txtNroCliente.getText());
					limpiar = true;
				} finally {
					PantPrincipalController.setRestoreCursor();
				}
			} else {
				getModel().setClienteID("");
				getView().lblNombreLegal.setText("S/D");
				getView().lblNombreCliente.setText("S/D");

				limpiar = true;
			}

		}
		if (getView().txtVencidosAl.getDate() != null) {
			if (!FechaManagerUtil.fechaIguales(getView().txtVencidosAl.getDate(), getModel().getFechaVigenciaAl())) {
				limpiar = true;
				getModel().setFechaVigenciaAl(getView().txtVencidosAl.getDate());
			}
		}

		boolean sonDistintos = (getView().txtVencidosDesde.getDate() != null)
				&& (getModel().getFechaVigenciaDesde() != null) && !FechaManagerUtil
						.fechaIguales(getView().txtVencidosDesde.getDate(), getModel().getFechaVigenciaDesde());

		if (sonDistintos
				|| ((getView().txtVencidosDesde.getDate() == null) && (getModel().getFechaVigenciaDesde() != null))
				|| ((getView().txtVencidosDesde.getDate() != null) && (getModel().getFechaVigenciaDesde() == null))) {
			limpiar = true;
			getModel().setFechaVigenciaDesde(getView().txtVencidosDesde.getDate());
		}

		// busqueda
		if (!getView().txtBusqueda.getText().equals(getModel().getBusqueda())) {
			getModel().setBusqueda(getView().txtBusqueda.getText());
			limpiar = true;
		}

		// TCMayot
		if (getView().txtTCMayor.getText().equals("") && (getModel().getTcDesde() != null)) {
			getModel().setTcDesde(null);
			limpiar = true;
		}

		if (!getView().txtTCMayor.getText().equals("")
				&& !Common.String2Double(getView().txtTCMayor.getText()).equals(getModel().getTcDesde())) {
			getModel().setTcDesde(Common.String2Double(getView().txtTCMayor.getText()));
			limpiar = true;
		}

		// TC Menor
		if (getView().txtTCMenor.getText().equals("") && (getModel().getTcHasta() != null)) {
			getModel().setTcHasta(null);
			limpiar = true;
		}

		if (!getView().txtTCMenor.getText().equals("")
				&& !Common.String2Double(getView().txtTCMenor.getText()).equals(getModel().getTcHasta())) {
			getModel().setTcHasta(Common.String2Double(getView().txtTCMenor.getText()));
			limpiar = true;
		}

		if (limpiar) {
			limpiarBusqueda();
		}

	}

	private void busquedaProductos(String idCliente, Date fechaVencidosAl, Date fechaVencidosDesde, String busqueda,
			Double tcDesde, Double tcHasta) {
		PantPrincipalController.setCursorOcupado();
		try {

			getView().tableDescFamilia.setRowSorter(null);
			getView().tableDescEspecifico.setRowSorter(null);

			getView().tableDescFamilia.clear();
			getView().tableDescEspecifico.clear();

			Integer clienteID = null;
			if ((idCliente != null) && !idCliente.equals("")) {
				clienteID = Integer.valueOf(idCliente);
			}

			if (busqueda.equals("")) {
				busqueda = null;
			}

			List<CcobClie> listaAuxCliente = new ArrayList<CcobClie>();

			for (DescuentoXFamilias familia : FamiliaBusiness.getListaDescuentoByFiltros(clienteID, fechaVencidosAl,
					fechaVencidosDesde, busqueda)) {

				CcobClie cliente = buscarCliente(listaAuxCliente, familia.getPricFamiliaCliente());

				agregarRegistroATablaFamilia(getView().tableDescFamilia, familia, cliente);
			}

			for (PreciosEspecialesExDTO desc : PreciosEspecialesBusiness.getListaPrecioEspeciaByFiltros(clienteID,
					fechaVencidosAl, fechaVencidosDesde, busqueda, tcDesde, tcHasta)) {

				CcobClie cliente = buscarCliente(listaAuxCliente, desc.getPrecioEspecial().getPricCliente());

				SistUnim unidad = SistUnimDAO.findById(desc.getStockArts().getArtsUnimedStock());

				agregarRegistroATablaArticulo(getView().tableDescEspecifico, desc.getPrecioEspecial(),
						desc.getStockArts().getArtsArticuloEmp(), desc.getStockArts().getArtsNombre(),
						desc.getStockArts().getArtsDescripcion(), unidad.getUnimNombre(), cliente);
			}

			setSorter(getView().tableDescFamilia);
			setSorter(getView().tableDescEspecifico);

			sorterTablaDesEspecifico.sort();
			sorterTablaDesFamilia.sort();
		} finally {
			PantPrincipalController.setRestoreCursor();
		}

	}

	private CcobClie buscarCliente(List<CcobClie> listaAuxCliente, int idCliente) {
		for (CcobClie cliente : listaAuxCliente) {
			if (cliente.getClieCliente() == idCliente) {
				return cliente;
			}
		}

		CcobClie nuevoCliente = CcobClieDAO.findById(idCliente);

		listaAuxCliente.add(nuevoCliente);

		return nuevoCliente;
	}

	private void limpiarBusqueda() {
		getView().tableDescFamilia.clear();
		getView().tableDescEspecifico.clear();

		getView().tableDescFamilia.setRowSorter(null);
		getView().tableDescEspecifico.setRowSorter(null);
	}

	@Override
	protected String getNombrePantalla() {
		return "Busqueda de Vencidos";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		resetearDatosDePantalla();
	}

	protected void resetearDatosDePantalla() {
		getView().txtNroCliente.clear();
		getModel().setClienteID("");

		getView().lblNombreLegal.setText("S/D");
		getView().lblNombreCliente.setText("S/D");

		getView().lblError.setText("");

		getView().txtBusqueda.clear();
		getModel().setBusqueda("");

		getView().txtVencidosAl.setDate(FechaManagerUtil.getDateTimeFromPC());
		getModel().setFechaVigenciaAl(FechaManagerUtil.getDateTimeFromPC());

		getView().txtVencidosDesde.setDate(null);
		getModel().setFechaVigenciaDesde(null);

		getView().txtTCMayor.clear();
		getModel().setTcDesde(null);

		getView().txtTCMenor.clear();
		getModel().setTcHasta(null);

		limpiarBusqueda();

		getView().txtNroCliente.requestFocus();
	}

	private void setSorter(RPTable tabla) {
		if (tabla == getView().tableDescFamilia) {
			resetearTablaFamilia(tabla);
		} else {
			resetearTablaEspecifico(tabla);
		}
	}

	private void resetearTablaFamilia(RPTable tabla) {
		// Le seteo el order
		sorterTablaDesFamilia = new TableRowSorter<TableModel>(tabla.getModel());
		ArrayList<RowSorter.SortKey> sortKeysFamilia = new ArrayList<RowSorter.SortKey>();

		sortKeysFamilia.add(new RowSorter.SortKey(BusquedaVencidoView.COL_NOMBRE_FAMILIA, SortOrder.ASCENDING));
		sortKeysFamilia.add(new RowSorter.SortKey(BusquedaVencidoView.COL_DESDE_FAMILIA, SortOrder.ASCENDING));

		sorterTablaDesFamilia.setSortKeys(sortKeysFamilia);

		sorterTablaDesFamilia.setComparator(BusquedaVencidoView.COL_DESDE_FAMILIA, new Comparator<Date>() {

			@Override
			public int compare(Date fecha1, Date fecha2) {
				return (int) FechaManagerUtil.getDateDiff(fecha1, fecha2, TimeUnit.SECONDS);
			}
		});

		tabla.setRowSorter(sorterTablaDesFamilia);
		sorterTablaDesFamilia.sort();
	}

	private void resetearTablaEspecifico(RPTable tabla) {
		// Le seteo el order
		sorterTablaDesEspecifico = new TableRowSorter<TableModel>(tabla.getModel());
		tabla.setRowSorter(sorterTablaDesEspecifico);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();

		sortKeys.add(new RowSorter.SortKey(BusquedaVencidoView.COL_NOMBRE_ESPECIFICO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(BusquedaVencidoView.COL_DESDE_ESPECIFICO, SortOrder.ASCENDING));

		sorterTablaDesEspecifico.setComparator(BusquedaVencidoView.COL_DESDE_ESPECIFICO, new Comparator<Date>() {
			@Override
			public int compare(Date date1, Date date2) {
				return (int) FechaManagerUtil.getDateDiff(date2, date1, TimeUnit.SECONDS);
			}
		});

		sorterTablaDesEspecifico.setSortKeys(sortKeys);
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);
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
			}
			perdioFocoComponenteBusqueda();
		}
	}

	@Override
	public void ejecutarAccion(String accion) {

		if (accion.equals(ConstantesRP.PantBusquedaVencidos.BUSCAR.toString())) {
			try {
				perdioFocoComponenteBusqueda();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al perder foco componente");
			} // para que refesque

			busquedaProductos(getModel().getClienteID(), getModel().getFechaVigenciaAl(),
					getModel().getFechaVigenciaDesde(), getModel().getBusqueda(), getModel().getTcDesde(),
					getModel().getTcHasta());
		}
		if (accion.equals(ConstantesRP.PantBusquedaVencidos.LIMPIAR.toString())) {
			resetearDatosDePantalla();
		}

	}

	private void agregarRegistroATablaArticulo(RPTable tabla, PreciosEspeciales registro, String id_Articulo,
			String nombreItem, String descItem, String unidadItem, CcobClie cliente) {

		Color[] colorFondo = new Color[tabla.getModel().getColumnCount()];
		Color[] colorLetra = new Color[tabla.getModel().getColumnCount()];

		colorFondo = rellenarColor(colorFondo, null);
		colorLetra = rellenarColor(colorLetra, null);

		Boolean estaVigente = estaVigente(registro.getPricFechaDesde(), registro.getPricFechaHasta());

		tabla.addRowColor(new Object[] { registro.getPricCliente(), cliente.getClieNombre(), id_Articulo, nombreItem,
				descItem, unidadItem,
				registro.getPricDescuento1() != null ? Common.double2String(registro.getPricDescuento1().doubleValue())
						: "",
				registro.getPricDescuento2() != null ? Common.double2String(registro.getPricDescuento2().doubleValue())
						: "",
				registro.getPricMoneda() != null ? SistMoneDAO.findById(registro.getPricMoneda()).getMoneNombre() : "",
				registro.getPricPrecio() != null
						? CommonPricing.formatearImporte(registro.getPricPrecio().doubleValue())
						: "",
				registro.getPricFechaDesde(), registro.getPricFechaHasta(),
				Common.double2String(registro.getPricComision().doubleValue()), registro.getPricReferencia(),
				registro.getPricValorTC() != null ? CommonPricing.formatearImporte(registro.getPricValorTC(), 2) : "",

				(estaVigente ? "SI" : "NO"), registro }, colorFondo, colorLetra);

		tabla.adjustColumns();
	}

	private Boolean estaVigente(Date pricFechaDesde, Date pricFechaHasta) {
		return (FechaManagerUtil.getDateDiff(pricFechaDesde, FechaManagerUtil.getDateTimeFromPC(),
				TimeUnit.MINUTES) <= 0)
				&& (FechaManagerUtil.getDateDiff(pricFechaHasta, FechaManagerUtil.getDateTimeFromPC(),
						TimeUnit.MINUTES) >= 0);
	}

	private Color[] rellenarColor(Color[] vector, Color color) {
		for (int x = 0; x < vector.length; x++) {
			vector[x] = color;
		}
		return vector;
	}

	private void agregarRegistroATablaFamilia(RPTable tabla, DescuentoXFamilias registro, CcobClie cliente) {
		Boolean estaVigente = estaVigente(registro.getPricFamiliaFechaDesde(), registro.getPricFamiliaFechaHasta());

		tabla.addRow(new Object[] { registro.getPricFamiliaCliente(), cliente.getClieNombre(),
				registro.getPricCa01Clasif1(), registro.getNombreFamilia(),
				registro.getPricFamiliaDescuento1() != null
						? Common.double2String(registro.getPricFamiliaDescuento1().doubleValue())
						: "",
				registro.getPricFamiliaDescuento2() != null
						? Common.double2String(registro.getPricFamiliaDescuento2().doubleValue())
						: "",
				registro.getPricFamiliaFechaDesde(), registro.getPricFamiliaFechaHasta(),
				Common.double2String(registro.getPricFamiliaComision().doubleValue()), registro.getPricReferencia(),
				(estaVigente ? "SI" : "NO"), registro });

		tabla.adjustColumns();
	}

}