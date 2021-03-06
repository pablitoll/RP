package ar.com.rollpaper.pricing.dao;
// Generated 24/05/2018 09:03:46 by Hibernate Tools 5.3.0.Beta2

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.StocCa01;
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

	public static DescuentoXFamilias findById(int id) {
		log.debug("getting PricDescuentoXFamilias instance with id: " + id);
		try {
			Session session = HibernateUtil.getSession();
			DescuentoXFamilias instance = (DescuentoXFamilias) session
					.get("ar.com.rollpaper.pricing.beans.PricDescuentoXFamilias", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}

			return agregarDescripcion(instance);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	// Deberia ser un left join....
	private static DescuentoXFamilias agregarDescripcion(DescuentoXFamilias descuentoXFamilia) {
		StocCa01 familiaClass = StocCa01DAO.findById(descuentoXFamilia.getPricCa01Clasif1());
		descuentoXFamilia.setNombreFamilia(familiaClass.getCa01Nombre());
		return descuentoXFamilia;
	}

	private static List<DescuentoXFamilias> agregarDescripcion(List<DescuentoXFamilias> listaDescuentoXFamilia) {

		for (DescuentoXFamilias descuentoXFamilias : listaDescuentoXFamilia) {
			StocCa01 familiaClass = StocCa01DAO.findById(descuentoXFamilias.getPricCa01Clasif1());
			descuentoXFamilias.setNombreFamilia(familiaClass.getCa01Nombre());
		}

		return listaDescuentoXFamilia;
	}

	public static List<DescuentoXFamilias> getListaDescuentoByID(Integer pricFamiliaCliente, Integer nroLista) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<DescuentoXFamilias> criteriaQuery = session.getCriteriaBuilder()
				.createQuery(DescuentoXFamilias.class);
		Root<DescuentoXFamilias> i = criteriaQuery.from(DescuentoXFamilias.class);
		criteriaQuery.where(cb.equal(i.get("pricFamiliaCliente"), pricFamiliaCliente),
				cb.equal(i.get("pricFamiliaListaPrecvta"), nroLista));

		List<DescuentoXFamilias> descuentos = session.createQuery(criteriaQuery).getResultList();

		return agregarDescripcion(descuentos);
	}

	public static List<DescuentoXFamilias> getByCliente(int clieCliente) {
		Session session = HibernateUtil.getSession();
		CriteriaBuilder cb = session.getEntityManagerFactory().getCriteriaBuilder();

		CriteriaQuery<DescuentoXFamilias> criteriaQuery = session.getCriteriaBuilder()
				.createQuery(DescuentoXFamilias.class);
		Root<DescuentoXFamilias> i = criteriaQuery.from(DescuentoXFamilias.class);
		criteriaQuery.where(cb.equal(i.get("pricFamiliaCliente"), clieCliente));

		List<DescuentoXFamilias> descuentos = session.createQuery(criteriaQuery).getResultList();

		return agregarDescripcion(descuentos);
	}

	public static List<DescuentoXFamilias> getByClienteLista(CcobClie cliente, VentLipv lista, Date fechaVigencia) {

		return getListaDescuentoByID(cliente.getClieCliente(), lista.getLipvListaPrecvta());
	}

	public static List<DescuentoXFamilias> getByClienteListaVigente(CcobClie cliente, VentLipv lista,
			Date fechaVigencia) {
		List<DescuentoXFamilias> listasVigentes = new ArrayList<>();

		List<DescuentoXFamilias> listas = getListaDescuentoByID(cliente.getClieCliente(), lista.getLipvListaPrecvta());
		for (DescuentoXFamilias desc : listas) {
			if (desc.isvigente(fechaVigencia)) {
				listasVigentes.add(desc);
			}
		}
		return agregarDescripcion(listasVigentes);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static List<DescuentoXFamilias> getAll() {
		Session session = HibernateUtil.getSession();

//		CriteriaQuery<DescuentoXFamilias> criteriaQuery = session.getCriteriaBuilder()
//				.createQuery(DescuentoXFamilias.class);
//		List<DescuentoXFamilias> descuentos = session.createQuery(criteriaQuery).getResultList();
		
		Criteria criteria = session.createCriteria(DescuentoXFamilias.class);
		List<DescuentoXFamilias> descuentos = criteria.list();

		
		return agregarDescripcion(descuentos);
	}

	@SuppressWarnings("unchecked")
	public static List<DescuentoXFamilias> getListaDescuentoByFiltros(Integer idCliente, Date fechaVencidosAl,
			Date fechaVencidosDesde) {
		Session session = HibernateUtil.getSession();

		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(DescuentoXFamilias.class);

		if (idCliente != null) {
			criteria.add(Restrictions.eq("pricFamiliaCliente", idCliente));
		}

		criteria.add(Restrictions.lt("pricFamiliaFechaHasta", fechaVencidosAl));

		if (fechaVencidosDesde != null) {
			criteria.add(Restrictions. gt("pricFamiliaFechaHasta", fechaVencidosDesde));
		}
		
		List<DescuentoXFamilias> descuentos = criteria.list();

		return agregarDescripcion(descuentos);
	}

}
