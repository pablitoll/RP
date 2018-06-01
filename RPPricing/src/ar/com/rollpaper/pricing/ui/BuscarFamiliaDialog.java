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

public class BuscarFamiliaDialog extends DialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RPTable tableFamilia;
	private JTextField txtDescFamilia;
	private JButtonRP btnSeleccionar;
	private JButtonRP btnCancelar;
	private JButtonRP btnBuscar;
	private Integer nroFamilia = null;

	public Integer getNroFamilia() {
		return nroFamilia;
	}

	public BuscarFamiliaDialog(BasePantallaPrincipal<?, ?> pantPrincipal) {
		super(pantPrincipal);
		setBounds(100, 100, 600, 600);
		setModal(true);
		nroFamilia = null;
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnSeleccionar = new JButtonRP("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nroFamilia = (Integer) tableFamilia.getModel().getValueAt(tableFamilia.getSelectedRow(), 0);
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

		JLabel lblNombreDelArticulo = new JLabel("Nombre de la Familia:");
		lblNombreDelArticulo.setFont(Common.getStandarFont());
		panel_1.add(lblNombreDelArticulo);

		txtDescFamilia = new JTextField();
		txtDescFamilia.setFont(Common.getStandarFont());
		panel_1.add(txtDescFamilia);
		txtDescFamilia.setColumns(25);

		btnBuscar = new JButtonRP("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		btnBuscar.setFont(Common.getStandarFont());
		panel_1.add(btnBuscar);

		String[] header = { "Nro Familia", "Nombre", "Moneda" };
		String[][] data = {};
		tableFamilia = new RPTable();
		tableFamilia.setModel(new DefaultTableModel(data, header));
		tableFamilia.setEditable(false);
		WebScrollPane scrollPane = new WebScrollPane(tableFamilia);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		cambioArticulo();
	}

	private void cambioArticulo() {
		btnSeleccionar.setEnabled(tableFamilia.getRowCount() > 0);
	}

	protected void buscar() {
		tableFamilia.clear();

		for (VentLipv art : VentLipvDAO.getListaFamilia(txtDescFamilia.getText())) {
			tableFamilia.addRow(new Object[] { art.getLipvListaPrecvta(), art.getLipvNombre(), art.getSistMone().getMoneNombre()});
		}

		if (tableFamilia.getRowCount() > 0) {
			tableFamilia.setSelectedRow(0);
			tableFamilia.requestFocus();
		}

		cambioArticulo();
	}

	@Override
	protected void cargaPantalla() throws Exception {
	}

	@Override
	public boolean presionoTecla(KeyEvent ke) {
		boolean retorno = super.presionoTecla(ke);
		if (!retorno) {
			if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
				if (txtDescFamilia.hasFocus()) {
					retorno = true;
					btnBuscar.doClick();
				}
				if (tableFamilia.hasFocus()) {
					retorno = true;
					btnSeleccionar.doClick();
				}
			}
		}
		return retorno;
	}

}