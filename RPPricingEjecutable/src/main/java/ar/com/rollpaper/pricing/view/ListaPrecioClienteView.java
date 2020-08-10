package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebFormattedTextField;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseViewMVCExtendida;

public class ListaPrecioClienteView extends BaseViewMVCExtendida {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int COL_COD_ARTICULO = 1;
	private static final int COL_UNIDAD = 3;
	private static final int COL_MONEDA_ESPECIFICO = 4;
	private static final int COL_PRECIO = 5;
	public static final int COL_FECHA_VIGENCIA = 6;
	public static final int COL_DESCUENTOS = 7;
	public static final int COL_COMISION = 8;
	public static final int COL_TC = 9;

	public static final Integer COL_DESC = 2;
	public static final String COL_DES_FECHA_VIGENCIA = "Fecha Vigencia";
	public static final String COL_DES_COMISION = "% Comision";
	public static final String COL_DES_REFERENCIA = "Referencia";
	public static final String COL_DES_DESCUENTOS = "Dtos. Aplicados";
	public static final String COL_DES_TC = "Tipo Cambio";
	public RPTable tableResultado;
	public JButtonRP btnGenerarPDF;
	public JButtonRP btnExportarExcel;
	public WebFormattedTextField txtNroCliente;
	public WebComboBox cbNroLista;
	public JLabel lblNombreLista;
	public JLabel lblNombreCliente;
	public JLabel lblNombreLegal;
	public JButtonRP btnCancelar;
	private JButtonRP btnRefrescar;
	private JLabel lblTitle;
	private JPanel panel_1;
	public JCheckBox chkArticuloEspecifico;
	public JCheckBox chkArticuloLista;
	private JPanel panel_2;
	public JCheckBox chkFechaVigencia;
	public JCheckBox chkDtoAplicados;
	public JCheckBox chkReferencia;
	public JCheckBox chkComision;
	private JPanel panel_3;
	public JTextField txtBusqueda;
	private JPanel pnlFiltroCamposFiltroTexto;
	public JCheckBox chkBusquedaCodFamilia;
	public JCheckBox chkBusquedaCodProducto;
	public JCheckBox chkBusquedaDescrip;
	public JCheckBox chkTC;
	private JPanel panel_4;
	private JPanel norte;
	private JPanel sur;
	private JPanel centro;
	private JPanel titulo;
	private JPanel panel_5;
	private JLabel label;
	public JLabel lblFactor;

	public ListaPrecioClienteView() throws Exception {
		super();
		setTitle("Lista de Precios Actualizada (impactados)");

		panel_4 = new JPanel();
		getContentPane().add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BorderLayout(0, 0));

		norte = new JPanel();
		panel_4.add(norte, BorderLayout.NORTH);
		norte.setLayout(new BorderLayout(0, 0));

		titulo = new JPanel();
		norte.add(titulo, BorderLayout.NORTH);
		lblTitle = new JLabel("lblTitle");
		titulo.add(lblTitle);
		lblTitle.setText(getTitle());

		lblTitle.setFont(Common.getStandarFontBold(18));

		panel_5 = new JPanel();
		norte.add(panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		JLabel lblNroCliente = new JLabel("Nro. de Cliente:");
		GridBagConstraints gbc_lblNroCliente = new GridBagConstraints();
		gbc_lblNroCliente.anchor = GridBagConstraints.EAST;
		gbc_lblNroCliente.insets = new Insets(0, 0, 0, 5);
		gbc_lblNroCliente.gridx = 0;
		gbc_lblNroCliente.gridy = 0;
		panel_5.add(lblNroCliente, gbc_lblNroCliente);
		lblNroCliente.setFont(Common.getStandarFont());

		txtNroCliente = new WebFormattedTextField();
		GridBagConstraints gbc_txtNroCliente = new GridBagConstraints();
		gbc_txtNroCliente.insets = new Insets(0, 0, 0, 5);
		gbc_txtNroCliente.gridx = 1;
		gbc_txtNroCliente.gridy = 0;
		panel_5.add(txtNroCliente, gbc_txtNroCliente);
		txtNroCliente.setInputPromptFont(Common.getStandarFont());
		txtNroCliente.setInputPrompt("Ingrese nro. Cliente");
		txtNroCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNroCliente.setFont(Common.getStandarFont());
		txtNroCliente.setColumns(10);

		JLabel label_1 = new JLabel("Nombre:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 0;
		panel_5.add(label_1, gbc_label_1);
		label_1.setFont(Common.getStandarFont());

		lblNombreCliente = new JLabel("xxxxxxxxxxxxxxxxxxxx");
		GridBagConstraints gbc_lblNombreCliente = new GridBagConstraints();
		gbc_lblNombreCliente.anchor = GridBagConstraints.WEST;
		gbc_lblNombreCliente.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreCliente.gridx = 3;
		gbc_lblNombreCliente.gridy = 0;
		lblNombreCliente.setFont(Common.getStandarFontBold());
		panel_5.add(lblNombreCliente, gbc_lblNombreCliente);

		JLabel label_3 = new JLabel("Nombre Legal:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 0, 5);
		gbc_label_3.gridx = 4;
		gbc_label_3.gridy = 0;
		panel_5.add(label_3, gbc_label_3);
		label_3.setFont(Common.getStandarFont());

		lblNombreLegal = new JLabel("lblNombreLegal");
		GridBagConstraints gbc_lblNombreLegal = new GridBagConstraints();
		gbc_lblNombreLegal.anchor = GridBagConstraints.WEST;
		gbc_lblNombreLegal.gridx = 5;
		gbc_lblNombreLegal.gridy = 0;
		panel_5.add(lblNombreLegal, gbc_lblNombreLegal);
		lblNombreLegal.setFont(Common.getStandarFontBold());

		centro = new JPanel();
		panel_4.add(centro, BorderLayout.CENTER);
		GridBagLayout gbl_centro = new GridBagLayout();
		gbl_centro.columnWidths = new int[] { 150, 150, 0, 0, 0, 50, 0 };
		gbl_centro.rowHeights = new int[] { 0, 0 };
		gbl_centro.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_centro.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		centro.setLayout(gbl_centro);

		JLabel label_5 = new JLabel("Nro de Lista de Precio");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 0;
		centro.add(label_5, gbc_label_5);
		label_5.setFont(Common.getStandarFont());

		cbNroLista = new WebComboBox();
		GridBagConstraints gbc_cbNroLista = new GridBagConstraints();
		gbc_cbNroLista.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbNroLista.insets = new Insets(0, 0, 0, 5);
		gbc_cbNroLista.gridx = 1;
		gbc_cbNroLista.gridy = 0;
		centro.add(cbNroLista, gbc_cbNroLista);
		cbNroLista.setFont(Common.getStandarFont());

		JLabel label_6 = new JLabel("Nombre Lista ");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 0, 5);
		gbc_label_6.gridx = 2;
		gbc_label_6.gridy = 0;
		centro.add(label_6, gbc_label_6);
		label_6.setFont(Common.getStandarFont());

		lblNombreLista = new JLabel("lblNombreLista");
		GridBagConstraints gbc_lblNombreLista = new GridBagConstraints();
		gbc_lblNombreLista.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreLista.anchor = GridBagConstraints.WEST;
		gbc_lblNombreLista.gridx = 3;
		gbc_lblNombreLista.gridy = 0;
		centro.add(lblNombreLista, gbc_lblNombreLista);
		lblNombreLista.setFont(Common.getStandarFontBold());

		label = new JLabel("Multiplicador:");
		label.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 0;
		centro.add(label, gbc_label);

		lblFactor = new JLabel("factor:");
		lblFactor.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.gridx = 5;
		gbc_label_2.gridy = 0;
		centro.add(lblFactor, gbc_label_2);

		sur = new JPanel();
		panel_4.add(sur, BorderLayout.SOUTH);
		GridBagLayout gbl_sur = new GridBagLayout();
		gbl_sur.columnWidths = new int[] { 250, 0, 0, 0, 0 };
		gbl_sur.rowHeights = new int[] { 0, 0 };
		gbl_sur.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_sur.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		sur.setLayout(gbl_sur);

		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		sur.add(panel_3, gbc_panel_3);
		panel_3.setBorder(
				new TitledBorder(null, "Filtro por Texto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setLayout(new BorderLayout(0, 0));

		txtBusqueda = new JTextField();
		panel_3.add(txtBusqueda, BorderLayout.CENTER);
		txtBusqueda.setColumns(10);

		pnlFiltroCamposFiltroTexto = new JPanel();
		panel_3.add(pnlFiltroCamposFiltroTexto, BorderLayout.EAST);
		pnlFiltroCamposFiltroTexto.setBorder(null);
		pnlFiltroCamposFiltroTexto.setLayout(new GridLayout(3, 1, 0, 0));

		chkBusquedaCodFamilia = new JCheckBox("Fam.");
		chkBusquedaCodFamilia.setFont(Common.getStandarFont());
		pnlFiltroCamposFiltroTexto.add(chkBusquedaCodFamilia);

		chkBusquedaCodProducto = new JCheckBox("Prod.");
		chkBusquedaCodProducto.setFont(Common.getStandarFont());
		pnlFiltroCamposFiltroTexto.add(chkBusquedaCodProducto);

		chkBusquedaDescrip = new JCheckBox("Descrip.");
		chkBusquedaDescrip.setFont(Common.getStandarFont());
		pnlFiltroCamposFiltroTexto.add(chkBusquedaDescrip);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		sur.add(panel_1, gbc_panel_1);
		panel_1.setFont(Common.getStandarFont());
		panel_1.setBorder(new TitledBorder(null, "Filtros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		chkArticuloLista = new JCheckBox("Articulos de Lista");
		chkArticuloLista.setFont(Common.getStandarFont());

		panel_1.add(chkArticuloLista);

		chkArticuloEspecifico = new JCheckBox("Articulos Específicos");
		chkArticuloEspecifico.setFont(Common.getStandarFont());
		panel_1.add(chkArticuloEspecifico);

		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 3;
		gbc_panel_2.gridy = 0;
		sur.add(panel_2, gbc_panel_2);
		panel_2.setFont(Common.getStandarFont());
		panel_2.setBorder(
				new TitledBorder(null, "Campos a Incluir", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));

		chkFechaVigencia = new JCheckBox("Fecha Vigencia");

		chkFechaVigencia.setFont(Common.getStandarFont());
		panel_2.add(chkFechaVigencia);

		chkComision = new JCheckBox("% Comision");
		chkComision.setFont(Common.getStandarFont());
		panel_2.add(chkComision);

		chkReferencia = new JCheckBox("Referencia");
		chkReferencia.setFont(Common.getStandarFont());
		panel_2.add(chkReferencia);

		chkDtoAplicados = new JCheckBox("Descuentos Aplicados");
		chkDtoAplicados.setFont(Common.getStandarFont());
		panel_2.add(chkDtoAplicados);

		chkTC = new JCheckBox("Tipo Cambio Limite");
		chkTC.setFont(Common.getStandarFont());
		panel_2.add(chkTC);

		String[] headerTabla = { "Codigo Familia", "Codigo Articulo", "Descripcion", "Unidad", "Moneda", "Precio Venta",
				COL_DES_FECHA_VIGENCIA, COL_DES_DESCUENTOS, COL_DES_COMISION, COL_DES_TC, COL_DES_REFERENCIA };
		String[][] dataTabla = { {} };

		tableResultado = new RPTable();
		tableResultado.setModel(new DefaultTableModel(dataTabla, headerTabla));
		tableResultado.setRowHeight(30);
		tableResultado.setEditable(false);
		tableResultado.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableResultado.setRowSelectionAllowed(true);

		tableResultado.getColumnModel().getColumn(COL_UNIDAD).setCellRenderer(tableResultado.getCenterRender());
		tableResultado.getColumnModel().getColumn(COL_MONEDA_ESPECIFICO)
				.setCellRenderer(tableResultado.getCenterRender());
		tableResultado.getColumnModel().getColumn(COL_PRECIO).setCellRenderer(tableResultado.getRigthRender());

		tableResultado.getColumnModel().getColumn(COL_FECHA_VIGENCIA).setCellRenderer(tableResultado.getCenterRender());
		tableResultado.getColumnModel().getColumn(COL_DESCUENTOS).setCellRenderer(tableResultado.getCenterRender());
		tableResultado.getColumnModel().getColumn(COL_COMISION).setCellRenderer(tableResultado.getRigthRender());
		tableResultado.getColumnModel().getColumn(COL_TC).setCellRenderer(tableResultado.getRigthRender());
		tableResultado.getColumnModel().getColumn(COL_FECHA_VIGENCIA).setCellRenderer(tableResultado.getCenterRender());

		WebScrollPane webScrollPane = new WebScrollPane(tableResultado);
		getContentPane().add(webScrollPane, BorderLayout.CENTER);

		btnRefrescar = new JButtonRP("Recargar");
		btnRefrescar.setIcon(CommonUtils.loadIcon(ConstantesRP.IMG_RECARGAR, 15, 15));
		btnRefrescar.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnRefrescar);

		btnCancelar = new JButtonRP("Hacer otra Busqueda");
		btnCancelar.setIcon(Common.loadIconMenu(ConstantesRP.IMG_RETORNO));
		btnCancelar.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnCancelar);

		btnGenerarPDF = new JButtonRP("Precios Vigente (PDF)");
		btnGenerarPDF.setIcon(CommonUtils.loadIcon(ConstantesRP.IMG_PDF, 15, 15));
		btnGenerarPDF.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnGenerarPDF);

		btnExportarExcel = new JButtonRP("Exportar a Excel");
		btnExportarExcel.setIcon(CommonUtils.loadIcon(ConstantesRP.IMG_EXCEL, 15, 15));
		btnExportarExcel.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnExportarExcel);
	}

	@Override
	public void asignarBotonesPantExtendida() {
		asignarBotonAccion(btnGenerarPDF, ConstantesRP.PantListaPrecio.GENERAR_PDF.toString());
		asignarBotonAccion(btnExportarExcel, ConstantesRP.PantListaPrecio.GENERAR_EXCEL.toString());
		asignarBotonAccion(btnCancelar, ConstantesRP.PantListaPrecio.CANCELAR.toString());
		asignarBotonAccion(btnRefrescar, ConstantesRP.PantListaPrecio.RECARGAR.toString());
	}

}
