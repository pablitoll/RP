package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.extended.date.WebDateField;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebFormattedTextField;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseViewMVCExtendida;

public class BusquedaVencidoView extends BaseViewMVCExtendida {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int COL_COD_CLIENTE_FAMILIA = 0;
	public static final int COL_NOMBRE_CLIENTE_FAMILIA = 1;
	public static final int COL_ID_FAMILIA = 2;
	public static final int COL_NOMBRE_FAMILIA = 3;
	public static final int COL_1DESC_FAMILIA = 4;
	public static final int COL_2DESC_FAMILIA = 5;
	public static final int COL_DESDE_FAMILIA = 6;
	public static final int COL_HASTA_FAMILIA = 7;
	public static final int COL_COMSISION_FAMILIA = 8;
	public static final int COL_REFERENCIA_FAMILIA = 9;
	public static final int COL_ESTA_VIGENTE_FAMILIA = 10;
	public static final int COL_REGISTRO_FAMILIA = 11;

	public static final int COL_COD_CLIENTE_ESPECIFICO = 0;
	public static final int COL_NOMBRE_CLIENTE_ESPECIFICO = 1;
	public static final int COL_ID_ESPECIFICO = 2;
	public static final int COL_NOMBRE_ESPECIFICO = 3;
	public static final int COL_DESC_ESPECIFICO = 4;
	public static final int COL_UNIDAD_ESPECIFICO = 5;
	public static final int COL_1DESC_ESPECIFICO = 6;
	public static final int COL_2DESC_ESPECIFICO = 7;
	public static final int COL_MONEDA_ESPECIFICO = 8;
	public static final int COL_PRECIO_ESPECIFICO = 9;
	public static final int COL_DESDE_ESPECIFICO = 10;
	public static final int COL_HASTA_ESPECIFICO = 11;
	public static final int COL_COMISION_ESPECIFICO = 12;
	public static final int COL_REFERENCIA_ESPECIFICO = 13;
	public static final int COL_ESTA_VIGENTE_ESPECIFICO = 14;
	public static final int COL_REGISTRO_ESPECIFICO = 15;

	public WebFormattedTextField txtNroCliente;
	public RPTable tableDescEspecifico;
	public RPTable tableDescFamilia;
	public WebTabbedPane tabPanel;
	private JPanel pnlCenter;
	private JPanel panelCentral;
	public WebLabel lblError;
	public JLabel lblNombreCliente;
	public JLabel lblNombreLegal;
	private JLabel lblNombre_1;
	private JLabel lblNombreLegal_1;
	private JLabel lblTitle;
	public JButtonRP btnBuscar;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	public WebDateField txtVencidosAl;
	public WebDateField txtVencidosDesde;
	public WebFormattedTextField txtBusqueda;
	private JButtonRP btnLimpiar;

	public BusquedaVencidoView() throws Exception {
		super();
		lblTitle = new JLabel("lblTitle");
		setTitle("Busqueda de Vencidos");
		lblTitle.setText(getTitle());

		JPanel panelSuperior = new JPanel();
		panelSuperior.setFocusable(false);
		panelSuperior.setBorder(new EmptyBorder(5, 5, 5, 0));
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[] { 76, 150, 46, 150, 0, 0, 50, 0, 0, 0 };
		gbl_panelSuperior.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelSuperior.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelSuperior.rowWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		panelSuperior.setLayout(gbl_panelSuperior);

		lblTitle.setFont(Common.getStandarFontBold(18));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 9;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panelSuperior.add(lblTitle, gbc_label);

		JLabel lblcliente = new JLabel("Nro. de Cliente:");
		lblcliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
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
		gbc_txtNroCliente.gridy = 1;
		panelSuperior.add(txtNroCliente, gbc_txtNroCliente);
		txtNroCliente.setColumns(10);

		lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombre_1 = new GridBagConstraints();
		gbc_lblNombre_1.anchor = GridBagConstraints.EAST;
		gbc_lblNombre_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre_1.gridx = 2;
		gbc_lblNombre_1.gridy = 1;
		panelSuperior.add(lblNombre_1, gbc_lblNombre_1);

		lblNombreCliente = new JLabel("xxxxxxxxxxxxxxxxxxxx");
		lblNombreCliente.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNombreCliente = new GridBagConstraints();
		gbc_lblNombreCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombreCliente.gridwidth = 3;
		gbc_lblNombreCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreCliente.gridx = 3;
		gbc_lblNombreCliente.gridy = 1;
		panelSuperior.add(lblNombreCliente, gbc_lblNombreCliente);

		lblNombreLegal_1 = new JLabel("Nombre Legal:");
		lblNombreLegal_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreLegal_1 = new GridBagConstraints();
		gbc_lblNombreLegal_1.anchor = GridBagConstraints.EAST;
		gbc_lblNombreLegal_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreLegal_1.gridx = 6;
		gbc_lblNombreLegal_1.gridy = 1;
		panelSuperior.add(lblNombreLegal_1, gbc_lblNombreLegal_1);

		lblNombreLegal = new JLabel("New label");
		lblNombreLegal.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNombreLegal = new GridBagConstraints();
		gbc_lblNombreLegal.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombreLegal.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreLegal.gridx = 7;
		gbc_lblNombreLegal.gridy = 1;
		panelSuperior.add(lblNombreLegal, gbc_lblNombreLegal);

		label = new JLabel("Vencidos al:");
		label.setFont(Common.getStandarFont());
		GridBagConstraints gbc_labelv = new GridBagConstraints();
		gbc_labelv.anchor = GridBagConstraints.EAST;
		gbc_labelv.insets = new Insets(0, 0, 0, 5);
		gbc_labelv.gridx = 0;
		gbc_labelv.gridy = 3;
		panelSuperior.add(label, gbc_labelv);

		txtVencidosAl = new WebDateField();
		txtVencidosAl.setFont(Common.getStandarFont());
		txtVencidosAl.setDateFormat(new SimpleDateFormat(FechaManagerUtil.FORMATO_FECHA));
		GridBagConstraints gbc_webFormattedTextField = new GridBagConstraints();
		gbc_webFormattedTextField.insets = new Insets(0, 0, 0, 5);
		gbc_webFormattedTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_webFormattedTextField.gridx = 1;
		gbc_webFormattedTextField.gridy = 3;
		panelSuperior.add(txtVencidosAl, gbc_webFormattedTextField);

		label_1 = new JLabel("Excluir Vencidos Antes del:");
		label_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 3;
		panelSuperior.add(label_1, gbc_label_1);

		txtVencidosDesde = new WebDateField();
		txtVencidosDesde.setDateFormat(new SimpleDateFormat(FechaManagerUtil.FORMATO_FECHA));
		txtVencidosDesde.setFont(Common.getStandarFont());
		GridBagConstraints gbc_webFormattedTextField_1 = new GridBagConstraints();
		gbc_webFormattedTextField_1.insets = new Insets(0, 0, 0, 5);
		gbc_webFormattedTextField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_webFormattedTextField_1.gridx = 3;
		gbc_webFormattedTextField_1.gridy = 3;
		panelSuperior.add(txtVencidosDesde, gbc_webFormattedTextField_1);

		label_2 = new JLabel("Búsqueda:");
		label_2.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 4;
		gbc_label_2.gridy = 3;
		panelSuperior.add(label_2, gbc_label_2);

		txtBusqueda = new WebFormattedTextField();
		txtBusqueda.setFont(Common.getStandarFont());
		GridBagConstraints gbc_webFormattedTextField_2 = new GridBagConstraints();
		gbc_webFormattedTextField_2.gridwidth = 3;
		gbc_webFormattedTextField_2.insets = new Insets(0, 0, 0, 5);
		gbc_webFormattedTextField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_webFormattedTextField_2.gridx = 5;
		gbc_webFormattedTextField_2.gridy = 3;
		panelSuperior.add(txtBusqueda, gbc_webFormattedTextField_2);

		pnlCenter = new JPanel();
		getContentPane().add(pnlCenter, BorderLayout.WEST);

		panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout(0, 0));
		tabPanel = new WebTabbedPane();
		tabPanel.setTabPlacement(WebTabbedPane.TOP);
		tabPanel.setFont(Common.getStandarFont());

		panelCentral.add(tabPanel);
		getContentPane().add(panelCentral, BorderLayout.CENTER);

		String[] headerDescFamilia = { "Cod. Cliente", "Nombre Cliente", "Cod. Familia", "Nombre Familia", "% Dto. 1", "% Dto. 2", "Desde",
				"Hasta", "% Comision", "Referencia", "Esta Vigente", "" };
		String[][] dataDesFamilia = { {} };

		tableDescFamilia = new RPTable();
		tableDescFamilia.setModel(new DefaultTableModel(dataDesFamilia, headerDescFamilia));
		tableDescFamilia.setRowHeight(30);
		tableDescFamilia.setEditable(false);
		tableDescFamilia.getColumnModel()
				.removeColumn(tableDescFamilia.getColumnModel().getColumn(COL_REGISTRO_FAMILIA));

		tableDescFamilia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableDescFamilia.getColumnModel().getColumn(COL_1DESC_FAMILIA)
				.setCellRenderer(tableDescFamilia.getRigthRender());
		tableDescFamilia.getColumnModel().getColumn(COL_2DESC_FAMILIA)
				.setCellRenderer(tableDescFamilia.getRigthRender());
		tableDescFamilia.getColumnModel().getColumn(COL_COMSISION_FAMILIA)
				.setCellRenderer(tableDescFamilia.getRigthRender());
		tableDescFamilia.getColumnModel().getColumn(COL_DESDE_FAMILIA).setCellRenderer(new CellRenderFecha());
		tableDescFamilia.getColumnModel().getColumn(COL_HASTA_FAMILIA).setCellRenderer(new CellRenderFecha());
		tableDescFamilia.getColumnModel().getColumn(COL_ESTA_VIGENTE_FAMILIA)
				.setCellRenderer(tableDescFamilia.getCenterRender());
		tableDescFamilia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		WebScrollPane spDescLista = new WebScrollPane(tableDescFamilia);

		tabPanel.addTab("Descuento por Familia", spDescLista);

		String[] headerDescEspecifico = { "Cod. Cliente", "Nombre Cliente", "Articulo", "Nombre Articulo", "Descripcion", "Unidad", "% Dto. 1",
				"% Dto. 2", "Moneda", "Precio", "Desde", "Hasta", "% Comision", "Referencia", "Esta Vigente", "" };
		String[][] dataDesEspecifico = { {} };

		tableDescEspecifico = new RPTable();
		tableDescEspecifico.setColToIgnorar(new Integer[] { COL_DESC_ESPECIFICO, COL_REGISTRO_ESPECIFICO });

		tableDescEspecifico.setModel(new DefaultTableModel(dataDesEspecifico, headerDescEspecifico));
		tableDescEspecifico.setRowHeight(30);
		tableDescEspecifico.setEditable(false);

		tableDescEspecifico.getColumnModel()
				.removeColumn(tableDescEspecifico.getColumnModel().getColumn(COL_REGISTRO_ESPECIFICO));
		tableDescEspecifico.getColumnModel().getColumn(COL_1DESC_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_2DESC_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_PRECIO_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_UNIDAD_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getCenterRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_MONEDA_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getCenterRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_COMISION_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getRigthRender());		
		tableDescEspecifico.getColumnModel().getColumn(COL_DESDE_ESPECIFICO).setCellRenderer(new CellRenderFecha());
		tableDescEspecifico.getColumnModel().getColumn(COL_HASTA_ESPECIFICO).setCellRenderer(new CellRenderFecha());
		tableDescEspecifico.getColumnModel().getColumn(COL_ESTA_VIGENTE_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getCenterRender());

		WebScrollPane spDescEspecifico = new WebScrollPane(tableDescEspecifico);
		tabPanel.addTab("Descuentos y Precios Especificos", spDescEspecifico);

		lblError = new WebLabel("");
		lblError.setFont(Common.getStandarFontBold());
		panelCentral.add(lblError, BorderLayout.NORTH);

		btnLimpiar = new JButtonRP("Limpiar");
		btnLimpiar.setFont(Common.getStandarFont());
		btnLimpiar.setIcon(Common.loadIconMenu(ConstantesRP.IMG_LIMPIAR));
		pnlInferiorBotones.add(btnLimpiar);

		btnBuscar = new JButtonRP("Buscar");
		btnBuscar.setMnemonic(KeyEvent.VK_ENTER);
		btnBuscar.setFont(Common.getStandarFont());
		btnBuscar.setIcon(Common.loadIconMenu(ConstantesRP.IMG_SEARCH));
		pnlInferiorBotones.add(btnBuscar);
	}

	@Override
	public void asignarBotonesPantExtendida() {
		asignarBotonAccion(btnBuscar, ConstantesRP.PantBusquedaVencidos.BUSCAR.toString());
		asignarBotonAccion(btnLimpiar, ConstantesRP.PantBusquedaVencidos.LIMPIAR.toString());
	}

	public void setCerrarVisible(Boolean visible) {
		btnCerrar.setVisible(visible);
	}

}