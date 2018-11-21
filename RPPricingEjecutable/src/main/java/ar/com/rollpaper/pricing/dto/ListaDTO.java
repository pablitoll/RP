package ar.com.rollpaper.pricing.dto;

import ar.com.rollpaper.pricing.beans.VentLipv;

public class ListaDTO {
	
	private VentLipv ventLipv;
	private boolean isListaPrincipal = false;
	private boolean isListaHeredada = false;;

	@Override
	public String toString() { // Para el combo
		String listaPrincipal = isListaPrincipal ? " - PRINCIPAL" : "";
		String listaHeredada = isListaHeredada ? " - HEREDADA" : "";
		return "(" + getVentLipv().getLipvListaPrecvta() + ") " + getVentLipv().getLipvNombre() + listaPrincipal + listaHeredada;
	}

	public ListaDTO(VentLipv ventLipv, boolean isListaPrincipal, boolean isListaHeredada) {
		super();
		this.ventLipv = ventLipv;
		this.isListaPrincipal = isListaPrincipal;
		this.isListaHeredada = isListaHeredada;
	}

	public VentLipv getVentLipv() {
		return ventLipv;
	}

	public void setIsListaPrincipal(boolean isListaPrincipal) {
		this.isListaPrincipal = isListaPrincipal;
	}

	public boolean isListaPrincipal() {
		return isListaPrincipal;
	}

	public void setIsListaHeredada(boolean isListaHeredada) {
		this.isListaHeredada = isListaHeredada;
	}

	public boolean isListaHeredada() {
		return isListaHeredada;
	}
}
