package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.ui.Main;
import ar.com.rp.rpcutils.CommonUtils;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.componentes.JButtonBarraBotonesRP;
import ar.com.rp.ui.pantalla.BasePantallaPrincipalView;

public class PantPrincipalView extends BasePantallaPrincipalView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String textImprimirTicket = "Imprimir Ticket";
	public JLabel lblUsr = new JLabel("lblUsr");

	public JPanel pnlOnline = new JPanel();
	public JPanel pnlCentral = new JPanel();
	public JPanel pnlTeller = new JPanel();

	private JButtonBarraBotonesRP btnCalculadora = new JButtonBarraBotonesRP("Calculadora");

	private final JMenu mnCarga = new JMenu("Carga");
	private final JMenu mnConsulta = new JMenu("Consulta");
	private final JMenu mmAyuda = new JMenu("Ayuda");
	private final JMenu mnMantenimiento = new JMenu("Mantenimiento");
	private final JMenu mnSalir = new JMenu("Salir");

	private final JMenuItem mntmSalir = new JMenuItem("Salir de la Aplicacion");

	private final JMenuItem mntmConsulta1 = new JMenuItem("Consulta XX");

	private final JMenuItem mntmCargaPrecioCliente = new JMenuItem("Carga de Precio de Cliente");

	private final JMenuItem mnuVersion = new JMenuItem("Version");

	private final JMenuItem mntmCargaClienteEsclavo = new JMenuItem("Carga Cliente/Esclavo");

	private JButtonBarraBotonesRP btnCargaPrecio = new JButtonBarraBotonesRP("Carga de Precio");

	private JButtonBarraBotonesRP btnClienteEsclavo = new JButtonBarraBotonesRP("Carga de Cliente/Esclavo");

	public PantPrincipalView() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		super(null);

		setTitle("RollPaper Pricing");
		setIconImage(CommonUtils.loadImage(Main.class.getResource(ConstantesRP.IMG_ICONO_APP), 80, 80));
		toolBarSuperior.setVisible(false);

		pnlBotton.add(pnlCentral, BorderLayout.CENTER);
		pnlCentral.setLayout(new BorderLayout(0, 0));
		pnlBotton.add(pnlOnline, BorderLayout.EAST);

		pnlCentral.add(pnlTeller, BorderLayout.NORTH);
		pnlTeller.setBorder(null);
		pnlTeller.setMinimumSize(new Dimension(10, 10));
		pnlTeller.setLayout(new BorderLayout(0, 0));
		pnlCentral.add(lblUsr, BorderLayout.SOUTH);
		lblUsr.setBorder(null);

		pnlOnline.setLayout(new BorderLayout(0, 0));

		mntmCargaPrecioCliente.setFont(Common.getStandarFont());
		mnCarga.add(mntmCargaPrecioCliente);
		mntmCargaClienteEsclavo.setFont(Common.getStandarFont());
		mnCarga.add(mntmCargaClienteEsclavo);

		mnCarga.setFont(Common.getStandarFont());

		menuBar.add(mnCarga);

		mnConsulta.setFont(Common.getStandarFontMenu());
		menuBar.add(mnConsulta);
		mnConsulta.setFont(Common.getStandarFontMenu());
		mnConsulta.add(mntmConsulta1);
		mntmConsulta1.setFont(Common.getStandarFontMenu());

		mnMantenimiento.setFont(Common.getStandarFontMenu());
		menuBar.add(mnMantenimiento);

		mmAyuda.setFont(Common.getStandarFontMenu());
		menuBar.add(mmAyuda);
		mnuVersion.setFont(Common.getStandarFont());

		mmAyuda.add(mnuVersion);

		mnSalir.setFont(Common.getStandarFontMenu());
		menuBar.add(mnSalir);
		mntmSalir.setFont(Common.getStandarFontMenu());
		mntmSalir.setIcon(Common.loadIconMenu(Main.class.getResource(ConstantesRP.IMG_EXIT)));
		mnSalir.add(mntmSalir);

		agregarBotonStd2Barra(btnCargaPrecio, Main.class.getResource(ConstantesRP.IMG_CAL));
		btnCalculadora.setMnemonicControl(true);
		btnCalculadora.setMnemonic(KeyEvent.VK_P);

		agregarBotonStd2Barra(btnClienteEsclavo, Main.class.getResource(ConstantesRP.IMG_CAL));
		btnCalculadora.setMnemonicControl(true);
		btnCalculadora.setMnemonic(KeyEvent.VK_C);

		toolBarBotones.add(Box.createVerticalGlue()); // De aca en adelante los mando para abajo

		agregarBotonStd2Barra(btnCalculadora, Main.class.getResource(ConstantesRP.IMG_CAL));
		btnCalculadora.setMnemonicControl(true);
		btnCalculadora.setMnemonic(KeyEvent.VK_A);
	}

	@Override
	public void asignarBotones() {
		asignarBotonAccion(mntmSalir, ConstantesRP.Acciones.SALIR.toString());
		asignarBotonAccion(btnCalculadora, ConstantesRP.Acciones.CALCULADORA.toString());
		asignarBotonAccion(mntmConsulta1, ConstantesRP.Acciones.CONSULTA1.toString());
		asignarBotonAccion(mntmCargaPrecioCliente, ConstantesRP.Acciones.CARGA_PRECIO_CLIENTE.toString());
		asignarBotonAccion(mntmCargaClienteEsclavo, ConstantesRP.Acciones.CARGA_CLIENTE_ESCLAVO.toString());

		asignarBotonAccion(btnClienteEsclavo, ConstantesRP.Acciones.CARGA_CLIENTE_ESCLAVO.toString());
		asignarBotonAccion(btnCargaPrecio, ConstantesRP.Acciones.CARGA_PRECIO_CLIENTE.toString());
	}

}
