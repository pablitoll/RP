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
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;

import ar.com.rollpaper.pricing.beans.StocCa01;
import ar.com.rollpaper.pricing.dao.StocCa01DAO;
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
	private String nroFamilia = null;
	private JButtonRP btnTodos;

	public String getNroFamilia() {
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
				nroFamilia = (String) tableFamilia.getModel().getValueAt(tableFamilia.getSelectedRow(), 0);
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
		
		btnTodos = new JButtonRP("Visualizar Todos");
		btnTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar("");
			}
		});
		btnTodos.setFont(Common.getStandarFont());
		panel.add(btnTodos);
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
				buscar(txtDescFamilia.getText());
			}
		});
		btnBuscar.setFont(Common.getStandarFont());
		panel_1.add(btnBuscar);

		String[] header = { "Nro Familia", "Nombre" };
		String[][] data = {};
		tableFamilia = new RPTable();
		tableFamilia.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
		        if (mouseEvent.getClickCount() == 2 && tableFamilia.getSelectedRow() != -1) {
		            btnSeleccionar.doClick(); 
		        }
		    }
		});
		tableFamilia.setModel(new DefaultTableModel(data, header));
		tableFamilia.setEditable(false);
		WebScrollPane scrollPane = new WebScrollPane(tableFamilia);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		cambioArticulo();
	}
	
	private void cambioArticulo() {
		btnSeleccionar.setEnabled(tableFamilia.getRowCount() > 0);
	}

	protected void buscar(String nombre) {
		tableFamilia.clear();

		for (StocCa01 art : StocCa01DAO.getListaFamiliaByDesc_ID(nombre)) {
			tableFamilia.addRow(new Object[] { art.getCa01Clasif1(), art.getCa01Nombre() });
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