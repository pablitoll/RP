package ar.com.rollpaper.pricing.gp.beans;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.beans.VentArpv;

public class generarListaDePreciosResponse {
	private LinkedHashMap<VentArpv, BigDecimal> listaDePrecios;
	private List<MaestroEsclavo> listaDeEsclavos;

	public LinkedHashMap<VentArpv, BigDecimal> getListaDePrecios() {
		return listaDePrecios;
	}

	public void setListaDePrecios(LinkedHashMap<VentArpv, BigDecimal> listaDePrecios) {
		this.listaDePrecios = listaDePrecios;
	}

	public List<MaestroEsclavo> getListaDeEsclavos() {
		return listaDeEsclavos;
	}

	public void setListaDeEsclavos(List<MaestroEsclavo> listaDeEsclavos) {
		this.listaDeEsclavos = listaDeEsclavos;
	}

}
