package ar.com.rollpaper.pricing.dao;
// Generated 22/05/2018 19:11:08 by Hibernate Tools 5.3.0.Beta2

import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class PricPreciosEspeciales.
 * 
 * @see ar.com.rollpaper.pricing.beans.PricPreciosEspeciales
 * @author Hibernate Tools
 */
public class PreciosEspecialesDAO {

	private static final Log log = LogFactory.getLog(PreciosEspecialesDAO.class);

	public static void persist(Object transientInstance) {
		log.debug("persisting PricPreciosEspeciales instance");
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		try {
			session.persist(transientInstance);
			session.flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		} finally {
			session.getTransaction().commit();
		}
	}

	public static void attachDirty(PreciosEspeciales instance) {
		log.debug("attaching dirty PricPreciosEspeciales instance");
		Session session = HibernateUtil.getSession();
		try {
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static void attachClean(PreciosEspeciales instance) {
		log.debug("attaching clean PricPreciosEspeciales instance");
		Session session = HibernateUtil.getSession();
		try {
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static void delete(PreciosEspeciales persistentInstance) {
		log.debug("deleting PricPreciosEspeciales instance");
		Session session = HibernateUtil.getSession();
		try {
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public static PreciosEspeciales merge(PreciosEspeciales detachedInstance) {
		log.debug("merging PricPreciosEspeciales instance");
		Session session = HibernateUtil.getSession();
		try {
			PreciosEspeciales result = (PreciosEspeciales) session.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public static PreciosEspeciales findById(int id) {
		log.debug("getting PricPreciosEspeciales instance with id: " + id);
		Session session = HibernateUtil.getSession();
		try {
			// PreciosEspeciales instance = (PreciosEspeciales)
			// session.get("ar.com.rollpaper.pricing.beans.PricPreciosEspeciales", id);
			PreciosEspeciales instance = (PreciosEspeciales) session.get(PreciosEspeciales.class, id);
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

	// public static List findByExample(PreciosEspeciales instance) {
	// log.debug("finding PricPreciosEspeciales instance by example");
	// Session session = HibernateUtil.getSession();
	// try {
	// List results =
	// session.createCriteria("ar.com.rollpaper.pricing.beans.PricPreciosEspeciales").add(Example.create(instance)).list();
	// log.debug("find by example successful, result size: " + results.size());
	// return results;
	// } catch (RuntimeException re) {
	// log.error("find by example failed", re);
	// throw re;
	// }
	// }

	public static List<PreciosEspeciales> getListaPrecioEspeciaByID(Integer pricCliente, Integer nroLista) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<PreciosEspeciales> criteriaQuery = session.getCriteriaBuilder().createQuery(PreciosEspeciales.class);
		Root<PreciosEspeciales> i = criteriaQuery.from(PreciosEspeciales.class);
		criteriaQuery.where(cb.equal(i.get("pricCliente"), pricCliente), cb.equal(i.get("pricListaPrecvta"), nroLista));

		List<PreciosEspeciales> clientes = session.createQuery(criteriaQuery).getResultList();

		return clientes;

	}

	public static List<PreciosEspeciales> getByCliente(int clieCliente) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<PreciosEspeciales> criteriaQuery = session.getCriteriaBuilder().createQuery(PreciosEspeciales.class);
		Root<PreciosEspeciales> i = criteriaQuery.from(PreciosEspeciales.class);
		criteriaQuery.where(cb.equal(i.get("pricCliente"), clieCliente));

		List<PreciosEspeciales> clientes = session.createQuery(criteriaQuery).getResultList();

		return clientes;

	}

	public static List<PreciosEspeciales> getByClienteLista(CcobClie cliente, VentLipv lista, java.util.Date hoy) {
			return getListaPrecioEspeciaByID(cliente.getClieCliente(),lista.getLipvListaPrecvta());
	}

}
