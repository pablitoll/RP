package ar.com.rollpaper.pricing.dao;
// Generated 18/05/2018 07:33:44 by Hibernate Tools 5.3.0.Beta2

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.data.HibernateUtil;
import ar.com.rp.rpcutils.CommonUtils;

/**
 * Home object for domain model class CcobClie.
 * 
 * @see ar.com.rollpaper.pricing.beans.CcobClie
 * @author Hibernate Tools
 */
public class CcobClieDAO {

	private static final Log log = LogFactory.getLog(CcobClieDAO.class);

	public static CcobClie findById(int id) {
		log.debug("getting CcobClie instance with id: " + id);
		try {
			CcobClie instance = (CcobClie) HibernateUtil.getSession().get("ar.com.rollpaper.pricing.beans.CcobClie", id);
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

	public static List<CcobClie> getListaCliente(String nombre) {
		Integer id = -1;
		if (CommonUtils.isNumeric(nombre)) {
			id = Integer.valueOf(nombre);
		}

		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<CcobClie> criteriaQuery = session.getCriteriaBuilder().createQuery(CcobClie.class);
		Root<CcobClie> i = criteriaQuery.from(CcobClie.class);
		criteriaQuery.where(cb.or(cb.like(i.get("clieNombre"), "%" + nombre + "%"), cb.equal(i.get("clieCliente"), id)));

		List<CcobClie> clientes = session.createQuery(criteriaQuery).getResultList();

		return clientes;

	}

}
