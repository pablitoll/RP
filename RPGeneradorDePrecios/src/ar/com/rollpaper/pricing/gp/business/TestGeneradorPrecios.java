package ar.com.rollpaper.pricing.gp.business;

import ar.com.rollpaper.pricing.dao.CcobClieDAO;
import ar.com.rollpaper.pricing.dao.StocCa01DAO;
import ar.com.rollpaper.pricing.dao.VentClivDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;

public class TestGeneradorPrecios {

	public static void main(String[] args) {
		//genero la lista de precios para el cliente 54(AA2000) y la lista de precios 1(lista general)

		int cliente =Integer.parseInt(args[0]);
		int lista = Integer.parseInt(args[1]);
		System.out.println(	"cliente:");
		GeneradorDePrecios.generarListaPreciosPorClienteLista(CcobClieDAO.findById(cliente), VentLipvDAO.findById(lista));
		System.exit(0);

		//GeneradorDePrecios.generarListaPreciosPorClienteLista(CcobClieDAO.findById(386), VentLipvDAO.findById(1));
	}

}
