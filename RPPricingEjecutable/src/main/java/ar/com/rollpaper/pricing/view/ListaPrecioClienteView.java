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
import javax.swing.border.EmptyBorder;
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

	public static final Integer COL_DESC = 2;
	public static final String COL_DES_FECHA_VIGENCIA = "Fecha Vigencia";
	public static final String COL_DES_COMISION = "% Comision";
	public static final String COL_DES_REFERENCIA = "Referencia";
	public static final String COL_DES_DESCUENTOS = "Dtos. Aplicados";
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

	public ListaPrecioClienteView() throws Exception {
		super();
		lblTitle = new JLabel("lblTitle");
		setTitle("Lista de Precios Actualizada (impactados)");
		lblTitle.setText(getTitle());

		JPanel panel = new JPanel();
		panel.setFocusable(false);
		panel.setBorder(new EmptyBorder(5, 5, 5, 0));
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 50, 100, 46, 46, 100, 50, 50, 76, 100, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblTitle.setFont(Common.getStandarFontBold(18));
		GridBagConstraints gbc_label_22 = new GridBagConstraints();
		gbc_label_22.gridwidth = 11;
		gbc_label_22.insets = new Insets(0, 0, 5, 0);
		gbc_label_22.gridx = 0;
		gbc_label_22.gridy = 0;
		panel.add(lblTitle, gbc_label_22);

		JLabel lblNroCliente = new JLabel("Nro. de Cliente:");
		lblNroCliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNroCliente, gbc_lblNewLabel);

		txtNroCliente = new WebFormattedTextField();
		txtNroCliente.setInputPromptFont(Common.getStandarFont());
		txtNroCliente.setInputPrompt("Ingrese nro. Cliente");
		txtNroCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNroCliente.setFont(Common.getStandarFont());
		txtNroCliente.setColumns(10);
		GridBagConstraints gbc_webFormattedTextField = new GridBagConstraints();
		gbc_webFormattedTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_webFormattedTextField.anchor = GridBagConstraints.NORTH;
		gbc_webFormattedTextField.insets = new Insets(0, 0, 5, 5);
		gbc_webFormattedTextField.gridx = 1;
		gbc_webFormattedTextField.gridy = 1;
		panel.add(txtNroCliente, gbc_webFormattedTextField);

		JLabel label_1 = new JLabel("Nombre:");
		label_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 1;
		panel.add(label_1, gbc_label_1);

		lblNombreCliente = new JLabel("xxxxxxxxxxxxxxxxxxxx");
		lblNombreCliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 3;
		gbc_label_2.gridy = 1;
		panel.add(lblNombreCliente, gbc_label_2);

		JLabel label_3 = new JLabel("Nombre Legal:");
		label_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 4;
		gbc_label_3.gridy = 1;
		panel.add(label_3, gbc_label_3);

		lblNombreLegal = new JLabel("lblNombreLegal");
		lblNombreLegal.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 5;
		gbc_label_4.gridy = 1;
		panel.add(lblNombreLegal, gbc_label_4);

		panel_1 = new JPanel();
		panel_1.setFont(Common.getStandarFont());
		panel_1.setBorder(new TitledBorder(null, "Filtros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 3;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 8;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		chkArticuloLista = new JCheckBox("Articulos de Lista");
		chkArticuloLista.setFont(Common.getStandarFont());

		panel_1.add(chkArticuloLista);

		chkArticuloEspecifico = new JCheckBox("Articulos Específicos");
		chkArticuloEspecifico.setFont(Common.getStandarFont());
		panel_1.add(chkArticuloEspecifico);

		panel_2 = new JPanel();
		panel_2.setFont(Common.getStandarFont());
		panel_2.setBorder(
				new TitledBorder(null, "Campos a Incluir", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridheight = 3;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 9;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
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

		JLabel label_5 = new JLabel("Nro de Lista de Precio");
		label_5.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 2;
		panel.add(label_5, gbc_label_5);

		cbNroLista = new WebComboBox();
		cbNroLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_webComboBox = new GridBagConstraints();
		gbc_webComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_webComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_webComboBox.gridx = 1;
		gbc_webComboBox.gridy = 2;
		panel.add(cbNroLista, gbc_webComboBox);

		JLabel label_6 = new JLabel("Nombre Lista ");
		label_6.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.EAST;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 2;
		gbc_label_6.gridy = 2;
		panel.add(label_6, gbc_label_6);

		lblNombreLista = new JLabel("lblNombreLista");
		lblNombreLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 3;
		gbc_label_7.gridy = 2;
		panel.add(lblNombreLista, gbc_label_7);

		panel_3 = new JPanel();
		panel_3.setBorder(
				new TitledBorder(null, "Filtro por Texto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 5;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		panel.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		txtBusqueda = new JTextField();
		panel_3.add(txtBusqueda);
		txtBusqueda.setColumns(10);

		pnlFiltroCamposFiltroTexto = new JPanel();
		pnlFiltroCamposFiltroTexto.setBorder(
				new TitledBorder(null, "Campos Filtro por Texto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 5;
		gbc_panel_4.gridy = 3;
		panel.add(pnlFiltroCamposFiltroTexto, gbc_panel_4);

		chkBusquedaCodFamilia = new JCheckBox("Fam.");
		chkBusquedaCodFamilia.setFont(Common.getStandarFont());
		pnlFiltroCamposFiltroTexto.add(chkBusquedaCodFamilia);

		chkBusquedaCodProducto = new JCheckBox("Prod.");
		chkBusquedaCodProducto.setFont(Common.getStandarFont());
		pnlFiltroCamposFiltroTexto.add(chkBusquedaCodProducto);

		chkBusquedaDescrip = new JCheckBox("Descrip.");
		chkBusquedaDescrip.setFont(Common.getStandarFont());
		pnlFiltroCamposFiltroTexto.add(chkBusquedaDescrip);

		String[] headerTabla = { "Codigo Familia", "Codigo Articulo", "Descripcion", "Unidad", "Moneda", "Precio Venta",
				COL_DES_FECHA_VIGENCIA, COL_DES_DESCUENTOS, COL_DES_COMISION, COL_DES_REFERENCIA };
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
