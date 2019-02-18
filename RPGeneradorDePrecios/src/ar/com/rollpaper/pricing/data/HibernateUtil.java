package ar.com.rollpaper.pricing.data;

import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author pmv
 */
public class HibernateUtil {
	private static StandardServiceRegistry registry;
	private static Session session;

	public static Session getSession() {
		if (session == null) {
			System.out.println("no se realizo el getSessionFactory");
		}

		return session;
	}

	public static void getSessionFactory(String ConecctionString, String getUsr, String getPass) throws Exception {
		if (session == null) {
			Configuration cfg = new Configuration().addResource("ar/com/rollpaper/pricing/beans/CcobClie.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/StocArts.hbm.xml")
					.addResource("ar/com/rollpaper/pricing/beans/StocCa01.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/VentArpc.hbm.xml")
					.addResource("ar/com/rollpaper/pricing/beans/VentCliv.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/PreciosEspeciales.hbm.xml")
					.addResource("ar/com/rollpaper/pricing/beans/DescuentoXFamilias.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/MaestroEsclavo.hbm.xml")
					.addResource("ar/com/rollpaper/pricing/beans/SistMone.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/VentCliv.hbm.xml")
					.addResource("ar/com/rollpaper/pricing/beans/VentLipv.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/SistUnim.hbm.xml")
					.addResource("ar/com/rollpaper/pricing/beans/VentArpv.hbm.xml").setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
					.setProperty("hibernate.connection.url", ConecctionString).setProperty("hibernate.connection.username", getUsr)
					.setProperty("hibernate.connection.password", getPass).setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect")
					.setProperty("hibernate.show_sql", "true");

			session = cfg.buildSessionFactory().openSession();
		}
	}

	public static void reConectar(String ConecctionString, String getUsr, String getPass) throws Exception {
		getSession().close();
		session = null;
		HibernateUtil.getSessionFactory(ConecctionString, getUsr, getPass);

	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

}