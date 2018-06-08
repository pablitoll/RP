package ar.com.rollpaper.pricing.model;

import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.StocCa01;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaItemEspecialFamiliaModel extends BaseModel {

	private DescuentoXFamilias registro;
	private String accion = "";
	private StocCa01 familiaCargado = null;

	public void setFamiliaCargado(StocCa01 familiaCargado) {
		this.familiaCargado = familiaCargado;
	}

	public String getAccion() {
		return accion;
	}

	public DescuentoXFamilias getRegistroFamilia() {
		return registro;
	}

	public void setRegistro(DescuentoXFamilias registro) {
		this.registro = registro;
	}

	public String getFamiliaID() {
		if (familiaCargado != null) {
			return familiaCargado.getCa01Clasif1();
		}
		return "";
	}

	public boolean isEdicion() {
		return (registro != null) && (!getFamiliaID().equals(""));
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getNombreItem() {
		if (familiaCargado != null) {
			return familiaCargado.getCa01Nombre();
		}
		return "S/D";
	}
}
