package ar.com.rollpaper.pricing.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;

import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.interfaces.RPTableEvent;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;
import ar.com.rp.ui.pantalla.DialogBase;

public class BuscarArticuloDialog extends DialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int COL_ID_INTERNO = 3;
	private RPTable tableArticulo;
	private JTextField txtDescArticulo;
	private JButtonRP btnSeleccionar;
	private JButtonRP btnCancelar;
	private JButtonRP btnBuscar;
	private StocArts nroArticulo = null;

	public StocArts getNroArticulo() {
		return nroArticulo;
	}

	public BuscarArticuloDialog(BasePantallaPrincipal<?, ?> pantPrincipal) {
		super(pantPrincipal);
		setBounds(100, 100, 900, 600);
		setModal(true);
		nroArticulo = null;
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnSeleccionar = new JButtonRP("Seleccionar");
		btnSeleccionar.setIcon(Common.loadIconMenu(CargaPrecioView.class.getResource("/images/ok.png")));
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nroArticulo = StocArtsDAO.findById((int) tableArticulo.getModel().getValueAt(tableArticulo.getSelectedRow(), COL_ID_INTERNO));
				cerrar();
			}
		});
		btnSeleccionar.setFont(Common.getStandarFont());
		panel.add(btnSeleccionar);

		btnCancelar = new JButtonRP("Cancelar");
		btnCancelar.setIcon(Common.loadIconMenu(CargaClienteEsclavoView.class.getResource("/com/alee/laf/filechooser/icons/remove.png")));
		btnCancelar.setFont(Common.getStandarFont());
		btnCancelar.setMnemonic(KeyEvent.VK_ESCAPE);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrar();
			}
		});
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_1, BorderLayout.NORTH);

		JLabel lblNombreDelArticulo = new JLabel("Nombre del Articulo / ID:");
		lblNombreDelArticulo.setFont(Common.getStandarFont());
		panel_1.add(lblNombreDelArticulo);

		txtDescArticulo = new JTextField();
		txtDescArticulo.setFont(Common.getStandarFont());
		panel_1.add(txtDescArticulo);
		txtDescArticulo.setColumns(25);

		btnBuscar = new JButtonRP("Buscar");
		btnBuscar.setIcon(Common.loadIconMenu(CargaPrecioView.class.getResource("/images/search.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		btnBuscar.setFont(Common.getStandarFont());
		panel_1.add(btnBuscar);

		String[] header = { "ID Articulo", "Nombre", "Descripcion", "ID" };
		String[][] data = {};
		tableArticulo = new RPTable();
		tableArticulo.setModel(new DefaultTableModel(data, header));
		tableArticulo.setEditable(false);
		tableArticulo.getColumnModel().removeColumn(tableArticulo.getColumnModel().getColumn(COL_ID_INTERNO));
		tableArticulo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableArticulo.setRpTableEvent(new RPTableEvent() {

			@Override
			public void doubleClick(Integer fila, Integer columna) {
				btnSeleccionar.doClick();

			}
		});

		WebScrollPane scrollPane = new WebScrollPane(tableArticulo);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		cambioArticulo();
	}

	private void cambioArticulo() {
		btnSeleccionar.setEnabled(tableArticulo.getRowCount() > 0);
	}

	protected void buscar() {
		tableArticulo.clear();

		for (StocArts art : StocArtsDAO.getListaArticulos(txtDescArticulo.getText())) {
			tableArticulo.addRow(new Object[] { art.getArtsArticuloEmp(), art.getArtsNombre(), art.getArtsDescripcion(), art.getArtsArticulo() });
		}

		if (tableArticulo.getRowCount() > 0) {
			tableArticulo.setSelectedRow(0);
			tableArticulo.requestFocus();
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
				if (txtDescArticulo.hasFocus()) {
					retorno = true;
					btnBuscar.doClick();
				}
				if (tableArticulo.hasFocus()) {
					retorno = true;
					btnSeleccionar.doClick();
				}
			}
		}
		return retorno;
	}

}