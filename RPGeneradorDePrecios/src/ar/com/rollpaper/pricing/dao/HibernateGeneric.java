package ar.com.rollpaper.pricing.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import ar.com.rollpaper.pricing.data.HibernateUtil;

public class HibernateGeneric {

	private static final Log log = LogFactory.getLog(HibernateGeneric.class);

	public static void merge(Object instance) {
		log.debug("merging PricPreciosEspeciales instance");
		Session session = HibernateUtil.getSession();
		try {
			session.merge(instance);
			log.debug("merge successful");
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public static void persist(Object transientInstance) {
		log.debug("persisting transientInstance instance");
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		try {
			session.persist(transientInstance);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			try {

				session.getTransaction().rollback();
				e.printStackTrace();
				throw e;
			} catch (RuntimeException runtimeEx) {
				System.err.printf("Couldn’t Roll Back Transaction", runtimeEx);
				runtimeEx.printStackTrace();
				throw runtimeEx;
			}

		} finally {

		}
	}

	public static void attachDirty(Object instance) {
		log.debug("eliminando esclavo instance");
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		try {
			HibernateUtil.getSession().saveOrUpdate(instance);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			try {

				session.getTransaction().rollback();
			} catch (RuntimeException runtimeEx) {
				System.err.printf("Couldn’t Roll Back Transaction", runtimeEx);
			}
			e.printStackTrace();
			throw e;

		} finally {

		}
	}

	public static void remove(Object transientInstance) {
		log.debug("eliminando esclavo instance");
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		try {
			session.delete(transientInstance);
			session.flush();
			session.clear();
			session.getTransaction().commit();
		} catch (Exception e) {
			try {

				session.getTransaction().rollback();
			} catch (RuntimeException runtimeEx) {
				System.err.printf("Couldn’t Roll Back Transaction", runtimeEx);
			}
			e.printStackTrace();
			throw e;

		} finally {

		}
	}

}
