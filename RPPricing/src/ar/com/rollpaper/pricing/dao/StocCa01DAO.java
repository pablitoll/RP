package ar.com.rollpaper.pricing.dao;
// Generated 21/05/2018 20:09:26 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.StocCa01;

/**
 * Home object for domain model class StocCa01.
 * @see ar.com.rollpaper.pricing.beans.StocCa01
 * @author Hibernate Tools
 */
public class StocCa01DAO {

	private static final Log log = LogFactory.getLog(StocCa01DAO.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(StocCa01 transientInstance) {
		log.debug("persisting StocCa01 instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(StocCa01 instance) {
		log.debug("attaching dirty StocCa01 instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StocCa01 instance) {
		log.debug("attaching clean StocCa01 instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(StocCa01 persistentInstance) {
		log.debug("deleting StocCa01 instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StocCa01 merge(StocCa01 detachedInstance) {
		log.debug("merging StocCa01 instance");
		try {
			StocCa01 result = (StocCa01) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public StocCa01 findById(java.lang.String id) {
		log.debug("getting StocCa01 instance with id: " + id);
		try {
			StocCa01 instance = (StocCa01) sessionFactory.getCurrentSession()
					.get("ar.com.rollpaper.pricing.beans.StocCa01", id);
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

	public List findByExample(StocCa01 instance) {
		log.debug("finding StocCa01 instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.rollpaper.pricing.beans.StocCa01").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
