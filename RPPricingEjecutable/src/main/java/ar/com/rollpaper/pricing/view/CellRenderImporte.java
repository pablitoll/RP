package ar.com.rollpaper.pricing.view;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import ar.com.rollpaper.pricing.business.CommonPricing;

public class CellRenderImporte extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int decimales = 4;

	@Override
	public void setValue(Object value) {
		if (value != null) {
			super.setValue(CommonPricing.formatearImporte((double) value, decimales));
		} else {
			super.setValue("");
		}

	}

	public CellRenderImporte() {
		super();
		setHorizontalAlignment(JLabel.RIGHT);
	}

	public CellRenderImporte(int decimales) {
		super();
		setHorizontalAlignment(JLabel.RIGHT);
		this.decimales = decimales;
	}

}
