package ar.com.rollpaper.pricing.controller;

import ar.com.rollpaper.pricing.model.CargaPrecioModel;
import ar.com.rollpaper.pricing.model.Consulta1Model;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rollpaper.pricing.view.Consulta1View;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class CargaPrecioController extends BaseControllerMVC<PantPrincipalController, CargaPrecioView, CargaPrecioModel> {

	public CargaPrecioController(PantPrincipalController pantPrincipal, CargaPrecioView view, CargaPrecioModel model)
			throws Exception {
		super(pantPrincipal, view, model, null);
	}

	@Override
	protected String getNombrePantalla() {
		return "Carga de Precio por Cliente";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejecuarAccion(String accion) {
		// TODO Auto-generated method stub
		//llllll

	}
}
