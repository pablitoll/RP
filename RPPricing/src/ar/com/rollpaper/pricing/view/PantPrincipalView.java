package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

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
	public JLabel lblAmbiente = new JLabel("lblAmbiente");

	public JPanel pnlOnline = new JPanel();
	public JPanel pnlCentral = new JPanel();
	public JPanel pnlTeller = new JPanel();

	public JButtonBarraBotonesRP btnCalculadora = new JButtonBarraBotonesRP("Calculadora");

	private final JMenu mnConsulta = new JMenu("Consulta");
	private final JMenu mmAyuda = new JMenu("Ayuda");
	private final JMenu mnMantenimiento = new JMenu("Mantenimiento");
	private final JMenu mnSalir = new JMenu("Salir");

	private final JMenuItem mntmSalir = new JMenuItem("Salir de la Aplicacion");

	private final JMenuItem mntmConsulta1 = new JMenuItem("Consulta XX");

	private final JMenuItem mnuVersion = new JMenuItem("Version");

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

		mnMantenimiento.setFont(Common.getStandarFontMenu());

		mnConsulta.setFont(Common.getStandarFontMenu());
		menuBar.add(mnConsulta);
		mnConsulta.setFont(Common.getStandarFontMenu());
		mnConsulta.add(mntmConsulta1);
		mntmConsulta1.setFont(Common.getStandarFontMenu());
		mnConsulta.addSeparator();

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

		// Barra lateral
		lblAmbiente.setVisible(false);
		toolBarBotones.add(lblAmbiente);

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
		// ConstantesRP.Acciones.VER_LOG_OPERACIONES_DIARIA_B.toString());
		// asignarBotonAccion(mnuVersion, ConstantesRP.Acciones.VERSION.toString());
	}

}
