package ar.com.rollpaper.pricing.dao;
// Generated 24/05/2018 09:03:46 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class PricMaestroEsclavo.
 * @see ar.com.rollpaper.pricing.beans.MaestroEsclavo.PricMaestroEsclavo
 * @author Hibernate Tools
 */
public class MaestroEsclavoDAO {

	private static final Log log = LogFactory.getLog(MaestroEsclavoDAO.class);



	public static void persist(MaestroEsclavo transientInstance) {
		log.debug("persisting PricMaestroEsclavo instance");
		try {
		
			//HibernateUtil.persist(transientInstance);
			
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(MaestroEsclavo instance) {
		log.debug("attaching dirty PricMaestroEsclavo instance");
		try {
			Session session = HibernateUtil.getSession();			
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MaestroEsclavo instance) {
		log.debug("attaching clean PricMaestroEsclavo instance");
		try {
			Session session = HibernateUtil.getSession();	
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(MaestroEsclavo persistentInstance) {
		log.debug("deleting PricMaestroEsclavo instance");
		try {
			Session session = HibernateUtil.getSession();	
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MaestroEsclavo merge(MaestroEsclavo detachedInstance) {
		log.debug("merging PricMaestroEsclavo instance");
		try {
			Session session = HibernateUtil.getSession();	
			MaestroEsclavo result = (MaestroEsclavo) session.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public MaestroEsclavo findById(int id) {
		log.debug("getting PricMaestroEsclavo instance with id: " + id);
		try {
			Session session = HibernateUtil.getSession();
			MaestroEsclavo instance = (MaestroEsclavo)session.get("ar.com.rollpaper.pricing.beans.PricMaestroEsclavo", id);
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

	public List findByExample(MaestroEsclavo instance) {
		log.debug("finding PricMaestroEsclavo instance by example");
		try {
			Session session = HibernateUtil.getSession();
			List results = session.createCriteria("ar.com.rollpaper.pricing.beans.PricMaestroEsclavo")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
