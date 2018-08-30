package ar.com.rollpaper.pricing.jasper;

import java.io.Serializable;
import java.util.List;

public class ListaPrecioReporteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nomCliente;
	private String nomLegal;
	private String nroListaProducto;
	private List<ProductoDTO> listaProductos;
	
	public Integer getId() {
		return id;
	}
	public String getNomCliente() {
		return nomCliente;
	}
	public String getNomLegal() {
		return nomLegal;
	}
	public String getNroListaProducto() {
		return nroListaProducto;
	}
	public List<ProductoDTO> getListaProductos() {
		return listaProductos;
	}
	
	public ListaPrecioReporteDTO(Integer id, String nomCliente, String nomLegal, String nroListaProducto, List<ProductoDTO> listaProductos) {
		super();
		this.id = id;
		this.nomCliente = nomCliente;
		this.nomLegal = nomLegal;
		this.nroListaProducto = nroListaProducto;
		this.listaProductos = listaProductos;
	}
	
	

}

