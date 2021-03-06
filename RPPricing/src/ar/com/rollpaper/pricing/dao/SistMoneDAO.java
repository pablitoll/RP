package ar.com.rollpaper.pricing.dao;
// Generated 24/05/2018 09:16:17 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.rollpaper.pricing.beans.SistMone;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class SistMone.
 * 
 * @see ar.com.rollpaper.pricing.beans.SistMone
 * @author Hibernate Tools
 */
public class SistMoneDAO {

	private static final Log log = LogFactory.getLog(SistMoneDAO.class);

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
	// public void persist(SistMone transientInstance) {
	// log.debug("persisting SistMone instance");
	// try {
	// sessionFactory.getCurrentSession().persist(transientInstance);
	// log.debug("persist successful");
	// } catch (RuntimeException re) {
	// log.error("persist failed", re);
	// throw re;
	// }
	// }
	//
	// public void attachDirty(SistMone instance) {
	// log.debug("attaching dirty SistMone instance");
	// try {
	// sessionFactory.getCurrentSession().saveOrUpdate(instance);
	// log.debug("attach successful");
	// } catch (RuntimeException re) {
	// log.error("attach failed", re);
	// throw re;
	// }
	// }
	//
	// public void attachClean(SistMone instance) {
	// log.debug("attaching clean SistMone instance");
	// try {
	// sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
	// log.debug("attach successful");
	// } catch (RuntimeException re) {
	// log.error("attach failed", re);
	// throw re;
	// }
	// }
	//
	// public void delete(SistMone persistentInstance) {
	// log.debug("deleting SistMone instance");
	// try {
	// sessionFactory.getCurrentSession().delete(persistentInstance);
	// log.debug("delete successful");
	// } catch (RuntimeException re) {
	// log.error("delete failed", re);
	// throw re;
	// }
	// }
	//
	// public SistMone merge(SistMone detachedInstance) {
	// log.debug("merging SistMone instance");
	// try {
	// SistMone result = (SistMone)
	// sessionFactory.getCurrentSession().merge(detachedInstance);
	// log.debug("merge successful");
	// return result;
	// } catch (RuntimeException re) {
	// log.error("merge failed", re);
	// throw re;
	// }
	// }
	//
	public static SistMone findById(String id) {
		log.debug("getting SistMone instance with id: " + id);
		try {
			SistMone instance = (SistMone) HibernateUtil.getSession().get("ar.com.rollpaper.pricing.beans.SistMone", id);
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
	//
	// public List<SistMone> findByExample(SistMone instance) {
	// log.debug("finding SistMone instance by example");
	// try {
	// List<SistMone> results =
	// sessionFactory.getCurrentSession().createCriteria("ar.com.rollpaper.pricing.beans.SistMone").add(Example.create(instance)).list();
	// log.debug("find by example successful, result size: " + results.size());
	// return results;
	// } catch (RuntimeException re) {
	// log.error("find by example failed", re);
	// throw re;
	// }
	// }

	public static List<SistMone> getList() {
		CriteriaQuery<SistMone> criteria = HibernateUtil.getSession().getCriteriaBuilder().createQuery(SistMone.class);
		criteria.select(criteria.from(SistMone.class));
		List<SistMone> listaLP = HibernateUtil.getSession().createQuery(criteria).getResultList();
		return listaLP;
	}

}
