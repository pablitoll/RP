package ar.com.rollpaper.robot;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ar.com.rollpaper.pricing.data.HibernateUtil;
import ar.com.rollpaper.robot.ui.ConsolaControler;
import ar.com.rollpaper.robot.ui.ConsolaModel;
import ar.com.rollpaper.robot.ui.ConsolaView;
import ar.com.rollpaper.robot.ui.Dialog;
import ar.com.rollpaper.robot.ui.SplashScreen;
import ar.com.rollpaper.robot.worker.WorkerProcesar;
import ar.com.rp.ui.common.Common;
import ar.com.rp.ui.main.MainFramework;

public class Main extends MainFramework {

	private static ConsolaControler consola = null;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cargaFont();
					cargarHibernate();

				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}

				inicio();
			}
		});
	}

	public static void inicio() {
		try {
			TrayIcon trayIcon = null;
			if (SystemTray.isSupported()) {

				// get the SystemTray instance
				SystemTray tray = SystemTray.getSystemTray();
				Image image = Toolkit.getDefaultToolkit().getImage(SplashScreen.class.getResource(ConstantesRP.IMG_RP));

				ActionListener liSalir = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						salir();
					}
				};

				ActionListener lAbrirConsola = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						abrirConsola();
					}
				};

				// create a popup menu
				PopupMenu popup = new PopupMenu();
				popup.setFont(Common.getStandarFont());

				MenuItem miAbrir = new MenuItem("Abrir Consola");
				miAbrir.addActionListener(lAbrirConsola);
				popup.add(miAbrir);

				popup.addSeparator();

				MenuItem miSalir = new MenuItem("Apagar Robot");
				miSalir.addActionListener(liSalir);
				popup.add(miSalir);

				trayIcon = new TrayIcon(image, "Consola Pricing", popup);
				trayIcon.setImageAutoSize(true);
				// set the TrayIcon properties
				trayIcon.addActionListener(lAbrirConsola);
				// ...
				// add the tray image
				try {
					tray.add(trayIcon);
				} catch (AWTException e) {
					ManejoDeError.showError(e, "Error al iniciar");
				}

				// consola
				consola = new ConsolaControler(new ConsolaView(), new ConsolaModel());
				WorkerProcesar workerProcesar = new WorkerProcesar(consola);
				consola.setInterEntradaWorker(workerProcesar);
				workerProcesar.execute();

				// ...
			} else {
				Dialog.showMessageDialog("No se puede Iniciar");
			}

		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al iniciar");
			System.exit(-1);
		}

	}

	protected static void salir() {
		System.exit(0);
	}

	private static void abrirConsola() {
		consola.show();
	}

	public static void cargarHibernate() throws Exception {
		// inicializo coneccion a la BD local
		HibernateUtil.getSessionFactory(ArchivoDePropiedadesBusiness.getConecctionString(),
				ArchivoDePropiedadesBusiness.getUsr(), ArchivoDePropiedadesBusiness.getPass());

	}

	public static void cargaFont() throws Exception {
		// font
		Main.inicializarFont();

		ArchivoDePropiedadesBusiness
				.setPathToConfig(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

	}

}
