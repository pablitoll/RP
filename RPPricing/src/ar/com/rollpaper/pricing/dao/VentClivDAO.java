package ar.com.rollpaper.pricing.dao;
// Generated 21/05/2018 20:09:26 by Hibernate Tools 5.3.0.Beta2

import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.VentCliv;

/**
 * Home object for domain model class VentCliv.
 * @see ar.com.rollpaper.pricing.beans.VentCliv
 * @author Hibernate Tools
 */
public class VentClivDAO {

	private static final Log log = LogFactory.getLog(VentClivDAO.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(VentCliv transientInstance) {
		log.debug("persisting VentCliv instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(VentCliv instance) {
		log.debug("attaching dirty VentCliv instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VentCliv instance) {
		log.debug("attaching clean VentCliv instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(VentCliv persistentInstance) {
		log.debug("deleting VentCliv instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VentCliv merge(VentCliv detachedInstance) {
		log.debug("merging VentCliv instance");
		try {
			VentCliv result = (VentCliv) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public VentCliv findById(int id) {
		log.debug("getting VentCliv instance with id: " + id);
		try {
			VentCliv instance = (VentCliv) sessionFactory.getCurrentSession()
					.get("ar.com.rollpaper.pricing.beans.VentCliv", id);
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

	public List findByExample(VentCliv instance) {
		log.debug("finding VentCliv instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.rollpaper.pricing.beans.VentCliv").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

		
}
