package ar.com.rollpaper.pricing.view;

import java.awt.Component;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import ar.com.rp.rpcutils.FechaManagerUtil;

public class CellRenderFecha implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4,
			int arg5) {

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setText("");

		if (arg1 != null) {
			Date aux = (Date) arg1;
			rightRenderer.setText(FechaManagerUtil.Date2String(aux));
		}

		if(arg2) {
			rightRenderer.setBackground(arg0.getSelectionBackground());
			rightRenderer.setForeground(arg0.getSelectionForeground());
		}
		return rightRenderer;
	}

}
