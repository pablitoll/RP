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
	private String pricReferencia;

	public PreciosEspeciales() {
	}

	public PreciosEspeciales(int pricPreciosEspecialesId, int pricCliente, int pricListaPrecvta, int pricArticulo,
			BigDecimal pricDescuento1, BigDecimal pricDescuento2, String pricMoneda, BigDecimal pricPrecio,
			Date pricFechaDesde, Date pricFechaHasta) {
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
	}

	public PreciosEspeciales(int pricPreciosEspecialesId, int pricCliente, int pricListaPrecvta, int pricArticulo,
			BigDecimal pricDescuento1, BigDecimal pricDescuento2, String pricMoneda, BigDecimal pricPrecio,
			Date pricFechaDesde, Date pricFechaHasta, String pricReferencia) {
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
		this.pricReferencia = pricReferencia;
	}

	public int getPricPreciosEspecialesId() {
		return this.pricPreciosEspecialesId;
	}

	public void setPricPreciosEspecialesId(int pricPreciosEspecialesId) {
		this.pricPreciosEspecialesId = pricPreciosEspecialesId;
	}

	public int getPricCliente() {
		return this.pricCliente;
	}

	public void setPricCliente(int pricCliente) {
		this.pricCliente = pricCliente;
	}

	public int getPricListaPrecvta() {
		return this.pricListaPrecvta;
	}

	public void setPricListaPrecvta(int pricListaPrecvta) {
		this.pricListaPrecvta = pricListaPrecvta;
	}

	public int getPricArticulo() {
		return this.pricArticulo;
	}

	public void setPricArticulo(int pricArticulo) {
		this.pricArticulo = pricArticulo;
	}

	public BigDecimal getPricDescuento1() {
		return this.pricDescuento1;
	}

	public void setPricDescuento1(BigDecimal pricDescuento1) {
		this.pricDescuento1 = pricDescuento1;
	}

	public BigDecimal getPricDescuento2() {
		return this.pricDescuento2;
	}

	public void setPricDescuento2(BigDecimal pricDescuento2) {
		this.pricDescuento2 = pricDescuento2;
	}

	public String getPricMoneda() {
		return this.pricMoneda;
	}

	public void setPricMoneda(String pricMoneda) {
		this.pricMoneda = pricMoneda;
	}

	public BigDecimal getPricPrecio() {
		return this.pricPrecio;
	}

	public void setPricPrecio(BigDecimal pricPrecio) {
		this.pricPrecio = pricPrecio;
	}

	public Date getPricFechaDesde() {
		return this.pricFechaDesde;
	}

	public void setPricFechaDesde(Date pricFechaDesde) {
		this.pricFechaDesde = pricFechaDesde;
	}

	public Date getPricFechaHasta() {
		return this.pricFechaHasta;
	}

	public void setPricFechaHasta(Date pricFechaHasta) {
		this.pricFechaHasta = pricFechaHasta;
	}

	public String getPricReferencia() {
		return this.pricReferencia;
	}

	public void setPricReferencia(String pricReferencia) {
		this.pricReferencia = pricReferencia;
	}

}
