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
			log.debug("persist successful");
		} catch (Exception re) {
			log.error("persist failed", re);
			throw re;
		} finally {
			session.getTransaction().commit();
		}
	}

}
