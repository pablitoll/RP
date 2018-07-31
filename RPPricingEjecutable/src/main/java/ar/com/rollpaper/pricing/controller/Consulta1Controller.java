package ar.com.rollpaper.pricing.controller;

import ar.com.rollpaper.pricing.model.Consulta1Model;
import ar.com.rollpaper.pricing.view.Consulta1View;
import ar.com.rp.ui.pantalla.BaseControllerMVC;

public class Consulta1Controller extends BaseControllerMVC<PantPrincipalController, Consulta1View, Consulta1Model> {

	public Consulta1Controller(PantPrincipalController pantPrincipal, Consulta1View view, Consulta1Model model)
			throws Exception {
		super(pantPrincipal, view, model, null);
	}

	@Override
	protected String getNombrePantalla() {
		return "Consulta 1";
	}

	@Override
	protected void resetearPantalla() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void ejecutarAccion(String accion) {
		// TODO Auto-generated method stub
		//llllll
		int a;

	}
}
