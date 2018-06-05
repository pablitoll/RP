package ar.com.rollpaper.pricing.model;

import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaItemEspecialModel extends BaseModel {

	private Object registro;
	private String accion = "";
	private StocArts articuloCargado = null;
	private VentLipv familiaCargado = null;

	public void setFamiliaCargado(VentLipv familiaCargado) {
		this.familiaCargado = familiaCargado;
	}

	public String getAccion() {
		return accion;
	}

	public PreciosEspeciales getRegistro() {
		if (registro instanceof PreciosEspeciales) {
			return (PreciosEspeciales) registro;

		}
		return null;
	}

	public DescuentoXFamilias getRegistroFamilia() {
		if (registro instanceof DescuentoXFamilias) {
			return (DescuentoXFamilias) registro;
		}
		return null;
	}

	public void setRegistro(Object registro) {
		this.registro = registro;
	}

	public int getFamiliaID() {
		if (registro != null) {
			if (registro instanceof DescuentoXFamilias) {
				return ((DescuentoXFamilias) registro).getPricFamiliaListaPrecvta();
			}
		}
		return -1;

	}

	public int getArticuloID() {
		if (registro != null) {
			if (registro instanceof PreciosEspeciales) {
				return ((PreciosEspeciales) registro).getPricArticulo();
			}
		}
		return -1;
	}

	public boolean isEdicion() {
		return (registro != null) && ((getArticuloID() > 0) || (getFamiliaID() > 0));
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getNombreItem() {
		if (articuloCargado != null) {
			return articuloCargado.getArtsNombre();
		}
		if (familiaCargado != null) {
			return familiaCargado.getLipvNombre();
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

}
