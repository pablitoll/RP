package ar.com.rollpaper.pricing.dao;
// Generated 18/05/2018 07:33:44 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class StocArts.
 * 
 * @see ar.com.rollpaper.pricing.beans.StocArts
 * @author Hibernate Tools
 */
public class StocArtsDAO {

	private static final Log log = LogFactory.getLog(StocArtsDAO.class);
	//
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
	// public void persist(StocArts transientInstance) {
	// log.debug("persisting StocArts instance");
	// try {
	// sessionFactory.getCurrentSession().persist(transientInstance);
	// log.debug("persist successful");
	// } catch (RuntimeException re) {
	// log.error("persist failed", re);
	// throw re;
	// }
	// }
	//
	// public void attachDirty(StocArts instance) {
	// log.debug("attaching dirty StocArts instance");
	// try {
	// sessionFactory.getCurrentSession().saveOrUpdate(instance);
	// log.debug("attach successful");
	// } catch (RuntimeException re) {
	// log.error("attach failed", re);
	// throw re;
	// }
	// }
	//
	// public void attachClean(StocArts instance) {
	// log.debug("attaching clean StocArts instance");
	// try {
	// sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
	// log.debug("attach successful");
	// } catch (RuntimeException re) {
	// log.error("attach failed", re);
	// throw re;
	// }
	// }
	//
	// public void delete(StocArts persistentInstance) {
	// log.debug("deleting StocArts instance");
	// try {
	// sessionFactory.getCurrentSession().delete(persistentInstance);
	// log.debug("delete successful");
	// } catch (RuntimeException re) {
	// log.error("delete failed", re);
	// throw re;
	// }
	// }
	//
	// public StocArts merge(StocArts detachedInstance) {
	// log.debug("merging StocArts instance");
	// try {
	// StocArts result = (StocArts)
	// sessionFactory.getCurrentSession().merge(detachedInstance);
	// log.debug("merge successful");
	// return result;
	// } catch (RuntimeException re) {
	// log.error("merge failed", re);
	// throw re;
	// }
	// }

	public static StocArts findById(int id) {
		log.debug("getting StocArts instance with id: " + id);
		try {
			StocArts instance = (StocArts) HibernateUtil.getSession().get("ar.com.rollpaper.pricing.beans.StocArts", id);
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

	public static List<StocArts> getListaArticulos(String nombre) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<StocArts> criteriaQuery = session.getCriteriaBuilder().createQuery(StocArts.class);
		Root<StocArts> i = criteriaQuery.from(StocArts.class);
		criteriaQuery.where(cb.or(cb.like(i.get("artsNombre"), "%" + nombre + "%"), cb.like(i.get("artsArticuloEmp"), "%" + nombre + "%")), cb.equal(i.get("artsSeVende"), true));
		List<StocArts> clientes = session.createQuery(criteriaQuery).getResultList();

		return clientes;
	}

	public static List<StocArts> getListaArticulosxFamilia(String familiaID) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<StocArts> criteriaQuery = session.getCriteriaBuilder().createQuery(StocArts.class);
		Root<StocArts> i = criteriaQuery.from(StocArts.class);
		criteriaQuery.where(cb.equal(i.get("artsClasif1"), familiaID));
		List<StocArts> clientes = session.createQuery(criteriaQuery).getResultList();

		return clientes;

	}

	// public List findByExample(StocArts instance) {
	// log.debug("finding StocArts instance by example");
	// try {
	// List results =
	// sessionFactory.getCurrentSession().createCriteria("ar.com.rollpaper.pricing.beans.StocArts").add(Example.create(instance)).list();
	// log.debug("find by example successful, result size: " + results.size());
	// return results;
	// } catch (RuntimeException re) {
	// log.error("find by example failed", re);
	// throw re;
	// }
	// }

	public static StocArts getArticulo(String idEmp) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<StocArts> criteriaQuery = session.getCriteriaBuilder().createQuery(StocArts.class);
		Root<StocArts> i = criteriaQuery.from(StocArts.class);
		criteriaQuery.where(cb.equal(i.get("artsArticuloEmp"), idEmp), cb.equal(i.get("artsSeVende"), true));
		return session.createQuery(criteriaQuery).getSingleResult();
	}

	public static StocArts getArticuloByID(int id) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<StocArts> criteriaQuery = session.getCriteriaBuilder().createQuery(StocArts.class);
		Root<StocArts> i = criteriaQuery.from(StocArts.class);
		criteriaQuery.where(cb.equal(i.get("artsArticulo"), id));
		return session.createQuery(criteriaQuery).getSingleResult();
	}
}
