package ar.com.rollpaper.pricing.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;

import org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase;

import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.model.CargaItemEspecialModel;
import ar.com.rollpaper.pricing.view.CargaItemEspecialView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.ui.interfaces.PermisosInterface;
import ar.com.rp.ui.pantalla.BaseControllerDialog;

public class CargaItemEspecial
		extends BaseControllerDialog<PantPrincipalController, CargaItemEspecialView, CargaItemEspecialModel> {

	public PreciosEspeciales getRegistro() throws Exception {
		// TODO ACA
		PreciosEspeciales registro = getModel().getRegistro();
		if (getModel().isEdicion()) {
			registro.setPricArticulo(Integer.valueOf(getView().txtArticuloID.getText()));
		} else {
			registro.setPricArticulo(Integer.valueOf(getView().lblArticuloID.getText()));
		}
		registro.setPricDescuento1(new BigDecimal(getView().txtDesc1.getImporte()));
		registro.setPricDescuento2(new BigDecimal(getView().txtDesc2.getImporte()));
		registro.setPricFechaDesde(getView().dateFechaDesde.getDate());
		registro.setPricFechaHasta(getView().dateFechaHasta.getDate());
		registro.setPricListaPrecvta(1);
		registro.setPricMoneda("P");// getView().cbMoneda.getSelectedItem().toString());
		registro.setPricPrecio(new BigDecimal(getView().txtPrecio.getImporte()));
		registro.setPricReferencia(getView().txtReferencia.getText());

		return registro;
	}

	public void setRegistro(PreciosEspeciales registro) {
		getModel().setRegistro(registro);
	}

	public CargaItemEspecial(PantPrincipalController pantPrincipal, CargaItemEspecialView view,
			CargaItemEspecialModel model, PermisosInterface permisos) throws Exception {
		super(pantPrincipal, view, model, permisos);
		view.txtArticuloID.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				cargoArticulo(view.txtArticuloID.getText());
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}

	@Override
	protected void cargaPantalla() throws Exception {
		getView().lblArticuloID.setVisible(!getModel().isEdicion());
		getView().txtArticuloID.setVisible(getModel().isEdicion());
		
		RefrescarDatosArticulo();
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
	public void ejecuarAccion(String accion) {
		if (accion.equals(ConstantesRP.AccionesCargaItemFamilia.CANCELAR.toString())) {
			getModel().setAccion("");
			cerrarVentana();
		}

		if (accion.equals(ConstantesRP.AccionesCargaItemFamilia.ACEPTAR.toString())) {
			getModel().setAccion("ACEPTO");
			cerrarVentana();
		}

	}

	public String getNombreItem() {
		return getModel().getNombreItem();
	}

	protected void cargoArticulo(String id) {
		int idInt = Integer.valueOf(id);
		StocArts articulo = StocArtsDAO.findById(idInt);

		if (articulo != null) {
			getModel().setArticuloCargado(articulo);
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
}
