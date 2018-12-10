package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebFormattedTextField;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.ui.Main;
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

	public ListaPrecioClienteView() throws Exception {
		super();
		setTitle("Lista de Articulos Customizados por Cliente");

		JPanel panel = new JPanel();
		panel.setFocusable(false);
		panel.setBorder(new EmptyBorder(5, 5, 5, 0));
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 76, 100, 50, 46, 46, 50, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label = new JLabel("Nro. de Cliente:");
		label.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);

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
		gbc_webFormattedTextField.gridy = 0;
		panel.add(txtNroCliente, gbc_webFormattedTextField);

		JLabel label_1 = new JLabel("Nombre:");
		label_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 3;
		gbc_label_1.gridy = 0;
		panel.add(label_1, gbc_label_1);

		lblNombreCliente = new JLabel("xxxxxxxxxxxxxxxxxxxx");
		lblNombreCliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 4;
		gbc_label_2.gridy = 0;
		panel.add(lblNombreCliente, gbc_label_2);

		JLabel label_3 = new JLabel("Nombre Legal:");
		label_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 5;
		gbc_label_3.gridy = 0;
		panel.add(label_3, gbc_label_3);

		lblNombreLegal = new JLabel("lblNombreLegal");
		lblNombreLegal.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 6;
		gbc_label_4.gridy = 0;
		panel.add(lblNombreLegal, gbc_label_4);

		JLabel label_5 = new JLabel("Nro de Lista de Precio");
		label_5.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 1;
		panel.add(label_5, gbc_label_5);

		cbNroLista = new WebComboBox();
		cbNroLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_webComboBox = new GridBagConstraints();
		gbc_webComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_webComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_webComboBox.gridx = 1;
		gbc_webComboBox.gridy = 1;
		panel.add(cbNroLista, gbc_webComboBox);

		JLabel label_6 = new JLabel("Nombre Lista ");
		label_6.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 0, 5);
		gbc_label_6.gridx = 3;
		gbc_label_6.gridy = 1;
		panel.add(label_6, gbc_label_6);

		lblNombreLista = new JLabel("lblNombreLista");
		lblNombreLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.anchor = GridBagConstraints.WEST;
		gbc_label_7.insets = new Insets(0, 0, 0, 5);
		gbc_label_7.gridx = 4;
		gbc_label_7.gridy = 1;
		panel.add(lblNombreLista, gbc_label_7);

		String[] headerTabla = { "Codigo Familia", "Codigo Articulo", "Descripcion", "Unidad", "Moneda", "Precio Venta" };
		String[][] dataTabla = { {} };

		tableResultado = new RPTable();
		tableResultado.setModel(new DefaultTableModel(dataTabla, headerTabla));
		tableResultado.setRowHeight(30);
		tableResultado.setEditable(false);
		tableResultado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableResultado.getColumnModel().getColumn(COL_UNIDAD).setCellRenderer(tableResultado.getCenterRender());
		tableResultado.getColumnModel().getColumn(COL_MONEDA_ESPECIFICO).setCellRenderer(tableResultado.getCenterRender());
		tableResultado.getColumnModel().getColumn(COL_PRECIO).setCellRenderer(tableResultado.getRigthRender());

		WebScrollPane webScrollPane = new WebScrollPane(tableResultado);
		getContentPane().add(webScrollPane, BorderLayout.CENTER);

		btnRefrescar = new JButtonRP("Recargar");
		btnRefrescar.setIcon(CommonUtils.loadIcon(CargaClienteEsclavoView.class.getResource(ConstantesRP.IMG_RECARGAR), 15, 15));
		btnRefrescar.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnRefrescar);

		btnCancelar = new JButtonRP("Hacer otra Busqueda");
		btnCancelar.setIcon(Common.loadIconMenu(Main.class.getResource(ConstantesRP.IMG_RETORNO)));
		btnCancelar.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnCancelar);

		btnGenerarPDF = new JButtonRP("Precios Vigente (PDF)");
		btnGenerarPDF.setIcon(CommonUtils.loadIcon(CargaClienteEsclavoView.class.getResource(ConstantesRP.IMG_PDF), 15, 15));
		btnGenerarPDF.setFont(Common.getStandarFont());
		pnlInferiorBotones.add(btnGenerarPDF);

		btnExportarExcel = new JButtonRP("Exportar a Excel");
		btnExportarExcel.setIcon(CommonUtils.loadIcon(CargaClienteEsclavoView.class.getResource(ConstantesRP.IMG_EXCEL), 15, 15));
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
