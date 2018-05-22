package ar.com.rollpaper.pricing.dao;
// Generated 18/05/2018 07:33:44 by Hibernate Tools 5.3.0.Beta2

import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jcajce.provider.symmetric.AES.CBC;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class CcobClie.
 * 
 * @see ar.com.rollpaper.pricing.beans.CcobClie
 * @author Hibernate Tools
 */
public class CcobClieDAO {

	private static final Log log = null;// LogFactory.getLog(CcobClieDAO.class);

	private final static SessionFactory sessionFactory = null; // getSessionFactory();

	protected static SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(CcobClie transientInstance) {
		log.debug("persisting CcobClie instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(CcobClie instance) {
		log.debug("attaching dirty CcobClie instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CcobClie instance) {
		log.debug("attaching clean CcobClie instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(CcobClie persistentInstance) {
		log.debug("deleting CcobClie instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CcobClie merge(CcobClie detachedInstance) {
		log.debug("merging CcobClie instance");
		try {
			CcobClie result = (CcobClie) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public CcobClie findById(int id) {
		log.debug("getting CcobClie instance with id: " + id);
		try {
			CcobClie instance = (CcobClie) sessionFactory.getCurrentSession()
					.get("ar.com.rollpaper.pricing.beans.CcobClie", id);
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

	public List findByExample(CcobClie instance) {
		log.debug("finding CcobClie instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("ar.com.rollpaper.pricing.beans.CcobClie")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public static List<CcobClie> getListaCliente(String nombre) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<CcobClie> criteriaQuery = session.getCriteriaBuilder().createQuery(CcobClie.class);
		Root<CcobClie> i = criteriaQuery.from(CcobClie.class);
		criteriaQuery.where(cb.like(i.get("clieNombre"), "%" + nombre + "%"));

		List<CcobClie> clientes = session.createQuery(criteriaQuery).getResultList();

		return clientes;

		// Session session = HibernateUtil.getSessionFactory().openSession();
		// session.beginTransaction();
		//
		//
		//
		// CriteriaQuery<CcobClie> criteriaQuery =
		// session.getCriteriaBuilder().createQuery(CcobClie.class);
		// criteriaQuery.from(CcobClie.class);
		//
		// List<CcobClie> clientes = session.createQuery(criteriaQuery).getResultList();

		// return clientes;
	}

}
