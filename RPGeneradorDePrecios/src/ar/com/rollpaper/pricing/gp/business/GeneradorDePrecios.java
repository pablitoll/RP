package ar.com.rollpaper.pricing.gp.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ar.com.rollpaper.pricing.beans.CcobClie;
import ar.com.rollpaper.pricing.beans.DescuentoXFamilias;
import ar.com.rollpaper.pricing.beans.MaestroEsclavo;
import ar.com.rollpaper.pricing.beans.PreciosEspeciales;
import ar.com.rollpaper.pricing.beans.StocArts;
import ar.com.rollpaper.pricing.beans.VentArpc;
import ar.com.rollpaper.pricing.beans.VentArpv;
import ar.com.rollpaper.pricing.beans.VentArpvId;
import ar.com.rollpaper.pricing.beans.VentLipv;
import ar.com.rollpaper.pricing.dao.DescuentoXFamiliasDAO;
import ar.com.rollpaper.pricing.dao.MaestroEsclavoDAO;
import ar.com.rollpaper.pricing.dao.PreciosEspecialesDAO;
import ar.com.rollpaper.pricing.dao.StocArtsDAO;
import ar.com.rollpaper.pricing.dao.VentArpcDAO;
import ar.com.rollpaper.pricing.dao.VentArpvDAO;
import ar.com.rollpaper.pricing.dao.VentLipvDAO;
import ar.com.rollpaper.pricing.gp.beans.Articulo;

public class GeneradorDePrecios {

	public static void generarPrecios() {

		// primero busco la lista de precios por familia

		List<VentLipv> Listas = BuscarListasPrecios();
		for (Object lista : Listas) {

			generarPreciosPorFamilia(lista);
			generarPreciosEspeciales(lista);

		}
	}

	/**
	 * Genera la lista de precios para el cliente pasado por parametro
	 * 
	 * 
	 * @author pmv1283
	 * @param recibe
	 *            como parametro un cliente y una lista de precios
	 * 
	 **/
	public static void generarListaPreciosPorClienteLista(CcobClie cliente, VentLipv lista) {
		// Listo los precios para la combinacion cliente lista
		List<VentArpv> listaPrecios = VentArpvDAO.findByListaID(lista.getLipvListaPrecvta());
		// listo los precios originales
		System.out.println("Precios Originales " + lista.getLipvNombre() + " Cliente : " + cliente.getClieNombre());

		// lista de precios finales a insertar
		LinkedHashMap<VentArpv,BigDecimal> listaPreciosFinal = new LinkedHashMap<>();
		
		for (VentArpv v : listaPrecios) {
			System.out.println("Articulo: " + v.getId().getArpvArticulo() + " Precio ->" + v.getArpvPrecioVta());
		}
		// TODO ver aca la fecha del dia y como manejarla
		Date hoy = new Date();
		// aplico los descuentos por familia
		// busco las familias que tienen un descuento configurado en la tabla de pricing
		// para esta relacion Cliente-Lista
		// TODO : falta filtrar correctamente por fecha!!!!!
		List<DescuentoXFamilias> listaDescuentosXFamiliaVigentes = DescuentoXFamiliasDAO.getByClienteLista(cliente,
				lista, hoy);
		List<PreciosEspeciales> listaPreciosEspeciales = PreciosEspecialesDAO.getByClienteLista(cliente, lista, hoy);
		// busco los esclavos de este cliente
		List<MaestroEsclavo> listaMaestroEsclavo = MaestroEsclavoDAO.getListaEsclavosByCliente(cliente);

		// por cada producto que perteneza a una familia debo aplicar el descuento
		for (VentArpv articulo : listaPrecios) {
			
			// inicio calculo de descuentos por familia para cada articulo
			System.out.println("<<<<<<<<<<<<<<inicio Descuentos por familia>>>>>>>>>>>>>");
			BigDecimal precio = calcularDescuentosXFamilia(listaDescuentosXFamiliaVigentes, articulo);
			System.out.println("<<<<<<<<<<<<<<fin Descuentos por familia>>>>>>>>>>>>>");
			System.out.println("<<<<<<<<<<<<<<Inicio Descuentos precio Especial>>>>>>>>>>>>>");

			precio = calcularPreciosEspeciales(listaPreciosEspeciales, articulo);
			System.out.println("<<<<<<<<<<<<<<<<fin Descuentos precio especial>>>>>>>>>>>>>>");
			
			
			listaPreciosFinal.put(articulo, precio);
			// aplico los precios a los esclavos.
			// para cada esclavo le aplico los precios de este producto en la lista
			System.out.println("<<<<<<<<<<<<<<<<inicio Descuentos esclavos>>>>>>>>>>>>>>");
			for (MaestroEsclavo maestroEsclavo : listaMaestroEsclavo) {

				System.out.println("Maestro:" + maestroEsclavo.getPricMaestroCliente() + " Esclavo:"
						+ maestroEsclavo.getPricEsclavoCliente());

			}
			System.out.println("<<<<<<<<<<<<<<<<fin Descuentos esclavos>>>>>>>>>>>>>>");

		}
		
		
		System.out.println("<<<<<Lista de Precios final para "+lista.getLipvNombre() +">>>>>");
		System.out.println("A Aplicar a los siguientes clientes:");
		System.out.println("Maestro :" + cliente.getClieCliente() +"-" + cliente.getClieNombre());
		for (MaestroEsclavo maestroEsclavo : listaMaestroEsclavo) {
			System.out.println("Esclavo " + maestroEsclavo.getPricEsclavoCliente());
				
		}
		
		

		for (Entry<VentArpv, BigDecimal> art : listaPreciosFinal.entrySet()) {
			
            String nombreArticulo = StocArtsDAO.getArticuloByID(art.getKey().getId().getArpvArticulo()).getArtsNombre();
    		int Lista = art.getKey().getId().getArpvListaPrecvta();
            System.out.println("Lista ->" + Lista + " Articulo ->"+nombreArticulo + "---->$" + art.getValue());
		}
		
		System.out.println("<<<<<fin proceso>>>>>");

	}

	/**
	 * @param listaPreciosEspeciales
	 * @param articulo
	 */
	private static BigDecimal calcularPreciosEspeciales(List<PreciosEspeciales> listaPreciosEspeciales,
			VentArpv articulo) {
		// inicio del proceso que calcula el descuento especial para el articulo

		// busco en los la tabla de descuentos si tiene algun registro para ese articulo
		List<PreciosEspeciales> descuentoEspecial = listaPreciosEspeciales.stream()
				.filter(item -> item.getPricArticulo() == articulo.getId().getArpvArticulo())
				.collect(Collectors.toList());

		BigDecimal precioFinal = BigDecimal.ZERO;
		BigDecimal precio_original = articulo.getArpvPrecioVta();
		precioFinal = precio_original;

		if (descuentoEspecial.size() > 0) {
			BigDecimal descuento1 = descuentoEspecial.get(0).getPricDescuento1();
			BigDecimal descuento2 = descuentoEspecial.get(0).getPricDescuento2();
			BigDecimal precioEspecifico = descuentoEspecial.get(0).getPricPrecio();
			if (descuento1 != null) {
				precioFinal = (precio_original.multiply(BigDecimal.ONE.subtract(descuento1)));
			}
			if (descuento2 != null) {
				precioFinal = precioFinal.multiply(BigDecimal.ONE.subtract(descuento2));
			}
			if (precioEspecifico != null) {
				precioFinal = precioEspecifico;
			}
		}

		String nombreArticulo = StocArtsDAO.getArticuloByID(articulo.getId().getArpvArticulo()).getArtsNombre();
		System.out.println("articulo " + nombreArticulo + " Precio Original " + precio_original
				+ " Precio con descuento especial:" + precioFinal);
		return precioFinal;
	}

	/**
	 * @param listaDescuentosXFamiliaVigentes
	 * @param v
	 */
	private static BigDecimal calcularDescuentosXFamilia(List<DescuentoXFamilias> listaDescuentosXFamiliaVigentes,
			VentArpv v) {
		// obtengo la familia de cada articulo (ArtsClasif1)
		StocArts articulo = StocArtsDAO.getArticuloByID(v.getId().getArpvArticulo());
		System.out.println("Articulo: " + v.getId().getArpvArticulo() + " familia ->" + articulo.getArtsClasif1());
		List<Articulo> ListaPreciosCalculados = new ArrayList<>();

		// articulo a = new Articulo(articulo, v);

		// busco en la tabla de descuentos
		List<DescuentoXFamilias> DescuentoxFamiliaAAplicar = listaDescuentosXFamiliaVigentes.stream()
				.filter(item -> item.getPricCa01Clasif1().equals(articulo.getArtsClasif1()))
				.collect(Collectors.toList());

		// si hay algo en la DescuentoxFamiliaAAplicar debo aplicarse ese descuento al
		// producto

		BigDecimal precioFinal = BigDecimal.ZERO;
		BigDecimal precio_original = v.getArpvPrecioVta();
		precioFinal = precio_original;

		if (DescuentoxFamiliaAAplicar.size() > 0) {

			BigDecimal descuento1 = DescuentoxFamiliaAAplicar.get(0).getPricFamiliaDescuento1();
			BigDecimal descuento2 = DescuentoxFamiliaAAplicar.get(0).getPricFamiliaDescuento2();
			precioFinal = (precio_original.multiply(BigDecimal.ONE.subtract(descuento1)));
			if (descuento2 != null) {
				precioFinal = precioFinal.multiply(BigDecimal.ONE.subtract(descuento2));
			}

		}

		System.out.println("articulo " + articulo.getArtsNombre() + " Precio Original " + precio_original
				+ " Precio con descuento x familia:" + precioFinal);
		return precioFinal;
	}

	/**
	 * Busca todas las listas de Precios Activas y recopila en el objeto lista todos
	 * los datos necesarios para el calculo
	 * 
	 * @param sin
	 *            parametros de entrada
	 * @return an list of ListaPrecios
	 */
	private static List<VentLipv> BuscarListasPrecios() {

		return VentLipvDAO.getAllLists();
	}

	private static void generarPreciosEspeciales(Object lista) {
		System.out.println("Generando precios especiales");

	}

	private static List<Object> generarPreciosPorFamilia(Object lista) {

		System.out.println("Generando precios por familia");
		return null;

	}

}
