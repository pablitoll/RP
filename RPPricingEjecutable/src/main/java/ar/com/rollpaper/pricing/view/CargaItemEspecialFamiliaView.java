package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.extended.date.WebDateField;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.interfaces.RPTableEvent;
import ar.com.rp.ui.pantalla.BaseViewDialog;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JButton;

public class CargaItemEspecialFamiliaView extends BaseViewDialog {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static final int COL_EN_LISTA = 0;
	public static final int COL_CODIGO = 1;
	private static final int COL_NOMBRE = 2;
	private static final int COL_DESCRIP = 3;
	public componenteNumerico txtDesc1;
	public componenteNumerico txtDesc2;
	public WebComboBox cbMoneda;
	public componenteNumerico txtPrecio;
	public WebTextArea txtReferencia;
	public WebDateField dateFechaDesde;
	public WebDateField dateFechaHasta;
	public JButtonRP btnCancelar;
	public JButtonRP btnAceptar;
	public JLabel lblLabelMoneda;
	public JLabel lblLabelPrecio;
	private JLabel label_1;
	public componenteNumerico txtComision;
	public JLabel lblDesc1;
	public JLabel lblDesc2;
	private WebScrollPane scrollPane;
	public RPTable tableFalimia;
	public JPanel pnlID;
	public JTextField txtArticuloID;
	private WebTextArea txtDescripcion;
	private JLabel lblDescripcion;
	public WebTextArea txtNombre;
	private JLabel lblNombre;
	private WebLabel lblEstaEnLista;
	private JButton btnEliminar;

	public CargaItemEspecialFamiliaView() throws Exception {
		super();

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);
		setModal(true);
		setResizable(false);
		setSize(new Dimension(600, 585));
		setLocationRelativeTo(null);

		btnAceptar = new JButtonRP("Aceptar");
		btnAceptar.setIcon(Common.loadIconMenu(ConstantesRP.IMG_RETORNO));
		btnAceptar.setFont(Common.getStandarFont());
		panel.add(btnAceptar);

		btnCancelar = new JButtonRP("Cancelar");
		btnCancelar.setIcon(Common.loadIconMenu("com/alee/laf/filechooser/icons/remove.png"));
		btnCancelar.setFont(Common.getStandarFont());
		btnCancelar.setMnemonic(KeyEvent.VK_ESCAPE);
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 268, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 65, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		String[] headerDescFamilia = { "En Lista", "Codigo", "Nombre", "Descripcion" };
		String[][] dataDesFamilia = { {} };

		tableFalimia = new RPTable();
		tableFalimia.setModel(new DefaultTableModel(dataDesFamilia, headerDescFamilia));
		tableFalimia.setRowHeight(30);
		tableFalimia.setEditable(false);

		tableFalimia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableFalimia.getColumnModel().getColumn(COL_EN_LISTA).setCellRenderer(tableFalimia.getCenterRender());
		tableFalimia.getColumnModel().getColumn(COL_CODIGO).setCellRenderer(tableFalimia.getLeftRender());
		tableFalimia.getColumnModel().getColumn(COL_NOMBRE).setCellRenderer(tableFalimia.getLeftRender());
		tableFalimia.getColumnModel().getColumn(COL_DESCRIP).setCellRenderer(tableFalimia.getLeftRender());
		tableFalimia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableFalimia.setRpTableEvent(new RPTableEvent() {

			@Override
			public void doubleClick(Integer fila, Integer columna) {
				btnEliminar.doClick();
			}
		});

		pnlID = new JPanel();
		pnlID.setFont(Common.getStandarFont());
		pnlID.setBorder(new TitledBorder(null, "ID:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlID = new GridBagConstraints();
		gbc_pnlID.gridwidth = 5;
		gbc_pnlID.insets = new Insets(0, 0, 5, 0);
		gbc_pnlID.fill = GridBagConstraints.BOTH;
		gbc_pnlID.gridx = 0;
		gbc_pnlID.gridy = 0;
		panel_1.add(pnlID, gbc_pnlID);
		pnlID.setLayout(new BorderLayout(0, 0));

		txtArticuloID = new JTextField();
		txtArticuloID.setColumns(10);
		pnlID.add(txtArticuloID);

		scrollPane = new WebScrollPane(tableFalimia);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);

		btnEliminar = new JButton("");
		btnEliminar.setIcon(CommonUtils.loadIcon("com/alee/managers/notification/icons/types/minus.png", 15, 15));
		btnEliminar.setFont(Common.getStandarFont());
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 1;
		panel_1.add(btnEliminar, gbc_btnNewButton);

		lblEstaEnLista = new WebLabel("lblEstaEnLista");
		lblEstaEnLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblEstaEnLista = new GridBagConstraints();
		gbc_lblEstaEnLista.gridwidth = 2;
		gbc_lblEstaEnLista.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEstaEnLista.insets = new Insets(0, 0, 5, 0);
		gbc_lblEstaEnLista.gridx = 3;
		gbc_lblEstaEnLista.gridy = 2;
		panel_1.add(lblEstaEnLista, gbc_lblEstaEnLista);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 3;
		panel_1.add(lblNombre, gbc_lblNombre);

		txtNombre = new WebTextArea("");
		txtNombre.setFont(Common.getStandarFont());
		txtNombre.setFocusable(false);
		txtNombre.setEditable(false);
		txtNombre.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.gridwidth = 2;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 3;
		gbc_txtNombre.gridy = 3;
		panel_1.add(txtNombre, gbc_txtNombre);

		lblDescripcion = new JLabel("Descripcion: ");
		lblDescripcion.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 4;
		panel_1.add(lblDescripcion, gbc_lblDescripcion);

		txtDescripcion = new WebTextArea("");
		txtDescripcion.setWrapStyleWord(true);
		txtDescripcion.setFont(Common.getStandarFont());
		txtDescripcion.setFocusable(false);
		txtDescripcion.setEditable(false);
		txtDescripcion.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();
		gbc_txtDescripcion.gridwidth = 2;
		gbc_txtDescripcion.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescripcion.gridx = 3;
		gbc_txtDescripcion.gridy = 4;
		panel_1.add(txtDescripcion, gbc_txtDescripcion);

		lblDesc1 = new JLabel("1er Descuento %:");
		lblDesc1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 5;
		panel_1.add(lblDesc1, gbc_lblNewLabel_3);

		txtDesc1 = new componenteNumerico();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.gridwidth = 2;
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 5;
		panel_1.add(txtDesc1, gbc_textField_3);

		lblDesc2 = new JLabel("2do Descuento%:");
		lblDesc2.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblDesc2 = new GridBagConstraints();
		gbc_lblDesc2.anchor = GridBagConstraints.EAST;
		gbc_lblDesc2.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesc2.gridx = 1;
		gbc_lblDesc2.gridy = 6;
		panel_1.add(lblDesc2, gbc_lblDesc2);

		txtDesc2 = new componenteNumerico();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.gridwidth = 2;
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 6;
		panel_1.add(txtDesc2, gbc_textField_4);
		txtDesc2.setColumns(10);

		lblLabelMoneda = new JLabel("Moneda:");
		lblLabelMoneda.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 7;
		panel_1.add(lblLabelMoneda, gbc_label_1);

		cbMoneda = new WebComboBox();
		cbMoneda.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.gridwidth = 2;
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 7;
		panel_1.add(cbMoneda, gbc_textField_5);

		lblLabelPrecio = new JLabel("Precio:");
		lblLabelPrecio.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblLblestaenlistaPrecio = new GridBagConstraints();
		gbc_lblLblestaenlistaPrecio.anchor = GridBagConstraints.EAST;
		gbc_lblLblestaenlistaPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_lblLblestaenlistaPrecio.gridx = 1;
		gbc_lblLblestaenlistaPrecio.gridy = 8;
		panel_1.add(lblLabelPrecio, gbc_lblLblestaenlistaPrecio);

		txtPrecio = new componenteNumerico();
		txtPrecio.setCantEnteros(8);
		txtPrecio.setCantDecimales(4);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.gridwidth = 2;
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 3;
		gbc_textField_6.gridy = 8;
		panel_1.add(txtPrecio, gbc_textField_6);
		txtPrecio.setColumns(10);

		JLabel label_3 = new JLabel("Fecha Desde:");
		label_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 9;
		panel_1.add(label_3, gbc_label_3);

		dateFechaDesde = new WebDateField();
		dateFechaDesde.setFont(Common.getStandarFont());
		dateFechaDesde.setDateFormat(new SimpleDateFormat(FechaManagerUtil.FORMATO_FECHA));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.gridwidth = 2;
		gbc_lblNewLabel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 9;
		panel_1.add(dateFechaDesde, gbc_lblNewLabel_4);

		JLabel label_4 = new JLabel("Fecha Hasta:");
		label_4.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.EAST;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 10;
		panel_1.add(label_4, gbc_label_4);

		dateFechaHasta = new WebDateField();
		dateFechaHasta.setFont(Common.getStandarFont());
		dateFechaHasta.setDateFormat(new SimpleDateFormat(FechaManagerUtil.FORMATO_FECHA));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.gridwidth = 2;
		gbc_lblNewLabel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 10;
		panel_1.add(dateFechaHasta, gbc_lblNewLabel_5);

		label_1 = new JLabel("Comision %:");
		label_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_comision = new GridBagConstraints();
		gbc_label_comision.anchor = GridBagConstraints.EAST;
		gbc_label_comision.insets = new Insets(0, 0, 5, 5);
		gbc_label_comision.gridx = 1;
		gbc_label_comision.gridy = 11;
		panel_1.add(label_1, gbc_label_comision);

		txtComision = new componenteNumerico();
		GridBagConstraints gbc_txtComision = new GridBagConstraints();
		gbc_txtComision.gridwidth = 2;
		gbc_txtComision.insets = new Insets(0, 0, 5, 0);
		gbc_txtComision.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtComision.gridx = 3;
		gbc_txtComision.gridy = 11;
		panel_1.add(txtComision, gbc_txtComision);

		JLabel label_5 = new JLabel("Referencia:");
		label_5.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 12;
		panel_1.add(label_5, gbc_label_5);

		txtReferencia = new WebTextArea();
		txtReferencia.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		txtReferencia.setLineWrap(true);
		txtReferencia.setFont(Common.getStandarFont());
		GridBagConstraints gbc_txtReferencia1 = new GridBagConstraints();
		gbc_txtReferencia1.gridwidth = 2;
		gbc_txtReferencia1.fill = GridBagConstraints.BOTH;
		gbc_txtReferencia1.gridx = 3;
		gbc_txtReferencia1.gridy = 12;
		panel_1.add(txtReferencia, gbc_txtReferencia1);
		txtReferencia.setColumns(10);
		panel_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { pnlID, txtArticuloID,
				lblEstaEnLista, lblNombre, txtNombre, lblDescripcion, txtDescripcion, lblDesc1, txtDesc1, lblDesc2,
				txtDesc2, lblLabelMoneda, cbMoneda, lblLabelPrecio, txtPrecio, label_3, dateFechaDesde,
				dateFechaDesde.getTrailingComponent(), label_4, dateFechaHasta, dateFechaHasta.getTrailingComponent(),
				label_1, txtComision, label_5, txtReferencia }));

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				btnCancelar.doClick();
			}
		});
	}

	@Override
	public void asignarBotones() {
		asignarBotonAccion(btnAceptar, ConstantesRP.AccionesCargaItemFamilia.ACEPTAR.toString());
		asignarBotonAccion(btnCancelar, ConstantesRP.AccionesCargaItemFamilia.CANCELAR.toString());
		asignarBotonAccion(btnEliminar, ConstantesRP.AccionesCargaItemFamilia.ELIMINAR_FAMILIA.toString());
	}

	public void setModoFamilia(boolean activar) {
		txtPrecio.setVisible(!activar);
		cbMoneda.setVisible(!activar);
		lblDescripcion.setVisible(!activar);
		txtDescripcion.setVisible(!activar);
		lblLabelPrecio.setVisible(!activar);
		txtPrecio.setVisible(!activar);
		lblLabelMoneda.setVisible(!activar);
		cbMoneda.setVisible(!activar);
		lblEstaEnLista.setVisible(!activar);

		tableFalimia.getColumnModel().removeColumn(tableFalimia.getColumnModel().getColumn(COL_DESCRIP));
		tableFalimia.getColumnModel().removeColumn(tableFalimia.getColumnModel().getColumn(COL_EN_LISTA));
	}

	public void setModeEdicion(boolean activar) {
		txtArticuloID.setEditable(!activar);
		pnlID.setVisible(!activar);

		lblDescripcion.setVisible(activar && cbMoneda.isVisible()); // la moneda esta visible solo cuando es producto
		txtDescripcion.setVisible(activar && cbMoneda.isVisible());

		lblNombre.setVisible(activar && cbMoneda.isVisible());
		txtNombre.setVisible(activar && cbMoneda.isVisible());

	}

}
