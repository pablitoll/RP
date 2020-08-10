
package ar.com.rollpaper.pricing.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.alee.laf.optionpane.WebOptionPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.SistUnim;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.pricing.business.CommonPricing;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.GeneradorDePrecios;
import ar.com.rollpaper.pricing.business.ListaBusiness;
import ar.com.rollpaper.pricing.business.OverloadManager;
import ar.com.rollpaper.pricing.business.TableAnchoManager;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dao.SistMoneDAO;
import ar.com.rollpaper.pricing.dao.SistUnimDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rollpaper.pricing.model.CargaItemEspecialArticuloModel;
import ar.com.rollpaper.pricing.model.CargaItemEspecialFamiliaModel;
import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.ui.BuscarClienteDialog;
import ar.com.rollpaper.pricing.ui.BuscarListaDialog;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaItemEspecialArticuloView;
import ar.com.rollpaper.pricing.view.CargaItemEspecialFamiliaView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.CSVExport;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaPrecioController
		extends BaseControllerMVC<PantPrincipalController, CargaPrecioView, CargaPrecioModel> {

	private static final String RE_LIKE = "\\w*(?i)%s\\w*";
	private CargaItemEspecialArticuloModel itemEspecialArticuloModel = new CargaItemEspecialArticuloModel();
	private CargaItemEspecialArticuloView itemEspecialArticuloView = new CargaItemEspecialArticuloView();
	private CargaItemEspecialArticulo itemEspecialArticulo = new CargaItemEspecialArticulo(
			PantPrincipalController.getPantallaPrincipal(), itemEspecialArticuloView, itemEspecialArticuloModel, null);

	private CargaItemEspecialFamiliaModel itemEspecialFamiliaModel = new CargaItemEspecialFamiliaModel();
	private CargaItemEspecialFamiliaView itemEspecialFamiliaView = new CargaItemEspecialFamiliaView();
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

		view.chkSoloVigentes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setFiltros();
			}
		});

		view.rbTodos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setFiltros();
			}
		});

		view.rdArticuloLista.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setFiltros();
			}
		});

		view.rdArticuloEspecifico.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setFiltros();
			}
		});

		view.chkBusquedaCodArticulo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setFiltros();
			}
		});

		view.chkBusquedaCodNombre.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setFiltros();
			}
		});

		view.chkBusquedaDescrip.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setFiltros();
			}
		});

		view.txtBusqueda.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent evento) {
				setFiltros();
			}
		});
		view.txtMultiplicador.addFocusListener(new FocusAdapter() {

			public void focusLost(FocusEvent evento) {
				try {
					if (getView().txtMultiplicador.isEnabled()) {
						Double valor = Common.String2Double(getView().txtMultiplicador.getText());
						if ((valor <= 0.0) || (valor >= 1)) {
							Dialog.showMessageDialog("El factor de multiplicación debe ser superior a 0 e inferior a 1",
									"Valor Invalido", WebOptionPane.ERROR_MESSAGE);
							getView().txtMultiplicador.requestFocus();
						} else {
							getModel().setFactor(valor);
							agregoEditItem();
						}
					}
				} catch (Exception e1) {
					ManejoDeError.showError(e1, "Error al manejar multiplicador");
				}
			}
		});

		view.chkMultiplicador.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (getModel().isClienteCargado()) {
					getView().txtMultiplicador.setEnabled(getView().chkMultiplicador.isSelected());

					if (!getView().txtMultiplicador.isEnabled()) {
						getView().txtMultiplicador.setNumero(0.0);
						getModel().setFactor(null);
						agregoEditItem();
					} else {
						getView().txtMultiplicador.requestFocus();
					}
				}
			}
		});

		TableAnchoManager.registrarEvento(view.tableDescFamilia, "tablaCargaPrecioFamilia");
		TableAnchoManager.registrarEvento(view.tableDescEspecifico, "tablaCargaPrecioEspecifico");

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

	private void cargarProductos() {
		getView().tableDescFamilia.clear();
		getView().tableDescEspecifico.clear();

		getView().tableDescFamilia.setRowSorter(null);
		getView().tableDescEspecifico.setRowSorter(null);

		for (DescuentoXFamilias familia : DescuentoXFamiliasDAO.getListaDescuentoByID(
				getModel().getClienteCargado().getClieCliente(),
				getModel().getListaCargada().getVentLipv().getLipvListaPrecvta())) {
			agregarRegistroATablaFamilia(getView().tableDescFamilia, familia);
		}

		for (PreciosEspeciales desc : PreciosEspecialesDAO.getListaPrecioEspeciaByID(
				getModel().getClienteCargado().getClieCliente(),
				getModel().getListaCargada().getVentLipv().getLipvListaPrecvta())) {
			StocArts arti = StocArtsDAO.findById(desc.getPricArticulo());
			SistUnim unidad = SistUnimDAO.findById(arti.getArtsUnimedStock());
			Boolean estaEnLista = ListaBusiness.isArticuloEnLista(desc.getPricArticulo(), desc.getPricListaPrecvta());

			agregarRegistroATablaArticulo(getView().tableDescEspecifico, desc, arti.getArtsArticuloEmp(),
					arti.getArtsNombre(), arti.getArtsDescripcion(), unidad.getUnimNombre(), estaEnLista);
		}
		setSorter(getView().tableDescFamilia);
		setSorter(getView().tableDescEspecifico);

		sorterTablaDesEspecifico.sort();
		sorterTablaDesFamilia.sort();

		setModoPantalla();
	}

	protected void perdioFocoCliente(int id) throws Exception {
		if ((getModel().getClienteCargado() == null) || (getModel().getClienteCargado().getClieCliente() != id)) {

			CcobClie cliente = null;
			PantPrincipalController.setCursorOcupado();
			try {
				cliente = CcobClieDAO.findById(Integer.valueOf(id));

				if (cliente != null) {
					getModel().setClienteCargado(cliente);

					getView().lblNombreCliente.setText(cliente.getClieNombre());
					getView().lblNombreLegal.setText(cliente.getClieNombreLegal());
					getView().chkSoloVigentes.setEnabled(true);

					getView().chkMultiplicador.setEnabled(true);
					getView().chkMultiplicador.setSelected(getModel().getFactor() != null);
					getView().txtMultiplicador.setEnabled(getView().chkMultiplicador.isSelected());
					if (getModel().getFactor() != null) {
						getView().txtMultiplicador.setText(CommonPricing.formatearImporte(getModel().getFactor()));
					}

					cargarLista();
					setModoPantalla();

					getModel().setIsClienteCargado(true);
					getView().tableDescFamilia.requestFocus();
				} else {
					resetearDatosDePantalla();
				}
			} finally {
				PantPrincipalController.setRestoreCursor();
			}

			if (cliente != null) {
				if (!MaestroEsclavoDAO.getListaEsclavosByCliente(cliente).isEmpty()) {
					if (Dialog.showConfirmDialog(
							"IMPORTANTE: Este Cliente tiene esclavos.\nTodos lo que cargue impactará tambien sobre los mismos!. ¿Continuamos?",
							"Cliente con Esclavos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
							null) != JOptionPane.YES_OPTION) {

						getModel().setClienteCargado(null); // Elimino el cliente actual y reseteo
						resetearDatosDePantalla();
					}
				}
			}
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
		getView().btnExportar.setEnabled(tieneCli);

		getView().btnEliminarLista.setEnabled(habilitaEliminar);
		getView().btnAgregarLista.setEnabled(tieneCli);

		getView().txtBusqueda.setEnabled(tieneCli);
		getView().chkBusquedaCodArticulo.setEnabled(tieneCli);
		getView().chkBusquedaCodNombre.setEnabled(tieneCli);
		getView().chkBusquedaDescrip.setEnabled(tieneCli);

		getView().rdArticuloLista.setEnabled(tieneCli);
		getView().rdArticuloEspecifico.setEnabled(tieneCli);
		getView().rbTodos.setEnabled(tieneCli);

		if (tieneCli) {
			if (!getView().btnImpactarPrecios.isVisible() && !getView().btnCancelar.isVisible()) {
				getView().setCerrarVisible(false);
				getView().btnCancelar.setVisible(true);
			}
		}
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
			getModel().setIsClienteCargado(false);
			getView().txtNroCliente.clear();

			getView().lblNombreLista.setText("S/D");
			getView().lblNombreLegal.setText("S/D");
			getView().lblNombreCliente.setText("S/D");

			getModel().setClienteCargado(null);
			getView().cbNroLista.removeAllItems();
			getView().lblError.setText("");

			getView().tableDescFamilia.clear();
			getView().tableDescEspecifico.clear();

			setSorter(getView().tableDescFamilia);
			setSorter(getView().tableDescEspecifico);

			getView().setCerrarVisible(true);
			getView().btnImpactarPrecios.setVisible(false);
			getView().btnCancelar.setVisible(false);

			getView().chkSoloVigentes.setSelected(false);
			getView().chkMultiplicador.setEnabled(false);
			getView().chkMultiplicador.setSelected(false);
			getView().txtMultiplicador.setEnabled(false);
			getView().txtMultiplicador.setText("0");
			getView().chkSoloVigentes.setEnabled(false);

			getView().txtBusqueda.setText("");
			getView().chkBusquedaCodArticulo.setSelected(false);
			getView().chkBusquedaCodNombre.setSelected(false);
			getView().chkBusquedaDescrip.setSelected(false);
			getView().rdArticuloLista.setSelected(false);
			getView().rdArticuloEspecifico.setSelected(false);
			getView().rbTodos.setSelected(true);
		}

		setModoPantalla();
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

		sortKeysFamilia.add(new RowSorter.SortKey(CargaPrecioView.COL_NOMBRE_FAMILIA, SortOrder.ASCENDING));
		sortKeysFamilia.add(new RowSorter.SortKey(CargaPrecioView.COL_DESDE_FAMILIA, SortOrder.ASCENDING));

		sorterTablaDesFamilia.setSortKeys(sortKeysFamilia);

		sorterTablaDesFamilia.setComparator(CargaPrecioView.COL_DESDE_FAMILIA, new Comparator<Date>() {
			@Override
			public int compare(Date fecha1, Date fecha2) {
				return (int) FechaManagerUtil.getDateDiff(fecha1, fecha2, TimeUnit.DAYS);
			}
		});

		if (getView().chkSoloVigentes.isSelected()) {
			sorterTablaDesFamilia.setRowFilter(RowFilter.regexFilter("SI", CargaPrecioView.COL_ESTA_VIGENTE_FAMILIA));
		}

		tabla.setRowSorter(sorterTablaDesFamilia);
		sorterTablaDesFamilia.sort();
	}

	private void resetearTablaEspecifico(RPTable tabla) {
		// Le seteo el order
		sorterTablaDesEspecifico = new TableRowSorter<TableModel>(tabla.getModel());
		tabla.setRowSorter(sorterTablaDesEspecifico);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();

		sortKeys.add(new RowSorter.SortKey(CargaPrecioView.COL_NOMBRE_ESPECIFICO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(CargaPrecioView.COL_DESDE_ESPECIFICO, SortOrder.ASCENDING));

		sorterTablaDesEspecifico.setComparator(CargaPrecioView.COL_DESDE_ESPECIFICO, new Comparator<Date>() {
			@Override
			public int compare(Date fecha1, Date fecha2) {
				return (int) FechaManagerUtil.getDateDiff(fecha1, fecha2, TimeUnit.DAYS);
			}
		});

		sorterTablaDesEspecifico.setSortKeys(sortKeys);

		setFiltros();
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

	@SuppressWarnings("unchecked")
	private void agregarLista() throws Exception {
		BuscarListaDialog buscarListaDialog = new BuscarListaDialog(getPantallaPrincipal(),
				getView().cbNroLista.getModel());
		buscarListaDialog.iniciar();
		if (buscarListaDialog.getNroLista() != null) {
			VentLipv lista = buscarListaDialog.getNroLista();
			ListaDTO nuevaLista = new ListaDTO(lista, false, false);
			getModel().agregarLista(nuevaLista);
			getView().cbNroLista.addItem(nuevaLista);
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
		if (accion.equals(ConstantesRP.PantCarPrecio.EXPORTAR_EXCEL.toString())) {
			try {
				if (isPanelActivoFamilia()) {
					if (getView().tableDescFamilia.getRowCount() > 0) {

						String nombreArchivo = String.format("Precio_Descuento_X_Familia_%s_%s",
								getModel().getListaCargada().getVentLipv().getLipvListaPrecvta(), FechaManagerUtil
										.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), "yyyyMMdd_HHmmss"));

						CSVExport.exportToExcel(getView().tableDescFamilia, nombreArchivo, null);
					} else {
						Dialog.showMessageDialog("No hay registros para Exportar");
					}
				} else {
					if (getView().tableDescEspecifico.getRowCount() > 0) {

						String nombreArchivo = String.format("Precio_Descuento_Precio_Especifico_%s_%s",
								getModel().getListaCargada().getVentLipv().getLipvListaPrecvta(), FechaManagerUtil
										.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), "yyyyMMdd_HHmmss"));

						CSVExport.exportToExcel(getView().tableDescEspecifico, nombreArchivo, null);
					} else {
						Dialog.showMessageDialog("No hay registros para Exportar");
					}
				}

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al exportar");
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.IMPACTAR_PRECIOS.toString())) {
			if (Dialog.showConfirmDialog(
					String.format("Terminamos la carga Actual e Impactamos los procesios del Cliente %s",
							getModel().getClienteCargado().getClieNombre()),
					"Impacto de Precios", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
					null) == JOptionPane.YES_OPTION) {

				PantPrincipalController.setCursorOcupado();
				try {
					try {
						BigDecimal factor = null;
						if (getModel().getFactor() != null) {
							factor = new BigDecimal(getModel().getFactor());
						}
						for (ListaDTO lista : getModel().getListasToShow()) {
							GeneradorDePrecios.impactarPrecios(getModel().getClienteCargado(), lista.getVentLipv(),
									factor, ArchivoDePropiedadesBusiness.getidListaEspecial());
						}
					} finally {
						PantPrincipalController.setRestoreCursor();
					}

					Dialog.showMessageDialog("Se termino de aplicar nuevos precios en todas las lista del cliente",
							"Aplicacion de Precios", JOptionPane.INFORMATION_MESSAGE);

					// reseto la pantalla
					ejecutarAccion(ConstantesRP.PantCarPrecio.CANCELAR.toString());

				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al Impactar Precio");
				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.CANCELAR.toString()))

		{
			try {
				getModel().setClienteCargado(null); // Elimino el cliente actual y reseteo
				resetearDatosDePantalla();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al limpiar pantalla");
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.AGREGAR.toString())) {

			if (getModel().getListaCargada() != null) {

				String resutlado = "";
				try {
					if (isPanelActivoFamilia()) {

						if (itemEspecialFamilia.iniciarAlta(getModel().getClienteCargado(),
								getModel().getListaCargada().getVentLipv(),
								(DefaultTableModel) getView().tableDescFamilia.getModel())) {

							getView().tableDescFamilia.setRowSorter(null);
							try {
								for (DescuentoXFamilias registro : itemEspecialFamilia.getRegistro()) {

									actualzarFechasCortesFamilia(registro, getView().tableDescFamilia,
											CargaPrecioView.COL_REGISTRO_FAMILIA);

									HibernateGeneric.persist(registro);
									agregoEditItem();

									agregarRegistroATablaFamilia(getView().tableDescFamilia, registro);

								}
							} finally {
								setSorter(getView().tableDescFamilia);
							}

							posicionarEnRegisro(getView().tableDescFamilia, CargaPrecioView.COL_NOMBRE_FAMILIA,
									itemEspecialFamilia.getRegistro().get(0).getNombreFamilia(),
									CargaPrecioView.COL_DESDE_FAMILIA,
									itemEspecialFamilia.getRegistro().get(0).getPricFamiliaFechaDesde());

						}
					} else {
						itemEspecialArticuloModel
								.setTableModel((DefaultTableModel) getView().tableDescEspecifico.getModel());
						itemEspecialArticulo.setRegistro(getModel().getRegistroArticuloEmpty());

						resutlado = itemEspecialArticulo.iniciar();

						if (!resutlado.equals("")) {

							PreciosEspeciales registro = itemEspecialArticulo.getRegistro();

							HibernateGeneric.persist(registro);
							agregoEditItem();

							SistUnim unidad = SistUnimDAO.findById(itemEspecialArticulo.getUnidadItem());
							Boolean estaEnLista = ListaBusiness.isArticuloEnLista(registro.getPricArticulo(),
									registro.getPricListaPrecvta());

							agregarRegistroATablaArticulo(getView().tableDescEspecifico, registro,
									itemEspecialArticulo.getArticuloIDMostrar(), itemEspecialArticulo.getNombreItem(),
									itemEspecialArticulo.getDescripcionItem(), unidad.getUnimNombre(), estaEnLista);

							sorterTablaDesEspecifico.sort();

							posicionarEnRegisro(getView().tableDescEspecifico, CargaPrecioView.COL_NOMBRE_ESPECIFICO,
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
						int modelRow = getView().tableDescFamilia.convertRowIndexToModel(row);

						if (itemEspecialFamilia.iniciarModif(getModel().getClienteCargado(),
								getModel().getListaCargada().getVentLipv(),
								(DefaultTableModel) getView().tableDescFamilia.getModel(),
								(DescuentoXFamilias) getView().tableDescFamilia.getModel().getValueAt(modelRow,
										CargaPrecioView.COL_REGISTRO_FAMILIA))) {

							DescuentoXFamilias registro = itemEspecialFamilia.getRegistro().get(0); // Como es
																									 // edicion
																									 // es uno solo
							modificarRegistroATabla(getView().tableDescFamilia, registro, row);
							getView().tableDescFamilia.setRowSorter(null);
							try {
								actualzarFechasCortesFamilia(registro, getView().tableDescFamilia,
										CargaPrecioView.COL_REGISTRO_FAMILIA);

								HibernateGeneric.attachDirty(registro);

								agregoEditItem();

							} finally {
								setSorter(getView().tableDescFamilia);
							}

							posicionarEnRegisro(getView().tableDescFamilia, CargaPrecioView.COL_NOMBRE_FAMILIA,
									registro.getNombreFamilia(), CargaPrecioView.COL_DESDE_FAMILIA,
									registro.getPricFamiliaFechaDesde());

							getView().tableDescFamilia.adjustColumns();

						}
					}

				} else {
					if ((getView().tableDescEspecifico.getRowCount() > 0)
							&& (getView().tableDescEspecifico.getSelectedRow() >= 0)) {

						int row = getView().tableDescEspecifico.getSelectedRow();
						int modelRow = getView().tableDescEspecifico.convertRowIndexToModel(row);

						itemEspecialArticulo.setRegistro((PreciosEspeciales) getView().tableDescEspecifico.getModel()
								.getValueAt(modelRow, CargaPrecioView.COL_REGISTRO_ESPECIFICO));
						itemEspecialArticuloModel
								.setTableModel((DefaultTableModel) getView().tableDescEspecifico.getModel());

						resutlado = itemEspecialArticulo.iniciar();

						if (!resutlado.equals("")) {

							PreciosEspeciales registro = itemEspecialArticulo.getRegistro();

							HibernateGeneric.attachDirty(registro);
							agregoEditItem();

							modificarRegistroATabla(getView().tableDescEspecifico, registro, row);

							sorterTablaDesEspecifico.sort();

							posicionarEnRegisro(getView().tableDescEspecifico, CargaPrecioView.COL_NOMBRE_ESPECIFICO,
									itemEspecialArticulo.getNombreItem(), CargaPrecioView.COL_DESDE_ESPECIFICO,
									registro.getPricFechaDesde());

							getView().tableDescEspecifico.adjustColumns();
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
					try {
						int row = tabla.getSelectedRow();
						int modelRow = tabla.convertRowIndexToModel(row);

						tabla.setRowSorter(null);
						try {
							Object regis = tabla.getModel().getValueAt(modelRow, col_registro);
							DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
							dm.removeRow(modelRow);
							HibernateGeneric.remove(regis);
							agregoEditItem();

						} finally {
							setSorter(tabla);
							tabla.adjustColumns();
						}
					} catch (Exception e) {
						ManejoDeError.showError(e, "Error al eliminar registro");
					}
				}
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.AGREGAR_LISTA.toString())) {
			try {
				agregarLista();
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al agregar lista");
			}
		}

		if (accion.equals(ConstantesRP.PantCarPrecio.ELIMINAR_LISTA.toString())) {
			if (WebOptionPane.showConfirmDialog(getView(), "¿Eliminamos la lista y la Impactamos?",
					"Eliminacion de Lista", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == WebOptionPane.YES_OPTION) {
				for (int i = 0; i < getView().tableDescFamilia.getRowCount(); i++) {
					Object regis = getView().tableDescFamilia.getModel().getValueAt(
							getView().tableDescFamilia.convertColumnIndexToModel(i),
							CargaPrecioView.COL_REGISTRO_FAMILIA);
					HibernateGeneric.remove(regis);
					agregoEditItem();
				}

				for (int i = 0; i < getView().tableDescEspecifico.getRowCount(); i++) {
					Object regis = getView().tableDescEspecifico.getModel().getValueAt(
							getView().tableDescEspecifico.convertColumnIndexToModel(i),
							CargaPrecioView.COL_REGISTRO_ESPECIFICO);
					HibernateGeneric.remove(regis);
					agregoEditItem();
				}

				try {
					recargar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/*-
	 * Tengo 7 casos de solapamiento
	 * 1) A ---------------------
	 *            B--------------------
	 * 1B)A    -------
	*     B          --------
	 * 
	 * 
	 * 2)         A ----------------
	 *    B --------------
	 *  
	 *2B)         A ------
	 *    B ---------
	 * 
	 * 
	 * 3)	      A -------------
	 *    B -----------------------------
	 *    
	 *    
	 * 4) A ----------------------
	*             B ------
	*
	*  5) A ----
	*     B ----
	*
	* 6) A          ----------
	*    B   -----------------
	* 
	*    
	* 7) A    -------
	*    B    ------------------   
	* 
	* 
	 */
	private void actualzarFechasCortesFamilia(DescuentoXFamilias registroNuevo, RPTable tableActivo, int col_reg) {

		DefaultTableModel dm = (DefaultTableModel) tableActivo.getModel();

		/// caso 3 o 5
		for (int i = 0; i < dm.getRowCount(); i++) {

			DescuentoXFamilias regTablaFamilia = (DescuentoXFamilias) dm.getValueAt(i, col_reg);

			if (regTablaFamilia.getPricCa01Clasif1().equals(registroNuevo.getPricCa01Clasif1())) {
				// A = regTablaFamilia
				// B = registroNuevo

				if (OverloadManager.isCase3(regTablaFamilia, registroNuevo)
						|| OverloadManager.isCase5(regTablaFamilia, registroNuevo)
						|| OverloadManager.isCase6(regTablaFamilia, registroNuevo)
						|| OverloadManager.isCase7(regTablaFamilia, registroNuevo)) {

					dm.removeRow(i);
					HibernateGeneric.remove(regTablaFamilia);
					i--;
				}
			}
		}

		/// resto de casos
		for (int i = 0; i < dm.getRowCount(); i++) {

			DescuentoXFamilias regTablaFamilia = (DescuentoXFamilias) dm.getValueAt(i, col_reg);

			if (regTablaFamilia.getPricCa01Clasif1().equals(registroNuevo.getPricCa01Clasif1())) {

				if (OverloadManager.isCase1(regTablaFamilia, registroNuevo)) {
					regTablaFamilia.setPricFamiliaFechaHasta(
							FechaManagerUtil.addDays(registroNuevo.getPricFamiliaFechaDesde(), -1));
					HibernateGeneric.attachDirty(regTablaFamilia);
					modificarRegistroATabla(tableActivo, regTablaFamilia, tableActivo.convertRowIndexToView(i));
				}

				if (OverloadManager.isCase2(regTablaFamilia, registroNuevo)) {
					regTablaFamilia.setPricFamiliaFechaDesde(
							FechaManagerUtil.addDays(registroNuevo.getPricFamiliaFechaHasta(), 1));
					HibernateGeneric.attachDirty(regTablaFamilia);
					modificarRegistroATabla(tableActivo, regTablaFamilia, tableActivo.convertRowIndexToView(i));
				}

				if (OverloadManager.isCase4(regTablaFamilia, registroNuevo)) {
					DescuentoXFamilias regTablaFamilia2 = new DescuentoXFamilias(regTablaFamilia);

					regTablaFamilia.setPricFamiliaFechaHasta(
							FechaManagerUtil.addDays(registroNuevo.getPricFamiliaFechaDesde(), -1));
					HibernateGeneric.attachDirty(regTablaFamilia);
					modificarRegistroATabla(tableActivo, regTablaFamilia, tableActivo.convertRowIndexToView(i));

					// agrego un registro nuevo
					regTablaFamilia2.setPricFamiliaFechaDesde(
							FechaManagerUtil.addDays(registroNuevo.getPricFamiliaFechaHasta(), 1));
					HibernateGeneric.persist(regTablaFamilia2);

					agregarRegistroATablaFamilia(getView().tableDescFamilia, regTablaFamilia2);

				}

			}
		}
	}

	private void agregoEditItem() {
		getView().btnImpactarPrecios.setVisible(true);
		getView().setCerrarVisible(false);
		getView().btnCancelar.setVisible(!getView().btnImpactarPrecios.isVisible());

	}

	private void recargar() throws Exception {
		int idCliente = getModel().getClienteCargado().getClieCliente();
		getModel().setClienteCargado(null);
		resetearDatosDePantalla();
		getView().txtNroCliente.setText(String.valueOf(idCliente));
		perdioFocoCliente(idCliente);
	}

	private void posicionarEnRegisro(RPTable tableActivo, int col_nombre, String nombre, int col_desde,
			Date fechaDesde) {
		String strDesde = FechaManagerUtil.Date2String(fechaDesde);
		col_nombre = tableActivo.convertColumnIndexToView(col_nombre);
		col_desde = tableActivo.convertColumnIndexToView(col_desde);

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

			Boolean estaVigente = CommonPricing.estaVigente(registroPedido.getPricFechaDesde(),
					registroPedido.getPricFechaHasta());

			tableActivo.setValueAt(registroPedido.getPricDescuento1() != null
					? Common.double2String(registroPedido.getPricDescuento1().doubleValue())
					: "", row, tableActivo.convertColumnIndexToView(CargaPrecioView.COL_1DESC_ESPECIFICO));
			tableActivo.setValueAt(registroPedido.getPricDescuento2() != null
					? Common.double2String(registroPedido.getPricDescuento2().doubleValue())
					: "", row, tableActivo.convertColumnIndexToView(CargaPrecioView.COL_2DESC_ESPECIFICO));
			tableActivo.setValueAt(descMoneda, row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_MONEDA_ESPECIFICO));
			tableActivo.setValueAt(registroPedido.getPricPrecio() != null
					? CommonPricing.formatearImporte(registroPedido.getPricPrecio().doubleValue())
					: "", row, tableActivo.convertColumnIndexToView(CargaPrecioView.COL_PRECIO_ESPECIFICO));
			tableActivo.setValueAt(registroPedido.getPricFechaDesde(), row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_DESDE_ESPECIFICO));
			tableActivo.setValueAt(registroPedido.getPricFechaHasta(), row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_HASTA_ESPECIFICO));
			tableActivo.setValueAt(registroPedido.getPricComision() != null
					? Common.double2String(registroPedido.getPricComision().doubleValue())
					: "", row, tableActivo.convertColumnIndexToView(CargaPrecioView.COL_COMISION_ESPECIFICO));
			tableActivo.setValueAt(registroPedido.getPricReferencia(), row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_REFERENCIA_ESPECIFICO));
			tableActivo.setValueAt(
					registroPedido.getPricValorTC() != null
							? CommonPricing.formatearImporte(registroPedido.getPricValorTC(), 2)
							: "",
					row, tableActivo.convertColumnIndexToView(CargaPrecioView.COL_TC_ESPECIFICO));

			tableActivo.setValueAt((estaVigente ? "SI" : "NO"), row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_ESTA_VIGENTE_ESPECIFICO));
		} else {
			DescuentoXFamilias registroFamilia = (DescuentoXFamilias) registro;

			tableActivo.setValueAt(registroFamilia.getPricFamiliaDescuento1() != null
					? Common.double2String(registroFamilia.getPricFamiliaDescuento1().doubleValue())
					: "", row, tableActivo.convertColumnIndexToView(CargaPrecioView.COL_1DESC_FAMILIA));
			tableActivo.setValueAt(registroFamilia.getPricFamiliaDescuento2() != null
					? Common.double2String(registroFamilia.getPricFamiliaDescuento2().doubleValue())
					: "", row, tableActivo.convertColumnIndexToView(CargaPrecioView.COL_2DESC_FAMILIA));
			tableActivo.setValueAt(registroFamilia.getPricFamiliaFechaDesde(), row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_DESDE_FAMILIA));
			tableActivo.setValueAt(registroFamilia.getPricFamiliaFechaHasta(), row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_HASTA_FAMILIA));
			tableActivo.setValueAt(registroFamilia.getPricFamiliaComision() != null
					? Common.double2String(registroFamilia.getPricFamiliaComision().doubleValue())
					: "", row, tableActivo.convertColumnIndexToView(CargaPrecioView.COL_COMSISION_FAMILIA));
			tableActivo.setValueAt(registroFamilia.getPricReferencia(), row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_REFERENCIA_FAMILIA));

			Boolean estaVigente = CommonPricing.estaVigente(registroFamilia.getPricFamiliaFechaDesde(),
					registroFamilia.getPricFamiliaFechaHasta());

			tableActivo.setValueAt((estaVigente ? "SI" : "NO"), row,
					tableActivo.convertColumnIndexToView(CargaPrecioView.COL_ESTA_VIGENTE_FAMILIA));
		}
	}

	private void agregarRegistroATablaArticulo(RPTable tabla, PreciosEspeciales registro, String id_Articulo,
			String nombreItem, String descItem, String unidadItem, Boolean estaEnLista) {

		Boolean estaVigente = CommonPricing.estaVigente(registro.getPricFechaDesde(), registro.getPricFechaHasta());
		tabla.addRow(new Object[] { id_Articulo, nombreItem, descItem, unidadItem,
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
				(estaVigente ? "SI" : "NO"), (estaEnLista ? "SI" : "NO"), registro });

		tabla.adjustColumns();
	}

	private void agregarRegistroATablaFamilia(RPTable tabla, DescuentoXFamilias registro) {
		Boolean estaVigente = CommonPricing.estaVigente(registro.getPricFamiliaFechaDesde(),
				registro.getPricFamiliaFechaHasta());

		tabla.addRow(new Object[] { registro.getPricCa01Clasif1(), registro.getNombreFamilia(),
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

	private boolean isPanelActivoFamilia() {
		return (getView().tabPanel.getSelectedIndex() == 0);
	}

	public boolean isPendienteImpactar() {
		return getView().btnImpactarPrecios.isVisible();
	}

	protected void setFiltros() {
		boolean soloVigentes = getView().chkSoloVigentes.isSelected();
		boolean articuloEspescifico = getView().rdArticuloEspecifico.isSelected();
		boolean articuloLista = getView().rdArticuloLista.isSelected();

		boolean chkBusquedaCodArticulo = getView().chkBusquedaCodArticulo.isSelected();
		boolean chkBusquedaCodNombre = getView().chkBusquedaCodNombre.isSelected();
		boolean chkBusquedaDescrip = getView().chkBusquedaDescrip.isSelected();
		String txtBusqueda = getView().txtBusqueda.getText().trim();

		RowFilter<Object, Object> af = null;
		List<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object, Object>>();

		if (soloVigentes) {

			rfs.add(RowFilter.regexFilter("SI", getView().tableDescEspecifico
					.convertColumnIndexToView(CargaPrecioView.COL_ESTA_VIGENTE_ESPECIFICO)));

			// familia
			sorterTablaDesFamilia.setRowFilter(RowFilter.regexFilter("SI",
					getView().tableDescFamilia.convertColumnIndexToView(CargaPrecioView.COL_ESTA_VIGENTE_FAMILIA)));
		} else {
			sorterTablaDesFamilia.setRowFilter(null);
		}

		if (articuloEspescifico || articuloLista) {

			String filtro = "NO";
			if (articuloLista) {
				filtro = "SI";
			}
			rfs.add(RowFilter.regexFilter(filtro, getView().tableDescEspecifico
					.convertColumnIndexToView(CargaPrecioView.COL_ESTA_EN_LISTA_ESPECIFICO)));
		}

		if ((chkBusquedaCodArticulo || chkBusquedaCodNombre || chkBusquedaDescrip) && !txtBusqueda.equals("")) {

			List<RowFilter<Object, Object>> filterBusqueda = new ArrayList<RowFilter<Object, Object>>();
			String filtro = String.format(RE_LIKE, txtBusqueda.trim());

			if (chkBusquedaCodArticulo) {
				filterBusqueda.add(RowFilter.regexFilter(filtro,
						getView().tableDescEspecifico.convertColumnIndexToView(CargaPrecioView.COL_ID_ESPECIFICO)));
			}

			if (chkBusquedaCodNombre) {
				filterBusqueda.add(RowFilter.regexFilter(filtro,
						getView().tableDescEspecifico.convertColumnIndexToView(CargaPrecioView.COL_NOMBRE_ESPECIFICO)));
			}

			if (chkBusquedaDescrip) {
				filterBusqueda.add(RowFilter.regexFilter(filtro,
						getView().tableDescEspecifico.convertColumnIndexToView(CargaPrecioView.COL_DESC_ESPECIFICO)));
			}

			rfs.add(RowFilter.orFilter(filterBusqueda));
		}

		if (rfs.size() > 0) {
			af = RowFilter.andFilter(rfs);
		}

		sorterTablaDesEspecifico.setRowFilter(af);

	}

}