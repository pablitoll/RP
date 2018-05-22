package ar.com.rollpaper.pricing.data;

import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.StocArts;


public class HibernateTest {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		

		CriteriaQuery<CcobClie> criteriaQuery = session.getCriteriaBuilder().createQuery(CcobClie.class);
		criteriaQuery.from(CcobClie.class);

		List<CcobClie> clientes = session.createQuery(criteriaQuery).getResultList();
	
		Iterator itr = clientes.iterator();
		int i = 0;
		while (itr.hasNext()) {

			CcobClie emp = (CcobClie) itr.next();
			System.out.println(emp.getClieNombre()+"-"+ i++);

		}


		
		
		CriteriaQuery<StocArts> criteriaQuery1 = session.getCriteriaBuilder().createQuery(StocArts.class);
		criteriaQuery1.from(StocArts.class);

		List<StocArts> stock = session.createQuery(criteriaQuery1).getResultList();
	
		Iterator itr1 = stock.iterator();
	    i = 0;
		while (itr1.hasNext()) {

			StocArts emp = (StocArts) itr1.next();
			System.out.println(emp.getArtsDescripcion()+"-"+ i++);

		}

		
		
		
		session.getTransaction().commit();
		session.close();

		HibernateUtil.shutdown();
	}
}
