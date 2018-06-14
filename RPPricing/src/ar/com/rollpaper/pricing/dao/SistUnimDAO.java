package ar.com.rollpaper.pricing.dao;
// Generated 13/06/2018 12:29:53 by Hibernate Tools 5.3.0.Beta2

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.rollpaper.pricing.beans.SistUnim;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class SistUnim.
 * 
 * @see ar.com.rollpaper.pricing.beans.rpdb.SistUnim
 * @author Hibernate Tools
 */
public class SistUnimDAO {

	private static final Log log = LogFactory.getLog(SistUnimDAO.class);

	// private final SessionFactory sessionFactory = getSessionFactory();
	//
	// protected SessionFactory getSessionFactory() {
	// try {
	// return (SessionFactory) new InitialContext().lookup("SessionFactory");
	// } catch (Exception e) {
	// log.error("Could not locate SessionFactory in JNDI", e);
	// throw new IllegalStateException("Could not locate SessionFactory in JNDI");
	// }
	// }
	//
	// public void persist(SistUnim transientInstance) {
	// log.debug("persisting SistUnim instance");
	// try {
	// sessionFactory.getCurrentSession().persist(transientInstance);
	// log.debug("persist successful");
	// } catch (RuntimeException re) {
	// log.error("persist failed", re);
	// throw re;
	// }
	// }
	//
	// public void attachDirty(SistUnim instance) {
	// log.debug("attaching dirty SistUnim instance");
	// try {
	// sessionFactory.getCurrentSession().saveOrUpdate(instance);
	// log.debug("attach successful");
	// } catch (RuntimeException re) {
	// log.error("attach failed", re);
	// throw re;
	// }
	// }
	//
	// public void attachClean(SistUnim instance) {
	// log.debug("attaching clean SistUnim instance");
	// try {
	// sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
	// log.debug("attach successful");
	// } catch (RuntimeException re) {
	// log.error("attach failed", re);
	// throw re;
	// }
	// }
	//
	// public void delete(SistUnim persistentInstance) {
	// log.debug("deleting SistUnim instance");
	// try {
	// sessionFactory.getCurrentSession().delete(persistentInstance);
	// log.debug("delete successful");
	// } catch (RuntimeException re) {
	// log.error("delete failed", re);
	// throw re;
	// }
	// }
	//
	// public SistUnim merge(SistUnim detachedInstance) {
	// log.debug("merging SistUnim instance");
	// try {
	// SistUnim result = (SistUnim)
	// sessionFactory.getCurrentSession().merge(detachedInstance);
	// log.debug("merge successful");
	// return result;
	// } catch (RuntimeException re) {
	// log.error("merge failed", re);
	// throw re;
	// }
	// }

	public static SistUnim findById(String id) {
		log.debug("getting SistUnim instance with id: " + id);
		try {
			SistUnim instance = (SistUnim) HibernateUtil.getSession().get(SistUnim.class, id);
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

	// public List findByExample(SistUnim instance) {
	// log.debug("finding SistUnim instance by example");
	// try {
	// List results = sessionFactory.getCurrentSession()
	// .createCriteria("ar.com.rollpaper.pricing.beans.rpdb.SistUnim").add(Example.create(instance))
	// .list();
	// log.debug("find by example successful, result size: " + results.size());
	// return results;
	// } catch (RuntimeException re) {
	// log.error("find by example failed", re);
	// throw re;
	// }
	// }
}
