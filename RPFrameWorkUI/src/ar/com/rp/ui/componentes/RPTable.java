package ar.com.rp.ui.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.alee.laf.table.WebTable;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.interfaces.RPTableEvent;

public class RPTable extends WebTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer colToIgnorar[] = null;
	private Hashtable<Integer, RowColorDTO> listColor = new Hashtable<Integer, RowColorDTO>();
	private TableColumnAdjuster tableAdjuster;
	private RPTableEvent smsvTableEvent = null;
	private int[] anchoFijo = null;

	public int[] getAnchoFijo() {
		return anchoFijo;
	}

	public void setAnchoFijo(int[] anchoFijo) {
		this.anchoFijo = anchoFijo;
	}

	public RPTableEvent getSmsvTableEvent() {
		return smsvTableEvent;
	}

	public void setRpTableEvent(RPTableEvent smsvTableEvent) {
		this.smsvTableEvent = smsvTableEvent;
	}

	public Integer[] getColToIgnorar() {
		return colToIgnorar;
	}

	public void setColToIgnorar(Integer[] colToIgnorar) {
		this.colToIgnorar = colToIgnorar;
		this.tableAdjuster.setColToIgnorar(colToIgnorar);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);

		// int selRow = this.getSelectedRow();
		int selCol = this.getSelectedColumn();

		if (!isRowSelected(row) || (getCellSelectionEnabled() && (column != selCol))) {
			if (row % 2 > 0) {
				c.setBackground(Color.WHITE);
			} else {
				c.setBackground(new Color(230, 230, 230));
			}
		}

		// Color
		int rowModel = convertRowIndexToModel(row);
		// int selRowModel = selRow;
		// if (selRow > -1) {
		// selRowModel = convertRowIndexToModel(selRow);
		// }
		// if (rowModel != selRowModel) {
		if (!isRowSelected(row)) {
			c.setForeground(Color.BLACK); // el setBackground lo estoy seteando con el gris
			// if ((rowModel != selRowModel) || (getCellSelectionEnabled() && (column !=
			// selCol))) {
			if (!getCellSelectionEnabled() || (column != selCol)) {
				RowColorDTO colorToAplly = listColor.get(rowModel);
				if (colorToAplly != null) {
					if ((colorToAplly.getColorFondo() != null) && (colorToAplly.getColorFondo()[column] != null)) {
						c.setBackground(colorToAplly.getColorFondo()[column]);
					}
					if ((colorToAplly.getColorLetra() != null) && (colorToAplly.getColorLetra()[column] != null)) {
						c.setForeground(colorToAplly.getColorLetra()[column]);
					}
				}
			}
		}

		return c;

	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		super.setValueAt(aValue, row, column);
	}

	public void addRow(Object[] row) {
		DefaultTableModel tableModel = (DefaultTableModel) getModel();
		tableModel.addRow(row);
	}

	public void addFirst(Object[] row) {
		DefaultTableModel tableModel = (DefaultTableModel) getModel();
		tableModel.insertRow(0, row);
	}

	public void addRowColor(Object[] row, Color[] colorFondo, Color[] colorLetra) {
		addRow(row);
		listColor.put(getRowCount() - 1, new RowColorDTO(colorFondo, colorLetra));
	}

	public void clear() {
		DefaultTableModel tableModel = (DefaultTableModel) getModel();
		// tableModel.getDataVector().removeAllElements();
		tableModel.setRowCount(0);
		setRowSorter(new TableRowSorter<DefaultTableModel>(tableModel));
		if (listColor != null) {
			listColor.clear();
		}
	}

	public void adjustColumns() {

		if (anchoFijo == null) {

			int anchoTotal = tableAdjuster.adjustColumns();

			int anchoMax = 0;
			if (getParent() instanceof JViewport) {
				anchoMax = (int) ((JViewport) getParent()).getSize().getWidth();
			}

			ajustarAncho(anchoTotal, anchoMax);
		} else {
			for (int nroColumn = 0; nroColumn < getColumnCount(); nroColumn++) {
				TableColumn tableColumn = getColumnModel().getColumn(nroColumn);
				if (anchoFijo[nroColumn] == 0) {
					adjustColumn(nroColumn);
				} else {
					tableColumn.setPreferredWidth(anchoFijo[nroColumn]);
					tableColumn.setWidth(anchoFijo[nroColumn]);
				}
			}

		}
	}

	private void ajustarAncho(int anchoTotal, int anchoMax) {
		if (anchoTotal < anchoMax) {
			int cantCol = getColumnCount() - (colToIgnorar != null ? colToIgnorar.length : 0);
			int dif = (anchoMax - anchoTotal - cantCol) / cantCol;
			for (int nroColumn = 0; nroColumn < getColumnCount(); nroColumn++) {
				if (!isColToIgnorar(nroColumn)) {
					TableColumn tableColumn = getColumnModel().getColumn(nroColumn);
					tableColumn.setPreferredWidth(tableColumn.getPreferredWidth() + dif);
					tableColumn.setWidth(tableColumn.getWidth() + dif);
				}
			}
		}
	}

	public void adjustColumns(int ajustarA) {
		int anchoTotal = tableAdjuster.adjustColumns();

		ajustarAncho(anchoTotal, ajustarA);
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

	public int adjustColumn(Integer nroColumn) {
		return tableAdjuster.adjustColumn(nroColumn);
	}

	public RPTable() {
		super();
		constructorGenerico();
	}

	private void constructorGenerico() {
		setShowGrid(false);
		setFont(Common.getStandarFont());
		getTableHeader().setFont(Common.getStandarFont());
		tableAdjuster = new TableColumnAdjuster(this);
		tableAdjuster.setDynamicAdjustment(true);
		tableAdjuster.setOnlyAdjustLarger(false);
		setRowSorter(new TableRowSorter<TableModel>(getModel()));
		setAutoCreateRowSorter(true);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (smsvTableEvent != null) {
					RPTable table = (RPTable) mouseEvent.getSource();
					if ((mouseEvent.getClickCount() == 2) && (table.getSelectedRow() != -1)) {
						smsvTableEvent.doubleClick(table.getSelectedRow(), table.getSelectedColumn());
					}
				}
			}
		});

	}

	public void setDynamicAdjustment(boolean isDynamicAdjustment) {
		tableAdjuster.setDynamicAdjustment(isDynamicAdjustment);
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

	public RPTable(@SuppressWarnings("rawtypes") final Vector rowData,
			@SuppressWarnings("rawtypes") final Vector columnNames) {
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

	@Override
	public void setModel(TableModel dataModel) {
		setRowSorter(new TableRowSorter<TableModel>(dataModel));
		super.setModel(dataModel);
		if (listColor != null) {
			listColor.clear();
		}
	}

}
