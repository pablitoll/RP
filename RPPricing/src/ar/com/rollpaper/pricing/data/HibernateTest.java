package ar.com.rollpaper.pricing.data;

import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.CcobClie;


public class HibernateTest {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Check database version
		String sql = "SELECT \r\n"
				+ "substring(replace(replace(replace(replace(cast(CAST(GETDATE() AS datetime2) as \r\n"
				+ "varchar(50)),'-',''),' ',''),':',''),'.',''),1,18)";

		String result = (String) session.createNativeQuery(sql).getSingleResult();
		System.out.println(result);

		CriteriaQuery<CcobClie> criteriaQuery = session.getCriteriaBuilder().createQuery(CcobClie.class);
		criteriaQuery.from(CcobClie.class);

		List<CcobClie> clientes = session.createQuery(criteriaQuery).getResultList();
	
		Iterator itr = clientes.iterator();
		int i = 0;
		while (itr.hasNext()) {

			CcobClie emp = (CcobClie) itr.next();
			System.out.println(emp.getClieNombre()+"-"+ i++);

		}

		session.getTransaction().commit();
		session.close();

		HibernateUtil.shutdown();
	}
}
