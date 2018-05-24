package ar.com.rollpaper.pricing.data;

import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentCliv;
import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;


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

		
		// VentClivDAO
		
		CriteriaQuery<VentCliv> criteriaQuery11 = session.getCriteriaBuilder().createQuery(VentCliv.class);
		criteriaQuery11.from(VentCliv.class);

		List<VentCliv> ListaLP = session.createQuery(criteriaQuery11).getResultList();
	
		Iterator itr11 = ListaLP.iterator();
	    i = 0;
		while (itr11.hasNext()) {

			VentCliv listaprecios = (VentCliv) itr11.next();
			System.out.println(listaprecios.getClivCliente()+"-"+ i++);

		}
		

		VentClivDAO listaPrecio = new VentClivDAO();		
		VentCliv x = listaPrecio.findById(28);
		
		VentClivDAO listaPrecio1 = new VentClivDAO();		
		List<VentCliv> x1 = listaPrecio1.getListaPreciosByCliente(CcobClieDAO.findById(28));
		
		
		//PreciosEspeciales pe = new PreciosEspeciales(1, 28 , 100, 334, 10, 0, "DOL", 1, new Date(2018 ,05,18), new Date(2099,12,31));
		
		session.getTransaction().commit();
		session.close();

		HibernateUtil.shutdown();
	}
}
