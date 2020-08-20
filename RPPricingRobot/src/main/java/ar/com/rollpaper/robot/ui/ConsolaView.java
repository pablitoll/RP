package ar.com.rollpaper.robot.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.RPTable;

public class ConsolaView extends JFrame {

	public RPTable tableResultado;
	public JLabel lblStatus;
	private static final long serialVersionUID = 1L;
	public JTextField txtHs;

	public static final int COL_FECHA = 0;
	public static final int COL_ACCION = 1;
	public JCheckBox chkActivado;
	public JLabel lblDB;

	public ConsolaView() {
		setSize(new Dimension(600, 500));
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Consola de Pricing");
		setType(Type.UTILITY);
		getContentPane().setLayout(new BorderLayout(0, 0));

		String[] headerTabla = { "Fecha", "Detalle" };
		String[][] dataTabla = { {} };

		tableResultado = new RPTable();
		tableResultado.setModel(new DefaultTableModel(dataTabla, headerTabla));
		tableResultado.setRowHeight(30);
		tableResultado.setEditable(false);
		tableResultado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableResultado.setFont(Common.getStandarFont());

		tableResultado.getColumnModel().getColumn(COL_FECHA).setCellRenderer(tableResultado.getLeftRender());

		WebScrollPane webScrollPane = new WebScrollPane(tableResultado);
		getContentPane().add(webScrollPane, BorderLayout.CENTER);

		JPanel pnlOpciones = new JPanel();
		getContentPane().add(pnlOpciones, BorderLayout.NORTH);
		pnlOpciones.setLayout(new BorderLayout(0, 0));

		lblStatus = new JLabel("lblStatus");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		pnlOpciones.add(lblStatus, BorderLayout.NORTH);
		lblStatus.setFont(Common.getStandarFontItalic(20));

		chkActivado = new JCheckBox("Activado");
		pnlOpciones.add(chkActivado, BorderLayout.WEST);
		chkActivado.setFont(Common.getStandarFont());

		JPanel panel_2 = new JPanel();
		pnlOpciones.add(panel_2, BorderLayout.CENTER);

		JLabel lblEjecutaTodosLos = new JLabel("Ejecuta Todos los dias a las:");
		panel_2.add(lblEjecutaTodosLos);
		lblEjecutaTodosLos.setFont(Common.getStandarFont());

		txtHs = new JTextField();
		txtHs.setFont(Common.getStandarFont());
		panel_2.add(txtHs);
		txtHs.setColumns(10);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblDB = new JLabel("New label");
		lblDB.setFont(Common.getStandarFont());
		panel.add(lblDB, BorderLayout.EAST);
	}

	/**
	 * 
	 */

}
