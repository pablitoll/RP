package ar.com.rollpaper.pricing.dao;
// Generated 24/05/2018 09:16:17 by Hibernate Tools 5.3.0.Beta2

import java.util.List;
import javax.naming.InitialContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class VentLipv.
 * 
 * @see ar.com.rollpaper.pricing.beans.VentLipv
 * @author Hibernate Tools
 */
public class VentLipvDAO {

	private static final Log log = LogFactory.getLog(VentLipvDAO.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(VentLipv transientInstance) {
		log.debug("persisting VentLipv instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(VentLipv instance) {
		log.debug("attaching dirty VentLipv instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VentLipv instance) {
		log.debug("attaching clean VentLipv instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(VentLipv persistentInstance) {
		log.debug("deleting VentLipv instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VentLipv merge(VentLipv detachedInstance) {
		log.debug("merging VentLipv instance");
		try {
			VentLipv result = (VentLipv) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public static VentLipv findById(int id) {
		// TODO VER PORQUE NO ANDA
		return null;
		// log.debug("getting VentLipv instance with id: " + id);
		// try {
		// VentLipv instance = (VentLipv) HibernateUtil.getSession()
		// .get("ar.com.rollpaper.pricing.beans.VentLipv", id);
		// if (instance == null) {
		// log.debug("get successful, no instance found");
		// } else {
		// log.debug("get successful, instance found");
		// }
		// return instance;
		// } catch (RuntimeException re) {
		// log.error("get failed", re);
		// throw re;
		// }
	}

	public List findByExample(VentLipv instance) {
		log.debug("finding VentLipv instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("ar.com.rollpaper.pricing.beans.VentLipv")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
