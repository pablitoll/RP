package ar.com.rollpaper.pricing.view;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPImporte;
import ar.com.rp.ui.pantalla.BaseViewMVCExtendida;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebFormattedTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextField;

public class CargaClienteEsclavoView extends BaseViewMVCExtendida {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int COL_DESC = 1;
	public static final int COL_DESC_LEGAL = 2;
	public static final int COL_ID = 0;
	
	public WebFormattedTextField txtNroCliente;
	public WebTable tableEsclavo;
	public JButtonRP btnGrabar;
	public JLabel lblNombreLista;
	public JLabel lblNombreLegal;
	public JLabel lblNombreCliente;
	private JButtonRP btnImprimir;
	private JButtonRP btnImprimirTodo;
	private JPanel pnlCentral;
	private JPanel panel;
	public JButtonRP btnAgregar;
	public JButtonRP btnEliminar;
	public JLabel lblNroLista;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	public CargaClienteEsclavoView() throws Exception {
		super();
		setTitle("Carga de Cliente/Esclavo");

		JPanel pnlSuperior = new JPanel();
		getContentPane().add(pnlSuperior, BorderLayout.NORTH);
		GridBagLayout gbl_pnlSuperior = new GridBagLayout();
		gbl_pnlSuperior.columnWidths = new int[] { 76, 100, 50, 46, 150, 0, 0 };
		gbl_pnlSuperior.rowHeights = new int[] { 0, 0, 0 };
		gbl_pnlSuperior.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlSuperior.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pnlSuperior.setLayout(gbl_pnlSuperior);

		JLabel lblcliente = new JLabel("Nro. de Cliente:");
		lblcliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNroLista = new GridBagConstraints();
		gbc_lblNroLista.anchor = GridBagConstraints.WEST;
		gbc_lblNroLista.insets = new Insets(0, 0, 5, 5);
		gbc_lblNroLista.gridx = 0;
		gbc_lblNroLista.gridy = 0;
		pnlSuperior.add(lblcliente, gbc_lblNroLista);

		txtNroCliente = new WebFormattedTextField();
		txtNroCliente.setFont(Common.getStandarFont());
		txtNroCliente.setInputPrompt("Ingrese nro. Cliente");
		txtNroCliente.setInputPromptFont(Common.getStandarFontItalic());
		txtNroCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNroCliente.setColumns(10);
		txtNroCliente.clear();
		GridBagConstraints gbc_txtNroCliente = new GridBagConstraints();
		gbc_txtNroCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNroCliente.anchor = GridBagConstraints.NORTH;
		gbc_txtNroCliente.insets = new Insets(0, 0, 5, 5);
		gbc_txtNroCliente.gridx = 1;
		gbc_txtNroCliente.gridy = 0;
		pnlSuperior.add(txtNroCliente, gbc_txtNroCliente);
		txtNroCliente.setColumns(10);
		
		lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 0;
		pnlSuperior.add(lblNewLabel, gbc_lblNewLabel);

		lblNombreCliente = new JLabel("New label");
		lblNombreCliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreCliente = new GridBagConstraints();
		gbc_lblNombreCliente.anchor = GridBagConstraints.WEST;
		gbc_lblNombreCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreCliente.gridx = 3;
		gbc_lblNombreCliente.gridy = 0;
		pnlSuperior.add(lblNombreCliente, gbc_lblNombreCliente);
		
		lblNewLabel_3 = new JLabel("Nombre Legal:");
		lblNewLabel_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 4;
		gbc_lblNewLabel_3.gridy = 0;
		pnlSuperior.add(lblNewLabel_3, gbc_lblNewLabel_3);

		lblNombreLegal = new JLabel("New label");
		lblNombreLegal.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreLegal = new GridBagConstraints();
		gbc_lblNombreLegal.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombreLegal.anchor = GridBagConstraints.WEST;
		gbc_lblNombreLegal.gridx = 5;
		gbc_lblNombreLegal.gridy = 0;
		pnlSuperior.add(lblNombreLegal, gbc_lblNombreLegal);

		JLabel lblNewLabel_1 = new JLabel("Nro de Lista de Precio");
		lblNewLabel_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		pnlSuperior.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		lblNroLista = new JLabel("New label");
		lblNroLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNroListaShow = new GridBagConstraints();
		gbc_lblNroListaShow.insets = new Insets(0, 0, 0, 5);
		gbc_lblNroListaShow.gridx = 1;
		gbc_lblNroListaShow.gridy = 1;
		pnlSuperior.add(lblNroLista, gbc_lblNroListaShow);
		
		lblNewLabel_2 = new JLabel("Nombre Lista");
		lblNewLabel_2.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 1;
		pnlSuperior.add(lblNewLabel_2, gbc_lblNewLabel_2);

		lblNombreLista = new JLabel("New label");
		lblNombreLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreLista = new GridBagConstraints();
		gbc_lblNombreLista.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreLista.gridx = 3;
		gbc_lblNombreLista.gridy = 1;
		pnlSuperior.add(lblNombreLista, gbc_lblNombreLista);

		String[] header = { "Nro Cliente", "Nombre del Cliente", "Nombre de Fantasia" };
		String[][] data = { {} };

		btnGrabar = new JButtonRP("Grabar");
		btnGrabar.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnGrabar);
		
		btnImprimir = new JButtonRP("Imprimir");
		btnImprimir.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnImprimir);
		
		btnImprimirTodo = new JButtonRP("Imprimir Todo");
		btnImprimirTodo.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnImprimirTodo);
		
		pnlCentral = new JPanel();
		getContentPane().add(pnlCentral, BorderLayout.CENTER);
		
				tableEsclavo = new WebTable();
				tableEsclavo.setModel(new DefaultTableModel(data, header) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int col) {
						return col == COL_ID;
					}

					@Override
					public Class getColumnClass(int c) {
						return Integer.class;
					}
				});
				tableEsclavo.setFont(Common.getStandarFont());
						pnlCentral.setLayout(new BorderLayout(0, 0));
				
						WebScrollPane spEsclavo = new WebScrollPane(tableEsclavo);
						pnlCentral.add(spEsclavo);
						
						panel = new JPanel();
						getContentPane().add(panel, BorderLayout.EAST);
						GridBagLayout gbl_panel = new GridBagLayout();
						gbl_panel.columnWidths = new int[]{89, 0};
						gbl_panel.rowHeights = new int[]{23, 0, 0};
						gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
						gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
						panel.setLayout(gbl_panel);
						
						btnAgregar = new JButtonRP("Agregar");
						btnAgregar.setFont(Common.getStandarFont());
						GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
						gbc_btnAgregar.anchor = GridBagConstraints.NORTH;
						gbc_btnAgregar.insets = new Insets(0, 0, 5, 0);
						gbc_btnAgregar.gridx = 0;
						gbc_btnAgregar.gridy = 0;
						panel.add(btnAgregar, gbc_btnAgregar);
						
						btnEliminar = new JButtonRP("Eliminar");
						btnEliminar.setFont(Common.getStandarFont());
						GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
						gbc_btnEliminar.anchor = GridBagConstraints.NORTH;
						gbc_btnEliminar.gridx = 0;
						gbc_btnEliminar.gridy = 1;
						panel.add(btnEliminar, gbc_btnEliminar);
	}

	@Override
	public void asignarBotonesPantExtendida() {
		asignarBotonAccion(btnAgregar, ConstantesRP.PantCarClienteEsclabo.AGREGAR.toString());
		asignarBotonAccion(btnEliminar, ConstantesRP.PantCarClienteEsclabo.BORRAR.toString());
		asignarBotonAccion(btnGrabar, ConstantesRP.PantCarClienteEsclabo.GRABAR.toString());
		asignarBotonAccion(btnImprimir, ConstantesRP.PantCarClienteEsclabo.IMPRIMIR.toString());
		asignarBotonAccion(btnImprimirTodo, ConstantesRP.PantCarClienteEsclabo.IMPRIMIR_TODO.toString());
	}

}
