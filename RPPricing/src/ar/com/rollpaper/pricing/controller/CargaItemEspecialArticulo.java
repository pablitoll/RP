package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.SistMone;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.SistMoneDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.model.CargaItemEspecialArticuloModel;
import ar.com.rollpaper.pricing.ui.BuscarArticuloDialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaItemEspecialView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.componentes.ItemComboBox;
import ar.com.rp.ui.error.popUpError;
import ar.com.rp.ui.interfaces.PermisosInterface;
import ar.com.rp.ui.pantalla.BaseControllerDialog;

public class CargaItemEspecialArticulo extends BaseControllerDialog<PantPrincipalController, CargaItemEspecialView, CargaItemEspecialArticuloModel> {

	public PreciosEspeciales getRegistro() throws Exception {

		PreciosEspeciales registro = getModel().getRegistro();

		registro.setPricArticulo(getModel().getArticuloID());

		if (getView().txtDesc1.getImporte() > 0.0) {
			registro.setPricDescuento1(new BigDecimal(getView().txtDesc1.getImporte(), MathContext.DECIMAL64));
		} else {
			registro.setPricDescuento1(null);
		}

		if (getView().txtDesc2.getImporte() > 0.0) {
			registro.setPricDescuento2(new BigDecimal(getView().txtDesc2.getImporte(), MathContext.DECIMAL64));
		} else {
			registro.setPricDescuento2(null);
		}

		registro.setPricFechaDesde(getView().dateFechaDesde.getDate());
		registro.setPricFechaHasta(getView().dateFechaHasta.getDate());
		
		if (getView().cbMoneda.getSelectedIndex() > 0) {
			registro.setPricMoneda(((SistMone) getView().cbMoneda.getSelectedItem()).getMoneMoneda());
			registro.setPricPrecio(new BigDecimal(getView().txtPrecio.getImporte(), MathContext.DECIMAL64));
		} else {
			registro.setPricMoneda(null);
			registro.setPricPrecio(null);
		}
		registro.setPricComision(new BigDecimal(getView().txtComision.getImporte(), MathContext.DECIMAL64));

		registro.setPricReferencia(getView().txtReferencia.getText());

		return registro;

	}

	public void setRegistro(PreciosEspeciales registro) {
		getModel().setArticuloCargado(null);
		getModel().setRegistro(registro);

		if (registro.getPricArticulo() > 0) {
			getModel().setArticuloCargado(StocArtsDAO.findById(registro.getPricArticulo()));
		}
	}

	@SuppressWarnings("unchecked")
	public CargaItemEspecialArticulo(PantPrincipalController pantPrincipal, CargaItemEspecialView view, CargaItemEspecialArticuloModel model, PermisosInterface permisos)
			throws Exception {
		super(pantPrincipal, view, model, permisos);
		view.lblLabelArticulo.setText("Articulo ID:");
		view.txtArticuloID.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				getArticuloByEmp(view.txtArticuloID.getText());
				RefrescarDatosArticulo();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		getView().cbMoneda.addItem(new ItemComboBox(-1, "Sin Seleccionar"));

		for (SistMone reg : SistMoneDAO.getList()) {
			getView().cbMoneda.addItem(reg);
		}
	}

	@Override
	protected void cargaPantalla() throws Exception {
		getView().lblArticuloID.setVisible(getModel().isEdicion());
		getView().txtArticuloID.setVisible(!getModel().isEdicion());

		getView().lblArticuloID.setText("");
		getView().txtArticuloID.setText("");
		getView().txtDesc1.limpiar();
		getView().txtDesc2.limpiar();
		getView().txtPrecio.limpiar();
		getView().txtReferencia.setText("");
		getView().dateFechaDesde.clear();
		getView().dateFechaHasta.clear();
		getView().txtComision.limpiar();
		getView().cbMoneda.setSelectedIndex(0);

		if (getModel().isEdicion()) {

			getView().lblArticuloID.setText(getModel().getArticuloIDEmp());

			if ((getModel().getRegistro().getPricDescuento1() != null) && (getModel().getRegistro().getPricDescuento1().doubleValue() > 0.0)) {
				getView().txtDesc1.setImporte(getModel().getRegistro().getPricDescuento1().doubleValue());
			}
			if ((getModel().getRegistro().getPricDescuento2() != null) && (getModel().getRegistro().getPricDescuento2().doubleValue() > 0.0)) {
				getView().txtDesc2.setImporte(getModel().getRegistro().getPricDescuento2().doubleValue());
			}

			if (getModel().getRegistro().getPricMoneda() != null) {
				getView().cbMoneda.setSelectedItem(SistMoneDAO.findById(getModel().getRegistro().getPricMoneda()));

				if (getModel().getRegistro().getPricPrecio() != null) {
					getView().txtPrecio.setImporte(getModel().getRegistro().getPricPrecio().doubleValue());
				}
			}
			if (getModel().getRegistro().getPricFechaDesde() != null) {
				getView().dateFechaDesde.setDate(getModel().getRegistro().getPricFechaDesde());
			}

			if (getModel().getRegistro().getPricFechaHasta() != null) {
				getView().dateFechaHasta.setDate(getModel().getRegistro().getPricFechaHasta());
			}

			getView().txtComision.setImporte(getModel().getRegistro().getPricComision().doubleValue());

			if (getModel().getRegistro().getPricReferencia() != null) {
				getView().txtReferencia.setText(getModel().getRegistro().getPricReferencia());
			}
		}

		RefrescarDatosArticulo();

		getView().txtPrecio.setVisible(true);
		getView().cbMoneda.setVisible(true);
		getView().lblDescripcion.setVisible(true);
		getView().lblLabelDescipcion.setVisible(true);
		getView().lblLabelPrecio.setVisible(true);
		getView().lblLabelMoneda.setVisible(true);

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
		return "Carga de Registro de Precios";
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
			return false;
		}

		if ((getView().txtDesc1.isEmpty()) && (getView().txtPrecio.isEmpty())) {
			popUpError.showError(getView().txtDesc1, "Falta cargar el porcentage de descuento o el precio");
			return false;
		}

		if ((!getView().txtDesc1.isEmpty()) && (!getView().txtPrecio.isEmpty())) {
			popUpError.showError(getView().txtDesc1, "No se puede cargar un decuento y precio simultaneamente");
			return false;
		}

		if (!getView().txtPrecio.isEmpty() && (getView().cbMoneda.getSelectedIndex() == 0)) {
			popUpError.showError(getView().cbMoneda, "Falta cargar la moneda");
			return false;
		}

		if (getView().txtPrecio.isEmpty() && (getView().cbMoneda.getSelectedIndex() != 0)) {
			popUpError.showError(getView().cbMoneda, "Falta cargar el precio para la moneda selecionada");
			return false;
		}

		if (getView().dateFechaDesde.getDate() == null) {
			popUpError.showError(getView().dateFechaDesde, "La fecha Desde no puede estar Vacia");
			return false;
		}

		if (getView().dateFechaHasta.getDate() == null) {
			popUpError.showError(getView().dateFechaHasta, "La fecha Hasta no puede estar Vacia");
			return false;
		}

		Date dFechaDesde = getView().dateFechaDesde.getDate();
		Date dFechaHasta = getView().dateFechaHasta.getDate();

		if (FechaManagerUtil.getDateDiff(dFechaDesde, dFechaHasta, TimeUnit.DAYS) > 0) {
			popUpError.showError(getView().dateFechaDesde, "La fecha desde debe ser menor a la hasta");
			return false;
		}

		// Valido si el rango de fecha ya esta cargado
		for (int i = 0; i < getModel().getTableModel().getRowCount(); i++) {
			PreciosEspeciales registroTabla = (PreciosEspeciales) getModel().getTableModel().getValueAt(i, CargaPrecioView.COL_REGISTRO_ESPECIFICO);

			if (getModel().getArticuloID() == registroTabla.getPricArticulo()) {

				if (registroTabla.getPricPreciosEspecialesId() != getModel().getRegistro().getPricPreciosEspecialesId()) {
					//Si el desde que cargo esta entre las dos fecha del registro
					if ((FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(), registroTabla.getPricFechaDesde(), TimeUnit.DAYS) >= 0)
							&& (FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(), registroTabla.getPricFechaHasta(), TimeUnit.DAYS) <= 0)) {
						popUpError.showError(getView().dateFechaDesde,
								"Hay solapamiento de Rango de Fecha.\nYa esta carga el dia " + FechaManagerUtil.Date2String(getView().dateFechaDesde.getDate()));
						return false;
					}

					//Si el hasta que cargo esta entre las dos fecha del registro
					if ((FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(), registroTabla.getPricFechaDesde(), TimeUnit.DAYS) >= 0)
							&& (FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(), registroTabla.getPricFechaHasta(), TimeUnit.DAYS) <= 0)) {
						popUpError.showError(getView().dateFechaDesde,
								"Hay solapamiento de Rango de Fecha.\nYa esta carga el dia " + FechaManagerUtil.Date2String(getView().dateFechaHasta.getDate()));
						return false;
					}
					
					//Si el desde que cargo esta antes que el desde del registro y el hasta que cargo es mayor que el del registro
					if ((FechaManagerUtil.getDateDiff(getView().dateFechaDesde.getDate(), registroTabla.getPricFechaDesde(), TimeUnit.DAYS) <= 0)
							&& (FechaManagerUtil.getDateDiff(getView().dateFechaHasta.getDate(), registroTabla.getPricFechaHasta(), TimeUnit.DAYS) >= 0)) {
						popUpError.showError(getView().dateFechaDesde,
								"Hay solapamiento de Rango de Fecha.\nYa esta carga el rango " + FechaManagerUtil.Date2String(registroTabla.getPricFechaDesde()) + " - " + FechaManagerUtil.Date2String(registroTabla.getPricFechaHasta()));
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

	protected StocArts getArticuloByEmp(String idEmp) {
		StocArts articulo = StocArtsDAO.getArticulo(idEmp);
		getModel().setArticuloCargado(articulo);

		return articulo;
	}

	private StocArts buscarArticulo() throws Exception {
		BuscarArticuloDialog buscarClienteDialog = new BuscarArticuloDialog(getPantallaPrincipal());
		buscarClienteDialog.iniciar();
		return buscarClienteDialog.getNroArticulo();
	}

	private void RefrescarDatosArticulo() {
		getView().lblDescripcion.setText(getModel().getDescripcionItem());
		getView().lblNombre.setText(getModel().getNombreItem());
	}

	public String getDescripcionItem() {
		return getModel().getDescripcionItem();
	}

	public String getUnidadItem() {
		return getModel().getUnidadItem();
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);

		if (!retorno && (ke.getKeyCode() == KeyEvent.VK_F2) && getView().txtArticuloID.hasFocus()) {
			retorno = true;
			try {
				getModel().setArticuloCargado(buscarArticulo());

				RefrescarDatosArticulo();
				getView().txtArticuloID.setText(getModel().getArticuloIDEmp());

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

	public String getArticuloIDMostrar() {
		return getModel().getArticuloIDEmp();
	}

}
