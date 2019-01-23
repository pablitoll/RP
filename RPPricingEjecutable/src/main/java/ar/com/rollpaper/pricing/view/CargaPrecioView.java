package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
import java.awt.Font;

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
	public static final int COL_ESTA_VIGENTE_ESPECIFICO = 12;
	public static final int COL_ESTA_EN_LISTA_ESPECIFICO = 13;
	public static final int COL_REGISTRO_ESPECIFICO = 14;

	public WebFormattedTextField txtNroCliente;
	public RPTable tableDescEspecifico;
	public RPTable tableDescFamilia;
	public WebComboBox cbNroLista;
	private JPanel pnlBotonesTabla;
	public JButtonRP btnAgregar;
	public JButtonRP btnEliminar;
	public WebTabbedPane tabPanel;
	private JPanel pnlCenter;
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

	public CargaPrecioView() throws Exception {
		super();
		lblTitle = new JLabel("lblTitle");
		setTitle("Carga de Precio de Cliente");
		lblTitle.setText(getTitle());
		
		JPanel panelSuperior = new JPanel();
		panelSuperior.setFocusable(false);
		panelSuperior.setBorder(new EmptyBorder(5, 5, 5, 0));
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[] { 76, 100, 50, 46, 46, 50, 0, 0, 0 };
		gbl_panelSuperior.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelSuperior.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelSuperior.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelSuperior.setLayout(gbl_panelSuperior);

		
		lblTitle.setFont(Common.getStandarFontBold(18));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 8;
		gbc_label.insets = new Insets(0, 0, 5, 5);
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
		gbc_lblNombreLegal.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombreLegal.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreLegal.gridx = 6;
		gbc_lblNombreLegal.gridy = 1;
		panelSuperior.add(lblNombreLegal, gbc_lblNombreLegal);

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
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 2;
		panelSuperior.add(pnlBotonLista, gbc_panel);

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

		pnlCenter = new JPanel();
		getContentPane().add(pnlCenter, BorderLayout.WEST);

		panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout(0, 0));
		tabPanel = new WebTabbedPane();
		tabPanel.setTabPlacement(WebTabbedPane.TOP);
		tabPanel.setFont(Common.getStandarFont());

		panelCentral.add(tabPanel);
		getContentPane().add(panelCentral, BorderLayout.CENTER);

		String[] headerDescFamilia = { "Codigo", "Nombre Familia", "% Dto. 1", "% Dto. 2", "Desde", "Hasta", "% Comision", "Referencia", "Esta Vigente", "" };
		String[][] dataDesFamilia = { {} };

		tableDescFamilia = new RPTable();
		tableDescFamilia.setModel(new DefaultTableModel(dataDesFamilia, headerDescFamilia));
		tableDescFamilia.setRowHeight(30);
		tableDescFamilia.setEditable(false);
		tableDescFamilia.getColumnModel().removeColumn(tableDescFamilia.getColumnModel().getColumn(COL_REGISTRO_FAMILIA));

		tableDescFamilia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableDescFamilia.getColumnModel().getColumn(COL_1DESC_FAMILIA).setCellRenderer(tableDescFamilia.getRigthRender());
		tableDescFamilia.getColumnModel().getColumn(COL_2DESC_FAMILIA).setCellRenderer(tableDescFamilia.getRigthRender());
		tableDescFamilia.getColumnModel().getColumn(COL_COMSISION_FAMILIA).setCellRenderer(tableDescFamilia.getRigthRender());
		tableDescFamilia.getColumnModel().getColumn(COL_DESDE_FAMILIA).setCellRenderer(tableDescFamilia.getCenterRender());
		tableDescFamilia.getColumnModel().getColumn(COL_HASTA_FAMILIA).setCellRenderer(tableDescFamilia.getCenterRender());
		tableDescFamilia.getColumnModel().getColumn(COL_ESTA_VIGENTE_FAMILIA).setCellRenderer(tableDescFamilia.getCenterRender());
		tableDescFamilia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableDescFamilia.setRpTableEvent(new RPTableEvent() {

			@Override
			public void doubleClick(Integer fila, Integer columna) {
				btnModificar.doClick();

			}
		});

		WebScrollPane spDescLista = new WebScrollPane(tableDescFamilia);

		tabPanel.addTab("Descuento por Familia", spDescLista);

		String[] headerDescEspecifico = { "Articulo", "Nombre", "Descripción", "Unidad", "% Dto. 1", "% Dto. 2", "Moneda", "Precio", "Desde", "Hasta", "% Comision", "Referencia",
				"Esta Vigente", "Esta en Lista", "" };
		String[][] dataDesEspecifico = { {} };

		tableDescEspecifico = new RPTable();
		tableDescEspecifico.setColToIgnorar(new Integer[] { COL_DESC_ESPECIFICO, COL_REGISTRO_ESPECIFICO });

		tableDescEspecifico.setModel(new DefaultTableModel(dataDesEspecifico, headerDescEspecifico));
		tableDescEspecifico.setRowHeight(30);
		tableDescEspecifico.setEditable(false);

		// tableDescEspecifico.getColumnModel().getColumn(COL_REGISTRO_ESPECIFICO).setMaxWidth(0);
		// tableDescEspecifico.getColumnModel().getColumn(COL_REGISTRO_ESPECIFICO).setMinWidth(0);
		// tableDescEspecifico.getColumnModel().getColumn(COL_REGISTRO_ESPECIFICO).setPreferredWidth(0);
		//
		tableDescEspecifico.getColumnModel().removeColumn(tableDescEspecifico.getColumnModel().getColumn(COL_REGISTRO_ESPECIFICO));

		tableDescEspecifico.getColumnModel().getColumn(COL_1DESC_ESPECIFICO).setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_2DESC_ESPECIFICO).setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_PRECIO_ESPECIFICO).setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_UNIDAD_ESPECIFICO).setCellRenderer(tableDescEspecifico.getCenterRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_MONEDA_ESPECIFICO).setCellRenderer(tableDescEspecifico.getCenterRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_COMISION_ESPECIFICO).setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_DESDE_ESPECIFICO).setCellRenderer(tableDescEspecifico.getCenterRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_HASTA_ESPECIFICO).setCellRenderer(tableDescEspecifico.getRigthRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_ESTA_VIGENTE_ESPECIFICO).setCellRenderer(tableDescEspecifico.getCenterRender());
		tableDescEspecifico.getColumnModel().getColumn(COL_ESTA_EN_LISTA_ESPECIFICO).setCellRenderer(tableDescEspecifico.getCenterRender());

		tableDescEspecifico.setRpTableEvent(new RPTableEvent() {

			@Override
			public void doubleClick(Integer fila, Integer columna) {
				btnModificar.doClick();

			}
		});

		WebScrollPane spDescEspecifico = new WebScrollPane(tableDescEspecifico);
		tabPanel.addTab("Descuentos y Precios Especificos", spDescEspecifico);

		lblError = new WebLabel("");
		lblError.setFont(Common.getStandarFontBold());
		panelCentral.add(lblError, BorderLayout.NORTH);

		btnImpactarPrecios = new JButtonRP("Terminar Carga e Impactar precios");
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
	}

	public void setCerrarVisible(Boolean visible) {
		btnCerrar.setVisible(visible);
	}

}