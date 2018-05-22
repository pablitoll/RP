package ar.com.rollpaper.pricing.dao;
// Generated 22/05/2018 19:11:08 by Hibernate Tools 5.3.0.Beta2

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.PreciosEspeciales;

/**
 * Home object for domain model class PricPreciosEspeciales.
 * @see ar.com.rollpaper.pricing.beans.rpdb.PricPreciosEspeciales
 * @author Hibernate Tools
 */
public class PreciosEspecialesDAO {

	private static final Log log = LogFactory.getLog(PreciosEspecialesDAO.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(PreciosEspeciales transientInstance) {
		log.debug("persisting PricPreciosEspeciales instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(PreciosEspeciales instance) {
		log.debug("attaching dirty PricPreciosEspeciales instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PreciosEspeciales instance) {
		log.debug("attaching clean PricPreciosEspeciales instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(PreciosEspeciales persistentInstance) {
		log.debug("deleting PricPreciosEspeciales instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PreciosEspeciales merge(PreciosEspeciales detachedInstance) {
		log.debug("merging PricPreciosEspeciales instance");
		try {
			PreciosEspeciales result = (PreciosEspeciales) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public PreciosEspeciales findById(int id) {
		log.debug("getting PricPreciosEspeciales instance with id: " + id);
		try {
			PreciosEspeciales instance = (PreciosEspeciales) sessionFactory.getCurrentSession()
					.get("ar.com.rollpaper.pricing.beans.rpdb.PricPreciosEspeciales", id);
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

	public List findByExample(PreciosEspeciales instance) {
		log.debug("finding PricPreciosEspeciales instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.rollpaper.pricing.beans.rpdb.PricPreciosEspeciales")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
