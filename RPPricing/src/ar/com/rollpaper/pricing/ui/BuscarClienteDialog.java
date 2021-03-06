package ar.com.rollpaper.pricing.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;
import ar.com.rp.ui.pantalla.DialogBase;

public class BuscarClienteDialog extends DialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final int COL_REG_INTERNO = 3;
	protected RPTable tableCliente;
	private JTextField txtDescCliente;
	private JButtonRP btnSeleccionar;
	private JButtonRP btnCancelar;
	private JButtonRP btnBuscar;
	private CcobClie nroCliente = null;

	public CcobClie getCliente() {
		return nroCliente;
	}

	public BuscarClienteDialog(BasePantallaPrincipal<?, ?> pantPrincipal) {
		super(pantPrincipal);
		setBounds(100, 100, 600, 600);
		setModal(true);
		nroCliente = null;
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnSeleccionar = new JButtonRP("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(puedeSeleccionar()) {
					nroCliente = (CcobClie) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), COL_REG_INTERNO);
					cerrar();
				}
			}
			
		});
		btnSeleccionar.setFont(Common.getStandarFont());
		panel.add(btnSeleccionar);

		btnCancelar = new JButtonRP("Cancelar");
		btnCancelar.setFont(Common.getStandarFont());
		btnCancelar.setMnemonic(KeyEvent.VK_ESCAPE);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrar();
			}
		});
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);

		JLabel lblNombreDelCliente = new JLabel("Nombre del Cliente / Nro:");
		lblNombreDelCliente.setFont(Common.getStandarFont());
		panel_1.add(lblNombreDelCliente);

		txtDescCliente = new JTextField();
		txtDescCliente.setFont(Common.getStandarFont());
		panel_1.add(txtDescCliente);
		txtDescCliente.setColumns(25);

		btnBuscar = new JButtonRP("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar();
				if(CommonUtils.isNumeric(txtDescCliente.getText())) {
					if(tableCliente.getRowCount() == 1) {
						btnSeleccionar.doClick();
					}
				}
			}
		});
		btnBuscar.setFont(Common.getStandarFont());
		panel_1.add(btnBuscar);

		String[] header = { "Nro Cliente", "Nombre", "Nombre Legal", "" };
		String[][] data = {};
		tableCliente = new RPTable();
		tableCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2 && tableCliente.getSelectedRow() != -1) {
					btnSeleccionar.doClick();
				}
			}
		});
		tableCliente.setModel(new DefaultTableModel(data, header));
		tableCliente.setEditable(false);
		tableCliente.getColumnModel().removeColumn(tableCliente.getColumnModel().getColumn(COL_REG_INTERNO));
		tableCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		WebScrollPane scrollPane = new WebScrollPane(tableCliente);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		cambioCliente();
	}

	private void cambioCliente() {
		btnSeleccionar.setEnabled(tableCliente.getRowCount() > 0);
	}

	protected void buscar() {
		tableCliente.clear();

		for (CcobClie clie : CcobClieDAO.getListaCliente(txtDescCliente.getText())) {
			tableCliente.addRow(new Object[] { clie.getClieCliente(), clie.getClieNombre(), clie.getClieNombreLegal(), clie});
		}

		if (tableCliente.getRowCount() > 0) {
			tableCliente.setSelectedRow(0);
			tableCliente.requestFocus();
		}

		cambioCliente();
	}

	@Override
	protected void cargaPantalla() throws Exception {
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);
		if (!retorno) {
			if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
				if (txtDescCliente.hasFocus()) {
					retorno = true;
					btnBuscar.doClick();
				}
				if (tableCliente.hasFocus()) {
					retorno = true;
					btnSeleccionar.doClick();
				}
			}
		}
		return retorno;
	}
	
	protected boolean puedeSeleccionar() {
		return true; //Para el hijo
	}

}