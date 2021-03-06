package ar.com.rollpaper.pricing.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import ar.com.rollpaper.pricing.data.HibernateUtil;

public class HibernateGeneric {

	private static final Log log = LogFactory.getLog(HibernateGeneric.class);

	public static void persist(Object transientInstance) {
		log.debug("persisting PricPreciosEspeciales instance");
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		try {
			session.persist(transientInstance);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			try {

				session.getTransaction().rollback();
			} catch (RuntimeException runtimeEx) {
				System.err.printf("Couldn�t Roll Back Transaction", runtimeEx);
			}
			e.printStackTrace();
			throw e;

		} finally {

		}
	}

	public static void remove(Object transientInstance) {
		log.debug("eliminando esclavo instance");
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		try {
			session.delete(transientInstance);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			try {

				session.getTransaction().rollback();
			} catch (RuntimeException runtimeEx) {
				System.err.printf("Couldn�t Roll Back Transaction", runtimeEx);
			}
			e.printStackTrace();
			throw e;

		} finally {

		}
	}

}
