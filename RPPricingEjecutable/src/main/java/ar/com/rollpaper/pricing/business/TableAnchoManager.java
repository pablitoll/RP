package ar.com.rollpaper.pricing.business;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ar.com.rollpaper.pricing.ui.ManejoDeError;
import ar.com.rp.ui.componentes.RPTable;

public class TableAnchoManager implements MouseListener {

	private RPTable tabla;
	private String nombreTabla;

	public TableAnchoManager(RPTable tabla, String nombreTabla) {
		this.tabla = tabla;
		this.nombreTabla = nombreTabla;
	}

	public static void registrarEvento(RPTable tabla, String nombreTabla) throws Exception {

		TableAnchoManager mouseList = new TableAnchoManager(tabla, nombreTabla);

		tabla.getTableHeader().addMouseListener(mouseList);
		tabla.setAnchoFijo(ArchivoDePropiedadesBusiness.getAnchoTabla(nombreTabla));
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		try {
			int[] vectorAncho = new int[tabla.getModel().getColumnCount()];
			for (int i = 0; i < tabla.getModel().getColumnCount(); i++) {
				int tableRow = tabla.convertColumnIndexToView(i);

				if (tableRow > -1) {
					vectorAncho[i] = tabla.getColumnModel().getColumn(tableRow).getWidth();
				} else {
					vectorAncho[i] = 0;
				}
			}

			ArchivoDePropiedadesBusiness.setAnchoTabla(nombreTabla, vectorAncho);
		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al cambiar ancho");
		}
	}
}
