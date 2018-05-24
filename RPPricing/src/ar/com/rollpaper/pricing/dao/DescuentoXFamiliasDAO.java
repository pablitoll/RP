package ar.com.rollpaper.pricing.dao;
// Generated 24/05/2018 09:03:46 by Hibernate Tools 5.3.0.Beta2

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;

/**
 * Home object for domain model class PricDescuentoXFamilias.
 * @see ar.com.rollpaper.pricing.beans.DescuentoXFamilias.PricDescuentoXFamilias
 * @author Hibernate Tools
 */
public class DescuentoXFamiliasDAO {

	private static final Log log = LogFactory.getLog(DescuentoXFamiliasDAO.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(DescuentoXFamilias transientInstance) {
		log.debug("persisting PricDescuentoXFamilias instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DescuentoXFamilias instance) {
		log.debug("attaching dirty PricDescuentoXFamilias instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DescuentoXFamilias instance) {
		log.debug("attaching clean PricDescuentoXFamilias instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DescuentoXFamilias persistentInstance) {
		log.debug("deleting PricDescuentoXFamilias instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DescuentoXFamilias merge(DescuentoXFamilias detachedInstance) {
		log.debug("merging PricDescuentoXFamilias instance");
		try {
			DescuentoXFamilias result = (DescuentoXFamilias) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DescuentoXFamilias findById(int id) {
		log.debug("getting PricDescuentoXFamilias instance with id: " + id);
		try {
			DescuentoXFamilias instance = (DescuentoXFamilias) sessionFactory.getCurrentSession()
					.get("ar.com.rollpaper.pricing.beans.rpdb.PricDescuentoXFamilias", id);
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

	public List findByExample(DescuentoXFamilias instance) {
		log.debug("finding PricDescuentoXFamilias instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.rollpaper.pricing.beans.rpdb.PricDescuentoXFamilias")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
