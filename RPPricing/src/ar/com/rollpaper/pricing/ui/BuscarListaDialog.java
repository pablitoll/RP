package ar.com.rollpaper.pricing.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;

import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;
import ar.com.rp.ui.pantalla.DialogBase;

public class BuscarListaDialog extends DialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer COL_REGISTRO = 3;
	private RPTable tableLista;
	private JTextField txtDescCliente;
	private JButtonRP btnSeleccionar;
	private JButtonRP btnCancelar;
	private JButtonRP btnBuscar;
	private VentLipv nroLista = null;

	public VentLipv getNroLista() {
		return nroLista;
	}

	public BuscarListaDialog(BasePantallaPrincipal<?, ?> pantPrincipal) {
		super(pantPrincipal);
		setBounds(100, 100, 600, 600);
		setModal(true);
		nroLista = null;
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnSeleccionar = new JButtonRP("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nroLista = (VentLipv) tableLista.getModel().getValueAt(tableLista.getSelectedRow(), COL_REGISTRO);
				cerrar();
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

		JLabel lblNombreDelCliente = new JLabel("Nombre de la Lista:");
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

		String[] header = { "Nro de Lista", "Nombre", "Moneda","" }; 
		String[][] data = {};
		tableLista = new RPTable();
		tableLista.setModel(new DefaultTableModel(data, header));
		tableLista.setEditable(false);
		tableLista.setColToIgnorar(new Integer[] {COL_REGISTRO});
		tableLista.getColumnModel().getColumn(COL_REGISTRO).setMaxWidth(0);
		tableLista.getColumnModel().getColumn(COL_REGISTRO).setMinWidth(0);
		tableLista.getColumnModel().getColumn(COL_REGISTRO).setPreferredWidth(0);
		
		WebScrollPane scrollPane = new WebScrollPane(tableLista);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		cambioCliente();
	}

	private void cambioCliente() {
		btnSeleccionar.setEnabled(tableLista.getRowCount() > 0);
	}

	protected void buscar() {
		tableLista.clear();

		for (VentLipv lista : VentLipvDAO.getListaFamilia(txtDescCliente.getText())) {
			tableLista.addRow(new Object[] { lista.getLipvListaPrecvta(), lista.getLipvNombre(), lista.getSistMone().getMoneNombre(), lista});
		}

		if (tableLista.getRowCount() > 0) {
			tableLista.setSelectedRow(0);
			tableLista.requestFocus();
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
				if (tableLista.hasFocus()) {
					retorno = true;
					btnSeleccionar.doClick();
				}
			}
		}
		return retorno;
	}

}