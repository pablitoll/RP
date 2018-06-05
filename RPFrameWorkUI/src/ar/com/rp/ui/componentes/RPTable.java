package ar.com.rp.ui.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.alee.laf.table.WebTable;

import ar.com.rp.ui.common.Common;

public class RPTable extends WebTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);

		if (row >= 0) {
			if (getSelectedRow() != row) {
				if (row % 2 > 0) {
					c.setBackground(Color.WHITE);
				} else {
					c.setBackground(new Color(230, 230, 230));
				}
			}
		}

		return c;
	}

	public void addRow(Object[] row) {
		DefaultTableModel tableModel = (DefaultTableModel) getModel();
		tableModel.addRow(row);
		autoSize();
	}

	public void clear() {
		DefaultTableModel tableModel = (DefaultTableModel) getModel();
		tableModel.getDataVector().removeAllElements();
	}

	public void autoSizeAllColumn() {
		autoSizeAllColumn(-1);
	}

	public void autoSizeAllColumn(int colAIgnorar) {
		for (int nroColumn = 0; nroColumn < getColumnCount(); nroColumn++) {
			if (colAIgnorar != nroColumn) {
				autoSizeAColumn(nroColumn);
			}
		}
	}

	public int autoSizeAColumn(Integer nroColumn) {
		// verifico que la columan que me pasan exista
		int preferredWidth = 0;
		if (getColumnModel().getColumnCount() > nroColumn) {

			setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			JTableHeader tableHeader = getTableHeader();
			FontMetrics headerFontMetrics = tableHeader.getFontMetrics(tableHeader.getFont());

			TableColumn tableColumn = getColumnModel().getColumn(nroColumn);
			preferredWidth = headerFontMetrics.stringWidth(getColumnName(nroColumn)) + getIntercellSpacing().width + 20; // tama�o del header

			int maxWidth = tableColumn.getMaxWidth();

			for (int row = 0; row < getRowCount(); row++) {
				TableCellRenderer cellRenderer = getCellRenderer(row, nroColumn);
				Component c = prepareRenderer(cellRenderer, row, nroColumn);
				int width = c.getPreferredSize().width + getIntercellSpacing().width;
				preferredWidth = Math.max(preferredWidth, width);

				// We've exceeded the maximum width, no need to check other rows
				if (preferredWidth >= maxWidth) {
					preferredWidth = maxWidth;
					break;
				}
			}

			tableColumn.setPreferredWidth(preferredWidth);
			tableColumn.setWidth(preferredWidth);
		}

		return preferredWidth;
	}

	private void autoSize() {
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JViewport parent = (JViewport) getParent();
		JScrollPane enclosing = (JScrollPane) parent.getParent();

		JTableHeader tableHeader = getTableHeader();
		FontMetrics headerFontMetrics = tableHeader.getFontMetrics(tableHeader.getFont());
		int anchoTotal = 0;
		int cantCol = 0;

		for (int nroColumn = 0; nroColumn < getColumnCount(); nroColumn++) {

			TableColumn tableColumn = getColumnModel().getColumn(nroColumn);

			int widthHeader = headerFontMetrics.stringWidth(getColumnName(nroColumn)) + getIntercellSpacing().width + 20; // tama�o del header
			int widthCol = tableColumn.getWidth();
			if (getRowCount() == 1) { // Si es el primer registro ignoro la columna porque puede ser que la tenga que
										// achicar
				widthCol = 0;
			}

			TableCellRenderer cellRenderer = getCellRenderer(getRowCount() - 1, nroColumn);
			Component c = prepareRenderer(cellRenderer, getRowCount() - 1, nroColumn);
			int widthRender = c.getPreferredSize().width + getIntercellSpacing().width;

			int preferredWidth = Math.max(widthHeader, widthRender);
			preferredWidth = Math.max(preferredWidth, widthCol);

			tableColumn.setPreferredWidth(preferredWidth);
			tableColumn.setWidth(preferredWidth);
			anchoTotal += preferredWidth;
			cantCol++;
		}

		if (anchoTotal < enclosing.getWidth()) {
			int dif = (enclosing.getWidth() - anchoTotal) / cantCol;
			for (int nroColumn = 0; nroColumn < getColumnCount(); nroColumn++) {
				TableColumn tableColumn = getColumnModel().getColumn(nroColumn);
				if (tableColumn.getMaxWidth() > 0) {
					tableColumn.setPreferredWidth(tableColumn.getPreferredWidth() + dif);
					tableColumn.setWidth(tableColumn.getWidth() + dif);
				}
			}
		}
	}

	public RPTable() {
		super();
		constructorGenerico();
	}

	private void constructorGenerico() {
		setShowGrid(false);
		setFont(Common.getStandarFont());
		getTableHeader().setFont(Common.getStandarFont());
	}

	public RPTable(final TableModel dm) {
		super(dm);
		constructorGenerico();
	}

	public RPTable(final TableModel dm, final TableColumnModel cm) {
		super(dm, cm);
		constructorGenerico();
	}

	public RPTable(final TableModel dm, final TableColumnModel cm, final ListSelectionModel sm) {
		super(dm, cm, sm);
		constructorGenerico();
	}

	public RPTable(final int numRows, final int numColumns) {
		super(numRows, numColumns);
		constructorGenerico();
	}

	public RPTable(@SuppressWarnings("rawtypes") final Vector rowData, @SuppressWarnings("rawtypes") final Vector columnNames) {
		super(rowData, columnNames);
		constructorGenerico();
	}

	public RPTable(final Object[][] rowData, final Object[] columnNames) {
		super(rowData, columnNames);
		constructorGenerico();
	}

	public DefaultTableCellRenderer getRigthRender() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		return rightRenderer;
	}

	public DefaultTableCellRenderer getCenterRender() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		return centerRenderer;
	}

	public DefaultTableCellRenderer getLeftRender() {
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		return leftRenderer;
	}

}
