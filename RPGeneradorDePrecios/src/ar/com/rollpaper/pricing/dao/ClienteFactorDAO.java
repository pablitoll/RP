package ar.com.rollpaper.pricing.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import ar.com.rollpaper.pricing.beans.ClienteFactor;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class PricMaestroEsclavo.
 * 
 * @see ar.com.rollpaper.pricing.beans.MaestroEsclavo.PricMaestroEsclavo
 * @author Hibernate Tools
 */
public class ClienteFactorDAO {

	private static final Log log = LogFactory.getLog(ClienteFactorDAO.class);

	public static ClienteFactor findById(int id) {
		log.debug("getting ClienteFactor instance with id: " + id);
		try {
			Session session = HibernateUtil.getSession();
			ClienteFactor instance = (ClienteFactor) session.get("ar.com.rollpaper.pricing.beans.ClienteFactor", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

}
