package ar.com.rp.rpcutils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

public class CSVExport {

	private static final char DEFAULT_SEPARATOR = ';';

	public static boolean exportToExcel(JTable tabla, String nombreArchivo) throws Exception {
		// Lo genero
		FileWriter writer = new FileWriter(generarArchivoCSV(nombreArchivo));

		// Exporto la tabla
		tablaToCSC(writer, tabla);

		writer.flush();
		writer.close();

		return abrirArchivo(generarArchivoCSV(nombreArchivo));
	}

	public static boolean abrirArchivo(String nombreCSV) {
		// Lo abro
		try {
			Desktop.getDesktop().open(new File(nombreCSV));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String execString = "notepad.exe " + nombreCSV;
				Runtime run = Runtime.getRuntime();
				run.exec(execString);
			} catch (Exception e2) {
				e2.printStackTrace();
				return false;
			}
		}

		return true;
	}

	public static void tablaToCSC(FileWriter writer, JTable tabla) throws Exception {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();

		List<String> linea = new ArrayList<String>();
		for (int i = -1; i < tabla.getRowCount(); i++) {
			for (int c = 0; c < tabla.getColumnCount() - 1; c++) {
				if (i == -1) {
					linea.add(tabla.getColumnName(c));
				} else {
					Object valor = tabla.getValueAt(i, c);
					String strValor = valor == null ? "" : valor.toString();
					linea.add(strValor);
				}
			}
			writeLine(writer, linea, dfs.getPatternSeparator(), ' ');
			linea.clear();
		}

	}

	public static String generarArchivoCSV(String nombreArchivo) throws Exception {
		return nombreArchivo + ".csv";
	}

	public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws Exception {

		boolean first = true;

		// default customQuote is empty

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\r\n");
		w.append(sb.toString());
	}

	private static String followCVSformat(String value) {
		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

}
