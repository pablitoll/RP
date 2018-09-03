package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.StocCa01;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.FamiliaBusiness;
import ar.com.rollpaper.pricing.dao.StocCa01DAO;
import ar.com.rollpaper.pricing.model.CargaItemEspecialFamiliaModel;
import ar.com.rollpaper.pricing.ui.BuscarFamiliaDialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaItemEspecialView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.error.popUpError;
import ar.com.rp.ui.interfaces.PermisosInterface;
import ar.com.rp.ui.pantalla.BaseControllerDialog;

public class CargaItemEspecialFamilia extends BaseControllerDialog<PantPrincipalController, CargaItemEspecialView, CargaItemEspecialFamiliaModel> {

	public DescuentoXFamilias getRegistro() throws Exception {

		DescuentoXFamilias registro = getModel().getRegistroFamilia();

		registro.setPricCa01Clasif1(getModel().getFamiliaID());

		if (!getView().txtDesc1.getText().equals("")) {
			registro.setPricFamiliaDescuento1(new BigDecimal(Common.String2Double(getView().txtDesc1.getText()), MathContext.DECIMAL64));
		} else {
			registro.setPricFamiliaDescuento1(null);
		}
		if (!getView().txtDesc2.getText().equals("")) {
			registro.setPricFamiliaDescuento2(new BigDecimal(Common.String2Double(getView().txtDesc2.getText()), MathContext.DECIMAL64));
		} else {
			registro.setPricFamiliaDescuento2(null);
		}
		registro.setPricFamiliaFechaDesde(getView().dateFechaDesde.getDate());
		registro.setPricFamiliaFechaHasta(getView().dateFechaHasta.getDate());

		
		Double comision = 0.0;
		if(!getView().txtComision.getText().equals("")) {
			comision = new Double(Common.String2Double(getView().txtComision.getText()));
		}

		registro.setPricFamiliaComision(new BigDecimal(comision, MathContext.DECIMAL64));
		
		registro.setPricReferencia(getView().txtReferencia.getText());

		return registro;
	}

	public void setRegistro(DescuentoXFamilias registro) {
		getModel().setFamiliaCargado(null);
		getModel().setRegistro(registro);

		if ((registro.getPricCa01Clasif1() != null) && !registro.getPricCa01Clasif1().equals("")) {
			getModel().setFamiliaCargado(StocCa01DAO.findById(registro.getPricCa01Clasif1()));
		}
	}

	public CargaItemEspecialFamilia(PantPrincipalController pantPrincipal, CargaItemEspecialView view, CargaItemEspecialFamiliaModel model, PermisosInterface permisos)
			throws Exception {
		super(pantPrincipal, view, model, permisos);
		view.lblLabelArticulo.setText("Familia ID:");
		view.txtPrecio.setVisible(false);
		view.cbMoneda.setVisible(false);
		view.lblDescripcion.setVisible(false);
		view.lblLabelDescipcion.setVisible(false);
		view.lblLabelPrecio.setVisible(false);
		view.lblLabelMoneda.setVisible(false);
		view.lblEstaEnLista.setVisible(false);

		view.txtArticuloID.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				view.txtArticuloID.setText(view.txtArticuloID.getText().toUpperCase());

				String id = view.txtArticuloID.getText();

				if (!id.equals("")) {
					StocCa01 familiaCargado = StocCa01DAO.findById(id);
					getModel().setFamiliaCargado(familiaCargado);
				}
				RefrescarDatosFamilia();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}

	@Override
	protected void cargaPantalla() throws Exception {
		getView().lblArticuloID.setVisible(getModel().isEdicion());
		getView().txtArticuloID.setVisible(!getModel().isEdicion());

		getView().lblArticuloID.setText("");
		getView().txtArticuloID.setText("");
		getView().txtDesc1.setText("");
		getView().txtDesc2.setText("");
		getView().txtReferencia.setText("");
		getView().dateFechaDesde.clear();
		getView().dateFechaHasta.clear();
		getView().txtComision.setText("");

		if (getModel().isEdicion()) {

			getView().lblArticuloID.setText(String.valueOf(getModel().getRegistroFamilia().getPricFamiliaListaPrecvta()));

			if ((getModel().getRegistroFamilia().getPricFamiliaDescuento1() != null) && (getModel().getRegistroFamilia().getPricFamiliaDescuento1().doubleValue() > 0.0)) {
				getView().txtDesc1.setText(Common.double2String(getModel().getRegistroFamilia().getPricFamiliaDescuento1().doubleValue()));
			}

			if ((getModel().getRegistroFamilia().getPricFamiliaDescuento2() != null) && (getModel().getRegistroFamilia().getPricFamiliaDescuento2().doubleValue() > 0.0)) {
				getView().txtDesc2.setText(Common.double2String(getModel().getRegistroFamilia().getPricFamiliaDescuento2().doubleValue()));
			}

			if (getModel().getRegistroFamilia().getPricFamiliaFechaDesde() != null) {
				getView().dateFechaDesde.setDate(getModel().getRegistroFamilia().getPricFamiliaFechaDesde());
			}

			if (getModel().getRegistroFamilia().getPricFamiliaFechaHasta() != null) {
				getView().dateFechaHasta.setDate(getModel().getRegistroFamilia().getPricFamiliaFechaHasta());
			}

			getView().txtComision.setText(Common.double2String(getModel().getRegistroFamilia().getPricFamiliaComision().doubleValue()));

			if (getModel().getRegistroFamilia().getPricReferencia() != null) {
				getView().txtReferencia.setText(getModel().getRegistroFamilia().getPricReferencia());
			}
		}

		RefrescarDatosFamilia();

		if (getModel().isEdicion()) {
			getView().txtDesc1.requestFocus();
		} else {
			getView().txtArticuloID.requestFocus();
		}
	}

	@Override
	protected String getResultado() throws Exception {
		return getModel().getAccion();
	}

	@Override
	protected String getNombrePantalla() {
		return "Carga de Registro de Familia";
	}

	@Override
	public void ejecutarAccion(String accion) {
		if (accion.equals(ConstantesRP.AccionesCargaItemFamilia.CANCELAR.toString())) {
			getModel().setAccion("");
			cerrarVentana();
		}

		if (accion.equals(ConstantesRP.AccionesCargaItemFamilia.ACEPTAR.toString())) {
			getModel().setAccion("ACEPTO");
			if (validar()) {
				cerrarVentana();
			}
		}
	}

	private Boolean validar() {
		// Primero valido que los campos esten bien cargados
		if (!getModel().isEdicion() && getView().lblNombre.getText().contains("S/D")) {
			popUpError.showError(getView().txtArticuloID, "Falta cargar un producto valido");
			getView().txtArticuloID.requestFocus();
			return false;
		}

		if (!FamiliaBusiness.estaFamiliaEnLista(getModel().getFamiliaID(), getModel().getListaID())) {
			popUpError.showError(getView().txtArticuloID, "La familia no pertenece a la lista Original");
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

		// Valido si el rango de fecha ya esta cargado
		for (int i = 0; i < getModel().getTableModel().getRowCount(); i++) {
			DescuentoXFamilias registroTabla = (DescuentoXFamilias) getModel().getTableModel().getValueAt(i, CargaPrecioView.COL_REGISTRO_FAMILIA);

			if (getModel().getFamiliaID().equals(registroTabla.getPricCa01Clasif1())) {

				if (registroTabla.getPricFamiliaId() != getModel().getRegistroFamilia().getPricFamiliaId()) {
					// Si el desde que cargo esta entre las dos fecha del registro
					if ((FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(), registroTabla.getPricFamiliaFechaDesde(), TimeUnit.DAYS) >= 0)
							&& (FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(), registroTabla.getPricFamiliaFechaHasta(), TimeUnit.DAYS) <= 0)) {
						popUpError.showError(getView().dateFechaDesde,
								"Hay solapamiento de Rango de Fecha.\nYa esta carga el dia " + FechaManagerUtil.Date2String(getView().dateFechaDesde.getDate()));
						getView().dateFechaDesde.requestFocus();
						return false;
					}

					// Si el hasta que cargo esta entre las dos fecha del registro
					if ((FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(), registroTabla.getPricFamiliaFechaDesde(), TimeUnit.DAYS) >= 0)
							&& (FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(), registroTabla.getPricFamiliaFechaHasta(), TimeUnit.DAYS) <= 0)) {
						popUpError.showError(getView().dateFechaDesde,
								"Hay solapamiento de Rango de Fecha.\nYa esta carga el dia " + FechaManagerUtil.Date2String(getView().dateFechaHasta.getDate()));
						getView().dateFechaDesde.requestFocus();
						return false;
					}

					// Si el desde que cargo esta antes que el desde del registro y el hasta que
					// cargo es mayor que el del registro
					if ((FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(), registroTabla.getPricFamiliaFechaDesde(), TimeUnit.DAYS) <= 0)
							&& (FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(), registroTabla.getPricFamiliaFechaHasta(), TimeUnit.DAYS) >= 0)) {
						popUpError.showError(getView().dateFechaDesde,
								"Hay solapamiento de Rango de Fecha.\nYa esta carga el rango " + FechaManagerUtil.Date2String(registroTabla.getPricFamiliaFechaDesde()) + " - "
										+ FechaManagerUtil.Date2String(registroTabla.getPricFamiliaFechaHasta()));
						getView().dateFechaDesde.requestFocus();
						return false;
					}
				}
			}
		}

		return true;
	}

	public String getNombreItem() {
		return getModel().getNombreItem();
	}

	private void RefrescarDatosFamilia() {
		getView().lblNombre.setText(getModel().getNombreItem());

		if (!getModel().getFamiliaID().equals("") && !FamiliaBusiness.estaFamiliaEnLista(getModel().getFamiliaID(), getModel().getListaID())) {
			getView().lblEstaEnLista.setText("La Familia no esta en la lista");
			getView().lblEstaEnLista.setVisible(true);
		} else {
			getView().lblEstaEnLista.setVisible(false);
		}
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F2) && getView().txtArticuloID.hasFocus()) {
			retorno = true;
			try {
				String id = buscarFamilia();
				if (!id.equals("")) {
					getView().txtArticuloID.setText(id);
					getView().txtDesc1.requestFocus();
				}

			} catch (Exception e) {
				ManejoDeError.showError(e, "Error al cargar la busqueda de Articulo / Familia");
			}
		}

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F3) && getView().dateFechaDesde.hasFocus()) {
			getView().dateFechaDesde.setText(FechaManagerUtil.Date2StringGenerica(FechaManagerUtil.getDateTimeFromPC(), FechaManagerUtil.FORMATO_FECHA));
		}

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F3) && getView().dateFechaHasta.hasFocus()) {
			getView().dateFechaHasta.setText("01/01/2100");
		}

		return retorno;
	}

	private String buscarFamilia() throws Exception {
		String retorno = "";
		BuscarFamiliaDialog buscarFamiliaDialog = new BuscarFamiliaDialog(getPantallaPrincipal(), getModel().getListaID());
		buscarFamiliaDialog.iniciar();
		if (buscarFamiliaDialog.getNroFamilia() != null) {
			retorno = String.valueOf(buscarFamiliaDialog.getNroFamilia());
			getView().txtArticuloID.setText(retorno);
		}

		return retorno;
	}

}
