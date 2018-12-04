package ar.com.rollpaper.pricing.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;

import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.dto.ListaDTO;
import ar.com.rollpaper.pricing.view.CargaClienteEsclavoView;
import ar.com.rollpaper.pricing.view.CargaPrecioView;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.componentes.RPTable;
import ar.com.rp.ui.interfaces.RPTableEvent;
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
	private VentLipv listaSeleccionada = null;
	private ComboBoxModel<ListaDTO> cbmLista;

	public VentLipv getNroLista() {
		return listaSeleccionada;
	}

	public BuscarListaDialog(BasePantallaPrincipal<?, ?> pantPrincipal, ComboBoxModel<ListaDTO> cbmLista) {
		super(pantPrincipal);
		this.cbmLista = cbmLista;
		setBounds(100, 100, 600, 600);
		setModal(true);
		listaSeleccionada = null;
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnSeleccionar = new JButtonRP("Seleccionar");
		btnSeleccionar.setIcon(Common.loadIconMenu(CargaPrecioView.class.getResource("/images/ok.png")));
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableLista.getSelectedRow() > -1) {
					listaSeleccionada = (VentLipv) tableLista.getModel().getValueAt(tableLista.getSelectedRow(), COL_REGISTRO);
					if (!isListaYaCargada(listaSeleccionada)) {
						cerrar();
					} else {
						listaSeleccionada = null;
						Dialog.showMessageDialog("La lista ya esta catrgada, no se puede seleccionar");
					}
				}
			}
		});
		btnSeleccionar.setFont(Common.getStandarFont());

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
		getContentPane().add(panel_1, BorderLayout.NORTH);

		JLabel lblNombreDelCliente = new JLabel("Nombre de la Lista:");
		lblNombreDelCliente.setFont(Common.getStandarFont());
		panel_1.add(lblNombreDelCliente);

		txtDescCliente = new JTextField();
		txtDescCliente.setFont(Common.getStandarFont());
		panel_1.add(txtDescCliente);
		txtDescCliente.setColumns(25);

		btnBuscar = new JButtonRP("Buscar");
		btnBuscar.setIcon(Common.loadIconMenu(CargaPrecioView.class.getResource("/images/search.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		btnBuscar.setFont(Common.getStandarFont());
		panel_1.add(btnBuscar);

		String[] header = { "Nro de Lista", "Nombre", "Moneda", "" };
		String[][] data = {};
		tableLista = new RPTable();
		tableLista.setModel(new DefaultTableModel(data, header));
		tableLista.setEditable(false);
		tableLista.setColToIgnorar(new Integer[] { COL_REGISTRO });
		tableLista.getColumnModel().getColumn(COL_REGISTRO).setMaxWidth(0);
		tableLista.getColumnModel().getColumn(COL_REGISTRO).setMinWidth(0);
		tableLista.getColumnModel().getColumn(COL_REGISTRO).setPreferredWidth(0);
		tableLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		panel.add(btnSeleccionar);
		tableLista.setRpTableEvent(new RPTableEvent() {
			@Override
			public void doubleClick(Integer fila, Integer columna) {
				btnSeleccionar.doClick();
			}
		});

		WebScrollPane scrollPane = new WebScrollPane(tableLista);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		cambioCliente();
	}

	protected boolean isListaYaCargada(VentLipv listaSeleccionada) {
		for (int i = 0; i < cbmLista.getSize(); i++) {
			if (cbmLista.getElementAt(i).getVentLipv().getLipvListaPrecvta() == listaSeleccionada.getLipvListaPrecvta())
				return true;
		}
		return false;
	}

	private void cambioCliente() {
		btnSeleccionar.setEnabled(tableLista.getRowCount() > 0);
	}

	protected void buscar() {
		tableLista.clear();

		for (VentLipv lista : VentLipvDAO.getListaFamilia(txtDescCliente.getText())) {
			tableLista.addRow(new Object[] { lista.getLipvListaPrecvta(), lista.getLipvNombre(), lista.getSistMone().getMoneNombre(), lista });
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