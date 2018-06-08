package ar.com.rollpaper.pricing.dao;
// Generated 21/05/2018 20:09:26 by Hibernate Tools 5.3.0.Beta2

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class VentArpc.
 * @see ar.com.rollpaper.pricing.beans.VentArpc
 * @author Hibernate Tools
 */
public class VentArpcDAO {

	private static final Log log = LogFactory.getLog(VentArpcDAO.class);

//	private final SessionFactory sessionFactory = getSessionFactory();
//
//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext().lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
//		}
//	}
//
//	public void persist(VentArpc transientInstance) {
//		log.debug("persisting VentArpc instance");
//		try {
//			sessionFactory.getCurrentSession().persist(transientInstance);
//			log.debug("persist successful");
//		} catch (RuntimeException re) {
//			log.error("persist failed", re);
//			throw re;
//		}
//	}
//
//	public void attachDirty(VentArpc instance) {
//		log.debug("attaching dirty VentArpc instance");
//		try {
//			sessionFactory.getCurrentSession().saveOrUpdate(instance);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void attachClean(VentArpc instance) {
//		log.debug("attaching clean VentArpc instance");
//		try {
//			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void delete(VentArpc persistentInstance) {
//		log.debug("deleting VentArpc instance");
//		try {
//			sessionFactory.getCurrentSession().delete(persistentInstance);
//			log.debug("delete successful");
//		} catch (RuntimeException re) {
//			log.error("delete failed", re);
//			throw re;
//		}
//	}
//
//	public VentArpc merge(VentArpc detachedInstance) {
//		log.debug("merging VentArpc instance");
//		try {
//			VentArpc result = (VentArpc) sessionFactory.getCurrentSession().merge(detachedInstance);
//			log.debug("merge successful");
//			return result;
//		} catch (RuntimeException re) {
//			log.error("merge failed", re);
//			throw re;
//		}
//	}
//
//	public VentArpc findById(VentArpcId id) {
//		log.debug("getting VentArpc instance with id: " + id);
//		try {
//			VentArpc instance = (VentArpc) sessionFactory.getCurrentSession()
//					.get("ar.com.rollpaper.pricing.beans.VentArpc", id);
//			if (instance == null) {
//				log.debug("get successful, no instance found");
//			} else {
//				log.debug("get successful, instance found");
//			}
//			return instance;
//		} catch (RuntimeException re) {
//			log.error("get failed", re);
//			throw re;
//		}
//	}
//
//	public List findByExample(VentArpc instance) {
//		log.debug("finding VentArpc instance by example");
//		try {
//			List results = sessionFactory.getCurrentSession()
//					.createCriteria("ar.com.rollpaper.pricing.beans.VentArpc").add(Example.create(instance))
//					.list();
//			log.debug("find by example successful, result size: " + results.size());
//			return results;
//		} catch (RuntimeException re) {
//			log.error("find by example failed", re);
//			throw re;
//		}
//	}
}
