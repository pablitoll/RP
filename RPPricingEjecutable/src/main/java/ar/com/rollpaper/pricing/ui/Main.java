package ar.com.rollpaper.pricing.ui;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import ar.com.rollpaper.pricing.business.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.pricing.business.LogBusiness;
import ar.com.rollpaper.pricing.controller.PantPrincipalController;
import ar.com.rollpaper.pricing.data.HibernateUtil;
import ar.com.rollpaper.pricing.model.PantPrincipalModel;
import ar.com.rollpaper.pricing.view.PantPrincipalView;
import ar.com.rp.ui.main.MainFramework;

public class Main extends MainFramework {
	/**
	 * Launch the application.
	 */

	private static final int PORT = 12395; // random large port number

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Inicio();
			}

			/**
			 * 
			 */

		});
	}
	
	public static void Inicio() {
		try {

			// Inicializo el font
			inicializarFont();
			ArchivoDePropiedadesBusiness.setPathToConfig(
					Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

			// Cargo un log
			splashMsg("Cargando archivo de Log");
			LogBusiness.inicializarLogManager();

			if (isRunning(PORT)) {
				String[] option = { "Si", "No" };
				Object confirm = Dialog.showConfirmDialogObject(
						"<html>Ya hay una instancia del Cliente ejecutandose en este Puesto <br>Desea Abrir otra instancia?</html>",
						"Nueva Instancia Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, option, option[1]);
				if (confirm == option[1]) {
					System.exit(1);
				}
			}

			// inicializo coneccion a la BD local
			splashMsg("Cargando Base de Datos Local");

			HibernateUtil.getSessionFactory(ArchivoDePropiedadesBusiness.getConecctionString(),
					ArchivoDePropiedadesBusiness.getUsr(), ArchivoDePropiedadesBusiness.getPass());

			// Cargo pantalla pricipal
			splashMsg("Cargando Pantalla Principal");

			PantPrincipalView vista = new PantPrincipalView();
			PantPrincipalModel model = new PantPrincipalModel();
			PantPrincipalController controller = new PantPrincipalController(vista, model);

			controller.iniciar();

		} catch (Exception e) {
			ManejoDeError.showError(e, "Error al iniciar");
			System.exit(-1);
		}
	}

}