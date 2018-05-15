package ar.com.rp.rpcutils;

import java.awt.Component;
import java.awt.FontMetrics;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class CommonTablaManager {

	//busca la fila de la columna pkColumn que coicida coni el campo data
	public static void findPosition(JTable tabla, Integer pkColumn, String data){
		for(int i = 0; i < tabla.getRowCount(); i++){
			if(getValueAt(tabla, i, pkColumn).equalsIgnoreCase(data)){
				tabla.changeSelection(i, 0, false, false);
				return;
			}
		}
	}
	
	public static void setAutoSizeAllColumn(JTable tabla, JScrollPane sp){
		setAutoSizeAllColumn(tabla, sp, -1);
	}
	
	public static void setAutoSizeAllColumn(JTable tabla, JScrollPane sp, int colAIgnorar){
		
		int nroColumn = 0;
		int total = 0;
		for(nroColumn = 0; nroColumn < tabla.getColumnCount(); nroColumn++){
			total += setAutoSizeAColumn(tabla, nroColumn);
		}

		//Si la tabla es menor que el SP, entonces disrtubuyo la diferencia engtre las columnas
		int dif = sp.getWidth() - total;
		if(dif > 0){
			int cantCol = tabla.getColumnCount();
			if(colAIgnorar > -1){
				cantCol--;
			}
			int distribuir = (int) ((dif - 5) / cantCol);			
			for(nroColumn = 0; nroColumn < tabla.getColumnCount(); nroColumn++){
				if(nroColumn != colAIgnorar){
					TableColumn tableColumn = tabla.getColumnModel().getColumn(nroColumn);
					int newSize = tableColumn.getWidth() + distribuir;
					tableColumn.setWidth(newSize);
					tableColumn.setPreferredWidth(newSize);
				}
			}
		}
	}

	public static int setAutoSizeAColumn(JTable tabla, Integer nroColumn){
		//verifico que la columan que me pasan exista
		int preferredWidth = 0;
		if(tabla.getColumnModel().getColumnCount() > nroColumn){

			tabla.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
	
		    JTableHeader tableHeader = tabla.getTableHeader();
		    FontMetrics headerFontMetrics = tableHeader.getFontMetrics(tableHeader.getFont());		    
	
		    TableColumn tableColumn = tabla.getColumnModel().getColumn(nroColumn);
		    preferredWidth = headerFontMetrics.stringWidth(tabla.getColumnName(nroColumn)) + tabla.getIntercellSpacing().width + 10; //tama�o del header
		    
		    int maxWidth = tableColumn.getMaxWidth();

		    for (int row = 0; row < tabla.getRowCount(); row++)
		    {
		        TableCellRenderer cellRenderer = tabla.getCellRenderer(row, nroColumn);
		        Component c = tabla.prepareRenderer(cellRenderer, row, nroColumn);
		        int width = c.getPreferredSize().width + tabla.getIntercellSpacing().width;
		        preferredWidth = Math.max(preferredWidth, width);

		        //  We've exceeded the maximum width, no need to check other rows
		        if (preferredWidth >= maxWidth)
		        {
		            preferredWidth = maxWidth;
		            break;
		        }
		    }

		    tableColumn.setPreferredWidth( preferredWidth );		    
		    tableColumn.setWidth( preferredWidth);
		}
		
		return preferredWidth;
	}

	public static String getValueAtSelectedRow(JTable table, Integer colNumber){
		if(table.getSelectedRow() >= 0){
			return table.getValueAt(table.getSelectedRow(), colNumber).toString().trim();
		} else {
			return "";
		}
	}

	public static String getValueAt(JTable table, Integer filaNumber, Integer colNumber){
		if(table.getValueAt(filaNumber, colNumber) == null) {
			return "";
		} else {
			return table.getValueAt(filaNumber, colNumber).toString().trim();
		}
	}
}
