package ar.com.rollpaper.pricing.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.StocCa01;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rp.ui.pantalla.BaseModel;

public class CargaItemEspecialFamiliaModel extends BaseModel {

	private String accion = "";
	private List<StocCa01> ListaFamiliaCargada = new ArrayList<StocCa01>();
	private DefaultTableModel tableModel;
	private VentLipv listaID;
	private CcobClie cliente;
	private DescuentoXFamilias descuentoxFamiliaModif = null;

	public void reset() {
		ListaFamiliaCargada = new ArrayList<StocCa01>();
	}

	public CcobClie getCliente() {
		return cliente;
	}

	public void setListaID(VentLipv listaID) {
		this.listaID = listaID;
	}

	public void addFamiliaCargado(StocCa01 familiaCargado) {
		this.ListaFamiliaCargada.add(familiaCargado);
	}

	public List<StocCa01> getListaFamiliaCargada() {
		return ListaFamiliaCargada;
	}

	public String getAccion() {
		return accion;
	}

	public boolean isEdicion() {
		return (descuentoxFamiliaModif != null); // && (!getFamiliaID().equals(""));
	}

	public DescuentoXFamilias getDescuentoxFamiliaModif() {
		return descuentoxFamiliaModif;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public int getListaID() {
		return listaID.getLipvListaPrecvta();
	}

	public void setCliente(CcobClie cliente) {
		this.cliente = cliente;
	}

	public void setRegistroToModif(DescuentoXFamilias descuentoxFamiliaModif) {
		this.descuentoxFamiliaModif = descuentoxFamiliaModif;
	}

	public void deleteFamiliaCargado(String codFamilia) {
		for (int i = 0; i < ListaFamiliaCargada.size(); i++) {
			StocCa01 familia = ListaFamiliaCargada.get(i);
			if (familia.getCa01Clasif1().equals(codFamilia)) {
				ListaFamiliaCargada.remove(i);
				i = ListaFamiliaCargada.size() + 1;
			}
		}

	}
}
