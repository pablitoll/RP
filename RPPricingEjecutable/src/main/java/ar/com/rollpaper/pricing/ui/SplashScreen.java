package ar.com.rollpaper.pricing.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

import org.hibernate.HibernateException;

import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;

import ar.com.rollpaper.pricing.business.ArchivoDePropiedadesBusiness;
import ar.com.rollpaper.pricing.business.ConstantesRP;
import ar.com.rollpaper.pricing.business.LogBusiness;
import ar.com.rollpaper.pricing.controller.PantPrincipalController;
import ar.com.rollpaper.pricing.data.HibernateUtil;
import ar.com.rollpaper.pricing.model.PantPrincipalModel;
import ar.com.rollpaper.pricing.view.PantPrincipalView;
import ar.com.rp.ui.main.MainFramework;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class SplashScreen extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static boolean isRegistered;
	private static JProgressBar progressBar = new JProgressBar();
	private static SplashScreen execute;
	private static int count;
	private static Timer timer1;

	private static final int PORT = 12395; // random large port number

	public SplashScreen() {

		Container container = getContentPane();
		container.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBorder(new javax.swing.border.EtchedBorder());
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 10, 528, 308);
		panel.setLayout(null);
		container.add(panel);

		WebImage webImage3 = new WebImage(
				Toolkit.getDefaultToolkit().getImage(SplashScreen.class.getResource("/images/rpLogo.PNG")));
		webImage3.setDisplayType(DisplayType.fitComponent);
		webImage3.setImage(Toolkit.getDefaultToolkit().getImage(SplashScreen.class.getResource("/images/rpLogo.PNG")));
		webImage3.setBounds(0, 0, 525, 312);
		panel.add(webImage3);

		progressBar.setMaximum(50);
		progressBar.setBounds(10, 329, 528, 38);
		container.add(progressBar);
		loadProgressBar();
		setSize(548, 378);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadProgressBar() {
		ActionListener al = new ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				count++;

				progressBar.setValue(count);

				//System.out.println(count);
				if (count == 20) {
					// inicializo el jasper

					try {
						JasperReport jasperReport = (JasperReport) JRLoader.loadObject(Main.class.getResource(ConstantesRP.REPO_LISTA_PRECIO_ORIGINAL));
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (count == 50) {
					try {
						
						ArchivoDePropiedadesBusiness.setPathToConfig(
								Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
						HibernateUtil.getSessionFactory(ArchivoDePropiedadesBusiness.getConecctionString(),
								ArchivoDePropiedadesBusiness.getUsr(), ArchivoDePropiedadesBusiness.getPass());

			//			HibernateUtil.getSession();
						
					} catch (HibernateException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (count == 100) {

					createFrame();

					execute.setVisible(false);// swapped this around with timer1.stop()

					timer1.stop();
				}

			}

			private void createFrame() throws HeadlessException {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						Main.Inicio();
					}
				});
			}
		};
		timer1 = new Timer(7, al);
		timer1.start();
	}

	public static void main(String[] args) {
		execute = new SplashScreen();
	}
};