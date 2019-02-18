package ar.com.rollpaper.pricing.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

import ar.com.rollpaper.pricing.business.ConstantesRP;
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

	private JButtonBarraBotonesRP btnCalculadora = new JButtonBarraBotonesRP("Calculadora");

	private JButtonBarraBotonesRP btnPrecios = new JButtonBarraBotonesRP("Generar Precios");

	private JButtonBarraBotonesRP btnListaPercioxCliente = new JButtonBarraBotonesRP("Lista de Precios Actualizada (impactados)");
	private JButtonBarraBotonesRP btnListaPercioGenerales = new JButtonBarraBotonesRP("Lista de Precios Generales");

	private final JMenu mnCarga = new JMenu("Carga");
	private final JMenu mnConsulta = new JMenu("Consulta");
	private final JMenu mmAyuda = new JMenu("Ayuda");
	private final JMenu mnMantenimiento = new JMenu("Mantenimiento");
	private final JMenu mnSalir = new JMenu("Salir");

	private final JMenuItem mntmSalir = new JMenuItem("Salir de la Aplicacion");

	private final JMenuItem mntmListaPercioxCliente = new JMenuItem("Lista de Precios Actualizada (impactados)");
	private final JMenuItem mntmListaPercioGenerales = new JMenuItem("Lista de Precios Generales");

	private final JMenuItem mntmCargaPrecioCliente = new JMenuItem("Gestion de Precios");

	private final JMenuItem mnuVersion = new JMenuItem("Version");
	private final JMenuItem mnuCambiarDB = new JMenuItem("Refrescar DB");
	private final JMenuItem mnuCambiarSeparadorDecimal = new JMenuItem("Cambiar Separador Decimal");
	private final JMenuItem mnuCambiarSeparadorMiles = new JMenuItem("Cambiar Separador Miles");

	private final JMenuItem mntmCargaClienteEsclavo = new JMenuItem("Gestion de Cliente/Esclavo");

	private JButtonBarraBotonesRP btnCargaPrecio = new JButtonBarraBotonesRP("Gestion de Precios");

	private JButtonBarraBotonesRP btnClienteEsclavo = new JButtonBarraBotonesRP("Gestion de Cliente/Esclavo");
	public final JLabel lblDB = new JLabel("lblDB");

	public PantPrincipalView() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		super(null);

		setTitle("RollPaper Pricing");
		setIconImage(CommonUtils.loadImage(ConstantesRP.IMG_ICONO_APP, 80, 80));
		toolBarSuperior.setVisible(false);

		pnlBotton.add(pnlCentral, BorderLayout.CENTER);
		pnlCentral.setLayout(new BorderLayout(0, 0));
		pnlBotton.add(pnlOnline, BorderLayout.EAST);

		pnlCentral.add(lblDB, BorderLayout.EAST);
		pnlCentral.add(lblUsr, BorderLayout.WEST);
		lblUsr.setBorder(null);
		lblDB.setBorder(null);

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
		mnConsulta.add(mntmListaPercioxCliente);
		mntmListaPercioxCliente.setFont(Common.getStandarFontMenu());

		mnConsulta.add(mntmListaPercioGenerales);
		mntmListaPercioGenerales.setFont(Common.getStandarFontMenu());

		mnMantenimiento.setFont(Common.getStandarFontMenu());
		mnuCambiarDB.setFont(Common.getStandarFontMenu());
		mnMantenimiento.add(mnuCambiarDB);

		mnuCambiarSeparadorDecimal.setFont(Common.getStandarFontMenu());
		mnMantenimiento.add(mnuCambiarSeparadorDecimal);

		mnuCambiarSeparadorMiles.setFont(Common.getStandarFontMenu());
		mnMantenimiento.add(mnuCambiarSeparadorMiles);

		menuBar.add(mnMantenimiento);

		mmAyuda.setFont(Common.getStandarFontMenu());
		menuBar.add(mmAyuda);
		mnuVersion.setFont(Common.getStandarFont());

		mmAyuda.add(mnuVersion);

		mnSalir.setFont(Common.getStandarFontMenu());
		menuBar.add(mnSalir);
		mntmSalir.setFont(Common.getStandarFontMenu());
		mntmSalir.setIcon(Common.loadIconMenu(ConstantesRP.IMG_EXIT));
		mnSalir.add(mntmSalir);

		agregarBotonStd2Barra(btnCargaPrecio, ConstantesRP.IMG_PRICE_CONFIG);
		btnCargaPrecio.setMnemonicControl(true);
		btnCargaPrecio.setMnemonic(KeyEvent.VK_P);

		agregarBotonStd2Barra(btnClienteEsclavo, ConstantesRP.IMG_MASTER_SLAVE);
		btnClienteEsclavo.setMnemonicControl(true);
		btnClienteEsclavo.setMnemonic(KeyEvent.VK_C);

		agregarBotonStd2Barra(btnPrecios, ConstantesRP.IMG_PESOS);

		agregarBotonStd2Barra(btnListaPercioxCliente, ConstantesRP.IMG_PDF);
		agregarBotonStd2Barra(btnListaPercioGenerales, ConstantesRP.IMG_PDF);

		agregarBotonStd2Barra(btnCalculadora, ConstantesRP.IMG_CAL);
		btnCalculadora.setMnemonicControl(true);
		btnCalculadora.setMnemonic(KeyEvent.VK_A);

	}

	@Override
	public void asignarBotones() {
		asignarBotonAccion(mntmSalir, ConstantesRP.Acciones.SALIR.toString());
		asignarBotonAccion(btnCalculadora, ConstantesRP.Acciones.CALCULADORA.toString());
		asignarBotonAccion(btnPrecios, ConstantesRP.Acciones.GENERAR_PRECIOS.toString());

		asignarBotonAccion(mntmListaPercioxCliente, ConstantesRP.Acciones.LISTA_PRECIO_X_CLIENTE.toString());
		asignarBotonAccion(mntmListaPercioGenerales, ConstantesRP.Acciones.LISTA_PRECIO_GENERALES.toString());

		asignarBotonAccion(mntmCargaClienteEsclavo, ConstantesRP.Acciones.CARGA_CLIENTE_ESCLAVO.toString());
		asignarBotonAccion(mntmCargaPrecioCliente, ConstantesRP.Acciones.CARGA_PRECIO_CLIENTE.toString());
		asignarBotonAccion(mnuCambiarDB, ConstantesRP.Acciones.CAMBIAR_DB.toString());

		asignarBotonAccion(mnuCambiarSeparadorDecimal, ConstantesRP.Acciones.CAMBIAR_SEPARADOR_DECIMAL.toString());
		asignarBotonAccion(mnuCambiarSeparadorMiles, ConstantesRP.Acciones.CAMBIAR_SEPARADOR_MILES.toString());

		asignarBotonAccion(btnClienteEsclavo, ConstantesRP.Acciones.CARGA_CLIENTE_ESCLAVO.toString());
		asignarBotonAccion(btnCargaPrecio, ConstantesRP.Acciones.CARGA_PRECIO_CLIENTE.toString());
		asignarBotonAccion(btnListaPercioxCliente, ConstantesRP.Acciones.LISTA_PRECIO_X_CLIENTE.toString());
		asignarBotonAccion(btnListaPercioGenerales, ConstantesRP.Acciones.LISTA_PRECIO_GENERALES.toString());

	}

}
