package ar.com.rollpaper.pricing.ui;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;

public class BuscarClienteEsclavoDialog extends BuscarClienteDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RPTable tableEsclavo;

	public BuscarClienteEsclavoDialog(BasePantallaPrincipal<?, ?> pantPrincipal, RPTable tableEsclavo) {
		super(pantPrincipal);
		this.tableEsclavo = tableEsclavo;
	}

	
	@Override
	protected boolean puedeSeleccionar() {
		CcobClie  nroCliente = (CcobClie) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), COL_REG_INTERNO);
		if(MaestroEsclavoDAO.getListaEsclavosByCliente(nroCliente).size() > 0) {
			Dialog.showMessageDialog("El cliente seleccionado, no puede ser esclavo porque ya es maestro");
			return false;
		}
		
		if(estaClienteEnLista(nroCliente)) {
			Dialog.showMessageDialog("El cliente ya esta cargado");
			return false;
		}
		
		return true; 
	}


	private boolean estaClienteEnLista(CcobClie nroCliente) {
		for(int i = 0; i< tableEsclavo.getModel().getRowCount(); i++) {
			MaestroEsclavo me = (MaestroEsclavo) tableEsclavo.getModel().getValueAt(i, CargaClienteEsclavoView.COL_REGISTRO);	
			if(me.getPricEsclavoCliente() == nroCliente.getClieCliente()) {
				return true;
			}
		}
		return false;
	}

}