package ar.com.rollpaper.pricing.business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableColumnHider {
	// private JTable table;
	private TableColumnModel tcm;
	private Map<String, Serializable> hiddenColumns;

	public TableColumnHider(JTable table) {
		// this.table = table;
		tcm = table.getColumnModel();
		hiddenColumns = new HashMap<String, Serializable>();
	}

	public void hide(String columnName) {
		int index = getIndex(columnName);
		if (index > -1) {
			TableColumn column = tcm.getColumn(index);
			hiddenColumns.put(columnName, column);
			hiddenColumns.put(":" + columnName, new Integer(index));
			tcm.removeColumn(column);
		}
	}

	private int getIndex(String columnName) {
		try {
			return tcm.getColumnIndex(columnName);
		} catch (IllegalArgumentException e) {
			return -1;
		}
	}

	public void show(String columnName) {
		Object o = hiddenColumns.remove(columnName);
		if (o == null) {
			return;
		}
		tcm.addColumn((TableColumn) o);
		o = hiddenColumns.remove(":" + columnName);
		if (o == null) {
			return;
		}
		int column = ((Integer) o).intValue();
		int lastColumn = tcm.getColumnCount() - 1;
		if (column < lastColumn) {
			tcm.moveColumn(lastColumn, column);
		}
	}
}
