package ar.com.rollpaper.pricing.jasper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int artsArticulo;
	private String codArticulo;
	private String nomArticulo;
	private String descArticulo;
	private String unidadArticulo;
	private String monedaArticulo;
	private String precioArticulo;
	private String familiaCod;
	private boolean prodListaBase;
	private Date vigenciaDesde;
	private Date vigenciaHasta;
	private BigDecimal comision;
	private String referencia;
	private BigDecimal descuento1;
	private BigDecimal descuento2;
	private Double pricValorTC;

	public ProductoDTO(int artsArticulo, String codArticulo, String nomArticulo, String descArticulo,
			String unidadArticulo, String monedaArticulo, String precioArticulo, String familiaCod,
			boolean prodListaBase) {
		super();
		this.artsArticulo = artsArticulo;
		this.codArticulo = codArticulo;
		this.nomArticulo = nomArticulo;
		this.descArticulo = descArticulo;
		this.unidadArticulo = unidadArticulo;
		this.monedaArticulo = monedaArticulo;
		this.precioArticulo = precioArticulo;
		this.familiaCod = familiaCod == null ? "" : familiaCod;
		this.prodListaBase = prodListaBase;
	}

	public int getArtsArticulo() {
		return artsArticulo;
	}

	public void cargarExtras(Date vigenciaDesde, Date vigenciaHasta, BigDecimal comision, String referencia,
			BigDecimal descuento1, BigDecimal descuento2, Double pricValorTC) {
		this.vigenciaDesde = vigenciaDesde;
		this.vigenciaHasta = vigenciaHasta;
		this.comision = comision;
		this.referencia = referencia;
		this.descuento1 = descuento1;
		this.descuento2 = descuento2;
		this.pricValorTC = pricValorTC;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getPricValorTC() {
		return pricValorTC;
	}

	public Date getVigenciaDesde() {
		return vigenciaDesde;
	}

	public Date getVigenciaHasta() {
		return vigenciaHasta;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public String getReferencia() {
		return referencia;
	}

	public BigDecimal getDescuento1() {
		return descuento1;
	}

	public BigDecimal getDescuento2() {
		return descuento2;
	}

	public String getCodArticulo() {
		return codArticulo;
	}

	public String getNomArticulo() {
		return nomArticulo;
	}

	public String getDescArticulo() {
		return descArticulo;
	}

	public String getUnidadArticulo() {
		return unidadArticulo;
	}

	public String getMonedaArticulo() {
		return monedaArticulo;
	}

	public String getPrecioArticulo() {
		return precioArticulo;
	}

	public String getFamiliaCod() {
		return familiaCod;
	}

	public boolean isProdListaBase() {
		return prodListaBase;
	}

	public void setProdListaBase(boolean prodListaBase) {
		this.prodListaBase = prodListaBase;
	}

}
