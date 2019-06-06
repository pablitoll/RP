package ar.com.rollpaper.pricing.model;

import java.util.Date;

import ar.com.rp.ui.pantalla.BaseModel;

public class BusquedaVencidoModel extends BaseModel {

	private String clienteID = "";
	private Date fechaVigenciaAl;
	private Date fechaVigenciaDesde;
	private String busqueda = "";

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
