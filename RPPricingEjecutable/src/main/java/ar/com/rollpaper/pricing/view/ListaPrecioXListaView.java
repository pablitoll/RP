package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.scroll.WebScrollPane;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseViewMVCExtendida;

public class ListaPrecioXListaView extends BaseViewMVCExtendida {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int COL_COD_ARTICULO = 0;
	public RPTable tableResultado;
	public JButtonRP btnGenerarPDF;
	public JButtonRP btnExportarExcel;
	public WebComboBox cbNroLista;
	public JLabel lblNombreLista;

	public ListaPrecioXListaView() throws Exception {
		super();
		setTitle("Lista de Articulos Customizados por Cliente");

		JPanel panel = new JPanel();
		panel.setFocusable(false);
		panel.setBorder(new EmptyBorder(5, 5, 5, 0));
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 76, 100, 50, 46, 46, 50, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label_5 = new JLabel("Nro de Lista de Precio");
		label_5.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 0;
		panel.add(label_5, gbc_label_5);

		cbNroLista = new WebComboBox();
		cbNroLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_webComboBox = new GridBagConstraints();
		gbc_webComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_webComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_webComboBox.gridx = 1;
		gbc_webComboBox.gridy = 0;
		panel.add(cbNroLista, gbc_webComboBox);

		JLabel label_6 = new JLabel("Nombre Lista ");
		label_6.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 0, 5);
		gbc_label_6.gridx = 3;
		gbc_label_6.gridy = 0;
		panel.add(label_6, gbc_label_6);

		lblNombreLista = new JLabel("lblNombreLista");
		lblNombreLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.anchor = GridBagConstraints.WEST;
		gbc_label_7.insets = new Insets(0, 0, 0, 5);
		gbc_label_7.gridx = 4;
		gbc_label_7.gridy = 0;
		panel.add(lblNombreLista, gbc_label_7);

		String[] headerTabla = { "Codigo Articulo", "Descripcion", "Unidad", "Moneda", "Precio Venta" };
		String[][] dataTabla = { {} };

		tableResultado = new RPTable();
		tableResultado.setModel(new DefaultTableModel(dataTabla, headerTabla));
		tableResultado.setRowHeight(30);
		tableResultado.setEditable(false);
		tableResultado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		WebScrollPane webScrollPane = new WebScrollPane(tableResultado);
		getContentPane().add(webScrollPane, BorderLayout.CENTER);

		btnGenerarPDF = new JButtonRP("Exportar a PDF");
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
		asignarBotonAccion(btnGenerarPDF, ConstantesRP.PantListaPrecioXLista.GENERAR_PDF.toString());
		asignarBotonAccion(btnExportarExcel, ConstantesRP.PantListaPrecioXLista.GENERAR_EXCEL.toString());
	}

}