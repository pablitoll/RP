package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebFormattedTextField;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BaseViewMVCExtendida;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class CargaClienteEsclavoView extends BaseViewMVCExtendida {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int COL_ID_CLIENTE_ESCLAVO = 0;
	public static final int COL_DESC = 1;
	public static final int COL_DESC_LEGAL = 2;
	public static final int COL_REGISTRO = 3;
	public RPTable tableEsclavo;
	public JLabel lblNombreLista;
	public JLabel lblNombreLegal;
	public JLabel lblNombreCliente;
	public JButtonRP btnExpportar;
	public JButtonRP btnExportarTodo;
	private JPanel pnlCentral;
	private JPanel panel;
	public JButtonRP btnAgregar;
	public JButtonRP btnEliminar;
	public JLabel lblNroLista;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	public JButtonRP btnTerminarCarga;
	public WebFormattedTextField txtNroCliente;
	private JLabel lblTitle;

	public CargaClienteEsclavoView() throws Exception {
		super();
		lblTitle = new JLabel("lblTitle");
		lblTitle.setBorder(null);
		setTitle("Carga de Cliente/Esclavo");
		lblTitle.setText(getTitle());

		JPanel pnlSuperior = new JPanel();
		pnlSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(pnlSuperior, BorderLayout.NORTH);
		GridBagLayout gbl_pnlSuperior = new GridBagLayout();
		gbl_pnlSuperior.columnWidths = new int[] { 76, 100, 100, 111, 150, 0, 0 };
		gbl_pnlSuperior.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_pnlSuperior.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlSuperior.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlSuperior.setLayout(gbl_pnlSuperior);

		
		lblTitle.setFont(Common.getStandarFontBold(18));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 6;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		pnlSuperior.add(lblTitle, gbc_lblTitle);

		JLabel lblcliente = new JLabel("Nro. de Cliente:");
		lblcliente.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNroLista = new GridBagConstraints();
		gbc_lblNroLista.anchor = GridBagConstraints.WEST;
		gbc_lblNroLista.insets = new Insets(0, 0, 5, 5);
		gbc_lblNroLista.gridx = 0;
		gbc_lblNroLista.gridy = 1;
		pnlSuperior.add(lblcliente, gbc_lblNroLista);

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
		pnlSuperior.add(txtNroCliente, gbc_txtNroCliente);
		txtNroCliente.setColumns(10);

		lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		pnlSuperior.add(lblNewLabel, gbc_lblNewLabel);

		lblNombreCliente = new JLabel("New label");
		lblNombreCliente.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNombreCliente = new GridBagConstraints();
		gbc_lblNombreCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombreCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreCliente.gridx = 3;
		gbc_lblNombreCliente.gridy = 1;
		pnlSuperior.add(lblNombreCliente, gbc_lblNombreCliente);

		lblNewLabel_3 = new JLabel("Nombre Legal:");
		lblNewLabel_3.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 4;
		gbc_lblNewLabel_3.gridy = 1;
		pnlSuperior.add(lblNewLabel_3, gbc_lblNewLabel_3);

		lblNombreLegal = new JLabel("New label");
		lblNombreLegal.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNombreLegal = new GridBagConstraints();
		gbc_lblNombreLegal.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombreLegal.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombreLegal.gridx = 5;
		gbc_lblNombreLegal.gridy = 1;
		pnlSuperior.add(lblNombreLegal, gbc_lblNombreLegal);

		JLabel lblNewLabel_1 = new JLabel("Nro de Lista de Precio");
		lblNewLabel_1.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		pnlSuperior.add(lblNewLabel_1, gbc_lblNewLabel_1);

		lblNroLista = new JLabel("New label");
		lblNroLista.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNroListaShow = new GridBagConstraints();
		gbc_lblNroListaShow.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNroListaShow.insets = new Insets(0, 0, 0, 5);
		gbc_lblNroListaShow.gridx = 1;
		gbc_lblNroListaShow.gridy = 2;
		pnlSuperior.add(lblNroLista, gbc_lblNroListaShow);

		lblNewLabel_2 = new JLabel("Nombre Lista");
		lblNewLabel_2.setFont(Common.getStandarFont());
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 2;
		pnlSuperior.add(lblNewLabel_2, gbc_lblNewLabel_2);

		lblNombreLista = new JLabel("New label");
		lblNombreLista.setFont(Common.getStandarFontBold());
		GridBagConstraints gbc_lblNombreLista = new GridBagConstraints();
		gbc_lblNombreLista.fill = GridBagConstraints.BOTH;
		gbc_lblNombreLista.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreLista.gridx = 3;
		gbc_lblNombreLista.gridy = 2;
		pnlSuperior.add(lblNombreLista, gbc_lblNombreLista);

		String[] header = { "Nro Cliente", "Nombre del Cliente", "Nombre de Fantasia", "" };
		String[][] data = { {} };

		btnTerminarCarga = new JButtonRP("Terminar Carga e Impactar precios");
		btnTerminarCarga.setIcon(Common.loadIconMenu(ConstantesRP.IMG_PESOS));
		btnTerminarCarga.setFont(Common.getStandarFont());
		btnTerminarCarga.setMnemonic(KeyEvent.VK_ESCAPE);
		pnlInferiorBotones.add(btnTerminarCarga);

		btnExpportar = new JButtonRP("Exportar a Excel");
		btnExpportar.setIcon(CommonUtils.loadIcon(ConstantesRP.IMG_EXCEL, 15, 15));
		btnExpportar.setFont(Common.getStandarFont());
		btnExpportar.setToolTipText("Exportar a Excel el Cliente Actual y sus Hijos");
		pnlInferiorBotones.add(btnExpportar);

		btnExportarTodo = new JButtonRP("Imprimir Todo");
		btnExportarTodo.setFont(Common.getStandarFont());
		btnExportarTodo.setToolTipText("Exportar a Excel TODOS los Clientes con sus Hijos");
		btnExportarTodo.setVisible(false);
		pnlInferiorBotones.add(btnExportarTodo);

		pnlCentral = new JPanel();
		getContentPane().add(pnlCentral, BorderLayout.CENTER);

		tableEsclavo = new RPTable();
		tableEsclavo.setModel(new DefaultTableModel(data, header));
		tableEsclavo.setEditable(false);
		tableEsclavo.setFont(Common.getStandarFont());
		tableEsclavo.setRowHeight(30);
		tableEsclavo.getColumnModel().getColumn(COL_ID_CLIENTE_ESCLAVO).setCellRenderer(tableEsclavo.getCenterRender());
		tableEsclavo.getColumnModel().removeColumn(tableEsclavo.getColumnModel().getColumn(COL_REGISTRO));
		tableEsclavo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		pnlCentral.setLayout(new BorderLayout(0, 0));

		WebScrollPane spEsclavo = new WebScrollPane(tableEsclavo);
		pnlCentral.add(spEsclavo);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 89, 0 };
		gbl_panel.rowHeights = new int[] { 23, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		btnAgregar = new JButtonRP("Agregar");
		btnAgregar.setIcon(Common.loadIconMenu("com/alee/managers/notification/icons/types/plus.png"));
		btnAgregar.setFont(Common.getStandarFont());
		btnAgregar.setMnemonic(KeyEvent.VK_PLUS);
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.anchor = GridBagConstraints.NORTH;
		gbc_btnAgregar.insets = new Insets(0, 0, 5, 0);
		gbc_btnAgregar.gridx = 0;
		gbc_btnAgregar.gridy = 0;
		panel.add(btnAgregar, gbc_btnAgregar);

		btnEliminar = new JButtonRP("Eliminar");
		btnEliminar.setIcon(Common.loadIconMenu("com/alee/managers/notification/icons/types/minus.png"));
		btnEliminar.setFont(Common.getStandarFont());
		btnEliminar.setMnemonic(KeyEvent.VK_MINUS);
		GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
		gbc_btnEliminar.anchor = GridBagConstraints.NORTH;
		gbc_btnEliminar.gridx = 0;
		gbc_btnEliminar.gridy = 1;
		panel.add(btnEliminar, gbc_btnEliminar);
	}

	@Override
	public void asignarBotonesPantExtendida() {
		asignarBotonAccion(btnAgregar, ConstantesRP.PantCarClienteEsclabo.AGREGAR.toString());
		asignarBotonAccion(btnEliminar, ConstantesRP.PantCarClienteEsclabo.BORRAR.toString());
		asignarBotonAccion(btnTerminarCarga, ConstantesRP.PantCarClienteEsclabo.TERMINAR_CARGA.toString());
		asignarBotonAccion(btnExpportar, ConstantesRP.PantCarClienteEsclabo.EXPORTAR.toString());
		asignarBotonAccion(btnExportarTodo, ConstantesRP.PantCarClienteEsclabo.EXPORTAR_TODO.toString());
	}

	public void setCerrarVisible(Boolean visible) {
		btnCerrar.setVisible(visible);
	}

}
