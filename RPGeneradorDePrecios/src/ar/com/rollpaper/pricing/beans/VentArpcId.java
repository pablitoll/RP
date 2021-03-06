package ar.com.rollpaper.pricing.beans;
// Generated 21/05/2018 20:09:21 by Hibernate Tools 5.3.0.Beta2

/**
 * VentArpcId generated by hbm2java
 */
public class VentArpcId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2722005698759838051L;
	private int arpcArticulo;
	private int arpcListaPrecvta;
	private int arpcCliente;

	public VentArpcId() {
	}

	public VentArpcId(int arpcArticulo, int arpcListaPrecvta, int arpcCliente) {
		this.arpcArticulo = arpcArticulo;
		this.arpcListaPrecvta = arpcListaPrecvta;
		this.arpcCliente = arpcCliente;
	}

	public int getArpcArticulo() {
		return this.arpcArticulo;
	}

	public void setArpcArticulo(int arpcArticulo) {
		this.arpcArticulo = arpcArticulo;
	}

	public int getArpcListaPrecvta() {
		return this.arpcListaPrecvta;
	}

	public void setArpcListaPrecvta(int arpcListaPrecvta) {
		this.arpcListaPrecvta = arpcListaPrecvta;
	}

	public int getArpcCliente() {
		return this.arpcCliente;
	}

	public void setArpcCliente(int arpcCliente) {
		this.arpcCliente = arpcCliente;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VentArpcId))
			return false;
		VentArpcId castOther = (VentArpcId) other;

		return (this.getArpcArticulo() == castOther.getArpcArticulo())
				&& (this.getArpcListaPrecvta() == castOther.getArpcListaPrecvta())
				&& (this.getArpcCliente() == castOther.getArpcCliente());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getArpcArticulo();
		result = 37 * result + this.getArpcListaPrecvta();
		result = 37 * result + this.getArpcCliente();
		return result;
	}

}
