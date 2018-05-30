package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebFormattedTextField;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseViewMVCExtendida;

public class CargaPrecioView extends BaseViewMVCExtendida {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int COL_ID_FAMILIA = 0;
	public static final int COL_NOMBRE_FAMILIA = 1;	
	public static final int COL_1DESC_FAMILIA = 2;
	public static final int COL_2DESC_FAMILIA = 3;
	public static final int COL_DESDE_FAMIIA = 4;
	public static final int COL_HASTA_FAMILIA = 5;
	public static final int COL_REFERENCIA_FAMILIA = 6;
	public static final int COL_REGISTRO_FAMILIA = 7;
	

	public static final int COL_ID_ESPECIFICO = 0;
	public static final int COL_NOMBRE_ESPECIFICO = 1;
	public static final int COL_DESC_ESPECIFICO = 2;
	public static final int COL_UNIDAD_ESPECIFICO = 3;
	public static final int COL_1DESC_ESPECIFICO = 4;
	public static final int COL_2DESC_ESPECIFICO = 5;	
	public static final int COL_MONEDA_ESPECIFICO = 6;
	public static final int COL_PRECIO_ESPECIFICO = 7;	
	public static final int COL_DESDE_ESPECIFICO = 8;
	public static final int COL_HASTA_ESPECIFICO = 9;
	public static final int COL_REFERENCIA_ESPECIFICO = 10;
	public static final int COL_REGISTRO_ESPECIFICO = 11;
	


	public WebFormattedTextField txtNroCliente;
	public RPTable tableDescEspecifico;
	public RPTable tableDescFamilia;
	public WebFormattedTextField txtNroLista;
	public JButtonRP btnBorrar;
	public JLabel lblNombreLista;
	public JLabel lblNombreLegal;
	public JLabel lblNombreCliente;
	private JPanel pnlBotonesTabla;
	public JButtonRP btnAgregar;
	public JButtonRP btnEliminar;
	public WebTabbedPane tabPanel;
	public JButtonRP btnCancelar;
	private JPanel pnlCenter;
	private JPanel panelCentral;
	public WebLabel lblError;
	public JButtonRP btnModificar;

	public CargaPrecioView() throws Exception {
		super();
		setTitle("Carga de Precio de Cliente");

		JPanel panelSuperior = new JPanel();
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[] { 76, 100, 50, 46, 46, 0, 0 };
		gbl_panelSuperior.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelSuperior.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelSuperior.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelSuperior.setLayout(gbl_panelSuperior);

		JLabel lblcliente = new JLabel("Nro. de Cliente:");
		lblcliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelSuperior.add(lblcliente, gbc_lblNewLabel);

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
		panelSuperior.add(txtNroCliente, gbc_txtNroCliente);
		txtNroCliente.setColumns(10);

		lblNombreCliente = new JLabel("New label");
		lblNombreCliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreCliente = new GridBagConstraints();
		gbc_lblNombreCliente.anchor = GridBagConstraints.WEST;
		gbc_lblNombreCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreCliente.gridx = 3;
		gbc_lblNombreCliente.gridy = 0;
		panelSuperior.add(lblNombreCliente, gbc_lblNombreCliente);

		lblNombreLegal = new JLabel("New label");
		lblNombreLegal.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreLegal = new GridBagConstraints();
		gbc_lblNombreLegal.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombreLegal.anchor = GridBagConstraints.WEST;
		gbc_lblNombreLegal.gridx = 5;
		gbc_lblNombreLegal.gridy = 0;
		panelSuperior.add(lblNombreLegal, gbc_lblNombreLegal);

		JLabel lblNewLabel_1 = new JLabel("Nro de Lista de Precio");
		lblNewLabel_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelSuperior.add(lblNewLabel_1, gbc_lblNewLabel_1);

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
		panelSuperior.add(txtNroLista, gbc_txtNroLista);
		txtNroLista.setColumns(10);

		lblNombreLista = new JLabel("New label");
		lblNombreLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreLista = new GridBagConstraints();
		gbc_lblNombreLista.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreLista.gridx = 3;
		gbc_lblNombreLista.gridy = 1;
		panelSuperior.add(lblNombreLista, gbc_lblNombreLista);

		pnlBotonesTabla = new JPanel();
		getContentPane().add(pnlBotonesTabla, BorderLayout.EAST);
		GridBagLayout gbl_pnlBotonesTabla = new GridBagLayout();
		gbl_pnlBotonesTabla.columnWidths = new int[] { 89, 0 };
		gbl_pnlBotonesTabla.rowHeights = new int[] { 23, 0, 0, 0, 0 };
		gbl_pnlBotonesTabla.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pnlBotonesTabla.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlBotonesTabla.setLayout(gbl_pnlBotonesTabla);

		btnAgregar = new JButtonRP("Agregar");
		btnAgregar.setMnemonic(KeyEvent.VK_PLUS);
		btnAgregar.setFont(Common.getStandarFont());
		GridBagConstraints gbc_btnModificar_1 = new GridBagConstraints();
		gbc_btnModificar_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModificar_1.anchor = GridBagConstraints.NORTH;
		gbc_btnModificar_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificar_1.gridx = 0;
		gbc_btnModificar_1.gridy = 0;
		pnlBotonesTabla.add(btnAgregar, gbc_btnModificar_1);

		btnModificar = new JButtonRP("Modificar");
		btnModificar.setMnemonic(KeyEvent.VK_MINUS);
		btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_btnModificar_2 = new GridBagConstraints();
		gbc_btnModificar_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModificar_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificar_2.gridx = 0;
		gbc_btnModificar_2.gridy = 1;
		pnlBotonesTabla.add(btnModificar, gbc_btnModificar_2);

		btnEliminar = new JButtonRP("Eliminar");
		btnEliminar.setMnemonic(KeyEvent.VK_MINUS);
		btnEliminar.setFont(Common.getStandarFont());
		GridBagConstraints gbc_buttonRP_1 = new GridBagConstraints();
		gbc_buttonRP_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonRP_1.insets = new Insets(0, 0, 5, 0);
		gbc_buttonRP_1.anchor = GridBagConstraints.NORTH;
		gbc_buttonRP_1.gridx = 0;
		gbc_buttonRP_1.gridy = 2;
		pnlBotonesTabla.add(btnEliminar, gbc_buttonRP_1);

		pnlCenter = new JPanel();
		getContentPane().add(pnlCenter, BorderLayout.WEST);

		panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout(0, 0));
		tabPanel = new WebTabbedPane();
		tabPanel.setTabPlacement(WebTabbedPane.TOP);
		tabPanel.setFont(Common.getStandarFont());

		panelCentral.add(tabPanel);
		getContentPane().add(panelCentral, BorderLayout.CENTER);

		tableDescFamilia = new RPTable();
		String[] headerDescFamilia = { "Codigo", "Nombre Familia", "% Dto. 1", "% Dto. 2", "Desde", "Hasta" , "Referencia", ""};
		String[][] dataDesFamilia = { {} };

		tableDescFamilia = new RPTable();
		tableDescFamilia.setModel(new DefaultTableModel(dataDesFamilia, headerDescFamilia));		
		tableDescFamilia.setRowHeight(30);
		tableDescFamilia.setEditable(false);
		tableDescFamilia.getColumnModel().getColumn(COL_REGISTRO_FAMILIA).setMaxWidth(0);
		tableDescFamilia.getColumnModel().getColumn(COL_REGISTRO_FAMILIA).setMinWidth(0);
		tableDescFamilia.getColumnModel().getColumn(COL_REGISTRO_FAMILIA).setPreferredWidth(0);

		WebScrollPane spDescLista = new WebScrollPane(tableDescFamilia);

		tabPanel.addTab("Descuento por Familia", spDescLista);

		String[] headerDescEspecifico = { "Articulo", "Nombre", "Descripción", "Unidad", "% Dto. 1", "% Dto. 2",
				"Moneda", "Precio", "Desde", "Hasta", "Referencia" , ""};
		String[][] dataDesEspecifico = { {} };

		tableDescEspecifico = new RPTable();
		tableDescEspecifico.setModel(new DefaultTableModel(dataDesEspecifico, headerDescEspecifico));
		tableDescEspecifico.setRowHeight(30);
		tableDescEspecifico.setEditable(false);

		tableDescEspecifico.getColumnModel().getColumn(COL_REGISTRO_ESPECIFICO).setMaxWidth(0);
		tableDescEspecifico.getColumnModel().getColumn(COL_REGISTRO_ESPECIFICO).setMinWidth(0);
		tableDescEspecifico.getColumnModel().getColumn(COL_REGISTRO_ESPECIFICO).setPreferredWidth(0);

		WebScrollPane spDescEspecifico = new WebScrollPane(tableDescEspecifico);
		tabPanel.addTab("Descuentos y Precios Especificos", spDescEspecifico);

		lblError = new WebLabel("");
		lblError.setFont(Common.getStandarFontBold());
		panelCentral.add(lblError, BorderLayout.NORTH);

		btnCancelar = new JButtonRP("Cancelar");
		btnCancelar.setFont(Common.getStandarFont());
		btnCancelar.setMnemonic(KeyEvent.VK_ESCAPE);
		pnlInferiorBotones.add(btnCancelar);

		btnBorrar = new JButtonRP("Borrar");
		btnBorrar.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnBorrar);

	}

	@Override
	public void asignarBotonesPantExtendida() {
		asignarBotonAccion(btnBorrar, ConstantesRP.PantCarPrecio.BORRAR.toString());
		asignarBotonAccion(btnAgregar, ConstantesRP.PantCarPrecio.AGREGAR.toString());
		asignarBotonAccion(btnEliminar, ConstantesRP.PantCarPrecio.ELIMINAR.toString());
		asignarBotonAccion(btnCancelar, ConstantesRP.PantCarPrecio.CANCELAR.toString());
		asignarBotonAccion(btnModificar, ConstantesRP.PantCarPrecio.MODIFICAR.toString());
	}

	public void setCerrarVisible(Boolean visible) {
		btnCerrar.setVisible(visible);
	}

}
