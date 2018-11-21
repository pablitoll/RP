package ar.com.rollpaper.pricing.jasper;

import java.io.Serializable;

public class ProductoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codArticulo;
	private String nomArticulo;
	private String descArticulo;
	private String unidadArticulo;
	private String monedaArticulo;
	private String precioArticulo;
	
	public ProductoDTO(String codArticulo, String nomArticulo, String descArticulo, String unidadArticulo, String monedaArticulo, String precioArticulo) {
		super();
		this.codArticulo = codArticulo;
		this.nomArticulo = nomArticulo;
		this.descArticulo = descArticulo;
		this.unidadArticulo = unidadArticulo;
		this.monedaArticulo = monedaArticulo;
		this.precioArticulo = precioArticulo;
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
	
	
}