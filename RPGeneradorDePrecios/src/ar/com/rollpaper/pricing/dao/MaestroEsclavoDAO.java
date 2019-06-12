package ar.com.rollpaper.pricing.dao;
// Generated 24/05/2018 09:03:46 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class PricMaestroEsclavo.
 * 
 * @see ar.com.rollpaper.pricing.beans.MaestroEsclavo.PricMaestroEsclavo
 * @author Hibernate Tools
 */
public class MaestroEsclavoDAO {

	private static final Log log = LogFactory.getLog(MaestroEsclavoDAO.class);

//	public static void persist(MaestroEsclavo transientInstance) {
//		log.debug("persisting PricMaestroEsclavo instance");
//		try {
//
//			// HibernateUtil.persist(transientInstance);
//
//			log.debug("persist successful");
//		} catch (RuntimeException re) {
//			log.error("persist failed", re);
//			throw re;
//		}
//	}
//
//	public void attachDirty(MaestroEsclavo instance) {
//		log.debug("attaching dirty PricMaestroEsclavo instance");
//		try {
//			Session session = HibernateUtil.getSession();
//			session.saveOrUpdate(instance);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void attachClean(MaestroEsclavo instance) {
//		log.debug("attaching clean PricMaestroEsclavo instance");
//		try {
//			Session session = HibernateUtil.getSession();
//			session.lock(instance, LockMode.NONE);
//			log.debug("attach successful");
//		} catch (RuntimeException re) {
//			log.error("attach failed", re);
//			throw re;
//		}
//	}
//
//	public void delete(MaestroEsclavo persistentInstance) {
//		log.debug("deleting PricMaestroEsclavo instance");
//		try {
//			Session session = HibernateUtil.getSession();
//			session.delete(persistentInstance);
//			log.debug("delete successful");
//		} catch (RuntimeException re) {
//			log.error("delete failed", re);
//			throw re;
//		}
//	}
//
//	public MaestroEsclavo merge(MaestroEsclavo detachedInstance) {
//		log.debug("merging PricMaestroEsclavo instance");
//		try {
//			Session session = HibernateUtil.getSession();
//			MaestroEsclavo result = (MaestroEsclavo) session.merge(detachedInstance);
//			log.debug("merge successful");
//			return result;
//		} catch (RuntimeException re) {
//			log.error("merge failed", re);
//			throw re;
//		}
//	}

	public static MaestroEsclavo findById(int id) {
		log.debug("getting PricMaestroEsclavo instance with id: " + id);
		try {
			Session session = HibernateUtil.getSession();
			MaestroEsclavo instance = (MaestroEsclavo) session.get("ar.com.rollpaper.pricing.beans.MaestroEsclavo", id);
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

//	public List findByExample(MaestroEsclavo instance) {
//		log.debug("finding PricMaestroEsclavo instance by example");
//		try {
//			Session session = HibernateUtil.getSession();
//			List results = session.createCriteria("ar.com.rollpaper.pricing.beans.PricMaestroEsclavo")
//					.add(Example.create(instance)).list();
//			log.debug("find by example successful, result size: " + results.size());
//			return results;
//		} catch (RuntimeException re) {
//			log.error("find by example failed", re);
//			throw re;
//		}
//	}

	public static List<MaestroEsclavo> getListaEsclavosByCliente(CcobClie cliente) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<MaestroEsclavo> criteriaQuery = session.getCriteriaBuilder().createQuery(MaestroEsclavo.class);
		Root<MaestroEsclavo> i = criteriaQuery.from(MaestroEsclavo.class);
		criteriaQuery.where(cb.equal(i.get("pricMaestroCliente"), cliente.getClieCliente()));

		List<MaestroEsclavo> esclavos = session.createQuery(criteriaQuery).getResultList();

		return esclavos;

	}

	public static MaestroEsclavo findByClienteIdEsclavoID(int clienteID, int esclavoID) {

		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<MaestroEsclavo> criteriaQuery = session.getCriteriaBuilder().createQuery(MaestroEsclavo.class);
		Root<MaestroEsclavo> i = criteriaQuery.from(MaestroEsclavo.class);
		criteriaQuery.where(cb.and(cb.equal(i.get("pricMaestroCliente"), clienteID),cb.equal(i.get("pricEsclavoCliente"), esclavoID)));
		List<MaestroEsclavo> esclavos = session.createQuery(criteriaQuery).getResultList();

		return esclavos.get(0);
	}

	public static List<MaestroEsclavo>  getListaEsclavosByEsclavo(CcobClie esclavo) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<MaestroEsclavo> criteriaQuery = session.getCriteriaBuilder().createQuery(MaestroEsclavo.class);
		Root<MaestroEsclavo> i = criteriaQuery.from(MaestroEsclavo.class);
		criteriaQuery.where(cb.equal(i.get("pricEsclavoCliente"), esclavo.getClieCliente()));

		List<MaestroEsclavo> esclavos = session.createQuery(criteriaQuery).getResultList();

		return esclavos;

		
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public static List<MaestroEsclavo> getListaMaestroEsclavos() {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(MaestroEsclavo.class).addOrder(Order.asc("pricMaestroEsclavoId")).addOrder(Order.asc("pricEsclavoCliente"));

		List<MaestroEsclavo> esclavos = criteria.list();

		return esclavos;
	}
}
