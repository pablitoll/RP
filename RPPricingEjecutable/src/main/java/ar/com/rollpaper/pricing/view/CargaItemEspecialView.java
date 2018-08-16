package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.alee.extended.date.WebDateField;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebFormattedTextField;
import com.alee.laf.text.WebTextArea;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.rpcutils.FechaManagerUtil;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPImporte;
import ar.com.rp.ui.pantalla.BaseViewDialog;

public class CargaItemEspecialView extends BaseViewDialog {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public JLabel lblArticuloID;
	public WebTextArea lblNombre;
	public WebTextArea lblDescripcion;
	public componenteNumerico txtDesc1;
	public RPImporte txtDesc2;
	public WebComboBox cbMoneda;
	public RPImporte txtPrecio;
	public WebTextArea txtReferencia;
	public WebDateField dateFechaDesde;
	public WebDateField dateFechaHasta;
	public JButtonRP btnCancelar;
	public JButtonRP btnAceptar;
	public JTextField txtArticuloID;
	private JPanel panel_2;
	public JLabel lblLabelArticulo;
	public JLabel lblLabelDescipcion;
	public JLabel lblLabelMoneda;
	public JLabel lblLabelPrecio;
	private JLabel label_1;
	public RPImporte txtComision;
	public WebLabel lblEstaEnLista;

	public CargaItemEspecialView() throws Exception {
		super();

		JPanel panel = new JPanel();		
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);
		setModal(true);
		setResizable(false);
		setSize(new Dimension(600, 500));

		btnAceptar = new JButtonRP("Aceptar");
		btnAceptar.setFont(Common.getStandarFont());
		btnAceptar.setMnemonic(KeyEvent.VK_ENTER);
		panel.add(btnAceptar);

		btnCancelar = new JButtonRP("Cancelar");
		btnCancelar.setFont(Common.getStandarFont());
		btnCancelar.setMnemonic(KeyEvent.VK_ESCAPE);
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		lblLabelArticulo = new JLabel("Articulo ID:");
		lblLabelArticulo.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblLabelArticulo, gbc_lblNewLabel);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 3;
		gbc_panel_2.gridy = 0;
		panel_1.add(panel_2, gbc_panel_2);
				panel_2.setLayout(new BorderLayout(0, 0));
		
				lblArticuloID = new JLabel("lblar");
				panel_2.add(lblArticuloID, BorderLayout.WEST);
				lblArticuloID.setFont(Common.getStandarFont());
		
		txtArticuloID = new JTextField();
		panel_2.add(txtArticuloID, BorderLayout.CENTER);
		txtArticuloID.setColumns(10);
		
		lblEstaEnLista = new WebLabel("lblEstaEnLista");
		lblEstaEnLista.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblLblestaenlista = new GridBagConstraints();
		gbc_lblLblestaenlista.gridwidth = 3;
		gbc_lblLblestaenlista.insets = new Insets(0, 0, 5, 0);
		gbc_lblLblestaenlista.gridx = 1;
		gbc_lblLblestaenlista.gridy = 1;
		panel_1.add(lblEstaEnLista, gbc_lblLblestaenlista);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

		lblNombre = new WebTextArea("lbln");
		lblNombre.setFocusable(false);
		lblNombre.setBorder(UIManager.getBorder("TextField.border"));
		lblNombre.setEditable(false);
		lblNombre.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.BOTH;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 2;
		panel_1.add(lblNombre, gbc_textField_1);

		lblLabelDescipcion = new JLabel("Descripcion: ");
		lblLabelDescipcion.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		panel_1.add(lblLabelDescipcion, gbc_lblNewLabel_2);

		lblDescripcion = new WebTextArea("lblde");
		lblDescripcion.setFocusable(false);
		lblDescripcion.setWrapStyleWord(true);
		lblDescripcion.setBorder(UIManager.getBorder("TextField.border"));
		lblDescripcion.setEditable(false);
		lblDescripcion.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.BOTH;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 3;
		panel_1.add(lblDescripcion, gbc_textField_2);

		JLabel lblNewLabel_3 = new JLabel("1er Descuento %:");
		lblNewLabel_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 4;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);

		txtDesc1 = new componenteNumerico();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 4;
		panel_1.add(txtDesc1, gbc_textField_3);
		

		JLabel label = new JLabel("2do Descuento%:");
		label.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 5;
		panel_1.add(label, gbc_label);

		txtDesc2 = new RPImporte();
		txtDesc2.setFont(Common.getStandarFont());
		txtDesc2.setCantDecimales(4);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 5;
		panel_1.add(txtDesc2, gbc_textField_4);
		txtDesc2.setColumns(10);

		lblLabelMoneda = new JLabel("Moneda:");
		lblLabelMoneda.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 6;
		panel_1.add(lblLabelMoneda, gbc_label_1);

		cbMoneda = new WebComboBox();
		cbMoneda.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 6;
		panel_1.add(cbMoneda, gbc_textField_5);

		lblLabelPrecio = new JLabel("Precio:");
		lblLabelPrecio.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblLblestaenlistaPrecio = new GridBagConstraints();
		gbc_lblLblestaenlistaPrecio.anchor = GridBagConstraints.EAST;
		gbc_lblLblestaenlistaPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_lblLblestaenlistaPrecio.gridx = 1;
		gbc_lblLblestaenlistaPrecio.gridy = 7;
		panel_1.add(lblLabelPrecio, gbc_lblLblestaenlistaPrecio);

		txtPrecio = new RPImporte();
		txtPrecio.setFont(Common.getStandarFont());
		txtPrecio.setCantDecimales(4);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 3;
		gbc_textField_6.gridy = 7;
		panel_1.add(txtPrecio, gbc_textField_6);
		txtPrecio.setColumns(10);

		JLabel label_3 = new JLabel("Fecha Desde:");
		label_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 8;
		panel_1.add(label_3, gbc_label_3);

		dateFechaDesde = new WebDateField();
		dateFechaDesde.setFont(Common.getStandarFont());
		dateFechaDesde.setDateFormat(new SimpleDateFormat(FechaManagerUtil.FORMATO_FECHA));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 8;
		panel_1.add(dateFechaDesde, gbc_lblNewLabel_4);

		JLabel label_4 = new JLabel("Fecha Hasta:");
		label_4.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.EAST;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 9;
		panel_1.add(label_4, gbc_label_4);

		dateFechaHasta = new WebDateField();
		dateFechaHasta.setFont(Common.getStandarFont());
		dateFechaHasta.setDateFormat(new SimpleDateFormat(FechaManagerUtil.FORMATO_FECHA));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 9;
		panel_1.add(dateFechaHasta, gbc_lblNewLabel_5);
		
		label_1 = new JLabel("Comision %:");
		label_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_comision = new GridBagConstraints();
		gbc_label_comision.anchor = GridBagConstraints.EAST;
		gbc_label_comision.insets = new Insets(0, 0, 5, 5);
		gbc_label_comision.gridx = 1;
		gbc_label_comision.gridy = 10;
		panel_1.add(label_1, gbc_label_comision);
		
		txtComision = new RPImporte();
		txtComision.setFont(Common.getStandarFont());
		txtComision.setColumns(10);
		txtComision.setCantDecimales(4);
		GridBagConstraints gbc_txtComision = new GridBagConstraints();
		gbc_txtComision.insets = new Insets(0, 0, 5, 0);
		gbc_txtComision.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtComision.gridx = 3;
		gbc_txtComision.gridy = 10;
		panel_1.add(txtComision, gbc_txtComision);

		JLabel label_5 = new JLabel("Referencia:");
		label_5.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 11;
		panel_1.add(label_5, gbc_label_5);

		txtReferencia = new WebTextArea();
		txtReferencia.setBorder(UIManager.getBorder("TextField.border"));
		txtReferencia.setLineWrap(true);
		txtReferencia.setFont(Common.getStandarFont());
		GridBagConstraints gbc_txtReferencia1 = new GridBagConstraints();
		gbc_txtReferencia1.fill = GridBagConstraints.BOTH;
		gbc_txtReferencia1.gridx = 3;
		gbc_txtReferencia1.gridy = 11;
		panel_1.add(txtReferencia, gbc_txtReferencia1);
		txtReferencia.setColumns(10);
	}

	@Override
	public void asignarBotones() {
		asignarBotonAccion(btnAceptar, ConstantesRP.AccionesCargaItemFamilia.ACEPTAR.toString());
		asignarBotonAccion(btnCancelar, ConstantesRP.AccionesCargaItemFamilia.CANCELAR.toString());
	}

}
