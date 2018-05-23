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

public class CargaPrecioView extends BaseViewMVCExtendida {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public WebFormattedTextField txtNroCliente;
	public WebTable tableDescEspecifico;
	public WebTable tableDescLista;
	public WebFormattedTextField txtNroLista;
	public JButtonRP btnBorrar;
	public JButtonRP btnGrabar;
	public JLabel lblNombreLista;
	public JLabel lblNombreLegal;
	public JLabel lblNombreCliente;

	public CargaPrecioView() throws Exception {
		super();
		setTitle("Carga de Precio de Cliente");

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 76, 100, 50, 46, 46, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblcliente = new JLabel("Nro. de Cliente:");
		lblcliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblcliente, gbc_lblNewLabel);

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
		panel.add(txtNroCliente, gbc_txtNroCliente);
		txtNroCliente.setColumns(10);

		lblNombreCliente = new JLabel("New label");
		lblNombreCliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreCliente = new GridBagConstraints();
		gbc_lblNombreCliente.anchor = GridBagConstraints.WEST;
		gbc_lblNombreCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreCliente.gridx = 3;
		gbc_lblNombreCliente.gridy = 0;
		panel.add(lblNombreCliente, gbc_lblNombreCliente);

		lblNombreLegal = new JLabel("New label");
		lblNombreLegal.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreLegal = new GridBagConstraints();
		gbc_lblNombreLegal.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombreLegal.anchor = GridBagConstraints.WEST;
		gbc_lblNombreLegal.gridx = 5;
		gbc_lblNombreLegal.gridy = 0;
		panel.add(lblNombreLegal, gbc_lblNombreLegal);

		JLabel lblNewLabel_1 = new JLabel("Nro de Lista de Precio");
		lblNewLabel_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		txtNroLista = new WebFormattedTextField();
		txtNroLista.setFont(Common.getStandarFont());
		txtNroLista.setInputPrompt("Ingrese nro. Lista");
		txtNroLista.setInputPromptFont(Common.getStandarFontItalic());
		txtNroLista.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNroLista.setColumns(10);
		txtNroLista.clear();

		GridBagConstraints gbc_txtNroLista = new GridBagConstraints();
		gbc_txtNroLista.insets = new Insets(0, 0, 0, 5);
		gbc_txtNroLista.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNroLista.gridx = 1;
		gbc_txtNroLista.gridy = 1;
		panel.add(txtNroLista, gbc_txtNroLista);
		txtNroLista.setColumns(10);

		lblNombreLista = new JLabel("New label");
		lblNombreLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreLista = new GridBagConstraints();
		gbc_lblNombreLista.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreLista.gridx = 3;
		gbc_lblNombreLista.gridy = 1;
		panel.add(lblNombreLista, gbc_lblNombreLista);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tableDescLista = new WebTable();
		WebScrollPane spDescLista = new WebScrollPane(tableDescLista);
		tabbedPane.addTab("Descuento por Lista", null, spDescLista, null);

		String[] header = { "Articulo", "Nombre", "Descripción", "Unidad", "% Dto. 1", "% Dto. 2", "Precio", "Moneda",
				"Desde", "Hasta", "Referencia" };
		String[][] data = { {} };

		tableDescEspecifico = new WebTable();
		tableDescEspecifico.setModel(new DefaultTableModel(data, header) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override 
			public boolean isCellEditable(int row, int col) {
				return col >= 4;
			}
			
			@Override
			public Class getColumnClass(int c) {
				return Double.class;
			}
		});
		tableDescEspecifico.setFont(Common.getStandarFont());

		WebScrollPane spDescEspecifico = new WebScrollPane(tableDescEspecifico);
		tabbedPane.addTab("Descuentos y Precios Especificos", null, spDescEspecifico, null);

		btnBorrar = new JButtonRP("Borrar");
		btnBorrar.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnBorrar);

		btnGrabar = new JButtonRP("Grabar");
		btnGrabar.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnGrabar);
	}

	@Override
	public void asignarBotonesPantExtendida() {
		asignarBotonAccion(btnBorrar, ConstantesRP.PantCarPrecio.BORRAR.toString());
		asignarBotonAccion(btnGrabar, ConstantesRP.PantCarPrecio.GRABAR.toString());
	}

}
