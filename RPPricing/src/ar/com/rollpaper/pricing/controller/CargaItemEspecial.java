package ar.com.rollpaper.pricing.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.SistMone;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.SistMoneDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.model.CargaItemEspecialModel;
import ar.com.rollpaper.pricing.ui.BuscarArticuloDialog;
import ar.com.rollpaper.pricing.ui.BuscarFamiliaDialog;
import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rollpaper.pricing.view.CargaItemEspecialView;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.componentes.ItemComboBox;
import ar.com.rp.ui.error.popUpError;
import ar.com.rp.ui.interfaces.PermisosInterface;
import ar.com.rp.ui.pantalla.BaseControllerDialog;

public class CargaItemEspecial extends BaseControllerDialog<PantPrincipalController, CargaItemEspecialView, CargaItemEspecialModel> {

	public Object getRegistro() throws Exception {
		if (getModel().getRegistro() != null) {

			PreciosEspeciales registro = getModel().getRegistro();
			if (!getModel().isEdicion()) {
				registro.setPricArticulo(Integer.valueOf(getView().txtArticuloID.getText()));
			} else {
				registro.setPricArticulo(Integer.valueOf(getView().lblArticuloID.getText()));
			}
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

			registro.setPricListaPrecvta(1);
			if (getView().cbMoneda.getSelectedIndex() > 0) {
				registro.setPricMoneda(((SistMone) getView().cbMoneda.getSelectedItem()).getMoneMoneda());
				registro.setPricPrecio(new BigDecimal(getView().txtPrecio.getImporte(), MathContext.DECIMAL64));
			} else {
				registro.setPricMoneda(null);
				registro.setPricPrecio(null);
			}

			registro.setPricReferencia("." + getView().txtReferencia.getText());

			return registro;
		} else {
			DescuentoXFamilias registro = getModel().getRegistroFamilia();
			if (!getModel().isEdicion()) {
				registro.setPricFamiliaListaPrecvta(Integer.valueOf(getView().txtArticuloID.getText()));
			} else {
				registro.setPricFamiliaListaPrecvta(Integer.valueOf(getView().lblArticuloID.getText()));
			}

			if (getView().txtDesc1.getImporte() > 0.0) {
				registro.setPricFamiliaDescuento1(new BigDecimal(getView().txtDesc1.getImporte(), MathContext.DECIMAL64));
			} else {
				registro.setPricFamiliaDescuento1(null);
			}
			if (getView().txtDesc2.getImporte() > 0.0) {
				registro.setPricFamiliaDescuento2(new BigDecimal(getView().txtDesc2.getImporte(), MathContext.DECIMAL64));
			} else {
				registro.setPricFamiliaDescuento2(null);
			}
			registro.setPricFamiliaFechaDesde(getView().dateFechaDesde.getDate());
			registro.setPricFamiliaFechaHasta(getView().dateFechaHasta.getDate());
			registro.setPricReferencia("." + getView().txtReferencia.getText());

			return registro;
		}
	}

	public void setRegistro(Object registro) {
		getModel().setArticuloCargado(null);
		getModel().setFamiliaCargado(null);
		getModel().setRegistro(registro);

		if (getModel().getArticuloID() > 0) {
			cargoArticulo(String.valueOf(getModel().getArticuloID()));
		}
		if (getModel().getFamiliaID() > 0) {
			cargoFamilia(String.valueOf(getModel().getFamiliaID()));
		}

	}

	public CargaItemEspecial(PantPrincipalController pantPrincipal, CargaItemEspecialView view, CargaItemEspecialModel model, PermisosInterface permisos) throws Exception {
		super(pantPrincipal, view, model, permisos);
		view.txtArticuloID.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (getModel().getRegistro() != null) {
					cargoArticulo(view.txtArticuloID.getText());
				} else {
					cargoFamilia(view.txtArticuloID.getText());
				}
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
		getView().cbMoneda.setSelectedIndex(0);

		if (getModel().isEdicion()) {

			if (getModel().getRegistro() != null) {

				getView().lblArticuloID.setText(String.valueOf(getModel().getRegistro().getPricArticulo()));

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

				if (getModel().getRegistro().getPricReferencia() != null) {
					getView().txtReferencia.setText(getModel().getRegistro().getPricReferencia());
				}
			} else {
				getView().lblArticuloID.setText(String.valueOf(getModel().getRegistroFamilia().getPricFamiliaId()));

				if ((getModel().getRegistroFamilia().getPricFamiliaDescuento1() != null) && (getModel().getRegistroFamilia().getPricFamiliaDescuento1().doubleValue() > 0.0)) {
					getView().txtDesc1.setImporte(getModel().getRegistroFamilia().getPricFamiliaDescuento1().doubleValue());
				}

				if ((getModel().getRegistroFamilia().getPricFamiliaDescuento2() != null) && (getModel().getRegistroFamilia().getPricFamiliaDescuento2().doubleValue() > 0.0)) {
					getView().txtDesc2.setImporte(getModel().getRegistroFamilia().getPricFamiliaDescuento2().doubleValue());
				}

				if (getModel().getRegistroFamilia().getPricFamiliaFechaDesde() != null) {
					getView().dateFechaDesde.setDate(getModel().getRegistroFamilia().getPricFamiliaFechaDesde());
				}

				if (getModel().getRegistroFamilia().getPricFamiliaFechaHasta() != null) {
					getView().dateFechaHasta.setDate(getModel().getRegistroFamilia().getPricFamiliaFechaHasta());
				}

				if (getModel().getRegistroFamilia().getPricReferencia() != null) {
					getView().txtReferencia.setText(getModel().getRegistroFamilia().getPricReferencia());
				}
			}
		}

		RefrescarDatosArticulo();

		if (getModel().isEdicion()) {
			getView().txtDesc1.requestFocus();
		} else {
			getView().txtArticuloID.requestFocus();
		}

		if (getModel().getRegistro() != null) {
			getView().txtPrecio.setVisible(true);
			getView().cbMoneda.setVisible(true);
			getView().lblDescripcion.setVisible(true);
			getView().lblLabelDescipcion.setVisible(true);
			getView().lblLabelPrecio.setVisible(true);
			getView().lblLabelMoneda.setVisible(true);
			getView().lblLabelArticulo.setText("Articulo ID:");
		} else {
			getView().txtPrecio.setVisible(false);
			getView().cbMoneda.setVisible(false);
			getView().lblDescripcion.setVisible(false);
			getView().lblLabelDescipcion.setVisible(false);
			getView().lblLabelPrecio.setVisible(false);
			getView().lblLabelMoneda.setVisible(false);
			getView().lblLabelArticulo.setText("Familia ID:");
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

		if (getView().txtPrecio.isVisible() && (!getView().txtPrecio.isEmpty()) && (getView().cbMoneda.getSelectedIndex() == -1)) {
			popUpError.showError(getView().cbMoneda, "Falta cargar la moneda");
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

		if (FechaManagerUtil.getDateDiff(dFechaDesde, dFechaHasta, TimeUnit.DAYS) < 1) {
			popUpError.showError(getView().dateFechaDesde, "La fecha desde debe ser menor a la hasta");
			return false;
		}

		return true;
	}

	public String getNombreItem() {
		return getModel().getNombreItem();
	}

	protected void cargoArticulo(String id) {
		if (!id.equals("") && CommonUtils.isNumeric(id)) {
			int idInt = Integer.valueOf(id);
			StocArts articulo = StocArtsDAO.findById(idInt);

			if (articulo != null) {
				getModel().setArticuloCargado(articulo);
			}
		}
		RefrescarDatosArticulo();
	}

	protected void cargoFamilia(String id) {
		if (!id.equals("") && CommonUtils.isNumeric(id)) {
			int idInt = Integer.valueOf(id);
			VentLipv familiaCargado = VentLipvDAO.findById(idInt);

			if (familiaCargado != null) {
				getModel().setFamiliaCargado(familiaCargado);
			}
		}

		RefrescarDatosArticulo();
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
		if (!retorno) {

			if ((ke.getKeyCode() == KeyEvent.VK_F3) && getView().txtArticuloID.hasFocus()) {
				retorno = true;
				try {
					if (getModel().getRegistro() != null) { // es articulo
						String id = buscarArticulo();
						if (!id.equals("")) {
							getView().txtArticuloID.setText(id);
							getView().txtDesc1.requestFocus();
						}
					} else { // es familia
						String id = buscarFamilia();
						if (!id.equals("")) {
							getView().txtArticuloID.setText(id);
							getView().txtDesc1.requestFocus();
						}
					}

				} catch (Exception e) {
					ManejoDeError.showError(e, "Error al cargar la busqueda de Articulo / Familia");
				}
			}
		}
		return retorno;
	}

	private String buscarFamilia() throws Exception {
		String retorno = "";
		BuscarFamiliaDialog buscarFamiliaDialog = new BuscarFamiliaDialog(getPantallaPrincipal());
		buscarFamiliaDialog.iniciar();
		if (buscarFamiliaDialog.getNroFamilia() != null) {
			retorno = String.valueOf(buscarFamiliaDialog.getNroFamilia());
			getView().txtArticuloID.setText(retorno);
		}

		return retorno;
	}

	private String buscarArticulo() throws Exception {
		String retorno = "";
		BuscarArticuloDialog buscarClienteDialog = new BuscarArticuloDialog(getPantallaPrincipal());
		buscarClienteDialog.iniciar();
		if (buscarClienteDialog.getNroArticulo() != null) {
			retorno = String.valueOf(buscarClienteDialog.getNroArticulo());
			getView().txtArticuloID.setText(retorno);
		}

		return retorno;
	}
}
