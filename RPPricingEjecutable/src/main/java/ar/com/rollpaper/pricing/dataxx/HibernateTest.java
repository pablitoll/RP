package ar.com.rollpaper.pricing.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.dao.HibernateGeneric;

public class HibernateTest {

	public static void main(String[] args) throws HibernateException, Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		CriteriaQuery<CcobClie> criteriaQuery = session.getCriteriaBuilder().createQuery(CcobClie.class);
		criteriaQuery.from(CcobClie.class);

		List<CcobClie> clientes = session.createQuery(criteriaQuery).getResultList();
	
		Iterator<CcobClie> itr = clientes.iterator();
		int i = 0;
		while (itr.hasNext()) {

			CcobClie emp = itr.next();
			System.out.println(emp.getClieNombre()+"-"+ i++);

		}
		
		CriteriaQuery<StocArts> criteriaQuery1 = session.getCriteriaBuilder().createQuery(StocArts.class);
		criteriaQuery1.from(StocArts.class);

		List<StocArts> stock = session.createQuery(criteriaQuery1).getResultList();
	
		Iterator<StocArts> itr1 = stock.iterator();
	    i = 0;
		while (itr1.hasNext()) {

			StocArts emp = itr1.next();
			System.out.println(emp.getArtsDescripcion()+"-"+ i++);

		}

		
		// VentClivDAO
		
		CriteriaQuery<VentCliv> criteriaQuery11 = session.getCriteriaBuilder().createQuery(VentCliv.class);
		criteriaQuery11.from(VentCliv.class);

		List<VentCliv> ListaLP = session.createQuery(criteriaQuery11).getResultList();
	
		Iterator<VentCliv> itr11 = ListaLP.iterator();
	    i = 0;
		while (itr11.hasNext()) {

			VentCliv listaprecios = itr11.next();
			System.out.println(listaprecios.getClivCliente()+"-"+ i++);

		}
		

//		VentClivDAO listaPrecio = new VentClivDAO();		
//		VentCliv x = listaPrecio.findById(28);
		
//		VentClivDAO listaPrecio1 = new VentClivDAO();		
//		List<VentCliv> x1 = listaPrecio1.getListaPreciosByCliente(CcobClieDAO.findById(28));
		
		
		@SuppressWarnings("deprecation")
		PreciosEspeciales pe = new PreciosEspeciales( 28 , 100, 10, 10,
				new BigDecimal(2.0),new BigDecimal(3.0),
				"DOL", new BigDecimal(4.0), new Date(2018 ,05,18), new Date(2099,12,31), new BigDecimal(3.0), "");
		
			HibernateGeneric.persist(pe);
		//session.flush();
		//session.save(pe);
		session.getTransaction().commit();
		session.close();

		HibernateUtil.shutdown();
	}
}
