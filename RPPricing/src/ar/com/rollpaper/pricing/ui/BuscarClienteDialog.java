package ar.com.rollpaper.pricing.ui;

import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonRP;
import ar.com.rp.ui.pantalla.BasePantallaPrincipal;
import ar.com.rp.ui.pantalla.DialogBase;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuscarClienteDialog extends DialogBase {

	public BuscarClienteDialog(BasePantallaPrincipal<?, ?> pantPrincipal) {
		super(pantPrincipal);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButtonRP btnSeleccionar = new JButtonRP("Seleccionar");
		btnSeleccionar.setFont(Common.getStandarFont());
		panel.add(btnSeleccionar);
		
		JButtonRP btnCancelar = new JButtonRP("Cancelar");
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
		
		descCliente = new JTextField();
		descCliente.setFont(Common.getStandarFont());
		panel_1.add(descCliente);
		descCliente.setColumns(25);
		
		JButtonRP btnBuscar = new JButtonRP("Buscar");
		btnBuscar.setFont(Common.getStandarFont());
		panel_1.add(btnBuscar);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField descCliente;

	@Override
	protected void cargaPantalla() throws Exception {
		// TODO Auto-generated method stub
		
	}
}