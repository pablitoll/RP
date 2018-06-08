package ar.com.rollpaper.pricing.model;

import com.alee.laf.table.WebTable;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaPrecioModel extends BaseModel {

	private CcobClie clienteCargado;

	public CcobClie getClienteCargado() {
		return clienteCargado;
	}

	public void setClienteCargado(CcobClie clienteCargado) {
		this.clienteCargado = clienteCargado;
	}

	public Object getRegistro(WebTable tablaPedidoEspecial, int row, int col) {
		return tablaPedidoEspecial.getModel().getValueAt(row, col);
	}

	public PreciosEspeciales getRegistroArticuloEmpty() {
		PreciosEspeciales preciosEspeciales = new PreciosEspeciales();
		preciosEspeciales.setPricCliente(clienteCargado.getClieCliente());
		return preciosEspeciales;
	}

	public DescuentoXFamilias getRegistroFamilaiEmpty() {
		DescuentoXFamilias descuentoXFamilias = new DescuentoXFamilias();
		descuentoXFamilias.setPricFamiliaCliente(clienteCargado.getClieCliente());
		return descuentoXFamilias;

	}
}
