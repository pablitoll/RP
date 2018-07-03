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
	private Integer colToIgnorar[] = null;

	public Integer[] getColToIgnorar() {
		return colToIgnorar;
	}

	public void setColToIgnorar(Integer[] colToIgnorar) {
		this.colToIgnorar = colToIgnorar;
	}

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

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		super.setValueAt(aValue, row, column);
		autoSizeAllColumn();
	}

	public void addRow(Object[] row) {
		DefaultTableModel tableModel = (DefaultTableModel) getModel();
		tableModel.addRow(row);
		autoSizeAllColumn();
	}

	public void clear() {
		DefaultTableModel tableModel = (DefaultTableModel) getModel();
		tableModel.getDataVector().removeAllElements();
		repaint();
	}

	public void autoSizeAllColumn() {
		JViewport parent = (JViewport) getParent();
		if (parent != null) {
			JScrollPane enclosing = (JScrollPane) parent.getParent();
			int anchoTotal = 0;

			for (int nroColumn = 0; nroColumn < getColumnCount(); nroColumn++) {
				if (!isColToIgnorar(nroColumn)) {
					anchoTotal += autoSizeAColumn(nroColumn);
				} else {
					anchoTotal += getColumnModel().getColumn(nroColumn).getWidth();
				}
			}

			if (anchoTotal < enclosing.getWidth()) {
				int cantCol = getColumnCount() - (colToIgnorar != null ? colToIgnorar.length : 0);
				int dif = (enclosing.getWidth() - anchoTotal - cantCol) / cantCol;
				for (int nroColumn = 0; nroColumn < getColumnCount(); nroColumn++) {
					if (!isColToIgnorar(nroColumn)) {
						TableColumn tableColumn = getColumnModel().getColumn(nroColumn);
						tableColumn.setPreferredWidth(tableColumn.getPreferredWidth() + dif);
						tableColumn.setWidth(tableColumn.getWidth() + dif);
					}
				}
			}
		}
	}

	private boolean isColToIgnorar(int nroColumn) {
		if (colToIgnorar != null) {
			for (int col : colToIgnorar) {
				if (col == nroColumn) {
					return true;
				}
			}
		}
		return false;
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
