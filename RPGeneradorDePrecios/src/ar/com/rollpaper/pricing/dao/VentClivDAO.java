package ar.com.rollpaper.pricing.dao;
// Generated 21/05/2018 20:09:26 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class VentCliv.
 * 
 * @see ar.com.rollpaper.pricing.beans.VentCliv
 * @author Hibernate Tools
 */
public class VentClivDAO {

//	private static final Log log = LogFactory.getLog(VentClivDAO.class);

//	public void persist(VentCliv transientInstance) {
//		log.debug("persisting VentCliv instance");
//		try {
//			HibernateUtil.getSession().persist(transientInstance);
//			log.debug("persist successful");
//		} catch (RuntimeException re) {
//			log.error("persist failed", re);
//			throw re;
//		}
//	}
//
//	public void attachDirty(VentCliv instance) {
//		log.debug("attaching dirty VentCliv instance");
//		try {
//			HibernateUtil.getSession().saveOrUpdate(instance);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void attachClean(VentCliv instance) {
//		log.debug("attaching clean VentCliv instance");
//		try {
//			HibernateUtil.getSession().lock(instance, LockMode.NONE);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void delete(VentCliv persistentInstance) {
//		log.debug("deleting VentCliv instance");
//		try {
//			HibernateUtil.getSession().delete(persistentInstance);
//			log.debug("delete successful");
//		} catch (RuntimeException re) {
//			log.error("delete failed", re);
//			throw re;
//		}
//	}
//
//	public VentCliv merge(VentCliv detachedInstance) {
//		log.debug("merging VentCliv instance");
//		try {
//			VentCliv result = (VentCliv) HibernateUtil.getSession().merge(detachedInstance);
//			log.debug("merge successful");
//			return result;
//		} catch (RuntimeException re) {
//			log.error("merge failed", re);
//			throw re;
//		}
//	}
//
//	public VentCliv findById(int id) {
//		log.debug("getting VentCliv instance with id: " + id);
//		try {
//			VentCliv instance = (VentCliv) HibernateUtil.getSession().get("ar.com.rollpaper.pricing.beans.VentCliv",
//					id);
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
//	public List<VentCliv> findByExample(VentCliv instance) {
//		log.debug("finding VentCliv instance by example");
//		try {
//			List<VentCliv> results = HibernateUtil.getSession()
//					.createCriteria("ar.com.rollpaper.pricing.beans.VentCliv").add(Example.create(instance)).list();
//			log.debug("find by example successful, result size: " + results.size());
//			return results;
//		} catch (RuntimeException re) {
//			log.error("find by example failed", re);
//			throw re;
//		}
//	}

	public static List<VentCliv> getListaPreciosByCliente(CcobClie cliente) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<VentCliv> criteriaQuery = session.getCriteriaBuilder().createQuery(VentCliv.class);
		Root<VentCliv> i = criteriaQuery.from(VentCliv.class);
		criteriaQuery.where(cb.equal(i.get("clivCliente"), cliente.getClieCliente()));
		List<VentCliv> listaLP = session.createQuery(criteriaQuery).getResultList();
		return listaLP;

	}

}
