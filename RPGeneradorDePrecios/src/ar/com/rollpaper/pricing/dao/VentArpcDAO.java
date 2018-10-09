package ar.com.rollpaper.pricing.dao;
// Generated 21/05/2018 20:09:26 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.VentArpc;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.data.HibernateUtil;

/**
 * Home object for domain model class VentArpc.
 * 
 * @see ar.com.rollpaper.pricing.beans.VentArpc
 * @author Hibernate Tools
 */
public class VentArpcDAO {

	public static List<VentArpc> findByListaByClient(int clieCliente, int lipvListaPrecvta) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<VentArpc> criteriaQuery = session.getCriteriaBuilder().createQuery(VentArpc.class);
		Root<VentArpc> i = criteriaQuery.from(VentArpc .class);

		criteriaQuery.where(cb.equal(i.get("id").get("arpcCliente") , clieCliente), cb.equal(i.get("id").get("arpcListaPrecvta"), lipvListaPrecvta));
		
		return  session.createQuery(criteriaQuery).getResultList();
	}
	// public static List<VentArpc> findByListaID(int lipvListaPrecvta) {
	// return null;
	// }

	// private static final Log log = LogFactory.getLog(VentArpcDAO.class);

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
	// public void persist(VentArpc transientInstance) {
	// log.debug("persisting VentArpc instance");
	// try {
	// sessionFactory.getCurrentSession().persist(transientInstance);
	// log.debug("persist successful");
	// } catch (RuntimeException re) {
	// log.error("persist failed", re);
	// throw re;
	// }
	// }
	//
	 public static void attachDirty(VentArpc instance) {
	// log.debug("attaching dirty VentArpc instance");
	 try {
	// HibernateUtil.getSession().beginTransaction();
		 HibernateUtil.getSession().saveOrUpdate(instance);
	//	 HibernateUtil.getSession().getTransaction().commit();
	 //log.debug("attach successful");
	 } catch (RuntimeException re) {
	// log.error("attach failed", re);
	 throw re;
	 }
	 }
	//
	// public void attachClean(VentArpc instance) {
	// log.debug("attaching clean VentArpc instance");
	// try {
	// sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
	// log.debug("attach successful");
	// } catch (RuntimeException re) {
	// log.error("attach failed", re);
	// throw re;
	// }
	// }
	//
	// public void delete(VentArpc persistentInstance) {
	// log.debug("deleting VentArpc instance");
	// try {
	// sessionFactory.getCurrentSession().delete(persistentInstance);
	// log.debug("delete successful");
	// } catch (RuntimeException re) {
	// log.error("delete failed", re);
	// throw re;
	// }
	// }
	//
	// public VentArpc merge(VentArpc detachedInstance) {
	// log.debug("merging VentArpc instance");
	// try {
	// VentArpc result = (VentArpc)
	// sessionFactory.getCurrentSession().merge(detachedInstance);
	// log.debug("merge successful");
	// return result;
	// } catch (RuntimeException re) {
	// log.error("merge failed", re);
	// throw re;
	// }
	// }
	//
	// public VentArpc findById(VentArpcId id) {
	// log.debug("getting VentArpc instance with id: " + id);
	// try {
	// VentArpc instance = (VentArpc) sessionFactory.getCurrentSession()
	// .get("ar.com.rollpaper.pricing.beans.VentArpc", id);
	// if (instance == null) {
	// log.debug("get successful, no instance found");
	// } else {
	// log.debug("get successful, instance found");
	// }
	// return instance;
	// } catch (RuntimeException re) {
	// log.error("get failed", re);
	// throw re;
	// }
	// }
	//
	// public List findByExample(VentArpc instance) {
	// log.debug("finding VentArpc instance by example");
	// try {
	// List results = sessionFactory.getCurrentSession()
	// .createCriteria("ar.com.rollpaper.pricing.beans.VentArpc").add(Example.create(instance))
	// .list();
	// log.debug("find by example successful, result size: " + results.size());
	// return results;
	// } catch (RuntimeException re) {
	// log.error("find by example failed", re);
	// throw re;
	// }
	// }

	public static List<VentArpc> findByListaByClient(CcobClie c, VentLipv listaCargada) {
	return	findByListaByClient(c.getClieCliente(), listaCargada.getLipvListaPrecvta());
		
	}

}
