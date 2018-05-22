package ar.com.rollpaper.pricing.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.alee.laf.table.WebTable;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;
import ar.com.rp.ui.pantalla.DialogBase;

public class BuscarClienteDialog extends DialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTable tableCliente;
	private JTextField txtDescCliente;
	private JButtonRP btnSeleccionar;
	private JButtonRP btnCancelar;
	private JButtonRP btnBuscar;
	private Integer nroCliente = null;

	public Integer getNroCliente() {
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
				nroCliente = 1000;
				cerrar();
			}
		});
		btnSeleccionar.setFont(Common.getStandarFont());
		panel.add(btnSeleccionar);

		btnCancelar = new JButtonRP("Cancelar");
		btnCancelar.setFont(Common.getStandarFont());
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrar();
			}
		});
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);

		JLabel lblNombreDelCliente = new JLabel("Nombre del Cliente:");
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
			}
		});
		btnBuscar.setFont(Common.getStandarFont());
		panel_1.add(btnBuscar);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		String[] header = { "Nro Cliente", "Nombre", "Nombre Legal" };
		String[][] data = {};
		//tableCliente = new WebTable(data, header);
		tableCliente = new WebTable();
		tableCliente.setModel(new DefaultTableModel(data, header));
		tableCliente.setEditable(false);
		scrollPane.setViewportView(tableCliente);
	}

	protected void buscar() {
		DefaultTableModel tableModel = (DefaultTableModel) tableCliente.getModel();
		tableModel.getDataVector().removeAllElements();
		
		for(CcobClie clie : CcobClieDAO.getListaCliente(txtDescCliente.getText())) {
			tableModel.addRow(new Object[] {clie.getClieCliente(), clie.getClieNombre(), clie.getClieNombreLegal()});
		}
		
	}

	@Override
	protected void cargaPantalla() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);
		if (!retorno) {
			if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
				retorno = true;
				btnBuscar.doClick();
			}
		}
		return retorno;
	}

}