package ar.com.rollpaper.pricing.data;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ar.com.rollpaper.pricing.business.ArchivoDePropiedadesBusiness;

/**
 * @author pmv
 */
public class HibernateUtil {
	private static Session session;

	public static Session getSession() throws HibernateException, Exception {
		if (session == null) {
			session = getSessionFactory().openSession();
		}

		return session;
	}

	public static SessionFactory getSessionFactory() throws Exception {
		Configuration cfg = new Configuration().addResource("ar/com/rollpaper/pricing/beans/CcobClie.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/StocArts.hbm.xml")
				.addResource("ar/com/rollpaper/pricing/beans/StocCa01.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/VentArpc.hbm.xml")
				.addResource("ar/com/rollpaper/pricing/beans/VentCliv.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/PreciosEspeciales.hbm.xml")
				.addResource("ar/com/rollpaper/pricing/beans/DescuentoXFamilias.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/MaestroEsclavo.hbm.xml")
				.addResource("ar/com/rollpaper/pricing/beans/SistMone.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/VentCliv.hbm.xml")
				.addResource("ar/com/rollpaper/pricing/beans/VentLipv.hbm.xml").addResource("ar/com/rollpaper/pricing/beans/SistUnim.hbm.xml")
				.addResource("ar/com/rollpaper/pricing/beans/VentArpv.hbm.xml").setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
				.setProperty("hibernate.connection.url", ArchivoDePropiedadesBusiness.getConecctionString())
				.setProperty("hibernate.connection.username", ArchivoDePropiedadesBusiness.getUsr())
				.setProperty("hibernate.connection.password", ArchivoDePropiedadesBusiness.getPass()).setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect")
				.setProperty("hibernate.show_sql", "true");

		return cfg.buildSessionFactory();

	}

	public static void shutdown() throws Exception {
		getSession().close();
		session = null;
	}

	public static void reConectar() throws Exception {
		getSession().close();
		session = null;
	}

}