package ar.com.rollpaper.pricing.model;

import java.util.Date;

import ar.com.rp.ui.pantalla.BaseModel;

public class BusquedaVencidoModel extends BaseModel {

	private String clienteID = "";
	private Date fechaVigenciaAl;
	private Date fechaVigenciaDesde;
	private String busqueda = "";
	private Double tcDesde = null;
	private Double tcHasta = null;

	public Double getTcDesde() {
		return tcDesde;
	}

	public void setTcDesde(Double tcDesde) {
		this.tcDesde = tcDesde;
	}

	public Double getTcHasta() {
		return tcHasta;
	}

	public void setTcHasta(Double tcHasta) {
		this.tcHasta = tcHasta;
	}

	public String getClienteID() {
		return clienteID;
	}

	public void setClienteID(String clienteID) {
		this.clienteID = clienteID;
	}

	public Date getFechaVigenciaAl() {
		return fechaVigenciaAl;
	}

	public void setFechaVigenciaAl(Date fechaVigenciaAl) {
		this.fechaVigenciaAl = fechaVigenciaAl;
	}

	public Date getFechaVigenciaDesde() {
		return fechaVigenciaDesde;
	}

	public void setFechaVigenciaDesde(Date fechaVigenciaDesde) {
		this.fechaVigenciaDesde = fechaVigenciaDesde;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

}
