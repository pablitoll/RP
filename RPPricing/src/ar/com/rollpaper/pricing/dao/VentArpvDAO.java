package ar.com.rollpaper.pricing.dao;
// Generated 24/05/2018 09:16:17 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.VentArpv;

/**
 * Home object for domain model class VentArpv.
 * @see ar.com.rollpaper.pricing.beans.VentArpv
 * @author Hibernate Tools
 */
public class VentArpvDAO {

	private static final Log log = LogFactory.getLog(VentArpvDAO.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(VentArpv transientInstance) {
		log.debug("persisting VentArpv instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(VentArpv instance) {
		log.debug("attaching dirty VentArpv instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VentArpv instance) {
		log.debug("attaching clean VentArpv instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(VentArpv persistentInstance) {
		log.debug("deleting VentArpv instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VentArpv merge(VentArpv detachedInstance) {
		log.debug("merging VentArpv instance");
		try {
			VentArpv result = (VentArpv) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public VentArpv findById(ar.com.rollpaper.pricing.beans.VentArpvId id) {
		log.debug("getting VentArpv instance with id: " + id);
		try {
			VentArpv instance = (VentArpv) sessionFactory.getCurrentSession()
					.get("ar.com.rollpaper.pricing.beans.VentArpv", id);
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

	public List findByExample(VentArpv instance) {
		log.debug("finding VentArpv instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.rollpaper.pricing.beans.VentArpv").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
