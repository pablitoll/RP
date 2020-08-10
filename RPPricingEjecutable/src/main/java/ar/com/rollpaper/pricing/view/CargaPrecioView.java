package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebFormattedTextField;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.interfaces.RPTableEvent;
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
	public static final int COL_DESDE_FAMILIA = 4;
	public static final int COL_HASTA_FAMILIA = 5;
	public static final int COL_COMSISION_FAMILIA = 6;
	public static final int COL_REFERENCIA_FAMILIA = 7;
	public static final int COL_ESTA_VIGENTE_FAMILIA = 8;
	public static final int COL_REGISTRO_FAMILIA = 9;

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
	public static final int COL_COMISION_ESPECIFICO = 10;
	public static final int COL_REFERENCIA_ESPECIFICO = 11;
	public static final int COL_TC_ESPECIFICO = 12;
	public static final int COL_ESTA_VIGENTE_ESPECIFICO = 13;
	public static final int COL_ESTA_EN_LISTA_ESPECIFICO = 14;
	public static final int COL_REGISTRO_ESPECIFICO = 15;

	public WebFormattedTextField txtNroCliente;
	public RPTable tableDescEspecifico;
	public RPTable tableDescFamilia;
	public WebComboBox cbNroLista;
	private JPanel pnlBotonesTabla;
	public JButtonRP btnAgregar;
	public JButtonRP btnEliminar;
	public WebTabbedPane tabPanel;
	private JPanel panelCentral;
	public WebLabel lblError;
	public JButtonRP btnModificar;
	public JLabel lblNombreCliente;
	public JLabel lblNombreLista;
	public JLabel lblNombreLegal;
	private JLabel lblNombre_1;
	private JLabel lbl_1;
	private JLabel lblNombreLegal_1;
	public JButtonRP btnAgregarLista;
	public JButtonRP btnEliminarLista;
	private JPanel pnlBotonLista;
	public JButtonRP btnImpactarPrecios;
	private JLabel lblTitle;
	public JButtonRP btnCancelar;
	public JCheckBox chkSoloVigentes;
	public JCheckBox chkMultiplicador;
	public componenteNumerico txtMultiplicador;
	private JPanel pnlSupGrillas;
	private JPanel pnlNorth;
	private JPanel sur;
	private JPanel pnlTexto;
	private JPanel panel_1;
	public JCheckBox chkBusquedaCodArticulo;
	public JCheckBox chkBusquedaCodNombre;
	public JCheckBox chkBusquedaDescrip;
	private JPanel panel_2;
	public JTextField txtBusqueda;
	private JPanel panel;
	public JRadioButton rdArticuloLista;
	public JRadioButton rbTodos;
	public JRadioButton rdArticuloEspecifico;
	public JButtonRP btnExportar;

	public CargaPrecioView() throws Exception {
		super();
		setTitle("Carga de Precio de Cliente");

		pnlBotonesTabla = new JPanel();
		getContentPane().add(pnlBotonesTabla, BorderLayout.EAST);
		GridBagLayout gbl_pnlBotonesTabla = new GridBagLayout();
		gbl_pnlBotonesTabla.columnWidths = new int[] { 89, 0 };
		gbl_pnlBotonesTabla.rowHeights = new int[] { 23, 0, 0, 0, 0 };
		gbl_pnlBotonesTabla.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pnlBotonesTabla.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlBotonesTabla.setLayout(gbl_pnlBotonesTabla);

		btnAgregar = new JButtonRP("Agregar");
		btnAgregar.setIcon(Common.loadIconMenu("com/alee/managers/notification/icons/types/plus.png"));

		btnAgregar.setMnemonic(KeyEvent.VK_PLUS);
		btnAgregar.setFont(Common.getStandarFont());
		GridBagConstraints gbc_btnModificar_1 = new GridBagConstraints();
		gbc_btnModificar_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModificar_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificar_1.gridx = 0;
		gbc_btnModificar_1.gridy = 0;
		pnlBotonesTabla.add(btnAgregar, gbc_btnModificar_1);

		btnModificar = new JButtonRP("Modificar");
		btnModificar.setIcon(CommonUtils.loadIcon(ConstantesRP.IMG_EDIT_ICON, 15, 15));
		btnModificar.setMnemonic(KeyEvent.VK_MINUS);
		btnModificar.setFont(Common.getStandarFont());
		GridBagConstraints gbc_btnModificar_2 = new GridBagConstraints();
		gbc_btnModificar_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModificar_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificar_2.gridx = 0;
		gbc_btnModificar_2.gridy = 1;
		pnlBotonesTabla.add(btnModificar, gbc_btnModificar_2);

		btnEliminar = new JButtonRP("Eliminar");
		btnEliminar.setIcon(Common.loadIconMenu("com/alee/managers/notification/icons/types/minus.png"));

		btnEliminar.setMnemonic(KeyEvent.VK_MINUS);
		btnEliminar.setFont(Common.getStandarFont());
		GridBagConstraints gbc_buttonRP_1 = new GridBagConstraints();
		gbc_buttonRP_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonRP_1.insets = new Insets(0, 0, 5, 0);
		gbc_buttonRP_1.gridx = 0;
		gbc_buttonRP_1.gridy = 2;
		pnlBotonesTabla.add(btnEliminar, gbc_buttonRP_1);

		btnExportar = new JButtonRP("Exportar a Excel");
		btnExportar.setIcon(CommonUtils.loadIcon(ConstantesRP.IMG_EXCEL, 15, 15));
		btnExportar.setFont(Common.getStandarFont());

		GridBagConstraints gbc_buttonRP = new GridBagConstraints();
		gbc_buttonRP.gridx = 0;
		gbc_buttonRP.gridy = 3;
		pnlBotonesTabla.add(btnExportar, gbc_buttonRP);

		pnlNorth = new JPanel();
		getContentPane().add(pnlNorth, BorderLayout.NORTH);
		pnlNorth.setLayout(new BorderLayout(0, 0));
		lblTitle = new JLabel("lblTitle");
		lblTitle.setText(getTitle());

		JPanel panelSuperior = new JPanel();
		pnlNorth.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setFocusable(false);
		panelSuperior.setBorder(new EmptyBorder(5, 5, 5, 0));
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[] { 76, 100, 50, 46, 46, 50, 0, 0, 0, 0, 0 };
		gbl_panelSuperior.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelSuperior.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelSuperior.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		panelSuperior.setLayout(gbl_panelSuperior);

		lblTitle.setFont(Common.getStandarFontBold(18));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 10;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panelSuperior.add(lblTitle, gbc_label);

		JLabel lblcliente = new JLabel("Nro. de Cliente:");
		lblcliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
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
		gbc_lblNombre_1.gridx = 3;
		gbc_lblNombre_1.gridy = 1;
		panelSuperior.add(lblNombre_1, gbc_lblNombre_1);

		lblNombreCliente = new JLabel("xxxxxxxxxxxxxxxxxxxx");
		lblNombreCliente.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNombreCliente = new GridBagConstraints();
		gbc_lblNombreCliente.anchor = GridBagConstraints.WEST;
		gbc_lblNombreCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreCliente.gridx = 4;
		gbc_lblNombreCliente.gridy = 1;
		panelSuperior.add(lblNombreCliente, gbc_lblNombreCliente);

		lblNombreLegal_1 = new JLabel("Nombre Legal:");
		lblNombreLegal_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombreLegal_1 = new GridBagConstraints();
		gbc_lblNombreLegal_1.anchor = GridBagConstraints.EAST;
		gbc_lblNombreLegal_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreLegal_1.gridx = 5;
		gbc_lblNombreLegal_1.gridy = 1;
		panelSuperior.add(lblNombreLegal_1, gbc_lblNombreLegal_1);

		lblNombreLegal = new JLabel("New label");
		lblNombreLegal.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNombreLegal = new GridBagConstraints();
		gbc_lblNombreLegal.anchor = GridBagConstraints.WEST;
		gbc_lblNombreLegal.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreLegal.gridx = 6;
		gbc_lblNombreLegal.gridy = 1;
		panelSuperior.add(lblNombreLegal, gbc_lblNombreLegal);

		pnlSupGrillas = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 7;
		gbc_panel.gridy = 1;
		panelSuperior.add(pnlSupGrillas, gbc_panel);

		JLabel lblNewLabel_1 = new JLabel("Nro de Lista de Precio");
		lblNewLabel_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelSuperior.add(lblNewLabel_1, gbc_lblNewLabel_1);

		cbNroLista = new WebComboBox();
		cbNroLista.setFont(Common.getStandarFont());

		GridBagConstraints gbc_txtNroLista = new GridBagConstraints();
		gbc_txtNroLista.insets = new Insets(0, 0, 0, 5);
		gbc_txtNroLista.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNroLista.gridx = 1;
		gbc_txtNroLista.gridy = 2;
		panelSuperior.add(cbNroLista, gbc_txtNroLista);

		btnAgregarLista = new JButtonRP("");
		btnAgregarLista.setLeftRightSpacing(0);
		btnAgregarLista.setToolTipText("Agregar Lista");
		btnAgregarLista.setFont(Common.getStandarFont());
		btnAgregarLista.setIcon(CommonUtils.loadIcon("com/alee/managers/notification/icons/types/plus.png", 15, 15));

		btnEliminarLista = new JButtonRP("");
		btnEliminarLista.setLeftRightSpacing(0);
		btnEliminarLista.setToolTipText("Eliminar Lista");
		btnEliminarLista.setFont(Common.getStandarFont());
		btnEliminarLista.setIcon(CommonUtils.loadIcon("com/alee/managers/notification/icons/types/minus.png", 15, 15));

		pnlBotonLista = new JPanel();
		FlowLayout fl_pnlBotonLista = new FlowLayout(FlowLayout.CENTER, 0, 0);
		pnlBotonLista.setLayout(fl_pnlBotonLista);
		pnlBotonLista.add(btnAgregarLista);
		pnlBotonLista.add(btnEliminarLista);
		GridBagConstraints gbcpnlBotonLista = new GridBagConstraints();
		gbcpnlBotonLista.insets = new Insets(0, 0, 0, 5);
		gbcpnlBotonLista.fill = GridBagConstraints.BOTH;
		gbcpnlBotonLista.gridx = 2;
		gbcpnlBotonLista.gridy = 2;
		panelSuperior.add(pnlBotonLista, gbcpnlBotonLista);

		lbl_1 = new JLabel("Nombre Lista ");
		lbl_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lbl_1 = new GridBagConstraints();
		gbc_lbl_1.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_1.gridx = 3;
		gbc_lbl_1.gridy = 2;
		panelSuperior.add(lbl_1, gbc_lbl_1);

		lblNombreLista = new JLabel("New label");
		lblNombreLista.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNombreLista = new GridBagConstraints();
		gbc_lblNombreLista.anchor = GridBagConstraints.WEST;
		gbc_lblNombreLista.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreLista.gridx = 4;
		gbc_lblNombreLista.gridy = 2;
		panelSuperior.add(lblNombreLista, gbc_lblNombreLista);

		chkMultiplicador = new JCheckBox("Habilitar Multiplicador");
		chkMultiplicador.setFont(Common.getStandarFont());
		GridBagConstraints gbc_chckbxFactorDeCorrecion = new GridBagConstraints();
		gbc_chckbxFactorDeCorrecion.anchor = GridBagConstraints.EAST;
		gbc_chckbxFactorDeCorrecion.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxFactorDeCorrecion.gridx = 5;
		gbc_chckbxFactorDeCorrecion.gridy = 2;
		panelSuperior.add(chkMultiplicador, gbc_chckbxFactorDeCorrecion);

		txtMultiplicador = new componenteNumerico();
		txtMultiplicador.setMinimumSize(new Dimension(70, 29));
		txtMultiplicador.setMinimumWidth(70);
		txtMultiplicador.setFont(Common.getStandarFont());
		txtMultiplicador.setCantEnteros(1);
		txtMultiplicador.setCantDecimales(2);
		txtMultiplicador.setColumns(5);
		GridBagConstraints gbc_webFormattedTextField = new GridBagConstraints();
		gbc_webFormattedTextField.anchor = GridBagConstraints.WEST;
		gbc_webFormattedTextField.insets = new Insets(0, 0, 0, 5);
		gbc_webFormattedTextField.gridx = 6;
		gbc_webFormattedTextField.gridy = 2;
		panelSuperior.add(txtMultiplicador, gbc_webFormattedTextField);

		chkSoloVigentes = new JCheckBox("Ver solo Vigentes");
		chkSoloVigentes.setFont(Common.getStandarFont());
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxNewCheckBox.gridx = 8;
		gbc_chckbxNewCheckBox.gridy = 2;
		panelSuperior.add(chkSoloVigentes, gbc_chckbxNewCheckBox);

		panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout(0, 0));
		tabPanel = new WebTabbedPane();
		tabPanel.setTabPlacement(WebTabbedPane.TOP);
		tabPanel.setFont(Common.getStandarFont());

		panelCentral.add(tabPanel);
		getContentPane().add(panelCentral, BorderLayout.CENTER);

		String[] headerDescFamilia = { "Codigo", "Nombre Familia", "% Dto. 1", "% Dto. 2", "Desde", "Hasta",
				"% Comision", "Referencia", "Esta Vigente", "" };
		String[][] dataDesFamilia = { {} };

		tableDescFamilia = new RPTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				if (row > -1) {
					boolean estaVigente = false;

					Object objEstaVigente = getValueAt(row, convertColumnIndexToView(COL_ESTA_VIGENTE_FAMILIA));
					if (objEstaVigente != null) {
						try {
							estaVigente = CommonUtils.string2Boolean(String.valueOf(objEstaVigente));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (!estaVigente && (row != getSelectedRow())) {
						c.setForeground(Color.RED);
					}
				}
				return c;
			}
		};

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

		tableDescFamilia.setRpTableEvent(new RPTableEvent() {

			@Override
			public void doubleClick(Integer fila, Integer columna) {
				btnModificar.doClick();

			}
		});

		WebScrollPane spDescLista = new WebScrollPane(tableDescFamilia);

		tabPanel.addTab("Descuento por Familia", spDescLista);

		String[] headerDescEspecifico = { "Articulo", "Nombre", "Descripcion", "Unidad", "% Dto. 1", "% Dto. 2",
				"Moneda", "Precio", "Desde", "Hasta", "% Comision", "Referencia", "TC", "Esta Vigente", "Esta en Lista",
				"" };
		String[][] dataDesEspecifico = { {} };

		tableDescEspecifico = new RPTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				if (row > -1) {
					boolean estaEnLista = false;
					boolean estaVigente = false;

					Object objEstaVigente = getValueAt(row, convertColumnIndexToView(COL_ESTA_VIGENTE_ESPECIFICO));
					if (objEstaVigente != null) {
						try {
							estaVigente = CommonUtils.string2Boolean(String.valueOf(objEstaVigente));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					Object obkEstaEnLista = getValueAt(row, convertColumnIndexToView(COL_ESTA_EN_LISTA_ESPECIFICO));
					if (obkEstaEnLista != null) {
						try {
							estaEnLista = CommonUtils.string2Boolean(String.valueOf(obkEstaEnLista));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (!estaEnLista && (row != getSelectedRow())) {
						c.setBackground(Color.YELLOW);
					}

					if (!estaVigente && (row != getSelectedRow())) {
						c.setForeground(Color.RED);
					}
				}
				return c;
			}
		};
		tableDescEspecifico.setColToIgnorar(new Integer[] { COL_DESC_ESPECIFICO, COL_REGISTRO_ESPECIFICO });

		tableDescEspecifico.setModel(new DefaultTableModel(dataDesEspecifico, headerDescEspecifico));
		tableDescEspecifico.setRowHeight(30);
		tableDescEspecifico.setEditable(false);
		tableDescEspecifico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		tableDescEspecifico.getColumnModel().getColumn(COL_TC_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_ESTA_VIGENTE_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getCenterRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_ESTA_EN_LISTA_ESPECIFICO)
				.setCellRenderer(tableDescEspecifico.getCenterRender());

		tableDescEspecifico.setRpTableEvent(new RPTableEvent() {

			@Override
			public void doubleClick(Integer fila, Integer columna) {
				btnModificar.doClick();

			}
		});

		panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));

		WebScrollPane spDescEspecifico = new WebScrollPane(tableDescEspecifico);
		tabPanel.addTab("Descuentos y Precios Especificos", panel);
		panel.add(spDescEspecifico);

		sur = new JPanel();
		panel.add(sur, BorderLayout.NORTH);
		GridBagLayout gbl_sur = new GridBagLayout();
		gbl_sur.columnWidths = new int[] { 250, 0, 0, 0, 0 };
		gbl_sur.rowHeights = new int[] { 0, 0 };
		gbl_sur.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_sur.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		sur.setLayout(gbl_sur);

		pnlTexto = new JPanel();
		pnlTexto.setBorder(
				new TitledBorder(null, "Filtro por Texto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlTexto = new GridBagConstraints();
		gbc_pnlTexto.insets = new Insets(0, 0, 0, 5);
		gbc_pnlTexto.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlTexto.gridx = 0;
		gbc_pnlTexto.gridy = 0;
		sur.add(pnlTexto, gbc_pnlTexto);
		pnlTexto.setLayout(new BorderLayout(0, 0));

		txtBusqueda = new JTextField();
		pnlTexto.add(txtBusqueda);
		txtBusqueda.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Campos Filtro por Texto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		sur.add(panel_1, gbc_panel_1);

		chkBusquedaCodArticulo = new JCheckBox("Cod. Art.");
		chkBusquedaCodArticulo.setFont(Common.getStandarFont());
		panel_1.add(chkBusquedaCodArticulo);

		chkBusquedaCodNombre = new JCheckBox("Nombre");
		chkBusquedaCodNombre.setFont(Common.getStandarFont());
		panel_1.add(chkBusquedaCodNombre);

		chkBusquedaDescrip = new JCheckBox("Descrip.");
		chkBusquedaDescrip.setFont(Common.getStandarFont());
		panel_1.add(chkBusquedaDescrip);

		panel_2 = new JPanel();
		ButtonGroup br = new ButtonGroup();

		panel_2.setFont(Common.getStandarFont());
		panel_2.setBorder(new TitledBorder(null, "Filtros", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		sur.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		rbTodos = new JRadioButton("Ambos");
		br.add(rbTodos);
		panel_2.add(rbTodos);

		rdArticuloLista = new JRadioButton("Articulos de Lista");
		br.add(rdArticuloLista);
		panel_2.add(rdArticuloLista);

		rdArticuloEspecifico = new JRadioButton("Articulos Específicos");
		br.add(rdArticuloEspecifico);
		panel_2.add(rdArticuloEspecifico);
		lblError = new WebLabel("");
		lblError.setFont(Common.getStandarFontBold());
		panelCentral.add(lblError, BorderLayout.NORTH);

		btnCancelar = new JButtonRP("Cancelar");
		btnCancelar.setMnemonic(KeyEvent.VK_ESCAPE);
		btnCancelar.setFont(Common.getStandarFont());
		btnCancelar.setIcon(
				new ImageIcon(BaseViewMVCExtendida.class.getResource("/com/alee/laf/filechooser/icons/remove.png")));
		pnlInferiorBotones.add(btnCancelar);

		btnImpactarPrecios = new JButtonRP("Terminar Carga e Impactar precios");
		btnImpactarPrecios.setMnemonic(KeyEvent.VK_ESCAPE);
		btnImpactarPrecios.setIcon(Common.loadIconMenu(ConstantesRP.IMG_PESOS));
		btnImpactarPrecios.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnImpactarPrecios);

	}

	@Override
	public void asignarBotonesPantExtendida() {
		asignarBotonAccion(btnAgregar, ConstantesRP.PantCarPrecio.AGREGAR.toString());
		asignarBotonAccion(btnEliminar, ConstantesRP.PantCarPrecio.ELIMINAR.toString());
		asignarBotonAccion(btnModificar, ConstantesRP.PantCarPrecio.MODIFICAR.toString());
		asignarBotonAccion(btnAgregarLista, ConstantesRP.PantCarPrecio.AGREGAR_LISTA.toString());
		asignarBotonAccion(btnEliminarLista, ConstantesRP.PantCarPrecio.ELIMINAR_LISTA.toString());
		asignarBotonAccion(btnImpactarPrecios, ConstantesRP.PantCarPrecio.IMPACTAR_PRECIOS.toString());
		asignarBotonAccion(btnCancelar, ConstantesRP.PantCarPrecio.CANCELAR.toString());
		asignarBotonAccion(btnExportar, ConstantesRP.PantCarPrecio.EXPORTAR_EXCEL.toString());
	}

	public void setCerrarVisible(Boolean visible) {
		btnCerrar.setVisible(visible);
	}

}