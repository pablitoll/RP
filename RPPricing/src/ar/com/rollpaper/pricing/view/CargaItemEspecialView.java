package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.alee.extended.date.WebDateField;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.text.WebFormattedTextField;

import ar.com.rollpaper.pricing.business.ConstantesRP;
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
	public JLabel lblNombre;
	public JLabel lblDescripcion;
	public RPImporte txtDesc1;
	public RPImporte txtDesc2;
	public WebComboBox cbMoneda;
	public RPImporte txtPrecio;
	public JTextField txtReferencia;
	public WebDateField dateFechaDesde;
	public WebDateField dateFechaHasta;
	public JButtonRP btnCancelar;
	public JButtonRP btnAceptar;
	public JTextField txtArticuloID;
	private JPanel panel_2;

	public CargaItemEspecialView() throws Exception {
		super();

		JPanel panel = new JPanel();		
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);
		setModal(true);
		setSize(new Dimension(600, 400));

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
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblNewLabel = new JLabel("Articulo ID:");
		lblNewLabel.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
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

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

		lblNombre = new JLabel("lbln");
		lblNombre.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 1;
		panel_1.add(lblNombre, gbc_textField_1);

		JLabel lblNewLabel_2 = new JLabel("Descripcion: ");
		lblNewLabel_2.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 2;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);

		lblDescripcion = new JLabel("lblde");
		lblDescripcion.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 2;
		panel_1.add(lblDescripcion, gbc_textField_2);

		JLabel lblNewLabel_3 = new JLabel("1er Descuento %:");
		lblNewLabel_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 3;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);

		txtDesc1 = new RPImporte();
		txtDesc1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 3;
		panel_1.add(txtDesc1, gbc_textField_3);
		txtDesc1.setColumns(10);

		JLabel label = new JLabel("2do Descuento");
		label.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 4;
		panel_1.add(label, gbc_label);

		txtDesc2 = new RPImporte();
		txtDesc2.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 4;
		panel_1.add(txtDesc2, gbc_textField_4);
		txtDesc2.setColumns(10);

		JLabel label_1 = new JLabel("Moneda:");
		label_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 5;
		panel_1.add(label_1, gbc_label_1);

		cbMoneda = new WebComboBox();
		cbMoneda.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 5;
		panel_1.add(cbMoneda, gbc_textField_5);

		JLabel label_2 = new JLabel("Precio:");
		label_2.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 6;
		panel_1.add(label_2, gbc_label_2);

		txtPrecio = new RPImporte();
		txtPrecio.setFont(Common.getStandarFont());
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 3;
		gbc_textField_6.gridy = 6;
		panel_1.add(txtPrecio, gbc_textField_6);
		txtPrecio.setColumns(10);

		JLabel label_3 = new JLabel("Fecha Desde:");
		label_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 7;
		panel_1.add(label_3, gbc_label_3);

		dateFechaDesde = new WebDateField();
		dateFechaDesde.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 3;
		gbc_lblNewLabel_4.gridy = 7;
		panel_1.add(dateFechaDesde, gbc_lblNewLabel_4);

		JLabel label_4 = new JLabel("Fecha Hasta:");
		label_4.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 8;
		panel_1.add(label_4, gbc_label_4);

		dateFechaHasta = new WebDateField();
		dateFechaHasta.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 3;
		gbc_lblNewLabel_5.gridy = 8;
		panel_1.add(dateFechaHasta, gbc_lblNewLabel_5);

		JLabel label_5 = new JLabel("Referencia:");
		label_5.setFont(Common.getStandarFont());
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 9;
		panel_1.add(label_5, gbc_label_5);

		txtReferencia = new JTextField();
		txtReferencia.setFont(Common.getStandarFont());
		GridBagConstraints gbc_txtReferencia1 = new GridBagConstraints();
		gbc_txtReferencia1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtReferencia1.gridx = 3;
		gbc_txtReferencia1.gridy = 9;
		panel_1.add(txtReferencia, gbc_txtReferencia1);
		txtReferencia.setColumns(10);
	}

	@Override
	public void asignarBotones() {
		asignarBotonAccion(btnAceptar, ConstantesRP.AccionesCargaItemFamilia.ACEPTAR.toString());
		asignarBotonAccion(btnCancelar, ConstantesRP.AccionesCargaItemFamilia.CANCELAR.toString());
	}

}