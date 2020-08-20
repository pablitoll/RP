package ar.com.rollpaper.robot.ui;

import java.io.Serializable;
import java.util.Date;

public class RegistroData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Date fecha;
	private String detalle;

	public RegistroData() {
		super();
	}

	public RegistroData(Date fecha, String detalle) {
		super();
		this.fecha = fecha;
		this.detalle = detalle;
	}

	public String getDetalle() {
		return detalle;
	}

	public Date getFecha() {
		return fecha;
	}

}
