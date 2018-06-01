package ar.com.rollpaper.pricing.dao;
// Generated 24/05/2018 09:03:46 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class DescuentoXFamilias.
 * 
 * @see ar.com.rollpaper.pricing.beans.DescuentoXFamilias.DescuentoXFamilias
 * @author Hibernate Tools
 */
public class DescuentoXFamiliasDAO {

	private static final Log log = LogFactory.getLog(DescuentoXFamiliasDAO.class);

	public void persist(DescuentoXFamilias transientInstance) {
		log.debug("persisting DescuentoXFamilias instance");
		try {
			Session session = HibernateUtil.getSession();
			session.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DescuentoXFamilias instance) {
		log.debug("attaching dirty PricDescuentoXFamilias instance");
		try {
			Session session = HibernateUtil.getSession();
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DescuentoXFamilias instance) {
		log.debug("attaching clean DescuentoXFamilias instance");
		try {
			Session session = HibernateUtil.getSession();
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DescuentoXFamilias persistentInstance) {
		log.debug("deleting PricDescuentoXFamilias instance");
		try {
			Session session = HibernateUtil.getSession();
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DescuentoXFamilias merge(DescuentoXFamilias detachedInstance) {
		log.debug("merging PricDescuentoXFamilias instance");
		try {
			Session session = HibernateUtil.getSession();
			DescuentoXFamilias result = (DescuentoXFamilias) session.merge(detachedInstance);
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
			Session session = HibernateUtil.getSession();
			DescuentoXFamilias instance = (DescuentoXFamilias) session.get("ar.com.rollpaper.pricing.beans.PricDescuentoXFamilias", id);
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
		log.debug("finding DescuentoXFamilias instance by example");
		try {
			Session session = HibernateUtil.getSession();
			List results = session.getSession().createCriteria("ar.com.rollpaper.pricing.beans.DescuentoXFamilias").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public static List<DescuentoXFamilias> getListaDescuentoByID(Integer pricFamiliaCliente) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<DescuentoXFamilias> criteriaQuery = session.getCriteriaBuilder().createQuery(DescuentoXFamilias.class);
		Root<DescuentoXFamilias> i = criteriaQuery.from(DescuentoXFamilias.class);
		criteriaQuery.where(cb.equal(i.get("pricFamiliaCliente"), pricFamiliaCliente));

		List<DescuentoXFamilias> clientes = session.createQuery(criteriaQuery).getResultList();

		return clientes;

	}
}
