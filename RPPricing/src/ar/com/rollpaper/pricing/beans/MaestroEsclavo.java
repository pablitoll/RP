package ar.com.rollpaper.pricing.beans;
// Generated 24/05/2018 09:03:27 by Hibernate Tools 5.3.0.Beta2

/**
 * PricMaestroEsclavo generated by hbm2java
 */
public class MaestroEsclavo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5361713044339073812L;
	private int pricMaestroEsclavoId;
	private int pricMaestroCliente;
	private int pricMEListaPrecvta;
	private int pricEsclavoCliente;

	public MaestroEsclavo() {
	}

	public MaestroEsclavo( int pricMaestroCliente, int pricMEListaPrecvta,
			int pricEsclavoCliente) {
		//this.pricMaestroEsclavoId = pricMaestroEsclavoId;
		this.pricMaestroCliente = pricMaestroCliente;
		this.pricMEListaPrecvta = pricMEListaPrecvta;
		this.pricEsclavoCliente = pricEsclavoCliente;
	}

	public int getPricMaestroEsclavoId() {
		return this.pricMaestroEsclavoId;
	}

	public void setPricMaestroEsclavoId(int pricMaestroEsclavoId) {
		this.pricMaestroEsclavoId = pricMaestroEsclavoId;
	}

	public int getPricMaestroCliente() {
		return this.pricMaestroCliente;
	}

	public void setPricMaestroCliente(int pricMaestroCliente) {
		this.pricMaestroCliente = pricMaestroCliente;
	}

	public int getPricMEListaPrecvta() {
		return this.pricMEListaPrecvta;
	}

	public void setPricMEListaPrecvta(int pricMEListaPrecvta) {
		this.pricMEListaPrecvta = pricMEListaPrecvta;
	}

	public int getPricEsclavoCliente() {
		return this.pricEsclavoCliente;
	}

	public void setPricEsclavoCliente(int pricEsclavoCliente) {
		this.pricEsclavoCliente = pricEsclavoCliente;
	}

}
