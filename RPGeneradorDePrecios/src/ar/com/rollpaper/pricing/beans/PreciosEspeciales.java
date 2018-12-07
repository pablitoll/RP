package ar.com.rollpaper.pricing.beans;
// Generated 22/05/2018 19:11:06 by Hibernate Tools 5.3.0.Beta2

import java.math.BigDecimal;
import java.util.Date;

/**
 * PricPreciosEspeciales generated by hbm2java
 */
public class PreciosEspeciales implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8610866086500592889L;
	private int pricPreciosEspecialesId;
	private int pricCliente;
	private int pricListaPrecvta;
	private int pricArticulo;
	private BigDecimal pricDescuento1;
	private BigDecimal pricDescuento2;
	private String pricMoneda;
	private BigDecimal pricPrecio;
	private Date pricFechaDesde;
	private Date pricFechaHasta;
	private BigDecimal pricComision;
	private String pricReferencia;

	public PreciosEspeciales() {
	}

	public PreciosEspeciales(int pricPreciosEspecialesId, int pricCliente, int pricListaPrecvta, int pricArticulo, BigDecimal pricDescuento1, BigDecimal pricDescuento2,
			String pricMoneda, BigDecimal pricPrecio, Date pricFechaDesde, Date pricFechaHasta, BigDecimal pricComision, String pricReferencia) {
		super();
		this.pricPreciosEspecialesId = pricPreciosEspecialesId;
		this.pricCliente = pricCliente;
		this.pricListaPrecvta = pricListaPrecvta;
		this.pricArticulo = pricArticulo;
		this.pricDescuento1 = pricDescuento1;
		this.pricDescuento2 = pricDescuento2;
		this.pricMoneda = pricMoneda;
		this.pricPrecio = pricPrecio;
		this.pricFechaDesde = pricFechaDesde;
		this.pricFechaHasta = pricFechaHasta;
		this.pricComision = pricComision;
		this.pricReferencia = pricReferencia;
	}

	public int getPricPreciosEspecialesId() {
		return pricPreciosEspecialesId;
	}

	public void setPricPreciosEspecialesId(int pricPreciosEspecialesId) {
		this.pricPreciosEspecialesId = pricPreciosEspecialesId;
	}

	public int getPricCliente() {
		return pricCliente;
	}

	public void setPricCliente(int pricCliente) {
		this.pricCliente = pricCliente;
	}

	public int getPricListaPrecvta() {
		return pricListaPrecvta;
	}

	public void setPricListaPrecvta(int pricListaPrecvta) {
		this.pricListaPrecvta = pricListaPrecvta;
	}

	public int getPricArticulo() {
		return pricArticulo;
	}

	public void setPricArticulo(int pricArticulo) {
		this.pricArticulo = pricArticulo;
	}

	public BigDecimal getPricDescuento1() {
		return pricDescuento1;
	}

	public void setPricDescuento1(BigDecimal pricDescuento1) {
		this.pricDescuento1 = pricDescuento1;
	}

	public BigDecimal getPricDescuento2() {
		return pricDescuento2;
	}

	public void setPricDescuento2(BigDecimal pricDescuento2) {
		this.pricDescuento2 = pricDescuento2;
	}

	public String getPricMoneda() {
		return pricMoneda;
	}

	public void setPricMoneda(String pricMoneda) {
		this.pricMoneda = pricMoneda;
	}

	public BigDecimal getPricPrecio() {
		return pricPrecio;
	}

	public void setPricPrecio(BigDecimal pricPrecio) {
		this.pricPrecio = pricPrecio;
	}

	public Date getPricFechaDesde() {
		return pricFechaDesde;
	}

	public void setPricFechaDesde(Date pricFechaDesde) {
		this.pricFechaDesde = pricFechaDesde;
	}

	public Date getPricFechaHasta() {
		return pricFechaHasta;
	}

	public void setPricFechaHasta(Date pricFechaHasta) {
		this.pricFechaHasta = pricFechaHasta;
	}

	public BigDecimal getPricComision() {
		return pricComision;
	}

	public void setPricComision(BigDecimal pricComision) {
		this.pricComision = pricComision;
	}

	public String getPricReferencia() {
		return pricReferencia;
	}

	public void setPricReferencia(String pricReferencia) {
		this.pricReferencia = pricReferencia;
	}

	public boolean isvigente(Date date) {		
		
		return date.after(this.pricFechaDesde) && date.before(this.pricFechaHasta);
	}

}
