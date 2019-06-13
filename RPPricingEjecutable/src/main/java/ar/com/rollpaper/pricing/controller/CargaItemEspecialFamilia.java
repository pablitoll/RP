package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.StocCa01;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.FamiliaBusiness;
import ar.com.rollpaper.pricing.business.OverloadManager;
import ar.com.rollpaper.pricing.dao.StocCa01DAO;
import ar.com.rollpaper.pricing.model.CargaItemEspecialFamiliaModel;
import ar.com.rollpaper.pricing.ui.BuscarFamiliaDialog;
import ar.com.rollpaper.pricing.ui.Dialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaItemEspecialFamiliaView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.error.popUpError;
import ar.com.rp.ui.interfaces.PermisosInterface;
import ar.com.rp.ui.pantalla.BaseControllerDialog;

public class CargaItemEspecialFamilia extends
		BaseControllerDialog<PantPrincipalController, CargaItemEspecialFamiliaView, CargaItemEspecialFamiliaModel> {

	public CargaItemEspecialFamilia(PantPrincipalController pantPrincipal, CargaItemEspecialFamiliaView view,
			CargaItemEspecialFamiliaModel model, PermisosInterface permisos) throws Exception {
		super(pantPrincipal, view, model, permisos);
		view.setModoFamilia(true);

		view.txtArticuloID.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				view.txtArticuloID.setText(view.txtArticuloID.getText().toUpperCase());

				if (addFamiliaCargado(view.txtArticuloID.getText())) {
					view.txtArticuloID.setText("");
					getView().txtArticuloID.requestFocus();
				}
			}
		});

	}

	protected Boolean addFamiliaCargado(String idFamilia) {

		if (!idFamilia.equals("")) {
			StocCa01 nuevaFamilia = StocCa01DAO.findById(idFamilia);

			if (!isFamiliaCarga(nuevaFamilia)) {
				if (FamiliaBusiness.estaFamiliaEnLista(nuevaFamilia.getCa01Clasif1(), getModel().getListaID())) {

					getModel().addFamiliaCargado(nuevaFamilia);
					getView().tableFalimia.addRow(
							new Object[] { "", nuevaFamilia.getCa01Clasif1(), nuevaFamilia.getCa01Nombre(), "" });
					getView().tableFalimia.adjustColumns();
					return true;

				} else {
					popUpError.showError(getView().txtArticuloID, "La familia no pertenece a la lista Original");
				}
			}
		}

		return false;
	}

	private boolean isFamiliaCarga(StocCa01 nuevaFamilia) {
		for (StocCa01 familiaCargada : getModel().getListaFamiliaCargada()) {
			if (familiaCargada.getCa01Clasif1().equals(nuevaFamilia.getCa01Clasif1())) {
				return true;
			}
		}
		return false;
	}

	private String buscarFamilia() throws Exception {
		String retorno = "";
		BuscarFamiliaDialog buscarFamiliaDialog = new BuscarFamiliaDialog(getPantallaPrincipal(),
				getModel().getListaID());
		buscarFamiliaDialog.iniciar();
		if (buscarFamiliaDialog.getNroFamilia() != null) {
			retorno = String.valueOf(buscarFamiliaDialog.getNroFamilia());
		}

		return retorno;
	}

	@Override
	protected void cargaPantalla() throws Exception {
		getModel().reset();
		getView().tableFalimia.clear();
		getView().setModeEdicion(getModel().isEdicion());

		getView().txtArticuloID.setText("");
		getView().txtDesc1.setText("");
		getView().txtDesc2.setText("");
		getView().txtReferencia.setText("");
		getView().dateFechaDesde.clear();
		getView().dateFechaHasta.clear();
		getView().txtComision.setText("");

		if (getModel().isEdicion()) {

			addFamiliaCargado(getModel().getDescuentoxFamiliaModif().getPricCa01Clasif1());
			if ((getModel().getDescuentoxFamiliaModif().getPricFamiliaDescuento1() != null)
					&& (getModel().getDescuentoxFamiliaModif().getPricFamiliaDescuento1().doubleValue() > 0.0)) {
				getView().txtDesc1.setText(Common.double2String(
						getModel().getDescuentoxFamiliaModif().getPricFamiliaDescuento1().doubleValue()));
			}

			if ((getModel().getDescuentoxFamiliaModif().getPricFamiliaDescuento2() != null)
					&& (getModel().getDescuentoxFamiliaModif().getPricFamiliaDescuento2().doubleValue() > 0.0)) {
				getView().txtDesc2.setText(Common.double2String(
						getModel().getDescuentoxFamiliaModif().getPricFamiliaDescuento2().doubleValue()));
			}

			if (getModel().getDescuentoxFamiliaModif().getPricFamiliaFechaDesde() != null) {
				getView().dateFechaDesde.setDate(getModel().getDescuentoxFamiliaModif().getPricFamiliaFechaDesde());
			}

			if (getModel().getDescuentoxFamiliaModif().getPricFamiliaFechaHasta() != null) {
				getView().dateFechaHasta.setDate(getModel().getDescuentoxFamiliaModif().getPricFamiliaFechaHasta());
			}

			getView().txtComision.setText(Common
					.double2String(getModel().getDescuentoxFamiliaModif().getPricFamiliaComision().doubleValue()));

			if (getModel().getDescuentoxFamiliaModif().getPricReferencia() != null) {
				getView().txtReferencia.setText(getModel().getDescuentoxFamiliaModif().getPricReferencia());
			}
		}

		if (getModel().isEdicion()) {
			getView().txtDesc1.requestFocus();
		} else {
			getView().txtArticuloID.requestFocus();
		}
	}

	@Override
	public void ejecutarAccion(String accion) {
		if (accion.equals(ConstantesRP.AccionesCargaItemFamilia.CANCELAR.toString())) {
			getModel().setAccion("");
			cerrarVentana();
		}

		if (accion.equals(ConstantesRP.AccionesCargaItemFamilia.ACEPTAR.toString())) {
			getModel().setAccion("ACEPTO");
			try {
				if (validar()) {
					cerrarVentana();
				}
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al validar");
			}
		}

		if (accion.equals(ConstantesRP.AccionesCargaItemFamilia.ELIMINAR_FAMILIA.toString())) {

			getModel().deleteFamiliaCargado(
					(String) getView().tableFalimia.getValueAt(getView().tableFalimia.getSelectedRow(), 0));

			int modelRow = getView().tableFalimia.convertRowIndexToModel(getView().tableFalimia.getSelectedRow());

			DefaultTableModel dm = (DefaultTableModel) getView().tableFalimia.getModel();
			dm.removeRow(modelRow);

			getView().tableFalimia.adjustColumns();
		}
	}

	@Override
	protected String getNombrePantalla() {
		return "Carga de Registro de Familia";
	}

	public List<DescuentoXFamilias> getRegistro() throws Exception {

		List<DescuentoXFamilias> listaRegistro = new ArrayList<DescuentoXFamilias>();

		BigDecimal desc1 = null;
		BigDecimal desc2 = null;
		Double comision = 0.0;

		if (!getView().txtDesc1.getText().equals("")) {
			desc1 = new BigDecimal(Common.String2Double(getView().txtDesc1.getText()), MathContext.DECIMAL64);
		}

		if (!getView().txtDesc2.getText().equals("")) {
			desc2 = new BigDecimal(Common.String2Double(getView().txtDesc2.getText()), MathContext.DECIMAL64);
		}

		if (!getView().txtComision.getText().equals("")) {
			comision = new Double(Common.String2Double(getView().txtComision.getText()));
		}

		for (StocCa01 familia : getModel().getListaFamiliaCargada()) {
			DescuentoXFamilias registro;
			if (!getModel().isEdicion()) {
				registro = new DescuentoXFamilias();
			} else {
				registro = getModel().getDescuentoxFamiliaModif();
			}

			registro.setPricFamiliaListaPrecvta(getModel().getListaID());
			registro.setPricFamiliaCliente(getModel().getCliente().getClieCliente());

			registro.setPricCa01Clasif1(familia.getCa01Clasif1());
			registro.setNombreFamilia(familia.getCa01Nombre());

			registro.setPricFamiliaDescuento1(desc1);
			registro.setPricFamiliaDescuento2(desc2);
			registro.setPricFamiliaFechaDesde(getView().dateFechaDesde.getDate());
			registro.setPricFamiliaFechaHasta(getView().dateFechaHasta.getDate());
			registro.setPricFamiliaComision(new BigDecimal(comision, MathContext.DECIMAL64));
			registro.setPricReferencia(getView().txtReferencia.getText());

			listaRegistro.add(registro);
		}

		return listaRegistro;
	}

	@Override
	protected String getResultado() throws Exception {
		return getModel().getAccion();
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F2) && getView().txtArticuloID.hasFocus()) {
			retorno = true;
			try {
				addFamiliaCargado(buscarFamilia());
			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al cargar la busqueda de Articulo / Familia");
			}
		}

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F3) && getView().dateFechaDesde.hasFocus()) {
			getView().dateFechaDesde.setText(FechaManagerUtil.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(),
					FechaManagerUtil.FORMATO_FECHA));
		}

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F3) && getView().dateFechaHasta.hasFocus()) {
			getView().dateFechaHasta.setText("01/01/2100");
		}

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_ENTER) && getView().txtArticuloID.hasFocus()) {
			getView().txtArticuloID.setText(getView().txtArticuloID.getText().toUpperCase());

			if (addFamiliaCargado(getView().txtArticuloID.getText())) {
				getView().txtArticuloID.setText("");
				getView().txtArticuloID.requestFocus();
			}
		}

		// skip la grilla
		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_TAB)
				&& (getView().txtArticuloID.hasFocus() || getView().tableFalimia.hasFocus())) {
			getView().txtDesc1.requestFocus();
			retorno = true;
		}

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_TAB) && (ke.isShiftDown())
				&& (getView().txtDesc1.hasFocus() || getView().tableFalimia.hasFocus())) {
			getView().txtArticuloID.requestFocus();
			retorno = true;
		}

		return retorno;
	}

	private Boolean validar() throws Exception {
		// Primero valido que los campos esten bien cargados
		if (getModel().getListaFamiliaCargada().size() == 0) {
			popUpError.showError(getView().txtArticuloID, "Falta cargar un producto valido");
			getView().txtArticuloID.requestFocus();
			return false;
		}

		if ((getView().txtDesc1.getText().equals("")) && (getView().txtPrecio.getText().equals(""))) {
			popUpError.showError(getView().txtDesc1, "Falta cargar el porcentage de descuento o el precio");
			getView().txtDesc1.requestFocus();
			return false;
		}

		if (getView().dateFechaDesde.getDate() == null) {
			popUpError.showError(getView().dateFechaDesde, "La fecha Desde no puede estar Vacia");
			getView().dateFechaDesde.requestFocus();
			return false;
		}

		if (getView().dateFechaHasta.getDate() == null) {
			popUpError.showError(getView().dateFechaHasta, "La fecha Hasta no puede estar Vacia");
			getView().dateFechaHasta.requestFocus();
			return false;
		}

		Date dFechaDesde = getView().dateFechaDesde.getDate();
		Date dFechaHasta = getView().dateFechaHasta.getDate();

		if (FechaManagerUtil.getDateDiff(dFechaDesde, dFechaHasta, TimeUnit.DAYS) > 0) {
			popUpError.showError(getView().dateFechaDesde, "La fecha desde debe ser menor a la hasta");
			getView().dateFechaDesde.requestFocus();
			return false;
		}

		// Evaluo si hay overlap
		// TODO VER EDICION
		String msg = "";
		for (DescuentoXFamilias descuento : getRegistro()) {

			for (int i = 0; i < getModel().getTableModel().getRowCount(); i++) {
				DescuentoXFamilias regTablaFamilia = (DescuentoXFamilias) getModel().getTableModel().getValueAt(i,
						CargaPrecioView.COL_REGISTRO_FAMILIA);
				if (regTablaFamilia.getPricCa01Clasif1().equals(descuento.getPricCa01Clasif1())) {

					if (OverloadManager.isCase1(regTablaFamilia, descuento)
							|| OverloadManager.isCase2(regTablaFamilia, descuento)
							|| OverloadManager.isCase3(regTablaFamilia, descuento)
							|| OverloadManager.isCase4(regTablaFamilia, descuento)
							|| OverloadManager.isCase5(regTablaFamilia, descuento)
							|| OverloadManager.isCase6(regTablaFamilia, descuento)
							|| OverloadManager.isCase7(regTablaFamilia, descuento)) {
						msg += String.format("%s: %s - %s\n", regTablaFamilia.getPricCa01Clasif1(),
								FechaManagerUtil.Date2String(regTablaFamilia.getPricFamiliaFechaDesde()),
								FechaManagerUtil.Date2String(regTablaFamilia.getPricFamiliaFechaHasta()));
					}

				}
			}

			if (!msg.equals("")) {
				return (Dialog.showConfirmDialog("Hay Solapamiento de fechas\n\n" + msg + "¿Continuamos?",
						"Hay Solapamiento de fechas", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
						null, null) == JOptionPane.YES_OPTION);
			}
		}

		/*
		 * // Valido si el rango de fecha ya esta cargado for (int i = 0; i <
		 * getModel().getTableModel().getRowCount(); i++) { DescuentoXFamilias
		 * registroTabla = (DescuentoXFamilias) getModel().getTableModel().getValueAt(i,
		 * CargaPrecioView.COL_REGISTRO_FAMILIA);
		 * 
		 * if (getModel().getFamiliaID().equals(registroTabla.getPricCa01Clasif1())) {
		 * 
		 * if (registroTabla.getPricFamiliaId() !=
		 * getModel().getRegistroFamilia().getPricFamiliaId()) { // Si el desde que
		 * cargo esta entre las dos fecha del registro if
		 * ((FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(),
		 * registroTabla.getPricFamiliaFechaDesde(), TimeUnit.DAYS) >= 0) &&
		 * (FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(),
		 * registroTabla.getPricFamiliaFechaHasta(), TimeUnit.DAYS) <= 0)) {
		 * popUpError.showError(getView().dateFechaDesde,
		 * "Hay solapamiento de Rango de Fecha.\nYa esta carga el dia " +
		 * FechaManagerUtil.Date2String(getView().dateFechaDesde.getDate()));
		 * getView().dateFechaDesde.requestFocus(); return false; }
		 * 
		 * // Si el hasta que cargo esta entre las dos fecha del registro if
		 * ((FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(),
		 * registroTabla.getPricFamiliaFechaDesde(), TimeUnit.DAYS) >= 0) &&
		 * (FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(),
		 * registroTabla.getPricFamiliaFechaHasta(), TimeUnit.DAYS) <= 0)) {
		 * popUpError.showError(getView().dateFechaDesde,
		 * "Hay solapamiento de Rango de Fecha.\nYa esta carga el dia " +
		 * FechaManagerUtil.Date2String(getView().dateFechaHasta.getDate()));
		 * getView().dateFechaDesde.requestFocus(); return false; }
		 * 
		 * // Si el desde que cargo esta antes que el desde del registro y el hasta que
		 * // cargo es mayor que el del registro if
		 * ((FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(),
		 * registroTabla.getPricFamiliaFechaDesde(), TimeUnit.DAYS) <= 0) &&
		 * (FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(),
		 * registroTabla.getPricFamiliaFechaHasta(), TimeUnit.DAYS) >= 0)) {
		 * popUpError.showError(getView().dateFechaDesde,
		 * "Hay solapamiento de Rango de Fecha.\nYa esta carga el rango " +
		 * FechaManagerUtil.Date2String(registroTabla.getPricFamiliaFechaDesde()) +
		 * " - " +
		 * FechaManagerUtil.Date2String(registroTabla.getPricFamiliaFechaHasta()));
		 * getView().dateFechaDesde.requestFocus(); return false; } } }
		 * 
		 * }
		 */

		return true;
	}

	public boolean iniciarAlta(CcobClie clienteCargado, VentLipv ventLipv, DefaultTableModel model) throws Exception {
		getModel().setCliente(clienteCargado);
		getModel().setListaID(ventLipv);
		getModel().setTableModel(model);
		getModel().setRegistroToModif(null);

		return !iniciar().equals("");
	}

	public boolean iniciarModif(CcobClie clienteCargado, VentLipv ventLipv, DefaultTableModel model,
			DescuentoXFamilias registroToModif) throws Exception {
		getModel().setCliente(clienteCargado);
		getModel().setListaID(ventLipv);
		getModel().setTableModel(model);
		getModel().setRegistroToModif(registroToModif);

		return !iniciar().equals("");
	}

}
