package ar.com.rollpaper.pricing.model;

import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaItemEspecialArticuloModel extends BaseModel {

	private PreciosEspeciales registro;
	private String accion = "";
	private StocArts articuloCargado = null;

	public String getAccion() {
		return accion;
	}

	public PreciosEspeciales getRegistro() {
		return registro;
	}

	public void setRegistro(PreciosEspeciales registro) {
		this.registro = registro;
	}

	public int getArticuloID() {
		if (articuloCargado != null) {
			return articuloCargado.getArtsArticulo();
		}
		return -1;
	}

	public boolean isEdicion() {
		return (registro != null) && (getArticuloID() > 0);
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getNombreItem() {
		if (articuloCargado != null) {
			return articuloCargado.getArtsNombre();
		}
		return "S/D";
	}

	public String getDescripcionItem() {
		if (articuloCargado != null) {
			return articuloCargado.getArtsDescripcion();
		}
		return "S/D";
	}

	public String getUnidadItem() {
		if (articuloCargado != null) {
			return articuloCargado.getArtsUnimedStock();
		} else {
			return "S/D";
		}
	}

	public void setArticuloCargado(StocArts articuloCargado) {
		this.articuloCargado = articuloCargado;
	}

	public String getArticuloIDEmp() {
		if (articuloCargado != null) {
			return articuloCargado.getArtsArticuloEmp();
		} else {
			return "";
		}
	}

}
